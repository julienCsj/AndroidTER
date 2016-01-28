package com.m2dl.challenge.challengeandroid.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.m2dl.challenge.challengeandroid.JeuView;

public class JeuActivity extends AppCompatActivity {

    private JeuView jeuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        FrameLayout layout = new FrameLayout(this);
        jeuView = new JeuView(this);
        layout.addView(jeuView);
        setContentView(layout);
        super.onStart();
    }

    @Override
    protected void onStop() {
        try {
            jeuView.getThread().setRunning(false);
            jeuView = null;
        }
        catch (NullPointerException e) {}
        super.onStop();
    }
}
