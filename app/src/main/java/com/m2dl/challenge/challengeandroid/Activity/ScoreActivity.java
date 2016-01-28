package com.m2dl.challenge.challengeandroid.Activity;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.m2dl.challenge.challengeandroid.Model.Difficulte;
import com.m2dl.challenge.challengeandroid.Model.Score;
import com.m2dl.challenge.challengeandroid.R;
import com.m2dl.challenge.challengeandroid.ScoreAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    private ListView list;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        List<Score> scores = new Select().from(Score.class).orderBy("score desc").execute();

        /* Jeu de test pour l'ecran score
        *Score s1 = new Score(1000, "Patrick M.", "http", Difficulte.MOYEN);
        Score s2 = new Score(3000, "José-le-fou.", "http", Difficulte.DIFFICILE);
        Score s3 = new Score(200, "Fragile31", "http", Difficulte.FACILE);
        s1.save();
        s2.save();
        s3.save();*/

        scores = new Select().from(Score.class).orderBy("score desc").execute();
        list=(ListView) findViewById(R.id.listView);
        list.setAdapter(new ScoreAdapter(this, scores));


        reset = (Button) findViewById(R.id.resetScore);
        reset.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                List<Score> scores = new Select().from(Score.class).orderBy("score desc").execute();
                for (Score s : scores) {
                    s.delete();
                }

                finish();
                return true;
            }
        });
    }
}
