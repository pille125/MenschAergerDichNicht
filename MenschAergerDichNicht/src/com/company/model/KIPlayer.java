package com.company.model;


import java.util.ArrayList;


public class KIPlayer extends Player {
    public KIPlayer(String name, int playerID, int numberOfPieces, Playfield playfield) {
        super(name, playerID, numberOfPieces, playfield);
    }

    public ArrayList<Piece> getPieceOrderForMove() {

        ArrayList<Piece> order = new ArrayList<>();

        // let ki perform start pieces first
        for (Piece piece : pieces) {
            if (piece.getTile().getType() == TileType.START) {
                order.add(piece);
                break;
            }
        }

        // get the rest
        for (Piece piece : pieces) {
            if (piece.getTile().getType() != TileType.START) {
                order.add(piece);
                break;
            }
        }

        return order;
    }
}
