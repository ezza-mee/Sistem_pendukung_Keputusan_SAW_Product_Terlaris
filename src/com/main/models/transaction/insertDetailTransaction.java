package com.main.models.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.main.models.connectionDatabase;

public class insertDetailTransaction {
    public static boolean insertData(int idTransaction, int idProduct, String product, int quantity, int priceProduct,
            int subPrice, String periode) {
        boolean data = false;

        String query = "INSERT INTO tbl_detail_transaction (idtransaction, idProduct, product, quantity, price, subPrice, periode) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connectionDatabase.getConnection();
                PreparedStatement state = conn.prepareStatement(query);) {

            state.setInt(1, idTransaction);
            state.setInt(2, idProduct);
            state.setString(3, product);
            state.setInt(4, quantity);
            state.setInt(5, priceProduct);
            state.setInt(6, subPrice);
            state.setString(7, periode);

            if (state.executeUpdate() > 0) {
                data = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
