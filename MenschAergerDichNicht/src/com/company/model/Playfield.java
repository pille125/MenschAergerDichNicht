package com.company.model;


public class Playfield {
    private static Playfield instance = null;


    private Tile[][] tiles = null;
    private int numRows = 0;
    private int numColumns = 0;
    private int numPlayers = 0;
    private int numPiecesPerPlayer[];

    //Constructor
    private Playfield() {

        numRows = GEN_PlayfieldCreator.getNumRows();
        numColumns = GEN_PlayfieldCreator.getNumColumns();
        numPlayers = GEN_PlayfieldCreator.getNumPlayers();

        numPiecesPerPlayer = new int[numPlayers];
        for (int i=0; i<numPlayers; i++) {
            numPiecesPerPlayer[i] = GEN_PlayfieldCreator.getNumPiecesPerPlayer(i+1); // i+1 = playerID
        }

        GEN_PlayfieldCreator.createPlayfield(this);
    }

    //Singleton
    public static Playfield getInstance() {
        if (instance == null) {
            instance = new Playfield();
        }
        return instance;
    }

    public void setupTiles() {
        tiles = new Tile[numRows][numColumns];
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

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int getNumPiecesOfPlayer(int id) {
        return numPiecesPerPlayer[id - 1];
    }
}
