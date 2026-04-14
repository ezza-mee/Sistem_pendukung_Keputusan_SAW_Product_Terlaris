package com.main.models.kriteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.main.models.connectionDatabase;

public class updateDataKriteria {
    public static boolean updateData(int idWeight, String kriteria, int weight, String type) {
        String query = "UPDATE tbl_data_kriteria SET kriteria = ?, weight = ?, type = ?,  lastUpdate = NOW() WHERE idKriteria = ?";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, kriteria);
            stmt.setInt(2, weight);
            stmt.setString(3, type);
            stmt.setInt(4, idWeight);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
