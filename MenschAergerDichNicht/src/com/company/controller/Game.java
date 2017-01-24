package com.company.controller;

import com.company.model.Playfield;
import com.company.model.Piece;
import com.company.model.Tile;
import com.company.model.TileType;
import com.company.view.GUI;
import com.company.view.PlayfieldPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;


/**
 * Created by pille125 on 22.01.17.
 */
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

        // TODO: die mouse klicks werden prinzipiell richtig umgerechnet
        // TODO: allerdings ist getHeight() nicht richtig, wenn das spielfeld nicht genau passt
        // TODO: die mouse daten gehen bis zum "boden", die tiles aber nicht
        // TODO: dadurch sind die mouse x leicht verschoben (siehe das println)
        int row    = event.getY() * playfield.getNumRows() / panel.getHeight();
        int column = event.getX() * playfield.getNumColumns() / panel.getWidth();

        if (row    < 0 || row    >= playfield.getNumRows() ||
            column < 0 || column >= playfield.getNumColumns())
            return;

        System.out.println("mx: " + row + " my: " + column); // good for debug // TODO: hier
        Tile clickedTile = playfield.getTile(row, column);
        if (!clickedTile.hasPiece())
            return;

        // TODO: jetzt kannste hier die game logic einbauen, have fun,
        // TODO: wenn irgendwo exceptions kommen, ist die spiellogik nicht gut genug
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
