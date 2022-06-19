package com.wangjessica.jwlab04b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Stories extends AppCompatActivity {
    String TAG = "com.wangjessica.jwlab04b.values";
    SharedPreferences sharedPreferences;
    LinearLayout stories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stories);

        stories = findViewById(R.id.stories);
        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);

        int cnt = sharedPreferences.getInt("cnt", 0);
        for(int i=1; i<=cnt; i++){
            String cur = sharedPreferences.getString("story"+i,"");
            TextView text = new TextView(this);
            text.setBackgroundColor(getResources().getColor(R.color.light));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(20, 10, 20, 10);
            text.setLayoutParams(params);
            text.setText(cur);
            stories.addView(text);
        }
    }
}