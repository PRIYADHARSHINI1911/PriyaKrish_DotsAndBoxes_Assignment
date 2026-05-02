package com.entsia.dotsandboxes;

/**
 * Represents a move in the Dots and Boxes game.
 * A move consists of placing a line at a specific board position.
 * Input format: column (A-G) + row (0-6), e.g., "A1"
 */
public class Move {
    private static final char COLUMN_MIN = 'A';
    private static final char COLUMN_MAX = 'G';
    private static final char ROW_MIN = '0';
    private static final char ROW_MAX = '6';
    private static final int EXPECTED_LENGTH = 2;
    private static final int HASH_MULTIPLIER = 31;
    
    private final int column;  // 0-6 (A-G)
    private final int row;     // 0-6

    private Move(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    /**
     * Parse input string like "A1" to create a Move.
     * Returns null if input is invalid.
     */
    public static Move parse(String input) {
        if (input == null || input.length() != EXPECTED_LENGTH) {
            return null;
        }

        String normalized = input.toUpperCase();
        char columnChar = normalized.charAt(0);
        char rowChar = normalized.charAt(1);

        // Validate column (A-G)
        if (columnChar < COLUMN_MIN || columnChar > COLUMN_MAX) {
            return null;
        }

        // Validate row (0-6)
        if (rowChar < ROW_MIN || rowChar > ROW_MAX) {
            return null;
        }

        int column = columnChar - 'A';
        int row = rowChar - '0';

        return new Move(column, row);
    }

    @Override
    public String toString() {
        char col = (char) ('A' + column);
        return String.format("%c%d", col, row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return column == move.column && row == move.row;
    }

    @Override
    public int hashCode() {
        return HASH_MULTIPLIER * column + row;
    }
}
