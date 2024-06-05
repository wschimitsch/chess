/*-----------------------------------------------------------
 * Author: William Schimitsch
 * Date: 12/16/2023
 * 
 * User-friendly Title Screen class to introduce the game
 * and suggest any game setting changes. Initializes and 
 * starts the Game frame when the user clicks play. * 
 *-----------------------------------------------------------*/
package wfs.chess.board;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TitleScreen extends JFrame {
    /*
     * Title components.
     */
    private JLabel titleLabel;
    private JPanel titlePanel;
    /*
     * Button components for user input for game settings.
     */
    private JRadioButton whiteRadioButton, blackRadioButton, threeMinute, fiveMinute, tenMinute;
    private ImageIcon whiteIcon, blackIcon, whiteSelectedIcon, blackSelectedIcon;
    private ButtonGroup colorButtonGroup, timeButtonGroup;
    private JButton playButton;
    private JPanel timeButtons, timePanel, colorButtons, colorPanel;
    private JLabel timeLabel, colorLabel;
    /*
     * Keep track of user selections. The default game length is 10 minutes
     * and the default player color is white.
     */
    private int timeSelection = 10;
    private boolean colorSelection = true;
    /*
     * Extra JPanels so we can mimic the look of a chess board in our title screen.
     */
    private JPanel spots[] = new JPanel[8];
    /*
     * Colors for our Title Screen background.
     */
    private Color lb = new Color(255, 204, 153);
    private Color db = new Color(153, 76, 10);

    public TitleScreen () {
        // Set layout to mimic the look of a chessboard
        setLayout(new GridLayout(4, 3));
        setPreferredSize(new Dimension(500, 350));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        // Initialize spots
        boolean dark = false;
        for (int i = 0; i < 8; i++) {
            spots[i] = new JPanel();
            if (dark) {
                spots[i].setBackground(db);
            } else {
                spots[i].setBackground(lb);
            }
            if (i%2 != 0) {
                dark = !dark;
            }
        }
        // Set the Title Screen icon
        ImageIcon titleIcon = new ImageIcon(getClass().getResource("/img/chess.png"));
        // JLabel lab = new JLabel(titleIcon);
        // add(lab);
        setIconImage(titleIcon.getImage());
        // Creating Title Panel and components (first row)
        titleLabel = new JLabel("Chess");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(titleLabel);
        titlePanel.setBackground(db);
        add(spots[0]);
        add(titlePanel);
        add(spots[1]);
        // Creating buttons for game length selection (second row)
        threeMinute = new JRadioButton("3:00");
        threeMinute.addActionListener(new TimeBtnListener(3));
        threeMinute.setBackground(lb);
        fiveMinute = new JRadioButton("5:00");
        fiveMinute.addActionListener(new TimeBtnListener(5));
        fiveMinute.setBackground(lb);
        tenMinute = new JRadioButton("10:00");
        tenMinute.addActionListener(new TimeBtnListener(10));
        tenMinute.setBackground(lb);
        tenMinute.setSelected(true); // 10 minutes is our default game length
        timeLabel = new JLabel("Select Game Length"); // prompt user to select their desired game length
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Initialize button group for our time buttons
        timeButtonGroup = new ButtonGroup();
        timeButtonGroup.add(threeMinute);
        timeButtonGroup.add(fiveMinute);
        timeButtonGroup.add(tenMinute);

        timePanel = new JPanel(new BorderLayout());
        timeButtons = new JPanel();
        // Finish the time panel
        timePanel.add(timeLabel, BorderLayout.NORTH);
        timeButtons.add(threeMinute);
        timeButtons.add(fiveMinute);
        timeButtons.add(tenMinute);
        timeButtons.setBackground(lb);
        timePanel.add(timeButtons, BorderLayout.CENTER);
        timePanel.setBackground(lb);
        add(spots[2]);
        add(timePanel);
        add(spots[3]);
        // Creating buttons for color selection (third row)
        // Use rook icons for these buttons to denote piece colors
        ImageIcon white_rook = new ImageIcon(getClass().getResource("/img/w_rook.png"));
        ImageIcon black_rook = new ImageIcon(getClass().getResource("/img/b_rook.png"));
        // Get white image icons
        Image temp = white_rook.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        whiteIcon = new ImageIcon(temp);
        temp = white_rook.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        whiteSelectedIcon = new ImageIcon(temp);
        // Get black image icons
        temp = black_rook.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        blackIcon = new ImageIcon(temp);
        temp = black_rook.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        blackSelectedIcon = new ImageIcon(temp);
        // Set properties of white button
        whiteRadioButton = new JRadioButton();
        whiteRadioButton.setPreferredSize(new Dimension(50,50));
        whiteRadioButton.setIcon(whiteIcon);
        whiteRadioButton.setSelectedIcon(whiteSelectedIcon);
        whiteRadioButton.setSelected(true); // default player color is set to white
        whiteRadioButton.addActionListener(new ColorBtnListener(true));
        whiteRadioButton.setBackground(db);
        // Set properties of black button
        blackRadioButton = new JRadioButton();
        blackRadioButton.setPreferredSize(new Dimension(50,50));
        blackRadioButton.setIcon(blackIcon);
        blackRadioButton.setSelectedIcon(blackSelectedIcon);
        blackRadioButton.addActionListener(new ColorBtnListener(false));
        blackRadioButton.setBackground(db);
        // Create button group for the piece selection buttons
        colorButtonGroup = new ButtonGroup();
        colorButtonGroup.add(whiteRadioButton);
        colorButtonGroup.add(blackRadioButton);
        // Finish the color panel
        colorPanel = new JPanel(new BorderLayout());
        colorLabel = new JLabel("Select Pieces");
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        colorPanel.add(colorLabel, BorderLayout.NORTH);
        colorButtons = new JPanel(new FlowLayout());
        colorButtons.setBackground(db);
        colorButtons.add(whiteRadioButton);
        colorButtons.add(blackRadioButton);
        colorPanel.add(colorButtons, BorderLayout.CENTER);
        colorPanel.setBackground(db);
        add(spots[4]);
        add(colorPanel);
        add(spots[5]);
        // Creating panel for game start (fourth row)
        playButton = new JButton("Play");
        playButton.addActionListener(new PlayBtnListener());
        JPanel playPanel = new JPanel(new BorderLayout());
        playPanel.setBorder(BorderFactory.createEmptyBorder(15,25,15,25));
        playPanel.add(playButton, BorderLayout.CENTER);
        playPanel.setBackground(lb);
        add(spots[6]);
        add(playPanel);
        add(spots[7]);

        pack();
        setLocationRelativeTo(null); // display title screen in the center of the window
    }
    /*
     * Time Button Listener to record if and when the user selects
     * and/or changes game length settings.
     */
    private class TimeBtnListener implements ActionListener {
        private int time;
        
        public TimeBtnListener(int t) {
            time = t;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            timeSelection = time;
            System.out.println("You chose a game length of " + time + " minutes per player.");
        }
    }
    /*
     * Color Button Listener to record if and when the user selects
     * and/or changes their (Player 1's) color.
     */
    private class ColorBtnListener implements ActionListener {
        private boolean color;
        
        public ColorBtnListener(boolean c) {
            color = c;

        }    

        @Override
        public void actionPerformed(ActionEvent evt) {
            colorSelection = color;
            if (color) {
                System.out.println("You chose to play with the white pieces.");
            } else {
                System.out.println("You chose to play with the black pieces.");
            }
        }
    }
    /*
     * Play Button Listener to start the chess game with the user's
     * current game setting selections.
     */
    private class PlayBtnListener implements ActionListener { 
        @Override
        public void actionPerformed(ActionEvent evt) {
            String color = "";
            if (colorSelection) {
                color = "white";
            } else {
                color = "black";
            }
            System.out.println("You will be playing with the " + color + " pieces with " + timeSelection + " minute game time (per player)!");
            System.out.println("Enjoy!");

            TitleScreen.this.dispose(); // close title screen
            // Initialize new Game
            Game game = new Game(colorSelection, timeSelection);
            // Show the Game
            game.setVisible(true);  
        }
    }
} // end of TitleScreen class