package com.example.loolah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.databinding.RowToiletBinding;
import com.example.loolah.model.Toilet;

import java.util.ArrayList;

public class HomeToiletListAdapter extends RecyclerView.Adapter<HomeToiletListAdapter.HomeToiletViewHolder> {
    private ArrayList<Toilet> homeToiletList;
    private HomeToiletListAdapter.OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public HomeToiletListAdapter.HomeToiletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowToiletBinding binding = RowToiletBinding.inflate(layoutInflater, parent, false);

        return new HomeToiletListAdapter.HomeToiletViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeToiletListAdapter.HomeToiletViewHolder holder, int position) {
        final Toilet toilet = homeToiletList.get(position);
        holder.binding.setToilet(toilet);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return homeToiletList == null ? 0 : homeToiletList.size();
    }

    public void setHomeToiletList(ArrayList<Toilet> homeToiletList) {
        this.homeToiletList = homeToiletList;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(HomeToiletListAdapter.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    class HomeToiletViewHolder extends RecyclerView.ViewHolder {
        RowToiletBinding binding;

        public HomeToiletViewHolder(@NonNull RowToiletBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.lnlToiletRow.setOnClickListener(v -> {
                Toilet toilet = homeToiletList.get(getAdapterPosition());
                onItemClickListener.onSelectToilet(v, toilet);
            });
        }
    }

    public interface OnItemClickListener {
        void onSelectToilet(View view, Toilet toilet);
    }
}
