package com.company.view;

import com.company.controller.Game;
import com.company.model.Playfield;
import com.company.model.Tile;
import com.company.model.TileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class PlayfieldPanel extends JPanel implements MouseListener {
    private JButton startButton = null;
    private JButton rollDiceButton = null;

    private Game game = null;
    private Playfield playfield = null;

    // for correct mouse clicks
    private int sizePerPiece = 0;

    public PlayfieldPanel(Game game, Playfield playfield) {
        this.game = game;
        this.playfield = playfield;

        setBackground(Color.WHITE);
        setLayout(null);

        startButton = new JButton("Spiel Starten");
        startButton.setToolTipText("Spiel starten.");
        startButton.setBounds(250,550,100,60);
        startButton.setEnabled(true);
        startButton.setVisible(true);
        add(startButton);

        rollDiceButton = new JButton("Würfeln");
        rollDiceButton.setToolTipText("Würfeln");
        rollDiceButton.setBounds(250,550,100,60);
        rollDiceButton.setEnabled(false);
        rollDiceButton.setVisible(false);
        add(rollDiceButton);

        addMouseListener(this);

        startButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.onStartButtonClicked(e);
                    }
                }
        );
        rollDiceButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.onRollDiceButtonClicked(e);
                    }
                }
        );
    }

    @Override
    protected void paintComponent(Graphics gfx) {
        super.paintComponent(gfx);

        Graphics2D gfx2D = (Graphics2D) gfx;
        Color oldColor = gfx2D.getColor();
        gfx2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paintPlayfield(gfx2D);

        gfx2D.setColor(oldColor);
    }

    private void paintPlayfield(Graphics2D gfx2D) {
        // update this always
        sizePerPiece = this.getWidth() / this.playfield.getNumColumns();
        
        for (int i = 0; i < this.playfield.getNumRows(); i++) {
            for (int j = 0; j < playfield.getNumColumns(); j++) {

                Tile tile = playfield.getTile(i, j);
                Color currentColor = getTileColor(tile);
                if (currentColor == null) // dont paint none tiles
                    continue;

                int posx = j * sizePerPiece + sizePerPiece / 10;
                int posy = i * sizePerPiece + sizePerPiece / 10;
                int rad = sizePerPiece - sizePerPiece / 5;

                int radius = sizePerPiece / 2;

                // paint a colored tile with black border
                gfx2D.setColor(currentColor);
                gfx2D.fillOval(posx, posy, rad, rad);
                gfx2D.setColor(Color.BLACK);
                gfx2D.drawOval(posx, posy, rad, rad);

                if (tile.getType() == TileType.START ||
                    tile.getType() == TileType.TOGOAL ) {

                    gfx2D.setColor(tile.getType() == TileType.TOGOAL ?
                            getPlayerColor(tile.getGoal().getPlayerID()) :
                            Color.BLACK
                    );
                    gfx2D.fillOval( // the method params are pure hell
                            j*sizePerPiece + sizePerPiece / 3 + 3,
                            i*sizePerPiece + sizePerPiece / 3 + 3,
                            sizePerPiece / 5, sizePerPiece / 5
                    );
                }

                // render pieces on tiles
                if (tile.getPiece() != null) {
                    paintPiece(gfx2D, tile, i, j, sizePerPiece);
                }
            }
        }
    }

    // hack, inperformant
    private Point findTilePos(Tile tile) {
        for (int i = 0; i < this.playfield.getNumRows(); i++) {
            for (int j = 0; j < playfield.getNumColumns(); j++) {
                if (tile == playfield.getTile(i, j)) {
                    return new Point(i, j);
                }
            }
        }
        return new Point(-1, -1);
    }

    private void paintPiece(Graphics2D gfx2D, Tile tile, int i, int j, int size) {
        Color currentColor = getPlayerColor(tile.getPiece().getOwner().getPlayerID());

        gfx2D.setColor(currentColor.darker());
        gfx2D.fillOval(j*size + size / 4, i*size + size / 4, size / 2, size / 2);
        gfx2D.setColor(currentColor.darker().darker());
        gfx2D.fillOval(j*size + size / 3, i*size + size / 3, size / 3, size / 3);
    }

    private Color getTileColor(Tile tile) {
        Color currentColor = null;
        switch (tile.getType()) {
            case NONE:
                break;
            case WAY:
            case TOGOAL:
                currentColor = Color.WHITE;
                break;
            case HOME:
            case START:
            case GOAL:
                currentColor = getPlayerColor(tile.getPlayerID());
                break;
        }
        return currentColor;
    }

    public Color getPlayerColor(int id) {
        Color color = Color.WHITE;
        if      (id == 1) color = Color.GREEN;
        else if (id == 2) color = Color.RED;
        else if (id == 3) color = Color.CYAN;
        else if (id == 4) color = Color.YELLOW;
        return color;
    }

    public int getSizePerPiece() {
        return sizePerPiece;
    }

    public void enableDiceRoll() {
        rollDiceButton.setEnabled(true);
        rollDiceButton.setVisible(true);
    }

    public void disableDiceRoll() {
        rollDiceButton.setEnabled(false);
        rollDiceButton.setVisible(false);
    }

    public void enableStartGame() {
        startButton.setEnabled(true);
        startButton.setVisible(true);
        disableDiceRoll();
    }

    public void disableStartGame() {
        startButton.setEnabled(false);
        startButton.setVisible(false);
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        game.onMouseClicked(event, this);
        GUI.getGUI(null).repaintPlayfield();
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}