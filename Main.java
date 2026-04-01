import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        TennisTrackerFrame.setSystemLookAndFeel();
        SwingUtilities.invokeLater(() -> new TennisTrackerFrame().setVisible(true));
    }
}
