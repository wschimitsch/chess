/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * Abstract class to represent the basic functions of each Chess
 * piece. To be inherited by each respective piece class.
 * 
 * Source for piece images: https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces
 *-----------------------------------------------------------*/
package wfs.chess.pieces;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import wfs.chess.board.Square;

public abstract class Piece extends JLabel {
    /*
     * Piece properties:
     *      color
     *      alive/on board
     *      icon/display image
     *      current spot on board
     *      id to uniquely identify each piece
     */
    protected boolean isWhite;
    protected boolean hasMoved;
    protected ImageIcon icon; 
    protected Square position;
    protected int id;

    /*
     * Piece constructor. Sets the color to 
     * the function input and status to alive.
     */
    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
        position = null;
        hasMoved = false;
        id = (int) (Math.random() * 9999) + 1;
    }

    /*
     * setSquare sets this piece's square to the given square.
     */
    public void setSquare(Square sq) {
        position = sq;
    }

    /*
     * getColor returns the color of this piece.
     */
    public boolean getColor() {
        return isWhite;
    }

    /*
     * getSquare returns the position of this piece.
     */
    public Square getSquare() {
        return position;
    }
    
    /*
     * getId returns this piece's unique id.
     */
    public int getId() {
        return id;
    }

    /*
     * Basic check function for valid moves. Applicable for all pieces. Takes in
     * starting square, destination square, and 2D array of squares representing the board.
     * Moving from the current square to the current square is not a valid move. A piece
     * also cannot move to a square that has a piece of the same color on it. 
     * 
     * Children classes call this function to check basic requirements for a move, and
     * then have further specifications to check if the move is valid for that particular
     * piece.
     */
    public boolean isMove(Square start, Square dest, Square[][] squares) {
        if (start.equals(dest)) {
            return false;
        }
        if (dest.getPiece() == null) {
            return true;
        } else if (isWhite == dest.getPiece().getColor()) {
            return false;
        } else {
            return true;
        }

    }

    /*
     * Move this piece from one square to another.  
     */
    public boolean makeMove(Square start, Square dest, Square[][] squares) {
        if (dest.getPiece() == null) { // if destination square is empty, move there
            dest.setPiece(this);
            start.setPiece(null);
        } else { // otherwise take the piece on the destination square
            kill(dest.getPiece(), dest);
            dest.setPiece(this);
            start.setPiece(null);
        } 
        setSquare(dest);
        hasMoved = true; 
        return true;
    }

    /*
     * kill function to kill/take opposing pieces, if valid move.
     */
    public void kill(Piece pc, Square sq) {
        sq.setPiece(null);
        sq.remove(pc);
    }

    /*
     * Validates that a move between the given square is valid for a bishop. Abstracted
     * function so that it can be used to validate diagonal Queen moves. 
     */
    public boolean isBishopMove(Square start, Square dest, Square[][] squares) {
        int sx = start.getSquareX(), sy = start.getSquareY();
        int dx = dest.getSquareX(), dy = dest.getSquareY();
        if (Math.abs(dy-sy) != Math.abs(dx-sx)) { // if the destination square is not on a diagonal from the starting square, this is an invalid Bishop move
            return false;
        }
        // Prevent Bishop from jumping over pieces
        int i = sx, j = sy;
        while (i != dx && j != dy) { // check each square before our destination for a piece
            if (i != sx && j != sy && squares[j][i].getPiece() != null) {
                return false;
            }
            i = (sx < dx) ? i + 1 : i - 1;
            j = (sy < dy) ? j + 1 : j - 1;
        }
        return true;
    }

    /*
     * Validates that a move between the given square is valid for a rook. Abstracted
     * function so that it can be used to validate horizontal Queen moves.
     */
    public boolean isRookMove(Square start, Square dest, Square[][] squares) {
        int sx = start.getSquareX(), sy = start.getSquareY();
        int dx = dest.getSquareX(), dy = dest.getSquareY();
        if (sx != dx && sy != dy) { // if destination square is not on the same file or same row, this is an invalid Rook move
            return false;
        }
        // Prevent Rook from jumping over pieces
        if (sx == dx) { // if moving vertically
            if (sy > dy) {
                int temp = sy;
                sy = dy;
                dy = temp;
            }
            for (int i = sy+1; i < dy; i++) { // check each square before our destination for a piece
                if (squares[i][sx].getPiece() != null) {
                    return false;
                }
            }
        } else { // if moving horizontally
            if (sx > dx) { 
                int temp = sx;
                sx = dx;
                dx = temp;
            } 
            for (int i = sx+1; i < dx; i++) { // check each square before our destination for a piece
                if (squares[sy][i].getPiece() != null) {
                    return false;
                }
            }           
        }
        return true;
    }

    /*
     * isAttackingKing checks if this piece is threatening the enemy King.
     * Used to see if that King is in check/checkmate.
     */
    public abstract boolean isAttackingKing(Square kingSquare, Square[][] squares);
} // end Piece class