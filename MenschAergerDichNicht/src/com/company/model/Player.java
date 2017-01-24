package com.company.model;

import com.company.model.Piece;
import com.company.model.Playfield;
import com.company.model.Tile;
import com.company.model.TileType;

import java.util.ArrayList;
import java.util.Vector;

public class Player {

    protected Vector<Piece> pieces = null;

    protected int playerID = -1;
    protected String name = null;

    protected Tile startTile;
    protected ArrayList<Tile> homeTiles = new ArrayList<Tile>();

    //Constructor
    public Player(String name, int playerID, int numPieces, Playfield playfield) {
        this.name = name;
        this.playerID = playerID;
        this.pieces = new Vector<Piece>(numPieces);

        for (int i=0; i<numPieces; i++) {
            this.pieces.add(new Piece(this));
        }

        findOwnStartTile(playfield);
        findOwnHomeTiles(playfield);

        // connect home tile to start tile
        for (Tile homeTile : homeTiles) {
            homeTile.setNext(startTile);
        }

        // set pieces on home tiles
        for (Piece piece : pieces) {
            setPieceToHome(piece);
        }
    }

    private void findOwnStartTile(Playfield playfield) {
        for (int i = 0; i < playfield.getNumRows(); i++) {
            for (int j = 0; j < playfield.getNumColumns(); j++) {
                Tile tile = playfield.getTile(i, j);

                if (tile.getPlayerID() == playerID && tile.getType() == TileType.START) {
                    startTile = tile;
                    return;
                }
            }
        }
    }

    private void findOwnHomeTiles(Playfield playfield) {
        for (int i = 0; i < playfield.getNumRows(); i++) {
            for (int j = 0; j < playfield.getNumColumns(); j++) {
                Tile tile = playfield.getTile(i, j);

                if (tile.getPlayerID() == playerID && tile.getType() == TileType.HOME) {
                    homeTiles.add(tile);
                    if (homeTiles.size() == pieces.size())
                        return;
                }
            }
        }
    }

    public Vector<Piece> getPieces() {
        return this.pieces;
    }

    public void setPieceToHome(Piece piece) {
        for (Tile homeTile : homeTiles) { // just find a free home tile and set it

            if (!homeTile.hasPiece()) {
                homeTile.setPiece(piece);
                piece.setTile(homeTile);
                return;
            }
        }
    }

    public Tile getStartTile() {
        return startTile;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public String getName() {
        return this.name;
    }

    //true if player has a piece out of the house
    public boolean hasPieceOut() {
        for (Tile homeTile : homeTiles) {
            if (!homeTile.hasPiece()) {
                return true;
            }
        }
        return false;
    }

    //return true if Player has won
    public boolean hasWon() {
        for (Piece piece : pieces) {
            if (piece.getTile().getType() != TileType.GOAL)
                return false;
        }
        return true;
    }
}
