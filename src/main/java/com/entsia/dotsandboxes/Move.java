package com.entsia.dotsandboxes;

/**
 * Represents a move in the Dots and Boxes game.
 * A move consists of placing a line at a specific board position.
 * Input format: column (A-G) + row (0-6), e.g., "A1"
 */
public class Move {
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
        if (input == null || input.length() != 2) {
            return null;
        }

        String normalized = input.toUpperCase();
        char columnChar = normalized.charAt(0);
        char rowChar = normalized.charAt(1);

        // Validate column (A-G)
        if (columnChar < 'A' || columnChar > 'G') {
            return null;
        }

        // Validate row (0-6)
        if (rowChar < '0' || rowChar > '6') {
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
        return 31 * column + row;
    }
}
