package com.m2dl.challenge.challengeandroid.Activity;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.m2dl.challenge.challengeandroid.Model.Configuration;
import com.m2dl.challenge.challengeandroid.Model.Difficulte;
import com.m2dl.challenge.challengeandroid.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionsActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button sauvegarder;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


        Configuration confActuelle = new Select().from(Configuration.class).orderBy("date DESC").executeSingle();
        if (confActuelle == null) {
            confActuelle = new Configuration("Anonyme", Difficulte.FACILE, new Date());
        }

        final Difficulte difficulte = confActuelle.getDifficulte();


        spinner = (Spinner) findViewById(R.id.spinner);
        input = (EditText) findViewById(R.id.pseudo);


        List<Map<String, String>> items = new ArrayList<Map<String, String>>();
        List<Difficulte> difficultes = Arrays.asList(Difficulte.values());

        for (Difficulte d : difficultes) {
            Map<String, String> item = new HashMap<String, String>(2);
            item.put("text", d.traduction);
            item.put("subText", d.traductionComplete);
            items.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, items,
                android.R.layout.simple_spinner_item,
                new String[] {"text", "subText"},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

        adapter.setDropDownViewResource(android.R.layout.simple_list_item_2);


        spinner.setAdapter(adapter);


        if (confActuelle.getPseudo() != null && confActuelle.getDifficulte() != null) {
            input.setText(confActuelle.getPseudo());
            spinner.post(new Runnable() {
                @Override
                public void run() {
                    spinner.setSelection(difficulte.ordre);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Pas de val", Toast.LENGTH_SHORT).show();
        }


        sauvegarder = (Button) findViewById(R.id.buttonSauvegarder);

        sauvegarder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int difficulte = spinner.getSelectedItemPosition();
                String pseudo = input.getText().toString();

                Configuration conf = new Configuration();
                conf.setPseudo(pseudo);
                conf.setDifficulte(Difficulte.getByPosition(difficulte));
                conf.setDate(new Date());
                conf.save();

                Toast.makeText(getApplicationContext(), "Configuration sauvegardée", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        });
    }


}
