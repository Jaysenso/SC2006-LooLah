package com.example.loolah.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loolah.databinding.RowToiletReviewBinding;
import com.example.loolah.model.ReviewDetails;
import com.example.loolah.model.ToiletDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ToiletReviewListAdapter extends RecyclerView.Adapter<ToiletReviewListAdapter.ToiletReviewViewHolder> {
    private ArrayList<ReviewDetails> toiletReviewList;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public ToiletReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RowToiletReviewBinding binding = RowToiletReviewBinding.inflate(layoutInflater, parent, false);

        return new ToiletReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ToiletReviewViewHolder holder, int position) {
        final ReviewDetails review = toiletReviewList.get(position);
        holder.binding.setReview(review);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return toiletReviewList == null ? 0 : toiletReviewList.size();
    }

    public void setToiletReviewList(ArrayList<ReviewDetails> toiletReviewList) {
        this.toiletReviewList = toiletReviewList;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    class ToiletReviewViewHolder extends RecyclerView.ViewHolder {
        RowToiletReviewBinding binding;

        public ToiletReviewViewHolder(@NonNull RowToiletReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.ibToiletReviewLike.setOnClickListener(v -> {
                ReviewDetails review = toiletReviewList.get(getAdapterPosition());
                onItemClickListener.onSelectReviewLike(review);
                review.setLiked(!review.isLiked());
                notifyDataSetChanged();
            });
        }
    }

    public interface OnItemClickListener {
        void onSelectReviewLike(ReviewDetails review);
    }
}
