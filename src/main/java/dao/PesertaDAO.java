package dao;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Peserta;

public class PesertaDAO {

    // Mengubah satu baris ResultSet menjadi satu object Peserta
    private Peserta mapRow(ResultSet rs) throws SQLException {
        Peserta p = new Peserta();
        p.setId(rs.getInt("id_peserta"));
        p.setNim(rs.getString("nim"));
        p.setNama(rs.getString("nama"));
        p.setJenisKelamin(rs.getString("jenis_kelamin"));
        p.setProdi(rs.getString("prodi"));
        p.setNoHp(rs.getString("no_hp"));
        p.setEmail(rs.getString("email"));
        return p;
    }

    // CREATE
    public boolean insert(Peserta p) throws SQLException {
        String sql = "INSERT INTO peserta "
                   + "(nim, nama, jenis_kelamin, prodi, no_hp, email) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNim());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJenisKelamin());
            ps.setString(4, p.getProdi());
            ps.setString(5, p.getNoHp());
            ps.setString(6, p.getEmail());

            return ps.executeUpdate() == 1;
        }
    }

    // READ semua data
    public List<Peserta> findAll() throws SQLException {
        List<Peserta> data = new ArrayList<>();
        String sql = "SELECT id_peserta, nim, nama, jenis_kelamin, "
                   + "prodi, no_hp, email "
                   + "FROM peserta ORDER BY id_peserta DESC";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                data.add(mapRow(rs));
            }
        }
        return data;
    }

    // UPDATE berdasarkan primary key
    public boolean update(Peserta p) throws SQLException {
        String sql = "UPDATE peserta SET nim=?, nama=?, jenis_kelamin=?, "
                   + "prodi=?, no_hp=?, email=? "
                   + "WHERE id_peserta=?";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNim());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJenisKelamin());
            ps.setString(4, p.getProdi());
            ps.setString(5, p.getNoHp());
            ps.setString(6, p.getEmail());
            ps.setInt(7, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // DELETE berdasarkan primary key
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM peserta WHERE id_peserta=?";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // SEARCH nama / NIM dengan LIKE parameter
    public List<Peserta> search(String keyword) throws SQLException {
        List<Peserta> data = new ArrayList<>();
        String sql = "SELECT id_peserta, nim, nama, jenis_kelamin, "
                   + "prodi, no_hp, email FROM peserta "
                   + "WHERE nim LIKE ? OR nama LIKE ? "
                   + "ORDER BY nama";
        String pattern = "%" + keyword.trim() + "%";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data.add(mapRow(rs));
                }
            }
        }
        return data;
    }
}