package latihan;

public class LatihanBiayaKursus {
    public static void main(String[] args) {

        // Variabel
        String kode = "JAVA-BSC";
        String nama = "Java Desktop Fundamental";
        double biayareg = 500_000;
        double biaya = 3_828_000;
        double diskon;
        boolean aktif = true;

        double totalawal = biayareg + biaya;
        
        // Menentukan diskon
       if (totalawal >= 3_000_000) {
            diskon = 0.15; 
        } else if (totalawal >= 1_500_000) {
            diskon = 0.10; 
        } else {
            diskon = 0.05; 
        }

        double potongan = totalawal * diskon;
        double total = totalawal - potongan;

        // Menentukan status
        String status;
        if (total > 2_500_000) {
            status = "MAHAL";
        } else if (total >= 1_500_000) {
            status = "STANDAR";
        } else {
            status = "TERJANGKAU";
        }

        // Output
        System.out.println("Kode   : " + kode);
        System.out.println("Kursus : " + nama);
        System.out.println("Aktif  : " + aktif);
        System.out.printf("Total  : Rp%,.0f%n", total);
        System.out.println("Status : " + status);
    }
}