package com.company.controller;

import com.company.model.Playfield;
import com.company.model.Piece;
import com.company.model.Tile;
import com.company.model.TileType;
import com.company.view.GUI;
import com.company.view.PlayfieldPanel;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;


public class Game {

    private PlayerController playerController;
    private GUI gui;
    private Playfield playfield;

    // game logic
    private Boolean gameStarted = false;
    private int currentPlayerID = 0;
    private Player currentPlayer = null;
    private boolean isHumanPlayersTurn = false;
    private boolean isDiceRolled = false;
    private int diceRoll = 0;

    public Game(PlayerController playerController, Playfield playField) {
        this.playerController = playerController;
        this.playfield = playField;
        this.gui = null;
    }

    public void setGUI(GUI gui) { this.gui = gui; }

    public void onStartButtonClicked(ActionEvent event) {
        gui.disableStartGame();
        startGame();
    }

    public void onRollDiceButtonClicked(ActionEvent event) {
        rollDice();
        gui.disableDiceRoll();
    }

    public void onMouseClicked(MouseEvent event, PlayfieldPanel panel) {
        if (!isGameStarted())
            return;

        if (!isDiceRolled)
            return;

        // grab input
        int row    = event.getY() / panel.getSizePerPiece();
        int column = event.getX() / panel.getSizePerPiece();
        if (row    < 0 || row    >= playfield.getNumRows() ||
            column < 0 || column >= playfield.getNumColumns())
            return;

        System.out.println("row: " + row + " column: " + column);
        Tile clickedTile = playfield.getTile(row, column);
        if (!clickedTile.hasPiece())
            return;

        // player rolled dice, click on a own piece to move it if legal
        Piece clickedPiece = clickedTile.getPiece();
        if (clickedPiece.getOwner() != currentPlayer) // not our piece
            return;

        if (diceRoll == 6 &&
            clickedPiece.getTile().getType() == TileType.HOME &&
            isValidMove(clickedPiece, 1)) { // hack to target start pos

            clickedPiece.moveBy(1);
            prepareBonusRoll();
            return;
        }

        if (diceRoll != 6 &&
            clickedPiece.getTile().getType() != TileType.HOME &&
            isValidMove(clickedPiece, diceRoll)) {

            clickedTile.getPiece().moveBy(diceRoll);
            prepareNextPlayer();
            return;
        }
    }

    public void startGame() {
        // TODO: clear all playfield tiles
        // TODO: reset the players

        // initialize a new game
        gameStarted = true;
        diceRoll = -1;
        isDiceRolled = false;
        currentPlayerID = 0; // player ids start at 1
        prepareNextPlayer();

        System.out.println("GAME STARTED");
        turn();
    }

    public void turn() {
        System.out.println("turn player " + currentPlayerID + " " +
                (isHumanPlayersTurn ? "HUMAN" : "KI") + gui.getPlayerColor(currentPlayerID));
        if (isHumanPlayersTurn)
            return; // mouse and button actions will resolve turn

        // ki here, resolve the move
        rollDice();

        // make a move
        for (Piece piece : currentPlayer.getPieces()) {
            if (isValidMove(piece, diceRoll)) {

                // if its a valid move and a piece is on the to be moved tile
                // its a legal hit
                piece.moveBy(diceRoll);

                // TODO: handle bonus roll for ki
                // prepareBonusRoll();

                prepareNextPlayer();
            }
        }
    }

    private void prepareBonusRoll () {
        diceRoll = -1;
        isDiceRolled = false;
        if (isHumanPlayersTurn) {
            gui.enableDiceRoll();
        } else {
            gui.disableDiceRoll();
        }
    }

    public void prepareNextPlayer() {
        diceRoll = -1;
        isDiceRolled = false;
        currentPlayerID = (currentPlayerID + 1) % (playerController.getAllPlayers().size() + 1);
        currentPlayer = playerController.getAllPlayers().get(currentPlayerID - 1);
        isHumanPlayersTurn = playerController.isHumanPlayer(currentPlayerID - 1);
        if (isHumanPlayersTurn) {
            gui.enableDiceRoll();
        } else {
            gui.disableDiceRoll();
        }
    }

    public boolean isValidMove(Piece piece, int diceRoll) {
        Tile targetTile = piece.getTargetTile(diceRoll);

        return targetTile == null ||  // player would run over last goal tile
               targetTile.getPiece().getOwner().getPlayerID() == currentPlayerID; // player would hit himself
    }

    public Boolean isGameStarted() {
        return gameStarted;
    }

    public Playfield getPlayfield() {
        return playfield;
    }

    public int checkForWin() {
        for (Player player: playerController.getAllPlayers()) {
            boolean playerWin = true;
            for (Piece piece : player.getPieces()) {
                if (piece.getTile().getType() != TileType.GOAL) {
                    playerWin = false;
                    break;
                }
            }

            if (playerWin == true) {
                return player.getPlayerID();
            }
        }
        return -1;
    }

    public void rollDice() {
        diceRoll = (int)(Math.random() * 6 + 1);
        isDiceRolled = true;
        System.out.println("Es wurde eine " + diceRoll + " gewürfelt");
    }

    public boolean isDiceRolled() {
        return isDiceRolled;
    }

}
