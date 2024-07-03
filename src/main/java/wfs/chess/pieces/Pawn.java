/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * Pawn class to represent the corresponding piece in Chess.
 * Can only move forwards, but can attack/take diagonally.
 *-----------------------------------------------------------*/
package wfs.chess.pieces;

import java.awt.Image;
import javax.swing.ImageIcon;
import wfs.chess.board.Square;

public class Pawn extends Piece {
    /*
     * Pawn can move 2 squares forward on first move, so keep track of if this Pawn has moved yet,
     * and if it's the player's piece (to know which side of the board it is on)
     */
    private int increment = 2;
    protected boolean isPlayerPiece;

    /*
     * Pawn constructor. Calls parent Piece constructor and sets the display image.
     */
    public Pawn(boolean white, boolean playerColor) {
        super(white); // call parent constructor
        this.isPlayerPiece = (white == playerColor);
        if (white) { // set display image based on piece color
            icon = new ImageIcon(getClass().getResource("/img/w_pawn.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/img/b_pawn.png"));
        }
        Image curr = icon.getImage().getScaledInstance(Square.SIZE, Square.SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(curr);
        this.setIcon(icon);
    }

    /*
     * isMove validates that a move between the given squares is valid with a pawn. 
     */
    @Override
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        if (!super.isMove(start, dest, squares)) {
            return false;
        }
        if (hasMoved) {
            increment = 1;
        }
        int sx = start.getSquareX(), sy = start.getSquareY();
        int dx = dest.getSquareX(), dy = dest.getSquareY();
        if ((isPlayerPiece && (dy+increment < sy || sy < dy)) || (!isPlayerPiece && (dy-increment > sy || sy > dy))) { // if invalid pawn move
            return false;
        }
        // Pawn must be moving forward if moving to an empty square
        if (dest.getPiece() == null) {
            if (sx != dx) { 
                return false;
            }
        } else if (sx == dx) { // If moving to an occupied square, pawn must be moving diagonally
            return false;
        }
        // Prevent pawn from jumping over other pieces
        if (increment == 2 && ((isPlayerPiece && squares[sy-1][sx].getPiece() != null) || (!isPlayerPiece && squares[sy+1][sx].getPiece() != null))) { 
            return false;
        }
        return true;
    }
    
    /*
     * Pawn threatens squares on its front diagonals.
     */
    @Override
    public boolean isAttackingKing(Square kingSquare, Square[][] squares) {
        return isMove(this.position, kingSquare, squares);
    }
}
