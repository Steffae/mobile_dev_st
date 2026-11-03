package ru.mirea.elkinasa.recyclerviewapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private List<HistoricalEvent> events;

    public EventAdapter(List<HistoricalEvent> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item_view, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        HistoricalEvent event = events.get(position);
        holder.getTitleView().setText(event.getTitle());
        holder.getDescriptionView().setText(event.getDescription());
        holder.getEventImageView().setImageResource(event.getImageResId());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}