package com.example.e_commerceapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerceapp.models.MyCartModel;
import com.example.e_commerceapp.repo.Repository;

import java.util.List;

public class MyCartViewModel extends ViewModel {

    MutableLiveData<List<MyCartModel>> myCartMutableLiveData;
    MutableLiveData<Integer> totalAmountMutableLiveData;


    public void getMyCart(){
        myCartMutableLiveData = Repository.getInstance().getMyCart();
    }

    public void getTotalAmount(){
        totalAmountMutableLiveData = Repository.getInstance().getTotalAmount();
    }
    public LiveData<List<MyCartModel>> getMyCartMutableLiveData() {
        return myCartMutableLiveData;
    }


    public MutableLiveData<Integer> getTotalAmountMutableLiveData() {
        return totalAmountMutableLiveData;
    }
}
