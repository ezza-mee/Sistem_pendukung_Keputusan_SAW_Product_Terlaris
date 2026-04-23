package com.main.views.dashboardAdmin.convertion;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.routes.dashboardAdminView;
import com.main.services.authDataConvertion;
import com.main.models.entity.dataConvertion;

public class convertionFormView extends contentPanel {

    private dashboardAdminView parentView;

    private panelRounded contentPanel;

    private textLabel formUnitLabel, toUnitLabel, multiplierLabel, descriptionLabel;

    private textLabel formUnitEmptyLabel, toUnitEmptyLabel, multiplierEmptyLabel, descriptionEmptyLabel;

    private comboBox<String> formUnitField, toUnitField;

    private textField multiplierField;

    private textArea descriptionField;

    private scrollPane scrollDescription;

    private buttonCustom buttonBack, buttonReset, buttonSave;

    private authDataConvertion authData = new authDataConvertion();

    private int convertionIdToEdit = -1;

    private appIcons appIcons = new appIcons();
    private imageIcon backIcon = appIcons.getBackIconWhite(20, 20);
    private imageIcon resetIcon = appIcons.getDeleteIconWhite(20, 20);
    private imageIcon saveIcon = appIcons.getSaveIconWhite(20, 20);

    public convertionFormView(dashboardAdminView parentView) {
        super();
        this.parentView = parentView;

        initContent();
    }

    @Override
    public void initContent() {
        setComponent();
        setColor();
        setFont();
        handleButton();

        contentPanel.add(formUnitLabel);
        contentPanel.add(toUnitLabel);
        contentPanel.add(multiplierLabel);
        contentPanel.add(descriptionLabel);

        contentPanel.add(formUnitField);
        contentPanel.add(toUnitField);
        contentPanel.add(multiplierField);
        contentPanel.add(scrollDescription);

        contentPanel.add(buttonBack);
        contentPanel.add(buttonReset);
        contentPanel.add(buttonSave);

        add(contentPanel);

        setVisible(true);
    }

    private void setComponent() {
        contentPanel = new panelRounded(40, 80, 1050, 550, 10, 10);

        formUnitLabel = new textLabel("unit", 180, 20, 300, 80);
        toUnitLabel = new textLabel("Konversi unit", 180, 100, 300, 80);
        multiplierLabel = new textLabel("Ukuran unit", 180, 180, 300, 80);
        descriptionLabel = new textLabel("Deskripsi", 180, 260, 300, 80);

        formUnitEmptyLabel = new textLabel("Unit tidak boleh kosong!", 180, 70, 300, 80);
        toUnitEmptyLabel = new textLabel("Konversi unit tidak boleh kosong!", 180, 150, 300, 80);
        multiplierEmptyLabel = new textLabel("Ukuran unit tidak boleh kosong!", 180, 230, 300, 80);
        descriptionEmptyLabel = new textLabel("Deskripsi unit tidak boleh kosong!", 180, 380, 300, 80);

        String[] formUnitItems = { null, "Pcs", "Box", "Kg", "Liter", "Pack", "Dus", "Botol" };
        formUnitField = new comboBox<>(formUnitItems, 180, 70, 700, 30, 10);
        formUnitField.setPlaceholder("Select Form Unit");

        String[] toUnitItems = { null, "Gram", "Kilogram", "Ons", "Mililiter", "Liter",
                "Sendok Makan", "Sendok Teh", "Gelas", "Botol",
                "Kaleng", "Buah", "Kemasan", "Sachet", "Lembaran",
                "Batangan", "Biji-bijian", "Cangkir", "Irisan", "Kotak", "Kemasan", "Tetesan" };
        toUnitField = new comboBox<>(toUnitItems, 180, 150, 700, 30, 10);
        toUnitField.setPlaceholder("Pilih konversi unit");

        multiplierField = new textField(180, 230, 700, 10);

        descriptionField = new textArea(180, 300, 700, 100, 10);

        buttonBack = new buttonCustom("    " + "Kembali", 180, 470, 130, 40, 10);
        buttonReset = new buttonCustom("    " + "Hapus", 620, 470, 130, 40, 10);
        buttonSave = new buttonCustom("    " + "Simpan", 780, 470, 130, 40, 10);

        buttonBack.setIcon(backIcon);
        buttonReset.setIcon(resetIcon);
        buttonSave.setIcon(saveIcon);

        scrollDescription = new scrollPane(descriptionField, 180, 310, 700, 100);
    }

