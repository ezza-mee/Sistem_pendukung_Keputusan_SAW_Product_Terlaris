package com.main.models.alternatif;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.main.models.connectionDatabase;
import com.main.models.entity.dataAlternatif;

public class loadDataAlternatif {
    public static DefaultTableModel getAllDataAlternatifByPeriode(String periode) {
        String[] dataHeader = {
                "ID", "ID", "Product", "K1", "K2",
                "K3", "K4", "Periode", "Last Update"
        };
        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

        String query = "SELECT * FROM tbl_data_alternatif WHERE periode = ?";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setString(1, periode);
            ResultSet resultData = state.executeQuery();

            while (resultData.next()) {
                Object[] rowData = {
                        "AL00" + resultData.getInt("idAlternatif"),
                        "AL00" + resultData.getInt("idProduct"),
                        resultData.getString("product"),
                        "Rp. " + resultData.getInt("K1"),
                        resultData.getInt("K2"),
                        "Rp. " + resultData.getInt("K3"),
                        resultData.getInt("K4"),
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

    public static List<dataAlternatif> getListAlternatifByPeriode(String periode) {
        List<dataAlternatif> list = new ArrayList<>();
        String query = "SELECT * FROM tbl_data_alternatif WHERE periode = ?";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, periode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                dataAlternatif k = new dataAlternatif(
                        rs.getInt("idProduct"),
                        rs.getString("product"),
                        rs.getInt("K1"),
                        rs.getInt("K2"),
                        rs.getInt("K3"),
                        rs.getInt("K4"),
                        rs.getString("periode"));
                list.add(k);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
