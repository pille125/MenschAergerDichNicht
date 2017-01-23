package com.company.model;

import com.company.controller.Player;

public class Tile {

    private TileType type = TileType.NONE;
    private Tile next = null;
    private int playerID = -1;
    private Tile goal = null;

    public Tile() {}

    public Tile setNext(Tile next) {
        this.next = next;
        return this;
    }

    public Tile setType(TileType type) {
        this.type = type;
        return this;
    }

    public Tile setGoal(Tile next) {
        this.next = next;
        return this;
    }

    public TileType getType() {
        return type;
    }

    public Tile setPlayerID(int playerID) {
        this.playerID = playerID;
        return this;
    }

    public int getPlayerID() {
        return playerID;
    }
}
