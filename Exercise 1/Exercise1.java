import javax.swing.*;
import java.awt.*;

public class Exercise1 extends JFrame {
    public Exercise1() {
        setTitle("登入");
        setSize(320, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel accountLabel = new JLabel("帳號:");
        JTextField accountField = new JTextField();
        
        JLabel passwordLabel = new JLabel("密碼:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("登入");

        mainPanel.add(new JLabel("帳號"));
        mainPanel.add(accountField);
        mainPanel.add(new JLabel("密碼:"));
        mainPanel.add(passwordField);
        mainPanel.add(new JLabel());
        mainPanel.add(loginButton);

        add(mainPanel);

        loginButton.addActionListener(e -> {
            String username = accountField.getText();
            String password = new String(passwordField.getPassword());

            if ("admin".equals(username) && "123456".equals(password)){
                JOptionPane.showMessageDialog(this, "登入成功！");
            } else {
                JOptionPane.showMessageDialog(this, "帳號或密碼錯誤！", "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Exercise1());
    }
}
