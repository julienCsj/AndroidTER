package com.m2dl.challenge.challengeandroid.Model;

import android.graphics.Bitmap;

/**
 * Created by Elliot on 28/01/2016.
 */
// Les objets qui tombent du ciel
public class Objet {
    private Float x;
    private Float y;
    private Float vitesse;

    private int skin;

    public Objet(Float x, Float y, Float vitesse, int skin) {
        this.x = x;
        this.y = y;
        this.skin = skin;
        this.vitesse = vitesse;
    }

    public void bouger() {
        y += vitesse;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public Float getVitesse() {
        return vitesse;
    }

    public int getSkin() {
        return skin;
    }
}
