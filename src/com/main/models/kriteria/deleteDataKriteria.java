package com.main.models.kriteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.main.models.connectionDatabase;

public class deleteDataKriteria {
    public static boolean deleteData(int idWeight) {
        boolean success = false;

        String query = "DELETE FROM tbl_data_kriteria WHERE idKriteria = ?";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idWeight);
            int result = stmt.executeUpdate();
            success = result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
