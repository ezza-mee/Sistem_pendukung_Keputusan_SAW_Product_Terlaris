package com.main.views.dashboardStaff.supplier;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.models.entity.dataSupplier;
import com.main.routes.dashboardStaffView;
import com.main.services.authDataSupplier;
import com.main.auth.sessionLogin;

public class supplierFormView extends contentPanel {

    private dashboardStaffView parentView;

    private panelRounded contentPanel;

    private textLabel headerLabel;

    private textLabel nameLabel, quantityLabel, unitLabel, descriptionLabel;

    private textLabel nameEmptyLabel, quantityEmptyLabel, unitEmptyLabel, descriptionEmptyLabel;

    private textField nameField, quantityField;

    private comboBox<String> unitField;

    private textArea descriptionField;

    private buttonCustom buttonBack, buttonReset, buttonSave;

    private scrollPane scrollDescription;

    private authDataSupplier authData = new authDataSupplier();

    private int supplierIdToEdit = -1;

    int idStaff = sessionLogin.get().getIdStaff();

    public supplierFormView(dashboardStaffView parentView) {
        super();
        this.parentView = parentView;
        initContent();
    }

    @Override
    public void initContent() {
        setPosition();
        setColor();
        setFont();
        handleButton();

        contentPanel.add(nameLabel);
        contentPanel.add(quantityLabel);
        contentPanel.add(unitLabel);
        contentPanel.add(descriptionLabel);

        contentPanel.add(nameField);
        contentPanel.add(quantityField);
        contentPanel.add(unitField);
        contentPanel.add(scrollDescription);

        contentPanel.add(buttonBack);
        contentPanel.add(buttonReset);
        contentPanel.add(buttonSave);

        add(headerLabel);
        add(contentPanel);

        setVisible(true);

    }

    private void setPosition() {
        headerLabel = new textLabel("Input Data Supplier", 40, 0, 400, 80);
        contentPanel = new panelRounded(40, 80, 1050, 550, 10, 10);

        nameLabel = new textLabel("Supplier", 180, 30, 300, 80);
        quantityLabel = new textLabel("quantity", 180, 130, 300, 80);
        unitLabel = new textLabel("Unit", 580, 130, 300, 80);
        descriptionLabel = new textLabel("Description", 180, 230, 300, 80);

        nameEmptyLabel = new textLabel("Supplier Name cannot be empty", 180, 90, 300, 80);
        quantityEmptyLabel = new textLabel("Quantity cannot be empty", 180, 190, 300, 80);
        unitEmptyLabel = new textLabel("Unit cannot be empty", 580, 190, 300, 80);
        descriptionEmptyLabel = new textLabel("Description cannot be empty", 180, 400, 300, 80);

        nameField = new textField(180, 85, 700, 10);
        quantityField = new textField(180, 185, 300, 10);

        String[] unitItems = { null, "Pcs", "Box", "Kg", "Liter", "Pack", "Dus", "Botol" };
        unitField = new comboBox<>(unitItems, 580, 185, 300, 30, 10);
        unitField.setPlaceholder("Select your Unit");

        descriptionField = new textArea(180, 285, 700, 140, 10);

        nameField.setPlaceholder("Enter Supplier Name");
        quantityField.setPlaceholder("Enter quantity Supplier");
        descriptionField.setPlaceholder("Enter Description Supplier");

        buttonBack = new buttonCustom("Back", 180, 470, 100, 40, 10);
        buttonReset = new buttonCustom("Reset", 640, 470, 100, 40, 10);
        buttonSave = new buttonCustom("Save", 780, 470, 100, 40, 10);

        scrollDescription = new scrollPane(descriptionField, 180, 285, 700, 140);

    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        contentPanel.setBackground(color.WHITE);

        nameLabel.setForeground(color.BLACK);
        quantityLabel.setForeground(color.BLACK);
        unitLabel.setForeground(color.BLACK);
        descriptionLabel.setForeground(color.BLACK);

        nameEmptyLabel.setForeground(color.RED);
        quantityEmptyLabel.setForeground(color.RED);
        unitEmptyLabel.setForeground(color.RED);
        descriptionEmptyLabel.setForeground(color.RED);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));

        nameLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        quantityLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        unitLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        descriptionLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));

        nameEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        quantityEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        unitEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        descriptionEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
    }

    public void setFormSupplier(dataSupplier dataSupplier) {
        nameField.setText(dataSupplier.getNameSupplier());
        quantityField.setText(String.valueOf(dataSupplier.getQuantity()));
        unitField.setSelectedItem(dataSupplier.getUnit());
        descriptionField.setText(dataSupplier.getDescription());

        supplierIdToEdit = dataSupplier.getIdSupplier();
    }

    private void handleButton() {
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                parentView.showDashboardSupplier();
                System.err.println("idStaff : " + idStaff);
            }
        });

        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                nameField.setText(null);
                quantityField.setText(null);
                unitField.setSelectedItem(null);
                descriptionField.setText(null);
            }
        });

        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    String nameSupplier = nameField.getText().trim();
                    String stringQuantity = quantityField.getText().trim();
                    String unit = (String) unitField.getSelectedItem();
                    String description = descriptionField.getText().trim();

                    // Bersihkan semua label error terlebih dahulu
                    contentPanel.remove(nameEmptyLabel);
                    contentPanel.remove(quantityEmptyLabel);
                    contentPanel.remove(unitEmptyLabel);
                    contentPanel.remove(descriptionEmptyLabel);

                    // Validasi input
                    String validation = authData.validateSupplierInput(nameSupplier, stringQuantity, unit, description);

                    switch (validation) {
                        case "ALL_FIELDS_EMPTY":
                            contentPanel.add(nameEmptyLabel);
                            contentPanel.add(quantityEmptyLabel);
                            contentPanel.add(unitEmptyLabel);
                            contentPanel.add(descriptionEmptyLabel);
                            break;

                        case "NAME_SUPPLIER_EMPTY":
                            contentPanel.add(nameEmptyLabel);
                            break;

                        case "QUANTITY_EMPTY":
                            contentPanel.add(quantityEmptyLabel);
                            break;

                        case "UNIT_EMPTY":
                            contentPanel.add(unitEmptyLabel);
                            break;

                        case "DESCRIPTION_EMPTY":
                            contentPanel.add(descriptionEmptyLabel);
                            break;

                        case "VALID":
                            boolean success = false;
                            double quantity = Double.parseDouble(stringQuantity);
                            if (supplierIdToEdit == -1) {
                                success = authDataSupplier.insertDataSupplier(idStaff, nameSupplier, quantity, unit,
                                        description);
                                if (success) {
                                    parentView.showDashboardSupplier();
                                    parentView.showSuccessPopUp("Data Supplier Successfully Saved");
                                } else {
                                    parentView.showFailedPopUp("Failed to Save Data Supplier");
                                }
                            } else {
                                success = authDataSupplier.updateDataSupplier(supplierIdToEdit, idStaff, nameSupplier,
                                        quantity,
                                        unit, description);
                                if (success) {
                                    parentView.showDashboardSupplier();
                                    parentView.showSuccessPopUp("Data Supplier Successfully Update");
                                } else {
                                    parentView.showFailedPopUp("Failed to Update Data Supplier");
                                }
                            }
                            break;
                    }

                    contentPanel.revalidate();
                    contentPanel.repaint();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
