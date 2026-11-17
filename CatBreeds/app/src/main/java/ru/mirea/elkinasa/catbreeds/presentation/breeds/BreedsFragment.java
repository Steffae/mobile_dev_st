package ru.mirea.elkinasa.catbreeds.presentation.breeds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.mirea.elkinasa.catbreeds.R;

public class BreedsFragment extends Fragment {

    private BreedsAdapter adapter;
    private BreedsListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breeds, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new BreedsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this, new BreedsListViewModelFactory(requireContext()))
                .get(BreedsListViewModel.class);

        viewModel.getBreedsWithFavorite().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
        });

        adapter.setFavListener(item -> {
            viewModel.toggleFavorite(item.getBreed());
        });

        adapter.setBreedListener(item -> {
            // Переход на экран деталей
            BreedDetailsFragment detailsFragment = BreedDetailsFragment.newInstance(item.getBreed());

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .addToBackStack("details") // Добавляем в стек, чтобы работала кнопка Назад
                    .commit();
        });

        // Подписка на сообщение о необходимости входа
        viewModel.getShowLoginMessage().observe(getViewLifecycleOwner(), show -> {
            if (show != null && show) {
                Toast.makeText(requireContext(), "Войдите в аккаунт, чтобы добавлять в избранное", Toast.LENGTH_SHORT).show();
                viewModel.onLoginMessageShown();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Обновляем состояние при возврате из деталей
        if (viewModel != null) {
            viewModel.refreshFavorites();
        }
    }
}