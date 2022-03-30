package com.example.e_commerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    Toolbar toolbar;
    TextView subTotal, tvDiscount, tvTotal;
    int totalPrice = 0;//subtotal
    int totalAmount = 0;//subtotal
    int discount=0;
    int total=0;
    Button paymentBtn;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        sharedPreferences = getSharedPreferences("product_details",MODE_PRIVATE);

        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //go preivous activity when user press on top left arrow back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

      //  amount = getIntent().getDoubleExtra("amount", 0.0);
       // totalPrice = getIntent().getIntExtra("totalPrice",0);
       // totalAmount =  getIntent().getIntExtra("totalAmount",0);
        totalPrice = sharedPreferences.getInt("total_price",0);
        totalAmount = sharedPreferences.getInt("total_amount",0);



        subTotal = findViewById(R.id.sub_total);
        tvDiscount = findViewById(R.id.discount);
        tvTotal = findViewById(R.id.total);
        paymentBtn = findViewById(R.id.pay_btn);


       // subTotal.setText(amount + " $");
        if(totalPrice!=0){
            subTotal.setText(totalPrice + " $");
            total=discount+totalPrice;
            tvTotal.setText(total+" $");
        }
        if(totalAmount!=0){
            subTotal.setText(totalAmount + " $");
            total=discount+totalAmount;
            tvTotal.setText(total+" $");
        }

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentMethod();
            }
        });

    }
        private void paymentMethod () {

            Checkout checkout = new Checkout();

            final Activity activity = PaymentActivity.this;

            try {
                JSONObject options = new JSONObject();
                //Set Company Name
                options.put("name", "My E-Commerce App");
                //Ref no
                options.put("description", "Reference No. #123456");
                //Image to be display
                options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                //options.put("order_id", "order_9A33XWu170gUtm");
                // Currency type
                options.put("currency", "USD");
                //double total = Double.parseDouble(mAmountText.getText().toString());
                //multiply with 100 to get exact amount in rupee
              //  amount = amount * 100;
                //amount
                if(totalPrice!=0){
                    options.put("amount", totalPrice*100);
                }
                if (totalAmount!=0){
                    options.put("amount", totalAmount*100);
                }

                JSONObject preFill = new JSONObject();
                //email
                preFill.put("email", "mohamedelzmar2018@gmail.com");
                //contact
                preFill.put("contact", "1285089330");

                options.put("prefill", preFill);

                checkout.open(activity, options);
            } catch (Exception e) {
                Log.e("TAG", "Error in starting Razorpay Checkout", e);
            }
        }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Cancel", Toast.LENGTH_SHORT).show();

    }
}




