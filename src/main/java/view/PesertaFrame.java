package view;

import dao.PesertaDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import model.Peserta;

/**
 * Master Data Peserta - SIKURSUS Desktop (Praktikum PBO Pertemuan 10).
 * CRUD lengkap + JTable + Search + Refresh + Reset + Cetak.
 * View hanya membaca input & menampilkan hasil; SQL ada di PesertaDAO.
 */
public class PesertaFrame extends JFrame {

    private static final Color PINK_HEADER = new Color(0xE9, 0x4B, 0x9A);
    private static final Color BIRU_JUDUL  = new Color(0x1E, 0x5F, 0xC8);

    // ===== komponen form =====
    private final JTextField txtNim = new JTextField();
    private final JTextField txtNama = new JTextField();
    private final JComboBox<String> cbJenisKelamin =
            new JComboBox<>(new String[]{"L", "P"});
    private final JComboBox<String> cbProdi = new JComboBox<>(new String[]{
        "Informatika", "Sistem Informasi", "Matematika", "Fisika", "Biologi"
    });
    private final JTextField txtNoHp = new JTextField();
    private final JTextField txtEmail = new JTextField();

    private final ColorButton btnSimpan = new ColorButton("Simpan", new Color(0x1E, 0x90, 0xFF));
    private final ColorButton btnUbah = new ColorButton("Ubah", new Color(0x00, 0x5C, 0xC8));
    private final ColorButton btnHapus = new ColorButton("Hapus", new Color(0xE5, 0x4B, 0x4B));
    private final ColorButton btnReset = new ColorButton("Reset", new Color(0x6C, 0x84, 0xA8));
    private final ColorButton btnCetak = new ColorButton("Cetak Semua", new Color(0x22, 0xB0, 0x6B));

    // ===== pencarian & tabel =====
    private final JTextField txtCari = new JTextField();
    private final ColorButton btnCari = new ColorButton("Cari", new Color(0x00, 0x8C, 0xD8));
    private final JTable tblPeserta = new JTable();

    // ===== state =====
    private final PesertaDAO dao = new PesertaDAO();
    private int selectedId = -1;   // -1 = belum ada data yang dipilih

    public PesertaFrame() {
        setTitle("Master Peserta - SIKURSUS Desktop");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        configureTable();
        loadTable();
        clearForm();

        setSize(1000, 720);
        setMinimumSize(new Dimension(820, 600));
        setLocationRelativeTo(null);
    }

