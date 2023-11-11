package com.example.loolah.view.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.loolah.R;

public class ImageAdapter extends BaseAdapter {
    private final Context context;
    private final String[] imageUrls;

    public ImageAdapter(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setLayoutParams(new ViewGroup.LayoutParams((parent.getWidth() - 50) / 3, (parent.getWidth() - 50) / 3));
        Glide.with(imageView.getContext())
                .load(imageUrls[position])
                .placeholder(R.drawable.img_placeholder)
                .fallback(R.drawable.img_placeholder)
                .into(imageView);

        return imageView;
    }
}

