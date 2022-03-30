package com.example.e_commerceapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_commerceapp.models.CategoryModel;
import com.example.e_commerceapp.models.NewProductsModel;
import com.example.e_commerceapp.models.PopularProductsModel;
import com.example.e_commerceapp.repo.Repository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<CategoryModel>> categoryMutableLiveData;
    private MutableLiveData<List<NewProductsModel>> newProductsMutableLiveData;
    private MutableLiveData<List<PopularProductsModel>> popularProductsMutableLiveData;



    public void getCategory(){
//        if (categoryMutableLiveData!=null){
//            return;
//        }
        categoryMutableLiveData = Repository.getInstance().getCategory();
    }

    public void getNewProducts(){

        newProductsMutableLiveData = Repository.getInstance().getNewProducts();
    }

    public void getPopularProducts(){

        popularProductsMutableLiveData = Repository.getInstance().getPopularProducts();
    }

    public MutableLiveData<List<CategoryModel>> getCategoryMutableLiveData() {
        return categoryMutableLiveData;
    }

    public MutableLiveData<List<NewProductsModel>> getNewProductsMutableLiveData() {
        return newProductsMutableLiveData;
    }

    public MutableLiveData<List<PopularProductsModel>> getPopularProductsMutableLiveData() {
        return popularProductsMutableLiveData;
    }
}
