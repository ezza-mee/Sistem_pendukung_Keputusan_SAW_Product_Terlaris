package com.main.services;

import java.util.List;

import com.main.models.entity.listTransactionProduct;
import com.main.models.transaction.insertDetailTransaction;
import com.main.models.transaction.insertTransaction;
import com.main.models.transaction.updateTransactionStatus;

public class authDataTransaction {

    public static int insertDataTransaction(int idStaff, int idTable, String staff,
            String numberTable, String customer, int qantity, int priceProduct, int subPrice, String description,
            String payment, String periode,
            List<listTransactionProduct> listProduct) {

        int idTransaction = insertTransaction.insertData(idStaff, idTable, staff, numberTable, customer, qantity,
                subPrice, description, payment, periode);

        if (idTransaction == -1) {
            System.out.println("gagal insert");
            return -1;
        }

        for (listTransactionProduct data : listProduct) {
            boolean insertListProduct = insertDetailTransaction.insertData(
                    idTransaction,
                    data.idProduct,
                    data.nameProduct,
                    data.quantity,
                    data.priceProduct,
                    data.subPrice,
                    data.periode);

            if (!insertListProduct) {
                return -1;
            }
        }

        return idTransaction;
    }

    public static boolean updateStatusTransaction(int idTransaction) {
        return updateTransactionStatus.updateToDone(idTransaction);
    }

    public String validateDataTransactionInput(String numberTable, String customer, String description,
            String payment) {
        if ((numberTable == null || numberTable.isEmpty()) &&
                (customer == null || customer.isEmpty()) &&
                (description == null || description.isEmpty()) &&
                (payment == null || payment.isEmpty())) {
            return "ALL_FIELDS_EMPTY";
        } else if (numberTable == null || numberTable.isEmpty()) {
            return "NUMBER_TABLE_EMPTY";
        } else if (customer == null || customer.isEmpty()) {
            return "CUSTOMER_INVALID";
        } else if (description == null || description.isEmpty()) {
            return "DESCRIPTION_EMPTY";
        } else if (payment == null || payment.isEmpty()) {
            return "PAYMENT_EMPTY";
        } else {
            return "VALID";
        }
    }

}
