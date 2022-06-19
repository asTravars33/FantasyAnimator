package com.wangjessica.jwlab04b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Animation extends AppCompatActivity {
    String TAG = "com.wangjessica.jwlab04b.values";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> names;
    ArrayList<String> types;
    ArrayList<ImageView> characters;
    TextView story;
    LinearLayout layout;
    String[] backgrounds = {"beach", "fantasy", "waterfall", "countryside"};
    String[] randomNames;
    String curStory = "";
    int curBack = 1;
    int W = 0;
    int H = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation);

        story = findViewById(R.id.dialogue);
        layout = findViewById(R.id.animation_screen);
        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        randomNames = getResources().getStringArray(R.array.random_names);

        Intent intent = getIntent();
        names = intent.getStringArrayListExtra("names");
        types = intent.getStringArrayListExtra("types");
        characters = new ArrayList<ImageView>();

        // Generate random names for the characters
        // If they were not given
        for(int i=0; i<names.size(); i++){
            if(names.get(i).equals("")){
                names.set(i, randomNames[(int)(Math.random()*randomNames.length)]);
            }
        }

        System.out.println(names);
        System.out.println(types);

        W = Math.min(300, (int)(360*2.5/types.size()));
        H = Math.min(400, (int)(4.0*2.5*360/(3*types.size())));

        // Place all the characters into the scene
        curStory = "Once upon a time, there was ";
        for(int i=0; i<types.size(); i++){
            // Add the character to the display...
            String type = types.get(i);
            ImageView image = new ImageView(this);
            System.out.println(layout.getWidth());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(W, H);
            image.setLayoutParams(layoutParams);
            image.setImageResource(getResources().getIdentifier(type, "drawable", "com.wangjessica.jwlab04b"));
            layout.addView(image);
            characters.add(image);
            // ...and to the story
            if(i==types.size()-1){
                curStory += "and "+names.get(i)+" the "+type+".";
            }
            else{
                curStory +=names.get(i)+" the "+type+", ";
            }
        }
        story.setText(curStory);

        // Add movement controls and dialogue fields
        LinearLayout layout = findViewById(R.id.motion_layout);
        for(int i=0; i<characters.size(); i++){
            LinearLayout sub = new LinearLayout(this);
            sub.setOrientation(LinearLayout.HORIZONTAL);
            Button back = new Button(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
            back.setLayoutParams(lp);
            back.setId(i);
            back.setText("<");
            back.setOnClickListener(this::offset);
            Button forward = new Button(this);
            forward.setLayoutParams(lp);
            forward.setId(i);
            forward.setText(">");
            forward.setOnClickListener(this::offset);
            sub.addView(back);
            sub.addView(forward);
            layout.addView(sub);
        }
    }

    public void celebrate(View view) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean up = true;
            int cnt = 0;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(cnt==7){
                            timer.cancel();
                        }
                        System.out.println("Running..");
                        boolean localUp = up;
                        for(ImageView img: characters){
                            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) img.getLayoutParams();
                            LinearLayout.LayoutParams newLp = new LinearLayout.LayoutParams(W, H);
                            int newTop = localUp?lp.topMargin+50: lp.topMargin-50;
                            localUp = !localUp;
                            newLp.setMargins(lp.leftMargin, newTop, lp.rightMargin, lp.bottomMargin);
                            img.setLayoutParams(newLp);
                        }
                        up = !up;
                        cnt++;
                    }
                });
            }
        }, 500, 500);
        curStory+="They celebrated.";
        story.setText(curStory);
    }

    public void fight(View view) {
        // "Meeting" spot
        System.out.println(layout.getHeight());
        int marTop = layout.getHeight()/2;
        int marLeft = layout.getWidth()/2;
        // Run the animation
        Timer timer = new Timer();
        LinearLayout.LayoutParams[] curPos = new LinearLayout.LayoutParams[characters.size()];
        for(int i=0; i<curPos.length; i++){
            curPos[i] = (LinearLayout.LayoutParams) characters.get(i).getLayoutParams();
            System.out.println(curPos[i]);
        }
        timer.schedule(new TimerTask() {
            boolean met = false;
            int cnt = 0;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageView center = findViewById(R.id.magic);
                        System.out.println("Running");
                        System.out.println(met);
                        if(cnt>6){
                            timer.cancel();
                        }
                        if(!met){
                            for(int i=0; i<characters.size(); i++){
                                ImageView img = characters.get(i);
                                img.setVisibility(View.GONE);
                            }
                            center.setVisibility(View.VISIBLE);
                            met = true;
                        }
                        else{
                            System.out.println("Going back");
                            for(int i=0; i<characters.size(); i++){
                                ImageView img = characters.get(i);
                                img.setVisibility(View.VISIBLE);
                            }
                            center.setVisibility(View.GONE);
                            met = false;
                        }
                        cnt++;
                    }
                });
            }
        }, 500, 500);
        curStory += "They fought.";
        story.setText(curStory);
    }

    public void teleport(View view) {
        int id = (int)(Math.random()*backgrounds.length);
        while(id==curBack){
            id = (int)(Math.random()*backgrounds.length);
        }
        curStory += "The group teleported from the "+backgrounds[curBack] +" to the "+backgrounds[id]+". ";
        curBack = id;
        ImageView back = findViewById(R.id.animation_background);
        back.setBackground(getResources().getDrawable(getResources().getIdentifier(backgrounds[id], "drawable", "com.wangjessica.jwlab04b")));
    }

    public void save(View view) {
        int cnt = sharedPreferences.getInt("cnt", 0);
        editor.putString("story"+(cnt+1), curStory).apply();
        editor.putInt("cnt", cnt+1).apply();
    }

    public void offset(View view){
        Button clicked = (Button) view;
        int id = clicked.getId();
        int deltaLeft = clicked.getText()=="<"?-50: 50;
        LinearLayout.LayoutParams newLp = new LinearLayout.LayoutParams(W, H);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) characters.get(id).getLayoutParams();
        newLp.setMargins(lp.leftMargin+deltaLeft, lp.topMargin, lp.rightMargin-deltaLeft, lp.bottomMargin);
        characters.get(id).setLayoutParams(newLp);
        curStory += names.get(id)+" moved "+(deltaLeft==-50?"to the left.":"to the right.");
        story.setText(curStory);
    }
}