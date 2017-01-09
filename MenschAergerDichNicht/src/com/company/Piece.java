package com.company;

/**
 * Created by pille125 on 09.01.17.
 */
public class Piece {
    private Player owner;
    private int position;

    //Constructor
    public Piece(Player owner) {
        this.owner = owner;
        this.position = -1;
    }

    public int getPosition() {
        return position;
    }

    public Player getOwner() {
        return owner;
    }

    //lets the piece out of the house
    public void goOut() {
        position = owner.getHomeLocation();
    }

    //move piece
    public void moveBy(int diceThrow) {
        position = position + diceThrow;
    }

    //put piece back to home, if piece was hit
    public void removePiece() {
        this.position = -1;
    }

    //return true if piece is close to finish
    public boolean isCloseToFinish() {
        switch (owner.getPlayerNumber()) {
            case 0:
                return (this.position > 39 - 6) && (this.position <= 39);
            case 1:
                return (this.position > 9 - 6) && (this.position <= 9);
            case 2:
                return (this.position > 19 - 6) && (this.position <= 19);
            default:
                return (this.position > 29 - 6) && (this.position <= 29);
        }
    }

    //return true if piece is finished
    public boolean isFinished() {
        return position > 39 + (4 * owner.getPlayerNumber());
    }

}
