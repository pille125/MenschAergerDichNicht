package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Player player = new Player("Human", 0);
        PlayerController playerController = new PlayerController(player);
        Playfield playfield = new Playfield();

        Game game = new Game(playerController, playfield);

        GUI.getGUI().startGUI();
    }
}
