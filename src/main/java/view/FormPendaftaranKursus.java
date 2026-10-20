package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FormPendaftaranKursus extends JFrame {

    // ===== PALET DARI GAMBAR =====
    private final Color pink = new Color(255, 202, 212);        // #FFCAD4
    private final Color pinkMuda = new Color(255, 221, 221);    // #FFDDDD
    private final Color krem = new Color(255, 241, 220);        // #FFF1DC
    private final Color peach = new Color(255, 230, 194);       // #FFE6C2

    // ===== WARNA PENDUKUNG (lebih tua agar teks & aksen terbaca) =====
    private final Color pinkTua = new Color(214, 110, 140);     // aksen tombol utama
    private final Color roseTua = new Color(110, 50, 70);       // teks utama
    private final Color roseGelap = new Color(140, 70, 95);     // label kode di header
    private final Color borderPink = new Color(240, 160, 180);  // garis tepi
    private final Color abu = new Color(150, 110, 125);         // teks deskripsi
    private final Color putih = Color.WHITE;

    private JTextField txtNama;
    private JTextField txtKursus;

    private JRadioButton rbMahasiswa;
    private JRadioButton rbUmum;

    private JComboBox<String> cmbKursus;

    private JCheckBox chkModul;
    private JCheckBox chkSertifikat;
    private JCheckBox chkRekaman;

    private JTextArea txtRingkasan;

    public FormPendaftaranKursus() {
        setTitle("Pendaftaran Kursus");
        setSize(1000, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        buatTampilan();
    }

    private void buatTampilan() {
        JPanel utama = new JPanel(new BorderLayout());
        utama.setBackground(krem);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(pink);
        header.setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel infoHeader = new JPanel();
        infoHeader.setLayout(new BoxLayout(infoHeader, BoxLayout.Y_AXIS));
        infoHeader.setOpaque(false);

        JLabel judul = new JLabel("Pendaftaran Kursus");
        judul.setFont(new Font("SansSerif", Font.BOLD, 28));
        judul.setForeground(roseTua);

        JLabel subjudul = new JLabel(
                "Lengkapi formulir untuk mendaftar program kursus pilihan Anda"
        );
        subjudul.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subjudul.setForeground(roseGelap);

        infoHeader.add(judul);
        infoHeader.add(Box.createVerticalStrut(8));
        infoHeader.add(subjudul);

        header.add(infoHeader, BorderLayout.WEST);

        JLabel labelKode = new JLabel("PBO2026");
        labelKode.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelKode.setForeground(putih);
        labelKode.setOpaque(true);
        labelKode.setBackground(roseGelap);
        labelKode.setBorder(new EmptyBorder(10, 15, 10, 15));

        header.add(labelKode, BorderLayout.EAST);

        // KONTEN UTAMA
        JPanel konten = new JPanel(new GridLayout(1, 2, 22, 0));
        konten.setBackground(krem);
        konten.setBorder(new EmptyBorder(25, 30, 25, 30));

        konten.add(buatPanelForm());
        konten.add(buatPanelRingkasan());

        utama.add(header, BorderLayout.NORTH);
        utama.add(konten, BorderLayout.CENTER);

        setContentPane(utama);
    }

    private JPanel buatPanelForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(putih);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderPink),
                new EmptyBorder(22, 22, 22, 22)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel judul = new JLabel("Formulir Peserta");
        judul.setFont(new Font("SansSerif", Font.BOLD, 21));
        judul.setForeground(roseTua);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 7, 0);
        panel.add(judul, gbc);

        JLabel deskripsi = new JLabel("Masukkan informasi pendaftaran Anda.");
        deskripsi.setFont(new Font("SansSerif", Font.PLAIN, 12));
        deskripsi.setForeground(abu);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 18, 0);
        panel.add(deskripsi, gbc);

        tambahLabel(panel, gbc, 2, "Nama Peserta");

        txtNama = buatTextField();
        tambahKomponen(panel, gbc, 3, txtNama);

        tambahLabel(panel, gbc, 4, "Kode Akses");

        txtKursus = buatTextField();
        txtKursus.setToolTipText("Masukkan kode akses PBO2026");
        tambahKomponen(panel, gbc, 5, txtKursus);

        tambahLabel(panel, gbc, 6, "Jenis Peserta");

        rbMahasiswa = new JRadioButton("Mahasiswa");
        rbUmum = new JRadioButton("Umum");

        rbMahasiswa.setBackground(putih);
        rbUmum.setBackground(putih);
        rbMahasiswa.setForeground(roseTua);
        rbUmum.setForeground(roseTua);
        rbMahasiswa.setFocusPainted(false);
        rbUmum.setFocusPainted(false);

        ButtonGroup grupJenis = new ButtonGroup();
        grupJenis.add(rbMahasiswa);
        grupJenis.add(rbUmum);

        JPanel panelJenis = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelJenis.setOpaque(false);
        panelJenis.add(rbMahasiswa);
        panelJenis.add(rbUmum);

        tambahKomponen(panel, gbc, 7, panelJenis);

        tambahLabel(panel, gbc, 8, "Pilihan Kursus");

        cmbKursus = new JComboBox<>(new String[]{
            "-- Pilih Kursus --",
            "Pemrograman Java",
            "Web Development",
            "Desain Grafis",
            "Microsoft Office",
            "Database MySQL"
        });

        cmbKursus.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cmbKursus.setPreferredSize(new Dimension(300, 36));
        cmbKursus.setBackground(pinkMuda);
        cmbKursus.setForeground(roseTua);

        tambahKomponen(panel, gbc, 9, cmbKursus);

        tambahLabel(panel, gbc, 10, "Fasilitas Tambahan");

        chkModul = buatCheckBox("Modul Cetak");
        chkSertifikat = buatCheckBox("Sertifikat");
        chkRekaman = buatCheckBox("Akses Rekaman");

        JPanel panelFasilitas = new JPanel(new GridLayout(0, 1, 0, 5));
        panelFasilitas.setOpaque(false);
        panelFasilitas.add(chkModul);
        panelFasilitas.add(chkSertifikat);
        panelFasilitas.add(chkRekaman);

        tambahKomponen(panel, gbc, 11, panelFasilitas);

        // TOMBOL DAFTAR
        JButton btnDaftar = new JButton("Daftar Sekarang");
        btnDaftar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnDaftar.setForeground(putih);
        btnDaftar.setBackground(pinkTua);
        btnDaftar.setOpaque(true);
        btnDaftar.setContentAreaFilled(true);
        btnDaftar.setFocusPainted(false);
        btnDaftar.setBorderPainted(false);
        btnDaftar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDaftar.setPreferredSize(new Dimension(200, 42));
        btnDaftar.addActionListener(e -> prosesPendaftaran());

        // TOMBOL RESET
        JButton btnReset = new JButton("Reset Form");
        btnReset.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnReset.setForeground(roseTua);
        btnReset.setBackground(peach);
        btnReset.setOpaque(true);
        btnReset.setContentAreaFilled(true);
        btnReset.setFocusPainted(false);
        btnReset.setBorderPainted(false);
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReset.setPreferredSize(new Dimension(200, 42));
        btnReset.addActionListener(e -> resetForm());

        JPanel panelTombol = new JPanel(new GridLayout(1, 2, 10, 0));
        panelTombol.setOpaque(false);
        panelTombol.add(btnDaftar);
        panelTombol.add(btnReset);

        tambahKomponen(panel, gbc, 12, panelTombol);

        return panel;
    }

    private JPanel buatPanelRingkasan() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(putih);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderPink),
                new EmptyBorder(22, 22, 22, 22)
        ));

        JLabel judul = new JLabel("Ringkasan Pendaftaran");
        judul.setFont(new Font("SansSerif", Font.BOLD, 21));
        judul.setForeground(roseTua);

        JLabel deskripsi = new JLabel("Periksa kembali informasi pendaftaran Anda.");
        deskripsi.setFont(new Font("SansSerif", Font.PLAIN, 12));
        deskripsi.setForeground(abu);

        JPanel bagianAtas = new JPanel();
        bagianAtas.setLayout(new BoxLayout(bagianAtas, BoxLayout.Y_AXIS));
        bagianAtas.setOpaque(false);
        bagianAtas.add(judul);
        bagianAtas.add(Box.createVerticalStrut(8));
        bagianAtas.add(deskripsi);

        txtRingkasan = new JTextArea();
        txtRingkasan.setEditable(false);
        txtRingkasan.setLineWrap(true);
        txtRingkasan.setWrapStyleWord(true);
        txtRingkasan.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtRingkasan.setForeground(roseTua);
        txtRingkasan.setBackground(krem);
        txtRingkasan.setBorder(new EmptyBorder(15, 15, 15, 15));
        txtRingkasan.setText(
                "Belum ada pendaftaran.\n\n"
                + "Silakan lengkapi formulir di sebelah kiri."
        );

        JScrollPane scroll = new JScrollPane(txtRingkasan);
        scroll.setBorder(BorderFactory.createLineBorder(peach));

        panel.add(bagianAtas, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void tambahLabel(
            JPanel panel,
            GridBagConstraints gbc,
            int baris,
            String teksLabel
    ) {
        gbc.gridx = 0;
        gbc.gridy = baris;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(9, 0, 5, 0);

        JLabel label = new JLabel(teksLabel);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(roseTua);

        panel.add(label, gbc);
    }

    private void tambahKomponen(
            JPanel panel,
            GridBagConstraints gbc,
            int baris,
            JComponent komponen
    ) {
        gbc.gridx = 0;
        gbc.gridy = baris;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 0);

        panel.add(komponen, gbc);
    }

    private JTextField buatTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setForeground(roseTua);
        field.setBackground(pinkMuda);
        field.setCaretColor(roseTua);
        field.setPreferredSize(new Dimension(300, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderPink),
                new EmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JCheckBox buatCheckBox(String nama) {
        JCheckBox checkBox = new JCheckBox(nama);
        checkBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        checkBox.setForeground(roseTua);
        checkBox.setBackground(putih);
        checkBox.setFocusPainted(false);
        return checkBox;
    }

    private void prosesPendaftaran() {
        String nama = txtNama.getText().trim();
        String kode = txtKursus.getText().trim();

        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nama peserta wajib diisi!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            txtNama.requestFocus();
            return;
        }

        if (!kode.equals("PBO2026")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Kode akses salah! Masukkan PBO2026.",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            txtKursus.requestFocus();
            return;
        }

        if (!rbMahasiswa.isSelected() && !rbUmum.isSelected()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Silakan pilih jenis peserta!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String jenis = rbMahasiswa.isSelected() ? "Mahasiswa" : "Umum";

        String kursus = (String) cmbKursus.getSelectedItem();

        if (kursus == null || kursus.equals("-- Pilih Kursus --")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Silakan pilih kursus!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        StringBuilder fasilitas = new StringBuilder();

        if (chkModul.isSelected()) {
            fasilitas.append("- Modul Cetak\n");
        }

        if (chkSertifikat.isSelected()) {
            fasilitas.append("- Sertifikat\n");
        }

        if (chkRekaman.isSelected()) {
            fasilitas.append("- Akses Rekaman\n");
        }

        if (fasilitas.length() == 0) {
            fasilitas.append("Tidak ada fasilitas tambahan");
        }

        txtRingkasan.setText(
                "RINGKASAN PENDAFTARAN KURSUS\n"
                + "================================\n\n"
                + "Nama Peserta : " + nama + "\n"
                + "Kode Akses   : " + kode + "\n"
                + "Jenis Peserta: " + jenis + "\n"
                + "Pilihan Kursus: " + kursus + "\n\n"
                + "Fasilitas Tambahan:\n"
                + fasilitas
                + "\nStatus: Pendaftaran berhasil"
        );

        JOptionPane.showMessageDialog(
                this,
                "Pendaftaran berhasil!",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void resetForm() {
        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin menghapus semua isian formulir?",
                "Konfirmasi Reset Form",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            txtNama.setText("");
            txtKursus.setText("");

            rbMahasiswa.setSelected(false);
            rbUmum.setSelected(false);

            cmbKursus.setSelectedIndex(0);

            chkModul.setSelected(false);
            chkSertifikat.setSelected(false);
            chkRekaman.setSelected(false);

            txtRingkasan.setText(
                    "Belum ada pendaftaran.\n\n"
                    + "Silakan lengkapi formulir di sebelah kiri."
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Semua isian formulir berhasil direset.",
                    "Reset Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new FormPendaftaranKursus().setVisible(true);
        });
    }
}