package com.main.models.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.main.models.connectionDatabase;
import com.main.models.entity.dataTable;
import com.main.models.entity.dataSearchTable;

public class loadDataTable {
    public static DefaultTableModel getAllDataTable() {

        String[] dataHeader = { "ID", "Tanggal", "Nomor", "Kapasitas", "Deskripsi", "Status", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatatable";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "TB00" + resultData.getInt("idTable"),
                        resultData.getString("date"),
                        resultData.getString("number"),
                        resultData.getString("capacity"),
                        resultData.getString("description"),
                        resultData.getString("status") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static ArrayList<dataSearchTable> getAllTable() {
        ArrayList<dataSearchTable> TableList = new ArrayList<>();
        String query = "SELECT * FROM vwalldatatable";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery();
            while (resultData.next()) {
                dataSearchTable Table = new dataSearchTable(
                        resultData.getInt("idTable"),
                        resultData.getString("number"),
                        resultData.getString("capacity"),
                        resultData.getString("description"),
                        resultData.getString("status"));
                TableList.add(Table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TableList;
    }

    public static ArrayList<dataSearchTable> getAllTableByStatus(String status) {
        ArrayList<dataSearchTable> TableList = new ArrayList<>();
        String query = "SELECT * FROM vwalldatatable";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setString(1, status);
            ResultSet resultData = state.executeQuery();
            while (resultData.next()) {
                dataSearchTable Table = new dataSearchTable(
                        resultData.getInt("idTable"),
                        resultData.getString("number"),
                        resultData.getString("capacity"),
                        resultData.getString("description"),
                        resultData.getString("status"));
                TableList.add(Table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TableList;
    }

    public static DefaultTableModel getAllDataAvailableTable() {

        String[] dataHeader = { "ID", "Tanggal", "Nomor", "Kapasitas", "Deskripsi", "Status", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatatable WHERE status = 'Available'";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "TB00" + resultData.getInt("idTable"),
                        resultData.getString("date"),
                        resultData.getString("number"),
                        resultData.getString("capacity"),
                        resultData.getString("description"),
                        resultData.getString("status") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllDataCleaningTable() {

        String[] dataHeader = { "ID", "Tanggal", "Nomor", "Kapasitas", "Deskripsi", "Status", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatatable WHERE status = 'Cleaning' ";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "TB00" + resultData.getInt("idTable"),
                        resultData.getString("date"),
                        resultData.getString("number"),
                        resultData.getString("capacity"),
                        resultData.getString("description"),
                        resultData.getString("status") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllDataReservedTable() {

        String[] dataHeader = { "ID", "Tanggal", "Nomor", "Kapasitas", "Deskripsi", "Status", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatatable WHERE status = 'Reserved'";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "TB00" + resultData.getInt("idTable"),
                        resultData.getString("date"),
                        resultData.getString("number"),
                        resultData.getString("capacity"),
                        resultData.getString("description"),
                        resultData.getString("status") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static DefaultTableModel getAllDataOutOfOrderTable() {

       String[] dataHeader = { "ID", "Tanggal", "Nomor", "Kapasitas", "Deskripsi", "Status", "Aksi" };

        DefaultTableModel tm = new DefaultTableModel(null, dataHeader);
        String query = "SELECT * FROM vwalldatatable WHERE LOWER(status) = 'Out Of Order'";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            ResultSet resultData = state.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {
                        "TB00" + resultData.getInt("idTable"),
                        resultData.getString("date"),
                        resultData.getString("number"),
                        resultData.getString("capacity"),
                        resultData.getString("description"),
                        resultData.getString("status") };
                tm.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tm;
    }

    public static dataTable getDataById(int idTable) {
        String query = "SELECT * FROM tbl_data_table WHERE idTable = ?";
        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query)) {

            state.setInt(1, idTable);
            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                return new dataTable(
                        rs.getInt("idTable"),
                        rs.getString("number"),
                        rs.getString("capacity"),
                        rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<dataTable> getAllAvailableNumberTable() {
        List<dataTable> numberTable = new ArrayList<>();
        String query = "SELECT DISTINCT idTable, number FROM vwalldataTable WHERE status = 'Available' ";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query);
                ResultSet rs = state.executeQuery()) {

            while (rs.next()) {
                int idTable = rs.getInt("idTable");
                String number = rs.getString("number");

                numberTable.add(new dataTable(idTable, number, "", ""));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numberTable;
    }

    public static int getAllQuantityDataTable() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_table";

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

    public static int getAllQuantityDataCleaningTable() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_table  WHERE status = 'Cleaning' ";

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

    public static int getAllQuantityDataReservedTable() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_table WHERE status = 'Reserved' ";

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

    public static int getAllQuantityDataAvailableTable() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_table WHERE status = 'Available' ";

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

    public static int getAllQuantityDataOutOfOrderTable() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM tbl_data_table WHERE LOWER(status) = 'Out Of Order' ";

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
