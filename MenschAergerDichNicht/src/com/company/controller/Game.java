package com.company.controller;

import com.company.model.Playfield;
import com.company.model.Tile;
import com.company.model.TileType;

/**
 * Created by pille125 on 22.01.17.
 */
public class Game {
    private Boolean gameStarted = false;
    private Boolean gameEnded = true;
    private int currentPlayer = 0;
    private PlayerController playerController;
    private Playfield playfield;
    private static Game instance;

    public Game() {

    }

    public Game(PlayerController playerController, Playfield playField) {
        this.playerController = playerController;
        this.playfield = playField;
        instance = this;
    }

    public void startGame() {
        gameStarted = true;
        gameEnded = false;
    }

    public void playGame() {
        while (gameStarted == true && gameEnded == false) {
            if (!checkForWin()) {
                
            }

        }
    }

    public static Game getInstance()
    {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
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

    public Boolean checkForWin() {
        for (Tile[] tiles : playfield.getAllTiles()) {
            for (Tile tile : tiles) {
                if (tile.getType() == TileType.GOAL) {
                    //TODO //wie erfolgt die Auswertung auf einen gewinn?Prüfung ob alle pieces eines Spielers im tiletyp goal?

                }
            }
        }
        return false;

    }


}
