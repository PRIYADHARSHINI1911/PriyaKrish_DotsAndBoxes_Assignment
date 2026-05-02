package com.entsia.dotsandboxes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Move class.
 */
public class MoveTest {

    @Test
    void testValidMoveParsing() {
        Move move = Move.parse("A1");
        assertNotNull(move);
        assertEquals(0, move.getColumn());
        assertEquals(1, move.getRow());
    }

    @Test
    void testUppercaseConversion() {
        Move move = Move.parse("a1");
        assertNotNull(move);
        assertEquals(0, move.getColumn());
        assertEquals(1, move.getRow());
    }

    @Test
    void testAllColumns() {
        for (int i = 0; i < 7; i++) {
            char colChar = (char) ('A' + i);
            Move move = Move.parse(colChar + "1");
            assertNotNull(move);
            assertEquals(i, move.getColumn());
        }
    }

    @Test
    void testAllRows() {
        for (int i = 0; i < 7; i++) {
            Move move = Move.parse("A" + i);
            assertNotNull(move);
            assertEquals(i, move.getRow());
        }
    }

    @Test
    void testInvalidColumn() {
        assertNull(Move.parse("H1"), "Column H is invalid");
        assertNull(Move.parse("Z0"), "Column Z is invalid");
        assertNull(Move.parse("@1"), "Invalid character");
    }

    @Test
    void testInvalidRow() {
        assertNull(Move.parse("A7"), "Row 7 is invalid");
        assertNull(Move.parse("A9"), "Row 9 is invalid");
        assertNull(Move.parse("A-"), "Invalid character");
    }

    @Test
    void testNullInput() {
        assertNull(Move.parse(null));
    }

    @Test
    void testWrongLength() {
        assertNull(Move.parse("A"));
        assertNull(Move.parse("A11"));
        assertNull(Move.parse(""));
    }

    @Test
    void testMoveToString() {
        Move parsed = Move.parse("A1");
        assertEquals("A1", parsed.toString());
        
        Move parsed2 = Move.parse("G6");
        assertEquals("G6", parsed2.toString());
    }

    @Test
    void testMoveEquality() {
        Move move1 = Move.parse("B2");
        Move move2 = Move.parse("B2");
        Move move3 = Move.parse("C3");

        assertEquals(move1, move2);
        assertNotEquals(move1, move3);
    }

    @Test
    void testMoveHashCode() {
        Move move1 = Move.parse("B2");
        Move move2 = Move.parse("B2");
        assertEquals(move1.hashCode(), move2.hashCode());
    }
}
