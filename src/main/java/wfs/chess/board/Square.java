/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * Square class to represent a square or spot on the chessboard.
 * Can be empty or can have a piece in it. Extends Java Swing
 * JPanel component.
 *-----------------------------------------------------------*/
package wfs.chess.board;

import javax.swing.JPanel;
import wfs.chess.pieces.Piece;
import wfs.chess.pieces.King;

public class Square extends JPanel {
    /*
     * Size of each square on the chess board, in pixels.
     */
    public static final int SIZE = 64;
    /*
     * The piece on this square.
     */
    private Piece pc;
    /*
     * Coordinates of the square. Visually, x represents the column of the square while
     * y represents the row of square, with respect to the chessboard.
     */
    private int x; 
    private int y;  

    /*
     * Square constuctor. Initialize piece to null (empty square) and set the coordinates
     * based on function arguments. 
     */
    public Square(int x, int y) {
        pc = null;
        this.x = x;
        this.y = y;
    }
    
    /*
     * getPiece function to return the piece on this square. 
     */
    public Piece getPiece() {
        return pc;
    }

    /*
     * setPiece function to set/change the piece on this square.
     */
    public void setPiece(Piece pc) {
        this.pc = pc;
    }

    /*
     * getSquareX function to return the square's x position. 
     */
    public int getSquareX() {
        return x;
    }

    /*
     * getSquareY function to return the square's y position. 
     */
    public int getSquareY() {
        return y;
    }

    /*
     * equals function to compare different Square objects.
     */
    public boolean equals(Square sq) {
        if (this.x == sq.getSquareX() && this.y == sq.getSquareY()) {
            return true;
        } else {
            return false;
        }
    }
    
    /*
     * checkForKing function to enforce rule that Kings cannot move next to each other.
     */
    public boolean checkForKing(int x, int y, Square[][] squares) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (y+i > 7 || y+i < 0 || x+j > 7 || x+j < 0|| (y+i == y && x+j == x)) { // ensure valid indices
                    continue;
                }
                if (squares[y+i][x+j].getPiece() instanceof King) { // check neighboring squares for a King
                    return true;
                }
            }
        } 
        return false;
    }
}