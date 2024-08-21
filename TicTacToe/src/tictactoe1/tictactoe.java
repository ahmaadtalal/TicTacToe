package tictactoe1;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class tictactoe implements ActionListener {

    // Random number generator for deciding the first player
    Random random = new Random();
    // Main frame for the game window
    JFrame frame = new JFrame();
    // Panel for the title (game status display)
    JPanel titlePanel = new JPanel();
    // Panel for the game buttons (tic-tac-toe grid)
    JPanel buttonPanel = new JPanel();
    // Label for displaying the game status
    JLabel textField = new JLabel();
    // Array of buttons for the tic-tac-toe grid
    JButton[] buttons = new JButton[9];
    // Boolean to track the current player's turn
    boolean player1Turn;
    // Boolean to check if the game is over
    boolean gameOver;

    // Constructor to set up the game
    tictactoe() {
        // Set frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Set textField properties for the game status display
        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(25, 255, 0));
        textField.setFont(new Font("Ink Free", Font.BOLD, 75));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic-Tac-Toe");
        textField.setOpaque(true);

        // Set titlePanel properties and layout
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        // Set buttonPanel properties and layout
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        // Initialize buttons and add to buttonPanel
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        // Add components to frame
        titlePanel.add(textField);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);

        // Determine the first player's turn
        firstTurn();
    }

    // Method to determine and display the first player's turn
    public void firstTurn() {
        try {
            Thread.sleep(2000); // Pause for 2 seconds before starting the game
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1Turn = true;
            textField.setText("X Turn");
        } else {
            player1Turn = false;
            textField.setText("O Turn");
        }
    }

    // Action listener method to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return; // If game is over, do nothing
        }
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1Turn) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(250, 0, 0));
                        buttons[i].setText("X");
                        player1Turn = false;
                        textField.setText("O Turn");
                        if (checkWin("X")) {
                            textField.setText("X Wins!");
                            gameOver = true;
                            showMenu();
                        } else if (checkDraw()) {
                            textField.setText("It's a Draw!");
                            gameOver = true;
                            showMenu();
                        }
                    }
                } else {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(0, 0, 255));
                        buttons[i].setText("O");
                        player1Turn = true;
                        textField.setText("X Turn");
                        if (checkWin("O")) {
                            textField.setText("O Wins!");
                            gameOver = true;
                            showMenu();
                        } else if (checkDraw()) {
                            textField.setText("It's a Draw!");
                            gameOver = true;
                            showMenu();
                        }
                    }
                }
            }
        }
    }

    // Method to check if the given player has won
    public boolean checkWin(String player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i * 3].getText().equals(player)
                    && buttons[i * 3 + 1].getText().equals(player)
                    && buttons[i * 3 + 2].getText().equals(player)) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[i].getText().equals(player)
                    && buttons[i + 3].getText().equals(player)
                    && buttons[i + 6].getText().equals(player)) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0].getText().equals(player)
                && buttons[4].getText().equals(player)
                && buttons[8].getText().equals(player)) {
            return true;
        }
        if (buttons[2].getText().equals(player)
                && buttons[4].getText().equals(player)
                && buttons[6].getText().equals(player)) {
            return true;
        }

        return false;
    }

    // Method to check if the game is a draw
    public boolean checkDraw() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false; // If any button is empty, it's not a draw
            }
        }
        return true;
    }

    // Method to display a menu asking if the player wants to play again
    public void showMenu() {
        int option = JOptionPane.showConfirmDialog(frame, "Do you want to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            resetGame(); // Reset the game if player wants to play again
        } else {
            System.exit(0); // Exit the application if player does not want to play again
        }
    }

    // Method to reset the game
    public void resetGame() {
        for (JButton button : buttons) {
            button.setText(""); // Clear the text of each button
        }
        player1Turn = true;
        gameOver = false;
        firstTurn(); // Determine the first player's turn for the new game
    }

    // Main method to start the application
    public static void main(String[] args) {
        new tictactoe(); // Create an instance of the tictactoe class
    }
}
