package com.example.loolah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Toilet_RVAdapter extends RecyclerView.Adapter<Toilet_RVAdapter.MyViewHolder>{
    Context context;
    ArrayList<ToiletModel> displayList;

    public Toilet_RVAdapter(Context context, ArrayList<ToiletModel> toiletDataList)  {
        this.context = context;
        this.displayList = toiletDataList;
    }
    @NonNull
    @Override
    public Toilet_RVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //this is where you inflate the layout (giving a look to our rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemframe_recyclerview, parent, false);

        return new Toilet_RVAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Toilet_RVAdapter.MyViewHolder holder, int position) {

        //holder.imageView.setImageResource(R.drawable.ic_map_searchpin);
        holder.name.setText(displayList.get(position).getName());
        holder.address.setText(displayList.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    public void filterList(ArrayList<ToiletModel> filteredList) {
        displayList = filteredList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mapSearchPin);
            name = itemView.findViewById(R.id.toiletName);
            address = itemView.findViewById(R.id.toiletAddress);
        }
    }

}