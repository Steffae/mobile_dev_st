package ru.mirea.elkinasa.fragmentapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HeaderFragment extends Fragment {

    private static final String ARG_TEXT = "header_text";

    public static HeaderFragment newInstance(String text) {
        HeaderFragment fragment = new HeaderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(HeaderFragment.class.getSimpleName(), "onCreateView");
        View view = inflater.inflate(R.layout.fragment_header, container, false);
        TextView tv = view.findViewById(R.id.headerText);
        if (getArguments() != null) {
            tv.setText(getArguments().getString(ARG_TEXT, "Список дел"));
        }
        return view;
    }
}
