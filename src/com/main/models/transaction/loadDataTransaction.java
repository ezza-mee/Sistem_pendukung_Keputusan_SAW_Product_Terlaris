package com.main.models.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

import com.main.auth.sessionLogin;
import com.main.models.connectionDatabase;

public class loadDataTransaction {
    public static DefaultTableModel getAllDataTransaction() {

        String[] dataHeader = { "ID", "ID Staff", "Tanggal", "Meja", "Pelanggan", "Total", "Total Harga", "status",
                "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatatransactionwithstaff WHERE idStaff = ? AND status = 'Process'";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            int idStaff = sessionLogin.get().getIdStaff();
            state.setInt(1, idStaff);

            ResultSet resultData = state.executeQuery();

            while (resultData.next()) {
                Object[] rowData = {
                        "STLK00" + resultData.getInt("idTransaction"),
                        resultData.getInt("idStaff"),
                        resultData.getString("date"),
                        resultData.getString("numberTable"),
                        resultData.getString("customer"),
                        resultData.getInt("subQuantity"),
                        resultData.getInt("subPrice"),
                        resultData.getString("status") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllDataTransactionAdmin(String selectedDate) {

        String[] dataHeader = { "ID", "Tanggal", "Staff", "Meja", "Pelanggan", "Total", "Total Harga", "Status",
                "Periode" };
        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

        String query = "SELECT * FROM vwalldatatransactionwithadmin WHERE periode = ?";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setString(1, selectedDate);

            ResultSet resultData = state.executeQuery();

            while (resultData.next()) {
                Object[] rowData = {
                        "STLK00" + resultData.getInt("idTransaction"),
                        resultData.getString("date"),
                        resultData.getString("staff"),
                        resultData.getString("numberTable"),
                        resultData.getString("customer"),
                        resultData.getInt("subQuantity"),
                        resultData.getInt("subPrice"),
                        resultData.getString("status"),
                        resultData.getString("periode")
                };
                tm.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tm;
    }

    public static DefaultTableModel getAllDetailTransactionAdmin(String selectedDate) {

        String[] header = { "ID", "Tanggal", "Customer", "Product", "Qty", "Price", "Sub Price", "Periode" };
        DefaultTableModel tm = new DefaultTableModel(null, header);

        String query = "SELECT * FROM vwalldetailtransactionwithadmin WHERE periode = ?";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setString(1, selectedDate);

            ResultSet resultData = state.executeQuery();

            while (resultData.next()) {
                Object[] row = {
                        "DTL00" + resultData.getInt("idDetail"),
                        resultData.getString("date"),
                        resultData.getString("customer"),
                        resultData.getString("product"),
                        resultData.getInt("quantity"),
                        resultData.getInt("price"),
                        resultData.getInt("subPrice"),
                        resultData.getString("periode")
                };
                tm.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tm;
    }

    public static int getQuatityTransaction(String selectedDate) {

        String query = "SELECT COUNT(*) AS subQuantity FROM vwalldatatransactionwithadmin WHERE periode = ?";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, selectedDate);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("subQuantity");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getQuantityPriceTransaction(String selectedDate) {

        String query = "SELECT SUM(subPrice) AS total_price " +
                "FROM vwalldatatransactionwithadmin WHERE periode = ?";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, selectedDate);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total_price");
                return rs.wasNull() ? 0 : total;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static DefaultTableModel getAllDataTransactionDoneByStaff() {

        String[] dataHeader = { "ID", "ID Staff", "Tanggal", "Meja", "Pelanggan", "Total", "Total Harga", "status" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatatransactionwithstaff WHERE idStaff = ? AND status = 'Done'";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            int idStaff = sessionLogin.get().getIdStaff();
            state.setInt(1, idStaff);

            ResultSet resultData = state.executeQuery();

            while (resultData.next()) {
                Object[] rowData = {
                        "STLK00" + resultData.getInt("idTransaction"),
                        resultData.getInt("idStaff"),
                        resultData.getString("date"),
                        resultData.getString("numberTable"),
                        resultData.getString("customer"),
                        resultData.getInt("subQuantity"),
                        resultData.getInt("subPrice"),
                        resultData.getString("status") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static int getAllQuantityDataTransaction() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_transaction";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public static int getAllQuantityDataTransactionByStatusDone(int idStaff) {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_transaction WHERE idStaff = ? AND status = 'Done'";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idStaff);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public static int getAllQuantityDataTransactionByStatusProcess(int idStaff) {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_transaction WHERE idStaff = ? AND status = 'Process'";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idStaff);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt("total");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

}
