package com.company.controller;

import com.company.model.*;
import com.company.view.GUI;
import com.company.view.PlayfieldPanel;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;


public class Game {

    // mvc
    private PlayerController playerController;
    private Playfield playfield;
    private GUI gui;

    // game logic
    private Boolean gameStarted = false;
    private int currentPlayerID = 0;
    private Player currentPlayer = null;
    private boolean isHumanPlayersTurn = false;
    private boolean isDiceRolled = false;
    private int diceRoll = 0;

    // singleton
    private static Game instance = null;
    private Game() {}
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void init(PlayerController playerController, Playfield playField, GUI gui) {
        this.playerController = playerController;
        this.playfield = playField;
        this.gui = gui;

        if (gui != null) {
            gui.init(this);
            gui.start();
        }
    }

    public void onStartButtonClicked(ActionEvent event) {
        gui.disableStartGame();
        startGame();
    }

    public void onRollDiceButtonClicked(ActionEvent event) {
        gui.disableDiceRoll();
        rollDice();

        if (cantMoveOut()) {
            prepareNextPlayer();
        }
        turn();
    }

    public void onMouseClicked(MouseEvent event, PlayfieldPanel panel) {
        if (!isGameStarted() ||
                !isHumanPlayersTurn ||
                !isDiceRolled)
            return;

        // grab input
        int row    = event.getY() / panel.getSizePerPiece();
        int column = event.getX() / panel.getSizePerPiece();
        if (row    < 0 || row    >= playfield.getNumRows() ||
            column < 0 || column >= playfield.getNumColumns())
            return;

        System.out.println("row: " + row + " column: " + column);

        // only try a move if player hit a piece
        Tile clickedTile = playfield.getTile(row, column);
        if (!clickedTile.hasPiece())
            return;

        // player rolled dice, click on a own piece to move if its legal
        Piece clickedPiece = clickedTile.getPiece();
        if (clickedPiece.getOwner() != currentPlayer) // not our piece
            return;

        if (tryMove(clickedPiece)) {

            if (diceRoll == 6) {
                prepareBonusRoll();
                turn();
            } else {
                prepareNextPlayer();
                turn();
            }
        }
    }

    public void startGame() {
        System.out.println("GAME STARTED");

        for (Player player : playerController.getAllPlayers()) {
            player.resetPieces();
        }

        // initialize a new game
        gameStarted = true;
        diceRoll = -1;
        isDiceRolled = false;
        currentPlayerID = 0; // player ids start at 1
        prepareNextPlayer();
        turn();
    }

    public void endGame(Player player) {
        System.out.println("Player " + player.getPlayerID() + " has won! kthxbye ...");
        gameStarted = false;
    }

    // all in home, no 6
    private boolean cantMoveOut() {
        return diceRoll != 6 && !currentPlayer.hasPieceOut();
    }

    public void turn() {
        gui.repaintPlayfield();

        for (Player player : playerController.getAllPlayers()) {
            if (player.hasWon()) {
                endGame(player);
                return;
            }
        }

        System.out.println("turn player " +
                playerController.getAllPlayers().get(currentPlayerID - 1).getName() + " " +
                (isHumanPlayersTurn ? "HUMAN " : "KI ") +
                gui.getPlayerColor(currentPlayerID)
        );

        if (isHumanPlayersTurn) {
            return; // mouse and button actions will resolve turn
        }
        else {
            rollDice();
            turnKI();
        }
    }

    private void turnKI() {
        // all in home, no 6
        if (diceRoll != 6 && !currentPlayer.hasPieceOut()) {
            prepareNextPlayer();
            turn();
            return;
        }

        // fake a mouse click order
        for (Piece piece : ((KIPlayer)currentPlayer).getPieceOrderForMove()) {
            if (tryMove(piece)) {

                if (diceRoll == 6) {
                    prepareBonusRoll();
                } else {
                    prepareNextPlayer();
                }
                turn();
                return;
            }
        }
        // KI couldnt move with any piece
        prepareNextPlayer();
        turn();

    }
    // applies the rules and returns the success
    public boolean tryMove(Piece piece) {

        // no 6 roll
        if (diceRoll != 6) {

            // only out pieces and valid moves
            if (piece.getTile().getType() == TileType.HOME ||
                    !isValidTarget(piece, diceRoll)) {
                return false;
            }

            piece.moveBy(diceRoll);
            return true;
        }

        // 6 roll
        if (diceRoll == 6) {
            Player owner = piece.getOwner();

            // must move out rule
            if (owner.hasPieceInHome() &&
                    !owner.getStartTile().hasPiece()) {

                if (piece.getTile().getType() == TileType.HOME) {
                    piece.moveBy(1);
                    return true;
                } else {
                    return false;
                }
            }
            // normal move
            else if (piece.getTile().getType() != TileType.HOME &&
                    isValidTarget(piece, diceRoll)) {
                piece.moveBy(diceRoll);
                return true;
            }
        }

        return false;
    }

    private void prepareBonusRoll () {
        diceRoll = -1;
        isDiceRolled = false;
        if (isHumanPlayersTurn) {
            gui.enableDiceRoll();
        } else {
            gui.disableDiceRoll();
        }
    }

    public void prepareNextPlayer() {
        diceRoll = -1;
        isDiceRolled = false;
        currentPlayerID = (currentPlayerID + 1) % (playerController.getAllPlayers().size() + 1);
        if (currentPlayerID == 0) {
            currentPlayerID = 1;
            System.out.println("\nNEXT ROUND");
        }

        currentPlayer = playerController.getAllPlayers().get(currentPlayerID - 1);
        isHumanPlayersTurn = playerController.isHumanPlayer(currentPlayerID - 1);
        if (isHumanPlayersTurn) {
            gui.enableDiceRoll();
        } else {
            gui.disableDiceRoll();
        }
    }

    // checks if its legal for a piece to move to a target tile
    public boolean isValidTarget(Piece piece, int diceRoll) {
        Tile targetTile = piece.getTargetTile(diceRoll);

        if (targetTile == null) // run over last goal
            return false;

        if (!targetTile.hasPiece()) // free target, nice
            return true;

        return targetTile.getPiece().getOwner().getPlayerID() != currentPlayerID; // player would hit himself
    }

    public int checkForWin() {
        for (Player player: playerController.getAllPlayers()) {
            boolean playerWin = true;
            for (Piece piece : player.getPieces()) {
                if (piece.getTile().getType() != TileType.GOAL) {
                    playerWin = false;
                    break;
                }
            }

            if (playerWin) {
                return player.getPlayerID();
            }
        }
        return -1;
    }

    public void rollDice() {
        diceRoll = (int)(Math.random() * 6 + 1);
        isDiceRolled = true;
        System.out.println("\t ... es wurde eine " + diceRoll + " gewürfelt");
    }

    public Boolean isGameStarted() {
        return gameStarted;
    }

    public Playfield getPlayfield() {
        return playfield;
    }

    public int getDiceRoll() { return diceRoll;}

}
