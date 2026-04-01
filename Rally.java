public class Rally {
    boolean firstServe;
    boolean secondServe;
    boolean serveIn;
    boolean firstServeIn;

    boolean isAce;
    boolean isDoubleFault;

    boolean isFHWinner;
    boolean isBHWinner;
    boolean isFHUnforcedError;
    boolean isBHUnforcedError;

    boolean isDropshot;
    int serverDrop;
    int returnerDrop;
    boolean isSlice;
    int serverSlice;
    int returnerSlice;
    boolean isVolley;
    boolean lastShotVolley;

    boolean lastShotForehand;
    boolean lastShotBackhand;

    int serverForehand;
    int returnerForehand;

    char lastShot;
    String rawInput;

    boolean isServerPoint;

    int serverShotCount;
    int returnerShotCount;
    int shotcount;

    Player previousHitter;

    Player server;
    Player returner;

    Player winner;

    public Rally() {
        this.firstServe = false;
        this.secondServe = false;
        this.serveIn = false;
        this.firstServeIn = false;

        this.isAce = false;
        this.isDoubleFault = false;

        this.isFHWinner = false;
        this.isBHWinner = false;
        this.isFHUnforcedError = false;
        this.isBHUnforcedError = false;

        this.isDropshot = false;
        this.serverDrop = 0;
        this.returnerDrop = 0;
        this.isSlice = false;
        this.serverSlice = 0;
        this.returnerSlice = 0;

        this.isVolley = false;
        this.lastShotVolley = false;

        this.lastShotForehand = false;
        this.lastShotBackhand = false;

        this.serverForehand = 0;
        this.returnerForehand = 0;

        this.isServerPoint = false;

        this.serverShotCount = 0;
        this.returnerShotCount = 0;
        this.shotcount = 0;
    }

    public static boolean isInvalidInput(String input) {
        if (input == null) {
            return true;
        }
        input = input.trim();
        if (input.equalsIgnoreCase("delete") || input.equalsIgnoreCase("stats")) {
            return false;
        }
        if (input.length() < 2 || input.equals("1!")) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            String c = input.substring(i, i + 1);
            if (!c.equals("v")
                    && !c.equals("d")
                    && !c.equals("f")
                    && !c.equals("b")
                    && !c.equals("s")
                    && !c.equals("1")
                    && !c.equals("2")
                    && !c.equals("!")
                    && !c.equals("*")) {
                return true;
            }
        }
        return input.indexOf('1') == -1 && input.indexOf('2') == -1;
    }

    public static Rally parse(String input, Player server, Player returner) {
        Rally r = new Rally();
        input = input.trim();

        r.firstServe = input.contains("1");
        r.secondServe = input.contains("2");

        r.server = server;
        r.returner = returner;

        if (r.firstServe) {
            if (!r.secondServe) {
                r.serveIn = true;
            }
        }
        if (r.secondServe) {
            r.serveIn = !input.substring(1, 2).equals("!");
        }

        if (r.firstServe && !r.secondServe) {
            r.firstServeIn = true;
        }

        if (input.length() >= 2) {
            String ending = input.substring(input.length() - 2);
            if (ending.equals("f!")) {
                r.isFHUnforcedError = true;
            } else if (ending.equals("b!")) {
                r.isBHUnforcedError = true;
            } else if (ending.equals("f*")) {
                r.isFHWinner = true;
            } else if (ending.equals("b*")) {
                r.isBHWinner = true;
            }
        }

        if (input.startsWith("1*") || input.startsWith("2*")) {
            r.isAce = true;
        }

        if (input.contains("2!")) {
            r.isDoubleFault = true;
        }

        int strokes = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '1' || c == '2' || c == '!' || c == '*') {
                continue;
            }

            strokes++;
            if (c == 'd') {
                r.isDropshot = true;
                if (i % 2 == 0) {
                    r.serverDrop++;
                } else {
                    r.returnerDrop++;
                }
            }
            if (c == 's') {
                r.isSlice = true;
                if (i % 2 == 0) {
                    r.serverSlice++;
                } else {
                    r.returnerSlice++;
                }
            }
            if (c == 'f') {
                if (i % 2 == 0) {
                    r.serverForehand++;
                } else {
                    r.returnerForehand++;
                }
            }
        }

        if (input.endsWith("v")
                || input.substring(Math.max(0, input.length() - 2), input.length() - 1).equals("v")) {
            r.isVolley = true;
        } else if (input.length() >= 3
                && input.substring(input.length() - 3, input.length() - 2).equals("v")
                && (input.endsWith("*") || input.endsWith("!"))) {
            r.isVolley = true;
        }

        if (input.endsWith("v")) {
            r.lastShotVolley = true;
        } else if (input.length() >= 2
                && input.substring(input.length() - 2, input.length() - 1).equals("v")
                && (input.endsWith("*") || input.endsWith("!"))) {
            r.lastShotVolley = true;
        }

        if (strokes % 2 == 1 && !input.endsWith("*")) {
            r.isServerPoint = true;
        } else if (input.endsWith("*") && strokes % 2 == 0) {
            r.isServerPoint = true;
        }

        if (strokes % 2 == 0) {
            r.previousHitter = server;
        } else {
            r.previousHitter = returner;
        }

        r.shotcount = strokes + 1;
        r.serverShotCount = strokes / 2;
        r.returnerShotCount = (strokes + 1) / 2;
        r.lastShot = input.charAt(input.length() - 1);
        r.rawInput = input;

        if (input.charAt(input.length() - 1) == 'f') {
            r.lastShotForehand = true;
        } else if (input.charAt(input.length() - 1) == 'b') {
            r.lastShotBackhand = true;
        } else if ((input.charAt(input.length() - 1) == '!' || input.charAt(input.length() - 1) == '*')
                && input.charAt(input.length() - 2) == 'f') {
            r.lastShotForehand = true;
        }

        r.winner = r.isServerPoint ? server : returner;
        return r;
    }
}
