# Dots and Boxes Game

A clean, console-based implementation of the classic Dots and Boxes game in Java.

## Overview

Dots and Boxes is a two-player strategy game played on a 4×4 grid of dots. Players take turns drawing lines between adjacent dots, and when a player completes the fourth side of a box, they score a point and play again. The player with the most boxes at the end wins.

## Requirements

- **Java 11 or later** 
- Gradle (wrapper included in project)

## Quick Start

### Easiest: Quick-Start Batch File

**Windows:**
Double-click `run.bat` to build and start the game immediately.

---

### IDE (IntelliJ IDEA / STS / Eclipse)
1. Open the project in your IDE
2. Navigate to `src/main/java/com/entsia/dotsandboxes/DotsAndBoxes.java`
3. Click the **▶ Run** button 

---

### Command Line (Windows) 

#### **Command Prompt (cmd.exe) - Clean Output**
```cmd
gradlew clean build && java -cp build/classes/java/main com.entsia.dotsandboxes.DotsAndBoxes
```

#### **PowerShell**
```powershell
./gradlew clean build
java -cp build/classes/java/main com.entsia.dotsandboxes.DotsAndBoxes
```

#### **Quick: Just Run (No Build)**
```cmd
java -cp build/classes/java/main com.entsia.dotsandboxes.DotsAndBoxes
```

---

### Testing & Code Quality

**Build & Test**
```bash
./gradlew clean build
```

**Run Tests Only**
```bash
./gradlew test
```

**Generate Coverage Report**
```bash
./gradlew jacocoTestReport
```
Coverage report: `build/reports/jacoco/test/html/index.html`

**List All Tasks**
```bash
./gradlew tasks
```

---

## Testing & Verification

### Complete Testing Documentation

**[Testing_Document.pdf](docs/Testing_Document.pdf)** - Full evidence with screenshots covering:
-  Build Successful
-  Run Successful
-  Run.bat execution
-  Code Coverage metrics
-  6 Complete Testing Scenarios with screenshots:
  1. Normal Game Flow (Happy Path)
  2. Box Completion & Scoring
  3. Invalid Move Rejection
  4. Position already Occupied
  5. Winner Declaration
  6. Quit Game

### Test Coverage

- **60 Total Tests**: All passing
  - 15 Board tests
  - 13 Game logic tests
  - 10 Move parsing tests
  - 8 Input validation tests
  - 14+ Integration tests

- **Code Coverage**: 95%+ of game logic
  - Board.java: 99%
  - Game.java: 92%
  - Move.java: 96%
  - Player.java: 100%
  - InputValidator.java: 100%
  - **DotsAndBoxes.java: Excluded** (static I/O layer inherently untestable)

*Note: DotsAndBoxes contains only user input/output orchestration. Actual game logic is 92-100% covered.*

---

### How to Play
Enter moves like `A1`, `B2`, etc., or `Q` to quit.

**Example Game:**
```
Player 1: A1
Player 2: B2
Player 1: C1
...
Player 1: Q
```

## Game Rules

- **Players**: 2 players (denoted by 1 and 2)
- **Board**: 4×4 grid of dots (rendered as 7×7 with lines)
- **Turns**: Player 1 always goes first
- **Move**: Input format is `<column><row>` (e.g., `A1`, `B2`)
  - Columns: A–G (left to right)
  - Rows: 0–6 (top to bottom)
- **Box Completion**: Completing a box grants 1 point and an extra turn
- **Win Condition**: Most boxes at the end of the game
- **Quit**: Type `Q` to exit at any time (case-insensitive)

## Architecture

### Class Responsibilities

#### `DotsAndBoxes` (Main)
- Entry point for the application
- Manages the game loop
- Handles user input and output

#### `Game`
- Central game controller
- Manages turn order and game state
- Processes moves and validates them
- Detects box completion and awards points
- Determines game end and winner

#### `Board`
- Maintains the 7×7 grid state
- Stores dots, lines, and box owners
- Validates edge positions
- Renders the board for display
- Provides line placement and occupancy checks

#### `Move`
- Parses user input (e.g., "A1")
- Converts to grid coordinates (column, row)
- Handles case-insensitive input
- Provides equality and string representation

#### `Player`
- Represents a player (ID 1 or 2)
- Tracks score
- Provides score increment/update methods

#### `InputValidator`
- Validates edge positions on the board
- Distinguishes horizontal vs. vertical edges
- Ensures moves are within valid bounds

### Board Representation

The board uses a 7×7 grid where:
- **Even row, even column** = dots (`*`)
- **Even row, odd column** = horizontal lines (`-` or space)
- **Odd row, even column** = vertical lines (`|` or space)
- **Odd row, odd column** = boxes (`1`, `2`, or space)

