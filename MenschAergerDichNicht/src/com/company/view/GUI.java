package com.company.view;

import com.company.controller.Game;
import com.company.model.Playfield;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

/**
 * Created by pille125 on 09.01.17.
 */
public class GUI {
    private static final Dimension WINDOW_MIN_DIM = new Dimension(600,650);

    private static GUI gui = null;

    private JFrame window = null;
    private PlayfieldPanel playfieldPanel = null;
    private Game game = null;
    private Playfield playfield = null;

    public static GUI getGUI(Game game) {
        if(GUI.gui == null) {
            GUI.gui = new GUI(game);
        }
        return GUI.gui;
    }

    private GUI(Game game) {
        this.game = game;
        this.playfield = game.getPlayfield();

        // setup GUI container
        this.window = new JFrame("Mensch Aerger dich nicht!");
        this.window.setMinimumSize(WINDOW_MIN_DIM);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup panel in container
        setupPlayfieldPanel();
        window.add(this.playfieldPanel);

        // render
        window.getContentPane().validate();
        window.getContentPane().repaint();
        //repaintPlayfield();
    }

    private void setupPlayfieldPanel() {
        this.playfieldPanel = new PlayfieldPanel(game, playfield);

        int size = Math.min(this.playfieldPanel.getWidth(), this.playfieldPanel.getHeight());
        Dimension dim = new Dimension(
                size - (size % this.playfield.getNumColumns()),
                size - (size % this.playfield.getNumRows())
        );
        this.playfieldPanel.setPreferredSize(dim);
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
}


