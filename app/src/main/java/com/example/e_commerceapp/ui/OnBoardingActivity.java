package com.example.e_commerceapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.e_commerceapp.R;
import com.example.e_commerceapp.adapter.OnBoardingSliderAdapter;

public class OnBoardingActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    LinearLayout dotsLayout;
     Button btn;

    OnBoardingSliderAdapter onBoardingSliderAdapter;
    TextView[] dots;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding);
        //hide toolbar
//        getSupportActionBar().hide();

        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        btn = findViewById(R.id.get_started_btn);

        btn.setOnClickListener(this);

        // add dots by default and adding color for  first dot
        addDots(0);


        viewPager.addOnPageChangeListener(pageChangeListener());

        //call Adapter
        onBoardingSliderAdapter = new OnBoardingSliderAdapter(this);
        viewPager.setAdapter(onBoardingSliderAdapter);



    }

    private void addDots(int position) {
        dots = new TextView[3];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));  //display .
            dots[i].setTextSize(35);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.orange_dark));
        }
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(OnBoardingActivity.this,RegistrationActivity.class));
        finish();
    }
    public ViewPager.OnPageChangeListener pageChangeListener(){
        ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);
                if (position==0){
                    btn.setVisibility(View.INVISIBLE);

                }
                else if(position==1){
                    btn.setVisibility(View.INVISIBLE);
                }
                else{
                    animation = AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.slide_animation);
                    btn.setAnimation(animation);
                    btn.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        return changeListener;
    }
}