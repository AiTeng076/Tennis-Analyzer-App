# Tennis Match Tracker

A Java Swing desktop app for tracking a tennis match from rally notation, with live score updates and player statistics.

## What It Does

This app turns the original console-based tracker into a graphical desktop application. You can start a new match, resume from an existing score, enter rally strings during play, delete the last rally, and view both players' stats at any time.

## Features

- Swing GUI instead of terminal input
- Live display of server, returner, sets, games, and point score
- Optional resume-from-score setup
- Tiebreak support
- Rally-by-rally input using your existing notation
- Delete last rally
- Player stats summary dialog
- Runnable JAR for sharing

## How to Use

1. Launch the app.
2. Enter Player 1 and Player 2 names.
3. Choose the starting server.
4. If needed, enable the option to resume from an existing score and fill in the current sets, games, and points.
5. Start the match.
6. During the match, type a rally string and press `Submit`.
7. Use `Delete Last` to undo the previous rally.
8. Use `Show Stats` to view player statistics at any time.

## Run Locally

Make sure Java is installed, then run:

```bash
javac *.java
java Main
```

## Build a Shareable JAR

```bash
javac *.java
jar cfe TennisMatchTracker.jar Main *.class
```

Then run or share:

```bash
java -jar TennisMatchTracker.jar
```

Note: anyone opening the JAR will need Java installed on their computer.

## Files

- `Main.java` starts the app
- `TennisTrackerFrame.java` contains the Swing interface
- `Match.java` contains match flow and scoring
- `Player.java`, `Rally.java`, and `StatsCalculator.java` contain the tennis tracking logic

## Publishing

This project can be published on GitHub, and the compiled `TennisMatchTracker.jar` can be attached to a GitHub Release so other people can download it directly.
