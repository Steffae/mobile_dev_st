package ru.mirea.elkinasa.catbreeds.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.mirea.elkinasa.catbreeds.R;
import ru.mirea.elkinasa.catbreeds.presentation.login.LoginActivity;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvEmail = view.findViewById(R.id.tvProfileEmail);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        ProfileViewModel viewModel = new ViewModelProvider(this, new ProfileViewModelFactory(requireContext()))
                .get(ProfileViewModel.class);

        viewModel.getEmail().observe(getViewLifecycleOwner(), email -> {
            if (email.equals("Гость")) {
                tvEmail.setText("Вы не авторизованы");
                btnLogout.setText("Войти");
            } else {
                tvEmail.setText("Вы вошли как: " + email);
                btnLogout.setText("Выйти");
            }
        });

        btnLogout.setOnClickListener(v -> {
            if (viewModel.getEmail().getValue().equals("Гость")) {
                // Переход на экран входа
                startActivity(new android.content.Intent(requireContext(), LoginActivity.class));
                requireActivity().finish();
            } else {
                // Выход
                viewModel.logout();
            }
        });

        return view;
    }
}