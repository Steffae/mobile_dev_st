package ru.mirea.elkinasa.listviewapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Массив из 35 книг/авторов
    private String[] books = {
            "Мастер и Маргарита — Булгаков",
            "1984 — Оруэлл",
            "Преступление и наказание — Достоевский",
            "Война и мир — Толстой",
            "Гарри Поттер — Роулинг",
            "Властелин колец — Толкин",
            "Маленький принц — Сент-Экзюпери",
            "Три товарища — Ремарк",
            "Улисс — Джойс",
            "Над пропастью во ржи — Сэлинджер",
            "451 градус по Фаренгейту — Брэдбери",
            "Марсианин — Вейер",
            "Дюна — Герберт",
            "Игра престолов — Мартин",
            "Солярис — Лем",
            "Сталкер — Стругацкие",
            "Пикник на обочине — Стругацкие",
            "Атлант расправил плечи — Рэнд",
            "Граф Монте-Кристо — Дюма",
            "Отверженные — Гюго",
            "Собор Парижской Богоматери — Гюго",
            "Тень ветра — Сафон",
            "Код да Винчи — Браун",
            "Ангелы и демоны — Браун",
            "Шантарам — Робертс",
            "Сто лет одиночества — Маркес",
            "Любовь во время чумы — Маркес",
            "Портрет Дориана Грея — Уайльд",
            "О дивный новый мир — Хаксли",
            "Мы — Замятин",
            "Собачье сердце — Булгаков",
            "Белая гвардия — Булгаков",
            "Зеленая миля — Кинг",
            "Оно — Кинг",
            "Сияние — Кинг"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.books_list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                books
        );

        listView.setAdapter(adapter);
    }
}