package report;

import config.Database;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReportService {

    // 1. Report Peserta (Tanpa Parameter)
    public static void cetakPeserta() {
        tampilkanReport("/report/laporan_peserta.jasper", new HashMap<>());
    }

    // 2. Report Pendaftaran (Parameter Integer)
    public static void cetakPendaftaran(int idPendaftaran) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_ID_PENDAFTARAN", idPendaftaran);
        tampilkanReport("/report/laporan_pendaftaran.jasper", params);
    }

    // 3. Report Pembayaran (Parameter Rentang Tanggal)
    public static void cetakPembayaran(Date tanggalAwal, Date tanggalAkhir) {
        Map<String, Object> params = new HashMap<>();
        params.put("P_TANGGAL_AWAL", tanggalAwal);
        params.put("P_TANGGAL_AKHIR", tanggalAkhir);
        tampilkanReport("/report/laporan_pembayaran.jasper", params);
    }

    // Helper umum untuk menjalankan JasperReports
    private static void tampilkanReport(String pathJasper, Map<String, Object> params) {
        try (Connection conn = Database.getConnection();
             InputStream reportStream = ReportService.class.getResourceAsStream(pathJasper)) {

            if (reportStream == null) {
                throw new IllegalStateException("File report tidak ditemukan: " + pathJasper);
            }

            JasperPrint print = JasperFillManager.fillReport(reportStream, params, conn);

            if (print.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Data laporan tidak ditemukan pada kriteria tersebut.");
                return;
            }

            JasperViewer.viewReport(print, false);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mencetak laporan: " + ex.getMessage());
        }
    }

    public static void tampilkanLaporanPeserta() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}