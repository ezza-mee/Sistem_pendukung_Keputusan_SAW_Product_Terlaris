package com.main.views.dashboardAdmin.table;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.models.entity.dataTable;
import com.main.routes.dashboardAdminView;
import com.main.services.authDataTable;

public class tableFormView extends contentPanel {

    private dashboardAdminView parentView;

    private textLabel headerLabel, numberLabel, capacityLabel, descriptionLabel;

    private textLabel numberEmptyLabel, capacityEmptyLabel, descriptionEmptyLabel;

    private textField numberField, capacityField;

    private textArea descriptionField;

    private panelRounded contentPanel;

    private buttonCustom buttonBack, buttonReset, buttonSave;

    private scrollPane scrollDescription;

    private authDataTable authData = new authDataTable();

    private int tableIdToEdit = -1;

    public tableFormView(dashboardAdminView parentView) {
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

        contentPanel.add(numberLabel);
        contentPanel.add(capacityLabel);
        contentPanel.add(descriptionLabel);

        contentPanel.add(numberField);
        contentPanel.add(capacityField);
        contentPanel.add(scrollDescription);

        contentPanel.add(buttonBack);
        contentPanel.add(buttonReset);
        contentPanel.add(buttonSave);

        add(headerLabel);
        add(contentPanel);

        setVisible(true);
    }

    private void setPosition() {
        contentPanel = new panelRounded(40, 80, 1050, 550, 10, 10);

        headerLabel = new textLabel("Input Data Table", 40, 0, 300, 80);

        numberLabel = new textLabel("Number Table", 180, 30, 300, 80);
        capacityLabel = new textLabel("Capacity", 180, 130, 300, 80);
        descriptionLabel = new textLabel("Description", 180, 230, 300, 80);

        numberEmptyLabel = new textLabel("Number Table cannot be empty", 180, 90, 300, 80);
        capacityEmptyLabel = new textLabel("Capacity cannot be empty", 180, 190, 300, 80);
        descriptionEmptyLabel = new textLabel("Description cannot be empty", 180, 400, 300, 80);

        numberField = new textField(180, 85, 700, 10);
        capacityField = new textField(180, 185, 700, 10);
        descriptionField = new textArea(180, 285, 700, 140, 10);

        numberField.setPlaceholder("Enter Number Table");
        capacityField.setPlaceholder("Enter Capacity Table");
        descriptionField.setPlaceholder("Enter Description Table");

        buttonBack = new buttonCustom("Back", 180, 470, 100, 40, 10);
        buttonReset = new buttonCustom("Reset", 640, 470, 100, 40, 10);
        buttonSave = new buttonCustom("Save", 780, 470, 100, 40, 10);

        scrollDescription = new scrollPane(descriptionField, 180, 285, 700, 140);
    }

    private void setColor() {
        contentPanel.setBackground(color.WHITE);
        headerLabel.setForeground(color.BLACK);

        numberLabel.setForeground(color.BLACK);
        capacityLabel.setForeground(color.BLACK);
        descriptionLabel.setForeground(color.BLACK);

        numberEmptyLabel.setForeground(color.RED);
        capacityEmptyLabel.setForeground(color.RED);
        descriptionEmptyLabel.setForeground(color.RED);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));

        numberLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        capacityLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        descriptionLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));

        numberEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        capacityEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        descriptionEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
    }

    public void setFormTable(dataTable dataTable) {
        numberField.setText(dataTable.getNumber());
        capacityField.setText(dataTable.getCapacity());
        descriptionField.setText(dataTable.getDescription());

        tableIdToEdit = dataTable.getIdtable();
    }

    private void handleButton() {
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showDashboardTable();
            }
        });

        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                numberField.setText(null);
                capacityField.setText(null);
                descriptionField.setText(null);
            }
        });

        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {

                    String number = numberField.getText().trim();
                    String capacity = capacityField.getText().trim();
                    String description = descriptionField.getText().trim();

                    contentPanel.remove(numberEmptyLabel);
                    contentPanel.remove(capacityEmptyLabel);
                    contentPanel.remove(descriptionEmptyLabel);

                    String validation = authData.validateTableInput(number, capacity, description);

                    switch (validation) {
                        case "ALL_FIELDS_EMPTY":
                            contentPanel.add(numberEmptyLabel);
                            contentPanel.add(capacityEmptyLabel);
                            contentPanel.add(descriptionEmptyLabel);
                            break;
                        case "NUMBER_EMPTY":
                            contentPanel.add(numberEmptyLabel);
                            break;
                        case "CAPACITY_EMPTY":
                            contentPanel.add(capacityEmptyLabel);
                            break;
                        case "DESCRIPTION_EMPTY":
                            contentPanel.add(descriptionEmptyLabel);
                            break;
                        case "VALID":
                            boolean success = false;
                            if (tableIdToEdit == -1) {
                                success = authDataTable.insertDataTable(number, capacity, description);
                                if (success) {
                                    parentView.showDashboardTable();
                                    parentView.showSuccessPopUp("Data Table Successfully Saved");
                                } else {
                                    parentView.showFailedPopUp("Failed to Save Data Table");
                                }
                            } else {
                                success = authDataTable.updateDataTable(tableIdToEdit, number,
                                        capacity, description);
                                if (success) {
                                    parentView.showDashboardTable();
                                    parentView.showSuccessPopUp("Data Table Successfully Update");
                                } else {
                                    parentView.showFailedPopUp("Failed to Update Data Table");
                                }
                            }
                            break;
                    }
                    contentPanel.revalidate();
                    contentPanel.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
