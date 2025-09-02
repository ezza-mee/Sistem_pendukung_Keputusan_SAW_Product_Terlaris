package com.main.views.popUp.popUpBobotKriteria;

import java.util.EnumSet;

import com.main.components.*;
import com.main.components.panelApps.popUpPanel;
import com.main.controller.actionButtonTable;
import com.main.models.bobotKriteria.loadDataBobotKriteria;
import com.main.models.entity.dataBobotKriteria;
import com.main.routes.dashboardAdminView;
import com.main.views.popUp.popUpConfrim;
import com.main.routes.mainFrame;
import com.main.services.authDataBobotKriteria;

public class popUpDataBobotKriteria extends popUpPanel {

    private mainFrame parentApp;
    private dashboardAdminView parentView;

    private textLabel headerLabel, totalWeightBobot, totalBobotKriteria, resultBobotLabel;

    private tableActionButton dataBobotKriteria;
    private tableNoActionButton calculationBobotKriteria;

    private scrollTable scrollDataBobotKriteria, scrollTableCalculationKriteria;

    private buttonCustom buttonBack, buttonAdd, buttonDetail;

    private appIcons appIcons = new appIcons();
    private imageIcon backIcon = appIcons.getBackIconWhite(20, 20);
    private imageIcon addIcon = appIcons.getAddIconWhite(20, 20);
    private imageIcon detailIcon = appIcons.getDetailIconWhite(20, 20);

    private EnumSet<buttonType> buttonTypes = EnumSet.of(buttonType.EDIT, buttonType.DELETE);

    String valueWeightBobot = loadDataBobotKriteria.getTotalBobotKriteriaText();
    String valueBobotKriteria = loadDataBobotKriteria.getTotalResultKriteriaText();

    public popUpDataBobotKriteria(mainFrame parentApp, dashboardAdminView parentView) {
        super();
        this.parentApp = parentApp;
        this.parentView = parentView;

        setSize(1000, 750);
        initComponent();
    }

