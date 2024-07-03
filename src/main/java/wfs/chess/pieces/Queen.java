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
    public Queen(boolean white) {
        super(white); // call parent constructor
        if (white) { // set display image based on piece color
            icon = new ImageIcon(getClass().getResource("/img/w_queen.png"));
        } else {
            icon = new ImageIcon(getClass().getResource("/img/b_queen.png"));
        }
        Image curr = icon.getImage().getScaledInstance(Square.SIZE, Square.SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(curr);
        this.setIcon(icon);
    }

    /*
     * isMove validates that a move between the given squares is valid with a queen using 
     * rook and bishop move validation.
     */
    @Override
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        if(!super.isMove(start, dest, squares)){
            return false;
        }
        if (!super.isRookMove(start, dest, squares) && !super.isBishopMove(start, dest, squares)) {
            return false;
        }
        return true;
    }
    
    /*
     * Queen can threaten pieces horizontally, vertically, and diagonally from it.
     */
    @Override
    public boolean isAttackingKing(Square kingSquare, Square[][] squares) {
        return isMove(this.position, kingSquare, squares);
    }
}


