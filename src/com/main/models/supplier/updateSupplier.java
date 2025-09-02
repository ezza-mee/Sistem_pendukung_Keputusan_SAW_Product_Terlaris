package com.main.models.supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.main.models.connectionDatabase;

public class updateSupplier {
    public static boolean updateData(
            int idSupplier,
            int idStaff,
            String nameSupplier,
            double quantity,
            String unit,
            String description) {

        String querySupplier =
            "UPDATE tbl_data_supplier " +
            "SET idStaff = ?, nameSupplier = ?, quantity = ?, unit = ?, description = ?, status = 'Processing' " +
            "WHERE idSupplier = ?";

        // Pakai JOIN agar bisa update SEMUA produk yang terkait supplier ini lewat tabel composition
        String queryUpdateProductsBySupplier =
            "UPDATE tbl_data_product p " +
            "JOIN tbl_data_composition_product c ON c.idProduct = p.idProduct " +
            "SET p.status = LOWER(?) " +
            "WHERE c.idSupplier = ?";

        try (Connection conn = connectionDatabase.getConnection();
             PreparedStatement stmtSupplier = conn.prepareStatement(querySupplier)) {

            // --- Update supplier ---
            stmtSupplier.setInt(1, idStaff);
            stmtSupplier.setString(2, nameSupplier);
            stmtSupplier.setDouble(3, quantity);
            stmtSupplier.setString(4, unit);
            stmtSupplier.setString(5, description);
            stmtSupplier.setInt(6, idSupplier);

            int rows = stmtSupplier.executeUpdate();
            System.out.println("[SUPPLIER] updated rows = " + rows);

            if (rows > 0) {
                // Hindari masalah double precision: bulatkan ke int jika input seharusnya bilangan bulat
                int qty = (int) Math.round(quantity);

                String status = null;
                if (qty == 0) {
                    status = "out of stock";
                } else if (qty == 10) {
                    status = "ready";
                }

                if (status != null) {
                    System.out.println("[PRODUCT] computed status = " + status + " (from quantity=" + qty + ")");
                    try (PreparedStatement stmtUpdateProducts = conn.prepareStatement(queryUpdateProductsBySupplier)) {
                        stmtUpdateProducts.setString(1, status);
                        stmtUpdateProducts.setInt(2, idSupplier);
                        int productRows = stmtUpdateProducts.executeUpdate();
                        System.out.println("[PRODUCT] updated rows via JOIN = " + productRows);

                        if (productRows == 0) {
                            System.out.println("[WARN] Tidak ada produk yang terhubung dengan supplier id=" + idSupplier +
                                    " di tbl_data_composition_product. Cek relasi (idSupplier, idProduct).");
                        }
                    }
                } else {
                    System.out.println("[PRODUCT] quantity=" + qty + " → tidak ada perubahan status produk.");
                }
            }

            return rows > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] updateData supplier gagal");
            e.printStackTrace();
        }

        return false;
    }


   public static boolean approveSupplier(int idSupplier, String status) {
        String queryUpdateSupplier =
            "UPDATE tbl_data_supplier SET status = ?, dateApprove = NOW() WHERE idSupplier = ?";

        // Ambil idProduct lewat composition (relasi supplier → product)
        String queryGetProduct =
            "SELECT idProduct FROM tbl_data_composition_product WHERE idSupplier = ?";

        String queryUpdateProduct =
            "UPDATE tbl_data_product SET status = ? WHERE idProduct = ?";

        try (Connection conn = connectionDatabase.getConnection()) {
            conn.setAutoCommit(false); // supaya aman

            // 1. Update status supplier
            try (PreparedStatement stmt = conn.prepareStatement(queryUpdateSupplier)) {
                stmt.setString(1, status);
                stmt.setInt(2, idSupplier);
                stmt.executeUpdate();
            }

            // 2. Ambil semua idProduct yang terkait supplier
            try (PreparedStatement stmt = conn.prepareStatement(queryGetProduct)) {
                stmt.setInt(1, idSupplier);
                try (ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        int idProduct = rs.getInt("idProduct");

                        // 3. Tentukan status produk berdasarkan status supplier
                        String productStatus = status.equalsIgnoreCase("Ready") ? "Ready" : "Out of Stock";

                        try (PreparedStatement stmtUpdate = conn.prepareStatement(queryUpdateProduct)) {
                            stmtUpdate.setString(1, productStatus);
                            stmtUpdate.setInt(2, idProduct);
                            stmtUpdate.executeUpdate();
                        }
                    }
                }
            }

            conn.commit(); // simpan semua perubahan
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
