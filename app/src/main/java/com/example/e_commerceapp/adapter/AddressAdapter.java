package com.example.e_commerceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceapp.R;
import com.example.e_commerceapp.models.AddressModel;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    Context context;
    List<AddressModel> list =new ArrayList<>();
    SelectedAddress selectedAddress;

    public void setList(List<AddressModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private RadioButton selectedRadioBtn;

    public AddressAdapter(Context context, SelectedAddress selectedAddress) {
        this.context = context;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {

        holder.address.setText(list.get(position).getUserAddress());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AddressModel address :list){
                    address.setSelected(false);
                }
                list.get(position).setSelected(true);
                if(selectedRadioBtn!=null){
                    selectedRadioBtn.setChecked(false);
                }
                selectedRadioBtn = (RadioButton) v;
                selectedRadioBtn.setChecked(true);
                selectedAddress.setAddress(list.get(position).getUserAddress());

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        RadioButton radioButton;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);

        }
    }
    public interface SelectedAddress{
        void setAddress(String address);
    }
}
