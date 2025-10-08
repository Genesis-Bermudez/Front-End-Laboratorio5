package Presentation.Views;

import Domain.Dtos.auth.UserResponseDto;
import Presentation.IObserver;
import Utilities.EventType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame implements IObserver {
    private JPanel MainPanel;
    private JPanel UsernamePanel;
    private JPanel PasswordPanel;
    private JPanel ButtonsPanel;
    private JButton LoginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JDialog loadingDialog;

    public LoginView() {
        setTitle("Login");
        setContentPane(MainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 175);
        setLocationRelativeTo(null);
        createLoadingDialog();
    }

    public void addLoginListener(ActionListener listener) {
        LoginButton.addActionListener(listener);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    @Override
    public void update(EventType eventType, Object data) {
        switch (eventType) {
            case CREATED:
                UserResponseDto user = (UserResponseDto) data;
                JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername());
                break;
            case UPDATED:
            case DELETED:
                JOptionPane.showMessageDialog(this, data.toString(), "Login Info", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void createLoadingDialog() {
        loadingDialog = new JDialog(this, "Please wait...", true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Logging in..."), BorderLayout.CENTER);
        panel.add(new JProgressBar(), BorderLayout.SOUTH);
        loadingDialog.setContentPane(panel);
        loadingDialog.setSize(200, 100);
        loadingDialog.setLocationRelativeTo(this);
    }

    public void showLoading() {
        SwingUtilities.invokeLater(() -> loadingDialog.setVisible(true));
    }

    public void hideLoading() {
        SwingUtilities.invokeLater(() -> loadingDialog.setVisible(false));
    }
}
