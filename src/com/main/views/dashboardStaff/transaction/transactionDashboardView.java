package com.main.views.dashboardStaff.transaction;

import java.util.EnumSet;
import java.util.List;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.models.alternatif.getPeriodeTransaction;
import com.main.models.transaction.loadDataTransaction;
import com.main.routes.dashboardStaffView;
import com.main.routes.mainFrame;
import com.main.services.authDataSaw;
import com.main.services.authDataTransaction;
import com.main.controller.actionButtonTable;

public class transactionDashboardView extends contentPanel {

    private mainFrame parentApp;

    private dashboardStaffView parentView;

    private textLabel headerLabel;

    private panelRounded headerPanel, contentPanel;

    private buttonCustom buttonAdd;

    private tableActionButton dataTransaction;
    private scrollTable scrollTableTransaction;

    private appIcons appIcons = new appIcons();
    private imageIcon addIcon = appIcons.getAddIconWhite(20, 20);

    private EnumSet<buttonType> buttonTypes = EnumSet.of(buttonType.DETAIL, buttonType.APPROVE);

    public transactionDashboardView(mainFrame parentApp, dashboardStaffView parentView) {
        super();
        this.parentApp = parentApp;
        this.parentView = parentView;
        initContent();
    }

    @Override
    public void initContent() {
        setPostion();
        setColor();
        setFont();
        handleButton();

        headerPanel.add(buttonAdd);

        contentPanel.add(scrollTableTransaction);

        add(headerLabel);
        add(headerPanel);
        add(contentPanel);

        setVisible(true);
    }

    private void setPostion() {
        headerLabel = new textLabel("Data Transaksi", 40, 0, 400, 80);
        headerPanel = new panelRounded(40, 80, 1050, 110, 10, 10);
        contentPanel = new panelRounded(40, 220, 1050, 410, 10, 10);

        buttonAdd = new buttonCustom("    " + "Tambah", 890, 40, 130, 40, 10);
        buttonAdd.setIcon(addIcon);

        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                // Not implemented
                System.out.println("onEdit row: " + row);
            }

            @Override
            public void onDelete(int row) {
                // Not implemented
                System.out.println("onDelete row: " + row);
            }

            @Override
            public void onDetail(int row) {
                // Not implemented
                System.out.println("onDetail row: " + row);
            }

            @Override
            public void onApprove(int row) {
                int selectedRow = dataTransaction.getSelectedRow();
                if (selectedRow != -1) {
                    String stringIdTransaction = dataTransaction.getValueAt(selectedRow, 0).toString().trim();
                    int idTransaction = Integer.parseInt(stringIdTransaction.replaceAll("[^0-9]", ""));

                    boolean isSuccess = authDataTransaction.updateStatusTransaction(idTransaction);

                    if (isSuccess) {
                        try {

                            String periode = null;
                            int attempts = 0;

                            while (periode == null && attempts < 5) {
                                Thread.sleep(1500); // tunggu 0.5 detik

                                periode = getPeriodeTransaction.getPeriodeByTransaction(idTransaction);
                                attempts++;
                            }

                            if (periode != null) {
                                List<Integer> idProductList = getPeriodeTransaction
                                        .getIdProductsByTransaction(idTransaction);
                                authDataSaw.executeSAW(periode, idProductList);

                                System.out.println("Periode : " + periode);
                            } else {
                                System.out.println("Clicked Transaction ID: " + idTransaction);
                                System.out.println("Periode hasil ambil: " + periode);

                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        parentApp.hideGlassNotificationPanel();
                        parentView.showSuccessPopUp("Success Approve Data Transaction");
                        parentView.showDashboardTransaction();
                    } else {
                        parentApp.hideGlassNotificationPanel();
                        parentView.showFailedPopUp("Failed Approve Data Transaction");
                        parentView.showDashboardTransaction();
                    }
                }
            }
        };

        dataTransaction = new tableActionButton(loadDataTransaction.getAllDataTransaction(), actionButton);

        int actionColumnIndex = 8;
        dataTransaction.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataTransaction.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollTableTransaction = new scrollTable(dataTransaction, 0, 0, 1050, 410);

        dataTransaction.getColumnModel().getColumn(0).setMinWidth(80);
        dataTransaction.getColumnModel().getColumn(0).setMaxWidth(80);
        dataTransaction.getColumnModel().getColumn(0).setWidth(80);

        dataTransaction.getColumnModel().getColumn(1).setMinWidth(0);
        dataTransaction.getColumnModel().getColumn(1).setMaxWidth(0);
        dataTransaction.getColumnModel().getColumn(1).setWidth(0);

        dataTransaction.getColumnModel().getColumn(3).setMinWidth(80);
        dataTransaction.getColumnModel().getColumn(3).setMaxWidth(80);
        dataTransaction.getColumnModel().getColumn(3).setWidth(80);

        dataTransaction.getColumnModel().getColumn(5).setMinWidth(100);
        dataTransaction.getColumnModel().getColumn(5).setMaxWidth(100);
        dataTransaction.getColumnModel().getColumn(5).setWidth(100);

        dataTransaction.getColumnModel().getColumn(6).setMinWidth(120);
        dataTransaction.getColumnModel().getColumn(6).setMaxWidth(120);
        dataTransaction.getColumnModel().getColumn(6).setWidth(120);

        dataTransaction.getColumnModel().getColumn(7).setMinWidth(100);
        dataTransaction.getColumnModel().getColumn(7).setMaxWidth(100);
        dataTransaction.getColumnModel().getColumn(7).setWidth(100);

        dataTransaction.getColumnModel().getColumn(8).setMinWidth(100);
        dataTransaction.getColumnModel().getColumn(8).setMaxWidth(100);
        dataTransaction.getColumnModel().getColumn(8).setWidth(100);
    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        headerPanel.setBackground(color.WHITE);
        contentPanel.setBackground(color.WHITE);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 30f));
    }

    private void handleButton() {
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showFormTransaction();
            }
        });
    }

}
