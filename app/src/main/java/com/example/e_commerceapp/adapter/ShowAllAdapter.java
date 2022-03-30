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
import com.example.e_commerceapp.models.ShowAllModel;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ShowAllViewHolder> {
    Context context;
    List<ShowAllModel>list;

    public ShowAllAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ShowAllModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ShowAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowAllViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.mItemImage);
        holder.mCost.setText("Price :"+list.get(position).getPrice()+"$");
        holder.mName.setText(list.get(position).getName());

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

    public class ShowAllViewHolder extends RecyclerView.ViewHolder {
         ImageView mItemImage;
         TextView mCost,mName;

        public ShowAllViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemImage = itemView.findViewById(R.id.all_item_image);
            mCost = itemView.findViewById(R.id.all_item_cost);
            mName = itemView.findViewById(R.id.all_item_name);

        }
    }
}
