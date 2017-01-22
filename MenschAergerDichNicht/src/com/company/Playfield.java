package com.company;

import java.util.Vector;

/**
 * Created by pille125 on 09.01.17.
 */
public class Playfield {
    private static Playfield instance = null;
    private Vector<Piece> fields;

    //Constructor
    public Playfield() {
        this.fields = new Vector<Piece>(56);
        this.fields.setSize(56);
    }

    //Singelton
    public static Playfield getInstance() {
        if (instance == null) {
            instance = new Playfield();
        }
        return instance;
    }
}
