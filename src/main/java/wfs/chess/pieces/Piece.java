/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * Abstract class to represent the basic functions of each Chess
 * piece. To be inherited by each respective piece class.
 * 
 * Source for piece images: https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces
 *-----------------------------------------------------------*/
package wfs.chess.pieces;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import wfs.chess.board.Square;

public abstract class Piece extends JLabel {
    /*
     * Piece properties:
     *      color
     *      alive/on board
     *      icon/display image
     *      current spot on board
     */
    protected boolean isWhite;
    protected boolean isAlive;
    protected boolean isPlayerPiece;
    protected boolean hasMoved;
    protected ImageIcon icon; 
    protected Square position;
    /*
     * Piece constructor. Sets the color to 
     * the function input and status to alive.
     */
    public Piece(boolean white, boolean playerColor) {
        this.isWhite = white;
        this.isPlayerPiece = (white == playerColor);
        this.isAlive = true;
        this.position = null;
    }
    /*
     * setSquare sets this square equal to the given square.
     */
    public void setSquare(Square sq) {
        this.position = sq;
    }
    /*
     * getColor returns the color of this piece.
     */
    public boolean getColor() {
        return isWhite;
    }
    /*
     * Basic check function for valid moves. Applicable for all pieces. Takes in
     * starting square, destination square, and 2D array of squares representing the board.
     * Moving from the current square to the current square is not a valid move. A piece
     * also cannot move to a square that has a piece of the same color on it. 
     * 
     * Children classes call this function to check basic requirements for a move, and
     * then have further specifications to check if the move is valid for that particular
     * piece.
     */
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        if (start.equals(dest)) {
            return false;
        }
        if (dest.getPiece() == null) {
            return true;
        } else if (this.getColor() == dest.getPiece().getColor()) {
            return false;
        } else {
            return true;
        }
        
    }
    /*
     * Move piece from one square to another.  
     */
    public boolean makeMove(Square start, Square dest) {
        if (dest.getPiece() == null) { // if destination square is empty, move there
            dest.setPiece(this);
            start.setPiece(null);
            return true;
        } else { // otherwise take the piece on the destination square
            kill(dest.getPiece(), dest);
            dest.setPiece(this);
            start.setPiece(null);
            return true;
        } 
    }
    /*
     * kill function to kill/take opposing pieces, if valid move.
     */
    public void kill(Piece pc, Square sq) {
        pc.isAlive = false;
        sq.setPiece(null);
        sq.remove(pc);
    }
    /*
     * isAttackingKing checks if this piece is threatening the enemy King.
     * Used to see if that King is in check/checkmate.
     */
    public abstract boolean isAttackingKing(Square[][] squares);
    /*
     * 
     */
    public abstract boolean move(Square start, Square dest, Square[][] squares);
}
