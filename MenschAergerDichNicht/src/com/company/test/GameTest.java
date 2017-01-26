package com.company.test;

import com.company.controller.Game;
import com.company.controller.PlayerController;
import com.company.model.HumanPlayer;
import com.company.model.KIPlayer;
import com.company.model.Player;
import com.company.model.Playfield;
import com.company.model.Piece;
import com.company.view.GUI;

import static org.junit.Assert.*;

/**
 * Created by pille125 on 26.01.17.
 */
public class GameTest {
    public Game game;
    public PlayerController playerController;
    public Playfield playfield;

    @org.junit.Before
    public void setUp() throws Exception {
        game = Game.getInstance();
        playfield = Playfield.getInstance();
        playerController = PlayerController.getInstance();
        playerController.addPlayer(new HumanPlayer("test", 1, 3, playfield));
        playerController.addPlayer(new KIPlayer("test2", 2, 3, playfield));
        game.init(playerController, playfield, GUI.getInstance());
    }

    @org.junit.After
    public void tearDown() throws Exception {
        game = null;
        playfield = null;
        playerController = null;
    }

    @org.junit.Test
    public void startGame() throws Exception {
        game.startGame();
        assertTrue("Game failed to start",game.isGameStarted());
    }

    @org.junit.Test
    public void endGame() throws Exception {
        game.endGame(playerController.getAllPlayers().elementAt(0));
        assertTrue("Game failed to end",!game.isGameStarted());
    }

    @org.junit.Test
    public void turn() throws Exception {

    }

    @org.junit.Test
    public void tryMove() throws Exception {
        for (Piece piece : playerController.getHumanPlayer().getPieces()) {
            game.rollDice();
            if (game.getDiceRoll() == 6) {
                Boolean tryMove = game.tryMove(piece);
                assertTrue("move failed (piece didnt go out)",tryMove);
            }else {
                Boolean tryMove = game.tryMove(piece);
                assertTrue("move failed (move shouldnt be possible)",!tryMove);
            }

        }


    }

    @org.junit.Test
    public void prepareNextPlayer() throws Exception {

    }

    @org.junit.Test
    public void isValidTarget() throws Exception {
        for (Piece piece : playerController.getHumanPlayer().getPieces()) {
            for (int i=1; i<7; i++) {
                Boolean isValid = game.isValidTarget(piece, i);
                assertTrue("isValidTarget failed", isValid);
            }
            Boolean isValid = game.isValidTarget(piece, 100);
            assertTrue("isValidTarget failed", !isValid);
        }
    }

    @org.junit.Test
    public void checkForWin() throws Exception {

    }

    @org.junit.Test
    public void rollDice() throws Exception {
        game.rollDice();
        int dice = game.getDiceRoll();
        assertTrue("diceRoll failed (to big)", dice < 7);
        assertTrue("diceRoll failed (to small)", dice > 0);
    }

}