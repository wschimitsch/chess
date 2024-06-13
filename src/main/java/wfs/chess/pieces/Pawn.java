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
     * Pawn can move 2 squares forward on first move, so keep track of if this Pawn has moved yet.
     */
    private int increment = 2;
    /*
     * Pawn constructor. Calls parent Piece constructor and sets the display image.
     */
    public Pawn(boolean white, boolean playerColor) {
        super(white, playerColor); // call parent constructor
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
     * move function for Pawn. Pawn can move forwards or, if taking a piece, diagonally forward.
     */
    @Override
    public boolean move(Square start, Square dest, Square[][] squares) {
        if (!isMove(start, dest, squares)) { // check for basic move requirements
            return false;
        } 
        increment = 1;
        return this.makeMove(start, dest);
        
    }
    @Override
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        if (!super.isMove(start, dest, squares)) {
            return false;
        }

        if ((isPlayerPiece && (dest.py+increment < start.py || start.py < dest.py)) || (!isPlayerPiece && (dest.py-increment > start.py || start.py > dest.py))) { // if invalid pawn move
            return false;
        }
        // Pawn must be moving forward if moving to an empty square
        if (dest.getPiece() == null) {
            if (start.px != dest.px) { 
                return false;
            }
        } else if (start.px == dest.px) { // If moving to an occupied square, pawn must be moving diagonally
            return false;
        }
        // Prevent pawn from jumping over other pieces
        if (increment == 2 && ((isPlayerPiece && squares[start.py-1][start.px].getPiece() != null) || (!isPlayerPiece && squares[start.py+1][start.px].getPiece() != null))) { 
            return false;
        }
        return true;
    }
    /*
     * Pawn threatens squares on its front diagonals.
     */
    @Override
    public boolean isAttackingKing(Square[][] squares) { 
        return false;
    }
}
