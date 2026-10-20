package latihan;

import java.util.Scanner;

public class LatihanBidangDatar {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Menampilkan Pilihan Menu
        System.out.println("=== MENU HITUNG BIDANG DATAR ===");
        System.out.println("1. Persegi Panjang");
        System.out.println("2. Lingkaran");
        System.out.print("Pilih jenis bidang datar (1/2): ");
        int pilihan = input.nextInt();

        System.out.println("--------------------------------");

        // Proses Logika Berdasarkan Pilihan
        if (pilihan == 1) {
            // Input Persegi Panjang
            System.out.print("Masukkan Panjang (cm): ");
            double panjang = input.nextDouble();
            System.out.print("Masukkan Lebar (cm)  : ");
            double lebar = input.nextDouble();

            // Rumus Persegi Panjang
            double luas = panjang * lebar;
            double keliling = 2 * (panjang + lebar);

            // Output Hasil
            System.out.println("\n========= HASIL REKAP =========");
            System.out.printf("Luas Persegi Panjang    : %.2f cm²%n", luas);
            System.out.printf("Keliling Persegi Panjang: %.2f cm%n", keliling);

        } else if (pilihan == 2) {
            // Input Lingkaran
            System.out.print("Masukkan Jari-jari (cm): ");
            double r = input.nextDouble();

            // Rumus Lingkaran
            double luas = Math.PI * r * r;
            double keliling = 2 * Math.PI * r;

            // Output Hasil
            System.out.println("\n========= HASIL REKAP =========");
            System.out.printf("Luas Lingkaran    : %.2f cm²%n", luas);
            System.out.printf("Keliling Lingkaran: %.2f cm%n", keliling);

        } else {
            System.out.println("Error: Pilihan menu tidak valid!");
        }
        System.out.println("===============================");
    }
}