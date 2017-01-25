package com.company.view;

import com.company.controller.Game;
import com.company.model.Playfield;

import javax.swing.*;
import java.awt.*;


public class GUI {

    private static final Dimension WINDOW_MIN_DIM = new Dimension(600,650);
    private JFrame window = null;
    private PlayfieldPanel playfieldPanel = null;
    private Game game = null;
    private Playfield playfield = null;

    // singleton
    private static GUI instance = null;
    private GUI() {}
    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
        }
        return instance;
    }

    public void init(Game game) {
        this.game = game;
        this.playfield = game.getPlayfield();

        // setup GUI container
        this.window = new JFrame("Mensch aerger dich nicht!");
        this.window.setMinimumSize(WINDOW_MIN_DIM);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup panel in container
        setupPlayfieldPanel();
        window.add(this.playfieldPanel);

        // render
        window.getContentPane().validate();
        window.getContentPane().repaint();
    }

    private void setupPlayfieldPanel() {
        this.playfieldPanel = new PlayfieldPanel(game, playfield);

        int size = Math.min(this.playfieldPanel.getWidth(), this.playfieldPanel.getHeight());
        int width = this.playfieldPanel.getWidth();
        int height = this.playfieldPanel.getHeight();

        Dimension dim = new Dimension(
                width  - (width % this.playfield.getNumColumns()),
                height - (height % this.playfield.getNumRows())
        );
        this.playfieldPanel.setSize(dim);
        this.playfieldPanel.validate();
    }

    public void startGUI(Game game) {
        this.game = game;
        this.window.pack();
        this.window.setVisible(true);
    }

    public void repaintPlayfield() {
        if (this.playfieldPanel != null) {
            this.playfieldPanel.repaint();
        }
    }

    public void enableDiceRoll() {
        playfieldPanel.enableDiceRoll();
    }

    public void disableDiceRoll() {
        playfieldPanel.disableDiceRoll();
    }

    public void enableStartGame() {
        playfieldPanel.enableStartGame();
    }

    public void disableStartGame() {
        playfieldPanel.disableStartGame();
    }

    public Color getPlayerColor(int playerID) {
        return playfieldPanel.getPlayerColor(playerID);
    }
}
