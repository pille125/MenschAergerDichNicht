package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pille125 on 09.01.17.
 */
public class GUI {
    private static final Dimension WINDOW_MIN_DIM = new Dimension(600,650);

    private static GUI gui = null;

    private PlayfieldPanel playfield = null;
    private JFrame window = null;

    private GUI() {
        this.window = new JFrame("Mensch Aerger dich nicht!");
        this.window.setMinimumSize(WINDOW_MIN_DIM);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupPlayfield();

        window.add(this.playfield);
        window.getContentPane().validate();
        window.getContentPane().repaint();
        //repaintPlayfield();
    }

    public static GUI getGUI(){
        if(GUI.gui == null){
            GUI.gui = new GUI();
        }
        return GUI.gui;
    }

    public void startGUI() {
        this.window.pack();
        this.window.setVisible(true);

    }

    public void repaintPlayfield() {
        if (this.playfield != null) {
            this.playfield.repaint();
        }
    }

    private void setupPlayfield() {
        this.playfield = new PlayfieldPanel();


        int size = Math.min(this.playfield.getWidth(), this.playfield.getHeight());
        Dimension d = new Dimension(size-(size%11),size-(size%11));
        this.playfield.setPreferredSize(d);
        this.playfield.setSize(d);
        this.playfield.validate();


    }
}


