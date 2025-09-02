package com.main.models.bobotKriteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import com.main.models.connectionDatabase;
import com.main.models.entity.dataBobotKriteria;

public class loadDataBobotKriteria {
    public static DefaultTableModel getAllDataBobotKriteria() {

        String[] dataHeader = { "ID", "Kriteria", "Weight", "Type", "Date", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM tbl_data_kriteria";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "BK00" + resultData.getInt("idKriteria"),
                        resultData.getString("Kriteria"),
                        resultData.getInt("weight"),
                        resultData.getString("type"),
                        resultData.getString("lastUpdate") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllDataBobotKriteriaNormalisasi() {
        String[] dataHeader = { "ID", "Kriteria", "Weight", "Calculation", "Results" };
        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

        String query = "SELECT idKriteria, kriteria, weight FROM tbl_data_kriteria ORDER BY idKriteria ASC";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query);
                ResultSet rs = state.executeQuery()) {

            List<Map<String, Object>> list = new ArrayList<>();
            double totalBobot = 0.0;

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                int id = rs.getInt("idKriteria");
                String nama = rs.getString("kriteria");
                double bobot = rs.getDouble("weight");

                row.put("id", id);
                row.put("nama", nama);
                row.put("bobot", bobot);
                list.add(row);
                totalBobot += bobot;
            }

            for (Map<String, Object> r : list) {
                double bobot = (double) r.get("bobot");
                double normalisasi = bobot / totalBobot;
                String rumus = bobot + " / " + totalBobot;

                Object[] rowData = {
                        "BK00" + r.get("id"),
                        r.get("nama"),
                        bobot,
                        rumus,
                        String.format("%.4f", normalisasi)
                };
                tm.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tm;
    }

    public static String getTotalBobotKriteriaText() {
        String totalText = "0"; // default kalau kosong

        String query = "SELECT SUM(weight) as totalWeight FROM tbl_data_kriteria";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query);
                ResultSet rs = state.executeQuery()) {

            if (rs.next()) {
                int totalWeight = rs.getInt("totalWeight");
                totalText = "Total Weight:       " + totalWeight;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalText;
    }

    public static String getTotalResultKriteriaText() {
        String totalText = "0"; // default

        String query = "SELECT weight FROM tbl_data_kriteria ORDER BY idKriteria ASC";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query);
                ResultSet rs = state.executeQuery()) {

            List<Double> weights = new ArrayList<>();
            double totalBobot = 0.0;

            // Ambil semua weight dan hitung total bobot
            while (rs.next()) {
                double w = rs.getDouble("weight");
                weights.add(w);
                totalBobot += w;
            }

            // Hitung total result normalisasi
            double totalResult = 0.0;
            for (double w : weights) {
                double normalisasi = w / totalBobot;
                totalResult += normalisasi;
            }

            totalText = "Total Result:          " + String.format("%.2f", totalResult);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalText;
    }

    public static dataBobotKriteria getDataById(int idKriteria) {
        String query = "SELECT * FROM tbl_data_kriteria WHERE idKriteria = ?";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setInt(1, idKriteria);
            ResultSet resultData = state.executeQuery();

            if (resultData.next()) {
                return new dataBobotKriteria(
                        resultData.getInt("idKriteria"),
                        resultData.getString("Kriteria"),
                        resultData.getInt("weight"),
                        resultData.getString("type"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTipeKriteria(String idKriteria) {
        String query = "SELECT tipe FROM tbl_data_kriteria WHERE idKriteria = ?";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idKriteria);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "benefit";
    }

}
