package com.main.models.kriteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.main.models.connectionDatabase;

public class insertDataKriteria {
    public static boolean insertData(String kriteria, int weight, String type) {
        boolean data = false;

        String query = "INSERT INTO tbl_data_kriteria (kriteria, weight, type, lastUpdate) VALUES (?, ?, ?, NOW())";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query);) {

            state.setString(1, kriteria);
            state.setInt(2, weight);
            state.setString(3, type);

            if (state.executeUpdate() > 0) {
                data = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
