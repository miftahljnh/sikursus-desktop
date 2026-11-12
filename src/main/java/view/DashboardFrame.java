package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.*;
import model.User;
import util.UserSession;
import util.WindowManager;

public class DashboardFrame extends JFrame {
    private JLabel lblUser;
    private JLabel lblRole;

    private JMenu menuMaster;
    private JMenu menuTransaksi;
    private JMenu menuLaporan;
    private JMenu menuKeluar;

    private JMenuItem miPeserta;
    private JMenuItem miInstruktur;
    private JMenuItem miKursus;
    private JMenuItem miKelas;

    private JMenuItem miPendaftaran;
    private JMenuItem miPembayaran;

    private JMenuItem miLaporanPeserta;
    private JMenuItem miLaporanPendaftaran;

    private JMenuItem miLogout;
    private JMenuItem miTutup;

    public DashboardFrame() {
        initComponents();
        setLocationRelativeTo(null);
        applySession();
    }

    private void initComponents() {
        setTitle("Dashboard - SIKURSUS Desktop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 500);
        setLayout(new BorderLayout());

        // 1. Menu Bar di Atas dengan Nuansa Tema Pink/Rose Elegan
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(190, 24, 93)); // Deep Rose / Magenta
        menuBar.setOpaque(true);

        // Menu Master
        menuMaster = new JMenu("Master");
        menuMaster.setForeground(Color.WHITE);
        miPeserta = new JMenuItem("Peserta");
        miInstruktur = new JMenuItem("Instruktur");
        miKursus = new JMenuItem("Kursus");
        miKelas = new JMenuItem("Kelas");

        miPeserta.setIcon(loadIcon("/icons/peserta.png"));
        miInstruktur.setIcon(loadIcon("/icons/instruktur.png"));
        miKursus.setIcon(loadIcon("/icons/kursus.png"));
        miKelas.setIcon(loadIcon("/icons/kelas.png"));

        miPeserta.setIconTextGap(10);
        miInstruktur.setIconTextGap(10);
        miKursus.setIconTextGap(10);
        miKelas.setIconTextGap(10);

        menuMaster.add(miPeserta);
        menuMaster.add(miInstruktur);
        menuMaster.add(miKursus);
        menuMaster.add(miKelas);
        menuBar.add(menuMaster);

        // Menu Transaksi
        menuTransaksi = new JMenu("Transaksi");
        menuTransaksi.setForeground(Color.WHITE);
        miPendaftaran = new JMenuItem("Pendaftaran");
        miPembayaran = new JMenuItem("Pembayaran");
        menuTransaksi.add(miPendaftaran);
        menuTransaksi.add(miPembayaran);
        menuBar.add(menuTransaksi);

        // Menu Laporan
        menuLaporan = new JMenu("Laporan");
        menuLaporan.setForeground(Color.WHITE);
        miLaporanPeserta = new JMenuItem("Laporan Peserta");
        miLaporanPendaftaran = new JMenuItem("Laporan Pendaftaran");
        menuLaporan.add(miLaporanPeserta);
        menuLaporan.add(miLaporanPendaftaran);
        menuBar.add(menuLaporan);

        // Menu Keluar
        menuKeluar = new JMenu("Keluar");
        menuKeluar.setForeground(Color.WHITE);
        miLogout = new JMenuItem("Logout");
        miTutup = new JMenuItem("Tutup Aplikasi");
        menuKeluar.add(miLogout);
        menuKeluar.addSeparator();
        menuKeluar.add(miTutup);
        menuBar.add(menuKeluar);

        setJMenuBar(menuBar);

        // 2. Panel Outer Wrapper dengan Background Soft Pink
        JPanel panelOuterWrapper = new JPanel(new BorderLayout());
        panelOuterWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                BorderFactory.createLineBorder(new Color(244, 114, 182))
        ));
        panelOuterWrapper.setBackground(new Color(253, 242, 248)); // Soft Pink

        // Panel Container Dalam (Putih)
        JPanel panelContainer = new JPanel(new BorderLayout());
        panelContainer.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panelContainer.setBackground(Color.WHITE);

        // Panel Top Header
        JPanel panelTopHeader = new JPanel(new BorderLayout());
        panelTopHeader.setBackground(Color.WHITE);
        panelTopHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel panelTitleGroup = new JPanel();
        panelTitleGroup.setLayout(new BoxLayout(panelTitleGroup, BoxLayout.Y_AXIS));
        panelTitleGroup.setBackground(Color.WHITE);

        JLabel lblAppTitle = new JLabel("SIKURSUS Desktop");
        lblAppTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblAppTitle.setForeground(new Color(190, 24, 93)); // Warna Pink Rose
        panelTitleGroup.add(lblAppTitle);
        panelTitleGroup.add(Box.createVerticalStrut(3));

        JLabel lblSubTitle = new JLabel("Sistem Informasi Manajemen Kursus & Pelatihan");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubTitle.setForeground(new Color(136, 19, 55));
        panelTitleGroup.add(lblSubTitle);

        panelTopHeader.add(panelTitleGroup, BorderLayout.WEST);

        // Info User & Role di Kanan
        JPanel panelUserRole = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panelUserRole.setBackground(Color.WHITE);
        lblUser = new JLabel("User: admin");
        lblRole = new JLabel(" | Role: ADMIN");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUser.setForeground(new Color(159, 18, 57));
        lblRole.setForeground(new Color(159, 18, 57));
        panelUserRole.add(lblUser);
        panelUserRole.add(lblRole);
        panelTopHeader.add(panelUserRole, BorderLayout.EAST);

        JPanel panelHeaderAndSeparator = new JPanel(new BorderLayout());
        panelHeaderAndSeparator.setBackground(Color.WHITE);
        panelHeaderAndSeparator.add(panelTopHeader, BorderLayout.NORTH);

        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(244, 114, 182));
        panelHeaderAndSeparator.add(separator, BorderLayout.CENTER);

        panelContainer.add(panelHeaderAndSeparator, BorderLayout.NORTH);

        // 3. Area Dashboard / Ringkasan Aplikasi di Tengah Halaman
        JPanel panelCenterArea = new JPanel(new GridBagLayout());
        panelCenterArea.setBackground(Color.WHITE);
        JLabel lblCenterText = new JLabel("✨ Area dashboard / ringkasan aplikasi ✨");
        lblCenterText.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblCenterText.setForeground(new Color(219, 39, 119));
        panelCenterArea.add(lblCenterText);

        panelContainer.add(panelCenterArea, BorderLayout.CENTER);

        panelOuterWrapper.add(panelContainer, BorderLayout.CENTER);
        add(panelOuterWrapper, BorderLayout.CENTER);

        // Popup Menu Klik Kanan pada Area Dashboard
        JPopupMenu popupDashboard = new JPopupMenu();
        JMenuItem miTentang = new JMenuItem("Tentang Aplikasi");
        JMenuItem miRefresh = new JMenuItem("Refresh");
        popupDashboard.add(miTentang);
        popupDashboard.add(miRefresh);
        panelOuterWrapper.setComponentPopupMenu(popupDashboard);

        Color menuBgColor = Color.WHITE;
        miPeserta.setBackground(menuBgColor); miPeserta.setOpaque(true);
        miInstruktur.setBackground(menuBgColor); miInstruktur.setOpaque(true);
        miKursus.setBackground(menuBgColor); miKursus.setOpaque(true); 
        miKelas.setBackground(menuBgColor); miKelas.setOpaque(true);

        // Event Listeners Navigasi
        miPeserta.addActionListener(e -> WindowManager.open(PesertaFrame.class, PesertaFrame::new));
        miInstruktur.addActionListener(e -> WindowManager.open(InstrukturFrame.class, InstrukturFrame::new));
        miKursus.addActionListener(e -> WindowManager.open(KursusFrame.class, KursusFrame::new));
        miKelas.addActionListener(e -> WindowManager.open(KelasFrame.class, KelasFrame::new));

        miPendaftaran.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modul Pendaftaran sedang dikembangkan pada tahap project berikutnya."));
        miPembayaran.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modul Pembayaran sedang dikembangkan pada tahap project berikutnya."));
        miLaporanPeserta.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modul laporan diaktifkan pada Pertemuan 12 dengan Jaspersoft Studio."));
        miLaporanPendaftaran.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modul laporan diaktifkan pada Pertemuan 12 dengan Jaspersoft Studio."));

        miLogout.addActionListener(this::miLogoutActionPerformed);
        miTutup.addActionListener(this::miTutupActionPerformed);
        miTentang.addActionListener(e -> JOptionPane.showMessageDialog(this, "SIKURSUS Desktop\nProject PBO Program Studi Informatika"));
        miRefresh.addActionListener(e -> JOptionPane.showMessageDialog(this, "Dashboard berhasil direfresh."));
    }

    private ImageIcon loadIcon(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            System.err.println("Icon tidak ditemukan: " + path);
            return null;
        }

        ImageIcon originalIcon = new ImageIcon(url);
        int targetWidth = 40;
        int targetHeight = 20;

        Image scaledImage = originalIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void applySession() {
        User user = UserSession.getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Session tidak ditemukan. Silakan login ulang.");
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
            return;
        }
        lblUser.setText("User: " + user.getUsername());
        lblRole.setText(" | Role: " + user.getRole());
        boolean admin = user.isAdmin();
        miInstruktur.setEnabled(admin);
        miKursus.setEnabled(admin);
        miKelas.setEnabled(admin);
        miPeserta.setEnabled(true);
        menuTransaksi.setEnabled(true);
        menuLaporan.setEnabled(true);
    }

    private void miLogoutActionPerformed(ActionEvent evt) {
        int pilihan = JOptionPane.showConfirmDialog(this, "Logout dari aplikasi?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            WindowManager.closeAll();
            UserSession.clear();
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        }
    }

    private void miTutupActionPerformed(ActionEvent evt) {
        int pilihan = JOptionPane.showConfirmDialog(this, "Tutup aplikasi SIKURSUS?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}