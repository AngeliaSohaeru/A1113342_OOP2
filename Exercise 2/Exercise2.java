import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Exercise2 extends JFrame {

    private static final int ANIMATION_TICKS = 10;   // number of flashes
    private static final int TICK_DELAY_MS = 50;     // 10 x 50ms = 0.5 seconds
    private static final Color DARK_GREEN = new Color(0, 150, 0);

    private final JLabel topLabel;
    private final JLabel centerLabel;
    private final JButton rollButton;
    private final JButton resetButton;

    private int rollCount = 0;
    private int totalSum = 0;
    private final Random random = new Random();

    public Exercise2() {
        // Window settings
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top label (statistics)
        topLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", SwingConstants.CENTER);
        topLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(topLabel, BorderLayout.NORTH);

        // Center label (dice result)
        centerLabel = new JLabel("-", SwingConstants.CENTER);
        centerLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        add(centerLabel, BorderLayout.CENTER);

        // Bottom panel with two buttons side by side
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        resetButton = new JButton("重置");
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JPanel southPanel = new JPanel(new GridLayout(1, 2));
        southPanel.add(rollButton);
        southPanel.add(resetButton);
        add(southPanel, BorderLayout.SOUTH);

        // Button events
        rollButton.addActionListener(e -> startRoll());
        resetButton.addActionListener(e -> resetStats());
    }

    // Flash random numbers for ~0.5s, then settle on the final value.
    // javax.swing.Timer fires on the EDT, so the window never freezes.
    private void startRoll() {
        rollButton.setEnabled(false);   // prevent overlapping animations
        resetButton.setEnabled(false);
        centerLabel.setForeground(Color.GRAY);

        final int[] ticks = {0};
        new javax.swing.Timer(TICK_DELAY_MS, e -> {
            ticks[0]++;
            if (ticks[0] < ANIMATION_TICKS) {
                // Still animating: show a throwaway random face
                centerLabel.setText(String.valueOf(random.nextInt(6) + 1));
            } else {
                // Last tick: stop the timer and record the real result
                ((javax.swing.Timer) e.getSource()).stop();
                finishRoll(random.nextInt(6) + 1);
            }
        }).start();
    }

    // Update statistics only once the animation has finished
    private void finishRoll(int diceValue) {
        rollCount++;
        totalSum += diceValue;
        double average = (double) totalSum / rollCount;

        topLabel.setText(String.format("已擲 %d 次，總和 %d，平均 %.2f", rollCount, totalSum, average));
        centerLabel.setText(String.valueOf(diceValue));

        if (diceValue == 6) {
            centerLabel.setForeground(DARK_GREEN);
        } else if (diceValue == 1) {
            centerLabel.setForeground(Color.RED);
        } else {
            centerLabel.setForeground(Color.BLACK);
        }

        rollButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    // Clear counters and restore the starting display
    private void resetStats() {
        rollCount = 0;
        totalSum = 0;
        topLabel.setText("已擲 0 次，總和 0，平均 0.00");
        centerLabel.setText("-");
        centerLabel.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Exercise2().setVisible(true));
    }
}
