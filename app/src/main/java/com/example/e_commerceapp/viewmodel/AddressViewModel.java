package com.example.e_commerceapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerceapp.models.AddressModel;
import com.example.e_commerceapp.repo.Repository;

import java.util.List;

public class AddressViewModel extends ViewModel {
    MutableLiveData<List<AddressModel>>addressMutableLiveData;

    public void getAddress(){
        addressMutableLiveData = Repository.getInstance().getAddress();
    }
    public MutableLiveData<List<AddressModel>> getAddressMutableLiveData() {
        return addressMutableLiveData;
    }
}
