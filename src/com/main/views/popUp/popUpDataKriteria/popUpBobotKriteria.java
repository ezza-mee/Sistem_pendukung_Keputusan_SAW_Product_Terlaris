package com.main.views.popUp.popUpDataKriteria;

import javax.swing.table.DefaultTableModel;

import com.main.components.*;
import com.main.components.panelApps.popUpPanel;
import com.main.routes.mainFrame;

public class popUpBobotKriteria extends popUpPanel {

    private mainFrame parentApp;

    private textLabel headerLabel;

    private buttonCustom buttonBack;

    private tableNoActionButton tableBobotKriteria;
    private scrollTable scrollTable;

    public popUpBobotKriteria(mainFrame parentApp) {
        super();
        this.parentApp = parentApp;
        setSize(550, 380); // Diperbesar untuk menampung tabel
        initComponent();
    }

    private void initComponent() {
        setPosition();
        setColor();
        setFont();
        setupTable();
        handleButton();

        // Menambahkan tabel ke panel setelah dibuat
        add(scrollTable);

        setVisible(true);
    }

    private void setPosition() {
        headerLabel = new textLabel("Data Weight Kriteria", 30, 30, 550, 30);
        buttonBack = new buttonCustom("Back", 80, 320, 400, 40, 10);

        add(headerLabel);
        add(buttonBack);
    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);

    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
    }

    private void setupTable() {
        // Mendefinisikan header kolom
        String[] columnNames = { "No", "Kriteria", "Weight" };

        // Data bobot kriteria yang sudah ditetapkan
        Object[][] data = {
                { "1", "Sangat Penting", "5" },
                { "2", "Penting", "4" },
                { "3", "Cukup Penting", "3" },
                { "4", "Kurang Penting", "2" },
                { "5", "Tidak Penting", "1" }
        };

        // Membuat DefaultTableModel dengan data
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Membuat instance tableBobotKriteria dengan model
        tableBobotKriteria = new tableNoActionButton(model);
        scrollTable = new scrollTable(tableBobotKriteria, 0, 85, 550, 200);

        // Menerapkan custom style
        tableBobotKriteria.applyCustomStyle();

        tableBobotKriteria.setRowHeight(30);
    }

    private void handleButton() {
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentApp.hideGlassNotificationPanel();
            }
        });
    }
}