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

    // look ahead function, does not change any tile or piece!
    public Tile getTargetTile(int diceRoll) {
        Tile targetTile = tile;
        while (diceRoll > 0) {

            // grab the correct next tile
            if (targetTile.getType() == TileType.HOME) {
                targetTile = owner.getStartTile();
            } else if (targetTile.getType() == TileType.TOGOAL) {

                if (targetTile.getPlayerID() == owner.getPlayerID()) { // our to goal switch
                    targetTile = targetTile.getGoal();
                } else {
                    targetTile = targetTile.getNext();
                }
            }
            if (targetTile == null) // run over last goal
                break;

            diceRoll--;
        }
        return targetTile;  // tile to inspect by game logic
    }

    //move piece, this really changes states
    public void moveBy(int diceRoll) {
        tile.setPiece(null);

        while (diceRoll > 0) {

            // grab the correct next tile
            if (tile.getType() == TileType.HOME) {
                tile = owner.getStartTile();
            } else if (tile.getType() == TileType.TOGOAL) {

                if (tile.getPlayerID() == owner.getPlayerID()) { // our to goal switch
                    tile = tile.getGoal();
                } else {
                    tile = tile.getNext();
                }
            }

            if (diceRoll == 1) {  // this was the last step
                if (tile.hasPiece()) { // enemy piece
                    tile.getPiece().getOwner().setPieceToHome(tile.getPiece());
                }
            }

            diceRoll--;
        }

        tile.setPiece(this);
    }

    // move a piece from one tile to another
    // this is no game move
    public void move(Tile toTile) {
        if (toTile.hasPiece()) {
            throw new Error("handle piece on this tile first");
        }

        tile.setPiece(null);    // remove piece from this tile
        toTile.setPiece(this);  // set it to new tile
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
