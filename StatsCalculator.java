public class StatsCalculator {
    public static void updateStats(Player server, Player returner, Rally e) {
        Player hitter = e.previousHitter;
        server.recordFirstServeAttempt(e.firstServeIn);
        returner.recordReturn(!e.isServerPoint);

        if (e.firstServe) {
            if (e.isAce) {
                server.recordFirstServePointWon(true);
                server.recordAce();
            } else if (e.isServerPoint) {
                server.recordFirstServePointWon(true);
            }
        }

        if (e.secondServe) {
            if (e.isDoubleFault) {
                server.recordDoubleFault();
            } else if (e.isAce) {
                server.recordAce();
                server.recordSecondServePointWon(e.isServerPoint);
            } else if (e.isServerPoint) {
                server.recordSecondServePointWon(true);
            }
        }

        if (e.isFHWinner) {
            hitter.recordWinnerFH();
            if (hitter == server) {
                server.recordPointWon();
            } else {
                returner.recordPointWon();
            }
        } else if (e.isBHWinner) {
            hitter.recordWinnerBH();
            if (hitter == server) {
                server.recordPointWon();
            } else {
                returner.recordPointWon();
            }
        } else if (e.isFHUnforcedError) {
            hitter.recordUeFH();
            if (hitter == server) {
                returner.recordPointWon();
            } else {
                server.recordPointWon();
            }
        } else if (e.isBHUnforcedError) {
            hitter.recordUeBH();
            if (hitter == server) {
                returner.recordPointWon();
            } else {
                server.recordPointWon();
            }
        }

        server.isWinnersGreaterThanUE();
        returner.isWinnersGreaterThanUE();

        server.recordDropShot(e.serverDrop);
        returner.recordDropShot(e.returnerDrop);

        server.recordSlice(e.serverSlice);
        returner.recordSlice(e.returnerSlice);

        if (e.isVolley) {
            if (hitter.equals(server) && e.lastShotVolley) {
                server.recordNetPoint(e.isServerPoint);
            } else if (hitter.equals(returner) && e.lastShotVolley) {
                returner.recordNetPoint(!e.isServerPoint);
            } else if (hitter.equals(server)) {
                returner.recordNetPoint(!e.isServerPoint);
            } else if (hitter.equals(returner)) {
                server.recordNetPoint(e.isServerPoint);
            }
        }

        server.recordForehand(e.serverForehand);
        returner.recordForehand(e.returnerForehand);

        server.recordShots(e.serverShotCount);
        returner.recordShots(e.returnerShotCount);

        server.recordShotsBucket(e.shotcount, e.isServerPoint);
        returner.recordShotsBucket(e.shotcount, !e.isServerPoint);
    }

    public static void undoStats(Player server, Player returner, Rally e) {
        Player hitter = e.previousHitter;
        server.deleteFirstServeAttempt(e.firstServeIn);
        returner.deleteReturn(!e.isServerPoint);

        if (e.firstServe) {
            if (e.isAce) {
                server.deleteFirstServePointWon(true);
                server.deleteAce();
            } else if (e.isServerPoint) {
                server.deleteFirstServePointWon(true);
            }
        }

        if (e.secondServe) {
            if (e.isDoubleFault) {
                server.deleteDoubleFault();
            } else if (e.isAce) {
                server.deleteAce();
                server.deleteSecondServePointWon(e.isServerPoint);
            } else if (e.isServerPoint) {
                server.deleteSecondServePointWon(true);
            }
        }

        if (e.isFHWinner) {
            hitter.deleteWinnerFH();
        } else if (e.isBHWinner) {
            hitter.deleteWinnerBH();
            if (hitter == server) {
                server.deletePointWon();
            } else {
                returner.deletePointWon();
            }
        } else if (e.isFHUnforcedError) {
            hitter.deleteUeFH();
            if (hitter == server) {
                returner.deletePointWon();
            } else {
                server.deletePointWon();
            }
        } else if (e.isBHUnforcedError) {
            hitter.deleteUeBH();
            if (hitter == server) {
                returner.deletePointWon();
            } else {
                server.deletePointWon();
            }
        }

        server.isWinnersGreaterThanUE();
        returner.isWinnersGreaterThanUE();

        server.deleteDropShot(e.serverDrop);
        returner.deleteDropShot(e.returnerDrop);

        server.deleteSlice(e.serverSlice);
        returner.deleteSlice(e.returnerSlice);

        if (e.isVolley) {
            if (hitter.equals(server) && e.lastShotVolley) {
                server.deleteNetPoint(e.isServerPoint);
            } else if (hitter.equals(returner) && e.lastShotVolley) {
                returner.deleteNetPoint(!e.isServerPoint);
            } else if (hitter.equals(server)) {
                returner.deleteNetPoint(!e.isServerPoint);
            } else if (hitter.equals(returner)) {
                server.deleteNetPoint(e.isServerPoint);
            }
        }

        server.deleteForehand(e.serverForehand);
        returner.deleteForehand(e.returnerForehand);

        server.deleteShots(e.serverShotCount);
        returner.deleteShots(e.returnerShotCount);

        server.deleteShotsBucket(e.shotcount, e.isServerPoint);
        returner.deleteShotsBucket(e.shotcount, !e.isServerPoint);
    }
}
