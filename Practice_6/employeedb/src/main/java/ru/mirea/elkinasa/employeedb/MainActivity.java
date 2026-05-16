package ru.mirea.elkinasa.employeedb;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.elkinasa.employeedb.databinding.ActivityMainBinding;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppDatabase database;
    private EmployeeDao employeeDao;
    private ArrayAdapter<String> adapter;
    private List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Инициализация базы данных
        database = AppDatabase.getInstance(this);
        employeeDao = database.employeeDao();

        // Загрузка списка героев
        loadHeroList();

        // Обработчик добавления героя
        binding.buttonAdd.setOnClickListener(v -> addHero());
    }

    private void addHero() {
        String name = binding.editTextName.getText().toString().trim();
        String superpower = binding.editTextSuperpower.getText().toString().trim();
        String strengthStr = binding.editTextStrength.getText().toString().trim();

        if (name.isEmpty() || superpower.isEmpty() || strengthStr.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int strength = Integer.parseInt(strengthStr);

        Employee employee = new Employee(name, superpower, strength);
        employeeDao.insert(employee);

        Toast.makeText(this, "Герой добавлен!", Toast.LENGTH_SHORT).show();

        // Очистка полей
        binding.editTextName.setText("");
        binding.editTextSuperpower.setText("");
        binding.editTextStrength.setText("");

        // Обновление списка
        loadHeroList();
    }

    private void loadHeroList() {
        employeeList = employeeDao.getAll();

        List<String> heroNames = new java.util.ArrayList<>();
        for (Employee emp : employeeList) {
            heroNames.add(emp.name + " - " + emp.superpower + " (сила: " + emp.strengthLevel + ")");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, heroNames);
        binding.listViewHeroes.setAdapter(adapter);
    }
}