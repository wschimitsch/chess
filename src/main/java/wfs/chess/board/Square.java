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
    public int px; 
    public int py;    
    /*
     * Square constuctor. Initialize piece to null (empty square) and set the coordinates
     * based on function arguments. 
     */
    public Square(int x, int y) {
        this.pc = null;
        this.px = x;
        this.py = y;
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
     * equals function to compare different Square objects.
     */
    public boolean equals(Square sq) {
        if (this.px == sq.px && this.py == sq.py) {
            return true;
        } else {
            return false;
        }
    }
    /*
     * checkForKing function to enforce rule that Kings cannot move next to each other.
     */
    public boolean checkForKing(Square[][] squares, int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (py+i > 7 || py+i < 0 || px+j > 7 || px+j < 0|| (py+i == y && px+j == x)) { // ensure valid indices
                    continue;
                }
                if (squares[py+i][px+j].getPiece() instanceof King) { // check neighboring squares for a King
                    return true;
                }
            }
        } 
        return false;
    }
}