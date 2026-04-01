import java.util.ArrayList;
import java.util.List;

public class Match {
    private final Player player1;
    private final Player player2;

    private Player server;
    private Player returner;

    private int serverPoints = 0;
    private int returnerPoints = 0;

    private int serverPointsBefore;
    private int returnerPointsBefore;
    private int player1GamesBefore;
    private int player2GamesBefore;
    private int player1SetsBefore;
    private int player2SetsBefore;
    private Player serverBefore;
    private Player returnerBefore;
    private boolean inTiebreakBefore;
    private int tiebreakPlayer1PointsBefore;
    private int tiebreakPlayer2PointsBefore;
    private Player tiebreakStartingServerBefore;
    private int lastTiebreakLoserPointsBefore;
    private boolean gameOverBefore;
    private int setResultsSizeBefore;

    private int player1Games = 0;
    private int player2Games = 0;

    private int player1Sets = 0;
    private int player2Sets = 0;

    private boolean inTiebreak = false;
    private int tiebreakPlayer1Points = 0;
    private int tiebreakPlayer2Points = 0;
    private Player tiebreakStartingServer;
    private int lastTiebreakLoserPoints = -1;

    private boolean gameOver;

    private final ArrayList<Rally> rallies = new ArrayList<>();
    private final ArrayList<String> setResults = new ArrayList<>();

    public Match(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.server = p1;
        this.returner = p2;
    }

    public void setInitialState(MatchSetup setup) {
        player1Sets = setup.player1Sets;
        player2Sets = setup.player2Sets;
        player1Games = setup.player1Games;
        player2Games = setup.player2Games;

        if (setup.startingServerIsPlayerOne) {
            server = player1;
            returner = player2;
        } else {
            server = player2;
            returner = player1;
        }

        if (player1Games == 6 && player2Games == 6) {
            inTiebreak = true;
            tiebreakPlayer1Points = setup.tiebreakPlayer1Points;
            tiebreakPlayer2Points = setup.tiebreakPlayer2Points;
            tiebreakStartingServer = server;
            recomputeTiebreakServer();
        } else {
            serverPoints = convertPointsToInternal(setup.serverGamePoints);
            returnerPoints = convertPointsToInternal(setup.returnerGamePoints);
        }
    }

    private int convertPointsToInternal(int p) {
        switch (p) {
            case 0:
                return 0;
            case 15:
                return 1;
            case 30:
                return 2;
            case 40:
                return 3;
            default:
                return 0;
        }
    }

    public MatchUpdate processCommand(String input) {
        String trimmed = input == null ? "" : input.trim();

        if (trimmed.isEmpty()) {
            return MatchUpdate.error("Enter a rally string, `stats`, or `delete`.");
        }
        if (Rally.isInvalidInput(trimmed)) {
            return MatchUpdate.error("Invalid input. Use your rally code, `stats`, or `delete`.");
        }
        if (trimmed.equalsIgnoreCase("stats")) {
            return MatchUpdate.info("Stats requested.");
        }
        if (trimmed.equalsIgnoreCase("delete")) {
            return deleteLastRally();
        }

        Rally rally = Rally.parse(trimmed, server, returner);
        snapshotBeforePoint();
        rallies.add(rally);

        if (isPressurePoint()) {
            server.recordPressureServe(rally.firstServe);
        }
        if (isBreakPoint()) {
            server.recordBreakPointFaced(rally.isServerPoint);
            returner.recordBreakPointOpportunity(!rally.isServerPoint);
        }

        StatsCalculator.updateStats(server, returner, rally);

        ArrayList<String> events = new ArrayList<>();

        if (inTiebreak) {
            handleTiebreakPoint(rally, events);
        } else {
            handleNormalPoint(rally, events);
        }

        if (isMatchOver()) {
            events.add("Match over: " + getWinner().getName() + " wins.");
        }

        return MatchUpdate.success(events);
    }

    private void snapshotBeforePoint() {
        serverPointsBefore = serverPoints;
        returnerPointsBefore = returnerPoints;
        player1GamesBefore = player1Games;
        player2GamesBefore = player2Games;
        player1SetsBefore = player1Sets;
        player2SetsBefore = player2Sets;
        serverBefore = server;
        returnerBefore = returner;
        inTiebreakBefore = inTiebreak;
        tiebreakPlayer1PointsBefore = tiebreakPlayer1Points;
        tiebreakPlayer2PointsBefore = tiebreakPlayer2Points;
        tiebreakStartingServerBefore = tiebreakStartingServer;
        lastTiebreakLoserPointsBefore = lastTiebreakLoserPoints;
        gameOverBefore = gameOver;
        setResultsSizeBefore = setResults.size();
    }

