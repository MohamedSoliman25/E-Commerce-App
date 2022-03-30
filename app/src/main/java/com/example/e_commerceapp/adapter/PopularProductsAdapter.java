package com.example.e_commerceapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_commerceapp.DetailedActivity;
import com.example.e_commerceapp.R;

import com.example.e_commerceapp.models.PopularProductsModel;

import java.util.List;

import javax.xml.validation.ValidatorHandler;

public class PopularProductsAdapter extends RecyclerView.Adapter<PopularProductsAdapter.PopularProductsViewHolder> {

    Context context;
    List<PopularProductsModel> list;

    public PopularProductsAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<PopularProductsModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PopularProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductsViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("Price: "+list.get(position).getPrice()+"$");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                //passing data (full object)from activity to another activity by Serializable
                intent.putExtra("detailed",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PopularProductsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price;

        public PopularProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.all_img);
            price = itemView.findViewById(R.id.all_price);
            name = itemView.findViewById(R.id.all_product_name);

        }

    }
}
