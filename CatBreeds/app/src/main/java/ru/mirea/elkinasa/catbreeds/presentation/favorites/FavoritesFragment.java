package ru.mirea.elkinasa.catbreeds.presentation.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.mirea.elkinasa.catbreeds.R;
import ru.mirea.elkinasa.catbreeds.presentation.breeds.BreedsAdapter;
import ru.mirea.elkinasa.catbreeds.presentation.breeds.BreedDetailsFragment;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel = new ViewModelProvider(this, new FavoritesViewModelFactory(requireContext()))
                .get(FavoritesViewModel.class);

        BreedsAdapter adapter = new BreedsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel.getFavorites().observe(getViewLifecycleOwner(), adapter::setItems);

        // Обработка удаления из избранного по клику на звездочку
        adapter.setFavListener(item -> {
            viewModel.removeFavorite(item.getBreed().getId());
        });

        // Обработка перехода в детали породы из избранного
        adapter.setBreedListener(item -> {
            BreedDetailsFragment detailsFragment = BreedDetailsFragment.newInstance(item.getBreed());
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .addToBackStack("details")
                    .commit();
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Обновляем список каждый раз при возвращении на экран Избранного
        if (viewModel != null) {
            viewModel.refresh();
        }
    }
}