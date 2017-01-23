package com.company.controller;

import com.company.model.Playfield;
import com.company.model.Tile;
import com.company.model.TileType;
import com.company.model.Piece;


/**
 * Created by pille125 on 22.01.17.
 */
public class Game {
    private Boolean gameStarted = false;
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

    public int checkForWin() {
        for (Player player: playerController.getAllPlayers()) {
            Boolean playerWin = true;
            for (Piece piece : player.getPieces()) {
                if (piece.isFinished() != true) {
                    playerWin = false;
                }
            }
            if (playerWin == true) {
                return player.getPlayerNumber();
            }
        }
        return -1;
    }


}
