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
import com.example.e_commerceapp.models.NewProductsModel;

import java.util.List;

public class NewProductsAdapter extends RecyclerView.Adapter<NewProductsAdapter.NewProductsViewHolder>{
   private Context context;
    private List<NewProductsModel> list;

    public NewProductsAdapter(Context context) {
        this.context = context;

    }

    public void setList(List<NewProductsModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NewProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductsViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.newImage);
        holder.newName.setText(list.get(position).getName());
        holder.newPrice.setText("Price: "+list.get(position).getPrice()+"$");

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

    public class NewProductsViewHolder extends RecyclerView.ViewHolder {
        ImageView newImage;
        TextView newName,newPrice;
        public NewProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            newImage = itemView.findViewById(R.id.new_img);
            newName = itemView.findViewById(R.id.new_product_name);
            newPrice = itemView.findViewById(R.id.new_price);

        }
    }
}
