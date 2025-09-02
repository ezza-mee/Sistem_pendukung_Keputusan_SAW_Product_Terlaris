package com.main.models.staff;

import com.main.models.connectionDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class staffService {

    // Update status staff (umum, bisa dipakai login/logout)
    public static boolean updateStaffStatus(int idStaff, String status) {
        String query = "UPDATE tbl_data_staff SET status = ? WHERE idStaff = ?";

        try (Connection conn = connectionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, status);
            stmt.setInt(2, idStaff);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Saat login berhasil
    public static boolean staffLogin(int idStaff) {
        return updateStaffStatus(idStaff, "Active");
    }

    // Saat logout
    public static boolean staffLogout(int idStaff) {
        return updateStaffStatus(idStaff, "Inactive");
    }
}
