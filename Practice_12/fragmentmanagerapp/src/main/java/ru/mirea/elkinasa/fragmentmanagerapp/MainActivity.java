package ru.mirea.elkinasa.fragmentmanagerapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity
        implements ListFragment.OnCountrySelectedListener {

    private DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.setReorderingAllowed(true);
            tx.replace(R.id.listContainer, new ListFragment(), "list");
            detailsFragment = new DetailsFragment();
            tx.replace(R.id.detailsContainer, detailsFragment, "details");
            tx.commit();
        } else {
            // Восстанавливаем ссылку после поворота экрана
            detailsFragment = (DetailsFragment)
                    getSupportFragmentManager().findFragmentByTag("details");
        }
    }

    @Override
    public void onCountrySelected(Country country) {
        if (detailsFragment != null) {
            detailsFragment.updateDetails(country);
        }
    }
}