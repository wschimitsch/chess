/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * Knight class to represent the corresponding piece in Chess.
 * Can move/attack in 'L's.
 *-----------------------------------------------------------*/
package wfs.chess.pieces;

import java.awt.Image;
import javax.swing.ImageIcon;
import wfs.chess.board.Square;

public class Knight extends Piece {

    /*
     * Knight constructor. Calls parent Piece constructor and sets the display image.
     */
    public Knight(boolean white) {
        super(white); // call parent constructor
        if (white) { // set display image based on piece color
            icon = new ImageIcon(getClass().getResource("/img/w_knight.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/img/b_knight.png"));
        }
        Image curr = icon.getImage().getScaledInstance(Square.SIZE, Square.SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(curr);
        this.setIcon(icon);
    }
    
    /*
     * isMove validates that a move between the given squares is valid with a knight. 
     */
    @Override
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        if (!super.isMove(start, dest, squares)) {
            return false;
        }
        if (Math.abs(start.getSquareX()-dest.getSquareX()) * Math.abs(start.getSquareY()-dest.getSquareY()) != 2) { 
            return false;
        }
        return true;
    }

    /*
     * Knight threatens squares that are an 'L' shape away from it.
     */
    @Override
    public boolean isAttackingKing(Square kingSquare, Square[][] squares) {
        return isMove(this.position, kingSquare, squares);
    }
}
