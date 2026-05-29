# Tennis Match Tracker

A Java Swing desktop app for tracking a tennis match from rally notation, with live score updates and player statistics.

## What It Does

This app turns the original console-based tracker into a graphical desktop application. You can start a new match, choose whether the match is best-of-three or best-of-five, resume from an existing score, enter rally strings during play, delete the last rally, and view both players' stats at any time.

## Features

- Swing GUI instead of terminal input
- Live display of server, returner, sets, games, and point score
- Best-of-three and best-of-five match formats
- Optional resume-from-score setup with completed set scores
- Tiebreak support with named tiebreak scoring
- Rally-by-rally input using your existing notation
- Delete last rally
- Player stats summary dialog
- Runnable JAR for sharing

## How to Use

1. Launch the app.
2. Enter Player 1 and Player 2 names.
3. Choose the starting server.
4. Choose how many sets are needed to win the match: `2` for best-of-three or `3` for best-of-five.
5. If needed, enable the option to resume from an existing score.
6. When resuming, enter the current set score, any completed set game scores, the current game score, and the current point score.
7. If the current game is a tiebreak, tick `Current game is a tiebreak`. The current game score will be set to `6 - 6`, and the tiebreak point fields will become available.
8. Start the match.
9. During the match, type a rally string and press `Submit`.
10. Use `Delete Last` to undo the previous rally. Warning: delete only works once for the most recent rally; after deleting, enter a new rally before deleting again.
11. Use `Show Stats` to view player statistics at any time.

If the full setup screen is not visible and you cannot see the `Start Match` button, double-click the title bar at the top of the app window to resize/maximize it. You can also drag the window edges to make the setup screen taller.

During a tiebreak, the point score is shown with player names, for example `Player 1 3 - Player 2 3`, and the displayed server is the player due to serve the next point.

## Rally Input Notation

Each rally is typed as one compact string. Start with the serve, then enter each shot in order. After the serve, shots alternate between the returner and the server until the point ends.

### Serve Symbols

- `1` means first serve.
- `2` means second serve.
- `!` after a serve means the serve missed.
- `*` immediately after a serve means an ace.

Examples:

- `1*` means first-serve ace.
- `1!2*` means missed first serve, second-serve ace.
- `1!2!` means double fault.

### Shot Symbols

- `f` means forehand.
- `b` means backhand.
- `d` means drop shot.
- `s` means slice.
- `v` means volley.

Shots should be entered for both players in the order they happen. For example, in `1fbf*`, the first serve is in, then the returner hits a forehand, the server hits a backhand, and the returner hits a forehand winner.

### Point Ending Symbols

- `*` after a shot means that shot was a winner.
- `!` after a shot means that shot was an unforced error.

Examples:

- `1f!` means first serve in, returner forehand unforced error, so the server wins the point.
- `1fb*` means first serve in, returner forehand, server backhand winner.
- `1!2bf!` means first serve missed, second serve in, returner backhand, server forehand unforced error.
- `1fdv*` means first serve in, returner forehand, server drop shot, returner volley winner.

You can also type `stats` in the rally box to show stats, or `delete` to undo the previous rally. Delete only works once for the most recent rally.

## Run Locally

Make sure Java is installed, then:

1. Open Terminal.
2. Go to the folder that contains the project files and open files. If the file is in Downloads and is named `TennisAnalyticsApp.jar`, run:

```bash
cd "/Downloads/TennisAnalyticsApp.jar"
```

If it doesn't work, make sure you have Java installed on your computer, preferably Java JDK 17. 