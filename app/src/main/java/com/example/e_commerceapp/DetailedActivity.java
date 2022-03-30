package com.example.e_commerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_commerceapp.models.NewProductsModel;
import com.example.e_commerceapp.models.PopularProductsModel;
import com.example.e_commerceapp.models.ShowAllModel;
import com.example.e_commerceapp.repo.Repository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg,addItems,removeItems;
    TextView rating,name,description,price,quantity;
    Button addToCard,buyNow;
     Object obj;

    //New Products
    NewProductsModel newProductsModel = null;
    //popular products
    PopularProductsModel popularProductsModel = null;
    //Show all products
    ShowAllModel showAllModel = null;

    Toolbar toolbar;
    private FirebaseFirestore firestore;
    FirebaseAuth auth;
    int totalQuantity=1;
    int totalPrice = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

       // Toast.makeText(this, "on Create", Toast.LENGTH_SHORT).show();
        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //go preivous activity when user press on top left arrow back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("product_details",MODE_PRIVATE);
        editor = sharedPreferences.edit();

         obj = getIntent().getSerializableExtra("detailed");
        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }
        else if(obj instanceof  PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        }
        else if(obj instanceof  ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }


        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addToCard = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);
        quantity = findViewById(R.id.quantity);

        //New Products
        if(newProductsModel !=null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(newProductsModel.getPrice()+"$");
            //assign price to total price
            totalPrice = newProductsModel.getPrice()*totalQuantity;


        }

        //Popular Products
        if(popularProductsModel !=null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(popularProductsModel.getPrice()+"$");
            //assign price to total price total price
            totalPrice = popularProductsModel.getPrice()*totalQuantity;

        }

        //Show All Products
        if(showAllModel !=null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(showAllModel.getPrice()+"$");

            //assign price to total price
           totalPrice = showAllModel.getPrice()*totalQuantity;


        }

        //add to card
        addToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCard();
            }
        });

        //buy now
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //note
                Intent intent = new Intent(DetailedActivity.this,AddressActivity.class);

//                if (newProductsModel!=null){
//                    intent.putExtra("item",newProductsModel);
//
//                }
//
//                if (popularProductsModel!=null){
//                    intent.putExtra("item",popularProductsModel);
//
//                }
//
//                if (showAllModel!=null){
//                    intent.putExtra("item",showAllModel);
//                }
               // Toast.makeText(DetailedActivity.this, ""+totalPrice, Toast.LENGTH_SHORT).show();
                // i send total price for payment

           //    intent.putExtra("total_price",totalPrice);
               // editor.putInt("total_price",totalPrice);

                //because the total price and total amount will use the same activity called payment activity
                //to solve this problem , in detailed activity i put total_amount=0 and put the real value of total price
                //i can't use intent because the detailed activity will destroy in add address activity because of the user will go to add address activity and back to address activity
                // this is problem leads to lose real value of total price
                editor.putInt("total_amount",0);
                editor.commit();
                editor.putInt("total_price",totalPrice);
                editor.commit();
                startActivity(intent);
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if totalQuantity<10 increment totalQuantity otherwise not increment
                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    //calculate total price
                    if(newProductsModel !=null){
                        totalPrice = newProductsModel.getPrice()*totalQuantity;
                    }
                    if(popularProductsModel !=null){
                        totalPrice = popularProductsModel.getPrice()*totalQuantity;
                    }
                    if(showAllModel !=null){
                        totalPrice = showAllModel.getPrice()*totalQuantity;
                    }
                }
            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if totalQuantity>1 decrement totalQuantity otherwise not decrement
                if(totalQuantity>1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    //calculate total price
                    if(newProductsModel !=null){
                        totalPrice = newProductsModel.getPrice()*totalQuantity;
                    }
                    if(popularProductsModel !=null){
                        totalPrice = popularProductsModel.getPrice()*totalQuantity;
                    }
                    if(showAllModel !=null){
                        totalPrice = showAllModel.getPrice()*totalQuantity;
                    }
                }
            }
        });

    }

    private void addToCard() {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

       // Toast.makeText(this, saveCurrentDate, Toast.LENGTH_SHORT).show();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
       // Toast.makeText(this, saveCurrentTime, Toast.LENGTH_SHORT).show();



        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        //store Addtocard to firestore
        //add Addtocard by coding not manually as a collection and add documents inside this collection
        firestore.collection("AddToCard").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added To A cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(this, "on start", Toast.LENGTH_SHORT).show();
//
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(this, "on pause", Toast.LENGTH_SHORT).show();
//
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(this, "on stop", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(this, "on destroy", Toast.LENGTH_SHORT).show();
//
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//       Toast.makeText(this, "onResume ", Toast.LENGTH_SHORT).show();
//
//    }
}