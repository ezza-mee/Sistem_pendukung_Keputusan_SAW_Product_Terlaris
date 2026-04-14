package com.main.views.popUp.popUpDataKriteria;

import java.util.EnumSet;

import com.main.components.*;
import com.main.components.panelApps.popUpPanel;
import com.main.controller.actionButtonTable;
import com.main.models.entity.dataBobotKriteria;
import com.main.models.kriteria.loadDataKriteria;
import com.main.routes.dashboardAdminView;
import com.main.views.popUp.popUpConfrim;

import com.main.routes.mainFrame;
import com.main.services.authDataKriteria;

public class popUpDataKriteria extends popUpPanel {

    private mainFrame parentApp;
    private dashboardAdminView parentView;
    private panelRounded headerPanel, containerTable, footerPanel;
    private textLabel headerLabel, resultBobotLabel;
    private tableActionButton dataBobotKriteria;
    private tableNoActionButton calculationBobotKriteria;
    private scrollTable scrollDataBobotKriteria, scrollTableCalculationKriteria;
    private linkLabel kriteriaLabel, resultKriteriaLabel;
    private buttonCustom buttonBack, buttonAdd;

    private appIcons appIcons = new appIcons();
    private imageIcon backIcon = appIcons.getBackIconWhite(20, 20);
    private imageIcon addIcon = appIcons.getAddIconWhite(20, 20);

    private EnumSet<buttonType> buttonTypes = EnumSet.of(buttonType.EDIT, buttonType.DELETE);

    String valueBobot = loadDataKriteria.getTotalBobotKriteriaText();
    String valueBobotKriteria = loadDataKriteria.getTotalResultKriteriaText();

    public popUpDataKriteria(mainFrame parentApp, dashboardAdminView parentView) {
        super();
        this.parentApp = parentApp;
        this.parentView = parentView;

        setSize(1000, 580);
        setBackground(color.DARKGREY);
        initComponent();
    }

    private void initComponent() {
        setLayout();
        setColor();
        setFont();
        setAction();
        showDataKriteria();

        add(headerLabel);
        add(headerPanel);
        add(containerTable);
        add(footerPanel);

        containerTable.add(resultBobotLabel);

        headerPanel.add(kriteriaLabel);
        headerPanel.add(resultKriteriaLabel);
        headerPanel.add(buttonAdd);

        footerPanel.add(buttonBack);

        setVisible(true);
    }

