package com.company.src;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// Main = CodeGenerator with antlr classes
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException { // Get CSV lexer

        // Get a list of matched tokens
        PlayFieldLexer lexer = new PlayFieldLexer(new ANTLRInputStream(new FileReader("csv_files/playfield4.csv")));
        // Pass the tokens to the parser
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PlayFieldParser parser = new PlayFieldParser(tokens);
        // Specify our entry point
        PlayFieldParser.FileContext fileContext = parser.file();
        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();


        // parse meta
        PlayFieldMetaListener metaListener = new PlayFieldMetaListener();
        walker.walk(metaListener, fileContext);

        // parse semantic
        PlayFieldSemanticListener semanticListener = new PlayFieldSemanticListener(metaListener);
        walker.walk(semanticListener, fileContext);
    }
}
