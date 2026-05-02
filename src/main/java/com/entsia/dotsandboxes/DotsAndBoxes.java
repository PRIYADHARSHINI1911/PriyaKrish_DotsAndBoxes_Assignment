package com.entsia.dotsandboxes;

import java.util.Scanner;

/**
 * Main entry point for Dots and Boxes game.
 * Handles user interaction and game loop.
 */
public class DotsAndBoxes {
    private static final Game game = new Game();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            gameLoop(scanner);
        }
    }

    /**
     * Main game loop.
     */
    private static void gameLoop(Scanner scanner) {
        System.out.println(game.getStatus());

        while (!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();
            String prompt = String.format("Player %d, input a move <column><row> (or 'Q' to quit): ",
                    currentPlayer.getId());

            String input = getPlayerInput(scanner, prompt);

            if (input.equalsIgnoreCase("Q")) {
                System.out.println("Thanks for playing! Goodbye!");
                return;
            }

            Move move = Move.parse(input);

            if (isValidMove(move)) {
                game.makeMove(move);
                System.out.println(game.getStatus());
            } else {
                System.out.println("Invalid move. Please try again.");
            }
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
    private static String getPlayerInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Validate if move is legal.
     */
    private static boolean isValidMove(Move move) {
        if (move == null) {
            return false;
        }

        if (!InputValidator.isValidEdgePosition(move.getColumn(), move.getRow())) {
            return false;
        }

        return !game.getBoard().isOccupied(move.getColumn(), move.getRow());
    }
}
