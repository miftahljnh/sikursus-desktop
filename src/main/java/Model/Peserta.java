package model;

public class Peserta extends Orang {
    private String nim;
    private String prodi;
    private String jenisKelamin;
    private String email;

    // Constructor kosong (dipakai PesertaDAO dan PesertaFrame)
    public Peserta() {
    }

    // Constructor LAMA (tetap ada)
    public Peserta(int id, String nama, String noHp, String nim, String prodi) {
        super(id, nama, noHp);
        this.nim = nim;
        this.prodi = prodi;
    }

    // Constructor untuk simpan ke database
    public Peserta(String nim, String nama, String jenisKelamin,
                   String prodi, String noHp, String email) {
        super(0, nama, noHp);
        this.nim = nim;
        this.jenisKelamin = jenisKelamin;
        this.prodi = prodi;
        this.email = email;
    }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getProdi() { return prodi; }
    public void setProdi(String prodi) { this.prodi = prodi; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getInfo() {
        return "[Peserta] " + super.getInfo() + " | NIM: " + nim + " | Prodi: " + prodi;
    }
}