@echo off
REM Dots and Boxes Game - Easy Run Script for Windows
REM This script builds and runs the game

echo Building the game...
call gradlew build

if errorlevel 1 (
    echo.
    echo Build failed! Make sure you have Java 11+ installed.
    pause
    exit /b 1
)

echo.
echo Starting Dots and Boxes...
echo.

java -cp build/classes/java/main com.entsia.dotsandboxes.DotsAndBoxes

pause
