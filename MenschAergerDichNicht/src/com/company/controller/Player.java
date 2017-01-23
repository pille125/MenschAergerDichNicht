package com.company.controller;

import com.company.model.Piece;

import java.util.Vector;

/**
 * Created by pille125 on 09.01.17.
 */
public class Player {
    private Vector<Piece> pieces = null;
    private int homeLocation = -1;
    private int playerNumber = -1;
    private String name = null;

    private Boolean diceRolled = false;
    private int lastDiceRoll = 0;
    public Player() {

    }
    //Constructor
    public Player(String name, int playerNumber, int numberOfPieces) {
        this.homeLocation = (playerNumber - 1) * 10;
        this.name = name;
        this.playerNumber = playerNumber;
        this.pieces = new Vector<Piece>(numberOfPieces);

        for (int i=0; i<numberOfPieces; i++) {
            this.pieces.add(new Piece(this));
        }
    }

    public Vector<Piece> getPieces() {
        return this.pieces;
    }

    public int getHomeLocation() {
        return this.homeLocation;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public Boolean putAvailableHomePieceOut() {
        for (Piece piece : pieces) {
            if (piece.getPosition() == -1) {
                piece.goOut();
                return true;
            }
        }
        return false;
    }

    public int getLastDiceRoll() {
        return this.lastDiceRoll;
    }

    public String getName() {
        return this.name;
    }

    //true if player has a piece out of the house
    public boolean hasPieceOut() {
        for (int i=0; i<4; i++) {
            //check if piece is out of house and not finished
            if ((pieces.elementAt(i).getPosition() > -1) && (pieces.elementAt(i).getPosition() < 40)) {
                return true;
            }
        }
        return false;
    }

    //return true if Player has won
    public boolean hasWon() {
        for (int i=0; i<4; i++) {
            if (!pieces.elementAt(i).isFinished()) {
                return false;
            }
        }
        return true;
    }
    public int rollDice() {
        diceRolled = true;
        lastDiceRoll = (int)(Math.random() * 6 + 1);
        return lastDiceRoll;
    }
    public Boolean isDiceRolled() {
        return diceRolled;
    }

    public void setDiceRolled(Boolean diceRolled) {
        this.diceRolled = diceRolled;
    }

    public void moveComplete() {
        diceRolled = false;
    }
}
