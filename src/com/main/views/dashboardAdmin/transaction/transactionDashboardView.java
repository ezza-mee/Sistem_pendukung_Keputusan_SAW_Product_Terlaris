package com.main.views.dashboardAdmin.transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.models.transaction.loadDataTransaction;
import com.main.routes.dashboardAdminView;

import javax.swing.table.DefaultTableModel;

public class transactionDashboardView extends contentPanel {

    private dashboardAdminView parentView;

    private textLabel headerLabel, quantityLabel, transactionLabel, valueQuantityLabel, valueTransactionLabel;
    private panelRounded headerPanel, navigationPanel, contentPanel, tablePanel;

    private linkLabel dataTransactionLabel, dataDetailTransactionLabel;

    private datePickerField dateField;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String selectedPriode = sdf.format(new Date());
    private String today = sdf.format(new Date());

    private tableNoActionButton dataTransaction, detailTransaction;
    private scrollTable scrollDataTransaction, scrollDetailTransaction;

    private String currentTransaction = "Transaction";

    private int quantityTransaction = loadDataTransaction.getQuatityTransaction(selectedPriode);
    private int quantityPrice = loadDataTransaction.getQuantityPriceTransaction(selectedPriode);

    private String qtyString = String.valueOf(quantityTransaction);
    private String priceString = String.valueOf(quantityPrice);

