package com.m2dl.challenge.challengeandroid.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.m2dl.challenge.challengeandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionsActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        spinner = (Spinner) findViewById(R.id.spinner);

        List<Map<String, String>> items = new ArrayList<Map<String, String>>();

        Map<String, String> item0 = new HashMap<String, String>(2);
        item0.put("text", "Champomy");
        item0.put("subText", "Pour les débutants, c'est pas violent");
        items.add(item0);

        Map<String, String> item1 = new HashMap<String, String>(2);
        item1.put("text", "Bière");
        item1.put("subText", "Y'a moyen de s'amuser");
        items.add(item1);

        Map<String, String> item2 = new HashMap<String, String>(2);
        item2.put("text", "Barman de l'extrême");
        item2.put("subText", "Ca va mal finir !");
        items.add(item2);

        SimpleAdapter adapter = new SimpleAdapter(this, items,
                android.R.layout.simple_spinner_item, // This is the layout that will be used for the standard/static part of the spinner. (You can use android.R.layout.simple_list_item_2 if you want the subText to also be shown here.)
                new String[] {"text", "subText"},
                new int[] {android.R.id.text1, android.R.id.text2}
        );

// This sets the layout that will be used when the dropdown views are shown. I'm using android.R.layout.simple_list_item_2 so the subtext will also be shown.
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_2);

        spinner.setAdapter(adapter);
    }
}
