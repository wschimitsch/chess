/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * Queen class to represent the corresponding piece in Chess.
 * Can only move horionatally, vertically, or diagonally.
 *-----------------------------------------------------------*/
package wfs.chess.pieces;

import java.awt.Image;
import javax.swing.ImageIcon;
import wfs.chess.board.Square;

public class Queen extends Piece {
    /*
     * Queen constructor. Calls parent constuctor and sets the display image.
     */
    public Queen(boolean white, boolean playerColor) {
        super(white, playerColor); // call parent constructor
        if (white) { // set display image based on piece color
            icon = new ImageIcon("img/w_queen.png");
        } else {
            icon = new ImageIcon("img/b_queen.png");
        }
        Image curr = icon.getImage().getScaledInstance(Square.SIZE, Square.SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(curr);
        this.setIcon(icon);
    }
    /*
     * move function for Queen. Queen can move in any direction (horizontally, vertically, diagonally) unless
     * a piece is blocking it. 
     */
    @Override
    public boolean move(Square start, Square dest, Square[][] squares) {
        if (!super.move(start, dest, squares)) { // check for basic move requirements
            return false;
        } 
        int x1 = start.px, x2 = dest.px;
        int y1 = start.py, y2 = dest.py;
        if ((x1 != x2) && (y1 != y2) && (Math.abs(y2-y1) != Math.abs(x2-x1))) { // not a valid Queen move, i.e. not a horizontal, vertical, or diagonal move
            return false;
        }
        // Since Queen pieces can move multiple squares at a time, prevent Queen from jumping over pieces
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
        } else if (y1 == y2) { // if moving horizontally
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
        } else { // if moving diagonally
            int i = x1, j = y1;
            while (i != x2 && j != y2) { // check each square before our destination for a piece
                if (i != x1 && j != y1 && squares[j][i].getPiece() != null) {
                    return false;
                }
                if (x1 < x2) {
                    i++;
                } else {
                    i--;
                }
                if (y1 < y2) {
                    j++;
                } else {
                    j--;
                }
            }
        }
        if (dest.getPiece() == null) { // if destination square is empty, move to it
            dest.setPiece(this);
            start.setPiece(null);
            return true;
        } else { // if destination square is occupied, take the piece on it
            kill(dest.getPiece(), dest);
            dest.setPiece(this);
            start.setPiece(null);
            return true;
        }    
    }
    /*
     * Queen can threaten pieces horizontally, vertically, and diagonally from it.
     */
    @Override
    public boolean isAttackingKing(Square[][] squares) {
        // TODO Auto-generated method stub
        return false;
    }
}


