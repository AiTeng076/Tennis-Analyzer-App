import java.util.ArrayList;
import java.util.List;

public class MatchSetup {
    public String player1Name;
    public String player2Name;
    public boolean startingServerIsPlayerOne = true;
    public int setsToWin = 2;
    public boolean startInTiebreak;
    public int player1Sets;
    public int player2Sets;
    public int player1Games;
    public int player2Games;
    public int serverGamePoints;
    public int returnerGamePoints;
    public int tiebreakPlayer1Points;
    public int tiebreakPlayer2Points;
    public List<String> completedSetResults = new ArrayList<>();
}
