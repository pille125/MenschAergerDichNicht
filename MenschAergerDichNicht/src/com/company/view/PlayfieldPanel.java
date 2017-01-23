package com.company.view;

import com.company.controller.Player;
import com.company.controller.Game;
import com.company.controller.PlayerController;
import com.company.model.Piece;
import com.company.model.Playfield;
import com.company.model.Tile;
import com.company.model.TileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by pille125 on 09.01.17.
 */

class PlayfieldPanel extends JPanel implements MouseListener {
    private Point[] positionPoints = null;

    private JButton startButton = null;
    private JButton rollDiceButton = null;
    private int[][] playfield = null;

    public PlayfieldPanel(Playfield pfModel) {
        this.setBackground(Color.WHITE);

        // read the generated field
        playfield = new int[pfModel.getSizex()][pfModel.getSizey()];

        // map from model to gui constants
        for (int i = 0; i < this.playfield.length; i++) {
            for (int j = 0; j < playfield[i].length; j++) {

                Tile tile = pfModel.getTile(i, j);
                switch (tile.getType()) {

                    case NONE:
                        playfield[i][j] = 0;
                        break;
                    case WAY:
                        playfield[i][j] = 1;
                        break;

                    // TODO: jeder spielfeldtyp sollte andere ids bekommen (und zusätzlich pro player)
                    case HOME:
                        playfield[i][j] = tile.getPlayerID();
                        break;
                    case GOAL:
                        playfield[i][j] = tile.getPlayerID();
                        break;
                    case START:
                        playfield[i][j] = tile.getPlayerID();
                        break;
                    case TOGOAL:
                        playfield[i][j] = 1;
                        break;
                }
            }
        }

        /* wenn die genierung klappt, kann das weg
        this.playfield = new int[][]{
                {2, 2, 0, 0, 1, 1, 3, 0, 0, 3, 3},
                {2, 2, 0, 0, 1, 3, 1, 0, 0, 3, 3},
                {0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0},
                {2, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 0, 4, 4, 4, 4, 1},
                {1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 4},
                {0, 0, 0, 0, 1, 5, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 5, 1, 0, 0, 0, 0},
                {5, 5, 0, 0, 1, 5, 1, 0, 0, 4, 4},
                {5, 5, 0, 0, 5, 1, 1, 0, 0, 4, 4}
        };
        */

        this.positionPoints = new Point[]{
                new Point(4, 0), new Point(4, 1), new Point(4, 2), new Point(4, 3), new Point(4, 4), new Point(3, 4),
                new Point(2, 4), new Point(1, 4), new Point(0, 4), new Point(0, 5), new Point(0, 6), new Point(1, 6),
                new Point(2, 6), new Point(3, 6), new Point(4, 6), new Point(4, 7), new Point(4, 8), new Point(4, 9),
                new Point(4, 10), new Point(5, 10), new Point(6, 10), new Point(6, 9), new Point(6, 8), new Point(6, 7),
                new Point(6, 6), new Point(7, 6), new Point(8, 6), new Point(9, 6), new Point(10, 6), new Point(10, 5),
                new Point(10, 4), new Point(9, 4), new Point(8, 4), new Point(7, 4), new Point(6, 4), new Point(6, 3),
                new Point(6, 2), new Point(6, 1), new Point(6, 0), new Point(5, 0), new Point(5, 1), new Point(5, 2),
                new Point(5, 3), new Point(5, 4), new Point(1, 5), new Point(2, 5), new Point(3, 5), new Point(4, 5),
                new Point(5, 6), new Point(5, 7), new Point(5, 8), new Point(5, 9), new Point(6, 5), new Point(7, 5),
                new Point(8, 5), new Point(9, 5)
        };
        this.startButton = new JButton("Spiel Starten");
        this.startButton.setToolTipText("Spiel starten.");
        this.startButton.setEnabled(true);
        this.startButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Game game = Game.getInstance();
                        game.startGame();
                    }
                }
        );
        add(startButton);

        this.rollDiceButton = new JButton("Würfeln");
        this.rollDiceButton.setToolTipText("Würfeln");
        this.rollDiceButton.setEnabled(true);
        this.rollDiceButton.addActionListener(
                // esko: das sollte hier nicht sein
                // die Game klasse ist der controller, und der sollte events von der ui bekommen (mvc)
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Game game = Game.getInstance();
                        int roll = game.getPlayerController().getHumanPlayer().rollDice();
                        System.out.println("Der Spieler hat eine " + roll + " gewürfelt" );
                        if (roll == 6) {
                            //Wenn möglich muss eine Spielfigur auf die start position
                        }else {

                            //warte auf MouseClick (defintition welche Spielfigur sich bewegen soll
                        }
                    }
                }
        );
        add(rollDiceButton);

        addMouseListener(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int length = this.getWidth() / 11;
        Graphics2D g2 = (Graphics2D) g;
        Color tmpColor = g2.getColor();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < this.playfield.length; i++) {
            for (int j = 0; j < playfield[i].length; j++) {
                Color currentColor = getFieldColor(playfield[i][j]);

                if (currentColor != null) {
                    g2.setColor(currentColor);
                    g2.fillOval(j * length + length / 10, i * length + length / 10, length - length / 5, length - length / 5);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(j * length + length / 10, i * length + length / 10, length - length / 5, length - length / 5);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            Color currentColor = getFieldColor(i + 2);
            g2.setColor(currentColor.darker().darker());
            PlayerController playerController = PlayerController.getInstance();

            int onStartCounter = 0;

            Player player = playerController.getAllPlayers().elementAt(i);
            for (Piece piece : player.getPieces()) {
                if (piece.getPosition() > -2) {
                    Point position = new Point();
                    if (piece.getPosition() == -1) {
                        switch (player.getPlayerNumber()) {
                            case 0:
                                position.x = 0;
                                position.y = 0;
                                break;
                            case 1:
                                position.x = 9;
                                position.y = 0;
                                break;
                            case 2:
                                position.x = 9;
                                position.y = 9;
                                break;
                            case 3:
                                position.x = 0;
                                position.y = 9;
                                break;
                            default:
                                position.x = 0;
                                position.y = 9;
                                break;
                        }
                        switch (onStartCounter) {
                            case 3:
                                position.y++;
                                break;
                            case 1:
                                position.x++;
                                break;
                            case 2:
                                position.x++;
                                position.y++;
                                break;
                            case 0:

                            default:
                                break;
                        }
                        onStartCounter++;
                    } else {
                        position.x = this.positionPoints[piece.getPosition()].y;
                        position.y = this.positionPoints[piece.getPosition()].x;
                    }
                    position.x = position.x * length;
                    position.y = position.y * length;
                    g2.setColor(currentColor.darker().darker());
                    g2.fillOval(position.x + length / 4, position.y + length / 4, length / 2, length / 2);
                    g2.setColor(currentColor.darker());
                    g2.fillOval(position.x + length / 3, position.y + length / 3, length / 3, length / 3);
                }
            }

        }
        g2.setColor(tmpColor);
    }

    private Color getFieldColor(int i) {
        Color currentColor = null;
        switch (i) {
            case 0:
                break;
            case 2:
                currentColor = Color.GREEN;
                break;
            case 3:
                currentColor = Color.RED;
                break;
            case 4:
                currentColor = Color.BLUE;
                break;
            case 5:
                currentColor = Color.YELLOW;
                break;
            case 1:
            default:
                currentColor = Color.WHITE;
                break;
        }
        return currentColor;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Game game = Game.getInstance();
        if (game.isGameStarted()) {
            if (game.getPlayerController().getHumanPlayer().isDiceRolled()) {
                if (game.getPlayerController().getHumanPlayer().getLastDiceRoll() == 6) {
                    //komm raus
                }
            }



            for(Piece piece: game.getPlayerController().getHumanPlayer().getPieces()) {
                int size = this.getWidth();
                Point position = new Point(e.getX() * 11 / size, e.getY() * 11 / size);
                position.setLocation(position.y, position.x);
                int player = game.getCurrentPlayer();
                //Eigentlich soll nachdem der Button "Würfeln" gedrückt wurde der nutzer auf die Figur Klicken die sich
                // bewegen soll und diese soll sich um die Würfelanzahl bewegen
                if (game.getPlayerController().getHumanPlayer().isDiceRolled() && game.getPlayerController().getHumanPlayer().getLastDiceRoll() == 6) {

                }
                if (piece.getPosition() != -1) {
                    if (this.positionPoints[piece.getPosition()].x == position.x && this.positionPoints[piece.getPosition()].y == position.y) {
                        System.out.println("test");
                    }
                }else {

                }

                System.out.println(position.y + " " + position.x);
            }
        }
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