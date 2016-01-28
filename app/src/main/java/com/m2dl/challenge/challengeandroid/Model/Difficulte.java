package com.m2dl.challenge.challengeandroid.Model;

/**
 * Created by julien on 28/01/16.
 */
public enum Difficulte {
    FACILE("Champomy", ""),
    MOYEN("Bière", ""),
    DIFFICILE("Barman de l'extreme", "");

    private String traduction;
    private String traductionComplete;

    Difficulte(String traduction, String traductionComplete) {
        this.traduction = traduction;
        this.traductionComplete = traductionComplete;
    }
}
