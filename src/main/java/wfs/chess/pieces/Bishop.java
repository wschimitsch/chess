/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * Bishop class to represent the corresponding piece in Chess.
 * Moves/attacks diagonally.
 *-----------------------------------------------------------*/
package wfs.chess.pieces;

import java.awt.Image;
import javax.swing.ImageIcon;
import wfs.chess.board.Square;

public class Bishop extends Piece {
    /*
     * Bishop constructor. Calls parent constructor and sets display image.
     */
    public Bishop (boolean white, boolean playerColor) {
        super(white, playerColor); // call parent constructor
        if (white) { // set display image based on piece color
            icon = new ImageIcon(getClass().getResource("/img/w_bishop.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/img/b_bishop.png"));
        }
        Image curr = icon.getImage().getScaledInstance(Square.SIZE, Square.SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(curr);
        this.setIcon(icon);
    }
    /*
     * move function for Bishop. Can move to squares in any direction diagonally from it, unless
     * a piece is blocking it. 
     */
    @Override
    public boolean move(Square start, Square dest, Square[][] squares) {
        if (!isMove(start, dest, squares)) { // check basic move requirements
            return false;
        } 
        return this.makeMove(start, dest);
    }

    @Override
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        if (!super.isMove(start, dest, squares)) {
            return false;
        }

        int x1 = start.px, x2 = dest.px;
        int y1 = start.py, y2 = dest.py;
        if (Math.abs(y2-y1) != Math.abs(x2-x1)) { // if the destination square is not on a diagonal from the starting square, this is an invalid Bishop move
            return false;
        }
        // Prevent Bishop from jumping over pieces
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
        return true;
    }
    /*
     * A bishop threatens pieces on its diagonals.
     */
    @Override
    public boolean isAttackingKing(Square[][] squares) {
        // TODO Auto-generated method stub
        return false;
    }

}
