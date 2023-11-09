package com.example.loolah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.databinding.RowToiletFavoriteBinding;
import com.example.loolah.model.Toilet;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteToiletViewHolder>{
        private ArrayList<Toilet> favoritesToiletList;
        private OnItemClickListener onItemClickListener;

        @NonNull
        @Override
        public FavoriteAdapter.FavoriteToiletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            RowToiletFavoriteBinding binding = RowToiletFavoriteBinding.inflate(layoutInflater, parent, false);

            return new FavoriteAdapter.FavoriteToiletViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteToiletViewHolder holder, int position) {
            final Toilet toilet = favoritesToiletList.get(position);
            holder.binding.setToilet(toilet);
            holder.binding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return favoritesToiletList == null ? 0 : favoritesToiletList.size();
        }

        public void setFavoritesToiletList(ArrayList<Toilet> favoritesToiletList) {
            this.favoritesToiletList = favoritesToiletList;
            this.notifyDataSetChanged();
        }

        public void setOnItemClickListener(FavoriteAdapter.OnItemClickListener listener) {
            onItemClickListener = listener;
        }

        class FavoriteToiletViewHolder extends RecyclerView.ViewHolder {
            RowToiletFavoriteBinding binding;

            public FavoriteToiletViewHolder(@NonNull RowToiletFavoriteBinding binding) {
                super(binding.getRoot());
                this.binding = binding;

                binding.lnlToiletFavoriteRow.setOnClickListener(v -> {
                    Toilet toilet = favoritesToiletList.get(getAdapterPosition());
                    onItemClickListener.onSelectToilet(v, toilet);
                });
            }
        }

        public interface OnItemClickListener {
            void onSelectToilet(View view, Toilet toilet);
        }
}
