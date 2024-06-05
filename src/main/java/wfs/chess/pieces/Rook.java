/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * Rook class to represent the corresponding piece in Chess.
 * Moves horizontally or vertically.
 *-----------------------------------------------------------*/
package wfs.chess.pieces;

import java.awt.Image;
import javax.swing.ImageIcon;
import wfs.chess.board.Square;

public class Rook extends Piece {
    /*
     * Rook can castle with King, so keep track of if it has moved or not.
     */
    protected boolean hasMoved = false;
    /*
     * Rook constructor. Calls parent constructor and sets the display image.
     */
    public Rook(boolean white, boolean playerColor) {
        super(white, playerColor); // call parent constructor
        if (white) { // set display image based on piece color
            icon = new ImageIcon(getClass().getResource("/img/w_rook.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/img/b_rook.png"));
        }
        Image curr = icon.getImage().getScaledInstance(Square.SIZE, Square.SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(curr);
        this.setIcon(icon);
    }
    /* 
     * move function for Rook. Rook can move along rows or files of the chessboard, unless a piece
     * is blocking it. 
     */
    @Override
    public boolean move(Square start, Square dest, Square[][] squares) {
        if (!super.move(start, dest, squares)) { // check basic move requirements
            return false;
        } 
        int x1 = start.px, x2 = dest.px;
        int y1 = start.py, y2 = dest.py;
        if (x1 != x2 && y1 != y2) { // if destination square is not on the same file or same row, this is an invalid Rook move
            return false;
        }
        // Prevent Rook from jumping over pieces
        if (x1 == x2) { // if moving vertically
            if (y1 > y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            for (int i = y1+1; i < y2; i++) { // check each square before our destination for a piece
                if (squares[i][x1].getPiece() != null) {
                    return false;
                }
            }
        } else { // if moving horizontally
            if (x1 > x2) { 
                int temp = x1;
                x1 = x2;
                x2 = temp;
            } 
            for (int i = x1+1; i < x2; i++) { // check each square before our destination for a piece
                if (squares[y1][i].getPiece() != null) {
                    return false;
                }
            }           
        }
        if (dest.getPiece() == null) { // if destination square is empty, move there
            dest.setPiece(this);
            start.setPiece(null);
            hasMoved = true;
            return true;
        } else { // otherwise take the piece on the destination square 
            kill(dest.getPiece(), dest);
            dest.setPiece(this);
            start.setPiece(null);
            hasMoved = true;
            return true;
        } 
    }
    /*
     * Rook threatens pieces on the same row and file as it. 
     */
    @Override
    public boolean isAttackingKing(Square[][] squares) {
        // TODO Auto-generated method stub
        return false;
    }
}
