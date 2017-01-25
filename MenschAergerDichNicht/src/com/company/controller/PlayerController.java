package com.company.controller;

import com.company.model.HumanPlayer;
import com.company.model.Player;

import java.util.Vector;

public class PlayerController {

    private Vector<Player> allPlayers = null;

    // singleton
    private static PlayerController instance;
    public static PlayerController getInstance() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }
    private PlayerController() {
        this.allPlayers = new Vector<Player>(0);
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
