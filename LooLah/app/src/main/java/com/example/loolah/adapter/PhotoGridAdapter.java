package com.example.loolah.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.loolah.R;

import java.util.List;

public class PhotoGridAdapter extends BaseAdapter {
    private Context context;
    private Uri[] imageUris;

    public PhotoGridAdapter(Context context, Uri[] imageUris) {
        this.context = context;
        this.imageUris = imageUris;
    }

    @Override
    public int getCount() {
        return imageUris.length;
    }

    @Override
    public Object getItem(int position) {
        return imageUris[position];
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
                .load(imageUris[position])
                .placeholder(R.drawable.img_placeholder)
                .fallback(R.drawable.img_placeholder)
                .into(imageView);

        return imageView;
    }

    public void setImageUris(Uri[] imageUris) {
        this.imageUris = imageUris;
    }
}

