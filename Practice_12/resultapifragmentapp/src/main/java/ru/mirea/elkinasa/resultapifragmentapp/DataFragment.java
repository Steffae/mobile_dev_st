package ru.mirea.elkinasa.resultapifragmentapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DataFragment extends Fragment {

    private FragmentListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement FragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        Button button = view.findViewById(R.id.buttonOpenBottomSheet);
        button.setOnClickListener(click -> {
            String text = ((EditText) view.findViewById(R.id.editTextInfo)).getText().toString();

            Bundle bundle = new Bundle();
            bundle.putString("key", text);

            // Отправляем результат в child
            getChildFragmentManager().setFragmentResult("requestKey", bundle);
            listener.sendResult(text);

            BottomSheetFragment bottomSheet = new BottomSheetFragment();
            bottomSheet.show(getChildFragmentManager(), "ModalBottomSheet");
        });

        return view;
    }
}