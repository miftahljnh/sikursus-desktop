package latihan;

import java.util.Scanner;

public class LatihanBiayaKursus {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // 1. INPUT DINAMIS & VALIDASI (Mencegah Input Selain Angka)
        System.out.println("=== SISTEM KASIR KURSUS IT ===");
        System.out.print("Masukkan Kode Kursus (Contoh: JAVA-BSC atau WEB-ADV): ");
        String kode = input.nextLine().toUpperCase(); // Otomatis dikapitalisasi
        
        double biayakursus = 0;
        boolean inputValid = false;
        
        // Perulangan while untuk memaksa user menginput angka biaya dengan benar
        while (!inputValid) {
            try {
                System.out.print("Masukkan Biaya Registrasi (Angka saja): Rp");
                biayakursus = input.nextDouble();
                inputValid = true; // Jika sukses melewati baris di atas, perulangan berhenti
            } catch (Exception e) {
                System.out.println("[ERROR] Input harus berupa angka tanpa titik/koma! Coba lagi.");
                input.nextLine(); // Membersihkan sisa input yang salah dari memori buffer
            }
        }
        
        // 2. OTOMATISASI DATA (Manipulasi String)
        // Menentukan nama kursus dan biaya tambahan secara otomatis dari potongan string KODE
        String namaKursus;
        double biayaTambahanFasilitas = 0;
        
        if (kode.startsWith("JAVA")) {
            namaKursus = "Java Desktop Fundamental";
            biayaTambahanFasilitas = 500000; // Fasilitas modul & lab Java
        } else if (kode.startsWith("WEB")) {
            namaKursus = "Advanced Web Development";
            biayaTambahanFasilitas = 750000; // Fasilitas server & hosting
        } else {
            namaKursus = "Kursus Umum (Standard Kelas)";
            biayaTambahanFasilitas = 250000;
        }

        // 3. PENGGUNAAN METODE MODULAR (Pemanggilan Fungsi)
        double totalSebelumDiskon = biayakursus + biayaTambahanFasilitas;
        double persenDiskon = hitungPersenDiskon(totalSebelumDiskon);
        double potongan = totalSebelumDiskon * persenDiskon;
        double totalAkhir = totalSebelumDiskon - potongan;
        String status = tentukanStatusHarga(totalAkhir);
        
        // 4. MENAMPILKAN OUTPUT NOTA
        System.out.println("\n====================================");
        System.out.println("Kode             : " + kode);
        System.out.println("Kursus           : " + namaKursus);
        System.out.printf("Biaya Registrasi : Rp%,.0f%n", biayakursus);
        System.out.printf("Biaya Tambahan   : Rp%,.0f%n", biayaTambahanFasilitas);
        System.out.printf("Total Kotor      : Rp%,.0f%n", totalSebelumDiskon);
        System.out.printf("Diskon (%d%%)     : Rp%,.0f%n", (int)(persenDiskon * 100), potongan);
        System.out.println("------------------------------------");
        System.out.printf("Total Bayar      : Rp%,.0f%n", totalAkhir);
        System.out.println("Status           : " + status);
        System.out.println("====================================");
        
        input.close();
    }

    // =========================================================================
    // CUSTOM METHOD (FUNGSI MANDIRI UNTUK MENGASAH LOGIKA TERPISAH)
    // =========================================================================
    
    /**
     * Mengasah Logika Percabangan Bertingkat (Diskon Dinamis)
     */
    public static double hitungPersenDiskon(double totalKotor) {
        if (totalKotor >= 3000000) {
            return 0.15; // Diskon 15% untuk total besar
        } else if (totalKotor >= 1500000) {
            return 0.10; // Diskon 10%
        } else {
            return 0.05; // Diskon 5%
        }
    }

    /**
     * Mengasah Logika Klasifikasi Data (Kategori Status)
     */
    public static String tentukanStatusHarga(double totalAkhir) {
        if (totalAkhir >= 2500000) {
            return "PREMIUM/MAHAL";
        } else if (totalAkhir >= 1500000) {
            return "STANDAR";
        } else {
            return "EKONOMIS/TERJANGKAU";
        }
    }
}