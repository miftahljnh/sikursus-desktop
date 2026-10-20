package app;

import java.util.ArrayList;
import model.Instruktur;
import model.Orang;
import model.Peserta;

public class DemoInheritance {
    public static void main(String[] args) {
        ArrayList<Orang> daftarOrang = new ArrayList<>();

        daftarOrang.add(new Peserta(
                1, "Alya Rahma", "081234567890",
                "252001", "Informatika"));

        daftarOrang.add(new Peserta(
                2, "Rafi Akbar", "081298765432",
                "252002", "Informatika"));

        Peserta pesertaUji = new Peserta(
                3, "Nama Lama", "080000000000",
                "252003", "Informatika");
        // Setter milik parent tetap dapat digunakan oleh object Peserta.
        pesertaUji.setNama("Siti Zahra");
        daftarOrang.add(pesertaUji);

        daftarOrang.add(new Instruktur(
                101, "Dina Pratama", "081211110001",
                "Java Desktop"));

        daftarOrang.add(new Instruktur(
                102, "Rizal Maulana", "081211110002",
                "Data Science"));

        System.out.println("=== DATA SIKURSUS ===");
        for (Orang orang : daftarOrang) {
            System.out.println(orang.getInfo());
        }

        System.out.println("Jumlah object: " + daftarOrang.size());
    }
}