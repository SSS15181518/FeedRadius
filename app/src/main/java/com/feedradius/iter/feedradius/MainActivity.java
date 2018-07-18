package com.feedradius.iter.feedradius;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public static int SPLASH_TIME_OUT = 3000;
        public static final String DEFAULT = "N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.animate().rotation(360).setDuration(2000);

        new Handler().postDelayed(new Runnable(){


            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("MyData",MODE_PRIVATE);
                String eid = sharedPreferences.getString("eid",DEFAULT);
                String pass = sharedPreferences.getString("pass",DEFAULT);
                if (eid.equals(DEFAULT)) {
                    Intent homeIntent = new Intent(getApplication(), homeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                else
                {
                    Intent homeIntent = new Intent(getApplication(), feedback.class);
                    startActivity(homeIntent);
                    finish();
                }

            }
        },SPLASH_TIME_OUT);














    }


}
