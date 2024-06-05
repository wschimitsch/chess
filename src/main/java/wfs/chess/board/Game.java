/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * Class representing an 8 x 8 Chess game. Extends Java
 * Swing JFrame component.
 * 
 * Game frame will look like layout below:
 * 
 * ----------------------------------------------------
 * |  ----------------------------------------------  |
 * |  |                                            |  |
 * |  |                                            |  |
 * |  |                                            |  |
 * |  |                                            |  |
 * |  |                  8 X 8                     |  |
 * |  |               Grid Layout                  |  |
 * |  |           Holding 64 Squares &             |  |
 * |  |             32 Chess pieces                |  |
 * |  |                                            |  |
 * |  |                                            |  |
 * |  |                                            |  |
 * |  |                                            |  |
 * |  ----------------------------------------------  |
 * |        White Timer           Black Timer         |
 * ----------------------------------------------------
 * 
 * Displays chess board and pieces, while also implementing piece 
 * moving functionality with custom MouseListener class MoveListener.
 * User can click (not drag) from square to square to move pieces.
 * Game is won either when a player is in checkmate (not yet 
 * implemented) or when a player runs out of time.
 *-----------------------------------------------------------*/
package wfs.chess.board;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Arrays;

import wfs.chess.pieces.*;

public class Game extends JFrame {
    /*
     * Size of the chess board. A traditional board is 8x8.
     */
    public final int BOARD_SIZE = 8;
    /*
     * Array to hold the pieces on the board.
     */
    private MoveListener ml;
    /*
     * Color of the Player, or Player 1.
     */
    protected boolean isPlayerWhite;
    /*
     * Keep track of whose turn it is.
     */
    protected boolean turn = true;
    /*
     * Keep track of the game status. 
     */
    private boolean gameOver = false;
    /*
     * Panels to display a win for white and for black.
     */
    private JPanel whiteWin;
    private JPanel blackWin;
    private JLabel whiteWinLabel;
    private JLabel blackWinLabel; 
    /*
     * Array to hold the squares on the board.
     */
    private Square squares[][];
    /*
     * JPanel to hold the chess board.
     */
    private JPanel boardPanel;
    /*
     * Timers for both players.
     */
    Timer whiteTimer;
    Timer blackTimer;
    /* 
     * Swing components for the timers.
     */
    JLabel whiteTimerLabel;
    JPanel whitePanel;
    JLabel blackTimerLabel;
    JPanel blackPanel;
    /*
     * JPanel to hold the timers and borders.
     */
    private JPanel timerPanel;
    private JPanel northBorder;
    private JPanel eastBorder;
    private JPanel westBorder;
    /*
     * Array to hold the names of each file on the board.
     */
    private char files[] = {'h', 'g', 'f', 'e', 'd', 'c', 'b', 'a'};
    /*
     * Array to hold the names of each row on the board.
     */
    private char rows[] = {'8', '7', '6', '5', '4', '3', '2', '1'};
    /*
     * Array to hold the pieces on the board.
     */
    private Piece pcs[][];
    /*
     * Board colors
     */
    private Color lb = new Color(255, 204, 153);
    private Color db = new Color(153, 76, 10);
    private Color dg = new Color(64, 64, 64);
    /*
     * Game constructor. Takes in the color of the (main) player and the 
     * length of the game (for each player). Initializes game frame and 
     * the chessboard display. 
     */
    public Game (boolean playerColor, int gameLength) {
        /*
         * Initialize Game properties:
         *      Border Layout Manager
         *      Fixed frame size
         *      Exit program on close         
         */
        setAlwaysOnTop(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        // Set the Game icon
        ImageIcon icon = new ImageIcon("img/chess.png");
        setIconImage(icon.getImage());
        // Initialize the player color
        isPlayerWhite = playerColor;
        if (!isPlayerWhite) {
            Arrays.sort(rows);
        } else {
            Arrays.sort(files);
        }
        /*
         * Initialize chess board panel
         * We'll use a Grid Layout here to mimic the grid of the board
         */
        boardPanel = new JPanel(new GridLayout(8, 8));
        // Declare and add the Squares of the chess board to the board panel
        boolean dark = false;
        squares = new Square[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                squares[x][y] = new Square(y, x);
                if (dark) {
                    squares[x][y].setBackground(db);
                } else {
                    squares[x][y].setBackground(lb);
                }
                boardPanel.add(squares[x][y]);    // add this square to the game frame
                dark = !dark;
            }
            dark = !dark; 
        }
        // Declare and add the pieces to the board
        pcs = new Piece[2][2*BOARD_SIZE];
        boolean color = false;
        for (int i = 0; i < 4; i++) { // four rows of pieces
            if (i > 1) {
                color = true;
            }
            for (int j = 0; j < BOARD_SIZE; j++) { // eight pieces per row
                Piece pc;
                if (i == 1 || i == 2) {
                    pc = new Pawn(color, isPlayerWhite);
                } else {
                    switch (j) { // initialize pieces based on their starting positions
                        case 0:
                        case 7:
                            pc = new Rook(color, isPlayerWhite);
                            break;
                        case 1:
                        case 6:
                            pc = new Knight(color, isPlayerWhite);
                            break;
                        case 2:
                        case 5:
                            pc = new Bishop(color, isPlayerWhite);
                            break;
                        case 3:
                            pc = new Queen(color, isPlayerWhite);
                            break;
                        case 4:
                            pc = new King(color, isPlayerWhite);
                            break;
                        default:
                            pc = new Pawn(color, isPlayerWhite);
                            break;
                    }
                }
                if (i > 1) { 
                    if (isPlayerWhite) {
                        // Player is white, so add the white pieces to the bottom of the board
                        squares[i+4][j].add(pc);
                        squares[i+4][j].setPiece(pc);
                        pc.setSquare(squares[i+4][j]);
                    } else {
                        // Player is black, so add the white pieces to the top of the board
                        if (i == 2) {
                            squares[1][j].add(pc);
                            squares[1][j].setPiece(pc);
                            pc.setSquare(squares[1][j]);
                        } else {
                            squares[0][j].add(pc);
                            squares[0][j].setPiece(pc);
                            pc.setSquare(squares[0][j]);
                        }
                        
                    }
                    // Add piece to array of pieces
                    if (i == 2) {
                        pcs[1][j] = pc;
                    } else {
                        pcs[1][8+j] = pc;
                    }
                } else { 
                    if (isPlayerWhite) {
                        // Player is white, so add the black pieces to the top of the board
                        squares[i][j].add(pc);
                        squares[i][j].setPiece(pc);
                        pc.setSquare(squares[i][j]);
                    } else {
                        // Player is black, so add the black pieces to the top of the board
                        if (i == 1) {
                            squares[6][j].add(pc);
                            squares[6][j].setPiece(pc);
                            pc.setSquare(squares[6][j]);
                        } else {
                            squares[7][j].add(pc);
                            squares[7][j].setPiece(pc);
                            pc.setSquare(squares[7][j]);
                        }
                    }
                    // Add piece to array of pieces
                    if (i == 0) {
                        pcs[0][j] = pc;
                    } else {
                        pcs[0][8+j] = pc;
                    }
                }
    
            }
        }
        // Add the board to our Game frame
        add(boardPanel, BorderLayout.CENTER);
        // Add a custom Move Listener to take mouse input from user for moves
        ml = new MoveListener();
        boardPanel.addMouseListener(ml);
        // Display the timers for both players
        String timerDisplay = String.format("%02d:%02d", gameLength, 0);
        whiteTimerLabel = new JLabel(timerDisplay);
        whitePanel = new JPanel();
        whitePanel.add(whiteTimerLabel);
        whitePanel.setBackground(Color.WHITE);
        blackTimerLabel = new JLabel(timerDisplay);
        blackTimerLabel.setForeground(Color.WHITE);
        blackPanel = new JPanel();
        blackPanel.add(blackTimerLabel);
        blackPanel.setBackground(Color.BLACK);

        whiteTimer = new Timer(1000, new TimerListener(gameLength * 60 * 1000, whiteTimerLabel));
        blackTimer = new Timer(1000, new TimerListener(gameLength * 60 * 1000, blackTimerLabel));

        timerPanel = new JPanel();
        
        timerPanel.add(whitePanel);
        timerPanel.add(blackPanel);
        timerPanel.setBackground(dg);
  
        add(timerPanel, BorderLayout.SOUTH);
        // Create a border around the chess board
        northBorder = new JPanel();
        eastBorder = new JPanel();
        westBorder = new JPanel();
        northBorder.setBackground(dg);
        eastBorder.setBackground(dg);
        westBorder.setBackground(dg);
        add(northBorder, BorderLayout.NORTH);
        add(eastBorder, BorderLayout.EAST);
        add(westBorder, BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null); // lets the Game display at the center of the window

    }
    /*
     * TODO: Implement check functionality
     * 
     * isInCheck function takes in the color of the player we are checking
     * and our 2D array of squares, i.e. the chess board, and our array of 
     * pieces. We will loop through every opposing piece to check if this 
     * King is being threatened.
     */
    public boolean isInCheck(boolean color, Square[][] squares, Piece[][] pcs) {
        Piece currPiece;
        /*
         * Loop through all the pieces to see if any are attacking
         * the King.
         */
        for (int i = 0; i < 16; i++) {
            if (!turn) {
                currPiece = pcs[0][i];
            } else {
                currPiece = pcs[1][i];
            }
            if (currPiece.isAttackingKing(squares)) {
                return true;
            }
        }
        return false;
    }
    /*
     * TODO: Implement checkmate functionality
     */
    public boolean isInCheckmate(boolean color) {
        gameOver = true;
        return true;
    }
    /*
     * MoveListener class to take in mouse input from user. Keeps track of 
     * the piece most recently clicked and implements functionality for 
     * piece moving. 
     */
    private class MoveListener implements MouseListener {
        // Initalize member variables to null/invalid
        // Previous piece is the piece we want to move
        private Piece prevPiece = null;
        private int prevRow = -1, prevCol = -1;

