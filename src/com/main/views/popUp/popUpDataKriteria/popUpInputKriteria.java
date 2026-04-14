package com.main.views.popUp.popUpDataKriteria;

import com.main.components.*;
import com.main.components.panelApps.popUpPanel;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;
import com.main.services.authDataKriteria;
import com.main.models.entity.bobotKriteria;
import com.main.models.entity.dataBobotKriteria;

public class popUpInputKriteria extends popUpPanel {

    private mainFrame parentApp;
    private dashboardAdminView parentView;
    private textLabel headerLabel;
    private textLabel kriteriaLabel, weightLabel, typeLabel;
    private textLabel kriteriaEmptyLabel, weightEmptyLabel, typeEmptyLabel;
    private textField kriteriaField;
    private comboBox<String> typeField;
    private comboBox<bobotKriteria> bobotField;
    private buttonCustom buttonBack, buttonReset, buttonSave, buttonDetail;
    private appIcons appIcons = new appIcons();
    private imageIcon backIcon = appIcons.getBackIconWhite(20, 20);
    private imageIcon detailIcon = appIcons.getDetailIconWhite(20, 20);
    private authDataKriteria authData = new authDataKriteria();
    private int bobotKriteriaIdToEdit = -1;

    public popUpInputKriteria(mainFrame parentApp, dashboardAdminView parentView) {
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
        setAction();

        add(kriteriaLabel);
        add(weightLabel);
        add(typeLabel);

        add(kriteriaEmptyLabel);
        add(weightEmptyLabel);
        add(typeEmptyLabel);

        add(kriteriaField);
        add(bobotField);
        add(typeField);

        add(buttonBack);
        add(buttonReset);
        add(buttonSave);
        add(buttonDetail);

        add(headerLabel);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Input Data Kriteria", 40, 30, 400, 40);

        kriteriaLabel = new textLabel("Kriteria", 40, 70, 300, 80);
        weightLabel = new textLabel("Bobot Kriteria", 40, 160, 300, 80);

        typeLabel = new textLabel("Tipe Kriteria", 40, 260, 300, 80);

        kriteriaEmptyLabel = new textLabel("Kriteria tidak boleh kosong!", 40, 120, 300, 80);
        weightEmptyLabel = new textLabel("Bobot tidak boleh kosong!", 40, 210, 300, 80);

        typeEmptyLabel = new textLabel("Tipe tidak boleh kosong!", 40, 310, 300, 80);

        kriteriaField = new textField(40, 120, 420, 10);

        buttonDetail = new buttonCustom("", 420, 210, 40, 30, 10);

        kriteriaField.setPlaceholder("Masukan Kriteria");

        bobotKriteria[] bobotItem = {
                null,
                new bobotKriteria("Sangat Penting", 5),
                new bobotKriteria("Penting", 4),
                new bobotKriteria("Cukup Penting", 3),
                new bobotKriteria("Kurang Penting", 2),
                new bobotKriteria("Tidak Penting", 1)
        };

        bobotField = new comboBox<>(bobotItem, 40, 210, 365, 30, 10);
        bobotField.setPlaceholder("Pilih Bobot Kriteria");

        String[] typeItems = { null, "Benefit", "Cost" };
        typeField = new comboBox<>(typeItems, 40, 310, 420, 30, 10);
        typeField.setPlaceholder("Pilih Tipe Kriteria ");

        buttonBack = new buttonCustom("Kembali", 40, 380, 130, 40, 10);

        buttonReset = new buttonCustom("Hapus", 230, 380, 100, 40, 10);
        buttonSave = new buttonCustom("Simpan", 360, 380, 100, 40, 10);

        buttonDetail.setIcon(detailIcon);
        buttonBack.setIcon(backIcon);

        kriteriaEmptyLabel.setVisible(false);
        weightEmptyLabel.setVisible(false);
        typeEmptyLabel.setVisible(false);
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

        int weight = bobotKriteria.getWeight();

        for (int i = 0; i < bobotField.getItemCount(); i++) {
            bobotKriteria item = bobotField.getItemAt(i);

            if (item != null && item.getValue() == weight) {
                bobotField.setSelectedIndex(i);
                break;
            }
        }

        typeField.setSelectedItem(bobotKriteria.getType());

        System.out.println("idKriteria : " + bobotKriteria.getIdKriteria());

        bobotKriteriaIdToEdit = bobotKriteria.getIdKriteria();
    }

    private void setAction() {
        buttonDetail.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showBobotKriteriaPopUp();
            }
        });

        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                kriteriaField.setText(null);
                bobotField.setSelectedItem(null);
                typeField.setSelectedItem(null);
            }
        });

        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentApp.hideGlassFormPanel();
            }
        });

        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    String kriteria = kriteriaField.getText().trim();
                    bobotKriteria selectedBobot = bobotField.getSelectedItem();
                    String type = (String) typeField.getSelectedItem();

                    String stringWeight = (selectedBobot != null)
                            ? String.valueOf(selectedBobot.getValue())
                            : "";

                    // Validasi input kosong
                    String validation = authData.validateBobotKriteriaInput(kriteria, stringWeight, type);

                    kriteriaEmptyLabel.setVisible(false);
                    weightEmptyLabel.setVisible(false);
                    typeEmptyLabel.setVisible(false);

                    switch (validation) {
                        case "ALL_FIELDS_EMPTY":
                            kriteriaEmptyLabel.setVisible(true);
                            weightEmptyLabel.setVisible(true);
                            typeEmptyLabel.setVisible(true);
                            return;
                        case "KRITERIA_EMPTY":
                            kriteriaEmptyLabel.setVisible(true);
                            return;
                        case "WEIGHT_EMPTY":
                            weightEmptyLabel.setVisible(true);
                            return;
                        case "TYPE_EMPTY":
                            typeEmptyLabel.setVisible(true);
                            return;
                        case "VALID":
                            kriteriaEmptyLabel.setVisible(false);
                            weightEmptyLabel.setVisible(false);
                            typeEmptyLabel.setVisible(false);

                            boolean success = false;
                            int weight = Integer.parseInt(stringWeight);

                            if (bobotKriteriaIdToEdit == -1) {
                                // Insert data baru
                                success = authDataKriteria.insertBobotKriteria(kriteria, weight, type);
                                if (success) {
                                    parentApp.hideGlassFormPanel();
                                    parentView.showDashboardBobotKriteria();
                                    parentView.showSuccessPopUp("Data kriteria berhasil disimpan");
                                } else {
                                    parentView.showFailedPopUp("Data kriteria gagal disimpan");
                                }
                            } else {
                                // Update data lama
                                success = authDataKriteria.updateBobotKriteria(bobotKriteriaIdToEdit, kriteria, weight,
                                        type);
                                if (success) {
                                    parentApp.hideGlassFormPanel();
                                    parentView.showDashboardBobotKriteria();
                                    parentView.showSuccessPopUp("Data kriteria berhasil diubah");
                                } else {
                                    parentView.showFailedPopUp("Data kriteria gagal diubah");
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
