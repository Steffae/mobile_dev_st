package ru.mirea.elkinasa.retrofitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Todo> todos;
    private OnTodoCheckedListener listener;

    public interface OnTodoCheckedListener {
        void onChecked(Todo todo, boolean isChecked);
    }

    public TodoAdapter(Context context, List<Todo> todoList, OnTodoCheckedListener listener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.todos = todoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.textViewTitle.setText(todo.getTitle());

        holder.checkBoxCompleted.setOnCheckedChangeListener(null);
        holder.checkBoxCompleted.setChecked(todo.getCompleted());
        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) listener.onChecked(todo, isChecked);
        });

        // Используем picsum.photos, подставляя ID задачи, чтобы картинки были разными
        String imageUrl = "https://picsum.photos/200?random=" + todo.getId();

        Picasso.get()
                .load(imageUrl)
                .resize(128, 128)      // Настраиваем размер
                .centerCrop()          // Обрезаем по центру
                .placeholder(android.R.drawable.ic_menu_gallery) // Заглушка во время загрузки
                .error(android.R.drawable.ic_menu_close_clear_cancel) // Если ошибка
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}