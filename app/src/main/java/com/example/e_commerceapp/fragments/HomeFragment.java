package com.example.e_commerceapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.e_commerceapp.R;
import com.example.e_commerceapp.ShowAllActivity;
import com.example.e_commerceapp.adapter.CategoryAdapter;
import com.example.e_commerceapp.adapter.NewProductsAdapter;
import com.example.e_commerceapp.adapter.PopularProductsAdapter;
import com.example.e_commerceapp.models.CategoryModel;
import com.example.e_commerceapp.models.NewProductsModel;
import com.example.e_commerceapp.models.PopularProductsModel;
import com.example.e_commerceapp.viewmodel.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{
    //new
    private static final String TAG = "HomeFragment";
    TextView catShowAll,popularShowAll,newProductShowAll;

    ProgressDialog progressDialog;
    LinearLayout linearLayout;
    RecyclerView categoryRecyclerView, newProductRecyclerView, popularProductRecyclerView;
    //category recycler view
    CategoryAdapter categoryAdapter;

    // new product  recycler view
    NewProductsAdapter newProductsAdapter;

    // popular product(all product)  recycler view
    PopularProductsAdapter popularProductsAdapter;

    //home view model
    HomeViewModel homeViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog = new ProgressDialog(getActivity());

        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        categoryRecyclerView = root.findViewById(R.id.rec_category);
        newProductRecyclerView = root.findViewById(R.id.new_product_rec);
        popularProductRecyclerView = root.findViewById(R.id.popular_rec);

        //home view model
        homeViewModel = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);

        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);



        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                   startActivity(intent);
            }
        });


       // hide home fragment layout
        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);
        
        //image slider
        displayImageSlider(imageSlider);

        //progress dialog 
        displayProgressDialog();




        //read data from FireStore and passing it to list of category

        readCategoryDataFromHomeViewModel();
        //Category
        displayCategoryInRecyclerView();

        // new Product
        displayProductInRecyclerView();


        //read data from FireStore and passing it to list of newProducts
        readNewProductDataFromFirestore();

        // popular Product (AllProducts)
        displayPopularProductInRecyclerView();


        //read data from FireStore and passing it to list of newProducts
        readPopularProductDataFromFirestore();


        return root;
}

    public void displayImageSlider(ImageSlider imageSlider) {
        //SlideModel is predefined in image slider library
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1,"Discount On Shoes Item", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"Discount On Perfume",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3,"70 % OFF",ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);
    }

    public void displayProgressDialog() {
        progressDialog.setTitle("Welcome To My E-commerce App");
        progressDialog.setMessage("please wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void readPopularProductDataFromFirestore() {
     homeViewModel.getPopularProducts();
     homeViewModel.getPopularProductsMutableLiveData().observe(getActivity(), new Observer<List<PopularProductsModel>>() {
         @Override
         public void onChanged(List<PopularProductsModel> popularProductsModels) {
             popularProductsAdapter.setList(popularProductsModels);
             popularProductsAdapter.notifyDataSetChanged();
         }
     });
    }

    public void displayPopularProductInRecyclerView() {
        popularProductRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularProductsAdapter = new PopularProductsAdapter(getActivity());
        popularProductRecyclerView.setAdapter(popularProductsAdapter);
    }

    public void readNewProductDataFromFirestore() {
   homeViewModel.getNewProducts();
   homeViewModel.getNewProductsMutableLiveData().observe(getActivity(), new Observer<List<NewProductsModel>>() {
       @Override
       public void onChanged(List<NewProductsModel> newProductsModels) {
           newProductsAdapter.setList(newProductsModels);
           newProductsAdapter.notifyDataSetChanged();
       //    Log.d(TAG, "onChanged Product : ");
       }
   });

    }

    public void displayProductInRecyclerView() {
        newProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
      //  newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getActivity());
        newProductRecyclerView.setAdapter(newProductsAdapter);
    }

    public void readCategoryDataFromHomeViewModel() {
        homeViewModel.getCategory();
        homeViewModel.getCategoryMutableLiveData().observe(getActivity(), new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categoryModels) {
                Log.d(TAG, "home: "+categoryModels.get(0).getName());

                categoryAdapter.setList(categoryModels);
                categoryAdapter.notifyDataSetChanged();
                //after getting data and adding to recycler view visible layout of home fragment
                linearLayout.setVisibility(View.VISIBLE);

                //after getting data and adding to recycler view hide progress
                progressDialog.dismiss();
            }
        });

    }

    public void displayCategoryInRecyclerView() {
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
//        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity());
        categoryRecyclerView.setAdapter(categoryAdapter);
    }
}