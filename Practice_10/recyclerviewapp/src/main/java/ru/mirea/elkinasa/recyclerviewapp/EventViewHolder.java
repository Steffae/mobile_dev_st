package ru.mirea.elkinasa.recyclerviewapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {
    private ImageView eventImageView;
    private TextView titleView;
    private TextView descriptionView;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        eventImageView = itemView.findViewById(R.id.imageView);
        titleView = itemView.findViewById(R.id.textViewTitle);
        descriptionView = itemView.findViewById(R.id.textViewDescription);
    }

    public ImageView getEventImageView() { return eventImageView; }
    public TextView getTitleView() { return titleView; }
    public TextView getDescriptionView() { return descriptionView; }
}