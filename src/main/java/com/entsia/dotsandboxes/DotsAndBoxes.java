package com.entsia.dotsandboxes;

import java.util.Scanner;

/**
 * Main entry point for Dots and Boxes game.
 * Handles user interaction and game loop.
 */
public class DotsAndBoxes {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Game game = new Game();

    public static void main(String[] args) {
        gameLoop();
    }

    /**
     * Main game loop.
     */
    private static void gameLoop() {
        System.out.println(game.getStatus());

        while (!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();
            String prompt = String.format("Player %d, input a move <column><row> (or 'Q' to quit): ",
                    currentPlayer.getId());

            String input = getPlayerInput(prompt);

            if (input.equalsIgnoreCase("Q")) {
                System.out.println("Thanks for playing! Goodbye!");
                return;
            }

            Move move = Move.parse(input);

            if (move == null) {
                System.out.println("Invalid move. Please try again.");
                continue;
            }

            // Check if position is valid
            if (!InputValidator.isValidEdgePosition(move.getColumn(), move.getRow())) {
                System.out.println("Invalid move. Please try again.");
                continue;
            }

            // Check if already occupied
            if (game.getBoard().isOccupied(move.getColumn(), move.getRow())) {
                System.out.println("This position is already occupied. Please try again.");
                continue;
            }

            // Make the move
            game.makeMove(move);
            System.out.println(game.getStatus());
        }

        // Game is over
        Player winner = game.getWinner();
        if (winner != null) {
            System.out.println("Game over. Player " + winner.getId() + " is the winner!");
        } else {
            System.out.println("Game over. It's a tie!");
        }

        System.out.println("Thanks for playing! Goodbye!");
    }

    /**
     * Get player input from console.
     */
    private static String getPlayerInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
