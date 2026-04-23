package com.main.views.dashboardStaff.table;

import java.util.ArrayList;
import java.util.EnumSet;

import javax.swing.table.DefaultTableModel;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.controller.actionButtonTable;
import com.main.controller.searchableView;
import com.main.models.entity.dataSearchTable;
import com.main.models.table.loadDataTable;
import com.main.routes.dashboardStaffView;
import com.main.routes.mainFrame;
import com.main.services.authDataTable;

public class tableDashboardView extends contentPanel implements searchableView {

    private mainFrame parentApp;
    private dashboardStaffView parentView;
    private panelRounded headerPanel, contentPanel;
    private textLabel headerLabel;
    private linkLabel allDataTableLabel, cleaningTableLabel, availableTableLabel, reservedTableLabel,
            outOfOrderTableLabel;
    private tableActionButton dataTable;
    private scrollTable scrollTable;
    private int quantityAllDataTable = loadDataTable.getAllQuantityDataTable();
    private int quantityAllDataCleaningTable = loadDataTable.getAllQuantityDataCleaningTable();
    private int quantityAllDataAvailableTable = loadDataTable.getAllQuantityDataAvailableTable();
    private int quantityAllDataReservedTable = loadDataTable.getAllQuantityDataReservedTable();
    private int quantityAllDataOutOfOrderTable = loadDataTable.getAllQuantityDataOutOfOrderTable();

    private EnumSet<buttonType> buttonTypes = EnumSet.of(buttonType.EDIT);

    private String currentStatus = "ALL";

