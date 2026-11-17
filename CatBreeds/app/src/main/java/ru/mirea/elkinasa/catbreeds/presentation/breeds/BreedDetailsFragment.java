package ru.mirea.elkinasa.catbreeds.presentation.breeds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.squareup.picasso.Picasso;
import ru.mirea.elkinasa.catbreeds.R;
import ru.mirea.elkinasa.domain.models.CatBreed;

public class BreedDetailsFragment extends Fragment {

    private static final String ARG_ID = "id";
    private static final String ARG_NAME = "name";
    private static final String ARG_DESC = "description";
    private static final String ARG_ORIGIN = "origin";
    private static final String ARG_IMAGE = "image";

    private BreedDetailsViewModel viewModel;

    public static BreedDetailsFragment newInstance(CatBreed breed) {
        BreedDetailsFragment fragment = new BreedDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, breed.getId());
        args.putString(ARG_NAME, breed.getName());
        args.putString(ARG_DESC, breed.getDescription());
        args.putString(ARG_ORIGIN, breed.getOrigin());
        args.putString(ARG_IMAGE, breed.getImageUrl());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_breed_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = view.findViewById(R.id.detailImageView);
        TextView tvName = view.findViewById(R.id.detailName);
        TextView tvOrigin = view.findViewById(R.id.detailOrigin);
        TextView tvDesc = view.findViewById(R.id.detailDescription);
        Button btnBack = view.findViewById(R.id.btnDetailBack);
        ImageView iconFavorite = view.findViewById(R.id.detailFavoriteIcon);

        if (getArguments() == null) return;

        // Восстанавливаем объект CatBreed из Bundle
        String id = getArguments().getString(ARG_ID);
        String name = getArguments().getString(ARG_NAME);
        String desc = getArguments().getString(ARG_DESC);
        String origin = getArguments().getString(ARG_ORIGIN);
        String imageUrl = getArguments().getString(ARG_IMAGE);
        CatBreed breed = new CatBreed(id, name, desc, origin, imageUrl);

        tvName.setText(name);
        tvOrigin.setText("Происхождение: " + origin);
        tvDesc.setText(desc != null && !desc.isEmpty() ? desc : "Описание пока отсутствует.");

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(imageView);
        }

        // Инициализация ViewModel
        viewModel = new ViewModelProvider(this, new BreedDetailsViewModelFactory(requireContext()))
                .get(BreedDetailsViewModel.class);
        viewModel.init(breed);

        // Подписка на статус избранного
        viewModel.getIsFavorite().observe(getViewLifecycleOwner(), isFav -> {
            if (isFav) {
                iconFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                iconFavorite.setImageResource(android.R.drawable.btn_star_big_off);
            }
        });

        // Обработка клика по звездочке
        iconFavorite.setOnClickListener(v -> {
            viewModel.toggleFavorite();
        });

        // Подписка на сообщение о необходимости входа
        viewModel.getShowLoginMessage().observe(getViewLifecycleOwner(), show -> {
            if (show != null && show) {
                Toast.makeText(requireContext(), "Войдите в аккаунт, чтобы добавлять в избранное", Toast.LENGTH_SHORT).show();
                viewModel.onLoginMessageShown();
            }
        });

        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }
}