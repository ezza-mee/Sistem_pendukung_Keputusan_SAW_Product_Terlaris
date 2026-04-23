package com.main.views.dashboardAdmin.convertion;

import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.table.DefaultTableModel;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.controller.actionButtonTable;
import com.main.controller.searchableView;
import com.main.models.convertion.loadDataConvertion;
import com.main.models.entity.dataSearchConvertion;
import com.main.models.entity.dataConvertion;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;
import com.main.services.authDataConvertion;
import com.main.views.popUp.popUpConfrim;

public class convertionDashboardView extends contentPanel implements searchableView {

    private mainFrame parentApp;

    private dashboardAdminView parentView;

    private panelRounded headerPanel, contentPanel;

    private buttonCustom buttonAdd;

    private tableActionButton dataConvertion;

    private scrollTable scrollDataConvertion;

    private textLabel headerLabel;

    private EnumSet<buttonType> buttonTypes = EnumSet.of(buttonType.EDIT,
            buttonType.DELETE);

    private appIcons appIcons = new appIcons();
    private imageIcon addIcon = appIcons.getAddIconWhite(20, 20);

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        headerPanel.setBackground(color.WHITE);
        contentPanel.setBackground(color.WHITE);

    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));
    }

    public convertionDashboardView(mainFrame parentApp, dashboardAdminView parentView) {
        super();
        this.parentView = parentView;
        this.parentApp = parentApp;

        initContent();
    }

    @Override
    public void initContent() {
        setLayout();
        setColor();
        setFont();
        setAction();

        headerPanel.add(buttonAdd);
        contentPanel.add(scrollDataConvertion);

        add(headerLabel);
        add(headerPanel);
        add(contentPanel);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Data Konversi", 40, 0, 300, 80);
        headerPanel = new panelRounded(40, 80, 1050, 110, 10, 10);
        contentPanel = new panelRounded(40, 220, 1050, 410, 10, 10);

        buttonAdd = new buttonCustom("    " + "Tambah", 890, 35, 130, 40, 10);
        buttonAdd.setIcon(addIcon);

        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView
                            .showConfrimPopUp("Apakah anda ingin menghapus data konversi?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataConvertion.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdSConvertion = dataConvertion.getValueAt(selectedRow, 0).toString();
                                int idConvertion = Integer.parseInt(stringIdSConvertion.replaceAll("[^0-9]", ""));

                                dataConvertion selectedDataSConvertion = loadDataConvertion.getDataById(idConvertion);

                                if (selectedDataSConvertion != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataConvertionToEdit(selectedDataSConvertion);
                                    parentView.showFormConvertion();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data konversi tidak ditemukan!");
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
                            .showConfrimPopUp("Apakah anda ingin menghapus data konversi?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataConvertion.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdConvertion = dataConvertion.getValueAt(selectedRow, 0).toString();
                                int idConvertion = Integer.parseInt(stringIdConvertion.replaceAll("[^0-9]", ""));
                                boolean isSuccess = authDataConvertion.deleteDataConvertion(idConvertion);

                                if (isSuccess) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showSuccessPopUp("Data konversi berhasil dihapus");
                                    parentView.showDashboardConvertion();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data Konversi gagal dihapus!");
                                    parentView.showDashboardConvertion();
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

        dataConvertion = new tableActionButton(loadDataConvertion.getAllDataConvertion(), actionButton);

        int actionColumnIndex = 6;
        dataConvertion.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataConvertion.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataConvertion = new scrollTable(dataConvertion, 0, 0, 1050, 410);

        dataConvertion.getColumnModel().getColumn(0).setMinWidth(80);
        dataConvertion.getColumnModel().getColumn(0).setMaxWidth(80);
        dataConvertion.getColumnModel().getColumn(0).setWidth(80);

        dataConvertion.getColumnModel().getColumn(1).setMinWidth(150);
        dataConvertion.getColumnModel().getColumn(1).setMaxWidth(150);
        dataConvertion.getColumnModel().getColumn(1).setWidth(150);

        dataConvertion.getColumnModel().getColumn(2).setMinWidth(100);
        dataConvertion.getColumnModel().getColumn(2).setMaxWidth(100);
        dataConvertion.getColumnModel().getColumn(2).setWidth(100);

        dataConvertion.getColumnModel().getColumn(3).setMinWidth(120);
        dataConvertion.getColumnModel().getColumn(3).setMaxWidth(120);
        dataConvertion.getColumnModel().getColumn(3).setWidth(120);

        dataConvertion.getColumnModel().getColumn(4).setMinWidth(90);
        dataConvertion.getColumnModel().getColumn(4).setMaxWidth(90);
        dataConvertion.getColumnModel().getColumn(4).setWidth(90);

        dataConvertion.getColumnModel().getColumn(6).setMinWidth(120);
        dataConvertion.getColumnModel().getColumn(6).setMaxWidth(120);
        dataConvertion.getColumnModel().getColumn(6).setWidth(120);

    }

    private void setAction() {
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showFormConvertion();
            }
        });
    }

    public void filterDataByKeyword(String keyword) {
        DefaultTableModel model = (DefaultTableModel) dataConvertion.getModel();
        model.setRowCount(0);

        ArrayList<dataSearchConvertion> list;

        if (keyword == null || keyword.trim().isEmpty()) {
            // Jika keyword kosong, tampilkan semua data
            list = loadDataConvertion.getAllConvertion(); // Pastikan method ini ada
        } else {
            // Jika keyword ada, lakukan pencarian
            list = authDataConvertion.searchConverstionByKeyword(keyword);
        }

        for (dataSearchConvertion dataConvertion : list) {
            model.addRow(new Object[] {
                    "CD00" + dataConvertion.getIdConvertion(),
                    dataConvertion.getDate(),
                    dataConvertion.getFormUnit(),
                    dataConvertion.getToUnit(),
                    dataConvertion.getMultiplier(),
                    dataConvertion.getDescription(),
                    "Aksi"
            });
        }

    }

}
