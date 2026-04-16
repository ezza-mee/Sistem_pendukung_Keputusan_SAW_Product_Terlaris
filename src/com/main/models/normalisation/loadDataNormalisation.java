package com.main.models.normalisation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import com.main.models.connectionDatabase;

public class loadDataNormalisation {
        public static DefaultTableModel getAllDataNormalisationByPeriode(String periode) {
                String[] dataHeader = {
                                "ID", "ID", "Produk", "K1", "K2",
                                "K3", "K4", "Periode", "Last Update"
                };

                DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

                String query = "SELECT * FROM tbl_data_normalisation " +
                                "WHERE periode = ? ORDER BY idAlternatif ASC";
                try (Connection conn = connectionDatabase.getConnection();
                                PreparedStatement state = conn.prepareStatement(query)) {

                        state.setString(1, periode);
                        ResultSet resultData = state.executeQuery();

                        while (resultData.next()) {
                                Object[] rowData = {
                                                resultData.getInt("idNormalisation"),
                                                "DK00" + resultData.getInt("idAlternatif"),
                                                resultData.getString("product"),
                                                String.format("%.3f", resultData.getDouble(
                                                                "K1")),
                                                String.format("%.3f", resultData.getDouble(
                                                                "K2")),
                                                String.format("%.3f", resultData.getDouble(
                                                                "K3")),
                                                String.format("%.3f", resultData.getDouble(
                                                                "K4")),
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

        public static DefaultTableModel getAllDataNormalisationWithFormula(String periode) {

                String[] dataHeader = {
                                "ID", "ID", "Produk", "K1", "K2", "K3", "K4", "Periode", "Last Update"
                };

                DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

                try (Connection conn = connectionDatabase.getConnection()) {

                        // 1. Ambil data alternatif (untuk nilai asli K1–K4)
                        String queryAlt = "SELECT idAlternatif, product, K1, K2, K3, K4 " +
                                        "FROM tbl_data_alternatif WHERE DATE(periode) = ?";
                        PreparedStatement stmtAlt = conn.prepareStatement(queryAlt);
                        stmtAlt.setString(1, periode);
                        ResultSet rsAlt = stmtAlt.executeQuery();

                        List<Map<String, Object>> alternatifList = new ArrayList<>();

                        double[] max = new double[4];
                        double[] min = new double[] {
                                        Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE
                        };

                        while (rsAlt.next()) {
                                Map<String, Object> alt = new HashMap<>();

                                int id = rsAlt.getInt("idAlternatif");
                                alt.put("idAlternatif", id);
                                alt.put("product", rsAlt.getString("product"));

                                for (int i = 0; i < 4; i++) {
                                        double val = rsAlt.getDouble("K" + (i + 1));
                                        alt.put("K" + (i + 1), val);

                                        max[i] = Math.max(max[i], val);
                                        min[i] = Math.min(min[i], val);
                                }

                                alternatifList.add(alt);
                        }

                        // 2. Ambil tipe kriteria
                        List<String> tipeList = new ArrayList<>();
                        try (Statement st = conn.createStatement();
                                        ResultSet rsKrit = st.executeQuery(
                                                        "SELECT type FROM tbl_data_kriteria ORDER BY idKriteria ASC")) {

                                while (rsKrit.next()) {
                                        tipeList.add(rsKrit.getString("type").trim().toLowerCase());
                                }
                        }

                        // 3. Ambil hasil normalisasi dari DB (hasil SAW resmi)
                        String queryNorm = "SELECT * FROM tbl_data_normalisation "
                                        + "WHERE periode = ? ORDER BY idAlternatif ASC";
                        PreparedStatement stmtNorm = conn.prepareStatement(queryNorm);
                        stmtNorm.setString(1, periode);
                        ResultSet rsNorm = stmtNorm.executeQuery();

                        Map<Integer, Map<String, Object>> normMap = new HashMap<>();

                        while (rsNorm.next()) {
                                Map<String, Object> norm = new HashMap<>();
                                norm.put("K1", rsNorm.getDouble("K1"));
                                norm.put("K2", rsNorm.getDouble("K2"));
                                norm.put("K3", rsNorm.getDouble("K3"));
                                norm.put("K4", rsNorm.getDouble("K4"));
                                norm.put("createAt", rsNorm.getString("createAt"));

                                normMap.put(rsNorm.getInt("idAlternatif"), norm);
                        }

                        // 4. Gabungkan & tampilkan
                        for (Map<String, Object> alt : alternatifList) {

                                int idAlt = (int) alt.get("idAlternatif");
                                Map<String, Object> norm = normMap.get(idAlt);

                                if (norm == null)
                                        continue;

                                double k1 = (double) alt.get("K1");
                                double k2 = (double) alt.get("K2");
                                double k3 = (double) alt.get("K3");
                                double k4 = (double) alt.get("K4");

                                double n1 = (double) norm.get("K1");
                                double n2 = (double) norm.get("K2");
                                double n3 = (double) norm.get("K3");
                                double n4 = (double) norm.get("K4");

                                // Rumus (SUDAH BENAR untuk benefit & cost)
                                String rumusK1 = tipeList.get(0).equals("benefit")
                                                ? String.format("%.0f / %.0f = %.3f", k1, max[0], n1)
                                                : String.format("%.0f / %.0f = %.3f", min[0], k1, n1);

                                String rumusK2 = tipeList.get(1).equals("benefit")
                                                ? String.format("%.0f / %.0f = %.3f", k2, max[1], n2)
                                                : String.format("%.0f / %.0f = %.3f", min[1], k2, n2);

                                String rumusK3 = tipeList.get(2).equals("benefit")
                                                ? String.format("%.0f / %.0f = %.3f", k3, max[2], n3)
                                                : String.format("%.0f / %.0f = %.3f", min[2], k3, n3);

                                String rumusK4 = tipeList.get(3).equals("benefit")
                                                ? String.format("%.0f / %.0f = %.3f", k4, max[3], n4)
                                                : String.format("%.0f / %.0f = %.3f", min[3], k4, n4);

                                Object[] rowData = {
                                                "DK00" + idAlt,
                                                idAlt,
                                                alt.get("product"),
                                                rumusK1,
                                                rumusK2,
                                                rumusK3,
                                                rumusK4,
                                                periode,
                                                norm.get("createAt")
                                };

                                tm.addRow(rowData);
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }

                return tm;
        }

        public static boolean isDataNormalisationExistForAlternatif(String periode, int idAlternatif) {
                String query = "SELECT COUNT(*) AS total FROM tbl_data_normalisation WHERE periode = ? AND idAlternatif = ?";

                try (Connection conn = connectionDatabase.getConnection();
                                PreparedStatement stmt = conn.prepareStatement(query)) {

                        stmt.setString(1, periode);
                        stmt.setInt(2, idAlternatif);
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
