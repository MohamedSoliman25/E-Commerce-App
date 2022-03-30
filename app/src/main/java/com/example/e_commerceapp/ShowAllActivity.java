package com.example.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.e_commerceapp.adapter.ShowAllAdapter;
import com.example.e_commerceapp.models.CategoryModel;
import com.example.e_commerceapp.models.ShowAllModel;
import com.example.e_commerceapp.viewmodel.HomeViewModel;
import com.example.e_commerceapp.viewmodel.SeeAllViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowAllActivity extends AppCompatActivity {


    private static final String TAG = "ShowAllActivity";
    Toolbar toolbar;
    RecyclerView recyclerView;
    ShowAllAdapter showAllAdapter;
   // List<ShowAllModel> showAllModelList;
    SeeAllViewModel seeAllViewModel;
    FirebaseFirestore firestore;
    String type = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        toolbar = findViewById(R.id.show_all_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //go preivous activity when user press on top left arrow back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        seeAllViewModel = ViewModelProviders.of(this).get(SeeAllViewModel.class);


        //getting field type from category activity
        type = getIntent().getStringExtra("type");

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.show_all_rec);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//      //  showAllModelList = new ArrayList<>();
//        showAllAdapter = new ShowAllAdapter(this);
//        recyclerView.setAdapter(showAllAdapter);

        if(type == null || type.isEmpty()){
            //if the user doesn't select any type of category and press on see All button  of category or new products or popular products display show all product
            readShowAllDataFromFirestore();
        }

//         equalsIgnoreCase() is the same equal function but it used for comparing between 2 strings with ignore case sensitive
        if (type!=null && type.equalsIgnoreCase("men")){
            readQueryShowAllByTypeFormFirestore("men");
        }

        if (type!=null && type.equalsIgnoreCase("woman")){

            readQueryShowAllByTypeFormFirestore("woman");

        }

        if (type!=null && type.equalsIgnoreCase("watch")){
          readQueryShowAllByTypeFormFirestore("watch");
        }

        if (type!=null && type.equalsIgnoreCase("camera")){
          readQueryShowAllByTypeFormFirestore("camera");
        }

        if (type!=null && type.equalsIgnoreCase("kids")){
        readQueryShowAllByTypeFormFirestore("kids");
        }

        if (type!=null && type.equalsIgnoreCase("shoes")){
          readQueryShowAllByTypeFormFirestore("shoes");
        }

    }

    private void readQueryShowAllByTypeFormFirestore(String type) {

  seeAllViewModel.getQueryShowAll(type);
  seeAllViewModel.getQueryShowAllMutableLiveData().observe(this, new Observer<List<ShowAllModel>>() {
      @Override
      public void onChanged(List<ShowAllModel> showAllModels) {

          //  showAllModelList = new ArrayList<>();
          showAllAdapter = new ShowAllAdapter(ShowAllActivity.this);
          showAllAdapter.setList(showAllModels);
          showAllAdapter.notifyDataSetChanged();
          recyclerView.setAdapter(showAllAdapter);
          recyclerView.setLayoutManager(new GridLayoutManager(ShowAllActivity.this,2));

      }
  });
    }

    private void readShowAllDataFromFirestore() {
        seeAllViewModel.getShowAll();
        seeAllViewModel.getShowAllMutableLiveData().observe(this, new Observer<List<ShowAllModel>>() {
            @Override
            public void onChanged(List<ShowAllModel> showAllModels) {
//                showAllAdapter.setList(showAllModels);
//                showAllAdapter.notifyDataSetChanged();
                showAllAdapter = new ShowAllAdapter(ShowAllActivity.this);
                showAllAdapter.setList(showAllModels);
                showAllAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(showAllAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(ShowAllActivity.this,2));
            }
        });
    }
}
