package com.company;

import com.company.controller.*;
import com.company.model.HumanPlayer;
import com.company.model.KIPlayer;
import com.company.model.Playfield;
import com.company.view.GUI;

public class Main {

    public static void main(String[] args) {

        // create the playfield
        Playfield playfield = Playfield.getInstance(); // uses generated code

        // create the players
        PlayerController playerController = PlayerController.getInstance();

        // atm just one human player
        playerController.addPlayer(new HumanPlayer("Human", 1, playfield.getNumPiecesOfPlayer(1), playfield));
        for (int i=0; i<playfield.getNumPlayers() - 1; i++) {
            playerController.addPlayer(new KIPlayer("KI", i+2, playfield.getNumPiecesOfPlayer(i+2), playfield));
        }

        // create the view
        GUI gui = GUI.getInstance();

        // create the game
        //Game game = new Game(playerController, playfield);
        Game game = Game.getInstance();
        game.init(playerController, playfield, gui);

        // connect game with gui
        //game.setGUI(gui);

        // lets go
        gui.startGUI(game);

    }
}
