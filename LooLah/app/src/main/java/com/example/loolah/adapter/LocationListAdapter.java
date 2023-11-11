package com.example.loolah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.databinding.RowToiletBinding;
import com.example.loolah.model.Toilet;

import java.util.ArrayList;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder> {
    private ArrayList<Toilet> toiletList;
    private LocationListAdapter.OnItemClickListener onItemClickListener;
    public LocationListAdapter.LocationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowToiletBinding binding = RowToiletBinding.inflate(layoutInflater, parent, false);

        return new LocationListAdapter.LocationListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.LocationListViewHolder holder, int position) {
        final Toilet toilet = toiletList.get(position);
        holder.binding.setToilet(toilet);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {return toiletList == null ? 0 : toiletList.size();}
    public void setToiletList(ArrayList<Toilet> toiletList) {
        this.toiletList = toiletList;
        this.notifyDataSetChanged();
    }
    public void setOnItemClickListener(LocationListAdapter.OnItemClickListener listener) {
        onItemClickListener = listener;
    }
    class LocationListViewHolder extends RecyclerView.ViewHolder {
        RowToiletBinding binding;
        public LocationListViewHolder(@NonNull RowToiletBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.lnlToiletRow.setOnClickListener(v -> {
                Toilet toilet = toiletList.get(getAdapterPosition());
                onItemClickListener.onSelectToilet(v, toilet);
            });
        }
    }
    public interface OnItemClickListener {
        void onSelectToilet(View view, Toilet toilet);
    }
}
