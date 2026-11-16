package ru.mirea.elkinasa.fragmentapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.setReorderingAllowed(true);

            // 1) Статический фрагмент HeaderFragment в контейнер
            tx.replace(R.id.headerContainerView,
                    HeaderFragment.newInstance("Список дел"),
                    "header");

            // 2) Передача номера студента через Bundle в BlankFragment
            Bundle bundle = new Bundle();
            bundle.putInt("my_number_student", 8);
            tx.replace(R.id.detailsContainerView,
                    BlankFragment.class, bundle, "details");

            tx.commit();
        }
    }
}