    private void handleTiebreakPoint(Rally rally, List<String> events) {
        if (rally.isServerPoint) {
            if (server.equals(player1)) {
                tiebreakPlayer1Points++;
            } else {
                tiebreakPlayer2Points++;
            }
        } else if (returner.equals(player1)) {
            tiebreakPlayer1Points++;
        } else {
            tiebreakPlayer2Points++;
        }

        events.add("Tiebreak: " + tiebreakPlayer1Points + " - " + tiebreakPlayer2Points
                + " | Server: " + server.getName());

        updateTiebreakServer();

        if ((tiebreakPlayer1Points >= 7 || tiebreakPlayer2Points >= 7)
                && Math.abs(tiebreakPlayer1Points - tiebreakPlayer2Points) >= 2) {

            gameOver = true;

            boolean player1Won = tiebreakPlayer1Points > tiebreakPlayer2Points;
            lastTiebreakLoserPoints = player1Won ? tiebreakPlayer2Points : tiebreakPlayer1Points;

            if (player1Won) {
                player1Sets++;
            } else {
                player2Sets++;
            }

            String setScore = (player1Won ? player1.getName() : player2.getName())
                    + " 7-6(" + lastTiebreakLoserPoints + ")";
            setResults.add(setScore);
            events.add("Set ended: " + setScore);

            player1Games = 0;
            player2Games = 0;
            tiebreakPlayer1Points = 0;
            tiebreakPlayer2Points = 0;
            inTiebreak = false;

            server = returner;
            returner = server.equals(player1) ? player2 : player1;
            events.add("New set. Starting server: " + server.getName());
        }
    }

    private void handleNormalPoint(Rally rally, List<String> events) {
        if (rally.isServerPoint) {
            serverPoints++;
        } else {
            returnerPoints++;
        }

        if (serverPoints >= 4 && serverPoints - returnerPoints >= 2) {
            gameOver = true;
            if (server.equals(player1)) {
                player1Games++;
            } else {
                player2Games++;
            }
        } else if (returnerPoints >= 4 && returnerPoints - serverPoints >= 2) {
            gameOver = true;
            if (server.equals(player1)) {
                player2Games++;
            } else {
                player1Games++;
            }
        }

        if (player1Games == 6 && player2Games == 6) {
            inTiebreak = true;
            tiebreakPlayer1Points = 0;
            tiebreakPlayer2Points = 0;
            tiebreakStartingServer = server;
            events.add("Tiebreak started.");
        } else if (player1Games >= 6 && player1Games - player2Games >= 2) {
            player1Sets++;
            String setScore = player1.getName() + " " + player1Games + "-" + player2Games;
            setResults.add(setScore);
            events.add("Set ended: " + setScore);
            player1Games = 0;
            player2Games = 0;
            server = returner;
            returner = server.equals(player1) ? player2 : player1;
            events.add("New set. Starting server: " + server.getName());
        } else if (player2Games >= 6 && player2Games - player1Games >= 2) {
            player2Sets++;
            String setScore = player2.getName() + " " + player2Games + "-" + player1Games;
            setResults.add(setScore);
            events.add("Set ended: " + setScore);
            player1Games = 0;
            player2Games = 0;
            server = returner;
            returner = server.equals(player1) ? player2 : player1;
            events.add("New set. Starting server: " + server.getName());
        } else if (gameOver) {
            events.add("Game won by " + (rally.isServerPoint ? server.getName() : returner.getName()) + ".");
            swapServerReturnerGame();
            events.add("Next server: " + server.getName());
        }
    }

