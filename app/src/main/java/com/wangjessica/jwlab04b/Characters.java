package com.wangjessica.jwlab04b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Characters extends AppCompatActivity {
    LinearLayout charsLayout;
    LinearLayout namesLayout;
    String recentIdentifier;
    ImageView preview;
    ArrayList<String> names=new ArrayList<String>(); // Character type --> name
    ArrayList<String> types=new ArrayList<String>();
    ArrayList<EditText> edits=new ArrayList<EditText>();
    int ind = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.characters);

        // Initialize page elements
        namesLayout = findViewById(R.id.names_layout);
        charsLayout = findViewById(R.id.characters);
        preview = findViewById(R.id.preview);
    }

    public void selectChar(View view) {
        Button clicked = (Button) view;
        String type = clicked.getText().toString();
        recentIdentifier = type.substring(0,1).toLowerCase(Locale.ROOT) + type.substring(1);
        preview.setImageResource(getResources().getIdentifier(recentIdentifier, "drawable", "com.wangjessica.jwlab04b"));
    }

    public void addChar(View view) {
        // Add the character to the display
        ImageView image = new ImageView(this);
        System.out.println("testing: "+charsLayout.getWidth()+":"+ charsLayout.getHeight());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 400);
        image.setLayoutParams(layoutParams);
        image.setImageResource(getResources().getIdentifier(recentIdentifier, "drawable", "com.wangjessica.jwlab04b"));
        charsLayout.addView(image);
        // Internally store character type
        types.add(recentIdentifier);
        names.add("");
        // Give the user an option to change the character's name
        EditText editName = new EditText(this);
        editName.setHint(""+types.size());
        editName.setId(types.size()-1);
        namesLayout.addView(editName);
        edits.add(editName);
    }

    public void nextScreen(View view) {
        // Update all the character names
        for(EditText edit: edits){
            names.set(edit.getId(), edit.getText().toString());
        }
        // Pass along character info to next screen
        System.out.println("here");
        Intent next = new Intent(getApplicationContext(), Animation.class);
        next.putExtra("names", names);
        next.putExtra("types", types);
        startActivity(next);
    }
}