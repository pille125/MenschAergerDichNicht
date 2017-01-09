package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pille125 on 09.01.17.
 */

class PlayfieldPanel extends JPanel{
    private int [][] playfield = null;
    private Point[] positionPoints = null;

    public PlayfieldPanel() {
        this.setBackground(Color.WHITE);

        this.playfield = new int[][] {
                { 2, 2, 0, 0, 1, 1, 3, 0, 0, 3, 3 },
                { 2, 2, 0, 0, 1, 3, 1, 0, 0, 3, 3 },
                { 0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0 },
                { 2, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1 },
                { 1, 2, 2, 2, 2, 0, 4, 4, 4, 4, 1 },
                { 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 4 },
                { 0, 0, 0, 0, 1, 5, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 1, 5, 1, 0, 0, 0, 0 },
                { 5, 5, 0, 0, 1, 5, 1, 0, 0, 4, 4 },
                { 5, 5, 0, 0, 5, 1, 1, 0, 0, 4, 4 }
        };

        this.positionPoints = new Point[] {
                new Point(4,0),new Point(4,1),new Point(4,2),new Point(4,3),new Point(4,4),new Point(3,4),
                new Point(2,4),new Point(1,4),new Point(0,4),new Point(0,5),new Point(0,6),new Point(1,6),
                new Point(2,6),new Point(3,6),new Point(4,6),new Point(4,7),new Point(4,8),new Point(4,9),
                new Point(4,10),new Point(5,10),new Point(6,10),new Point(6,9),new Point(6,8),new Point(6,7),
                new Point(6,6),new Point(7,6),new Point(8,6),new Point(9,6),new Point(10,6),new Point(10,5),
                new Point(10,4),new Point(9,4),new Point(8,4),new Point(7,4),new Point(6,4),new Point(6,3),
                new Point(6,2),new Point(6,1),new Point(6,0),new Point(5,0),new Point(5,1),new Point(5,2),
                new Point(5,3),new Point(5,4),new Point(1,5),new Point(2,5),new Point(3,5), new Point(4,5),
                new Point(5,6), new Point(5,7),new Point(5,8), new Point(5,9),new Point(6,5), new Point(7,5),
                new Point(8,5), new Point(9,5)
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int length = this.getWidth()/11;
        Graphics2D g2 = (Graphics2D)g;
        Color tmpColor = g2.getColor();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int i = 0; i< this.playfield.length;i++){
            for(int j = 0; j < playfield[i].length; j++) {
                Color currentColor = getFieldColor(playfield[i][j]);
                System.out.println("color" + currentColor);
                if (currentColor != null) {
                    g2.setColor(currentColor);
                    g2.fillOval(j * length + length / 10, i * length + length / 10, length - length / 5, length - length / 5);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(j * length + length / 10, i * length + length / 10, length - length / 5, length - length / 5);
                }
            }
        }
        g2.setColor(tmpColor);
    }

    private Color getFieldColor(int i) {
        Color currentColor = null;
        switch(i){
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
}
