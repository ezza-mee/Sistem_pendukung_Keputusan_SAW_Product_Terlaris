package com.main.views.popUp.popUpBobotKriteria;

import com.main.components.*;
import com.main.components.panelApps.popUpPanel;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;
import com.main.services.authDataBobotKriteria;
import com.main.models.entity.dataBobotKriteria;

public class popUpInputBobotKriteria extends popUpPanel {

    private mainFrame parentApp;
    private dashboardAdminView parentView;
    private textLabel headerLabel;
    private textLabel kriteriaLabel, weightLabel, typeLabel;
    private textLabel kriteriaEmptyLabel, weightEmptyLabel, typeEmptyLabel;
    private textField kriteriaField, weightField;
    private comboBox<String> typeField;
    private buttonCustom buttonBack, buttonReset, buttonSave;
    private appIcons appIcons = new appIcons();
    private imageIcon backIcon = appIcons.getBackIconWhite(25, 25);
    private authDataBobotKriteria authData = new authDataBobotKriteria();
    private int bobotKriteriaIdToEdit = -1;

    public popUpInputBobotKriteria(mainFrame parentApp, dashboardAdminView parentView) {
        super();
        this.parentApp = parentApp;
        this.parentView = parentView;

        setSize(500, 450);
        initComponent();
    }

    private void initComponent() {
        setLayout();
        setColor();
        setFont();
        handleButton();

        add(kriteriaLabel);
        add(weightLabel);
        add(typeLabel);

        add(kriteriaField);
        add(weightField);
        add(typeField);

        add(buttonBack);
        add(buttonReset);
        add(buttonSave);

        add(headerLabel);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Input Data Nilai Bobot Kriteria", 40, 30, 400, 40);

        kriteriaLabel = new textLabel("Kriteria", 40, 70, 300, 80);
        weightLabel = new textLabel("Weight", 40, 160, 300, 80);

        typeLabel = new textLabel("Type Bobot", 40, 260, 300, 80);

        kriteriaEmptyLabel = new textLabel("Kriteria is Empty", 40, 120, 300, 80);
        weightEmptyLabel = new textLabel("Weight is Empty", 40, 210, 300, 80);

        typeEmptyLabel = new textLabel("Type Bobot is Empty", 40, 310, 300, 80);

        kriteriaField = new textField(40, 120, 420, 10);
        weightField = new textField(40, 210, 420, 10);

        kriteriaField.setPlaceholder("Enter Kriteria");
        weightField.setPlaceholder("Enter Weight Bobot Kriteria");

        String[] typeItems = { null, "Benefit", "Cost" };
        typeField = new comboBox<>(typeItems, 40, 310, 420, 30, 10);
        typeField.setPlaceholder("Select type Bobot ");

        buttonBack = new buttonCustom("Back", 40, 380, 130, 40, 10);
        buttonBack.setIcon(backIcon);
        buttonReset = new buttonCustom("Reset", 230, 380, 100, 40, 10);
        buttonSave = new buttonCustom("Save", 360, 380, 100, 40, 10);

    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);

        kriteriaLabel.setForeground(color.BLACK);
        weightLabel.setForeground(color.BLACK);
        typeLabel.setForeground(color.BLACK);

        kriteriaEmptyLabel.setForeground(color.RED);
        weightEmptyLabel.setForeground(color.RED);
        typeEmptyLabel.setForeground(color.RED);

    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));

        kriteriaLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        weightLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        typeLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));

        kriteriaEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        weightEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        typeEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
    }

    public void setFormBobotKriteria(dataBobotKriteria bobotKriteria) {
        kriteriaField.setText(bobotKriteria.getKriteria());
        weightField.setText(String.valueOf(bobotKriteria.getWeight()));
        typeField.setSelectedItem(bobotKriteria.getType());

        System.out.println("idKriteria : " + bobotKriteria.getIdKriteria());

        bobotKriteriaIdToEdit = bobotKriteria.getIdKriteria();
    }

    private void handleButton() {
        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                kriteriaField.setText(null);
                weightField.setText(null);
                typeField.setSelectedItem(null);
            }
        });

        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentApp.hideGlassNotificationPanel();
            }
        });

        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    String kriteria = kriteriaField.getText().trim();
                    String stringWeight = weightField.getText().trim();
                    String type = (String) typeField.getSelectedItem();

                    // Bersihkan error label
                    remove(kriteriaEmptyLabel);
                    remove(weightEmptyLabel);
                    remove(typeEmptyLabel);

                    // Validasi input kosong
                    String validation = authData.validateBobotKriteriaInput(kriteria, stringWeight, type);
                    switch (validation) {
                        case "ALL_FIELDS_EMPTY":
                            add(kriteriaEmptyLabel);
                            add(weightEmptyLabel);
                            add(typeEmptyLabel);
                            return;
                        case "KRITERIA_EMPTY":
                            add(kriteriaEmptyLabel);
                            return;
                        case "WEIGHT_EMPTY":
                            add(weightEmptyLabel);
                            return;
                        case "TYPE_EMPTY":
                            add(typeEmptyLabel);
                            return;
                        case "VALID":
                            boolean success = false;

                            // Coba parse ke int
                            int weight;
                            try {
                                weight = Integer.parseInt(stringWeight);
                            } catch (NumberFormatException ex) {
                                parentView.showFailedPopUp("Weight must be a number between 1 and 5");
                                return;
                            }

                            // Validasi rentang bobot
                            if (weight < 1 || weight > 5) {
                                parentView.showFailedPopUp("Weight must be between 1 and 5 only");
                                return;
                            }

                            if (bobotKriteriaIdToEdit == -1) {
                                // Insert data baru
                                success = authDataBobotKriteria.insertBobotKriteria(kriteria, weight, type);
                                if (success) {
                                    parentApp.hideGlassFormPanel();
                                    parentView.showDashboardBobotKriteria();
                                    parentView.showSuccessPopUp("Data Weight Kriteria Successfully Saved");
                                } else {
                                    parentView.showFailedPopUp("Failed to Save Data Weight Kriteria");
                                }
                            } else {
                                // Update data lama
                                success = authDataBobotKriteria.updateBobotKriteria(bobotKriteriaIdToEdit, kriteria,
                                        weight, type);
                                if (success) {
                                    parentApp.hideGlassFormPanel();
                                    parentView.showDashboardBobotKriteria();
                                    parentView.showSuccessPopUp("Data Weight Kriteria Successfully Updated");
                                } else {
                                    parentView.showFailedPopUp("Failed to Update Data Weight Kriteria");
                                }
                            }
                            return;
                    }
                    revalidate();
                    repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
