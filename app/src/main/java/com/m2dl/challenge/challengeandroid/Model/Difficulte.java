package com.m2dl.challenge.challengeandroid.Model;

import android.util.Log;

/**
 * Created by julien on 28/01/16.
 */
public enum Difficulte {
    FACILE("Champomy", "Pour les débutants, c'est pas violent", 0, 90, 15),
    MOYEN("Bière", "Y'a moyen de s'amuser", 1, 60, 20),
    DIFFICILE("Barman de l'extreme", "Ca va mal finir !", 2, 30, 30);

    public String traduction;
    public String traductionComplete;
    public Integer ordre;
    public Integer timer;
    public Integer vitesse;

    Difficulte(String traduction, String traductionComplete, int ordre, int timer, int vitesse) {
        this.traduction = traduction;
        this.traductionComplete = traductionComplete;
        this.ordre = ordre;
        this.timer = timer;
        this.vitesse = vitesse;
    }

    public static Difficulte getByPosition(int t) {
        Log.d("ter.difficulte", "TEST");
        for (Difficulte d : Difficulte.values()) {
            if(d.ordre.equals(t)) {
                return d;
            }
        }
        return null;
    }

    public String getTraduction() {
        return traduction;
    }
}