    private void initComponent() {
        setLayout();
        setColor();
        setFont();
        handleButton();

        add(totalWeightBobot);
        add(totalBobotKriteria);

        add(resultBobotLabel);

        add(scrollDataBobotKriteria);
        add(scrollTableCalculationKriteria);

        add(headerLabel);
        add(buttonBack);
        add(buttonDetail);
        add(buttonAdd);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Data Kriteria", 200, 5, 400, 70);
        resultBobotLabel = new textLabel("Result Weight Kriteria", 20, 390, 400, 40);
        buttonBack = new buttonCustom("Back", 20, 20, 130, 40, 10);
        buttonAdd = new buttonCustom("", 920, 20, 40, 40, 10);
        buttonDetail = new buttonCustom("", 860, 20, 40, 40, 10);

        totalBobotKriteria = new textLabel(valueWeightBobot, 780, 360, 400, 40);
        totalWeightBobot = new textLabel(valueBobotKriteria, 780, 710, 400, 40);

        buttonBack.setIcon(backIcon);
        buttonAdd.setIcon(addIcon);
        buttonDetail.setIcon(detailIcon);

        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView.showConfrimPopUp("do you want to update Data Kriteria?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataBobotKriteria.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdSBobotKriteria = dataBobotKriteria.getValueAt(selectedRow,
                                        0).toString();
                                int idBobotKriteria = Integer.parseInt(stringIdSBobotKriteria.replaceAll("[^0-9]", ""));

                                dataBobotKriteria selectedDataSBobotKriteria = loadDataBobotKriteria
                                        .getDataById(idBobotKriteria);

                                if (selectedDataSBobotKriteria != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataBobotKriteriaToEdit(selectedDataSBobotKriteria);
                                    parentView.showFormDataKriteria();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data Bobot Kriteria not found!");
                                }
                            }
                        }
                    });

                    messagePopUp.getButtonCancel().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            parentApp.hideGlassNotificationPanel();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDelete(int row) {
                try {

                    popUpConfrim messagePopUp = parentView
                            .showConfrimPopUp("do you want to delete data Bobot Kriteria?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataBobotKriteria.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdBobotKriteria = dataBobotKriteria.getValueAt(selectedRow, 0).toString();
                                int idBobotKriteria = Integer.parseInt(stringIdBobotKriteria.replaceAll("[^0-9]", ""));
                                boolean isSuccess = authDataBobotKriteria.deleteBobotKriteria(idBobotKriteria);

                                if (isSuccess) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showDashboardBobotKriteria();
                                    parentView.showSuccessPopUp("Success Delete Data BobotKriteria");
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showDashboardBobotKriteria();
                                    parentView.showFailedPopUp("Failed Delete Data BobotKriteria");
                                }

                            }
                        }
                    });

                    messagePopUp.getButtonCancel().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            parentApp.hideGlassNotificationPanel();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDetail(int row) {
                // Not implemented
                System.out.println("Detail row: " + row);
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataBobotKriteria = new tableActionButton(loadDataBobotKriteria.getAllDataBobotKriteria(), actionButton);

        int actionColumnIndex = 5;
        dataBobotKriteria.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataBobotKriteria.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataBobotKriteria = new scrollTable(dataBobotKriteria, 0, 80, 1000,
                280);

        calculationBobotKriteria = new tableNoActionButton(loadDataBobotKriteria.getAllDataBobotKriteriaNormalisasi());
        scrollTableCalculationKriteria = new scrollTable(calculationBobotKriteria, 0, 430, 1050, 280);

        setHeaderDataKriteria();
        setHeaderBobotKriteria();
    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        totalBobotKriteria.setForeground(color.BLACK);
        totalWeightBobot.setForeground(color.BLACK);
        resultBobotLabel.setForeground(color.BLACK);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));
        totalBobotKriteria.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 18f));
        totalWeightBobot.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 18f));
        resultBobotLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 18f));

    }

    private void handleButton() {
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentApp.hideGlassDashboardPanel();
            }
        });

        buttonDetail.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showBobotKriteriaPopUp();
            }
        });

        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showFormDataKriteria();
            }
        });
    }

    private void setHeaderDataKriteria() {

        dataBobotKriteria.getColumnModel().getColumn(0).setMinWidth(80);
        dataBobotKriteria.getColumnModel().getColumn(0).setMaxWidth(80);
        dataBobotKriteria.getColumnModel().getColumn(0).setWidth(80);

        dataBobotKriteria.getColumnModel().getColumn(1).setMinWidth(390);
        dataBobotKriteria.getColumnModel().getColumn(1).setMaxWidth(390);
        dataBobotKriteria.getColumnModel().getColumn(1).setWidth(390);

        dataBobotKriteria.getColumnModel().getColumn(2).setMinWidth(80);
        dataBobotKriteria.getColumnModel().getColumn(2).setMaxWidth(80);
        dataBobotKriteria.getColumnModel().getColumn(2).setWidth(80);

        dataBobotKriteria.getColumnModel().getColumn(3).setMinWidth(100);
        dataBobotKriteria.getColumnModel().getColumn(3).setMaxWidth(100);
        dataBobotKriteria.getColumnModel().getColumn(3).setWidth(100);

    }

    private void setHeaderBobotKriteria() {

        calculationBobotKriteria.getColumnModel().getColumn(0).setMinWidth(80);
        calculationBobotKriteria.getColumnModel().getColumn(0).setMaxWidth(80);
        calculationBobotKriteria.getColumnModel().getColumn(0).setWidth(80);

        calculationBobotKriteria.getColumnModel().getColumn(1).setMinWidth(390);
        calculationBobotKriteria.getColumnModel().getColumn(1).setMaxWidth(390);
        calculationBobotKriteria.getColumnModel().getColumn(1).setWidth(390);

    }
}
