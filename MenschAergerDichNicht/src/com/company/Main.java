package com.company;

import com.company.controller.Game;
import com.company.controller.Player;
import com.company.controller.PlayerController;
import com.company.model.Playfield;
import com.company.view.GUI;

public class Main {

    public static void main(String[] args) {

        // create the playfield
        Playfield playfield = new Playfield();

        // create the players
        Player player = new Player("Human", 0, 4);
        PlayerController playerController = new PlayerController(player, 4);

        // create the game
        Game game = new Game(playerController, playfield);

        // lets go
        GUI.getGUI(playfield).startGUI();
    }
}
