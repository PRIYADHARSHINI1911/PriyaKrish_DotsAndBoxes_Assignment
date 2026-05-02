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
    private static final int TOTAL_BOXES = 9;
    private static final char DOT = '*';
    private static final char EMPTY = ' ';
    private static final char HORIZONTAL_LINE = '-';
    private static final char VERTICAL_LINE = '|';
    private static final char COLUMN_HEADER_START = 'A';
    private static final int STEP_SIZE = 2;
    private static final String COLUMN_SPACING = "  ";
    private static final String SPACE = " ";
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
                    grid[row][col] = DOT;  // Dots
                } else {
                    grid[row][col] = EMPTY;  // Empty spaces for lines and boxes
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

        if (grid[row][column] != EMPTY) {
            return false;  // Already occupied
        }

        if (InputValidator.isHorizontalEdge(column, row)) {
            grid[row][column] = HORIZONTAL_LINE;
        } else if (InputValidator.isVerticalEdge(column, row)) {
            grid[row][column] = VERTICAL_LINE;
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
        return grid[row][column] != EMPTY;
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
        return EMPTY;
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

    public static int getTotalBoxes() {
        return TOTAL_BOXES;
    }

    /**
     * Render the board as a string.
     */
    public String render() {
        StringBuilder sb = new StringBuilder();

        // Column headers with spacing
        sb.append(COLUMN_SPACING);  // Space for row numbers on the left
        for (int col = 0; col < SIZE; col++) {
            sb.append((char) (COLUMN_HEADER_START + col));
            if (col < SIZE - 1) {
                sb.append(SPACE);  // Space between columns
            }
        }
        sb.append("\n");

        // Rows with proper spacing and row numbers on the left
        for (int row = 0; row < SIZE; row++) {
            sb.append(row).append(SPACE);  // Row number on the left
            for (int col = 0; col < SIZE; col++) {
                sb.append(grid[row][col]);
                if (col < SIZE - 1) {
                    sb.append(SPACE);  // Space between columns
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
        for (int row = 1; row < SIZE; row += STEP_SIZE) {
            for (int col = 1; col < SIZE; col += STEP_SIZE) {
                if (getBoxOwner(col, row) != 0) {
                    filledBoxes++;
                }
            }
        }
        return filledBoxes == TOTAL_BOXES;
    }
}
