package app;

import java.util.ArrayList;
import java.util.List;
import model.Admin;
import model.Instruktur;
import model.Orang;
import model.Peserta;

/**
 * Milestone Pertemuan 6: List<Orang>, overriding, dynamic dispatch,
 * polymorphic argument, dan instanceof terkontrol.
 */
public class DemoPolymorphism {

    public static void main(String[] args) {
        Peserta peserta = new Peserta(
                1, "Miftah Huljanah", "083179941099",
                "252001", "Informatika");

        Instruktur instruktur = new Instruktur(
                2, "Santri Wani", "081211110001",
                "Java Desktop");

        Admin admin = new Admin(
                3, "Fuji Sakiyah", "081233334444",
                "Operator Akademik");

        List<Orang> daftarOrang = new ArrayList<Orang>();
        daftarOrang.add(peserta);
        daftarOrang.add(instruktur);
        daftarOrang.add(admin);

        System.out.println("=== LOOP POLYMORPHISM ===");
        cetakSemua(daftarOrang);

        System.out.println("\n=== POLYMORPHIC ARGUMENT ===");
        tampilkanInfo(peserta);
        tampilkanInfo(instruktur);
        tampilkanInfo(admin);

        System.out.println("\n=== INSTANCEOF HANYA UNTUK DATA SUBTYPE ===");
        for (Orang o : daftarOrang) {
            if (o instanceof Peserta) {
                Peserta p = (Peserta) o;
                System.out.println("NIM khusus peserta: " + p.getNim());
            }
        }
    }

    public static void tampilkanInfo(Orang orang) {
        System.out.println(orang.getInfo());
    }

    public static void cetakSemua(List<Orang> daftar) {
        for (Orang o : daftar) {
            System.out.println(o.getInfo());
        }
    }
}