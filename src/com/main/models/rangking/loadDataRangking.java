package com.main.models.rangking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.main.models.connectionDatabase;

public class loadDataRangking {
    public static DefaultTableModel getAllDataRangkingByPeriode(String periode) {
        String[] dataHeader = {
                "ID", "ID Product", "Product", "score", "rank", "Periode", "Last Update"
        };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

        String query = "SELECT * FROM tbl_data_rangking WHERE periode = ? ORDER BY  `rank` ASC";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setString(1, periode);
            ResultSet resultData = state.executeQuery();

            while (resultData.next()) {
                Object[] rowData = {
                        "DK00" + resultData.getInt("idRangking"),
                        resultData.getInt("idNormalisation"),
                        resultData.getString("product"),
                        String.format("%.2f", resultData.getDouble("score")),
                        resultData.getInt("rank"),
                        resultData.getString("periode"),
                        resultData.getString("createAt")
                };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllDataRangkingWithNormalizedWeightByPeriode(String periode) {
        String[] dataHeader = {
                "ID", "Product", "Calculation", "Score", "Rank", "Periode", "Last Update"
        };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

        String query = "SELECT r.idRangking, a.product, r.score, r.rank, r.periode, r.createAt, " +
                "n.K1, n.K2, n.K3, n.K4 " +
                "FROM tbl_data_rangking r " +
                "JOIN tbl_data_normalisation n ON r.idNormalisation = n.idNormalisation " +
                "JOIN tbl_data_alternatif a ON n.idAlternatif = a.idAlternatif " +
                "WHERE DATE(r.periode) = ? ORDER BY r.rank ASC";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setString(1, periode);
            ResultSet rs = state.executeQuery();

            // ambil bobot dari tabel kriteria
            List<Double> bobotList = new ArrayList<>();
            double totalBobot = 0.0;
            try (Statement st = conn.createStatement();
                    ResultSet rsBobot = st
                            .executeQuery("SELECT weight FROM tbl_data_kriteria ORDER BY idKriteria ASC")) {
                while (rsBobot.next()) {
                    double w = rsBobot.getDouble("weight");
                    bobotList.add(w);
                    totalBobot += w;
                }
            }

            // hitung bobot ternormalisasi
            List<Double> normalizedBobotList = new ArrayList<>();
            for (double w : bobotList) {
                normalizedBobotList.add(w / totalBobot);
            }

            while (rs.next()) {
                double n1 = rs.getDouble("K1");
                double n2 = rs.getDouble("K2");
                double n3 = rs.getDouble("K3");
                double n4 = rs.getDouble("K4");

                double w1 = normalizedBobotList.get(0);
                double w2 = normalizedBobotList.get(1);
                double w3 = normalizedBobotList.get(2);
                double w4 = normalizedBobotList.get(3);

                // hitung score berdasarkan bobot normalisasi
                double score = n1 * w1 + n2 * w2 + n3 * w3 + n4 * w4;

                // buat rumus string
                String rumus = "(" + String.format("%.4f", w1) + " * " + String.format("%.3f", n1) + ") + " +
                        "(" + String.format("%.4f", w2) + " * " + String.format("%.3f", n2) + ") + " +
                        "(" + String.format("%.4f", w3) + " * " + String.format("%.3f", n3) + ") + " +
                        "(" + String.format("%.4f", w4) + " * " + String.format("%.3f", n4) + ")" +
                        " = " + String.format("%.3f", score);

                Object[] rowData = {
                        "RK00" + rs.getInt("idRangking"),
                        rs.getString("product"),
                        rumus,
                        String.format("%.3f", score),
                        rs.getInt("rank"),
                        rs.getString("periode"),
                        rs.getString("createAt")
                };
                tm.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tm;
    }

    public static boolean isDataRankingExistForNormalisation(String periode, int idNormalisation) {
        String query = "SELECT COUNT(*) AS total FROM tbl_data_rangking WHERE periode = ? AND idNormalisation = ?";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, periode);
            stmt.setInt(2, idNormalisation);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("total");
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
