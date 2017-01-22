package com.company.controller;

import java.util.Vector;

/**
 * Created by pille125 on 21.01.17.
 */
public class PlayerController {
    private static PlayerController instance;

    private Vector<Player> allPlayers = null;

    public PlayerController() {
        this.allPlayers = new Vector<Player>(4);
        for (int i=2; i<6; i++){
            this.allPlayers.add(new KIPlayer("KI" + i, i));
        }
    }

    public PlayerController(Player player) {
        this.allPlayers = new Vector<Player>(4);
        this.allPlayers.add(player);
        for (int i=1; i<4; i++){
            this.allPlayers.add(new KIPlayer("KI" + i, i));
        }
        instance = this;
    }

    public static PlayerController getInstance()
    {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }

    public Vector<Player> getAllPlayers() {
        return allPlayers;
    }

    public Player getHumanPlayer() {
        return allPlayers.elementAt(0);
    }
}
