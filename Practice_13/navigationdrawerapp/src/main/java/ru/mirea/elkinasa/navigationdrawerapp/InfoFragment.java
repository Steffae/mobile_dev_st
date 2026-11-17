package ru.mirea.elkinasa.navigationdrawerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ru.mirea.elkinasa.navigationdrawerapp.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {
    private FragmentInfoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        binding.textInfo.setText("Помощь и справка");
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}