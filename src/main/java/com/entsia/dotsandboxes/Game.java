package com.entsia.dotsandboxes;

/**
 * Main game controller for Dots and Boxes.
 * Manages game state, turn order, moves, and box detection.
 */
public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private boolean gameOver;

    public Game() {
        this.board = new Board();
        this.player1 = new Player(1);
        this.player2 = new Player(2);
        this.currentPlayer = player1;  // Player 1 always goes first
        this.gameOver = false;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Process a move. Returns true if move was successful.
     */
    public boolean makeMove(Move move) {
        if (move == null || gameOver) {
            return false;
        }

        int column = move.getColumn();
        int row = move.getRow();

        // Check if position is valid
        if (!InputValidator.isValidEdgePosition(column, row)) {
            return false;
        }

        // Check if already occupied
        if (board.isOccupied(column, row)) {
            return false;
        }

        // Place the line
        board.placeLine(column, row);

        // Check for completed boxes
        int boxesCompleted = checkAndScoreBoxes(column, row, currentPlayer);

        // If no boxes completed, switch to next player
        if (boxesCompleted == 0) {
            switchPlayer();
        }

        // Check if game is over
        if (board.isFull()) {
            gameOver = true;
        }

        return true;
    }

    /**
     * Check for newly completed boxes around the placed line.
     * Returns number of boxes completed.
     */
    private int checkAndScoreBoxes(int column, int row, Player player) {
        int boxesCompleted = 0;

        if (InputValidator.isHorizontalEdge(column, row)) {
            // Horizontal line at (column, row)
            // Check box above at (column, row-1) if row >= 1
            if (row >= 1) {
                if (isBoxComplete(column, row - 1)) {
                    board.setBox(column, row - 1, player.getId());
                    player.incrementScore();
                    boxesCompleted++;
                }
            }
            // Check box below at (column, row+1) if row <= 5
            if (row <= 5) {
                if (isBoxComplete(column, row + 1)) {
                    board.setBox(column, row + 1, player.getId());
                    player.incrementScore();
                    boxesCompleted++;
                }
            }
        } else if (InputValidator.isVerticalEdge(column, row)) {
            // Vertical line at (column, row)
            // Check box to the left at (column-1, row) if column >= 1
            if (column >= 1) {
                if (isBoxComplete(column - 1, row)) {
                    board.setBox(column - 1, row, player.getId());
                    player.incrementScore();
                    boxesCompleted++;
                }
            }
            // Check box to the right at (column+1, row) if column <= 5
            if (column <= 5) {
                if (isBoxComplete(column + 1, row)) {
                    board.setBox(column + 1, row, player.getId());
                    player.incrementScore();
                    boxesCompleted++;
                }
            }
        }

        return boxesCompleted;
    }

    /**
     * Check if a box at (column, row) is complete.
     * Box position must be at odd row and odd column (1,3,5).
     * All 4 sides must be filled.
     */
    private boolean isBoxComplete(int column, int row) {
        // Check if box is already owned
        if (board.getBoxOwner(column, row) != 0) {
            return false;
        }

        // Box must have all 4 sides
        // Top: horizontal at (column, row-1)
        boolean hasTop = board.getCharAt(column, row - 1) == '-';
        // Bottom: horizontal at (column, row+1)
        boolean hasBottom = board.getCharAt(column, row + 1) == '-';
        // Left: vertical at (column-1, row)
        boolean hasLeft = board.getCharAt(column - 1, row) == '|';
        // Right: vertical at (column+1, row)
        boolean hasRight = board.getCharAt(column + 1, row) == '|';

        return hasTop && hasBottom && hasLeft && hasRight;
    }

    /**
     * Switch to the next player.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Get the winner.
     * Returns null if game is not over or it's a tie.
     */
    public Player getWinner() {
        if (!gameOver) {
            return null;
        }

        if (player1.getScore() > player2.getScore()) {
            return player1;
        } else if (player2.getScore() > player1.getScore()) {
            return player2;
        }
        return null;  // Tie
    }

    /**
     * Display current game status.
     */
    public String getStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append(board.render());
        sb.append("\nSCORE\n");
        sb.append("Player 1: ").append(player1.getScore()).append("\n");
        sb.append("Player 2: ").append(player2.getScore()).append("\n");
        return sb.toString();
    }
}
