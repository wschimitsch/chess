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
     * Rook constructor. Calls parent constructor and sets the display image.
     */
    public Rook(boolean white) {
        super(white); // call parent constructor
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
     * isMove validates that a move between the given squares is valid with a rook. 
     */
    @Override
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        return super.isMove(start, dest, squares) && super.isRookMove(start, dest, squares);
    }

    /*
     * Rook threatens pieces on the same row and file as it. 
     */
    @Override
    public boolean isAttackingKing(Square kingSquare, Square[][] squares) {
        return isMove(this.position, kingSquare, squares);
    }
}
