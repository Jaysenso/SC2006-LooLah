package com.example.loolah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Toilet_RecyclerViewAdapter extends RecyclerView.Adapter<Toilet_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<ToiletData> toiletDataList;


    public Toilet_RecyclerViewAdapter(Context context, ArrayList<ToiletData> toiletDataList) {
        this.context = context;
        this.toiletDataList = toiletDataList;
    }

    @NonNull
    @Override
    public Toilet_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this is where you inflate the layout (giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_recyclerviewsearch, parent, false);

        return new Toilet_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Toilet_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.name.setText(toiletDataList.get(position).getName());
        holder.address.setText(toiletDataList.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return toiletDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.Name);
            address = itemView.findViewById(R.id.Address);
        }
    }
}