        public MoveListener () {
            super();        
        }
        /*
         * Respond to mouse click input. If we have already clicked a piece, then
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            if (gameOver) { // game is over, don't respond to clicks
                return;
            }
            int col = e.getX()/74; // div by 74 since thats the preferred size of each square
            int row = e.getY()/74;

            if (prevPiece == null) {
                prevPiece = squares[row][col].getPiece();
                prevRow = row;
                prevCol = col;
                System.out.println("Mouse clicked square at "  + files[col] + rows[row]);
        
            } else if (isInCheck(turn, squares, pcs)) { // if the King is already in check
                // TODO
            } else if (prevPiece.getColor() == turn && prevPiece.move(squares[prevRow][prevCol], squares[row][col], squares)) {
                if (isInCheck(turn, squares, pcs)) { // this move puts the King in check
                    prevPiece.move(squares[row][col], squares[prevRow][prevCol], squares); // move the piece back
                    System.out.println("Sorry, that piece cannot move there because it causes a chec. Try a different move.");
                } else {
                    System.out.println("Mouse clicked square at "  + files[col] + rows[row]); 
                    squares[prevRow][prevCol].remove(prevPiece);
                    squares[row][col].add(prevPiece);
                    System.out.println("Successfully moved from " + files[prevCol] + rows[prevRow] + " to " + files[col] + rows[row]);
                    prevPiece = null;  
                    turn = !turn;
                    if (turn) {
                        blackTimer.stop();
                        whiteTimer.start();
                    } else {
                        whiteTimer.stop();
                        blackTimer.start();
                    }
                }                
            } else if (prevPiece.getColor() != turn) {
                if (turn) {
                    System.out.println("It is not black's turn!");
                } else {
                    System.out.println("It is not white's turn!");
                }
                prevPiece = null;
            } else {
                System.out.println("Sorry, that piece cannot move there. Try a different move.");
                prevPiece = null;
            }
            // Update our Game JFrame to display the moves made
            repaint();
        }

        // We don't want to respond to these actions
        @Override
        public void mousePressed(MouseEvent e) {
            return;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            return;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            return;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            return;
        }
    }

    /*
     * TimerListener class used for player game clock functionality.
     * When counting, these clocks count down from the set game time.
     * If the timer hits 0 for a player, that player loses automatically. 
     */
    private class TimerListener implements ActionListener {
        private int remainingTime;
        private JLabel timerLabel;

