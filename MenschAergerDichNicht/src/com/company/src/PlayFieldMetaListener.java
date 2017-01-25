package com.company.src;

import java.util.ArrayList;

public class PlayFieldMetaListener extends PlayFieldBaseListener {

    // to be filled
    private int numRows    = -1;
    private int numColumns = -1;
    private int numPlayers = 0;

    // helper
    private int row        = -1;
    private int column     = -1;
    private int maxPlayers = 4;

    private ArrayList<ArrayList<String>> playfield;
    private ArrayList<ArrayList<String>> players;
    private int numPiecesPerPlayer[];


    private void throwError(String what, String info) throws Error {
        // dont befuddle the main code with string deserts
        switch (what) {
            case "ROWS"             : throw new Error("row differs in size to preceding row");
            case "INVALID_PLAYER"   : throw new Error("invalid player" + info + " found");
            case "DUPLICATE_START"  : throw new Error("player"+ info +" has duplicate start field");
            case "TOO_LESS_PLAYERS" : throw new Error("not enough players defined, just "+ info);
            case "NO_PLAYER_PIECES" : throw new Error("player "+ info +" has no pieces");
            default:
                throw new Error(what);
        }
    }

    @Override public void enterFile(PlayFieldParser.FileContext ctx) {
        // reset
        numRows    = -1;
        numColumns = -1;
        row        = -1;
        column     = -1;
        playfield  = new ArrayList<>();
        players    = new ArrayList<>();
        for (int i=0; i < maxPlayers; i++)
            players.add(new ArrayList<String>());
    }

    @Override public void enterValue(PlayFieldParser.ValueContext ctx) {
        String fieldValue = ctx.children.get(0).getText();

        playfield.get(row).add(fieldValue); // collect playfield until file read
        column++;

        String tileType = "" + fieldValue.charAt(0);
        int playerNumber = -1;

        switch (tileType) {
            case "N":
                break;
            case "W":
                break; // nothing to do

            case "S":
                playerNumber = Integer.parseInt("" + fieldValue.charAt(1)) - 1;
                if (hasStartField(playerNumber))
                    throwError("DUPLICATE_START", "" + playerNumber);  // only one start field per player

                players.get(playerNumber).add(tileType);
                break;
            case "H":
                playerNumber = Integer.parseInt("" + fieldValue.charAt(1)) - 1;
                players.get(playerNumber).add(tileType);
                break;
            case "G":
                playerNumber = Integer.parseInt("" + fieldValue.charAt(1)) - 1;
                players.get(playerNumber).add(tileType);  // collect player specific data until file read
                break;
        }
    }

    @Override public void enterRow(PlayFieldParser.RowContext ctx) {
        playfield.add(new ArrayList<>());
        row++;

    }

    @Override public void exitRow(PlayFieldParser.RowContext ctx) {
        if (numColumns == -1)
            numColumns = column;
        else if (numColumns != column)
            throwError("ROWS", column +"doesnt equal"+ numColumns);

        column = -1; // reset for next row
    }

    @Override public void exitFile(PlayFieldParser.FileContext ctx) {

        // grab size of playfield
        numRows = row + 1;
        numColumns++;

        // check valid players
        for (int i=0; i<maxPlayers; i++) {
            if (players.get(i).size() == 0) // undefined player, ok
                continue;

            System.out.println(players.get(i));
            // if player is not empty, it MUST be valid
            if (hasStartField(i) &&             // has exactly one
                players.get(i).size() >= 3 &&   // one home, start and goal minimum
                hasSameHomeGoalFields(i)) {     // home and goal must match
                numPlayers++;
            }
            else
                throwError("INVALID_PLAYER", "" + i);
        }

        numPiecesPerPlayer = new int[numPlayers];
        for (int i=0; i<numPlayers; i++) {
            numPiecesPerPlayer[i] = countHomeFields(players.get(i));
        }
        for (int i=0; i<numPlayers; i++) {
            if (numPiecesPerPlayer[i] == 0)
                throwError("NO_PLAYER_PIECES", "" + i);
        }

        if (numPlayers < 2)
            throwError("TOO_LESS_PLAYERS", "" + numPlayers);

        System.out.println("If no error outputs occurred, then the following file has valid format:");
        System.out.println(ctx.getText());
    }

    private int countHomeFields(ArrayList<String> player) {
        int num = 0;
        for (int i=0; i<player.size(); i++)
            if (player.get(i).equals("H"))
                num++;

        return num;
    }

    private boolean hasStartField(int playerNumber) {
        ArrayList<String> player = players.get(playerNumber);
        for (int i=0; i<player.size(); i++)
            if (player.get(i).equals("S"))
                return true;

        return false;
    }

    private boolean hasSameHomeGoalFields(int playerNumber) {
        return countTileType(players.get(playerNumber), "H") ==
               countTileType(players.get(playerNumber), "G");
    }

    private int countTileType(ArrayList<String> player, String type) {
        int num = 0;
        for (int i=0; i<player.size(); i++)
            if (player.get(i).equals(type))
                num++;

        return num;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public int[] getNumPiecesPerPlayer() {
        return numPiecesPerPlayer;
    }

    public ArrayList<ArrayList<String>> getPlayfield() {
        return playfield;
    }
}
