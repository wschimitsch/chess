
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
     * King constructor. Calls parent constructor and sets the display image.
     */
    public King(boolean white) {
        super(white); // call parent constructor
        if (white) { // set display image based on piece color
            icon = new ImageIcon(getClass().getResource("/img/w_king.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/img/b_king.png"));
        }
        Image curr = icon.getImage().getScaledInstance(Square.SIZE, Square.SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(curr);
        setIcon(icon);
    }

    /*
     * isMove validates that a move between the given squares is valid with a king. 
     */
    @Override
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        if (!super.isMove(start, dest, squares)) {
            return false;
        }
        int sx = start.getSquareX(), sy = start.getSquareY();
        if (dest.checkForKing(sx, sy, squares)) { // if there is a King neighboring this square it is an invalid move
            return false;
        }
        int dx = dest.getSquareX(), dy =dest.getSquareY();
        if (hasMoved && ((dx > sx+1) || (dx < sx-1) || (dy > sy+1 || dy < sy-1))) { // cannot move King more than one square
            return false;
        }
        return true; 
    }

    /*
     * makeMove function for King. King can castle or move 1 square in any direction.
     */
    @Override
    public boolean makeMove(Square start, Square dest, Square[][] squares) {
        if (!hasMoved && isValidCastling(start, dest, squares)) {
            return true;
        }
        return super.makeMove(start, dest, squares); 
    }

    /*
     * isValidCastling function to implement castling feature, whereby the King
     * can move two squares to safety while the rook on the respective side the
     * King is castling moves to the other side of King. 
     */
    public boolean isValidCastling(Square start, Square dest, Square[][] squares) {
        int sx = start.getSquareX(), sy = start.getSquareY(), dx = dest.getSquareX(); // get square coordinates
        if (dx == sx+2) { // rook would be on 7th column
            Piece pc = squares[sy][7].getPiece();
            if (pc instanceof Rook && !pc.hasMoved && pc.isMove(squares[sy][7], squares[sy][dx-1], squares)) {
                // Intrinsically remove pieces
                start.setPiece(null);
                squares[sy][7].setPiece(null);
                dest.setPiece(this);
                squares[sy][dx-1].setPiece(pc);
                // Graphically remove pieces
                squares[sy][7].remove(pc);
                squares[sy][dx-1].add(pc);
                hasMoved = true;
                return true;
            }
            return false;
        } else if (dx == sx-2) { // rook would be on 0th column
            Piece pc = squares[sy][0].getPiece();
            if (pc instanceof Rook && !pc.hasMoved && pc.isMove(squares[sy][0], squares[sy][dx+1], squares)) {
                // Intrinsically remove pieces
                start.setPiece(null);
                squares[sy][0].setPiece(null);
                dest.setPiece(this);
                squares[sy][dx+1].setPiece(pc);
                // Graphically remove pieces
                squares[sy][0].remove(pc);
                squares[sy][dx+1].add(pc);
                hasMoved = true;
                return true;
            }
            return false;
        }
        return false;
    }

    /*
     * Kings cannot attack other Kings. Simply return false.
     */
    @Override
    public boolean isAttackingKing(Square kingSquare, Square[][] squares) {
        return false;
    }
}
