public class Player {
    private final String name;

    private int aces;
    private int doubleFaults;

    private int totalFirstServes;
    private int totalFirstServesIn;

    private int firstServePointsWon;
    private int secondServePointsWon;

    private int pressureServeAttempts;
    private int pressureServeIn;

    private int returnPointsWon;

    private int netPoints;
    private int netPointsWon;

    private int dropShots;
    private int totalSlices;
    private int totalForehandGroundStrokes;

    private int winnersFH;
    private int winnersBH;

    private int ueFH;
    private int ueBH;

    private boolean winnersGreaterThanUE;

    private int breakPointsFaced;
    private int breakPointsOpportunities;
    private int breakPointsSaved;
    private int breakPointsConverted;

    private int shots1to3PointsWon;
    private int shots4to6PointsWon;
    private int shots7to9PointsWon;
    private int shots10PlusWon;

    private int totalShotsPlayed;
    private int totalReturns;

    public Player(String name) {
        this.name = name;
    }

    public void recordPointWon() {
    }

    public void deletePointWon() {
    }

    public void recordAce() {
        aces++;
    }

    public void deleteAce() {
        aces--;
    }

    public void recordDoubleFault() {
        doubleFaults++;
    }

    public void deleteDoubleFault() {
        doubleFaults--;
    }

    public void recordFirstServeAttempt(boolean in) {
        totalFirstServes++;
        if (in) {
            totalFirstServesIn++;
        }
    }

    public void deleteFirstServeAttempt(boolean in) {
        totalFirstServes--;
        if (in) {
            totalFirstServesIn--;
        }
    }

    public void recordFirstServePointWon(boolean won) {
        if (won) {
            firstServePointsWon++;
        }
    }

    public void deleteFirstServePointWon(boolean won) {
        if (won) {
            firstServePointsWon--;
        }
    }

    public void recordSecondServePointWon(boolean won) {
        if (won) {
            secondServePointsWon++;
        }
    }

    public void deleteSecondServePointWon(boolean won) {
        if (won) {
            secondServePointsWon--;
        }
    }

    public void recordReturnPointWon() {
        returnPointsWon++;
    }

    public void recordNetPoint(boolean won) {
        netPoints++;
        if (won) {
            netPointsWon++;
        }
    }

    public void deleteNetPoint(boolean won) {
        netPoints--;
        if (won) {
            netPointsWon--;
        }
    }

    public void recordDropShot(int drop) {
        dropShots += drop;
    }

    public void deleteDropShot(int drop) {
        dropShots -= drop;
    }

    public void recordSlice(int slice) {
        totalSlices += slice;
    }

    public void deleteSlice(int slice) {
        totalSlices -= slice;
    }

    public void recordForehand(int count) {
        totalForehandGroundStrokes += count;
    }

    public void deleteForehand(int count) {
        totalForehandGroundStrokes -= count;
    }

    public void recordWinnerFH() {
        winnersFH++;
    }

    public void deleteWinnerFH() {
        winnersFH--;
    }

    public void recordWinnerBH() {
        winnersBH++;
    }

    public void deleteWinnerBH() {
        winnersBH--;
    }

    public void recordUeFH() {
        ueFH++;
    }

    public void deleteUeFH() {
        ueFH--;
    }

    public void recordUeBH() {
        ueBH++;
    }

    public void deleteUeBH() {
        ueBH--;
    }

    public void isWinnersGreaterThanUE() {
        int totalUE = ueBH + ueFH;
        int totalWinners = winnersFH + winnersBH;
        winnersGreaterThanUE = totalWinners > totalUE;
    }

    public void recordPressureServe(boolean serveIn) {
        pressureServeAttempts++;
        if (serveIn) {
            pressureServeIn++;
        }
    }

    public void deletePressureServe(boolean serveIn) {
        pressureServeAttempts--;
        if (serveIn) {
            pressureServeIn--;
        }
    }

    public void recordBreakPointFaced() {
        breakPointsFaced++;
    }

    public void deleteBreakPointFaced() {
        breakPointsFaced--;
    }

    public void recordBreakPointSaved(boolean won) {
        if (won) {
            breakPointsSaved++;
        }
    }

    public void recordBreakPointConverted() {
        breakPointsConverted++;
    }

    public void recordShotsBucket(int shotCount, boolean won) {
        switch (shotCount) {
            case 1:
            case 2:
            case 3:
                if (won) {
                    shots1to3PointsWon++;
                }
                break;
            case 4:
            case 5:
            case 6:
                if (won) {
                    shots4to6PointsWon++;
                }
                break;
            case 7:
            case 8:
            case 9:
                if (won) {
                    shots7to9PointsWon++;
                }
                break;
            default:
                if (won) {
                    shots10PlusWon++;
                }
        }
    }

    public void deleteShotsBucket(int shotCount, boolean won) {
        switch (shotCount) {
            case 1:
            case 2:
            case 3:
                if (won) {
                    shots1to3PointsWon--;
                }
                break;
            case 4:
            case 5:
            case 6:
                if (won) {
                    shots4to6PointsWon--;
                }
                break;
            case 7:
            case 8:
            case 9:
                if (won) {
                    shots7to9PointsWon--;
                }
                break;
            default:
                if (won) {
                    shots10PlusWon--;
                }
        }
    }

    public void recordShots(int count) {
        totalShotsPlayed += count;
    }

    public void deleteShots(int count) {
        totalShotsPlayed -= count;
    }

