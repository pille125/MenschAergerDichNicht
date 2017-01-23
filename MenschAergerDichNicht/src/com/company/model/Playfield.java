package com.company.model;

import com.company.model.GEN_PlayfieldCreator;
/**
 * Created by pille125 on 09.01.17.
 */

// das spielfeld besteht aus einzelnen feldern (tiles) besitzt aber KEINE spielfiguren (pieces)
// die werden im game gemanaged
public class Playfield {
    private static Playfield instance = null;


    private Tile[][] tiles = null;
    private int sizex = 0;
    private int sizey = 0;

    //Constructor
    public Playfield() {
        GEN_PlayfieldCreator.createPlayfield(this);
    }

    //Singleton
    public static Playfield getInstance() {
        if (instance == null) {
            instance = new Playfield();
        }
        return instance;
    }

    public void setupTiles(int sizex, int sizey) {
        this.sizex = sizex;
        this.sizey = sizey;
        tiles = new Tile[sizex][sizey];
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public Tile[][] getAllTiles() {
        return tiles;
    }

    public int getSizex() {
        return sizex;
    }

    public int getSizey() {
        return sizey;
    }

}
