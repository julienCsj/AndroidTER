package com.m2dl.challenge.challengeandroid.Service;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.m2dl.challenge.challengeandroid.Model.Cola;
import com.m2dl.challenge.challengeandroid.Model.Glacon;
import com.m2dl.challenge.challengeandroid.Model.Objet;

import java.util.Random;

/**
 * Created by Elliot on 28/01/2016.
 */
public class GenerationObjet {
    private int widthScreen;
    private int heightScreen;

    public GenerationObjet(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);

        widthScreen = metrics.widthPixels;
        heightScreen = metrics.heightPixels;
    }

    public Objet genererObjetAleatoire() {
        Objet resultat = null;

        Random r = new Random();
        int vitesse = r.nextInt(15 - 5) + 5;
        int x = r.nextInt((widthScreen - widthScreen/10) - (widthScreen/10)) + widthScreen/10;
        int y = 0;//r.nextInt(heightScreen - heightScreen/5);


        switch (r.nextInt(2)) {
            case 0:
                resultat = new Glacon(new Float(x), new Float(y), new Float(vitesse));
                break;
            case 1:
                resultat = new Cola(new Float(x), new Float(y), new Float(vitesse));
                break;
        }

        return resultat;
    }
}
