package com.main.views.dashboardAdmin.staff;

import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.table.DefaultTableModel;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.controller.searchableView;
import com.main.controller.actionButtonTable;
import com.main.models.entity.dataStaff;
import com.main.models.entity.entityDataStaff;
import com.main.models.staff.loadDataStaff;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;
import com.main.services.authDataStaff;
import com.main.views.popUp.popUpConfrim;

public class staffDashboardView extends contentPanel implements searchableView {

    private mainFrame parentApp;
    private dashboardAdminView parentView;
    private textLabel headerLabel;
    private panelRounded headerPanel, contentPanel;
    private buttonCustom buttonAdd;
    private tableActionButton dataStaffTable;
    private scrollTable scrollDataStaff;
    private EnumSet<buttonType> buttonTypes = EnumSet.of(buttonType.EDIT, buttonType.DELETE, buttonType.DETAIL);
    private linkLabel allDataStaffLabel, dataStaffActiveLabel, dataStaffInActiveLabel, dataStaffResignLabel;

    private int quantityAllDataStaff = loadDataStaff.getAllQuantityDataStaff();
    private int quantityDataStaffActive = loadDataStaff.getAllQuantityDataStaffActive();
    private int quantityDataStaffInActive = loadDataStaff.getAllQuantityDataStaffInActive();
    private int quantityDataStaffResign = loadDataStaff.getAllQuantityDataStaffResign();

    private appIcons appIcons = new appIcons();
    private imageIcon addIcon = appIcons.getAddIconWhite(20, 20);