    public transactionDashboardView(dashboardAdminView parentView) {
        super();
        this.parentView = parentView;
        initContent();
    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        quantityLabel.setForeground(color.BLACK);
        transactionLabel.setForeground(color.BLACK);
        valueQuantityLabel.setForeground(color.BLACK);
        valueTransactionLabel.setForeground(color.BLACK);

        headerPanel.setBackground(color.WHITE);
        navigationPanel.setBackground(color.WHITE);
        contentPanel.setBackground(color.WHITE);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));
        quantityLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        transactionLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        valueQuantityLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));
        valueTransactionLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));
        dataTransactionLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        dataDetailTransactionLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
    }

    @Override
    public void initContent() {
        setPosition();
        setColor();
        setFont();
        setAction();
        showDataTransaction();

        headerPanel.add(dateField);
        headerPanel.add(quantityLabel);
        headerPanel.add(transactionLabel);
        headerPanel.add(valueQuantityLabel);
        headerPanel.add(valueTransactionLabel);

        navigationPanel.add(dataTransactionLabel);
        navigationPanel.add(dataDetailTransactionLabel);

        contentPanel.add(tablePanel);

        add(headerLabel);
        add(headerPanel);
        add(navigationPanel);
        add(contentPanel);

        setVisible(true);
    }

    private void setPosition() {
        headerLabel = new textLabel("Data Transaksi", 40, 0, 300, 80);
        quantityLabel = new textLabel("Transaksi", 30, 10, 200, 40);
        transactionLabel = new textLabel("Total Penjualan", 350, 10, 200, 40);

        valueQuantityLabel = new textLabel(qtyString, 30, 35, 400, 40);
        valueTransactionLabel = new textLabel("Rp. " + priceString, 350, 35, 400, 40);

        dataTransactionLabel = new linkLabel("Data Transaction", 40, 10, 130, 30);
        dataDetailTransactionLabel = new linkLabel("Detail Transaction", 220, 10, 135, 30);

        headerPanel = new panelRounded(40, 80, 1050, 80, 10, 10);
        navigationPanel = new panelRounded(40, 180, 1050, 50, 10, 10);
        contentPanel = new panelRounded(40, 250, 1050, 380, 10, 10);
        tablePanel = new panelRounded(0, 0, 1050, 380, 0, 0);

        dateField = new datePickerField(720, 20, 300, 40, today);
        dateField.setSelectedDate(today);

        dataTransaction = new tableNoActionButton(loadDataTransaction.getAllDataTransactionAdmin(selectedPriode));
        scrollDataTransaction = new scrollTable(dataTransaction, 0, 0, 1050, 380);

        detailTransaction = new tableNoActionButton(loadDataTransaction.getAllDetailTransactionAdmin(selectedPriode));
        scrollDetailTransaction = new scrollTable(detailTransaction, 0, 0, 1050, 380);
    }

    private void setAction() {
        dateField.getDatePicker().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    String fullDate = dateField.getSelectedDate();
                    if (fullDate != null) {
                        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(fullDate);
                        selectedPriode = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
                        switch (currentTransaction) {
                            case "Transaction":
                                loadTransaction(selectedPriode);
                                break;

                            case "Detail":
                                loadDetailTransaction(selectedPriode);
                                break;

                            default:
                                parentView.showFailedPopUp("Navigation tidak dikenali");
                                break;
                        }
                        refreshHeader();
                    } else {
                        parentView.showFailedPopUp("Pilih tanggal untuk melihat Data");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        dataTransactionLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentTransaction = "Transaction";
                showDataTransaction();
            }
        });

        dataDetailTransactionLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentTransaction = "Detail";
                showDetailTransaction();
            }
        });
    }

    private void resetLinkLabel() {
        dataTransactionLabel.setForeground(color.DARKGREY);
        dataTransactionLabel.setLabelInActive();

        dataDetailTransactionLabel.setForeground(color.DARKGREY);
        dataDetailTransactionLabel.setLabelInActive();

    }

    private void refreshHeader() {
        int totalQty = loadDataTransaction.getQuatityTransaction(selectedPriode);
        int totalPendapatan = loadDataTransaction.getQuantityPriceTransaction(selectedPriode);

        valueQuantityLabel.setText(String.valueOf(totalQty));
        valueTransactionLabel.setText("Rp. " + totalPendapatan);

        headerPanel.revalidate();
        headerPanel.repaint();
    }

    private void showDataTransaction() {
        resetLinkLabel();

        dataTransactionLabel.setForeground(color.DARKGREEN);
        dataTransactionLabel.setLabelActive();
        selectedPriode = getSelectedPriode();
        loadTransaction(selectedPriode);

    }

    private void showDetailTransaction() {
        resetLinkLabel();

        dataDetailTransactionLabel.setForeground(color.DARKGREEN);
        dataDetailTransactionLabel.setLabelActive();
        selectedPriode = getSelectedPriode();
        loadDetailTransaction(selectedPriode);

    }

    private String getSelectedPriode() {
        try {
            String fullDate = dateField.getSelectedDate();

            if (fullDate == null || fullDate.trim().isEmpty()) {
                fullDate = today;
            }

            Date parsedDate = sdf.parse(fullDate);
            return sdf.format(parsedDate);

        } catch (Exception e) {
            e.printStackTrace();
            return today;
        }
    }

    private void loadTransaction(String periode) {
        DefaultTableModel modelTransaction;
        if (periode == null || periode.isEmpty()) {
            modelTransaction = new DefaultTableModel();
        } else {
            modelTransaction = loadDataTransaction.getAllDataTransactionAdmin(periode);
        }

        dataTransaction.setModel(modelTransaction);

        if (dataTransaction instanceof tableNoActionButton) {
            tableNoActionButton customTable = (tableNoActionButton) dataTransaction;
            customTable.applyCustomStyle();
        }

        tablePanel.removeAll();
        tablePanel.add(scrollDataTransaction);
        tablePanel.revalidate();
        tablePanel.repaint();

        setHeaderDataTransaction();

    }

    private void loadDetailTransaction(String periode) {
        DefaultTableModel modelDetailTransaction;
        if (periode == null || periode.isEmpty()) {
            modelDetailTransaction = new DefaultTableModel();
        } else {
            modelDetailTransaction = loadDataTransaction.getAllDetailTransactionAdmin(periode);
        }

        detailTransaction.setModel(modelDetailTransaction);

        if (detailTransaction instanceof tableNoActionButton) {
            tableNoActionButton customTable = (tableNoActionButton) detailTransaction;
            customTable.applyCustomStyle();
        }

        tablePanel.removeAll();
        tablePanel.add(scrollDetailTransaction);
        tablePanel.revalidate();
        tablePanel.repaint();

        setHeaderDetailTransaction();
    }

    private void setHeaderDataTransaction() {

        dataTransaction.getColumnModel().getColumn(3).setMinWidth(80);
        dataTransaction.getColumnModel().getColumn(3).setMaxWidth(80);
        dataTransaction.getColumnModel().getColumn(3).setWidth(80);

        dataTransaction.getColumnModel().getColumn(5).setMinWidth(80);
        dataTransaction.getColumnModel().getColumn(5).setMaxWidth(80);
        dataTransaction.getColumnModel().getColumn(5).setWidth(80);

        dataTransaction.getColumnModel().getColumn(7).setMinWidth(100);
        dataTransaction.getColumnModel().getColumn(7).setMaxWidth(100);
        dataTransaction.getColumnModel().getColumn(7).setWidth(100);

        dataTransaction.getColumnModel().getColumn(8).setMinWidth(0);
        dataTransaction.getColumnModel().getColumn(8).setMaxWidth(0);
        dataTransaction.getColumnModel().getColumn(8).setWidth(0);
    }

    private void setHeaderDetailTransaction() {

        detailTransaction.getColumnModel().getColumn(7).setMinWidth(0);
        detailTransaction.getColumnModel().getColumn(7).setMaxWidth(0);
        detailTransaction.getColumnModel().getColumn(7).setWidth(0);
    }

}
