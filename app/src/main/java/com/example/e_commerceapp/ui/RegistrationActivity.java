package com.example.e_commerceapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText name, email, password;
    String userName, userEmail, userPassword;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    boolean isFirstTime;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        //  getSupportActionBar().hide();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

        //check if the user had already signed up or not
        // if true go to MainActivity directly
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }

        openOnBoardingScreenInFirstTime();

    }


    public void signUp(View view) {


        registerWithEmailAndPassword();


    }

    public void signIn(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

    }

    public void openOnBoardingScreenInFirstTime() {
        isFirstTime = sharedPreferences.getBoolean("firstTime", true);
        //note on boarding screen is opening if the user open app in  first time only
        //check if the user open app in first time , if true  make first time false and go to OnBoardingActivity
        if (isFirstTime) {
            editor = sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
            startActivity(new Intent(RegistrationActivity.this, OnBoardingActivity.class));
            finish();
        }

    }

    public void register() {
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Successfully Register", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

                        } else {
                            Toast.makeText(RegistrationActivity.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void registerWithEmailAndPassword() {
        userName = name.getText().toString();
        userEmail = email.getText().toString();
        userPassword = password.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Enter Name !", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Enter Email Address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Enter Password !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password too short,enter minimum 6 characters !", Toast.LENGTH_SHORT).show();
            return;
        }
        register();
    }
}