    private String currentStatus = "ALL";

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        headerPanel.setBackground(color.WHITE);
        contentPanel.setBackground(color.WHITE);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));
        allDataStaffLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        dataStaffActiveLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        dataStaffInActiveLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        dataStaffResignLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
    }

    public staffDashboardView(mainFrame parentApp, dashboardAdminView parentView) {
        super();
        this.parentApp = parentApp;
        this.parentView = parentView;
        initContent();
    }

    @Override
    public void initContent() {
        setLayout();
        setColor();
        setFont();
        setAction();
        showAllDataStaff();

        headerPanel.add(allDataStaffLabel);
        headerPanel.add(dataStaffActiveLabel);
        headerPanel.add(dataStaffInActiveLabel);
        headerPanel.add(dataStaffResignLabel);
        headerPanel.add(buttonAdd);

        contentPanel.add(scrollDataStaff);

        add(headerLabel);
        add(headerPanel);
        add(contentPanel);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Data Staff", 40, 0, 200, 80);
        headerPanel = new panelRounded(40, 80, 1050, 110, 10, 10);
        contentPanel = new panelRounded(40, 220, 1050, 410, 10, 10);

        allDataStaffLabel = new linkLabel("ALL", 40, 40, 80, 30);
        allDataStaffLabel.setQuantity(quantityAllDataStaff);
        dataStaffActiveLabel = new linkLabel("Active", 155, 40, 90, 30);
        dataStaffActiveLabel.setQuantity(quantityDataStaffActive);
        dataStaffInActiveLabel = new linkLabel("In Active", 280, 40, 110, 30);
        dataStaffInActiveLabel.setQuantity(quantityDataStaffInActive);
        dataStaffResignLabel = new linkLabel("Resign", 430, 40, 110, 30);
        dataStaffResignLabel.setQuantity(quantityDataStaffResign);

        buttonAdd = new buttonCustom("    " +"Tambah", 900, 35, 130, 40, 10);
        buttonAdd.setIcon(addIcon);
    }

    private void setAction() {
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {

                parentView.showFormStaff();
            }
        });

        allDataStaffLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentStatus = "ALL";
                showAllDataStaff();
            }
        });

        dataStaffActiveLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentStatus = "Active";
                showDataStaffActive();
            }
        });

        dataStaffInActiveLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentStatus = "Inctive";
                showDataStaffInActive();
            }
        });

        dataStaffResignLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentStatus = "Resign";
                showDataStaffResign();
            }
        });
    }

    private void resetLinkLabel() {
        allDataStaffLabel.setForeground(color.DARKGREY);
        allDataStaffLabel.setLabelInActive();

        dataStaffActiveLabel.setForeground(color.DARKGREY);
        dataStaffActiveLabel.setLabelInActive();

        dataStaffInActiveLabel.setForeground(color.DARKGREY);
        dataStaffInActiveLabel.setLabelInActive();

        dataStaffResignLabel.setForeground(color.DARKGREEN);
        dataStaffResignLabel.setLabelInActive();
    }

    private void showAllDataStaff() {
        resetLinkLabel();
        allDataStaffLabel.setForeground(color.DARKGREEN);
        allDataStaffLabel.setLabelActive();
        loadAllDataStaff();

    }

    private void showDataStaffActive() {
        resetLinkLabel();
        dataStaffActiveLabel.setForeground(color.DARKGREEN);
        dataStaffActiveLabel.setLabelActive();
        loadDataStaffActive();
    }

    private void showDataStaffInActive() {
        resetLinkLabel();
        dataStaffInActiveLabel.setForeground(color.DARKGREEN);
        dataStaffInActiveLabel.setLabelActive();
        loadDataStaffInActive();
    }

    private void showDataStaffResign() {
        resetLinkLabel();
        dataStaffResignLabel.setForeground(color.DARKGREEN);
        dataStaffResignLabel.setLabelActive();
        loadDataStaffResign();
    }

    private void loadAllDataStaff() {

        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView
                            .showConfrimPopUp("Apakah anda ingin mengubah data staff?");
                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataStaffTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                                int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));

                                entityDataStaff selectedDataStaff = loadDataStaff.getDataStaffById(idStaff);

                                if (selectedDataStaff != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataStaffToEdit(selectedDataStaff);
                                    parentView.showFormStaff();
                                } else {
                                    parentView.showFailedPopUp("Data staff tidak ditemukan!");
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
                            .showConfrimPopUp("Apakah anda ingin menghapus data staff?");
                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataStaffTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                                int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));
                                boolean isSuccess = authDataStaff.resignStaffById(idStaff);

                                if (isSuccess) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showSuccessPopUp("Data staff berhasil dihapus");
                                    parentView.showDashboardStaff();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data staff gagal dihapus!");
                                    parentView.showDashboardStaff();
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
                try {
                    int selectedRow = dataStaffTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                        int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));
                        parentView.showDetailPopUpDataStaff(idStaff);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    parentView.showFailedPopUp("Data staff tidak ditemukan!");
                }
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataStaffTable = new tableActionButton(loadDataStaff.getAllDataStaff(), actionButton);

        int actionColumnIndex = 5;
        dataStaffTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataStaffTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataStaff = new scrollTable(dataStaffTable, 0, 0, 1050, 410);

        setHeaderTableAllDataStaff();
        contentPanel.removeAll(); // ini penting
        contentPanel.add(scrollDataStaff);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void loadDataStaffActive() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView
                            .showConfrimPopUp("Apakah anda ingin mengubah data staff?");
                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataStaffTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                                int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));

                                entityDataStaff selectedDataStaff = loadDataStaff.getDataStaffById(idStaff);

                                if (selectedDataStaff != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataStaffToEdit(selectedDataStaff);
                                    parentView.showFormStaff();
                                } else {
                                    parentView.showFailedPopUp("Data staff tidak ditemukan!");
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
                            .showConfrimPopUp("Apakah anda ingin menghapus data staff?");
                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataStaffTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                                int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));
                                boolean isSuccess = authDataStaff.resignStaffById(idStaff);

                                if (isSuccess) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showSuccessPopUp("Data staff berhasil dihapus");
                                    parentView.showDashboardStaff();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data staff gagal dihapus!");
                                    parentView.showDashboardStaff();
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
                try {
                    int selectedRow = dataStaffTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                        int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));
                        parentView.showDetailPopUpDataStaff(idStaff);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    parentView.showFailedPopUp("Data staff tidak ditemukan!");
                }
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataStaffTable = new tableActionButton(loadDataStaff.getAllDataStaffActive(), actionButton);

        int actionColumnIndex = 5;
        dataStaffTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataStaffTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataStaff = new scrollTable(dataStaffTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollDataStaff);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataStaff();
    }

    private void loadDataStaffInActive() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView
                            .showConfrimPopUp("Apakah anda ingin mengubah data staff?");
                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataStaffTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                                int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));

                                entityDataStaff selectedDataStaff = loadDataStaff.getDataStaffById(idStaff);

                                if (selectedDataStaff != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataStaffToEdit(selectedDataStaff);
                                    parentView.showFormStaff();
                                } else {
                                    parentView.showFailedPopUp("Data staff tidak ditemukan!");
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
                            .showConfrimPopUp("Apakah anda ingin menghapus data staff?");
                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataStaffTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                                int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));
                                boolean isSuccess = authDataStaff.resignStaffById(idStaff);

                                if (isSuccess) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showSuccessPopUp("Data staff berhasil dihapus");
                                    parentView.showDashboardStaff();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data staff gagal dihapus!");
                                    parentView.showDashboardStaff();
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
                try {
                    int selectedRow = dataStaffTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                        int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));
                        parentView.showDetailPopUpDataStaff(idStaff);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    parentView.showFailedPopUp("Data staff tidak ditemukan!");
                }
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataStaffTable = new tableActionButton(loadDataStaff.getAllDataStaffInActive(), actionButton);

        int actionColumnIndex = 5;
        dataStaffTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataStaffTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataStaff = new scrollTable(dataStaffTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollDataStaff);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataStaff();
    }

    private void loadDataStaffResign() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView
                            .showConfrimPopUp("Apakah anda ingin mengubah data staff?");
                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataStaffTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                                int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));

                                entityDataStaff selectedDataStaff = loadDataStaff.getDataStaffById(idStaff);

                                if (selectedDataStaff != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataStaffToEdit(selectedDataStaff);
                                    parentView.showFormStaff();
                                } else {
                                    parentView.showFailedPopUp("Data staff tidak ditemukan!");
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
                            .showConfrimPopUp("Apakah anda ingin menghapus data staff?");
                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataStaffTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                                int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));
                                boolean isSuccess = authDataStaff.resignStaffById(idStaff);

                                if (isSuccess) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showSuccessPopUp("Data staff berhasil dihapus");
                                    parentView.showDashboardStaff();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data staff gagal dihapus!");
                                    parentView.showDashboardStaff();
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
                try {
                    int selectedRow = dataStaffTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String stringIdStaff = dataStaffTable.getValueAt(selectedRow, 0).toString();
                        int idStaff = Integer.parseInt(stringIdStaff.replaceAll("[^0-9]", ""));
                        parentView.showDetailPopUpDataStaff(idStaff);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    parentView.showFailedPopUp("Data staff tidak ditemukan!");
                }
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataStaffTable = new tableActionButton(loadDataStaff.getAllDataStaffResign(), actionButton);

        int actionColumnIndex = 5;
        dataStaffTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataStaffTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataStaff = new scrollTable(dataStaffTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollDataStaff);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataStaff();
    }

    private void setHeaderTableAllDataStaff() {
        dataStaffTable.getColumnModel().getColumn(0).setMinWidth(80);
        dataStaffTable.getColumnModel().getColumn(0).setMaxWidth(80);
        dataStaffTable.getColumnModel().getColumn(0).setWidth(80);

        dataStaffTable.getColumnModel().getColumn(3).setMinWidth(120);
        dataStaffTable.getColumnModel().getColumn(3).setMaxWidth(120);
        dataStaffTable.getColumnModel().getColumn(3).setWidth(120);

        dataStaffTable.getColumnModel().getColumn(4).setMinWidth(90);
        dataStaffTable.getColumnModel().getColumn(4).setMaxWidth(90);
        dataStaffTable.getColumnModel().getColumn(4).setWidth(90);
    }

    public void filterDataByKeyword(String keyword) {
        DefaultTableModel model = (DefaultTableModel) dataStaffTable.getModel();
        model.setRowCount(0);

        ArrayList<dataStaff> StaffList = new ArrayList<>();

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean filterByStatus = currentStatus != null &&
                (currentStatus.equalsIgnoreCase("Active") ||
                        currentStatus.equalsIgnoreCase("Inactive") ||
                        currentStatus.equalsIgnoreCase("Resign"));

        if (hasKeyword) {
            // Ada keyword dan status
            if (filterByStatus) {
                StaffList = authDataStaff.searchStaffByKeywordAndStatus(keyword, currentStatus);
            } else {
                StaffList = authDataStaff.searchStaffByKeyword(keyword);
            }
        } else {
            // Tidak ada keyword
            if (filterByStatus) {
                StaffList = loadDataStaff.getAllStaffByStatus(currentStatus);
            } else {
                StaffList = loadDataStaff.getAllStaff();
            }
        }

        for (dataStaff dataStaff : StaffList) {
            model.addRow(new Object[] {
                    "NS00" + dataStaff.getIdStaff(),
                    dataStaff.getDate(),
                    dataStaff.getName(),
                    dataStaff.getJobdesk(),
                    dataStaff.getStatus(),
                    "Aksi"
            });
        }
    }

}