        public TimerListener(int rt, JLabel tl) {
            remainingTime = rt;
            timerLabel = tl;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            remainingTime -= 1000; // subtract 1000 milliseconds, or 1 second, from remaining time

            int minutes = remainingTime / (60 * 1000);
            int seconds = (remainingTime / 1000) % 60;
            // Display time
            String formattedTime = String.format("%02d:%02d", minutes, seconds);
            timerLabel.setText(formattedTime);

            if (remainingTime <= 0) {
                gameOver = true;
                Timer t = (Timer)e.getSource();
                t.stop();

                if (turn) { // white ran out of time, so black won
                    blackWin = new JPanel();
                    blackWin.setBackground(Color.DARK_GRAY);
                    blackWin.setBorder(BorderFactory.createEmptyBorder(250,100,250,100));
                    blackWinLabel = new JLabel("Black Wins!", SwingConstants.CENTER);
                    blackWinLabel.setFont(new Font("Arial", Font.BOLD, 32));
                    blackWinLabel.setForeground(Color.WHITE);
                    // Display an image of a King
                    ImageIcon i = new ImageIcon("img/b_king.png");
                    Image curr = i.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    i = new ImageIcon(curr);
                    JLabel king = new JLabel(i);
                    // Add components
                    blackWin.add(king);                 
                    blackWin.add(blackWinLabel);
                    Game.this.add(blackWin, BorderLayout.CENTER);
                } else { // black ran out of time, so white won
                    whiteWin = new JPanel();
                    whiteWin.setBackground(Color.WHITE);
                    whiteWin.setBorder(BorderFactory.createEmptyBorder(250,100,250,100));
                    whiteWinLabel = new JLabel("White Wins!", SwingConstants.CENTER);
                    whiteWinLabel.setFont(new Font("Arial", Font.BOLD, 32));
                    // Display an image of a King
                    ImageIcon i = new ImageIcon("img/w_king.png");
                    Image curr = i.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    i = new ImageIcon(curr);
                    JLabel king = new JLabel(i);
                    // Add components
                    whiteWin.add(king);                 
                    whiteWin.add(whiteWinLabel);
                    Game.this.add(whiteWin);
                }
            }
        }
    }
} // end of Game class