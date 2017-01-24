package com.company.controller;

import com.company.controller.Player;
import com.company.model.Playfield;

/**
 * Created by pille125 on 21.01.17.
 */
public class KIPlayer extends Player {
    public KIPlayer(String name, int playerID, int numberOfPieces, Playfield playfield) {
        super(name, playerID, numberOfPieces, playfield);
    }
}
