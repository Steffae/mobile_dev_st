package ru.mirea.elkinasa.catbreeds.presentation.breeds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.elkinasa.catbreeds.R;
import ru.mirea.elkinasa.domain.models.BreedItem;

public class BreedsAdapter extends RecyclerView.Adapter<BreedsAdapter.BreedViewHolder> {

    private List<BreedItem> items = new ArrayList<>();
    private OnFavoriteClickListener listener;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(BreedItem item);
    }

    public void setItems(List<BreedItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setListener(OnFavoriteClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_breed, parent, false);
        return new BreedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
        BreedItem item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class BreedViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final ImageView imageFav;
        private final ImageView imageBreed; // Для картинки породы

        public BreedViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewBreedName);
            imageFav = itemView.findViewById(R.id.imageViewFavorite);
            imageBreed = itemView.findViewById(R.id.imageViewBreed);
        }

        public void bind(BreedItem item, OnFavoriteClickListener listener) {
            textName.setText(item.getBreed().getName());

            // Безопасная загрузка картинки
            String imageUrl = item.getBreed().getImageUrl();

            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get()
                        .load(imageUrl)
                        .resize(400, 400)
                        .centerCrop()
                        .placeholder(android.R.drawable.ic_menu_gallery)
                        .error(android.R.drawable.ic_menu_close_clear_cancel)
                        .into(imageBreed);
            } else {
                // Если картинки нет — ставим заглушку, чтобы Picasso не падал
                imageBreed.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            // Закрашенная звездочка
            if (item.isFavorite()) {
                imageFav.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                imageFav.setImageResource(android.R.drawable.btn_star_big_off);
            }

            imageFav.setOnClickListener(v -> {
                if (listener != null) listener.onFavoriteClick(item);
            });
        }
    }
}