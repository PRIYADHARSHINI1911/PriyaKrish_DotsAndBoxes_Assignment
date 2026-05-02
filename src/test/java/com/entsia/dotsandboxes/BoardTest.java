package com.entsia.dotsandboxes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Board class.
 */
class BoardTest {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testBoardInitialization() {
        // All dots should be present
        assertEquals('*', board.getCharAt(0, 0));
        assertEquals('*', board.getCharAt(6, 6));
        assertEquals('*', board.getCharAt(2, 4));
    }

    @Test
    void testPlaceHorizontalLine() {
        boolean result = board.placeLine(1, 0);
        assertTrue(result, "Should place horizontal line at B0");
        assertEquals('-', board.getCharAt(1, 0));
    }

    @Test
    void testPlaceVerticalLine() {
        boolean result = board.placeLine(0, 1);
        assertTrue(result, "Should place vertical line at A1");
        assertEquals('|', board.getCharAt(0, 1));
    }

    @Test
    void testCannotPlaceLineOnDot() {
        boolean result = board.placeLine(0, 0);
        assertFalse(result, "Cannot place line on dot");
        assertEquals('*', board.getCharAt(0, 0));
    }

    @Test
    void testCannotPlaceLineOnBox() {
        boolean result = board.placeLine(1, 1);
        assertFalse(result, "Cannot place line on box position");
    }

    @Test
    void testCannotPlaceLineAlreadyOccupied() {
        board.placeLine(1, 0);
        boolean result = board.placeLine(1, 0);
        assertFalse(result, "Cannot place line on occupied position");
    }

    @Test
    void testIsOccupiedCheck() {
        assertFalse(board.isOccupied(1, 0), "Position should be empty initially");
        board.placeLine(1, 0);
        assertTrue(board.isOccupied(1, 0), "Position should be occupied after placing line");
    }

    @Test
    void testSetBox() {
        board.setBox(1, 1, 1);
        assertEquals('1', board.getCharAt(1, 1));
        
        board.setBox(3, 3, 2);
        assertEquals('2', board.getCharAt(3, 3));
    }

    @Test
    void testGetBoxOwner() {
        board.setBox(1, 1, 1);
        assertEquals(1, board.getBoxOwner(1, 1));
        
        board.setBox(3, 3, 2);
        assertEquals(2, board.getBoxOwner(3, 3));
        
        assertEquals(0, board.getBoxOwner(5, 5), "Empty box should return 0");
    }

    @Test
    void testBoardRender() {
        String rendered = board.render();
        assertNotNull(rendered);
        assertTrue(rendered.contains("A"), "Should contain column header A");
        assertTrue(rendered.contains("G"), "Should contain column header G");
        assertTrue(rendered.contains("*"), "Should contain dots");
        assertTrue(rendered.contains("0"), "Should contain row number 0");
    }

    @Test
    void testBoardNotFullInitially() {
        assertFalse(board.isFull(), "Board should not be full initially");
    }

    @Test
    void testBoardFull() {
        // Fill all boxes
        for (int row = 1; row < 7; row += 2) {
            for (int col = 1; col < 7; col += 2) {
                board.setBox(col, row, (row + col) % 2 + 1);
            }
        }
        assertTrue(board.isFull(), "Board should be full when all boxes are assigned");
    }

    @Test
    void testTotalBoxes() {
        assertEquals(9, Board.getTotalBoxes(), "Should be 9 boxes (3x3)");
    }
}
