package com.m2dl.challenge.challengeandroid.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by julien on 28/01/16.
 */

@Table(name = "score")
public class Score extends Model {
    @Column(name = "score")
    private int score;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "pathPhoto")
    private String pathPhoto;

    @Column(name = "difficulte")
    private Difficulte difficulte;

    public Score() {
    }

    public Score(int score, String pseudo, Difficulte difficulte) {
        this.score = score;
        this.pseudo = pseudo;
        this.difficulte = difficulte;
        this.pathPhoto = "";
    }

    public Difficulte getDifficulte() {
        return difficulte;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getScore() {
        return score;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }
}
