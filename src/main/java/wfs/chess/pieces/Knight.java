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
    public Knight(boolean white, boolean playerColor) {
        super(white, playerColor); // call parent constructor
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
     * move function for Knight. Knight can move in an 'L' shape, i.e. 2 squares right and one up.
     * The Knight is allowed to 'jump' over pieces.
     */
    @Override
    public boolean move(Square start, Square dest, Square[][] squares) {
        if (!super.move(start, dest, squares)) { // check for basic move requirements
            return false;
        } 
        if (Math.abs(start.px-dest.px) * Math.abs(start.py-dest.py) != 2) { // if invalid Knight move
            return false;
        }
        if (dest.getPiece() == null) { // destination square is empty, so move there
            dest.setPiece(this);
            start.setPiece(null);
            return true;
        } else { // destination square has opposing piece, so take it
            kill(dest.getPiece(), dest);
            dest.setPiece(this);
            start.setPiece(null);
            return true;
        }     
    }
    /*
     * Knight threatens squares that are an 'L' shape away from it.
     */
    @Override
    public boolean isAttackingKing(Square[][] squares) {
        // TODO Auto-generated method stub
        return false;
    }
}
