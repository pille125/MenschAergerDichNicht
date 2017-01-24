package com.company.model;

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

    // look ahead function, does not change any tile or piece!
    public Tile getTargetTile(int diceRoll) {
        Tile targetTile = tile.getNext();

        while (diceRoll > 0) {
            if (targetTile == null) // run over last goal
                break;

            // grab the correct next tile
            switch (targetTile.getType()) {
                case HOME:
                    targetTile = owner.getStartTile();
                    break;
                case TOGOAL:
                    if (targetTile.getPlayerID() == owner.getPlayerID()) { // our to goal switch
                        targetTile = targetTile.getGoal();
                    } else {
                        targetTile = targetTile.getNext();
                    }
                    break;
                case WAY:
                case GOAL:
                case START:
                    targetTile = targetTile.getNext();
                    break;
            }
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
            }
            else if (tile.getType() == TileType.TOGOAL) {

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
