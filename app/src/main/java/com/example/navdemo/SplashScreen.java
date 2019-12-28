package com.example.navdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.navdemo.App.dataMap;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;

    ArrayList<Integer> randoms = new ArrayList<>();

    public void prepareData() {
        if (dataMap == null) {
            dataMap = new HashMap<>();

            dataMap.put("random", new ArrayList<Integer>());
            prepareToAddImages(R.drawable.class.getFields());
            ArrayList<Integer> randomImages = dataMap.get("random");
            ArrayList<Integer> filteredRandoms = new ArrayList<>();
            int count = 9;
            while (count >= 0) {
                filteredRandoms.add(randomImages.get(count));
                count--;
            }
            dataMap.put("random", filteredRandoms);
        }
    }

    public void prepareToAddImages(Field[] fields) {
        for (Field field : fields) {
            String name = field.getName();
            if (name.startsWith("lens")) {
                String[] cats = name.split("_");
                String category = cats[1];
                dataMap.get("random").add(getResources().getIdentifier(field.getName(), "drawable", getPackageName()));
            }
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.
                FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        logo = (ImageView) findViewById(R.id.companyIcon);
        logo.postDelayed(new Runnable() {
            @Override
            public void run() {
                zoomLogoIn();
            }
        }, 1);
    }

    void zoomLogoIn() {
        Animation bubbleAnim = AnimationUtils.loadAnimation(SplashScreen.this,
                R.anim.bubble_zoom_in_anim);
        bubbleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                zoomLogoOut();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        logo.startAnimation(bubbleAnim);
    }

    void zoomLogoOut() {
        Animation bubbleAnim = AnimationUtils.loadAnimation(SplashScreen.this,
                R.anim.bubble_zoom_out_anim);
        bubbleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setVisibility(View.VISIBLE);
                fadeInJataks();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        logo.startAnimation(bubbleAnim);
    }

    void fadeInJataks() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(900);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                preLoadData();

            }
        }, 2500);
        super.onPostCreate(savedInstanceState);
    }

    private void preLoadData() {
        //    ((App)getApplication()).loadAds();
        prepareData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exitCompLogo();
            }
        }, 600);
    }

    private void exitCompLogo() {
        Animation zoomOut = new AlphaAnimation(1, 0);
        zoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setVisibility(View.INVISIBLE);
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        zoomOut.setDuration(500);
        logo.startAnimation(zoomOut);


    }
}
