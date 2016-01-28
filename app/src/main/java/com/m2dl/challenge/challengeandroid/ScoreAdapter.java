package com.m2dl.challenge.challengeandroid;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m2dl.challenge.challengeandroid.Model.Score;

import java.util.List;

/**
 * Created by Elliot on 28/01/2016.
 */
public class ScoreAdapter extends ArrayAdapter<Score> {

    List<Score> scores;
    LayoutInflater inflater;

    //Initialize adapter
    public ScoreAdapter(Context context, List<Score> scores) {
        super(context, -1, scores);
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.scores = scores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = inflater.inflate(R.layout.row_score, null);
        int positionReelle = position + 1;
        ((TextView) view.findViewById(R.id.place)).setText(positionReelle + ".");
        ((ImageView) view.findViewById(R.id.photo)).setImageBitmap(BitmapFactory.decodeFile(scores.get(position).getPathPhoto()));
        ((TextView) view.findViewById(R.id.pseudoEtDiff)).setText(scores.get(position).getPseudo() + " (" + scores.get(position).getDifficulte().getTraduction() + ")");
        ((TextView) view.findViewById(R.id.montantScore)).setText(scores.get(position).getScore()+"");
        return view;
    }

}
