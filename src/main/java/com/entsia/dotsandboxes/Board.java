package com.entsia.dotsandboxes;

/**
 * Represents the game board for Dots and Boxes.
 * Board is 7x7 with:
 * - Even row, even column: dots (*)
 * - Even row, odd column: horizontal lines (-)
 * - Odd row, even column: vertical lines (|)
 * - Odd row, odd column: boxes (1, 2, or space)
 */
public class Board {
    private static final int SIZE = 7;
    private final char[][] grid;

    public Board() {
        this.grid = new char[SIZE][SIZE];
        initializeBoard();
    }

    /**
     * Initialize board with dots and empty spaces.
     */
    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (row % 2 == 0 && col % 2 == 0) {
                    grid[row][col] = '*';  // Dots
                } else {
                    grid[row][col] = ' ';  // Empty spaces for lines and boxes
                }
            }
        }
    }

    /**
     * Place a line at the given position.
     * Returns true if successful, false if already occupied.
     */
    public boolean placeLine(int column, int row) {
        if (!isValidPosition(column, row)) {
            return false;
        }

        if (grid[row][column] != ' ') {
            return false;  // Already occupied
        }

        if (InputValidator.isHorizontalEdge(column, row)) {
            grid[row][column] = '-';
        } else if (InputValidator.isVerticalEdge(column, row)) {
            grid[row][column] = '|';
        } else {
            return false;
        }

        return true;
    }

    /**
     * Check if a position already has a line.
     */
    public boolean isOccupied(int column, int row) {
        if (!isValidPosition(column, row)) {
            return false;
        }
        return grid[row][column] != ' ';
    }

    /**
     * Check if a valid edge position on the board.
     */
    private boolean isValidPosition(int column, int row) {
        return InputValidator.isValidEdgePosition(column, row);
    }

    /**
     * Get the character at a specific grid position.
     */
    public char getCharAt(int column, int row) {
        if (row >= 0 && row < SIZE && column >= 0 && column < SIZE) {
            return grid[row][column];
        }
        return ' ';
    }

    /**
     * Set a box with the player's number.
     */
    public void setBox(int column, int row, int playerNumber) {
        if (row % 2 == 1 && column % 2 == 1) {  // Valid box position
            grid[row][column] = (char) ('0' + playerNumber);
        }
    }

    /**
     * Get the owner of a box (1, 2, or 0 if empty).
     */
    public int getBoxOwner(int column, int row) {
        if (row % 2 == 1 && column % 2 == 1) {
            char c = grid[row][column];
            if (c == '1' || c == '2') {
                return c - '0';
            }
        }
        return 0;  // Empty box
    }

    /**
     * Render the board as a string.
     */
    public String render() {
        StringBuilder sb = new StringBuilder();

        // Column headers with spacing
        sb.append("  ");  // Space for row numbers on the left
        for (int col = 0; col < SIZE; col++) {
            sb.append((char) ('A' + col));
            if (col < SIZE - 1) {
                sb.append(" ");  // Space between columns
            }
        }
        sb.append("\n");

        // Rows with proper spacing and row numbers on the left
        for (int row = 0; row < SIZE; row++) {
            sb.append(row).append(" ");  // Row number on the left
            for (int col = 0; col < SIZE; col++) {
                sb.append(grid[row][col]);
                if (col < SIZE - 1) {
                    sb.append(" ");  // Space between columns
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Check if the board is full (all boxes assigned).
     */
    public boolean isFull() {
        // Total boxes = 3x3 = 9
        int filledBoxes = 0;
        for (int row = 1; row < SIZE; row += 2) {
            for (int col = 1; col < SIZE; col += 2) {
                if (getBoxOwner(col, row) != 0) {
                    filledBoxes++;
                }
            }
        }
        return filledBoxes == 9;
    }

    /**
     * Get total boxes on board (3x3 grid).
     */
    public static int getTotalBoxes() {
        return 9;
    }
}
