package report;

import config.Database;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public final class ReportService {

    private ReportService() {
    }

    public static void tampilkanLaporanPeserta() {
        try (Connection conn = Database.getConnection()) {
            InputStream in = ReportService.class.getResourceAsStream("/report/laporan_peserta.jasper");
            if (in == null) {
                throw new IllegalStateException(
                        "File laporan_peserta.jasper tidak ditemukan di resource."
                );
            }

            JasperPrint print = JasperFillManager.fillReport(
                    in,
                    new HashMap<>(),
                    conn
            );

            JasperViewer.viewReport(print, false);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Gagal membuka laporan: " + e.getMessage(),
                    "Laporan Peserta",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
