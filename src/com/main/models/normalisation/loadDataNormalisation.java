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
                                "ID", "ID", "Product", "K1", "K2",
                                "K3", "K4", "Periode", "Last Update"
                };

                DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

                String query = "SELECT * FROM tbl_data_normalisation WHERE periode = ?";
                try (Connection conn = connectionDatabase.getConnection();
                                PreparedStatement state = conn.prepareStatement(query)) {

                        state.setString(1, periode);
                        ResultSet resultData = state.executeQuery();

                        while (resultData.next()) {
                                Object[] rowData = {
                                                "DK00" + resultData.getInt("idNormalisation"),
                                                resultData.getInt("idAlternatif"),
                                                resultData.getString("product"),
                                                String.format("%.2f", resultData.getDouble(
                                                                "K1")),
                                                String.format("%.2f", resultData.getDouble(
                                                                "K2")),
                                                String.format("%.2f", resultData.getDouble(
                                                                "K3")),
                                                String.format("%.2f", resultData.getDouble(
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
                                "ID", "ID", "Product", "K1", "K2",
                                "K3", "K4", "Periode", "Last Update"
                };
                DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

                String query = "SELECT * FROM tbl_data_alternatif WHERE periode = ? ORDER BY idAlternatif ASC";

                try (Connection conn = connectionDatabase.getConnection();
                                PreparedStatement state = conn.prepareStatement(query)) {

                        state.setString(1, periode);

                        try (ResultSet rs = state.executeQuery()) {

                                List<Map<String, Object>> alternatifList = new ArrayList<>();

                                double[] max = { Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE,
                                                Double.MIN_VALUE };
                                double[] min = { Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE,
                                                Double.MAX_VALUE };

                                while (rs.next()) {
                                        Map<String, Object> alt = new HashMap<>();
                                        alt.put("idAlternatif", rs.getInt("idAlternatif"));
                                        alt.put("product", rs.getString("product"));
                                        alt.put("createAt", rs.getString("createAt"));

                                        double k1 = rs.getDouble("K1");
                                        double k2 = rs.getDouble("K2");
                                        double k3 = rs.getDouble("K3");
                                        double k4 = rs.getDouble("K4");

                                        alt.put("K1", k1);
                                        alt.put("K2", k2);
                                        alt.put("K3", k3);
                                        alt.put("K4", k4);

                                        max[0] = Math.max(max[0], k1);
                                        max[1] = Math.max(max[1], k2);
                                        max[2] = Math.max(max[2], k3);
                                        max[3] = Math.max(max[3], k4);

                                        min[0] = Math.min(min[0], k1);
                                        min[1] = Math.min(min[1], k2);
                                        min[2] = Math.min(min[2], k3);
                                        min[3] = Math.min(min[3], k4);

                                        alternatifList.add(alt);
                                }

                                // ambil tipe kriteria
                                List<String> tipeList = new ArrayList<>();
                                try (Statement st = conn.createStatement();
                                                ResultSet rsKrit = st.executeQuery(
                                                                "SELECT type FROM tbl_data_kriteria ORDER BY idKriteria ASC")) {
                                        while (rsKrit.next()) {
                                                tipeList.add(rsKrit.getString("type").trim().toLowerCase());
                                        }
                                }

                                // generate rumus + hasil normalisasi
                                for (Map<String, Object> alt : alternatifList) {
                                        double k1 = (double) alt.get("K1");
                                        double k2 = (double) alt.get("K2");
                                        double k3 = (double) alt.get("K3");
                                        double k4 = (double) alt.get("K4");

                                        double normK1 = tipeList.get(0).equals("benefit") ? k1 / max[0] : min[0] / k1;
                                        double normK2 = tipeList.get(1).equals("benefit") ? k2 / max[1] : min[1] / k2;
                                        double normK3 = tipeList.get(2).equals("benefit") ? k3 / max[2] : min[2] / k3;
                                        double normK4 = tipeList.get(3).equals("benefit") ? k4 / max[3] : min[3] / k4;

                                        String rumusK1 = String.format("%.2f / %.2f = %.3f", k1,
                                                        tipeList.get(0).equals("benefit") ? max[0] : k1, normK1);
                                        String rumusK2 = String.format("%.2f / %.2f = %.3f", k2,
                                                        tipeList.get(1).equals("benefit") ? max[1] : k2, normK2);
                                        String rumusK3 = String.format("%.2f / %.2f = %.3f", k3,
                                                        tipeList.get(2).equals("benefit") ? max[2] : k3, normK3);
                                        String rumusK4 = String.format("%.2f / %.2f = %.3f", k4,
                                                        tipeList.get(3).equals("benefit") ? max[3] : k4, normK4);

                                        Object[] rowData = {
                                                        "DK00" + alt.get("idAlternatif"),
                                                        alt.get("idAlternatif"),
                                                        alt.get("product"),
                                                        rumusK1,
                                                        rumusK2,
                                                        rumusK3,
                                                        rumusK4,
                                                        periode,
                                                        alt.get("createAt")
                                        };
                                        tm.addRow(rowData);
                                }
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
