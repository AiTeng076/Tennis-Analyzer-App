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

public class TennisTrackerFrame extends JFrame {
    private final CardLayout cards = new CardLayout();
    private final JPanel root = new JPanel(cards);

    private final JTextField player1Field = new JTextField("Player 1", 16);
    private final JTextField player2Field = new JTextField("Player 2", 16);
    private final JComboBox<String> startingServerBox = new JComboBox<>();
    private final JCheckBox useStartingScoreCheck = new JCheckBox("Resume from an existing score");
    private final JTextField player1SetsField = new JTextField("0", 4);
    private final JTextField player2SetsField = new JTextField("0", 4);
    private final JTextField player1GamesField = new JTextField("0", 4);
    private final JTextField player2GamesField = new JTextField("0", 4);
    private final JComboBox<Integer> serverPointsBox = new JComboBox<>(new Integer[]{0, 15, 30, 40});
    private final JComboBox<Integer> returnerPointsBox = new JComboBox<>(new Integer[]{0, 15, 30, 40});
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
        player1SetsField.setEnabled(enabled);
        player2SetsField.setEnabled(enabled);
        player1GamesField.setEnabled(enabled);
        player2GamesField.setEnabled(enabled);
        serverPointsBox.setEnabled(enabled);
        returnerPointsBox.setEnabled(enabled);
        tiebreakPlayer1Field.setEnabled(enabled);
        tiebreakPlayer2Field.setEnabled(enabled);
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

        if (useStartingScoreCheck.isSelected()) {
            try {
                setup.player1Sets = parseNonNegative(player1SetsField.getText(), "Player 1 sets");
                setup.player2Sets = parseNonNegative(player2SetsField.getText(), "Player 2 sets");
                setup.player1Games = parseNonNegative(player1GamesField.getText(), "Player 1 games");
                setup.player2Games = parseNonNegative(player2GamesField.getText(), "Player 2 games");
                setup.serverGamePoints = (Integer) serverPointsBox.getSelectedItem();
                setup.returnerGamePoints = (Integer) returnerPointsBox.getSelectedItem();
                setup.tiebreakPlayer1Points = parseNonNegative(tiebreakPlayer1Field.getText(), "Tiebreak Player 1");
                setup.tiebreakPlayer2Points = parseNonNegative(tiebreakPlayer2Field.getText(), "Tiebreak Player 2");
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
