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
    public Bishop(boolean white) {
        super(white); // call parent constructor
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
     * isMove validates that a move between the given squares is valid with a bishop. 
     */
    @Override
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        return super.isMove(start, dest, squares) && super.isBishopMove(start, dest, squares);
    }
    
    /*
     * A bishop threatens pieces on its diagonals.
     */
    @Override
    public boolean isAttackingKing(Square kingSquare, Square[][] squares) {
        return isMove(this.position, kingSquare, squares);
    }
}
