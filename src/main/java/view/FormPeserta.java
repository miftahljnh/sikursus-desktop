package view;

import dao.PesertaDAO;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import model.Peserta;

public class FormPeserta extends JFrame {

    private final JTextField txtNim = new JTextField(20);
    private final JTextField txtNama = new JTextField(20);
    private final JRadioButton rbLaki = new JRadioButton("Laki-laki");
    private final JRadioButton rbPerempuan = new JRadioButton("Perempuan");
    private final ButtonGroup buttonGroupJenisKelamin = new ButtonGroup();
    private final JTextField txtProdi = new JTextField(20);
    private final JTextField txtNoHp = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JButton btnSimpan = new JButton("Simpan");

    public FormPeserta() {
        setTitle("Form Peserta - SIKURSUS");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buttonGroupJenisKelamin.add(rbLaki);
        buttonGroupJenisKelamin.add(rbPerempuan);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        JLabel judul = new JLabel("Form Peserta");
        judul.setFont(judul.getFont().deriveFont(java.awt.Font.BOLD, 18f));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(judul, c);

        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;

        tambahBaris(panel, c, 1, "NIM", txtNim);
        tambahBaris(panel, c, 2, "Nama", txtNama);

        JPanel panelJk = new JPanel();
        panelJk.add(rbLaki);
        panelJk.add(rbPerempuan);
        tambahBaris(panel, c, 3, "Jenis Kelamin", panelJk);

        tambahBaris(panel, c, 4, "Prodi", txtProdi);
        tambahBaris(panel, c, 5, "No HP", txtNoHp);
        tambahBaris(panel, c, 6, "Email", txtEmail);

        c.gridx = 1;
        c.gridy = 7;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(btnSimpan, c);

        btnSimpan.addActionListener(e -> btnSimpanActionPerformed());

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private void tambahBaris(JPanel panel, GridBagConstraints c, int baris,
                             String label, java.awt.Component isi) {
        c.gridx = 0;
        c.gridy = baris;
        c.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(label), c);

        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(isi, c);
        c.fill = GridBagConstraints.NONE;
    }

    private void clearForm() {
        txtNim.setText("");
        txtNama.setText("");
        buttonGroupJenisKelamin.clearSelection();
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtNim.requestFocus();
    }

    private void btnSimpanActionPerformed() {
        // TAHAP 1: AMBIL input
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String prodi = txtProdi.getText().trim();
        String noHp = txtNoHp.getText().trim();
        String email = txtEmail.getText().trim();

        String jenisKelamin = "";
        if (rbLaki.isSelected()) {
            jenisKelamin = "L";
        } else if (rbPerempuan.isSelected()) {
            jenisKelamin = "P";
        }

        // TAHAP 2: VALIDASI
        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty()
                || jenisKelamin.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "NIM, nama, jenis kelamin, dan prodi wajib diisi.",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // TAHAP 3: BENTUK objek
        Peserta peserta = new Peserta(
                nim, nama, jenisKelamin, prodi, noHp, email
        );

        // TAHAP 4: PANGGIL DAO
        PesertaDAO dao = new PesertaDAO();
        try {
            boolean berhasil = dao.insert(peserta);
            if (berhasil) {
                JOptionPane.showMessageDialog(
                        this, "Data peserta berhasil disimpan."
                );
                clearForm();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Data tidak tersimpan.",
                        "Informasi",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Gagal menyimpan data:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPeserta().setVisible(true));
    }
}