package com.company.controller;

import java.util.Vector;

public class PlayerController {
    private static PlayerController instance;
    private Vector<Player> allPlayers = null;

    private PlayerController() {
        this.allPlayers = new Vector<Player>(0);
    }

    public static PlayerController getInstance() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }

    public void addPlayer(Player player) {
        this.allPlayers.add(player);
    }

    public boolean isHumanPlayer(int playerID) {
        return allPlayers.get(playerID) instanceof HumanPlayer;
    }

    public Vector<Player> getAllPlayers() {
        return allPlayers;
    }

    public Player getHumanPlayer() {
        return allPlayers.elementAt(0);
    } // hack
}
