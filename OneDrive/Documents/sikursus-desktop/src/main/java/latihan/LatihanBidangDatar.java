
package latihan;


public class LatihanBidangDatar {
    public static void main(String[] args) {

        //variabel persegi panjang
        double panjang = 12;
        double lebar = 8;
        double luasPersegiPanjang = panjang * lebar;
        double kelilingPersegiPanjang = 2 * (panjang + lebar);

        //variabel lingkaran
        double jariJari = 7;
        double phi = 3.14;
        double luasLingkaran = phi * jariJari * jariJari;
        double kelilingLingkaran = 2 * phi * jariJari;

        //variabel segitiga (alas & tinggi, plus 3 sisi buat keliling)
        double alas = 10;
        double tinggi = 6;
        double sisiA = 10, sisiB = 8, sisiC = 7;
        double luasSegitiga = 0.5 * alas * tinggi;
        double kelilingSegitiga = sisiA + sisiB + sisiC;

        //output
        System.out.println("=== Persegi Panjang ===");
        System.out.println("Luas     : " + luasPersegiPanjang);
        System.out.println("Keliling : " + kelilingPersegiPanjang);

        System.out.println("=== Lingkaran ===");
        System.out.println("Luas     : " + luasLingkaran);
        System.out.println("Keliling : " + kelilingLingkaran);

        System.out.println("=== Segitiga ===");
        System.out.println("Luas     : " + luasSegitiga);
        System.out.println("Keliling : " + kelilingSegitiga);
    }
}