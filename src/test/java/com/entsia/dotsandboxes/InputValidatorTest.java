package com.entsia.dotsandboxes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for InputValidator class.
 */
class InputValidatorTest {

    @Test
    void testValidHorizontalEdges() {
        // Even rows (0,2,4,6), odd columns (1,3,5)
        assertTrue(InputValidator.isValidEdgePosition(1, 0), "A0 should be valid");
        assertTrue(InputValidator.isValidEdgePosition(3, 2), "D2 should be valid");
        assertTrue(InputValidator.isValidEdgePosition(5, 4), "F4 should be valid");
        assertTrue(InputValidator.isValidEdgePosition(1, 6), "B6 should be valid");
    }

    @Test
    void testValidVerticalEdges() {
        // Even columns (0,2,4,6), odd rows (1,3,5)
        assertTrue(InputValidator.isValidEdgePosition(0, 1), "A1 should be valid");
        assertTrue(InputValidator.isValidEdgePosition(2, 3), "C3 should be valid");
        assertTrue(InputValidator.isValidEdgePosition(4, 5), "E5 should be valid");
        assertTrue(InputValidator.isValidEdgePosition(6, 1), "G1 should be valid");
    }

    @Test
    void testInvalidDotPositions() {
        // Even rows, even columns (dots)
        assertFalse(InputValidator.isValidEdgePosition(0, 0), "A0 (dot) should be invalid");
        assertFalse(InputValidator.isValidEdgePosition(2, 2), "C2 (dot) should be invalid");
        assertFalse(InputValidator.isValidEdgePosition(6, 6), "G6 (dot) should be invalid");
    }

    @Test
    void testInvalidBoxPositions() {
        // Odd rows, odd columns (boxes)
        assertFalse(InputValidator.isValidEdgePosition(1, 1), "B1 (box) should be invalid");
        assertFalse(InputValidator.isValidEdgePosition(3, 3), "D3 (box) should be invalid");
        assertFalse(InputValidator.isValidEdgePosition(5, 5), "F5 (box) should be invalid");
    }

    @Test
    void testOutOfBounds() {
        assertFalse(InputValidator.isValidEdgePosition(-1, 0), "Negative column");
        assertFalse(InputValidator.isValidEdgePosition(7, 0), "Column > 6");
        assertFalse(InputValidator.isValidEdgePosition(0, -1), "Negative row");
        assertFalse(InputValidator.isValidEdgePosition(0, 7), "Row > 6");
    }

    @Test
    void testHorizontalEdgeDetection() {
        assertTrue(InputValidator.isHorizontalEdge(1, 0), "B0 is horizontal");
        assertTrue(InputValidator.isHorizontalEdge(3, 4), "D4 is horizontal");
        assertFalse(InputValidator.isHorizontalEdge(0, 1), "A1 is vertical, not horizontal");
        assertFalse(InputValidator.isHorizontalEdge(2, 3), "C3 is vertical, not horizontal");
    }

    @Test
    void testVerticalEdgeDetection() {
        assertTrue(InputValidator.isVerticalEdge(0, 1), "A1 is vertical");
        assertTrue(InputValidator.isVerticalEdge(4, 5), "E5 is vertical");
        assertFalse(InputValidator.isVerticalEdge(1, 0), "B0 is horizontal, not vertical");
        assertFalse(InputValidator.isVerticalEdge(3, 2), "D2 is horizontal, not vertical");
    }
}