    private void setLayout() {
        headerPanel = new panelRounded(20, 70, 960, 75, 10, 10);
        containerTable = new panelRounded(0, 170, 1000, 330, 0, 0);
        footerPanel = new panelRounded(20, 510, 960, 60, 10, 10);

        headerLabel = new textLabel("Pengolahan Data Kriteria", 20, 0, 400, 70);
        resultBobotLabel = new textLabel("", 720, 285, 400, 40);

        kriteriaLabel = new linkLabel("Data Kriteria", 20, 20, 95, 30);
        resultKriteriaLabel = new linkLabel("Normalisasi Kriteria", 155, 20, 150, 30);

        buttonBack = new buttonCustom("Kembali", 20, 10, 130, 40, 10);
        buttonAdd = new buttonCustom("", 900, 20, 40, 40, 10);

        buttonBack.setIcon(backIcon);
        buttonAdd.setIcon(addIcon);

    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        resultBobotLabel.setForeground(color.BLACK);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));
        resultBobotLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 18f));

        kriteriaLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        resultKriteriaLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
    }

    private void setAction() {
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentApp.hideGlassDashboardPanel();
            }
        });

        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                int totalKriteria = authDataKriteria.getTotalKriteria();

                if (totalKriteria >= 4) {
                    parentView.showFailedPopUp("Maksimal input data kriteria hanya 4");
                } else {
                    parentView.showFormDataKriteria();
                }
            }
        });

        kriteriaLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showDataKriteria();
            }
        });

        resultKriteriaLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showResultKriteria();
            }
        });
    }

    private void setTableContent() {
        containerTable.removeAll();
        add(containerTable);
        containerTable.add(resultBobotLabel);
        containerTable.revalidate();
        containerTable.repaint();
    }

    private void showDataKriteria() {
        setTableContent();
        resetLinkLabel();
        kriteriaLabel.setForeground(color.DARKGREEN);
        kriteriaLabel.setLabelActive();
        loadDataKriteria();
        resultBobotLabel.setText("Total Bobot : " + valueBobot);
        resultBobotLabel.setBounds(790, 285, 400, 40);
        containerTable.add(scrollDataBobotKriteria);
    }

    private void showResultKriteria() {
        setTableContent();
        resetLinkLabel();
        resultKriteriaLabel.setForeground(color.DARKGREEN);
        resultKriteriaLabel.setLabelActive();
        loadResultDataKriteria();
        resultBobotLabel.setText("Total Bobot : " + valueBobotKriteria);
        resultBobotLabel.setBounds(790, 285, 400, 40);
        containerTable.add(scrollTableCalculationKriteria);
    }

    private void resetLinkLabel() {
        kriteriaLabel.setForeground(color.DARKGREY);
        kriteriaLabel.setLabelInActive();

        resultKriteriaLabel.setForeground(color.DARKGREY);
        resultKriteriaLabel.setLabelInActive();
    }

    private void loadDataKriteria() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView.showConfrimPopUp("Anda yakin untuk mengubah data kriteria?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataBobotKriteria.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdSBobotKriteria = dataBobotKriteria.getValueAt(selectedRow, 1).toString();
                                int idBobotKriteria = Integer.parseInt(stringIdSBobotKriteria.replaceAll("[^0-9]", ""));

                                dataBobotKriteria selectedDataSBobotKriteria = loadDataKriteria
                                        .getDataById(idBobotKriteria);

                                if (selectedDataSBobotKriteria != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataBobotKriteriaToEdit(selectedDataSBobotKriteria);
                                    parentView.showFormDataKriteria();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data bobot kriteria tidak ditemukan!");
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
                            .showConfrimPopUp("Anda yakin ingin menghapus data kriteria?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataBobotKriteria.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdBobotKriteria = dataBobotKriteria.getValueAt(selectedRow, 1).toString();
                                int idBobotKriteria = Integer.parseInt(stringIdBobotKriteria.replaceAll("[^0-9]", ""));
                                boolean isSuccess = authDataKriteria.deleteBobotKriteria(idBobotKriteria);

                                if (isSuccess) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showDashboardBobotKriteria();
                                    parentView.showSuccessPopUp("Data kriteria berhasil dihapus");
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showDashboardBobotKriteria();
                                    parentView.showFailedPopUp("Data kriteria gagal dihapus");
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

        dataBobotKriteria = new tableActionButton(loadDataKriteria.getAllDataBobotKriteria(), actionButton);

        int actionColumnIndex = 0;
        dataBobotKriteria.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataBobotKriteria.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataBobotKriteria = new scrollTable(dataBobotKriteria, 0, 0, 1000, 280);

        setHeaderDataKriteria();
    }

    private void loadResultDataKriteria() {
        calculationBobotKriteria = new tableNoActionButton(loadDataKriteria.getAllDataBobotKriteriaNormalisasi());
        scrollTableCalculationKriteria = new scrollTable(calculationBobotKriteria, 0, 0, 1000, 280);

        setHeaderBobotKriteria();
    }

    private void setHeaderDataKriteria() {

        dataBobotKriteria.getColumnModel().getColumn(0).setMinWidth(120);
        dataBobotKriteria.getColumnModel().getColumn(0).setMaxWidth(120);
        dataBobotKriteria.getColumnModel().getColumn(0).setWidth(120);

        dataBobotKriteria.getColumnModel().getColumn(1).setMinWidth(80);
        dataBobotKriteria.getColumnModel().getColumn(1).setMaxWidth(80);
        dataBobotKriteria.getColumnModel().getColumn(1).setWidth(80);

        dataBobotKriteria.getColumnModel().getColumn(2).setMinWidth(160);
        dataBobotKriteria.getColumnModel().getColumn(2).setMaxWidth(160);
        dataBobotKriteria.getColumnModel().getColumn(2).setWidth(160);

        dataBobotKriteria.getColumnModel().getColumn(4).setMinWidth(100);
        dataBobotKriteria.getColumnModel().getColumn(4).setMaxWidth(100);
        dataBobotKriteria.getColumnModel().getColumn(4).setWidth(100);

        dataBobotKriteria.getColumnModel().getColumn(5).setMinWidth(100);
        dataBobotKriteria.getColumnModel().getColumn(5).setMaxWidth(100);
        dataBobotKriteria.getColumnModel().getColumn(5).setWidth(100);

    }

    private void setHeaderBobotKriteria() {

        calculationBobotKriteria.getColumnModel().getColumn(0).setMinWidth(80);
        calculationBobotKriteria.getColumnModel().getColumn(0).setMaxWidth(80);
        calculationBobotKriteria.getColumnModel().getColumn(0).setWidth(80);

        calculationBobotKriteria.getColumnModel().getColumn(2).setMinWidth(100);
        calculationBobotKriteria.getColumnModel().getColumn(2).setMaxWidth(100);
        calculationBobotKriteria.getColumnModel().getColumn(2).setWidth(100);

        calculationBobotKriteria.getColumnModel().getColumn(3).setMinWidth(100);
        calculationBobotKriteria.getColumnModel().getColumn(3).setMaxWidth(100);
        calculationBobotKriteria.getColumnModel().getColumn(3).setWidth(100);

        calculationBobotKriteria.getColumnModel().getColumn(4).setMinWidth(100);
        calculationBobotKriteria.getColumnModel().getColumn(4).setMaxWidth(100);
        calculationBobotKriteria.getColumnModel().getColumn(4).setWidth(100);

    }
}
