package com.company.controller;

import com.company.model.Playfield;

public class HumanPlayer extends Player {

    //Constructor
    public HumanPlayer(String name, int playerID, int numberOfPieces, Playfield playfield) {
        super(name, playerID, numberOfPieces, playfield);
    }
}
