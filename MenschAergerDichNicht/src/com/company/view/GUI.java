package com.company.view;

import com.company.model.Playfield;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pille125 on 09.01.17.
 */
public class GUI {
    private static final Dimension WINDOW_MIN_DIM = new Dimension(600,650);

    private static GUI gui = null;

    private PlayfieldPanel playfieldPanel = null;
    private JFrame window = null;
    private Playfield playfield;

    private GUI(Playfield playfield) {
        this.playfield = playfield;

        // setup GUI container
        this.window = new JFrame("Mensch Aerger dich nicht!");
        this.window.setMinimumSize(WINDOW_MIN_DIM);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup panel in container
        setupPlayfield();
        window.add(this.playfieldPanel);

        // render
        window.getContentPane().validate();
        window.getContentPane().repaint();
        //repaintPlayfield();
    }

    public static GUI getGUI(Playfield playfield) {
        if(GUI.gui == null) {
            GUI.gui = new GUI(playfield);
        }
        return GUI.gui;
    }

    public void startGUI() {
        this.window.pack();
        this.window.setVisible(true);

    }

    public void repaintPlayfield() {
        if (this.playfieldPanel != null) {
            this.playfieldPanel.repaint();
        }
    }

    private void setupPlayfield() {
        this.playfieldPanel = new PlayfieldPanel(this.playfield);


        int size = Math.min(this.playfieldPanel.getWidth(), this.playfieldPanel.getHeight());
        Dimension d = new Dimension(size-(size%11),size-(size%11));
        this.playfieldPanel.setPreferredSize(d);
        this.playfieldPanel.setSize(d);
        this.playfieldPanel.validate();
    }
}


