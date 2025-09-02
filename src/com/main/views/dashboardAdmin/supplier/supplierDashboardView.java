package com.main.views.dashboardAdmin.supplier;

import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.table.DefaultTableModel;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.controller.actionButtonTable;
import com.main.controller.searchableView;
import com.main.models.entity.dataSearchSupplier;
import com.main.models.entity.dataSupplier;
import com.main.models.supplier.loadDataSupplier;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;
import com.main.services.authDataSupplier;
import com.main.views.popUp.popUpConfrim;

public class supplierDashboardView extends contentPanel implements searchableView {

    private mainFrame parentApp;
    private dashboardAdminView parentView;
    private textLabel headerLabel;
    private panelRounded headerPanel, contentPanel;
    private linkLabel allSupplierLabel, pendingSupplierLabel, stockSupplierLabel, outStockSupplierLabel,
            rejectedSupplierLabel;
    private tableActionButton dataSupplierTable;
    private scrollTable scrollDataSupplier;
    private int quantityAllDataSupplier = loadDataSupplier.getAllQuantityDataSupplier();
    private int quantityAllPendingDataSupplier = loadDataSupplier.getAllQuantityPendingDataSupplier();
    private int quantityAllStockDataSupplier = loadDataSupplier.getAllQuantityStockDataSupplier();
    private int quantityAllOutStockDataSupplier = loadDataSupplier.getAllQuantityOutStockDataSupplier();
    private int quantityAllRejectedDataSupplier = loadDataSupplier.getAllQuantityRejectedDataSupplier();
    private EnumSet<buttonType> buttonTypes = EnumSet.of(buttonType.EDIT, buttonType.APPROVE);

