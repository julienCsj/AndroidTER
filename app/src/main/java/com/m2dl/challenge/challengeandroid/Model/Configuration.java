package com.m2dl.challenge.challengeandroid.Model;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by julien on 28/01/16.
 */

@Table(name = "score")
public class Configuration {

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "difficulte")
    private Difficulte difficulte;
}
