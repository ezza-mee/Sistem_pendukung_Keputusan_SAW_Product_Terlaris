package com.main.views.popUp.popUpEditStatusTable;

import com.main.components.panelApps.popUpPanel;
import com.main.routes.dashboardStaffView;
import com.main.routes.mainFrame;
import com.main.services.authDataTable;

import javax.swing.JLabel;

import com.main.components.*;

public class popUpEditStatusTable extends popUpPanel {

    private mainFrame parentApp;
    private dashboardStaffView parentView;

    private textLabel headerLabel;

    private comboBox<String> statusField;

    private buttonCustom buttonCancel, buttonConfrim;

    private int idTable;

    private appIcons appIcons = new appIcons();
    private imageIcon backIcon = appIcons.getBackIconWhite(20, 20);
    private imageIcon saveIcon = appIcons.getSaveIconWhite(20, 20);

    public popUpEditStatusTable(mainFrame parentApp, dashboardStaffView parentView, int idTable) {
        super();
        this.parentApp = parentApp;
        this.parentView = parentView;
        this.idTable = idTable;
        setSize(400, 260);
        initComponent();
    }

    private void initComponent() {
        setLayout();
        setFont();
        setColor();
        setAction();

        add(headerLabel);
        add(statusField);
        add(buttonCancel);
        add(buttonConfrim);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Edit Status Table", 0, 0, 400, 80);

        String[] statusFields = { null, "Available", "Cleaning", "Out of Order", "Reserved" };
        statusField = new comboBox<>(statusFields, 40, 100, 320, 30, 10);
        statusField.setPlaceholder("Pilih Status Data Meja");

        buttonCancel = new buttonCustom("    " + "Batal", 40, 190, 140, 40, 10);
        buttonConfrim = new buttonCustom("    " + "Simpan", 220, 190, 140, 40, 10);

        buttonCancel.setIcon(backIcon);
        buttonConfrim.setIcon(saveIcon);

    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));

        headerLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
    }

    private void setAction() {
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentApp.hideGlassFormPanel();
            }
        });

        buttonConfrim.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                String selectedStatus = (String) statusField.getSelectedItem();

                if (selectedStatus == null || selectedStatus.trim().isEmpty()) {
                    parentView.showFailedPopUp("Pilih status meja");
                    return;
                }

                boolean success = authDataTable.updateStatusTable(idTable, selectedStatus);
                if (success) {
                    parentView.showSuccessPopUp("Status update data meja!");
                    parentApp.hideGlassFormPanel();
                    parentView.showDashboardTable();
                } else {
                    parentView.showFailedPopUp("gagal update data meja.");
                }
            }
        });
    }

}
