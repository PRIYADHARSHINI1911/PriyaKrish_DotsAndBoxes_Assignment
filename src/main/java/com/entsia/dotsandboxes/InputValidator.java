package com.entsia.dotsandboxes;

/**
 * Utility class for validating input and moves on the board.
 */
public class InputValidator {

    /**
     * Check if a move is a valid edge position on the board.
     * Valid positions are between dots:
     * - Horizontal edges: even rows (0,2,4,6), columns 0-6
     * - Vertical edges: even columns (0,2,4,6), rows 0-6
     */
    public static boolean isValidEdgePosition(int column, int row) {
        // Must be within board bounds
        if (column < 0 || column > 6 || row < 0 || row > 6) {
            return false;
        }

        // Must be on an edge (either even row or even column, but not both)
        boolean evenColumn = (column % 2 == 0);
        boolean evenRow = (row % 2 == 0);

        // Valid edges are:
        // - Vertical edges: even columns (between dots horizontally) at odd rows
        // - Horizontal edges: even rows (between dots vertically) at odd columns
        return (evenColumn && row % 2 == 1) || (evenRow && column % 2 == 1);
    }

    /**
     * Determine if the edge at this position is horizontal or vertical.
     * Assumes isValidEdgePosition has already been validated.
     */
    public static boolean isHorizontalEdge(int column, int row) {
        // Horizontal edges: even rows, odd columns
        return row % 2 == 0 && column % 2 == 1;
    }

    /**
     * Determine if the edge at this position is vertical.
     * Assumes isValidEdgePosition has already been validated.
     */
    public static boolean isVerticalEdge(int column, int row) {
        // Vertical edges: even columns, odd rows
        return column % 2 == 0 && row % 2 == 1;
    }
}
