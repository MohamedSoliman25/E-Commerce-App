package com.example.e_commerceapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerceapp.models.ShowAllModel;
import com.example.e_commerceapp.repo.Repository;

import java.util.List;

public class SeeAllViewModel extends ViewModel {

    MutableLiveData<List<ShowAllModel>> showAllMutableLiveData ;
    MutableLiveData<List<ShowAllModel>> queryShowAllMutableLiveData ;

    public void getShowAll(){
        showAllMutableLiveData = Repository.getInstance().getShowAll();
    }
    public void getQueryShowAll(String type){
        queryShowAllMutableLiveData = Repository.getInstance().getQueryShowAll(type);
    }

    public MutableLiveData<List<ShowAllModel>> getShowAllMutableLiveData() {
        return showAllMutableLiveData;
    }

    public MutableLiveData<List<ShowAllModel>> getQueryShowAllMutableLiveData() {
        return queryShowAllMutableLiveData;
    }
}