    public void recordReturn(boolean won) {
        totalReturns++;
        if (won) {
            returnPointsWon++;
        }
    }

    public void deleteReturn(boolean won) {
        totalReturns--;
        if (won) {
            returnPointsWon--;
        }
    }

    public void recordBreakPointFaced(boolean serverWon) {
        breakPointsFaced++;
        if (serverWon) {
            breakPointsSaved++;
        }
    }

    public void deleteBreakPointFaced(boolean serverWon) {
        breakPointsFaced--;
        if (serverWon) {
            breakPointsSaved--;
        }
    }

    public void recordBreakPointOpportunity(boolean returnerWonPoint) {
        breakPointsOpportunities++;
        if (returnerWonPoint) {
            breakPointsConverted++;
        }
    }

    public void deleteBreakPointOpportunity(boolean returnerWonPoint) {
        breakPointsOpportunities--;
        if (returnerWonPoint) {
            breakPointsConverted--;
        }
    }

    public String getName() {
        return name;
    }

    public double getFirstServePercentage() {
        if (totalFirstServes == 0) {
            return 0;
        }
        return 100.0 * totalFirstServesIn / totalFirstServes;
    }

    public double getFirstServePointsWonPercentage() {
        if (totalFirstServesIn == 0) {
            return 0;
        }
        return 100.0 * firstServePointsWon / totalFirstServesIn;
    }

    public double getSecondServePointsWonPercentage() {
        int secondServes = totalFirstServes - totalFirstServesIn;
        int validSecondServes = secondServes - doubleFaults;
        if (validSecondServes <= 0) {
            return 0;
        }
        return 100.0 * secondServePointsWon / validSecondServes;
    }

    public double getReturnPointsWonPercentage() {
        if (totalReturns == 0) {
            return 0;
        }
        return 100.0 * returnPointsWon / totalReturns;
    }

    public double getNetPointsWonPercentage() {
        if (netPoints == 0) {
            return 0;
        }
        return 100.0 * netPointsWon / netPoints;
    }

    public double getSlicePercentage() {
        if (totalShotsPlayed == 0) {
            return 0;
        }
        return 100.0 * totalSlices / totalShotsPlayed;
    }

    public double getForehandGroundStrokePercentage() {
        if (totalShotsPlayed == 0) {
            return 0;
        }
        return 100.0 * totalForehandGroundStrokes / totalShotsPlayed;
    }

    public double getPointsWonBucketPercentage(int bucket) {
        switch (bucket) {
            case 1:
                return shots1to3PointsWon;
            case 2:
                return shots4to6PointsWon;
            case 3:
                return shots7to9PointsWon;
            case 4:
                return shots10PlusWon;
            default:
                return 0;
        }
    }

    public double getPressureServePercentage() {
        if (pressureServeAttempts == 0) {
            return 0;
        }
        return 100.0 * pressureServeIn / pressureServeAttempts;
    }

    public double getBreakPointsSavedPercentage() {
        if (breakPointsFaced == 0) {
            return 0;
        }
        return breakPointsSaved * 100.0 / breakPointsFaced;
    }

    public double getBreakPointsConvertedPercentage() {
        if (breakPointsOpportunities == 0) {
            return 0;
        }
        return breakPointsConverted * 100.0 / breakPointsOpportunities;
    }

    public String buildSummary() {
        return "-----Stats-----\n"
                + name + "\n"
                + "Aces: " + aces + "\n"
                + "Double Faults: " + doubleFaults + "\n"
                + "1st Serve %: " + String.format("%.1f", getFirstServePercentage()) + "%\n"
                + "1st Serve Points Won %: " + String.format("%.1f", getFirstServePointsWonPercentage()) + "%\n"
                + "2nd Serve Points Won %: " + String.format("%.1f", getSecondServePointsWonPercentage()) + "%\n"
                + "1st Serve Pressure Point %: " + String.format("%.1f", getPressureServePercentage()) + "%\n"
                + "Return Points Won %: " + String.format("%.1f", getReturnPointsWonPercentage()) + "%\n"
                + "Net Points Won %: " + String.format("%.1f", getNetPointsWonPercentage()) + "%\n"
                + "Total Net Points: " + netPoints + "\n"
                + "Drop Shots: " + dropShots + "\n"
                + "Total slices: " + totalSlices + "\n"
                + "Slice %: " + String.format("%.1f", getSlicePercentage()) + "%\n"
                + "Forehand Groundstroke %: " + String.format("%.1f", getForehandGroundStrokePercentage()) + "%\n"
                + "Winners FH: " + winnersFH + ", BH: " + winnersBH + "\n"
                + "UE FH: " + ueFH + ", BH: " + ueBH + "\n"
                + "Winners > UE: " + winnersGreaterThanUE + "\n"
                + "Shots won in 1-3: " + shots1to3PointsWon + ", 4-6: " + shots4to6PointsWon
                + ", 7-9: " + shots7to9PointsWon + ", 10+: " + shots10PlusWon + "\n"
                + "Break Points Faced: " + breakPointsFaced + ", Saved: " + breakPointsSaved
                + ", Converted: " + breakPointsConverted + "\n"
                + "Break Points Saved %: " + String.format("%.1f", getBreakPointsSavedPercentage()) + "%\n"
                + "Break Points Converted %: " + String.format("%.1f", getBreakPointsConvertedPercentage()) + "%\n"
                + "---------------";
    }

    public void printSummary() {
        System.out.println(buildSummary());
    }
}