    private void setColor() {
        headerPanel.setBackground(color.WHITE);
        contentPanel.setBackground(color.WHITE);

        headerLabel.setForeground(color.BLACK);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));
        allDataTableLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        cleaningTableLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        availableTableLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        reservedTableLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        outOfOrderTableLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
    }

    public tableDashboardView(mainFrame parentApp, dashboardStaffView parentView) {
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
        showAllTable();

        headerPanel.add(allDataTableLabel);
        headerPanel.add(cleaningTableLabel);
        headerPanel.add(availableTableLabel);
        headerPanel.add(reservedTableLabel);
        headerPanel.add(outOfOrderTableLabel);

        contentPanel.add(scrollTable);

        add(headerLabel);
        add(headerPanel);
        add(contentPanel);

        setVisible(true);
    }

    private void setLayout() {
        headerPanel = new panelRounded(40, 80, 1050, 110, 10, 10);
        contentPanel = new panelRounded(40, 220, 1050, 410, 10, 10);

        headerLabel = new textLabel("Data Table", 40, 0, 200, 80);

        allDataTableLabel = new linkLabel("ALL", 40, 40, 80, 30);
        allDataTableLabel.setQuantity(quantityAllDataTable);
        cleaningTableLabel = new linkLabel("Cleaning", 155, 40, 120, 30);
        cleaningTableLabel.setQuantity(quantityAllDataCleaningTable);
        availableTableLabel = new linkLabel("Available", 310, 40, 120, 30);
        availableTableLabel.setQuantity(quantityAllDataAvailableTable);
        reservedTableLabel = new linkLabel("Reserved", 470, 40, 120, 30);
        reservedTableLabel.setQuantity(quantityAllDataReservedTable);
        outOfOrderTableLabel = new linkLabel("Out of Order", 630, 40, 140, 30);
        outOfOrderTableLabel.setQuantity(quantityAllDataOutOfOrderTable);

    }

    private void setAction() {
        allDataTableLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showAllTable();
            }
        });

        availableTableLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showAvailableTable();
            }
        });

        cleaningTableLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showCleaningTable();
            }
        });

        reservedTableLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showReservedTable();
            }
        });

        outOfOrderTableLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showOutOfOrderTable();
            }
        });

    }

    private void resetLinkLabel() {
        allDataTableLabel.setForeground(color.DARKGREY);
        allDataTableLabel.setLabelInActive();

        cleaningTableLabel.setForeground(color.DARKGREY);
        cleaningTableLabel.setLabelInActive();

        availableTableLabel.setForeground(color.DARKGREY);
        availableTableLabel.setLabelInActive();

        reservedTableLabel.setForeground(color.DARKGREEN);
        reservedTableLabel.setLabelInActive();

        outOfOrderTableLabel.setForeground(color.DARKGREEN);
        outOfOrderTableLabel.setLabelInActive();
    }

    private void showAllTable() {
        resetLinkLabel();
        allDataTableLabel.setForeground(color.DARKGREEN);
        allDataTableLabel.setLabelActive();
        loadAllTable();

    }

    private void showCleaningTable() {
        resetLinkLabel();
        cleaningTableLabel.setForeground(color.DARKGREEN);
        cleaningTableLabel.setLabelActive();
        loadAllCleaningTable();

    }

    private void showReservedTable() {
        resetLinkLabel();
        reservedTableLabel.setForeground(color.DARKGREEN);
        reservedTableLabel.setLabelActive();
        loadAllReservedTable();

    }

    private void showAvailableTable() {
        resetLinkLabel();
        availableTableLabel.setForeground(color.DARKGREEN);
        availableTableLabel.setLabelActive();
        loadAllAvailableTable();

    }

    private void showOutOfOrderTable() {
        resetLinkLabel();
        outOfOrderTableLabel.setForeground(color.DARKGREEN);
        outOfOrderTableLabel.setLabelActive();
        loadAllOutOfOrderTable();

    }

    private void loadAllTable() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                int selectedRow = dataTable.getSelectedRow();
                String stringId = dataTable.getValueAt(selectedRow, 0).toString();
                int id = Integer.parseInt(stringId.replaceAll("[^0-9]", ""));

                System.out.println("ID Table : " + id);

                parentView.showPopUpEditStatusTable(id);
            }

            @Override
            public void onDelete(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onDetail(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataTable = new tableActionButton(loadDataTable.getAllDataTable(), actionButton);

        int actionColumnIndex = 6;
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollTable = new scrollTable(dataTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollTable);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataTable();
    }

    private void loadAllAvailableTable() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                int selectedRow = dataTable.getSelectedRow();
                String stringId = dataTable.getValueAt(selectedRow, 0).toString();
                int id = Integer.parseInt(stringId.replaceAll("[^0-9]", ""));

                System.out.println("ID Table : " + id);

                parentView.showPopUpEditStatusTable(id);
            }

            @Override
            public void onDelete(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onDetail(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataTable = new tableActionButton(loadDataTable.getAllDataAvailableTable(), actionButton);

        int actionColumnIndex = 6;
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollTable = new scrollTable(dataTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollTable);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataTable();
    }

    private void loadAllCleaningTable() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                int selectedRow = dataTable.getSelectedRow();
                String stringId = dataTable.getValueAt(selectedRow, 0).toString();
                int id = Integer.parseInt(stringId.replaceAll("[^0-9]", ""));

                System.out.println("ID Table : " + id);

                parentView.showPopUpEditStatusTable(id);
            }

            @Override
            public void onDelete(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onDetail(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataTable = new tableActionButton(loadDataTable.getAllDataCleaningTable(), actionButton);

        int actionColumnIndex = 6;
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollTable = new scrollTable(dataTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollTable);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataTable();
    }

    private void loadAllOutOfOrderTable() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                int selectedRow = dataTable.getSelectedRow();
                String stringId = dataTable.getValueAt(selectedRow, 0).toString();
                int id = Integer.parseInt(stringId.replaceAll("[^0-9]", ""));

                System.out.println("ID Table : " + id);

                parentView.showPopUpEditStatusTable(id);
            }

            @Override
            public void onDelete(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onDetail(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataTable = new tableActionButton(loadDataTable.getAllDataOutOfOrderTable(), actionButton);

        int actionColumnIndex = 6;
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollTable = new scrollTable(dataTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollTable);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataTable();
    }

    private void loadAllReservedTable() {
        actionButtonTable actionButton = new actionButtonTable() {
            @Override
            public void onEdit(int row) {
                int selectedRow = dataTable.getSelectedRow();
                String stringId = dataTable.getValueAt(selectedRow, 0).toString();
                int id = Integer.parseInt(stringId.replaceAll("[^0-9]", ""));

                System.out.println("ID Table : " + id);

                parentView.showPopUpEditStatusTable(id);
            }

            @Override
            public void onDelete(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onDetail(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }

            @Override
            public void onApprove(int row) {
                // Not implemented
                System.out.println("Approve row: " + row);
            }
        };

        dataTable = new tableActionButton(loadDataTable.getAllDataReservedTable(), actionButton);

        int actionColumnIndex = 6;
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellRenderer(new buttonTableRenderer(buttonTypes));
        dataTable.getColumnModel().getColumn(actionColumnIndex)
                .setCellEditor(new buttonTableEditor(actionButton, buttonTypes));

        scrollTable = new scrollTable(dataTable, 0, 0, 1050, 410);

        contentPanel.removeAll();
        contentPanel.add(scrollTable);
        contentPanel.revalidate();
        contentPanel.repaint();

        setHeaderTableAllDataTable();
    }

    private void setHeaderTableAllDataTable() {
        dataTable.getColumnModel().getColumn(0).setMinWidth(80);
        dataTable.getColumnModel().getColumn(0).setMaxWidth(80);
        dataTable.getColumnModel().getColumn(0).setWidth(80);

        dataTable.getColumnModel().getColumn(1).setMinWidth(150);
        dataTable.getColumnModel().getColumn(1).setMaxWidth(150);
        dataTable.getColumnModel().getColumn(1).setWidth(150);

        dataTable.getColumnModel().getColumn(2).setMinWidth(100);
        dataTable.getColumnModel().getColumn(2).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(2).setWidth(100);

        dataTable.getColumnModel().getColumn(3).setMinWidth(100);
        dataTable.getColumnModel().getColumn(3).setMaxWidth(100);
        dataTable.getColumnModel().getColumn(3).setWidth(100);

        dataTable.getColumnModel().getColumn(5).setMinWidth(90);
        dataTable.getColumnModel().getColumn(5).setMaxWidth(90);
        dataTable.getColumnModel().getColumn(5).setWidth(90);

        dataTable.getColumnModel().getColumn(6).setMinWidth(180);
        dataTable.getColumnModel().getColumn(6).setMaxWidth(180);
        dataTable.getColumnModel().getColumn(6).setWidth(180);
    }

    public void filterDataByKeyword(String keyword) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        ArrayList<dataSearchTable> SearchList = new ArrayList<>();

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean filterByStatus = currentStatus != null &&
                (currentStatus.equalsIgnoreCase("Active") ||
                        currentStatus.equalsIgnoreCase("Inactive") ||
                        currentStatus.equalsIgnoreCase("Resign"));

        if (hasKeyword) {
            // Ada keyword dan status
            if (filterByStatus) {
                SearchList = authDataTable.searchTableByKeywordAndStatus(keyword, currentStatus);
            } else {
                SearchList = authDataTable.searchTableByKeyword(keyword);
            }
        } else {
            // Tidak ada keyword
            if (filterByStatus) {
                SearchList = loadDataTable.getAllTableByStatus(currentStatus);
            } else {
                SearchList = loadDataTable.getAllTable();
            }
        }

        for (dataSearchTable dataTable : SearchList) {
            model.addRow(new Object[] {
                    "THB00" + dataTable.getIdtable(),
                    dataTable.getNumber(),
                    dataTable.getCapacity(),
                    dataTable.getDescription(),
                    dataTable.getDescription(),
                    dataTable.getStatus(),
                    "Aksi"
            });
        }
    }
}
