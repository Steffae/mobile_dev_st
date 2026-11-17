package ru.mirea.elkinasa.catbreeds.presentation.breeds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import ru.mirea.elkinasa.catbreeds.R;
import ru.mirea.elkinasa.domain.models.BreedItem;

public class BreedsAdapter extends RecyclerView.Adapter<BreedsAdapter.BreedViewHolder> {

    private List<BreedItem> items = new ArrayList<>();
    private OnFavoriteClickListener favListener;
    private OnBreedClickListener breedListener;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(BreedItem item);
    }

    public interface OnBreedClickListener {
        void onBreedClick(BreedItem item);
    }

    public void setItems(List<BreedItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setFavListener(OnFavoriteClickListener listener) {
        this.favListener = listener;
    }

    public void setBreedListener(OnBreedClickListener listener) {
        this.breedListener = listener;
    }

    @NonNull
    @Override
    public BreedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_breed, parent, false);
        return new BreedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedViewHolder holder, int position) {
        BreedItem item = items.get(position);
        holder.bind(item, favListener, breedListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class BreedViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final ImageView imageFav;
        private final ImageView imageBreed;

        public BreedViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewBreedName);
            imageFav = itemView.findViewById(R.id.imageViewFavorite);
            imageBreed = itemView.findViewById(R.id.imageViewBreed);
        }

        public void bind(BreedItem item, OnFavoriteClickListener favListener, OnBreedClickListener breedListener) {
            textName.setText(item.getBreed().getName());

            String imageUrl = item.getBreed().getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).resize(400, 400).centerCrop().into(imageBreed);
            } else {
                imageBreed.setImageResource(android.R.drawable.ic_menu_gallery);
            }

            if (item.isFavorite()) {
                imageFav.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                imageFav.setImageResource(android.R.drawable.btn_star_big_off);
            }

            // Клик по звездочке
            imageFav.setOnClickListener(v -> {
                if (favListener != null) favListener.onFavoriteClick(item);
            });

            // Клик по всей карточке или названию (переход в детали)
            itemView.setOnClickListener(v -> {
                if (breedListener != null) breedListener.onBreedClick(item);
            });
        }
    }
}