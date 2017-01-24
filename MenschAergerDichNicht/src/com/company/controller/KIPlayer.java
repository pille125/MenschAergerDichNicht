package com.company.controller;

import com.company.controller.Player;
import com.company.model.Playfield;


public class KIPlayer extends Player {
    public KIPlayer(String name, int playerID, int numberOfPieces, Playfield playfield) {
        super(name, playerID, numberOfPieces, playfield);
    }
}
