package view;

import dao.UserDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.*;
import model.User;
import util.UserSession;

public class LoginFrame extends JFrame {
    private final UserDAO userDAO = new UserDAO();

    // ===== Tema Warna Pink =====
    private static final Color WARNA_BACKGROUND = new Color(255, 235, 243); // pink muda
    private static final Color WARNA_JUDUL      = new Color(194, 24, 91);   // pink tua
    private static final Color WARNA_SUBJUDUL   = new Color(233, 30, 99);   // pink sedang
    private static final Color WARNA_INFO       = new Color(240, 98, 146);  // pink lembut
    private static final Color WARNA_TOMBOL     = new Color(236, 64, 122);  // pink tombol login
    private static final Color WARNA_KELUAR     = new Color(220, 53, 69);   // merah (tetap)

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    private JTextField txtUsername;
    private JPasswordField pwdPassword;
    private JButton btnLogin;
    private JButton btnKeluar;
    private JLabel lblInfo;

    public LoginFrame() {
        initComponents();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnLogin);
    }

    private void initComponents() {
        setTitle("Login - SIKURSUS Desktop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 340);
        setLayout(new BorderLayout());
        getContentPane().setBackground(WARNA_BACKGROUND);

        // Panel Utama dengan Padding
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        panelMain.setBackground(WARNA_BACKGROUND);

        // 1. Judul Aplikasi
        JLabel lblTitle = new JLabel("SIKURSUS Desktop");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(WARNA_JUDUL);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMain.add(lblTitle);
        panelMain.add(Box.createVerticalStrut(5));

        // Sub-judul
        JLabel lblSubTitle = new JLabel("Sistem Informasi Manajemen Kursus & Pelatihan");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubTitle.setForeground(WARNA_SUBJUDUL);
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMain.add(lblSubTitle);
        panelMain.add(Box.createVerticalStrut(15));

        // Info Text ("Masukkan akun SIKURSUS")
        lblInfo = new JLabel("Masukkan akun SIKURSUS");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(WARNA_INFO);
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMain.add(lblInfo);
        panelMain.add(Box.createVerticalStrut(20));

        // Panel Form Input (Username & Password)
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(WARNA_BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username Label & Textfield
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUser.setForeground(WARNA_JUDUL);
        panelForm.add(lblUser, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        txtUsername = new JTextField(20);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(WARNA_SUBJUDUL),
                BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        panelForm.add(txtUsername, gbc);

        // Password Label & PasswordField
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPass.setForeground(WARNA_JUDUL);
        panelForm.add(lblPass, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        pwdPassword = new JPasswordField(20);
        pwdPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pwdPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(WARNA_SUBJUDUL),
                BorderFactory.createEmptyBorder(3, 5, 3, 5)));
        panelForm.add(pwdPassword, gbc);

        panelForm.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMain.add(panelForm);
        panelMain.add(Box.createVerticalStrut(20));

        // Panel Tombol (Login Pink, Keluar Merah)
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelButton.setBackground(WARNA_BACKGROUND);

        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogin.setBackground(WARNA_TOMBOL); // Warna Pink
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(90, 30));

        btnKeluar = new JButton("Keluar");
        btnKeluar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnKeluar.setBackground(WARNA_KELUAR); // Warna Merah
        btnKeluar.setForeground(Color.WHITE);
        btnKeluar.setFocusPainted(false);
        btnKeluar.setPreferredSize(new Dimension(90, 30));

        panelButton.add(btnLogin);
        panelButton.add(btnKeluar);
        panelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMain.add(panelButton);

        add(panelMain, BorderLayout.CENTER);

        // Event Listener Tombol
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        btnKeluar.addActionListener(this::btnKeluarActionPerformed);
    }

    private void btnLoginActionPerformed(ActionEvent evt) {
        String username = txtUsername.getText().trim();
        char[] password = pwdPassword.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnLogin.setEnabled(false);

        try {
            User user = userDAO.authenticate(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this,
                        "Login gagal. Periksa username, password, atau status user.",
                        "Login Gagal",
                        JOptionPane.WARNING_MESSAGE);
                pwdPassword.setText("");
                pwdPassword.requestFocus();
                return;
            }

            UserSession.setCurrentUser(user);
            dispose();
            SwingUtilities.invokeLater(() -> new DashboardFrame().setVisible(true));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Tidak dapat mengakses database: " + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(password, '\0');
            btnLogin.setEnabled(true);
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private void btnKeluarActionPerformed(ActionEvent evt) {
        int pilihan = JOptionPane.showConfirmDialog(this, "Tutup aplikasi SIKURSUS?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}