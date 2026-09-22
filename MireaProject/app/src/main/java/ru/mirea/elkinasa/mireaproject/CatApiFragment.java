package ru.mirea.elkinasa.mireaproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.mirea.elkinasa.mireaproject.R;

public class CatApiFragment extends Fragment {

    private ImageView imageViewCat;
    private TextView textViewStatus;
    private Button buttonLoadCat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cat_api, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageViewCat = view.findViewById(R.id.imageViewCat);
        textViewStatus = view.findViewById(R.id.textViewStatus);
        buttonLoadCat = view.findViewById(R.id.buttonLoadCat);

        buttonLoadCat.setOnClickListener(v -> new LoadCatTask().execute());
    }

    private class LoadCatTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewStatus.setText("Ищу котика...");
            buttonLoadCat.setEnabled(false);
            buttonLoadCat.setText("Загрузка...");
            imageViewCat.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection connection = null;
            try {
                // API с котиками (Cataas - Cat as a Service)
                URL url = new URL("https://cataas.com/cat?json=true");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setDoInput(true);

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                String id = jsonObject.getString("id");
                // Формируем URL картинки
                return "https://cataas.com/cat/" + id;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String imageUrl) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                // Используем Picasso для загрузки изображения
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.ic_menu_gallery)
                        .into(imageViewCat);

                textViewStatus.setText("Вот такой котик!");
                buttonLoadCat.setEnabled(true);
                buttonLoadCat.setText("Показать другого котика");
            } else {
                buttonLoadCat.setEnabled(true);
                buttonLoadCat.setText("Показать котика");
                textViewStatus.setText("Не удалось найти котика, попробуйте ещё раз");
                Toast.makeText(getContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
            }
        }
    }
}