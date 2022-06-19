package com.wangjessica.jwlab04b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void makeStory(View view) {
        Intent intent = new Intent(getApplicationContext(), Characters.class);
        startActivity(intent);
    }

    public void viewStories(View view) {
        Intent intent = new Intent(getApplicationContext(), Stories.class);
        startActivity(intent);
    }
}