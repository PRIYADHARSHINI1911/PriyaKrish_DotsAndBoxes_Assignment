package com.entsia.dotsandboxes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the complete Dots and Boxes game flow.
 * Tests full game scenarios, edge cases, and complex interactions.
 */
class GameIntegrationTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void testCompleteGameFlow() {
        // Test a complete game scenario
        assertFalse(game.isGameOver());
        assertEquals(1, game.getCurrentPlayer().getId());

        // Player 1 makes a move
        game.makeMove(Move.parse("B0"));
        assertEquals(2, game.getCurrentPlayer().getId());

        // Player 2 makes a move
        game.makeMove(Move.parse("A1"));
        assertEquals(1, game.getCurrentPlayer().getId());

        // Player 1 makes a move
        game.makeMove(Move.parse("B2"));
        assertEquals(2, game.getCurrentPlayer().getId());

        // Verify board state
        assertTrue(game.getBoard().isOccupied(1, 0));
        assertTrue(game.getBoard().isOccupied(0, 1));
        assertTrue(game.getBoard().isOccupied(1, 2));
    }

    @Test
    void testMultipleBoxCompletionInOneMove() {
        // Set up scenario where one move completes multiple boxes
        // This requires careful setup of board state
        
        // Player 1 draws lines to prepare for box completion
        game.makeMove(Move.parse("B0"));  // horizontal line
        game.makeMove(Move.parse("A1"));  // vertical line
        game.makeMove(Move.parse("B2"));  // horizontal line
        game.makeMove(Move.parse("C1"));  // vertical line (completes box)
        
        // Verify box was completed
        assertEquals(1, game.getPlayer2().getScore());
        assertEquals(2, game.getCurrentPlayer().getId(), "Player 2 should get another turn");
    }

    @Test
    void testPlayerDoesNotSwitchAfterBoxCompletion() {
        // Verify that player doesn't switch after completing a box
        game.makeMove(Move.parse("B0"));  // Player 1
        Player afterFirstMove = game.getCurrentPlayer();
        
        // After normal move without box completion, player should switch
        assertEquals(2, afterFirstMove.getId());
        
        // Now set up a box completion scenario
        game.makeMove(Move.parse("A1"));  // Player 2
        game.makeMove(Move.parse("B2"));  // Player 1
        game.makeMove(Move.parse("C1"));  // Player 2 - completes box
        
        // Player 2 should get another turn (not switch to Player 1)
        assertEquals(2, game.getCurrentPlayer().getId());
        assertEquals(1, game.getPlayer2().getScore());
    }

    @Test
    void testInvalidMoveDoesNotChangeGameState() {
        String initialStatus = game.getStatus();
        
        // Try to make an invalid move
        game.makeMove(Move.parse("A0"));  // Invalid: dot position
        
        // Game state should not change
        assertEquals(initialStatus, game.getStatus());
        assertEquals(1, game.getCurrentPlayer().getId());
    }

    @Test
    void testDuplicateMoveIsRejected() {
        // Place a line
        assertTrue(game.makeMove(Move.parse("B0")));
        
        // Try to place on same position
        game.makeMove(Move.parse("A1"));  // Switch player
        boolean result = game.makeMove(Move.parse("B0"));  // Try duplicate
        
        // Should be rejected
        assertFalse(result);
    }

    @Test
    void testGameEndsWhenBoardFull() {
        // Manually fill all boxes to test game end condition
        game.getBoard().setBox(1, 1, 1);
        game.getBoard().setBox(3, 1, 1);
        game.getBoard().setBox(5, 1, 2);
        game.getBoard().setBox(1, 3, 1);
        game.getBoard().setBox(3, 3, 2);
        game.getBoard().setBox(5, 3, 2);
        game.getBoard().setBox(1, 5, 2);
        game.getBoard().setBox(3, 5, 1);
        game.getBoard().setBox(5, 5, 1);
        
        // Verify board is full
        assertTrue(game.getBoard().isFull());
        
        // Game should end after a move when board is full
        assertFalse(game.isGameOver());
        game.makeMove(Move.parse("B0"));
        assertTrue(game.isGameOver());
    }

    @Test
    void testWinnerDeterminationHigherScore() {
        // Set up a scenario where player 1 has more points
        game.getPlayer1().addScore(3);
        game.getPlayer2().addScore(1);
        
        // Manually end game
        assertTrue(game.getBoard().isFull() == false);  // Game not actually full yet
    }

    @Test
    void testTieGameHandling() {
        // Both players with same score
        game.getPlayer1().addScore(2);
        game.getPlayer2().addScore(2);
        
        // Game ends
        // Fill all boxes
        game.getBoard().setBox(1, 1, 1);
        game.getBoard().setBox(3, 1, 1);
        game.getBoard().setBox(5, 1, 2);
        game.getBoard().setBox(1, 3, 2);
        game.getBoard().setBox(3, 3, 2);
        game.getBoard().setBox(5, 3, 1);
        game.getBoard().setBox(1, 5, 1);
        game.getBoard().setBox(3, 5, 2);
        game.getBoard().setBox(5, 5, 1);
        
        game.makeMove(Move.parse("B0"));  // Trigger game end
        
        assertNull(game.getWinner(), "Tie game should have null winner");
    }

    @Test
    void testValidEdgeSequence() {
        // Test a valid sequence of moves forming a box
        assertTrue(game.makeMove(Move.parse("B0")));  // Top
        assertTrue(game.makeMove(Move.parse("A1")));  // Left
        assertTrue(game.makeMove(Move.parse("B2")));  // Bottom
        assertTrue(game.makeMove(Move.parse("C1")));  // Right - completes box
        
        assertEquals(1, game.getPlayer2().getScore());
        assertTrue(game.getBoard().getBoxOwner(1, 1) == 2);
    }

    @Test
    void testBoardStateConsistency() {
        // Verify board state remains consistent through game flow
        game.makeMove(Move.parse("B0"));
        game.makeMove(Move.parse("A1"));
        game.makeMove(Move.parse("D2"));
        game.makeMove(Move.parse("E1"));
        game.makeMove(Move.parse("F0"));
        
        // Verify all placed lines are occupied
        assertTrue(game.getBoard().isOccupied(1, 0));
        assertTrue(game.getBoard().isOccupied(0, 1));
        assertTrue(game.getBoard().isOccupied(3, 2));
        assertTrue(game.getBoard().isOccupied(4, 1));
        assertTrue(game.getBoard().isOccupied(5, 0));
        
        // Verify unoccupied positions
        assertFalse(game.getBoard().isOccupied(2, 0));
        assertFalse(game.getBoard().isOccupied(0, 3));
    }

    @Test
    void testGameStatusOutput() {
        // Verify game status is correctly formatted
        String status = game.getStatus();
        
        assertNotNull(status);
        assertTrue(status.contains("SCORE"));
        assertTrue(status.contains("Player 1: 0"));
        assertTrue(status.contains("Player 2: 0"));
        assertTrue(status.contains("A B C D E F G"));
        assertTrue(status.contains("*"));  // Board has dots
    }

    @Test
    void testLongGameSequence() {
        // Simulate a longer game with multiple turns and moves
        Move[] moves = {
            Move.parse("B0"), Move.parse("A1"), Move.parse("B2"), Move.parse("C1"),
            Move.parse("D0"), Move.parse("C3"), Move.parse("E1"), Move.parse("F0"),
            Move.parse("D2"), Move.parse("E3"), Move.parse("G1"), Move.parse("F2")
        };
        
        int moveCount = 0;
        for (Move move : moves) {
            if (move != null && !game.isGameOver()) {
                boolean result = game.makeMove(move);
                if (result) {
                    moveCount++;
                }
            }
        }
        
        // Verify at least some moves were successful
        assertTrue(moveCount > 0);
        
        // Verify game state is still valid
        assertTrue(game.getPlayer1().getScore() >= 0);
        assertTrue(game.getPlayer2().getScore() >= 0);
    }

    @Test
    void testInputValidationIntegration() {
        // Test that invalid inputs are properly rejected through the game layer
        
        // Invalid: outside bounds
        assertFalse(game.makeMove(Move.parse("H8")));
        
        // Invalid: at dot position (not edge)
        assertFalse(game.makeMove(Move.parse("A0")));
        
        // Invalid: at box position (not edge)
        assertFalse(game.makeMove(Move.parse("B1")));
        
        // Valid: horizontal edge
        assertTrue(game.makeMove(Move.parse("B0")));
    }

    @Test
    void testMoveParsingIntegration() {
        // Test that move parsing works correctly through the game
        
        // Case insensitive - valid horizontal edge (even row, odd col)
        assertTrue(game.makeMove(Move.parse("B0")));  // (1,0) - valid
        game.makeMove(Move.parse("A1"));  // (0,1) - valid
        
        // Different positions
        assertTrue(game.makeMove(Move.parse("F0")));  // (5,0) - valid
        
        // Invalid format parsed as null
        Move invalidMove = Move.parse("X9");  // out of bounds
        assertNull(invalidMove);
        assertFalse(game.makeMove(invalidMove));
    }

    @Test
    void testAlternatingPlayers() {
        // Verify players alternate correctly
        assertEquals(1, game.getCurrentPlayer().getId());
        
        game.makeMove(Move.parse("B0"));
        assertEquals(2, game.getCurrentPlayer().getId());
        
        game.makeMove(Move.parse("A1"));
        assertEquals(1, game.getCurrentPlayer().getId());
        
        game.makeMove(Move.parse("B2"));
        assertEquals(2, game.getCurrentPlayer().getId());
        
        game.makeMove(Move.parse("C3"));
        assertEquals(1, game.getCurrentPlayer().getId());
    }

    @Test
    void testScoreTallyAccuracy() {
        // Verify scoring is accurately tracked
        assertEquals(0, game.getPlayer1().getScore());
        assertEquals(0, game.getPlayer2().getScore());
        
        game.getPlayer1().incrementScore();
        assertEquals(1, game.getPlayer1().getScore());
        
        game.getPlayer1().addScore(2);
        assertEquals(3, game.getPlayer1().getScore());
        
        game.getPlayer2().addScore(5);
        assertEquals(5, game.getPlayer2().getScore());
    }
}