    private void setColor() {
        contentPanel.setBackground(color.WHITE);

        formUnitLabel.setForeground(color.BLACK);
        toUnitLabel.setForeground(color.BLACK);
        multiplierLabel.setForeground(color.BLACK);
        descriptionLabel.setForeground(color.BLACK);

        formUnitEmptyLabel.setForeground(color.RED);
        toUnitEmptyLabel.setForeground(color.RED);
        multiplierEmptyLabel.setForeground(color.RED);
        descriptionEmptyLabel.setForeground(color.RED);
    }

    private void setFont() {
        formUnitLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        toUnitLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        multiplierLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        descriptionLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));

    }

    public void setFormConvertion(dataConvertion dataConvertion) {
        formUnitField.setSelectedItem(dataConvertion.getFormUnit());
        toUnitField.setSelectedItem(dataConvertion.getToUnit());
        multiplierField.setText(String.valueOf(dataConvertion.getMultiplier()));
        descriptionField.setText(dataConvertion.getDescription());

        convertionIdToEdit = dataConvertion.getIdConvertion();
    }

    private void handleButton() {
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showDashboardConvertion();
            }
        });

        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                formUnitField.setSelectedItem(null);
                toUnitField.setSelectedItem(null);
                multiplierField.setText(null);
                descriptionField.setText(null);
            }
        });

        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    String formUnit = (String) formUnitField.getSelectedItem();
                    String toUnit = (String) toUnitField.getSelectedItem();
                    String stringMultiplier = multiplierField.getText().trim();
                    String description = descriptionField.getText().trim();

                    contentPanel.remove(formUnitEmptyLabel);
                    contentPanel.remove(toUnitEmptyLabel);
                    contentPanel.remove(multiplierEmptyLabel);
                    contentPanel.remove(descriptionEmptyLabel);

                    String validation = authData.validateConvertionInput(formUnit, toUnit, stringMultiplier,
                            description);

                    switch (validation) {
                        case "ALL_FIELDS_EMPTY":
                            contentPanel.add(formUnitEmptyLabel);
                            contentPanel.add(toUnitEmptyLabel);
                            contentPanel.add(multiplierEmptyLabel);
                            contentPanel.add(descriptionEmptyLabel);
                            break;
                        case "FORM_UNIT_EMPTY":
                            contentPanel.add(formUnitEmptyLabel);
                            break;
                        case "TO_UNIT_EMPTY":
                            contentPanel.add(toUnitEmptyLabel);
                            break;
                        case "MULTIPLIER_EMPTY":
                            contentPanel.add(multiplierEmptyLabel);
                            break;
                        case "DESCRIPTION_EMPTY":
                            contentPanel.add(descriptionEmptyLabel);
                            break;
                        case "VALID":
                            boolean success = false;
                            double multiplier = Double.parseDouble(stringMultiplier);

                            if (convertionIdToEdit == -1) {
                                success = authDataConvertion.insertDataConvertion(convertionIdToEdit, formUnit, toUnit,
                                        multiplier, description);
                                if (success) {
                                    parentView.showDashboardConvertion();
                                    parentView.showSuccessPopUp("Data konversi berhasil disimpan");
                                } else {
                                    parentView.showFailedPopUp("Data konversi gagal disimpan!");
                                }
                            } else {
                                success = authDataConvertion.updateDataConvertion(convertionIdToEdit, formUnit, toUnit,
                                        multiplier, description);
                                if (success) {
                                    parentView.showDashboardConvertion();
                                    parentView.showSuccessPopUp("Data konversi berhasil diubah");
                                } else {
                                    parentView.showFailedPopUp("Data konversi gagal diubah!");
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
