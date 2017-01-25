package com.company.src;// Generated from src/PlayField.g by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PlayFieldParser}.
 */
public interface PlayFieldListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PlayFieldParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(PlayFieldParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link PlayFieldParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(PlayFieldParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link PlayFieldParser#row}.
	 * @param ctx the parse tree
	 */
	void enterRow(PlayFieldParser.RowContext ctx);
	/**
	 * Exit a parse tree produced by {@link PlayFieldParser#row}.
	 * @param ctx the parse tree
	 */
	void exitRow(PlayFieldParser.RowContext ctx);
	/**
	 * Enter a parse tree produced by {@link PlayFieldParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(PlayFieldParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link PlayFieldParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(PlayFieldParser.ValueContext ctx);
}