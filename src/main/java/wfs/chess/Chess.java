/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * A Java implementation of the classic game of chess. Most of 
 * the game functionality lies in the other classes, like Game, 
 * TitleScreen, Square, and Piece.
 *-----------------------------------------------------------*/
package wfs.chess;

import wfs.chess.board.TitleScreen;

public class Chess {
    public static void main(String[] args) {
        System.out.println("--=== Welcome to Chess! ===--");

        TitleScreen title = new TitleScreen();
        title.setVisible(true);

        System.out.println("Please select the turn length and the color of pieces you'd like to play with...");       
    }
}
