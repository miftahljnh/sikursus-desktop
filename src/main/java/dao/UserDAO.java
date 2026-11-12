package dao;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import util.PasswordUtil;

public class UserDAO {
    public User authenticate(String username, char[] password) throws SQLException {
        String sql = "SELECT id_user, username, password_hash, role, aktif FROM users WHERE username=? AND aktif=1";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && PasswordUtil.verify(password, rs.getString("password_hash"))) {
                    return new User(
                            rs.getInt("id_user"),
                            rs.getString("username"),
                            rs.getString("role"),
                            rs.getBoolean("aktif")
                    );
                }
            }
        }
        return null;
    }
}