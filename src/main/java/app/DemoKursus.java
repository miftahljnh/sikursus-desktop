package app;

import model.Kursus;

public class DemoKursus {
    public static void main(String[] args) {
        Kursus k1 = new Kursus(
                "JAVA-BSC",
                "Java Desktop Fundamental",
                "BASIC",
                500000
        );

        Kursus kKosong = new Kursus(
                "TEST-000",
                "Kursus Biaya Nol",
                "BASIC",
                0
        );

        System.out.println("=== TEST MODEL KURSUS ===");
        cekHasil(1, k1.hitungBiayaSetelahDiskon(0), 500000);
        cekHasil(2, k1.hitungBiayaSetelahDiskon(10), 450000);
        cekHasil(3, k1.hitungBiayaSetelahDiskon(25), 375000);
        cekHasil(4, kKosong.hitungBiayaSetelahDiskon(10), 0);
        cekHasil(5, k1.hitungBiayaSetelahDiskon(100), 0);
    }

    private static void cekHasil(int nomor, double actual, double expected) {
        boolean pass = Math.abs(actual - expected) < 0.001;
        System.out.println(
                "Test " + nomor
                + " | Expected: " + expected
                + " | Actual: " + actual
                + " | " + (pass ? "PASS" : "FAIL")
        );
    }
}