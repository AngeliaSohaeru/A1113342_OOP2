import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Exercise3 extends JFrame implements ActionListener {
    // Components
    private JComboBox<String> categoryBox;
    private JTextField inputField;
    private JComboBox<String> sourceUnitBox;
    private JTextField outputField;
    private JComboBox<String> targetUnitBox;
    private JButton convertButton;

    // Unit Arrays
    private String[] lengthUnits = {"公尺", "公分", "英吋", "英尺"};
    private String[] weightUnits = {"公斤", "公克", "磅", "盎司"};
    private String[] tempUnits = {"攝氏", "華氏", "克氏"};

    public Exercise3() {
        setTitle("單位換算器");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Window main layout
        setLayout(new BorderLayout());

        // --- NORTH: Category Selection ---
        String[] categories = {"長度", "重量", "溫度"};
        categoryBox = new JComboBox<>(categories);
        categoryBox.addActionListener(this);

        JPanel northPanel = new JPanel(new FlowLayout());
        northPanel.add(new JLabel("換算類型："));
        northPanel.add(categoryBox);
        add(northPanel, BorderLayout.NORTH);

        // --- CENTER: 2 Rows (TextField + JComboBox) ---
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        inputField = new JTextField("1");
        sourceUnitBox = new JComboBox<>();
        outputField = new JTextField();
        outputField.setEditable(false);
        targetUnitBox = new JComboBox<>();

        centerPanel.add(inputField);
        centerPanel.add(sourceUnitBox);
        centerPanel.add(outputField);
        centerPanel.add(targetUnitBox);

        add(centerPanel, BorderLayout.CENTER);

        // --- SOUTH: Convert Button ---
        convertButton = new JButton("換算");
        convertButton.addActionListener(this);

        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(convertButton);
        add(southPanel, BorderLayout.SOUTH);

        // Initial setup for default category
        updateUnitBoxes();
    }

    // Dynamic dropdown updates based on category
    private void updateUnitBoxes() {
        outputField.setText("");
        sourceUnitBox.removeAllItems();
        targetUnitBox.removeAllItems();

        String selectedCategory = (String) categoryBox.getSelectedItem();
        String[] unitsToLoad;

        if (selectedCategory.equals("重量")) {
            unitsToLoad = weightUnits;
        } else if (selectedCategory.equals("溫度")) {
            unitsToLoad = tempUnits;
        } else {
            unitsToLoad = lengthUnits;
        }

        for (int i = 0; i < unitsToLoad.length; i++) {
            sourceUnitBox.addItem(unitsToLoad[i]);
            targetUnitBox.addItem(unitsToLoad[i]);
        }

        if (targetUnitBox.getItemCount() > 1) {
            targetUnitBox.setSelectedIndex(1);
        }
    }

    // Action listener event handler
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == categoryBox) {
            updateUnitBoxes();
        } else if (e.getSource() == convertButton) {
            performConversion();
        }
    }

    // Conversion logic
    private void performConversion() {
        try {
            double val = Double.parseDouble(inputField.getText().trim());
            String category = (String) categoryBox.getSelectedItem();
            String src = (String) sourceUnitBox.getSelectedItem();
            String tgt = (String) targetUnitBox.getSelectedItem();

            double result = 0;

            if (category.equals("長度")) {
                result = convertLength(val, src, tgt);
            } else if (category.equals("重量")) {
                result = convertWeight(val, src, tgt);
            } else if (category.equals("溫度")) {
                result = convertTemperature(val, src, tgt);
            }

            outputField.setText(String.format("%.4f", result));
        } catch (NumberFormatException ex) {
            outputField.setText("");
            JOptionPane.showMessageDialog(this, "請輸入有效數字", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Length conversion (Base unit: meters)
    private double convertLength(double val, String src, String tgt) {
        double inMeters = val;
        if (src.equals("公分")) {
            inMeters = val / 100.0;
        } else if (src.equals("英吋")) {
            inMeters = val * 0.0254;
        } else if (src.equals("英尺")) {
            inMeters = val * 0.3048;
        }

        if (tgt.equals("公分")) {
            return inMeters * 100.0;
        } else if (tgt.equals("英吋")) {
            return inMeters / 0.0254;
        } else if (tgt.equals("英尺")) {
            return inMeters / 0.3048;
        }
        return inMeters;
    }

    // Weight conversion (Base unit: kilograms)
    private double convertWeight(double val, String src, String tgt) {
        double inKg = val;
        if (src.equals("公克")) {
            inKg = val / 1000.0;
        } else if (src.equals("磅")) {
            inKg = val * 0.45359237;
        } else if (src.equals("盎司")) {
            inKg = val * 0.0283495231;
        }

        if (tgt.equals("公克")) {
            return inKg * 1000.0;
        } else if (tgt.equals("磅")) {
            return inKg / 0.45359237;
        } else if (tgt.equals("盎司")) {
            return inKg / 0.0283495231;
        }
        return inKg;
    }

    // Temperature conversion
    private double convertTemperature(double val, String src, String tgt) {
        if (src.equals(tgt)) return val;

        double inCelsius = val;
        if (src.equals("華氏")) {
            inCelsius = (val - 32) * 5.0 / 9.0;
        } else if (src.equals("克氏")) {
            inCelsius = val - 273.15;
        }

        if (tgt.equals("華氏")) {
            return (inCelsius * 9.0 / 5.0) + 32;
        } else if (tgt.equals("克氏")) {
            return inCelsius + 273.15;
        }
        return inCelsius;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Exercise3().setVisible(true));
        Exercise3 frame = new Exercise3();
        frame.setVisible(true);
    }
}
