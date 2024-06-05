
/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * King class to represent the corresponding piece in Chess.
 * Can only move one space at a time, unless castling with a
 * Rook.
 *-----------------------------------------------------------*/
package wfs.chess.pieces;

import java.awt.Image;
import javax.swing.ImageIcon;
import wfs.chess.board.Square;

public class King extends Piece {
    /*
     * King can castle if it hasn't moved yet, so keep track of this.
     */
    private boolean hasMoved = false;
    /*
     * King constructor. Calls parent constructor and sets the display image.
     */
    public King(boolean white, boolean playerColor) {
        super(white, playerColor); // call parent constructor
        if (white) { // set display image based on piece color
            icon = new ImageIcon(getClass().getResource("/img/w_king.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/img/b_king.png"));
        }
        Image curr = icon.getImage().getScaledInstance(Square.SIZE, Square.SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(curr);
        this.setIcon(icon);
    }
    /*
     * move function for King. King can move 1 square in any direction, or can castle.
     */
    @Override
    public boolean move(Square start, Square dest, Square[][] squares) {
        if (!super.move(start, dest, squares)) {
            return false;
        } 
        if (!hasMoved && isValidCastling(squares, start, dest)) {
            return true;
        }
        if ((dest.px > start.px+1) || (dest.px < start.px-1) || (dest.py > start.py+1 || dest.py < start.py-1)) { // cannot move King more than one square
            return false;
        }
        if (dest.getPiece() == null) { // if the destination square is empty, move to it
            if (dest.checkForKing(squares, start.px, start.py)) { // if there is a King neighboring this square it is an invalid move
                return false;
            }
            dest.setPiece(this);
            start.setPiece(null);
            return true;
        } else { // otherwise take the piece on it
            kill(dest.getPiece(), dest);
            dest.setPiece(this);
            start.setPiece(null);
            return true;
        }
    }
    /*
     * isValidCastling function to implement castling feature, whereby the King
     * can move two squares to safety while the rook on the respective side the
     * King is castling moves to the other side of King. 
     */
    public boolean isValidCastling(Square[][] squares, Square start, Square dest) {
        if (dest.px == start.px+2) { // rook would be on 7th column
            Piece pc = squares[start.py][7].getPiece();
            if (pc instanceof Rook && !pc.hasMoved && pc.move(squares[start.py][7], squares[start.py][dest.px-1], squares)) {
                dest.setPiece(this);
                start.setPiece(null);
                squares[start.py][7].remove(pc);
                squares[start.py][dest.px-1].add(pc);
                return true;
            }
            return false;
        } else if (dest.px == start.px-2) { // rook would be on 0th column
            Piece pc = squares[start.py][0].getPiece();
            if (pc instanceof Rook && !pc.hasMoved && pc.move(squares[start.py][0], squares[start.py][dest.px+1], squares)) {
                dest.setPiece(this);
                start.setPiece(null);
                squares[start.py][0].remove(pc);
                squares[start.py][dest.px+1].add(pc);
                return true;
            }
            return false;
        }
        return false;
    }
    /*
     * Kings cannot attack other Kings.
     */
    @Override
    public boolean isAttackingKing(Square[][] squares) {
        return false;
    }
}

// TODO: implement castling and disallowing moves next to other kings