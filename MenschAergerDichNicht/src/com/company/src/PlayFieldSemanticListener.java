package com.company.src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class PlayFieldSemanticListener extends PlayFieldBaseListener {

    private int row     = -1;
    private int column  = -1;
    private String code = "";

    private ArrayList<ArrayList<String>> playfield = null;
    private int numRows    = -1;
    private int numColumns = -1;
    private int numPlayers = 0;
    private int[] numPiecesPerPlayer = null;


    private String generatedFileName = "gen/GEN_PlayfieldCreator";

    PlayFieldSemanticListener(PlayFieldMetaListener metaListener) {
        // grab meta data
        this.playfield  = metaListener.getPlayfield();
        this.numRows    = metaListener.getNumRows();
        this.numColumns = metaListener.getNumColumns();
        this.numPlayers = metaListener.getNumPlayers();
        this.numPiecesPerPlayer = metaListener.getNumPiecesPerPlayer();
    }

    private void throwError(String what, String info) throws Error {
        // dont befuddle the main code with string deserts
        switch (what) {
            case "OUT_OF_PLAYFIELD" : throw new Error("way tile "+ info +" points out of playfield");
            case "WAY_TO_HOME" : throw new Error("way tile "+ info +" points to a home");
            case "NOT_TO_GOAL" : throw new Error(" tile "+ info +" does not point to a goal tile");
            default:
                throw new Error(what);
        }
    }

    @Override public void enterFile(PlayFieldParser.FileContext ctx) {
        // begin class code
        code += "package com.company.model;\n\n" +
                "import com.company.model.Playfield;\n" +
                "import com.company.model.TileType;\n" +
                "import com.company.model.Tile;\n" +
                "public class GEN_PlayfieldCreator {\n" +
                // generate getters
                "public static int getNumRows() { return "+ this.numRows +";}" +
                "public static int getNumColumns() { return "+ this.numColumns +";}" +
                "public static int getNumPlayers() { return "+ this.numPlayers +";}" +
                "public static int getNumPiecesPerPlayer(int id) { if (id <= "+ numPlayers +") {" +
                "switch (id) {";
                for (int i=0; i<numPlayers; i++) {
                    code +=  "case "+ (i+1) +": return "+ numPiecesPerPlayer[i] +";";
                }
                code += "}}else throw new Error(\"player doesnt exist\");return -1;}" +
                // the big guy
                "\tpublic static void createPlayfield(Playfield playfield) {\n" +
                "playfield.setupTiles();\n";
    }

    @Override public void enterValue(PlayFieldParser.ValueContext ctx) {
        column++;

        String fieldValue = ctx.children.get(0).getText();

        // add dynamic code part
        switch (fieldValue) {

            case "NO":
                writeFirstTileCode(row, column, "NONE");
                code += ";\n";
                break;

            case "WN":
                writeFirstTileCode(row, column, "WAY");
                if (nextWayTileValid(row - 1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row-1) + ", " + column + "))";
                    code += ";\n";
                }
                break;
            case "WS":
                writeFirstTileCode(row, column, "WAY");
                if (nextWayTileValid(row + 1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row+1) + ", " + column + "))";
                    code += ";\n";
                }
                break;
            case "WE":
                writeFirstTileCode(row, column, "WAY");
                if (nextWayTileValid(row, column + 1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + row + ", " + (column+1) + "))";
                    code += ";\n";
                }
                break;
            case "WW":
                writeFirstTileCode(row, column, "WAY");
                if (nextWayTileValid(row, column - 1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + row + ", " + (column-1) + "))";
                    code += ";\n";
                }
                break;

            case "G1":
            case "G2":
            case "G3":
            case "G4":
                writeFirstTileCode(row, column, "GOAL");
                code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                code += ";\n";
                break;
            case "G1N":
            case "G2N":
            case "G3N":
            case "G4N":
                writeFirstTileCode(row, column, "GOAL");
                if (nextGoalTileValid(row - 1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row-1) + ", " + column + "))";
                    code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                    code += ";\n";
                }
                break;
            case "G1S":
            case "G2S":
            case "G3S":
            case "G4S":
                writeFirstTileCode(row, column, "GOAL");
                if (nextGoalTileValid(row + 1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row+1) + ", " + column + "))";
                    code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                    code += ";\n";
                }
                break;
            case "G1W":
            case "G2W":
            case "G3W":
            case "G4W":
                System.out.println(fieldValue);
                writeFirstTileCode(row, column, "GOAL");
                if (nextGoalTileValid(row, column-1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + row + ", " + (column-1) + "))";
                    code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                    code += ";\n";
                }
                break;
            case "G1E":
            case "G2E":
            case "G3E":
            case "G4E":
                writeFirstTileCode(row, column, "GOAL");
                if (nextGoalTileValid(row, column+1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + row + ", " + (column+1) + "))";
                    code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                    code += ";\n";
                }
                break;

            case "S1N":
            case "S2N":
            case "S3N":
            case "S4N":
                writeFirstTileCode(row, column, "START");
                if (nextWayTileValid(row - 1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row-1) + ", " + column + "))";
                    code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                    code += ";\n";
                }
                break;
            case "S1S":
            case "S2S":
            case "S3S":
            case "S4S":
                writeFirstTileCode(row, column, "START");
                if (nextWayTileValid(row + 1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row+1) + ", " + column + "))";
                    code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                    code += ";\n";
                }
                break;
            case "S1W":
            case "S2W":
            case "S3W":
            case "S4W":
                writeFirstTileCode(row, column, "START");
                if (nextWayTileValid(row, column-1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + row + ", " + (column-1) + "))";
                    code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                    code += ";\n";
                }
                break;
            case "S1E":
            case "S2E":
            case "S3E":
            case "S4E":
                writeFirstTileCode(row, column, "START");
                if (nextWayTileValid(row, column+1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + row + ", " + (column+1) + "))";
                    code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                    code += ";\n";
                }
                break;

            case "H1":
            case "H2":
            case "H3":
            case "H4":
                writeFirstTileCode(row, column, "HOME");
                code += ".setPlayerID(" + fieldValue.charAt(1) + ")";
                code += ";\n";
                break;

//            'WNGE'|'WSGE'|'WWGE'|'WEGN'|'WNGW'|'WSGW'|'WWGN'|'WEGS'|'WNGS'|'WSGN'|'WWGS'|'WEGW

            case "WNGE":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row-1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row-1) + ", " + column + "))";
                }
                if (nextGoalTileValid(row, column + 1, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + row + ", " + (column+1) + "))";
                    code += ";\n";
                }
                break;
            case "WSGE":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row+1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row+1) + ", " + column + "))";
                }
                if (nextGoalTileValid(row, column + 1, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + row + ", " + (column+1) + "))";
                    code += ";\n";
                }
                break;
            case "WWGE":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row, column-1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row) + ", " + (column-1) + "))";
                }
                if (nextGoalTileValid(row, column + 1, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + row + ", " + (column+1) + "))";
                    code += ";\n";
                }
                break;

            case "WEGN":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row, column+1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row) + ", " + (column+1) + "))";
                }
                if (nextGoalTileValid(row-1, column, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + (row-1) + ", " + (column) + "))";
                    code += ";\n";
                }
                break;
            case "WNGW":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row-1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row-1) + ", " + column + "))";
                }
                if (nextGoalTileValid(row, column - 1, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + row + ", " + (column-1) + "))";
                    code += ";\n";
                }
                break;
            case "WSGW":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row+1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row+1) + ", " + column + "))";
                }
                if (nextGoalTileValid(row, column - 1, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + row + ", " + (column-1) + "))";
                    code += ";\n";
                }
                break;

            case "WWGN":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row, column-1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row) + ", " + (column-1) + "))";
                }
                if (nextGoalTileValid(row-1, column, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + (row-1) + ", " + (column) + "))";
                    code += ";\n";
                }
                break;
            case "WEGS":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row, column+1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row) + ", " + (column+1) + "))";
                }
                if (nextGoalTileValid(row+1, column, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + (row+1) + ", " + (column) + "))";
                    code += ";\n";
                }
                break;
            case "WNGS":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row-1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row-1) + ", " + column + "))";
                }
                if (nextGoalTileValid(row+1, column, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + (row+1) + ", " + (column) + "))";
                    code += ";\n";
                }
                break;
            case "WSGN":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row+1, column, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row+1) + ", " + column + "))";
                }
                if (nextGoalTileValid(row-1, column, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + (row-1) + ", " + (column) + "))";
                    code += ";\n";
                }
                break;
            case "WWGS":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row, column-1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row) + ", " + (column-1) + "))";
                }
                if (nextGoalTileValid(row+1, column, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + (row+1) + ", " + (column) + "))";
                    code += ";\n";
                }
                break;
            case "WEGW":
                writeFirstTileCode(row, column, "TOGOAL");
                if (nextWayTileValid(row, column+1, "" + fieldValue.charAt(1))) {
                    code += ".setNext(playfield.getTile(" + (row) + ", " + (column+1) + "))";
                }
                if (nextGoalTileValid(row, column - 1, "" + fieldValue.charAt(1))) {
                    code += ".setGoal(playfield.getTile(" + row + ", " + (column-1) + "))";
                    code += ";\n";
                }
                break;
        }
    }

    @Override public void enterRow(PlayFieldParser.RowContext ctx) {
        row++;
    }

    @Override public void exitRow(PlayFieldParser.RowContext ctx) {
        column = -1; // reset for next row
    }

    @Override public void exitFile(PlayFieldParser.FileContext ctx) {
        System.out.println("If no error outputs occurred, then the following file has valid format:");
        System.out.println(ctx.getText());

        // finish class code
        code += "\t\t\n\t}\n}\n";

        // write code to a .java file
        System.out.println("Generating code ...");
        try {
            FileWriter out = new FileWriter(generatedFileName +".java", false);
            out.write(code);
            out.close();
            System.out.println(generatedFileName +".java created.");
        }
        catch (IOException ex) {
            System.out.println("Code generation failed, reason:");
            ex.printStackTrace();
        }
    }

    private boolean nextWayTileValid(int x, int y, String direction) {
        if (x < 0 || x >= numRows)
            throwError("OUT_OF_PLAYFIELD", "(" +x+", "+y+")");
        if (y < 0 || y >= numColumns)
            throwError("OUT_OF_PLAYFIELD", "(" +x+", "+y+")");
        if (playfield.get(x).get(y).charAt(0) == 'H')
            throwError("WAY_TO_HOME", "(" +x+", "+y+")");

        return true;
    }

    private boolean nextGoalTileValid(int x, int y, String direction) {
        if (x < 0 || x >= numRows)
            throwError("OUT_OF_PLAYFIELD", "(" +x+", "+y+")");
        if (y < 0 || y >= numColumns)
            throwError("OUT_OF_PLAYFIELD", "(" +x+", "+y+")");
        if (playfield.get(x).get(y).charAt(0) != 'G')
            throwError("NOT_TO_GOAL", "(" +x+", "+y+")");

        return true;
    }

    private void writeFirstTileCode(int x, int y, String type) {
        code += "playfield.getTile(" + x + ", " + y + ")";
        code += ".setType(TileType."+type+")";
    }
}
