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
import com.example.e_commerceapp.R;
import com.example.e_commerceapp.ShowAllActivity;
import com.example.e_commerceapp.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {


   private Context context;
   private List<CategoryModel> list;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<CategoryModel> list) {
        this.list = list;
        //  notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.catImageView);
        holder.catTextView.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowAllActivity.class);
                intent.putExtra("type",list.get(position).getType());
                        context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView catImageView;
        TextView catTextView;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            catImageView = itemView.findViewById(R.id.cat_img);
            catTextView = itemView.findViewById(R.id.cat_name);

        }
    }
}
