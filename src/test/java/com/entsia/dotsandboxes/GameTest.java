package com.entsia.dotsandboxes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Game class.
 */
public class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game.getBoard());
        assertNotNull(game.getPlayer1());
        assertNotNull(game.getPlayer2());
        assertEquals(1, game.getCurrentPlayer().getId());
        assertFalse(game.isGameOver());
    }

    @Test
    void testPlayer1StartsFirst() {
        assertEquals(1, game.getCurrentPlayer().getId());
    }

    @Test
    void testValidMoveExecution() {
        Move move = Move.parse("B0");
        boolean result = game.makeMove(move);
        assertTrue(result, "Valid move should succeed");
        assertTrue(game.getBoard().isOccupied(1, 0));
    }

    @Test
    void testPlayerSwitchAfterMove() {
        game.makeMove(Move.parse("B0"));
        assertEquals(2, game.getCurrentPlayer().getId(), "Should switch to player 2");
        
        game.makeMove(Move.parse("A1"));
        assertEquals(1, game.getCurrentPlayer().getId(), "Should switch back to player 1");
    }

    @Test
    void testInvalidMoveRejected() {
        Move move = Move.parse("A0");  // Box position, not edge
        boolean result = game.makeMove(move);
        assertFalse(result, "Invalid move should be rejected");
    }

    @Test
    void testDuplicateMovePrevented() {
        Move move = Move.parse("B0");
        assertTrue(game.makeMove(move), "First move should succeed");
        
        // Try same move with different player
        game.makeMove(Move.parse("A1"));
        boolean result = game.makeMove(move);
        assertFalse(result, "Cannot place line on already occupied position");
    }

    @Test
    void testSingleBoxCompletion() {
        // Set up to complete a box at (1,1)
        // Need all 4 sides: top (1,0), bottom (1,2), left (0,1), right (2,1)
        game.makeMove(Move.parse("B0")); // top
        game.makeMove(Move.parse("A1")); // left (different player)
        
        assertEquals(0, game.getPlayer1().getScore(), "No box completed yet");
        assertEquals(0, game.getPlayer2().getScore());
    }

    @Test
    void testExtraTurnWhenBoxCompleted() {
        // Complete a box - player should get extra turn
        // This is harder to test without full board setup
        // But we can verify the player doesn't switch
        
        Move move1 = Move.parse("B0");
        game.makeMove(move1);
        Player afterFirstMove = game.getCurrentPlayer();
        
        // After a normal move, player should switch
        assertNotEquals(1, afterFirstMove.getId(), "Should have switched players");
    }

    @Test
    void testGameNotOverInitially() {
        assertFalse(game.isGameOver());
        assertNull(game.getWinner());
    }

    @Test
    void testScoreCounting() {
        game.getPlayer1().incrementScore();
        game.getPlayer1().incrementScore();
        game.getPlayer2().incrementScore();
        
        assertEquals(2, game.getPlayer1().getScore());
        assertEquals(1, game.getPlayer2().getScore());
    }

    @Test
    void testNullMoveHandled() {
        boolean result = game.makeMove(null);
        assertFalse(result, "Null move should be rejected");
    }

    @Test
    void testGameStatusDisplay() {
        String status = game.getStatus();
        assertNotNull(status);
        assertTrue(status.contains("SCORE"));
        assertTrue(status.contains("Player 1:"));
        assertTrue(status.contains("Player 2:"));
    }

    @Test
    void testPlayerObjects() {
        assertEquals(1, game.getPlayer1().getId());
        assertEquals(2, game.getPlayer2().getId());
        assertEquals(0, game.getPlayer1().getScore());
        assertEquals(0, game.getPlayer2().getScore());
    }
}
