package com.company.model;

import com.company.controller.Player;

public class Piece {
    private Player owner;
    private Tile tile;

    //Constructor
    public Piece(Player owner) {
        this.owner = owner;
        this.tile = null;
    }

    public Tile getTile() {
        return tile;
    }
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Player getOwner() {
        return owner;
    }

    //lets the piece out of the house
    public void goOut() {
        this.tile = owner.getStartTile();
    }

    //move piece
    public void moveBy(int diceThrow) {
        tile.setPiece(null);

        while (diceThrow > 0) {

            if (tile.getType() == TileType.HOME)
                tile = owner.getStartTile();
            else
                tile = tile.getNext();

            diceThrow--;
        }

        tile.setPiece(this);
    }

    //put piece back to home, if piece was hit
    public void removePiece() {
        tile.setPiece(null);
        owner.setPieceToHome(this);
    }

    //return true if piece is close to finish
    public boolean isCloseToFinish() {
        if (tile.getType() == TileType.GOAL)
            return false;

        int i = 6;
        Tile next;
        while (i > 0) {
            next = tile.getNext();
            if (next.getType() == TileType.TOGOAL &&
                next.getGoal().getPlayerID() == owner.getPlayerID()) {
                return true;
            }
            i--;
        }
        return false;
    }
}
