package com.main.models.stockManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.main.models.connectionDatabase;

public class processStokUsage {
    public static void processStockUsage(int idProduct, int idTransaction) {
        try (Connection conn = connectionDatabase.getConnection()) {

            // Step 0: Ambil jumlah produk yang dipesan (subQuantity)
            int quantityOrdered = 1;
            String getQuantityQuery = "SELECT subQuantity FROM tbl_data_transaction WHERE idTransaction = ?";
            PreparedStatement psQty = conn.prepareStatement(getQuantityQuery);
            psQty.setInt(1, idTransaction);
            ResultSet rsQty = psQty.executeQuery();
            if (rsQty.next()) {
                quantityOrdered = rsQty.getInt("subQuantity");
            }

            // Step 1: Ambil data komposisi bahan
            String compositionQuery = "SELECT c.idComposition, c.idSupplier, c.nameProduct, c.supplier,\n" +
                    "       c.quantity AS compositionQuantity, c.unit AS compositionUnit,\n" +
                    "       s.quantity AS supplierStock, s.unit AS supplierUnit, s.nameSupplier\n" +
                    "FROM tbl_data_composition_product c\n" +
                    "JOIN tbl_data_supplier s ON s.idSupplier = c.idSupplier\n" +
                    "WHERE c.idProduct = ?";

            PreparedStatement ps = conn.prepareStatement(compositionQuery);
            ps.setInt(1, idProduct);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idComposition = rs.getInt("idComposition");
                int idSupplier = rs.getInt("idSupplier");
                String ingredient = rs.getString("nameSupplier");
                double quantityPerProduct = rs.getDouble("compositionQuantity");
                String compositionUnit = rs.getString("compositionUnit");
                double supplierStock = rs.getDouble("supplierStock");
                String supplierUnit = rs.getString("supplierUnit");
                String productName = rs.getString("nameProduct");

                double totalUsage = quantityPerProduct * quantityOrdered;

                // Unit conversion
                if (!compositionUnit.equalsIgnoreCase(supplierUnit)) {
                    String convertQuery = "SELECT multiplier FROM tbl_unit_convertion WHERE formUnit = ? AND toUnit = ?";
                    PreparedStatement psConvert = conn.prepareStatement(convertQuery);
                    psConvert.setString(1, compositionUnit);
                    psConvert.setString(2, supplierUnit);
                    ResultSet rsConvert = psConvert.executeQuery();
                    if (rsConvert.next()) {
                        double multiplier = rsConvert.getDouble("multiplier");
                        totalUsage *= multiplier;
                    } else {
                        System.err.println("Konversi tidak ditemukan: " + compositionUnit + " ke " + supplierUnit);
                        continue;
                    }
                }

                double newStock = supplierStock - totalUsage;
                if (newStock < 0)
                    newStock = 0;

                // Log debug
                System.out.println("Supplier: " + ingredient + " | Before: " + supplierStock + " " + supplierUnit +
                        " | Used: " + totalUsage + " | After: " + newStock);

                // Update supplier stock
                String updateStockQuery = "UPDATE tbl_data_supplier SET quantity = ? WHERE idSupplier = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateStockQuery);
                updateStmt.setDouble(1, newStock);
                updateStmt.setInt(2, idSupplier);
                updateStmt.executeUpdate();

                // Insert usage log
                String insertLogQuery = "INSERT INTO tbl_supplier_usage_log\n" +
                        "(idSupplier, idProduct, idComposition, idTransaction, date, ingredient, amountUsed, unit, product)\n"
                        +
                        "VALUES (?, ?, ?, ?, NOW(), ?, ?, ?, ?)";

                PreparedStatement logStmt = conn.prepareStatement(insertLogQuery);
                logStmt.setInt(1, idSupplier);
                logStmt.setInt(2, idProduct);
                logStmt.setInt(3, idComposition);
                logStmt.setInt(4, idTransaction);
                logStmt.setString(5, ingredient);
                logStmt.setDouble(6, totalUsage);
                logStmt.setString(7, compositionUnit);
                logStmt.setString(8, productName);
                logStmt.executeUpdate();
            }

            // Step 5: Cek apakah semua bahan habis → update status produk
            String checkStockQuery = "SELECT SUM(s.quantity) AS total FROM tbl_data_supplier s " +
                    "JOIN tbl_data_composition_product c ON s.idSupplier = c.idSupplier WHERE c.idProduct = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkStockQuery);
            checkStmt.setInt(1, idProduct);
            ResultSet rsCheck = checkStmt.executeQuery();

            if (rsCheck.next() && rsCheck.getDouble("total") <= 0) {
                String updateProductStatus = "UPDATE tbl_data_product SET status = 'Out of Stock' WHERE idProduct = ?";
                PreparedStatement updateProductStmt = conn.prepareStatement(updateProductStatus);
                updateProductStmt.setInt(1, idProduct);
                updateProductStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
