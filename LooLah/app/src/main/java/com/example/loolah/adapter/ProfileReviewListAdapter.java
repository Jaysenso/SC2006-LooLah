package com.example.loolah.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.example.loolah.databinding.RowProfileReviewBinding;
import com.example.loolah.model.ReviewDetails;

import java.util.ArrayList;

public class ProfileReviewListAdapter extends RecyclerView.Adapter<ProfileReviewListAdapter.ProfileReviewViewHolder> {
    private ArrayList<ReviewDetails> profileReviewList;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public ProfileReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowProfileReviewBinding binding = RowProfileReviewBinding.inflate(layoutInflater, parent, false);

        return new ProfileReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileReviewViewHolder holder, int position) {
        final ReviewDetails review = profileReviewList.get(position);
        holder.binding.setReview(review);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return profileReviewList == null ? 0 : profileReviewList.size();
    }

    public void setProfileReviewList(ArrayList<ReviewDetails> profileReviewList) {
        this.profileReviewList = profileReviewList;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    class ProfileReviewViewHolder extends RecyclerView.ViewHolder {
        RowProfileReviewBinding binding;

        public ProfileReviewViewHolder(@NonNull RowProfileReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.lnlProfileReviewRow.setOnClickListener(v -> {
                ReviewDetails review = profileReviewList.get(getAdapterPosition());
                onItemClickListener.onSelectReview(v, review);
            });
        }
    }

    public interface OnItemClickListener {
        void onSelectReview(View view, ReviewDetails review);
    }
}