    private String currentStatus = "ALL";

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        headerPanel.setBackground(color.WHITE);
        contentPanel.setBackground(color.WHITE);

    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));

        allSupplierLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        pendingSupplierLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        stockSupplierLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        outStockSupplierLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        rejectedSupplierLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));

    }

    public supplierDashboardView(mainFrame parentApp, dashboardAdminView parentView) {
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
        showAllSupplier();

        headerPanel.add(allSupplierLabel);
        headerPanel.add(pendingSupplierLabel);
        headerPanel.add(stockSupplierLabel);
        headerPanel.add(outStockSupplierLabel);
        headerPanel.add(rejectedSupplierLabel);

        contentPanel.add(scrollDataSupplier);

        add(headerLabel);
        add(headerPanel);
        add(contentPanel);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Data Supplier", 40, 0, 300, 80);
        headerPanel = new panelRounded(40, 80, 1050, 110, 10, 10);
        contentPanel = new panelRounded(40, 220, 1050, 410, 10, 10);

        allSupplierLabel = new linkLabel("ALL", 40, 40, 80, 30);
        allSupplierLabel.setQuantity(quantityAllDataSupplier);
        pendingSupplierLabel = new linkLabel("Pending", 155, 40, 120, 30);
        pendingSupplierLabel.setQuantity(quantityAllPendingDataSupplier);
        stockSupplierLabel = new linkLabel("In Stock", 310, 40, 120, 30);
        stockSupplierLabel.setQuantity(quantityAllStockDataSupplier);
        outStockSupplierLabel = new linkLabel("Out Stock", 470, 40, 120, 30);
        outStockSupplierLabel.setQuantity(quantityAllOutStockDataSupplier);
        rejectedSupplierLabel = new linkLabel("Rejected", 630, 40, 120, 30);
        rejectedSupplierLabel.setQuantity(quantityAllRejectedDataSupplier);

    }

    private void setAction() {

        allSupplierLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentStatus = "ALL";
                showAllSupplier();
            }
        });

        pendingSupplierLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentStatus = "Pending";
                showPendingSupplier();
            }
        });

        stockSupplierLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentStatus = "Ready";
                showStockSupplier();
            }
        });

        outStockSupplierLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentStatus = "Out of Stock";
                showOutStockSupplier();
            }
        });

        rejectedSupplierLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentStatus = "Rejected";
                showRejectedSupplier();
            }
        });

    }

    private void resetLinkLabel() {
        allSupplierLabel.setForeground(color.DARKGREY);
        allSupplierLabel.setLabelInActive();

        pendingSupplierLabel.setForeground(color.DARKGREY);
        pendingSupplierLabel.setLabelInActive();

        stockSupplierLabel.setForeground(color.DARKGREY);
        stockSupplierLabel.setLabelInActive();

        outStockSupplierLabel.setForeground(color.DARKGREEN);
        outStockSupplierLabel.setLabelInActive();

        rejectedSupplierLabel.setForeground(color.DARKGREY);
        rejectedSupplierLabel.setLabelInActive();

    }

    private void showAllSupplier() {
        resetLinkLabel();
        allSupplierLabel.setForeground(color.DARKGREEN);
        allSupplierLabel.setLabelActive();
        loadAllSupplier();

    }

    private void showPendingSupplier() {
        resetLinkLabel();
        pendingSupplierLabel.setForeground(color.DARKGREEN);
        pendingSupplierLabel.setLabelActive();
        loadPendingSupplier();
    }

    private void showStockSupplier() {
        resetLinkLabel();
        stockSupplierLabel.setForeground(color.DARKGREEN);
        stockSupplierLabel.setLabelActive();
        loadStockSupplier();
    }

    private void showOutStockSupplier() {
        resetLinkLabel();
        outStockSupplierLabel.setForeground(color.DARKGREEN);
        outStockSupplierLabel.setLabelActive();
        loadOutStockSupplier();
    }

    private void showRejectedSupplier() {
        resetLinkLabel();
        rejectedSupplierLabel.setForeground(color.DARKGREEN);
        rejectedSupplierLabel.setLabelActive();
        loadRejectedSupplier();
    }

    private void loadAllSupplier() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView.showConfrimPopUp("do you want to delete product data?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataSupplierTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdSupplier = dataSupplierTable.getValueAt(selectedRow, 0).toString();
                                int idSupplier = Integer.parseInt(stringIdSupplier.replaceAll("[^0-9]", ""));

                                dataSupplier selectedDataSupplier = loadDataSupplier.getDataById(idSupplier);

                                if (selectedDataSupplier != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataSupplierToEdit(selectedDataSupplier);
                                    parentView.showFormSupplier();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data Supplier not found!");
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
                // Not implemented
                System.out.println("Detail row: " + row);
            }

            @Override
            public void onDetail(int row) {
                // Not implemented
                System.out.println("Detail row: " + row);
            }

            @Override
            public void onApprove(int row) {
                int selectedRow = dataSupplierTable.getSelectedRow();
                String stringId = dataSupplierTable.getValueAt(selectedRow, 0).toString();
                int id = Integer.parseInt(stringId.replaceAll("[^0-9]", ""));

                System.out.println("ID Supplier : " + id);

                parentView.showPopUpEditStatusSupplier(id);
            }
        };

        dataSupplierTable = new tableActionButton(loadDataSupplier.getAllDataSupplier(), actionButton);

        int actionColumnIndex = 8;
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataSupplier = new scrollTable(dataSupplierTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollDataSupplier);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataSupplier();

    }

    private void loadPendingSupplier() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView.showConfrimPopUp("do you want to delete product data?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataSupplierTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdSupplier = dataSupplierTable.getValueAt(selectedRow, 0).toString();
                                int idSupplier = Integer.parseInt(stringIdSupplier.replaceAll("[^0-9]", ""));

                                dataSupplier selectedDataSupplier = loadDataSupplier.getDataById(idSupplier);

                                if (selectedDataSupplier != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataSupplierToEdit(selectedDataSupplier);
                                    parentView.showFormSupplier();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data Supplier not found!");
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
                // Not implemented
                System.out.println("Detail row: " + row);
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

        dataSupplierTable = new tableActionButton(loadDataSupplier.getAllPendingDataSupplier(), actionButton);

        int actionColumnIndex = 8;
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataSupplier = new scrollTable(dataSupplierTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollDataSupplier);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataSupplier();
    }

    private void loadStockSupplier() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView.showConfrimPopUp("do you want to delete product data?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataSupplierTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdSupplier = dataSupplierTable.getValueAt(selectedRow, 0).toString();
                                int idSupplier = Integer.parseInt(stringIdSupplier.replaceAll("[^0-9]", ""));

                                dataSupplier selectedDataSupplier = loadDataSupplier.getDataById(idSupplier);

                                if (selectedDataSupplier != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataSupplierToEdit(selectedDataSupplier);
                                    parentView.showFormSupplier();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data Supplier not found!");
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
                // Not implemented
                System.out.println("Detail row: " + row);
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

        dataSupplierTable = new tableActionButton(loadDataSupplier.getAllStockDataSupplier(), actionButton);

        int actionColumnIndex = 8;
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataSupplier = new scrollTable(dataSupplierTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollDataSupplier);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataSupplier();

    }

    private void loadOutStockSupplier() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView.showConfrimPopUp("do you want to delete product data?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataSupplierTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdSupplier = dataSupplierTable.getValueAt(selectedRow, 0).toString();
                                int idSupplier = Integer.parseInt(stringIdSupplier.replaceAll("[^0-9]", ""));

                                dataSupplier selectedDataSupplier = loadDataSupplier.getDataById(idSupplier);

                                if (selectedDataSupplier != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataSupplierToEdit(selectedDataSupplier);
                                    parentView.showFormSupplier();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data Supplier not found!");
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
                // Not implemented
                System.out.println("Detail row: " + row);
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

        dataSupplierTable = new tableActionButton(loadDataSupplier.getAllOutStockDataSupplier(), actionButton);

        int actionColumnIndex = 8;
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataSupplier = new scrollTable(dataSupplierTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollDataSupplier);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataSupplier();

    }

    private void loadRejectedSupplier() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                try {
                    popUpConfrim messagePopUp = parentView.showConfrimPopUp("do you want to delete product data?");

                    messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent ae) {
                            int selectedRow = dataSupplierTable.getSelectedRow();
                            if (selectedRow != -1) {
                                String stringIdSupplier = dataSupplierTable.getValueAt(selectedRow, 0).toString();
                                int idSupplier = Integer.parseInt(stringIdSupplier.replaceAll("[^0-9]", ""));

                                dataSupplier selectedDataSupplier = loadDataSupplier.getDataById(idSupplier);

                                if (selectedDataSupplier != null) {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.setDataSupplierToEdit(selectedDataSupplier);
                                    parentView.showFormSupplier();
                                } else {
                                    parentApp.hideGlassNotificationPanel();
                                    parentView.showFailedPopUp("Data Supplier not found!");
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
                // Not implemented
                System.out.println("Detail row: " + row);
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

        dataSupplierTable = new tableActionButton(loadDataSupplier.getAllRejectedDataSupplier(), actionButton);

        int actionColumnIndex = 8;
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataSupplierTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollDataSupplier = new scrollTable(dataSupplierTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollDataSupplier);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataSupplier();

    }

    private void setHeaderTableAllDataSupplier() {
        dataSupplierTable.getColumnModel().getColumn(0).setMinWidth(80);
        dataSupplierTable.getColumnModel().getColumn(0).setMaxWidth(80);
        dataSupplierTable.getColumnModel().getColumn(0).setWidth(80);

        dataSupplierTable.getColumnModel().getColumn(3).setMinWidth(100);
        dataSupplierTable.getColumnModel().getColumn(3).setMaxWidth(100);
        dataSupplierTable.getColumnModel().getColumn(3).setWidth(100);

        dataSupplierTable.getColumnModel().getColumn(4).setMinWidth(90);
        dataSupplierTable.getColumnModel().getColumn(4).setMaxWidth(90);
        dataSupplierTable.getColumnModel().getColumn(4).setWidth(90);

        dataSupplierTable.getColumnModel().getColumn(7).setMinWidth(0);
        dataSupplierTable.getColumnModel().getColumn(7).setMaxWidth(0);
        dataSupplierTable.getColumnModel().getColumn(7).setWidth(0);
    }

    public void filterDataByKeyword(String keyword) {
        DefaultTableModel model = (DefaultTableModel) dataSupplierTable.getModel();
        model.setRowCount(0);

        ArrayList<dataSearchSupplier> supplierList = new ArrayList<>();

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean filterByStatus = currentStatus != null &&
                (currentStatus.equalsIgnoreCase("Pending") ||
                        currentStatus.equalsIgnoreCase("Ready") ||
                        currentStatus.equalsIgnoreCase("Out of Stock") ||
                        currentStatus.equalsIgnoreCase("Rejected"));

        if (hasKeyword) {
            // Ada keyword dan status
            if (filterByStatus) {
                supplierList = authDataSupplier.searchSupplierByKeywordAndStatus(keyword, currentStatus);
            } else {
                supplierList = authDataSupplier.searchSupplierByKeyword(keyword);
            }
        } else {
            // Tidak ada keyword
            if (filterByStatus) {
                supplierList = loadDataSupplier.getAllSupplierByStatus(currentStatus);
            } else {
                supplierList = loadDataSupplier.getAllSupplier();
            }
        }

        for (dataSearchSupplier dataSupplier : supplierList) {
            model.addRow(new Object[] {
                    "NS00" + dataSupplier.getIdSupplier(),
                    dataSupplier.getNameStaff(),
                    dataSupplier.getNameSupplier(),
                    dataSupplier.getQuantity(),
                    dataSupplier.getUnit(),
                    dataSupplier.getStatus(),
                    dataSupplier.getDate(),
                    "Aksi"
            });
        }
    }

}
