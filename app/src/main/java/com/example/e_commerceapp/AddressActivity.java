package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_commerceapp.adapter.AddressAdapter;
import com.example.e_commerceapp.models.AddressModel;
import com.example.e_commerceapp.viewmodel.AddressViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress{

    Button addAddress,paymentBtn;
    RecyclerView recyclerView;
  //  private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Toolbar toolbar;
    String finalAdderss = null;
    AddressViewModel addressViewModel;
//    int totalPrice = 0;
//    int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);
//        totalPrice = getIntent().getIntExtra("total_price",0);
//        Toast.makeText(this, ""+totalPrice, Toast.LENGTH_SHORT).show();
//        totalAmount = getIntent().getIntExtra("total_amount",0);

      //  Toast.makeText(AddressActivity.this, totalPrice+"", Toast.LENGTH_SHORT).show();



        //go preivous activity when user press on top left arrow back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //get Data from detailed activity
//        Object obj = getIntent().getSerializableExtra("item");

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        addAddress = findViewById(R.id.add_address_btn);
        recyclerView = findViewById(R.id.address_recycler);
        paymentBtn = findViewById(R.id.payment_btn);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // addressModelList = new ArrayList<>();
      //  addressAdapter = new AddressAdapter(getApplicationContext(),this);
//        recyclerView.setAdapter(addressAdapter);

        //getting address from firestore and passing it to list
//        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
//                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()){
//                    for (DocumentSnapshot doc : task.getResult().getDocuments()){
//                        AddressModel addressModel = doc.toObject(AddressModel.class);
//                        addressModelList.add(addressModel);
//                        addressAdapter.notifyDataSetChanged();
//
//                    }
//                }
//            }
//        });
        getAddressFromViewModel();


        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this,AddAddressActivity.class));
                finish();
            }
        });
//note
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                double amount = 0.0;
//              if(obj instanceof NewProductsModel){
//                  NewProductsModel   newProductsModel = (NewProductsModel)obj;
//                  amount = newProductsModel.getPrice();
//              }
//                if(obj instanceof PopularProductsModel){
//                    PopularProductsModel  popularProductsModel = (PopularProductsModel) obj;
//                    amount = popularProductsModel.getPrice();
//                }
//                if(obj instanceof ShowAllModel){
//                    ShowAllModel   showAllModel = (ShowAllModel) obj;
//                    amount = showAllModel.getPrice();
//                }
                if(finalAdderss!=null){
                    Toast.makeText(AddressActivity.this, finalAdderss, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
//                    intent.putExtra("totalPrice",totalPrice);
//                    intent.putExtra("totalAmount",totalAmount);
                    // intent.putExtra("amount",amount);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddressActivity.this, "Please Select Your Final Address!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void setAddress(String address) {
        //getting selected address
      finalAdderss = address;

    }

    public void getAddressFromViewModel(){
        addressViewModel.getAddress();
        addressAdapter = new AddressAdapter(getApplicationContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addressAdapter);
        addressViewModel.getAddressMutableLiveData().observe(this, new Observer<List<AddressModel>>() {
            @Override
            public void onChanged(List<AddressModel> addressModels) {
                addressAdapter.setList(addressModels);
                //addressAdapter.notifyDataSetChanged();
            }
        });
    }


}