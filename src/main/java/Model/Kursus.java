package model;

public class Kursus {
    private String kode;
    private String nama;
    private String level;
    private double biaya;

    public Kursus() {
    }

    public Kursus(String kode, String nama, String level, double biaya) {
        this.kode = kode;
        this.nama = nama;
        this.level = level;
        this.biaya = biaya;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getBiaya() {
        return biaya;
    }

    public void setBiaya(double biaya) {
        this.biaya = biaya;
    }

    public double hitungBiayaSetelahDiskon(double persenDiskon) {
        return biaya - (biaya * persenDiskon / 100.0);
    }
}