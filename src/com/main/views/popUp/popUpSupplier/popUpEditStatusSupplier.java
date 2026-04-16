package com.main.views.popUp.popUpSupplier;

import com.main.components.panelApps.popUpPanel;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;
import com.main.services.authDataSupplier;

import javax.swing.JLabel;

import com.main.components.*;

public class popUpEditStatusSupplier extends popUpPanel {

    private mainFrame parentApp;
    private dashboardAdminView parentView;

    private textLabel headerLabel;

    private comboBox<String> statusField;

    private buttonCustom buttonCancel, buttonConfrim;

    private int idSupplier;

    public popUpEditStatusSupplier(mainFrame parentApp, dashboardAdminView parentView, int idSupplier) {
        super();
        this.parentApp = parentApp;
        this.parentView = parentView;
        this.idSupplier = idSupplier;
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
        headerLabel = new textLabel("Edit Status Supplier", 0, 0, 400, 80);

        String[] statusFields = { null, "Out of Stock", "Processing", "Ready", "Rejected" };
        statusField = new comboBox<>(statusFields, 40, 100, 320, 30, 10);
        statusField.setPlaceholder("Select status");

        buttonCancel = new buttonCustom("Cancel", 40, 190, 140, 40, 10);
        buttonConfrim = new buttonCustom("Save", 220, 190, 140, 40, 10);

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
                    parentView.showFailedPopUp("Please Select a Status");
                    return;
                }

                boolean success = authDataSupplier.updateStatusSupplier(idSupplier, selectedStatus);
                if (success) {
                    parentView.showSuccessPopUp("Status updated successfully!");
                    parentApp.hideGlassFormPanel();
                    parentView.showDashboardSupplier();
                } else {
                    parentView.showFailedPopUp("Failed to update status.");
                }
            }
        });
    }

}
