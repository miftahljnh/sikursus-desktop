package model;

/**
 * Latihan penguatan Pertemuan 6: subtype baru tanpa mengubah loop polimorfik.
 */
public class Admin extends Orang {
    private String role;

    public Admin(int id, String nama, String noHp, String role) {
        super(id, nama, noHp);
        setRole(role);
    }

    public String getRole() {
        return role;
    }

    public final void setRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role admin wajib diisi");
        }
        this.role = role.trim();
    }

    @Override
    public String getInfo() {
        return "ADMIN | " + super.getInfo() + " | Role: " + getRole();
    }
}