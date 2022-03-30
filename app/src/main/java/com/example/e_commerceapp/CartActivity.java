package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerceapp.adapter.MyCartAdapter;
import com.example.e_commerceapp.models.MyCartModel;
import com.example.e_commerceapp.repo.Repository;
import com.example.e_commerceapp.viewmodel.MyCartViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartActivity extends AppCompatActivity implements MyCartAdapter.Refresh {

    private static final String TAG = "CartActivity";
    Toolbar toolbar;
    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    MyCartViewModel myCartViewModel;
    Button buyNow;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
int totalAmount=0;
    TextView overAllAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("product_details",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //recycler view
        cartAdapter = new MyCartAdapter(this,this);

        myCartViewModel = ViewModelProviders.of(this).get(MyCartViewModel.class);
        //go preivous activity when user press on top left arrow back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        //get data from my cart adapter
//        LocalBroadcastManager.getInstance(this)
//                .registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));
        buyNow = findViewById(R.id.buy_now);
        overAllAmount = findViewById(R.id.total_amount);
        recyclerView = findViewById(R.id.cart_rec);
        getMyCartFromViewModel();

     //   displayTotalAmount();
      getTotalAmountFromViewModel();

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, AddressActivity.class);
             //   intent.putExtra("total_amount", totalAmount);

                //because the total price and total amount will use the same activity called payment activity
                //to solve this problem , in detailed activity i put total_amount=0 and put the real value of total price
                //i can't use intent because the cart activity will destroy in add address activity because of the user will go to add address activity and back to address activity
                //this is problem leads to lose real value of total price
                editor.putInt("total_price",0);
                editor.commit();
                editor.putInt("total_amount",totalAmount);
                editor.commit();
               // Toast.makeText(CartActivity.this, "buy now :"+totalAmount, Toast.LENGTH_SHORT).show();
                startActivity(intent);


            }
        });
    }


    public void displayTotalAmount(int totalAmount) {
        overAllAmount.setText("Total Amount : " + totalAmount + "$");
    }

    //    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int totalBill = intent.getIntExtra("totalAmount",0);
//           overAllAmount.setText("Total Amount : "+totalAmount+"$");
//        }
//    };
    public void getMyCartFromViewModel() {
        myCartViewModel.getMyCart();
        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        recyclerView.setAdapter(cartAdapter);

        myCartViewModel.getMyCartMutableLiveData().observe(this, new Observer<List<MyCartModel>>() {
            @Override
            public void onChanged(List<MyCartModel> list) {
                cartAdapter.setList(list);
            }
        });


    }
    public void getTotalAmountFromViewModel(){
                myCartViewModel.getTotalAmount();
        myCartViewModel.getTotalAmountMutableLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                totalAmount = integer;
                displayTotalAmount(totalAmount);

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //to reset value of total amount if user leave the activity
        Repository.getInstance().totalAmount=0;
    }


    @Override
    public void refreshActivity() {
        recreate();
    }
}