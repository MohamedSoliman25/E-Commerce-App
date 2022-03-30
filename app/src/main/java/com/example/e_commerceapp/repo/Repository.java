package com.example.e_commerceapp.repo;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.e_commerceapp.ShowAllActivity;
import com.example.e_commerceapp.models.AddressModel;
import com.example.e_commerceapp.models.CategoryModel;
import com.example.e_commerceapp.models.MyCartModel;
import com.example.e_commerceapp.models.NewProductsModel;
import com.example.e_commerceapp.models.PopularProductsModel;
import com.example.e_commerceapp.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private static final String TAG = "Repository";
    private static Repository instance;
    // FireStore
    FirebaseFirestore db;
    //auth
    private FirebaseAuth auth;
    MutableLiveData<Integer>totalAmountMutableLiveData = new MutableLiveData<>();

    public  int totalAmount=0;

//singleton
    public static Repository getInstance(){
        if (instance==null){
            instance = new Repository();
        }

        return instance;
    }
// category
    public MutableLiveData<List<CategoryModel>>getCategory(){
        final MutableLiveData<List<CategoryModel>> categoryMutableLiveData = new MutableLiveData<>();
        List<CategoryModel> categoryList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryList.add(categoryModel);
                                categoryMutableLiveData.setValue(categoryList);
                                Log.d(TAG, "repo: "+categoryList.get(0).getName());

//                                //after getting data and adding to recycler view visible layout of home fragment
//                                linearLayout.setVisibility(View.VISIBLE);
//
//                                //after getting data and adding to recycler view hide progress
//                                progressDialog.dismiss();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return categoryMutableLiveData;
    }

    //new products
    public MutableLiveData<List<NewProductsModel>> getNewProducts(){
        MutableLiveData<List<NewProductsModel>> newProductsMutableLiveData = new MutableLiveData<>();
        List<NewProductsModel> newProductsModelList = new ArrayList<>();
        db.collection("New Products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProductsMutableLiveData.setValue(newProductsModelList);
                            }
                        } else {
                             Log.d(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return newProductsMutableLiveData;
    }

    //popular Products
    public MutableLiveData<List<PopularProductsModel>> getPopularProducts(){
        MutableLiveData<List<PopularProductsModel>> popularProductsMutableLiveData = new MutableLiveData<>();
        List<PopularProductsModel> popularProductsModelList = new ArrayList<>();
        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                                popularProductsModelList.add(popularProductsModel);
                             popularProductsMutableLiveData.setValue(popularProductsModelList);
                            }
                        } else {
                            // Log.d(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        return popularProductsMutableLiveData;
    }

    //show all
    public MutableLiveData<List<ShowAllModel>> getShowAll(){
        MutableLiveData<List<ShowAllModel>> showAllMutableLiveData = new MutableLiveData<>();
        List<ShowAllModel> showAllModelList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("ShowAll")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                       ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                                       showAllModelList.add(showAllModel);
                                                       showAllMutableLiveData.setValue(showAllModelList);

                                                   }
                                               } else {
                            Log.d(TAG, "Error getting documents.", task.getException());
                                                 //  Toast.makeText(ShowAllActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       }
                );

        return showAllMutableLiveData;
    }
    //query show all
    public MutableLiveData<List<ShowAllModel>> getQueryShowAll(String type){
        MutableLiveData<List<ShowAllModel>> queryShowAllMutableLiveData = new MutableLiveData<>();
        List<ShowAllModel> queryShowAllModelList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        //whereEqualTo("type","men") it is consider as query
        db.collection("ShowAll").whereEqualTo("type",type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               if (task.isSuccessful()){
                                                   for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                                       ShowAllModel showAllModel = document.toObject(ShowAllModel.class);
                                                      queryShowAllModelList.add(showAllModel);
                                                      queryShowAllMutableLiveData.setValue(queryShowAllModelList);

                                                   }
                                               } else {
                            Log.d(TAG, "Error getting documents.", task.getException());
                                                 //  Toast.makeText(ShowAllActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       }
                );
        return queryShowAllMutableLiveData;
    }
    //My Cart
    public MutableLiveData<List<MyCartModel>> getMyCart(){
        MutableLiveData<List<MyCartModel>> myCartMutableLiveData = new MutableLiveData<>();
        List<MyCartModel> myCartList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        db.collection("AddToCard").document(auth.getCurrentUser().getUid())

                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    //      for (DocumentSnapshot doc :task.getResult().getDocuments()){
                    for (QueryDocumentSnapshot doc : task.getResult()){

                        // i will use this id for delete item
                        String documentId = doc.getId();

                        MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                        myCartModel.setDocumentId(documentId);
                   //     Log.d(TAG, "onComplete name: "+doc.getId()+" =>"+doc.getData());

                        //calculate total amount across total price
                        totalAmount = totalAmount+myCartModel.getTotalPrice();
                   //     Log.d(TAG, "amount: "+totalAmount);
                        myCartList.add(myCartModel);
                        myCartMutableLiveData.setValue(myCartList);
                    }

//                    myCartModel.setTotalAmount(amount);
           //       Log.d(TAG, "onComplete total Amount: "+amount);

                    //for setting value for totalAmountMutableLiveData for getTotalAmount function
                    totalAmountMutableLiveData.setValue(totalAmount);
                }
                else{
                    Log.d(TAG, "Error: "+task.getException().getMessage());
                }
            }
        });
           return myCartMutableLiveData;
    }
    //getTotalAmount
    public MutableLiveData<Integer>getTotalAmount(){
        // i setting value for totalAmountMutableLiveData in function getMyCart
        return totalAmountMutableLiveData;

    }
    public MutableLiveData<List<AddressModel>>getAddress(){
        MutableLiveData<List<AddressModel>> addressMutableLiveData = new MutableLiveData<>();
        List<AddressModel>listAddress = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //getting address from firestore and passing it to list
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc : task.getResult().getDocuments()){
                        AddressModel addressModel = doc.toObject(AddressModel.class);
                        listAddress.add(addressModel);
                        addressMutableLiveData.setValue(listAddress);
                    }
                }
            }
        });
return addressMutableLiveData;
    }
    }