    // =====================================================================
    //  GUI
    // =====================================================================
    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout(0, 10));
        root.setBackground(new Color(0xF4, 0xF7, 0xFC));

        // --- header pink ---
        JLabel judul = new JLabel("Master Data Peserta", SwingConstants.CENTER);
        judul.setFont(judul.getFont().deriveFont(Font.BOLD, 20f));
        judul.setForeground(Color.WHITE);
        judul.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PINK_HEADER);
        header.add(judul, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        // --- isi tengah ---
        JPanel body = new JPanel(new BorderLayout(0, 10));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        body.add(buildFormPanel(), BorderLayout.NORTH);
        body.add(buildTablePanel(), BorderLayout.CENTER);
        root.add(body, BorderLayout.CENTER);

        setContentPane(root);

        // --- event ---
        btnSimpan.addActionListener(e -> btnSimpanActionPerformed());
        btnUbah.addActionListener(e -> btnUbahActionPerformed());
        btnHapus.addActionListener(e -> btnHapusActionPerformed());
        btnReset.addActionListener(e -> btnResetActionPerformed());
        btnCetak.addActionListener(e -> btnCetakActionPerformed());
        btnCari.addActionListener(e -> btnCariActionPerformed());
        txtCari.addActionListener(e -> btnCariActionPerformed());   // tekan Enter
        tblPeserta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPesertaMouseClicked(evt);
            }
        });
    }

    private JPanel buildFormPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(titled("Form Peserta"));

        cbProdi.setEditable(true);          // boleh pilih atau ketik prodi lain
        cbProdi.setSelectedIndex(-1);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 10);
        c.anchor = GridBagConstraints.WEST;

        addRow(p, c, 0, "NIM", txtNim);
        addRow(p, c, 1, "Nama Peserta", txtNama);
        addRow(p, c, 2, "Jenis Kelamin", cbJenisKelamin);
        addRow(p, c, 3, "Program Studi", cbProdi);
        addRow(p, c, 4, "Nomor HP", txtNoHp);
        addRow(p, c, 5, "Email", txtEmail);

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 4));
        tombol.setOpaque(false);
        tombol.add(btnSimpan);
        tombol.add(btnUbah);
        tombol.add(btnHapus);
        tombol.add(btnReset);
        tombol.add(btnCetak);

        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(tombol, c);
        return p;
    }

    private JPanel buildTablePanel() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(Color.WHITE);
        p.setBorder(titled("Data Peserta"));

        JPanel cari = new JPanel(new GridBagLayout());
        cari.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 6, 2, 6);
        c.gridy = 0;

        c.gridx = 0;
        cari.add(new JLabel("Pencarian"), c);

        c.gridx = 1;
        txtCari.setPreferredSize(new Dimension(220, 28));
        cari.add(txtCari, c);

        c.gridx = 2;
        cari.add(btnCari, c);

        c.gridx = 3;
        c.weightx = 1;
        cari.add(new JLabel(), c);   // pendorong ke kiri

        p.add(cari, BorderLayout.NORTH);
        p.add(new JScrollPane(tblPeserta), BorderLayout.CENTER);
        return p;
    }

    private void addRow(JPanel p, GridBagConstraints c, int row,
                        String label, java.awt.Component field) {
        c.gridy = row;

        c.gridx = 0;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        JLabel l = new JLabel(label);
        l.setPreferredSize(new Dimension(110, 26));
        p.add(l, c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(field, c);
    }

    private TitledBorder titled(String text) {
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0xCC, 0xD6, 0xE4)), text);
        tb.setTitleFont(tb.getTitleFont().deriveFont(Font.BOLD, 13f));
        tb.setTitleColor(BIRU_JUDUL);
        return tb;
    }

    // =====================================================================
    //  JTable
    // =====================================================================
    private void configureTable() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "NIM", "Nama", "JK", "Prodi", "No. HP", "Email"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;   // edit lewat form, bukan lewat sel tabel
            }
        };
        tblPeserta.setModel(model);
        tblPeserta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPeserta.setRowHeight(24);
        tblPeserta.setFillsViewportHeight(true);
        tblPeserta.getTableHeader().setReorderingAllowed(false);
        tblPeserta.getTableHeader().setFont(
                tblPeserta.getTableHeader().getFont().deriveFont(Font.BOLD));

        int[] lebar = {45, 90, 180, 40, 130, 120, 220};
        for (int i = 0; i < lebar.length; i++) {
            tblPeserta.getColumnModel().getColumn(i).setPreferredWidth(lebar[i]);
        }
    }

    private void fillTable(List<Peserta> data) {
        DefaultTableModel model = (DefaultTableModel) tblPeserta.getModel();
        model.setRowCount(0);   // wajib: cegah data dobel
        for (Peserta p : data) {
            model.addRow(new Object[]{
                p.getId(),
                p.getNim(),
                p.getNama(),
                p.getJenisKelamin(),
                p.getProdi(),
                p.getNoHp(),
                p.getEmail()
            });
        }
    }

    private void loadTable() {
        try {
            fillTable(dao.findAll());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshData() {
        loadTable();
        clearForm();
        txtCari.setText("");
    }

    // =====================================================================
    //  state form & mapping form -> object
    // =====================================================================
    private void clearForm() {
        selectedId = -1;
        txtNim.setText("");
        txtNama.setText("");
        cbJenisKelamin.setSelectedIndex(0);
        cbProdi.setSelectedIndex(-1);
        txtNoHp.setText("");
        txtEmail.setText("");
        tblPeserta.clearSelection();
        btnSimpan.setEnabled(true);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
        txtNim.requestFocus();
    }

    private String getJenisKelamin() {
        Object v = cbJenisKelamin.getSelectedItem();
        return v == null ? null : v.toString();
    }

    private String getProdi() {
        Object v = cbProdi.getSelectedItem();
        return v == null ? "" : v.toString().trim();
    }

    private Peserta getFormData() {
        Peserta p = new Peserta();
        p.setId(selectedId);
        p.setNim(txtNim.getText().trim());
        p.setNama(txtNama.getText().trim());
        p.setJenisKelamin(getJenisKelamin());
        p.setProdi(getProdi());
        p.setNoHp(txtNoHp.getText().trim());
        p.setEmail(txtEmail.getText().trim());
        return p;
    }

    private boolean validateForm() {
        if (txtNim.getText().trim().isEmpty()
                || txtNama.getText().trim().isEmpty()
                || getProdi().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "NIM, Nama, dan Prodi wajib diisi.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (getJenisKelamin() == null) {
            JOptionPane.showMessageDialog(this, "Pilih jenis kelamin.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private static String str(Object o) {
        return o == null ? "" : o.toString();
    }

    // =====================================================================
    //  EVENT
    // =====================================================================
    private void tblPesertaMouseClicked(java.awt.event.MouseEvent evt) {
        int row = tblPeserta.getSelectedRow();
        if (row == -1) {
            return;
        }
        // ID diambil dari kolom 0, BUKAN dari nomor baris
        selectedId = Integer.parseInt(tblPeserta.getValueAt(row, 0).toString());

        txtNim.setText(str(tblPeserta.getValueAt(row, 1)));
        txtNama.setText(str(tblPeserta.getValueAt(row, 2)));
        cbJenisKelamin.setSelectedItem(str(tblPeserta.getValueAt(row, 3)));
        cbProdi.setSelectedItem(str(tblPeserta.getValueAt(row, 4)));
        txtNoHp.setText(str(tblPeserta.getValueAt(row, 5)));
        txtEmail.setText(str(tblPeserta.getValueAt(row, 6)));

        btnSimpan.setEnabled(false);   // data lama dipilih: pakai Ubah, bukan Simpan
        btnUbah.setEnabled(true);
        btnHapus.setEnabled(true);
    }

    // CREATE
    private void btnSimpanActionPerformed() {
        if (!validateForm()) {
            return;
        }
        try {
            Peserta p = getFormData();
            if (dao.insert(p)) {
                JOptionPane.showMessageDialog(this, "Data peserta berhasil disimpan.");
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak tersimpan.",
                        "Informasi", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan data:\n" + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // UPDATE
    private void btnUbahActionPerformed() {
        if (selectedId <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih data peserta pada tabel terlebih dahulu.");
            return;
        }
        if (!validateForm()) {
            return;
        }
        try {
            Peserta p = getFormData();
            if (dao.update(p)) {
                JOptionPane.showMessageDialog(this, "Data peserta berhasil diubah.");
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Data tidak ditemukan atau tidak berubah.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengubah data: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // DELETE + konfirmasi
    private void btnHapusActionPerformed() {
        if (selectedId <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Pilih data peserta yang akan dihapus.");
            return;
        }
        String nama = txtNama.getText().trim();
        int jawab = JOptionPane.showConfirmDialog(this,
                "Yakin menghapus peserta: " + nama + "?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (jawab != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (dao.delete(selectedId)) {
                JOptionPane.showMessageDialog(this, "Data peserta berhasil dihapus.");
                refreshData();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menghapus data: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // RESET
    private void btnResetActionPerformed() {
        txtCari.setText("");
        loadTable();
        clearForm();
    }

    // SEARCH
    private void btnCariActionPerformed() {
        String keyword = txtCari.getText().trim();
        try {
            if (keyword.isEmpty()) {
                loadTable();
            } else {
                fillTable(dao.search(keyword));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Pencarian gagal: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // CETAK SEMUA (seluruh data dari database, bukan hanya hasil pencarian)
    private void btnCetakActionPerformed() {
        try {
            DefaultTableModel model = new DefaultTableModel(
                    new Object[]{"ID", "NIM", "Nama", "JK", "Prodi", "No. HP", "Email"}, 0);
            for (Peserta p : dao.findAll()) {
                model.addRow(new Object[]{p.getId(), p.getNim(), p.getNama(),
                    p.getJenisKelamin(), p.getProdi(), p.getNoHp(), p.getEmail()});
            }
            JTable semua = new JTable(model);
            boolean ok = semua.print(JTable.PrintMode.FIT_WIDTH,
                    new MessageFormat("Data Peserta - SIKURSUS"),
                    new MessageFormat("Halaman {0}"));
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Pencetakan dibatalkan.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data untuk dicetak: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mencetak: " + ex.getMessage(),
                    "Printer Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =====================================================================
    //  Tombol berwarna (tampil sama di semua Look & Feel)
    // =====================================================================
    private static class ColorButton extends JButton {
        private final Color warna;

        ColorButton(String text, Color warna) {
            super(text);
            this.warna = warna;
            setForeground(Color.WHITE);
            setFont(getFont().deriveFont(Font.BOLD, 12f));
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(text.length() > 6 ? 130 : 110, 32));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            Color base = isEnabled() ? warna : blend(warna, Color.WHITE, 0.55f);
            if (getModel().isPressed() && isEnabled()) {
                base = base.darker();
            } else if (getModel().isRollover() && isEnabled()) {
                base = blend(base, Color.WHITE, 0.15f);
            }
            g2.setPaint(new GradientPaint(0, 0, blend(base, Color.WHITE, 0.18f),
                    0, getHeight(), base));
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            g2.setColor(base.darker());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            g2.dispose();
            super.paintComponent(g);
        }

        private static Color blend(Color a, Color b, float t) {
            return new Color(
                    Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                    Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                    Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t));
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Nimbus tidak tersedia, memakai tampilan bawaan: " + ex.getMessage());
        }
        SwingUtilities.invokeLater(() -> new PesertaFrame().setVisible(true));
    }
}