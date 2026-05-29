import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TennisTrackerFrame extends JFrame {
    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);

    private final JTextField player1Field = new JTextField("Player 1", 16);
    private final JTextField player2Field = new JTextField("Player 2", 16);
    private final JComboBox<String> startingServerBox = new JComboBox<>();
    private final JComboBox<Integer> setsToWinBox = new JComboBox<>(new Integer[]{2, 3});
    private final JCheckBox useStartingScoreCheck = new JCheckBox("Resume from an existing score");
    private final JTextField player1SetsField = new JTextField("0", 4);
    private final JTextField player2SetsField = new JTextField("0", 4);
    private final JTextField set1Player1GamesField = new JTextField("0", 4);
    private final JTextField set1Player2GamesField = new JTextField("0", 4);
    private final JTextField set2Player1GamesField = new JTextField("0", 4);
    private final JTextField set2Player2GamesField = new JTextField("0", 4);
    private final JTextField set3Player1GamesField = new JTextField("0", 4);
    private final JTextField set3Player2GamesField = new JTextField("0", 4);
    private final JTextField set4Player1GamesField = new JTextField("0", 4);
    private final JTextField set4Player2GamesField = new JTextField("0", 4);
    private final JTextField player1GamesField = new JTextField("0", 4);
    private final JTextField player2GamesField = new JTextField("0", 4);
    private final JComboBox<Integer> serverPointsBox = new JComboBox<>(new Integer[]{0, 15, 30, 40});
    private final JComboBox<Integer> returnerPointsBox = new JComboBox<>(new Integer[]{0, 15, 30, 40});
    private final JCheckBox startInTiebreakCheck = new JCheckBox("Current game is a tiebreak");
    private final JTextField tiebreakPlayer1Field = new JTextField("0", 4);
    private final JTextField tiebreakPlayer2Field = new JTextField("0", 4);

    private final JLabel serverLabel = new JLabel();
    private final JLabel returnerLabel = new JLabel();
    private final JLabel setsLabel = new JLabel();
    private final JLabel gamesLabel = new JLabel();
    private final JLabel pointsLabel = new JLabel();
    private final JLabel modeLabel = new JLabel();
    private final JLabel setResultsLabel = new JLabel();

    private final JTextField rallyInputField = new JTextField(22);
    private final JTextArea logArea = new JTextArea();

    private Match match;

    public TennisTrackerFrame() {
        super("Tennis Match Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(920, 640));

        startingServerBox.addItem("Player 1 serves first");
        startingServerBox.addItem("Player 2 serves first");

        root.add(buildSetupPanel(), "setup");
        root.add(buildMatchPanel(), "match");

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        updateStartingScoreFields();
    }

    private JPanel buildSetupPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Tennis Match Setup");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Player 1 name"), gbc);
        gbc.gridx = 1;
        panel.add(player1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Player 2 name"), gbc);
        gbc.gridx = 1;
        panel.add(player2Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Starting server"), gbc);
        gbc.gridx = 1;
        panel.add(startingServerBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Sets to win match"), gbc);
        gbc.gridx = 1;
        panel.add(setsToWinBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(useStartingScoreCheck, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Player 1 sets"), gbc);
        gbc.gridx = 1;
        panel.add(player1SetsField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Player 2 sets"), gbc);
        gbc.gridx = 1;
        panel.add(player2SetsField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Completed set 1 games"), gbc);
        gbc.gridx = 1;
        panel.add(buildGameScorePanel(set1Player1GamesField, set1Player2GamesField), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Completed set 2 games"), gbc);
        gbc.gridx = 1;
        panel.add(buildGameScorePanel(set2Player1GamesField, set2Player2GamesField), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Completed set 3 games"), gbc);
        gbc.gridx = 1;
        panel.add(buildGameScorePanel(set3Player1GamesField, set3Player2GamesField), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Completed set 4 games"), gbc);
        gbc.gridx = 1;
        panel.add(buildGameScorePanel(set4Player1GamesField, set4Player2GamesField), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Player 1 games"), gbc);
        gbc.gridx = 1;
        panel.add(player1GamesField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Player 2 games"), gbc);
        gbc.gridx = 1;
        panel.add(player2GamesField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Server game points"), gbc);
        gbc.gridx = 1;
        panel.add(serverPointsBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Returner game points"), gbc);
        gbc.gridx = 1;
        panel.add(returnerPointsBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(startInTiebreakCheck, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Tiebreak points Player 1"), gbc);
        gbc.gridx = 1;
        panel.add(tiebreakPlayer1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Tiebreak points Player 2"), gbc);
        gbc.gridx = 1;
        panel.add(tiebreakPlayer2Field, gbc);

        JButton startButton = new JButton("Start Match");
        startButton.addActionListener(e -> startMatch());

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(startButton, gbc);

        useStartingScoreCheck.addActionListener(e -> updateStartingScoreFields());
        setsToWinBox.addActionListener(e -> updateStartingScoreFields());
        startInTiebreakCheck.addActionListener(e -> {
            if (startInTiebreakCheck.isSelected()) {
                player1GamesField.setText("6");
                player2GamesField.setText("6");
            }
            updateStartingScoreFields();
        });
        DocumentListener scoreListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                updateStartingScoreFields();
            }

            public void removeUpdate(DocumentEvent e) {
                updateStartingScoreFields();
            }

            public void changedUpdate(DocumentEvent e) {
                updateStartingScoreFields();
            }
        };
        player1SetsField.getDocument().addDocumentListener(scoreListener);
        player2SetsField.getDocument().addDocumentListener(scoreListener);
        return panel;
    }

    private JPanel buildGameScorePanel(JTextField player1GamesField, JTextField player2GamesField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panel.add(player1GamesField);
        panel.add(new JLabel("-"));
        panel.add(player2GamesField);
        return panel;
    }

    private JPanel buildMatchPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        scorePanel.setBorder(BorderFactory.createTitledBorder("Live Score"));

        scorePanel.add(serverLabel);
        scorePanel.add(returnerLabel);
        scorePanel.add(setsLabel);
        scorePanel.add(gamesLabel);
        scorePanel.add(pointsLabel);
        scorePanel.add(modeLabel);
        scorePanel.add(setResultsLabel);

        panel.add(scorePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Rally"));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> submitRally());
        rallyInputField.addActionListener(e -> submitRally());

        JButton deleteButton = new JButton("Delete Last");
        deleteButton.addActionListener(e -> {
            rallyInputField.setText("delete");
            submitRally();
        });

        JButton statsButton = new JButton("Show Stats");
        statsButton.addActionListener(e -> showStatsDialog());

        inputPanel.add(new JLabel("Code:"));
        inputPanel.add(rallyInputField);
        inputPanel.add(submitButton);
        inputPanel.add(deleteButton);
        inputPanel.add(statsButton);

        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);

        JButton newMatchButton = new JButton("New Match");
        newMatchButton.addActionListener(e -> {
            match = null;
            logArea.setText("");
            cards.show(root, "setup");
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(newMatchButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateStartingScoreFields() {
        boolean enabled = useStartingScoreCheck.isSelected();
        int completedSets = enabled ? getEnteredCompletedSets() : 0;
        boolean tiebreakEnabled = enabled && startInTiebreakCheck.isSelected();
        if (tiebreakEnabled) {
            player1GamesField.setText("6");
            player2GamesField.setText("6");
        }
        player1SetsField.setEnabled(enabled);
        player2SetsField.setEnabled(enabled);
        set1Player1GamesField.setEnabled(completedSets >= 1);
        set1Player2GamesField.setEnabled(completedSets >= 1);
        set2Player1GamesField.setEnabled(completedSets >= 2);
        set2Player2GamesField.setEnabled(completedSets >= 2);
        set3Player1GamesField.setEnabled(completedSets >= 3);
        set3Player2GamesField.setEnabled(completedSets >= 3);
        set4Player1GamesField.setEnabled(completedSets >= 4);
        set4Player2GamesField.setEnabled(completedSets >= 4);
        player1GamesField.setEnabled(enabled && !tiebreakEnabled);
        player2GamesField.setEnabled(enabled && !tiebreakEnabled);
        serverPointsBox.setEnabled(enabled && !tiebreakEnabled);
        returnerPointsBox.setEnabled(enabled && !tiebreakEnabled);
        startInTiebreakCheck.setEnabled(enabled);
        tiebreakPlayer1Field.setEnabled(tiebreakEnabled);
        tiebreakPlayer2Field.setEnabled(tiebreakEnabled);
    }

    private int getEnteredCompletedSets() {
        return parseOptionalNonNegative(player1SetsField.getText()) + parseOptionalNonNegative(player2SetsField.getText());
    }

    private int parseOptionalNonNegative(String value) {
        try {
            int parsed = Integer.parseInt(value.trim());
            return Math.max(parsed, 0);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void startMatch() {
        String player1Name = player1Field.getText().trim();
        String player2Name = player2Field.getText().trim();

        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both player names are required.", "Missing names",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        MatchSetup setup = new MatchSetup();
        setup.player1Name = player1Name;
        setup.player2Name = player2Name;
        setup.startingServerIsPlayerOne = startingServerBox.getSelectedIndex() == 0;
        setup.setsToWin = (Integer) setsToWinBox.getSelectedItem();

        if (useStartingScoreCheck.isSelected()) {
            try {
                setup.player1Sets = parseNonNegative(player1SetsField.getText(), "Player 1 sets");
                setup.player2Sets = parseNonNegative(player2SetsField.getText(), "Player 2 sets");
                setup.player1Games = parseNonNegative(player1GamesField.getText(), "Player 1 games");
                setup.player2Games = parseNonNegative(player2GamesField.getText(), "Player 2 games");
                validateMatchScore(setup);
                setup.completedSetResults = buildCompletedSetResults(setup);
                setup.startInTiebreak = startInTiebreakCheck.isSelected();
                if (setup.startInTiebreak && (setup.player1Games != 6 || setup.player2Games != 6)) {
                    throw new IllegalArgumentException("A tiebreak can only start from a 6 - 6 game score.");
                }
                if (!setup.startInTiebreak && setup.player1Games == 6 && setup.player2Games == 6) {
                    throw new IllegalArgumentException("Tick the tiebreak box to start from a 6 - 6 game score.");
                }
                setup.serverGamePoints = (Integer) serverPointsBox.getSelectedItem();
                setup.returnerGamePoints = (Integer) returnerPointsBox.getSelectedItem();
                if (setup.startInTiebreak) {
                    setup.tiebreakPlayer1Points = parseNonNegative(tiebreakPlayer1Field.getText(), "Tiebreak Player 1");
                    setup.tiebreakPlayer2Points = parseNonNegative(tiebreakPlayer2Field.getText(), "Tiebreak Player 2");
                    validateTiebreakScore(setup);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid starting score",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        match = new Match(new Player(setup.player1Name), new Player(setup.player2Name));
        match.setInitialState(setup);

        logArea.setText("Starting match: " + setup.player1Name + " vs " + setup.player2Name + "\n");
        refreshScoreboard();
        cards.show(root, "match");
        SwingUtilities.invokeLater(() -> rallyInputField.requestFocusInWindow());
    }

    private int parseNonNegative(String value, String label) {
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed < 0) {
                throw new IllegalArgumentException(label + " cannot be negative.");
            }
            return parsed;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(label + " must be a whole number.");
        }
    }

    private void validateTiebreakScore(MatchSetup setup) {
        if ((setup.tiebreakPlayer1Points >= 7 || setup.tiebreakPlayer2Points >= 7)
                && Math.abs(setup.tiebreakPlayer1Points - setup.tiebreakPlayer2Points) >= 2) {
            throw new IllegalArgumentException("Tiebreak points already show a completed tiebreak.");
        }
    }

    private void validateMatchScore(MatchSetup setup) {
        if (setup.player1Sets >= setup.setsToWin || setup.player2Sets >= setup.setsToWin
                || setup.player1Sets + setup.player2Sets > setup.setsToWin * 2 - 2) {
            throw new IllegalArgumentException("Starting set score must be before match point for the selected format.");
        }
        if (setup.player1Games > 6 || setup.player2Games > 6) {
            throw new IllegalArgumentException("Current set games cannot be above 6 before the next set is complete.");
        }
        if ((setup.player1Games == 6 || setup.player2Games == 6)
                && Math.abs(setup.player1Games - setup.player2Games) >= 2) {
            throw new IllegalArgumentException("Current set games already show a completed set.");
        }
    }

    private List<String> buildCompletedSetResults(MatchSetup setup) {
        int completedSets = setup.player1Sets + setup.player2Sets;
        int player1CompletedSets = 0;
        int player2CompletedSets = 0;
        java.util.ArrayList<String> results = new java.util.ArrayList<>();

        if (completedSets >= 1) {
            int p1Games = parseNonNegative(set1Player1GamesField.getText(), "Completed set 1 Player 1 games");
            int p2Games = parseNonNegative(set1Player2GamesField.getText(), "Completed set 1 Player 2 games");
            validateCompletedSetGames(p1Games, p2Games, "Completed set 1");
            if (p1Games > p2Games) {
                player1CompletedSets++;
            } else {
                player2CompletedSets++;
            }
            results.add(formatCompletedSet(setup, p1Games, p2Games));
        }

        if (completedSets >= 2) {
            int p1Games = parseNonNegative(set2Player1GamesField.getText(), "Completed set 2 Player 1 games");
            int p2Games = parseNonNegative(set2Player2GamesField.getText(), "Completed set 2 Player 2 games");
            validateCompletedSetGames(p1Games, p2Games, "Completed set 2");
            if (p1Games > p2Games) {
                player1CompletedSets++;
            } else {
                player2CompletedSets++;
            }
            results.add(formatCompletedSet(setup, p1Games, p2Games));
        }

        if (completedSets >= 3) {
            int p1Games = parseNonNegative(set3Player1GamesField.getText(), "Completed set 3 Player 1 games");
            int p2Games = parseNonNegative(set3Player2GamesField.getText(), "Completed set 3 Player 2 games");
            validateCompletedSetGames(p1Games, p2Games, "Completed set 3");
            if (p1Games > p2Games) {
                player1CompletedSets++;
            } else {
                player2CompletedSets++;
            }
            results.add(formatCompletedSet(setup, p1Games, p2Games));
        }

        if (completedSets >= 4) {
            int p1Games = parseNonNegative(set4Player1GamesField.getText(), "Completed set 4 Player 1 games");
            int p2Games = parseNonNegative(set4Player2GamesField.getText(), "Completed set 4 Player 2 games");
            validateCompletedSetGames(p1Games, p2Games, "Completed set 4");
            if (p1Games > p2Games) {
                player1CompletedSets++;
            } else {
                player2CompletedSets++;
            }
            results.add(formatCompletedSet(setup, p1Games, p2Games));
        }

        if (player1CompletedSets != setup.player1Sets || player2CompletedSets != setup.player2Sets) {
            throw new IllegalArgumentException("Completed set game scores must match the entered set score.");
        }

        return results;
    }

    private void validateCompletedSetGames(int player1Games, int player2Games, String label) {
        int winnerGames = Math.max(player1Games, player2Games);
        int loserGames = Math.min(player1Games, player2Games);
        if (player1Games == player2Games || winnerGames < 6 || winnerGames > 7 || loserGames > 6) {
            throw new IllegalArgumentException(label + " must be a completed set score, such as 6 - 4 or 7 - 6.");
        }
        if (winnerGames == 6 && loserGames > 4) {
            throw new IllegalArgumentException(label + " must be won by two games unless it is 7 - 5 or 7 - 6.");
        }
    }

    private String formatCompletedSet(MatchSetup setup, int player1Games, int player2Games) {
        String winnerName = player1Games > player2Games ? setup.player1Name : setup.player2Name;
        return winnerName + " " + player1Games + "-" + player2Games;
    }

    private void submitRally() {
        if (match == null) {
            return;
        }

        String input = rallyInputField.getText().trim();
        MatchUpdate update = match.processCommand(input);

        if (!update.isSuccess()) {
            JOptionPane.showMessageDialog(this, update.getMessage(), "Input error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (input.equalsIgnoreCase("stats")) {
            showStatsDialog();
        } else {
            appendEvents(update.getEvents());
        }

        refreshScoreboard();
        rallyInputField.setText("");

        if (match.isMatchOver()) {
            JOptionPane.showMessageDialog(this, match.buildFinalSummary(), "Match Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void appendEvents(List<String> events) {
        for (String event : events) {
            logArea.append(event + "\n");
        }
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void refreshScoreboard() {
        if (match == null) {
            return;
        }

        serverLabel.setText("Server: " + match.getServerName());
        returnerLabel.setText("Returner: " + match.getReturnerName());
        setsLabel.setText("Sets: " + match.getSetScore());
        gamesLabel.setText("Games: " + match.getGameScore());
        pointsLabel.setText("Points: " + match.getCurrentPointScore());
        modeLabel.setText(match.isInTiebreak() ? "Mode: Tiebreak" : "Mode: Standard Game");
        setResultsLabel.setText("Completed sets: " + match.getSetResultsSummary());
    }

    private void showStatsDialog() {
        if (match == null) {
            return;
        }
        String message = match.getPlayer1().buildSummary() + "\n\n" + match.getPlayer2().buildSummary();
        JTextArea area = new JTextArea(message, 24, 42);
        area.setEditable(false);
        area.setCaretPosition(0);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Player Stats",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }
}
