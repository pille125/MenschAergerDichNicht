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

    private Boolean gameStarted = false;
    private int currentPlayer = 0;

    public Game(PlayerController playerController, Playfield playField) {
        this.playerController = playerController;
        this.playfield = playField;
        this.gui = null;
    }

    public void setGUI(GUI gui) { this.gui = gui; }

    public void onStartButtonClicked(ActionEvent event) {
        startGame();
    }

    public void onRollDiceButtonClicked(ActionEvent event) {
        int roll = playerController.getHumanPlayer().rollDice();
        System.out.println("Der Spieler hat eine " + roll + " gewürfelt");
        if (roll == 6) {
            //Wenn möglich muss eine Spielfigur auf die start position
        } else {

            //warte auf MouseClick (defintition welche Spielfigur sich bewegen soll
        }
    }

    public void onMouseClicked(MouseEvent event, PlayfieldPanel panel) {
        if (!isGameStarted())
            return;

        int row    = event.getY() / panel.getSizePerPiece();
        int column = event.getX() / panel.getSizePerPiece();

        if (row    < 0 || row    >= playfield.getNumRows() ||
            column < 0 || column >= playfield.getNumColumns())
            return;

        System.out.println("row: " + row + " column: " + column);
        Tile clickedTile = playfield.getTile(row, column);
        if (!clickedTile.hasPiece())
            return;

        // TODO: jetzt kannste hier die game logic einbauen, have fun,
        // TODO: wenn irgendwo exceptions kommen, ist die spiellogik nicht gut genug (really)
        // TODO: wenn zb ein TIle kein next hat, dann den typ anschauen. HOME-Tiles haben kein next,
        // TODO: und ein GOAL (das hinterste) auch nicht
        // TODO: man muss hier prüfen, ob ein zug legal ist, nicht in den Piece und Tile klassen
        if (playerController.getHumanPlayer().isDiceRolled()) {
            int diceRoll = playerController.getHumanPlayer().getLastDiceRoll();

            // man muß nicht unbedingt einen rausziehen oder ist das pflicht?
            if (diceRoll == 6) {
                playerController.getHumanPlayer().putAvailableHomePieceOut();
            } else {
                clickedTile.getPiece().moveBy(diceRoll);
            }
            playerController.getHumanPlayer().setDiceRolled(false);
        }
        else {
            // TODO
        }
    }

    public void startGame() {
        gameStarted = true;
    }

    public void playGame() {
        while (gameStarted == true) {
            if (checkForWin() == -1) {
                //no winner
            }else {
                gameStarted = false;
            }

        }
    }

    public Boolean isGameStarted() {
        return gameStarted;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }
    public Playfield getPlayfield() {
        return playfield;
    }

    public void nextPlayer() {
        if (currentPlayer < playerController.getAllPlayers().size()) {
            currentPlayer++;
        }else {
            currentPlayer = 0;
        }
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


}