    private MatchUpdate deleteLastRally() {
        if (rallies.isEmpty()) {
            return MatchUpdate.error("There is no rally to delete.");
        }

        Rally last = rallies.remove(rallies.size() - 1);

        serverPoints = serverPointsBefore;
        returnerPoints = returnerPointsBefore;
        player1Games = player1GamesBefore;
        player2Games = player2GamesBefore;
        player1Sets = player1SetsBefore;
        player2Sets = player2SetsBefore;
        server = serverBefore;
        returner = returnerBefore;
        inTiebreak = inTiebreakBefore;
        tiebreakPlayer1Points = tiebreakPlayer1PointsBefore;
        tiebreakPlayer2Points = tiebreakPlayer2PointsBefore;
        tiebreakStartingServer = tiebreakStartingServerBefore;
        lastTiebreakLoserPoints = lastTiebreakLoserPointsBefore;
        gameOver = gameOverBefore;

        while (setResults.size() > setResultsSizeBefore) {
            setResults.remove(setResults.size() - 1);
        }

        if (server != null && returner != null) {
            if (isPressurePoint()) {
                server.deletePressureServe(last.serveIn);
            }
            if (isBreakPoint()) {
                server.deleteBreakPointFaced(last.isServerPoint);
                returner.deleteBreakPointOpportunity(!last.isServerPoint);
            }
            StatsCalculator.undoStats(server, returner, last);
        }

        return MatchUpdate.success(List.of("Last rally deleted."));
    }

    private void swapServerReturnerTiebreak() {
        Player temp = server;
        server = returner;
        returner = temp;
    }

    private void updateTiebreakServer() {
        int totalPoints = tiebreakPlayer1Points + tiebreakPlayer2Points;
        if (totalPoints % 2 == 1) {
            swapServerReturnerTiebreak();
        }
    }

    private void recomputeTiebreakServer() {
        int totalPoints = tiebreakPlayer1Points + tiebreakPlayer2Points;
        server = tiebreakStartingServer;
        returner = server.equals(player1) ? player2 : player1;

        if (totalPoints == 0) {
            return;
        }
        swapServerReturnerTiebreak();
        for (int i = 1; i < totalPoints; i++) {
            if (i % 2 == 1) {
                swapServerReturnerTiebreak();
            }
        }
    }

    public boolean isPressurePoint() {
        return serverPoints >= 3 || returnerPoints >= 3;
    }

    public boolean isBreakPoint() {
        return (returnerPoints > serverPoints && serverPoints >= 3)
                || (returnerPoints >= 3 && serverPoints < 3);
    }

    public void swapServerReturnerGame() {
        Player temp = server;
        server = returner;
        returner = temp;
        serverPoints = 0;
        returnerPoints = 0;
        gameOver = false;
    }

    public String getCurrentPointScore() {
        if (inTiebreak) {
            return tiebreakPlayer1Points + " - " + tiebreakPlayer2Points;
        }
        if (serverPoints >= 3 && returnerPoints >= 3) {
            if (serverPoints == returnerPoints) {
                return "40 - 40";
            }
            if (serverPoints == returnerPoints + 1) {
                return "AD - 40";
            }
            if (returnerPoints == serverPoints + 1) {
                return "40 - AD";
            }
        }
        return getScore(serverPoints) + " - " + getScore(returnerPoints);
    }

    private String getScore(int points) {
        switch (points) {
            case 0:
                return "0";
            case 1:
                return "15";
            case 2:
                return "30";
            case 3:
                return "40";
            default:
                return "40";
        }
    }

    public String getGameScore() {
        return player1.getName() + " " + player1Games + " - " + player2.getName() + " " + player2Games;
    }

    public String getSetScore() {
        return player1.getName() + " " + player1Sets + " - " + player2.getName() + " " + player2Sets;
    }

    public String getSetResultsSummary() {
        if (setResults.isEmpty()) {
            return "No completed sets yet.";
        }
        return String.join(" / ", setResults);
    }

    public boolean isInTiebreak() {
        return inTiebreak;
    }

    public String getServerName() {
        return server.getName();
    }

    public String getReturnerName() {
        return returner.getName();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public boolean isMatchOver() {
        return player1Sets == 2 || player2Sets == 2;
    }

    public Player getWinner() {
        if (!isMatchOver()) {
            return null;
        }
        return player1Sets == 2 ? player1 : player2;
    }

    public String buildFinalSummary() {
        return "Final match score: " + getSetResultsSummary()
                + "\n\n" + player1.getName() + " stats:\n" + player1.buildSummary()
                + "\n\n" + player2.getName() + " stats:\n" + player2.buildSummary();
    }
}
