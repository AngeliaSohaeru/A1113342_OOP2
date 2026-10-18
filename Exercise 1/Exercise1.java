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

        JLabel l1 = new JLabel("帳號:");
        JTextField t1 = new JTextField();
        
        JLabel l2 = new JLabel("密碼:");
        JPasswordField t2 = new JPasswordField();

        JButton btn = new JButton("登入");

        mainPanel.add(l1);
        mainPanel.add(t1);
        mainPanel.add(l2);
        mainPanel.add(t2);
        mainPanel.add(new JLabel());
        mainPanel.add(btn);

        add(mainPanel);

        btn.addActionListener(e -> {
            String username = t1.getText();
            String password = new String(t2.getPassword());

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
