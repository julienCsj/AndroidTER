package com.m2dl.challenge.challengeandroid.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by julien on 28/01/16.
 */

@Table(name = "configuration")
public class Configuration extends Model {

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "difficulte")
    private Difficulte difficulte;

    @Column(name = "date")
    private Date date;

    public Configuration() {}

    public Configuration(String pseudo, Difficulte difficulte, Date date) {
        this.pseudo = pseudo;
        this.difficulte = difficulte;
        this.date = date;
    }


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Difficulte getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(Difficulte difficulte) {
        this.difficulte = difficulte;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "pseudo='" + pseudo + '\'' +
                ", difficulte=" + difficulte +
                ", date=" + date +
                '}';
    }
}
