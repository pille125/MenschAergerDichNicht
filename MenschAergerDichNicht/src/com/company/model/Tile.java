package com.company.model;

public class Tile {

    private TileType type = TileType.NONE;
    private Tile next = null;
    private Tile goal = null;
    private Piece piece = null;
    private int playerID = -1;

    public Tile() {}

    public Tile setNext(Tile next) {
        this.next = next;
        return this;
    }

    public Tile getNext() {
        return next;
    }

    public Tile setType(TileType type) {
        this.type = type;
        return this;
    }

    public Tile setGoal(Tile goal) {
        this.goal = goal;
        return this;
    }
    public Tile getGoal() {
        return goal;
    }

    public TileType getType() {
        return type;
    }

    public Piece getPiece() {
        return piece;
    }

    public Tile setPlayerID(int playerID) {
        this.playerID = playerID;
        return this;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }
}
