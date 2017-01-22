package com.company.controller;

import com.company.model.Playfield;

/**
 * Created by pille125 on 22.01.17.
 */
public class Game {
    private Boolean gameStarted = false;
    private int currentPlayer;
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


}
