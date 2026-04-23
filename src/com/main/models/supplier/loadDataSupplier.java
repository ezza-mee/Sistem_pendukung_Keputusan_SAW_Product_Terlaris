package com.main.models.supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.main.models.connectionDatabase;
import com.main.models.entity.dataSearchSupplier;
import com.main.models.entity.dataSupplier;
import com.main.models.entity.dataSupplierReady;

public class loadDataSupplier {
    public static DefaultTableModel getAllDataSupplier() {

        String[] dataHeader = { "ID", "Staff", "Supplier", "Jumlah", "Unit", "Status", "Tanggal",
                "Tanggal Approve", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatasupplierwithstaff";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "NS00" + resultData.getInt("idSupplier"),
                        resultData.getString("nameStaff"),
                        resultData.getString("nameSupplier"),
                        String.format("%.2f", resultData.getDouble("quantity")),
                        resultData.getString("unit"),
                        resultData.getString("status"),
                        resultData.getString("date"),
                        resultData.getString("dateApprove") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllSupplierWithStaff(int idStaff) {

        String[] dataHeader = { "ID", "ID Staff", "Staff", "Supplier", "Jumlah", "Unit", "Status", "Tanggal",
                "Tanggal Approve" };
        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);

        String query = "SELECT * FROM vwalldatasupplierwithstaff WHERE idStaff = ?";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setInt(1, idStaff);

            ResultSet resultData = state.executeQuery();

            while (resultData.next()) {
                Object[] rowData = {
                        "NS00" + resultData.getInt("idSupplier"),
                        resultData.getInt("idStaff"),
                        resultData.getString("nameStaff"),
                        resultData.getString("nameSupplier"),
                        String.format("%.2f", resultData.getDouble("quantity")),
                        resultData.getString("unit"),
                        resultData.getString("status"),
                        resultData.getString("date"),
                        resultData.getString("dateApprove")
                };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tm;
    }

    public static DefaultTableModel getAllStockDataSupplier() {

        String[] dataHeader = { "ID", "Staff", "Supplier", "Jumlah", "Unit", "Status", "Tanggal",
                "Tanggal Approve", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatasupplierwithstaff WHERE status = 'Ready' ";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "NS00" + resultData.getInt("idSupplier"),
                        resultData.getString("nameStaff"),
                        resultData.getString("nameSupplier"),
                        String.format("%.2f", resultData.getDouble("quantity")),
                        resultData.getString("unit"),
                        resultData.getString("status"),
                        resultData.getString("date"),
                        resultData.getString("dateApprove") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllOutStockDataSupplier() {

        String[] dataHeader = { "ID", "Staff", "Supplier", "Jumlah", "Unit", "Status", "Tanggal",
                "Tanggal Approve", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatasupplierwithstaff WHERE status = 'Out of Stock' ";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "NS00" + resultData.getInt("idSupplier"),
                        resultData.getString("nameStaff"),
                        resultData.getString("nameSupplier"),
                        String.format("%.2f", resultData.getDouble("quantity")),
                        resultData.getString("unit"),
                        resultData.getString("status"),
                        resultData.getString("date"),
                        resultData.getString("dateApprove") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllPendingDataSupplier() {

        String[] dataHeader = { "ID", "Staff", "Supplier", "Jumlah", "Unit", "Status", "Tanggal",
                "Tanggal Approve", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatasupplierwithstaff WHERE status = 'Processing' ";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "NS00" + resultData.getInt("idSupplier"),
                        resultData.getString("nameStaff"),
                        resultData.getString("nameSupplier"),
                        String.format("%.2f", resultData.getDouble("quantity")),
                        resultData.getString("unit"),
                        resultData.getString("status"),
                        resultData.getString("date"),
                        resultData.getString("dateApprove") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllRejectedDataSupplier() {

        String[] dataHeader = { "ID", "Staff", "Supplier", "Jumlah", "Unit", "Status", "Tanggal",
                "Tanggal Approve", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatasupplierwithstaff WHERE status = 'Processsing' ";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "NS00" + resultData.getInt("idSupplier"),
                        resultData.getString("nameStaff"),
                        resultData.getString("nameSupplier"),
                        String.format("%.2f", resultData.getDouble("quantity")),
                        resultData.getString("unit"),
                        resultData.getString("status"),
                        resultData.getString("date"),
                        resultData.getString("dateApprove") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static List<dataSupplierReady> getAllReadySupplierNames() {
        List<dataSupplierReady> supplierNames = new ArrayList<>();
        String query = "SELECT DISTINCT idSupplier, nameSupplier FROM vwalldatasupplier WHERE status = 'Ready' ";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query);
                ResultSet rs = state.executeQuery()) {

            while (rs.next()) {
                int idSupplier = rs.getInt("idSupplier");
                String nameSupplier = rs.getString("nameSupplier");

                supplierNames.add(new dataSupplierReady(idSupplier, nameSupplier, 0, "", ""));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierNames;
    }

    public static dataSupplier getDataById(int idSupplier) {
        String query = "SELECT * FROM tbl_data_supplier WHERE idSupplier = ?";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setInt(1, idSupplier);
            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                return new dataSupplier(
                        rs.getInt("idSupplier"),
                        rs.getInt("idStaff"),
                        rs.getString("nameSupplier"),
                        rs.getDouble("quantity"),
                        rs.getString("unit"),
                        rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<dataSearchSupplier> getAllSupplier() {
        ArrayList<dataSearchSupplier> supplierList = new ArrayList<>();
        String query = "SELECT * FROM vwalldatasupplierwithstaff";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery();
            while (resultData.next()) {
                dataSearchSupplier dataSupplier = new dataSearchSupplier(
                        resultData.getInt("idSupplier"),
                        resultData.getString("nameStaff"),
                        resultData.getString("nameSupplier"),
                        resultData.getDouble("quantity"),
                        resultData.getString("unit"),
                        resultData.getString("status"),
                        resultData.getString("date"),
                        resultData.getString("dateApprove"));
                supplierList.add(dataSupplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return supplierList;

    }

    public static ArrayList<dataSearchSupplier> getAllSupplierByStatus(String status) {
        ArrayList<dataSearchSupplier> supplierList = new ArrayList<>();
        String query = "SELECT * FROM vwalldatasupplierwithstaff";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setString(1, status);
            ResultSet resultData = state.executeQuery();

            while (resultData.next()) {
                dataSearchSupplier dataSupplier = new dataSearchSupplier(
                        resultData.getInt("idSupplier"),
                        resultData.getString("nameStaff"),
                        resultData.getString("nameSupplier"),
                        resultData.getDouble("quantity"),
                        resultData.getString("unit"),
                        resultData.getString("status"),
                        resultData.getString("date"),
                        resultData.getString("dateApprove"));
                supplierList.add(dataSupplier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return supplierList;

    }

    public static int getAllQuantityDataSupplier() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_supplier";

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

    public static int getAllQuantityPendingDataSupplier() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_supplier WHERE status = 'Processing' ";

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

    public static int getAllQuantityStockDataSupplier() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_supplier WHERE status = 'Ready' ";

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

    public static int getAllQuantityOutStockDataSupplier() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_supplier WHERE LOWER(status) = 'Out of Stock' ";

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

    public static int getAllQuantityRejectedDataSupplier() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_supplier WHERE LOWER(status) = 'Rejected' ";

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

}