```
ABCDEFG
0 * * * * 
1 | 
2 * * * * 
```

### Game Logic Highlights

#### Box Detection
After placing a line, the game checks up to 4 adjacent boxes (boxes sharing the newly placed edge). If all 4 sides of a box are complete, the player scores and retains their turn.

#### Valid Moves
- Must be an edge position (not a dot or box center)
- Must not already be occupied
- Format: `<column><row>` where column is A–G and row is 0–6

#### Turn Switching
- Player switches after each move that doesn't complete a box
- Extra turn granted if a box is completed

#### Game End
- Game ends when all 9 boxes are assigned
- Highest score wins

## Project Structure

```
src/
├── main/
│   └── java/com/entsia/dotsandboxes/
│       ├── DotsAndBoxes.java         (main entry point)
│       ├── Game.java                 (game controller)
│       ├── Board.java                (board state)
│       ├── Move.java                 (input parsing)
│       ├── Player.java               (player model)
│       └── InputValidator.java       (validation utility)
└── test/
    └── java/com/entsia/dotsandboxes/
        ├── GameTest.java             (game logic tests)
        ├── BoardTest.java            (board tests)
        ├── MoveTest.java             (input parsing tests)
        └── InputValidatorTest.java   (validation tests)
```

## Testing

The project includes comprehensive JUnit 5 tests covering:
- Input validation (edge positions, bounds)
- Move parsing (valid/invalid formats)
- Board state management (line placement, box ownership)
- Game logic (turn switching, scoring, box detection)
- Integration tests (complete game flows, edge cases)

**Test Statistics**:
- **60 tests total**: All passing 
  - 15 Board tests
  - 13 Game tests
  - 10 Move tests
  - 8 InputValidator tests
  - 14+ Integration tests
- **Code Coverage**: 95%+ of game logic
- **Coverage Report**: `build/reports/jacoco/test/html/index.html`


Run tests with:
```bash
./gradlew test
```

Generate code coverage report:
```bash
./gradlew jacocoTestReport
```

## Example Game Session

```
ABCDEFG
0 * * * * 
1       
2 * * * * 
3       
4 * * * * 
5       
6 * * * * 

SCORE
Player 1: 0
Player 2: 0
Player 1, input a move <column><row> (or 'Q' to quit): B0

ABCDEFG
0 *-* * * 
1       
2 * * * * 
...
```

## Code Quality

### SonarQube Compliance
The codebase meets production quality standards:
- **No resource leaks**: Scanner managed with try-with-resources
- **No magic numbers**: All constants extracted with meaningful names (Board.SIZE, Game.BOARD_MAX_BOX_ROW, etc.)
- **Minimal nested control flow**: Complex conditions flattened using logical operators (S135 compliant)
- **No code duplication**: Each concept represented in single class
- **Clean code principles**: Proper encapsulation, single responsibility, clear naming

### Jacoco Code Coverage
- **Instruction Coverage**: 95% - Most code paths executed during testing
- **HTML Report**: Generated at `build/reports/jacoco/test/html/index.html`
- **XML Report**: Generated at `build/reports/jacoco/test/jacocoTestReport.xml` (for CI/CD integration)

## Design Decisions

### Separation of Concerns
- **Game**: Manages rules and logic (turn order, scoring, box detection)
- **Board**: Handles state and rendering (grid, lines, boxes)
- **Move**: Encapsulates input parsing and conversion
- **InputValidator**: Centralizes coordinate validation
- **Player**: Simple model for identity and score

### No External Dependencies
- Pure Java implementation (except JUnit for testing)
- No frameworks or libraries
- Console-based (no UI frameworks)

### Testability
- Core game logic decoupled from input/output
- Validator is a utility class (easy to test)
- Move parsing is independent of game state
- Board operations are isolated for unit testing

## How to Play

1. Run the game using `run.bat`
2. See the board and current scores
3. Enter moves in format `<column><row>` (e.g., `A1`)
   - Columns: A–G (left to right)
   - Rows: 0–6 (top to bottom)
4. Type `Q` to quit at any time
5. The game ends when all boxes are completed
6. Winner is declared or it's a tie

## Build Configuration

- **Language**: Java
- **Build Tool**: Gradle
- **Testing**: JUnit 5
- **Source Compatibility**: Java 11+

## Git History

All commits follow a logical progression:
1. Initial Player model
2. Move parsing logic
3. Input validation
4. Board state management
5. Game controller
6. Main entry point
7. Comprehensive tests
8. Documentation

---

**Author**: Priyadharshini Krishnaswamy
**Assignment**: Dots and Boxes (19/05/21 V1.1)  
**Version**: 1.0
