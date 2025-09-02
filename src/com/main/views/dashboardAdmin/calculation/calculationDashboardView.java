package com.main.views.dashboardAdmin.calculation;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.models.alternatif.loadDataAlternatif;
import com.main.models.normalisation.loadDataNormalisation;
import com.main.models.rangking.loadDataRangking;
import com.main.routes.dashboardAdminView;

public class calculationDashboardView extends contentPanel {

    private dashboardAdminView parentView;
    private panelRounded headerPanel, contentPanel, tablePanel;
    private textLabel headerLabel;
    private buttonCustom buttonAddBobot;
    private tableNoActionButton dataAlternatif, dataNormalisation, dataRangking;
    private tableNoActionButton calculationNormalisation, calculationRangking;
    private scrollTable scrollDataAlternatif, scrollDataNormalisation, scrollDataRangking;
    private scrollTable scrollCalculationNormalisation, scrollCalculationRangking;
    private linkLabel dataAlternatifLink, dataNormalisationLink, dataRangkingLink;

    private comboBox<String> dataCalculationField;

    private datePickerField dateField;
    private String selectedPriode = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String today = sdf.format(new Date());

    private String currentCalculation = "ALTERNATIF";

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        headerPanel.setBackground(color.WHITE);
        contentPanel.setBackground(color.WHITE);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));
        dataAlternatifLink.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        dataNormalisationLink.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        dataRangkingLink.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
    }

    public calculationDashboardView(dashboardAdminView parentView) {
        super();
        this.parentView = parentView;

        initContent();
    }

    @Override
    public void initContent() {
        setLayout();
        setColor();
        setFont();
        setAction();
        showDataAlternatif();

        headerPanel.add(dateField);
        headerPanel.add(buttonAddBobot);

        contentPanel.add(dataAlternatifLink);
        contentPanel.add(dataNormalisationLink);
        contentPanel.add(dataRangkingLink);
        contentPanel.add(dataCalculationField);
        contentPanel.add(tablePanel);

        add(headerLabel);
        add(headerPanel);
        add(contentPanel);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Data Calculation", 40, 0, 300, 80);
        headerPanel = new panelRounded(40, 80, 1050, 110, 10, 10);
        contentPanel = new panelRounded(40, 220, 1050, 410, 10, 10);
        tablePanel = new panelRounded(0, 60, 1050, 300, 0, 0);
        dateField = new datePickerField(40, 40, 300, 40, today);
        dateField.setSelectedDate(today);
        buttonAddBobot = new buttonCustom("Add Bobot", 870, 35, 135, 40, 10);

        dataAlternatifLink = new linkLabel("Data Alternatif", 40, 20, 110, 30);
        dataNormalisationLink = new linkLabel("Data Normalisation", 190, 20, 150, 30);
        dataRangkingLink = new linkLabel("Data Rangking", 380, 20, 110, 30);

        String[] dataCalculationItems = { null, "Calculation", "Output" };
        dataCalculationField = new comboBox<>(dataCalculationItems, 540, 20, 200, 30, 10);
        dataCalculationField.setPlaceholder("Select Data");

        dataAlternatif = new tableNoActionButton(loadDataAlternatif.getAllDataAlternatifByPeriode(selectedPriode));
        scrollDataAlternatif = new scrollTable(dataAlternatif, 0, 0, 1050, 300);

        dataNormalisation = new tableNoActionButton(
                loadDataNormalisation.getAllDataNormalisationByPeriode(selectedPriode));
        scrollDataNormalisation = new scrollTable(dataNormalisation, 0, 0, 1050, 300);

        dataRangking = new tableNoActionButton(loadDataRangking.getAllDataRangkingByPeriode(selectedPriode));
        scrollDataRangking = new scrollTable(dataRangking, 0, 0, 1050, 300);

        calculationNormalisation = new tableNoActionButton(
                loadDataNormalisation.getAllDataNormalisationWithFormula(selectedPriode));
        scrollCalculationNormalisation = new scrollTable(calculationNormalisation, 0, 0, 1050, 300);

        calculationRangking = new tableNoActionButton(
                loadDataRangking.getAllDataRangkingWithNormalizedWeightByPeriode(selectedPriode));
        scrollCalculationRangking = new scrollTable(calculationRangking, 0, 0, 1050, 300);

    }

    private void setAction() {

        buttonAddBobot.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showDashboardBobotKriteria();
            }
        });

        dateField.getDatePicker().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    String fullDate = dateField.getSelectedDate();
                    if (fullDate != null) {
                        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(fullDate);
                        selectedPriode = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
                        loadTableAlternatif(selectedPriode);
                        loadTableNormalisation(selectedPriode);
                        loadTableRangking(selectedPriode);
                    } else {
                        parentView.showFailedPopUp("Please select a date to display the criteria data");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        dataCalculationField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                applyCalculationFilter();
            }
        });

        dataAlternatifLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentCalculation = "ALTERNATIF";
                showDataAlternatif();
            }
        });

        dataNormalisationLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentCalculation = "NORMALISATION";
                showDataNormalisation();
            }
        });

        dataRangkingLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                currentCalculation = "RANGKING";
                showDataRangking();
            }
        });

    }

    private void resetLinkLabel() {
        dataAlternatifLink.setForeground(color.DARKGREY);
        dataAlternatifLink.setLabelInActive();

        dataNormalisationLink.setForeground(color.DARKGREY);
        dataNormalisationLink.setLabelInActive();

        dataRangkingLink.setForeground(color.DARKGREY);
        dataRangkingLink.setLabelInActive();

    }

    private void showDataAlternatif() {
        resetLinkLabel();

        dataAlternatifLink.setForeground(color.DARKGREEN);
        dataAlternatifLink.setLabelActive();
        selectedPriode = getSelectedPriode();
        loadTableAlternatif(selectedPriode);

        setHeaderTableAlternatif();

    }

    private void showDataNormalisation() {
        resetLinkLabel();

        dataNormalisationLink.setForeground(color.DARKGREEN);
        dataNormalisationLink.setLabelActive();

        selectedPriode = getSelectedPriode();

        applyCalculationFilter();

        setHeaderTableNormalisation();
    }

    private void showDataRangking() {
        resetLinkLabel();

        dataRangkingLink.setForeground(color.DARKGREEN);
        dataRangkingLink.setLabelActive();

        selectedPriode = getSelectedPriode();

        applyCalculationFilter();

        setHeaderTableRangking();
    }

    private void applyCalculationFilter() {
        try {
            String selected = (String) dataCalculationField.getSelectedItem();

            if (dataNormalisationLink.isActive()) {
                if ("Calculation".equals(selected)) {
                    loadTableCalculationNormalisation(selectedPriode);
                } else {
                    loadTableNormalisation(selectedPriode);
                }
            } else if (dataRangkingLink.isActive()) {
                if ("Calculation".equals(selected)) {
                    loadTableCalculationRangking(selectedPriode);
                } else {
                    loadTableRangking(selectedPriode);
                }
            }

            System.out.println("Priode: " + selectedPriode + ", Filter: " + selected);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getSelectedPriode() {
        try {
            String fullDate = dateField.getSelectedDate();

            if (fullDate == null || fullDate.trim().isEmpty()) {
                fullDate = today;
            }

            Date parsedDate = sdf.parse(fullDate);
            return sdf.format(parsedDate);

        } catch (Exception e) {
            e.printStackTrace();
            return today;
        }
    }

    private void loadTableAlternatif(String periode) {
        DefaultTableModel modelAlternatif;
        if (periode == null || periode.isEmpty()) {
            modelAlternatif = new DefaultTableModel();
        } else {
            modelAlternatif = loadDataAlternatif.getAllDataAlternatifByPeriode(periode);
        }

        dataAlternatif.setModel(modelAlternatif);

        if (dataAlternatif instanceof tableNoActionButton) {
            tableNoActionButton customTable = (tableNoActionButton) dataAlternatif;
            customTable.applyCustomStyle();
        }

        tablePanel.removeAll();
        tablePanel.add(scrollDataAlternatif);
        tablePanel.revalidate();
        tablePanel.repaint();

    }

    private void loadTableNormalisation(String periode) {
        DefaultTableModel modelNormalisation;
        if (periode == null || periode.isEmpty()) {
            modelNormalisation = new DefaultTableModel();
        } else {
            modelNormalisation = loadDataNormalisation.getAllDataNormalisationByPeriode(periode);
        }

        dataNormalisation.setModel(modelNormalisation);

        if (dataNormalisation instanceof tableNoActionButton) {
            tableNoActionButton customTable = (tableNoActionButton) dataNormalisation;
            customTable.applyCustomStyle();
        }

        tablePanel.removeAll();
        tablePanel.add(scrollDataNormalisation);
        tablePanel.revalidate();
        tablePanel.repaint();

        setHeaderTableNormalisation();
    }

    private void loadTableCalculationNormalisation(String periode) {
        DefaultTableModel modelNormalisation;
        if (periode == null || periode.isEmpty()) {
            modelNormalisation = new DefaultTableModel();
        } else {
            modelNormalisation = loadDataNormalisation.getAllDataNormalisationWithFormula(periode);
        }

        calculationNormalisation.setModel(modelNormalisation);

        if (calculationNormalisation instanceof tableNoActionButton) {
            tableNoActionButton customTable = (tableNoActionButton) calculationNormalisation;
            customTable.applyCustomStyle();
        }

        tablePanel.removeAll();
        tablePanel.add(scrollCalculationNormalisation);
        tablePanel.revalidate();
        tablePanel.repaint();

        setHeaderTableNormalisation();
    }

    private void loadTableRangking(String periode) {
        DefaultTableModel modelRangking;
        if (periode == null || periode.isEmpty()) {
            modelRangking = new DefaultTableModel();
        } else {
            modelRangking = loadDataRangking.getAllDataRangkingByPeriode(periode);
        }

        dataRangking.setModel(modelRangking);

        if (dataRangking instanceof tableNoActionButton) {
            tableNoActionButton customTable = (tableNoActionButton) dataRangking;
            customTable.applyCustomStyle();
        }

        tablePanel.removeAll();
        tablePanel.add(scrollDataRangking);
        tablePanel.revalidate();
        tablePanel.repaint();

        setHeaderTableRangking();
    }

    private void loadTableCalculationRangking(String periode) {
        DefaultTableModel modelRangking;
        if (periode == null || periode.isEmpty()) {
            modelRangking = new DefaultTableModel();
        } else {
            modelRangking = loadDataRangking.getAllDataRangkingWithNormalizedWeightByPeriode(periode);
        }

        calculationRangking.setModel(modelRangking);

        if (dataRangking instanceof tableNoActionButton) {
            tableNoActionButton customTable = (tableNoActionButton) calculationRangking;
            customTable.applyCustomStyle();
        }

        tablePanel.removeAll();
        tablePanel.add(scrollCalculationRangking);
        tablePanel.revalidate();
        tablePanel.repaint();

        setHeaderTableRangking();

    }

    private void setHeaderTableAlternatif() {

        dataAlternatif.getColumnModel().getColumn(0).setMinWidth(80);
        dataAlternatif.getColumnModel().getColumn(0).setMaxWidth(80);
        dataAlternatif.getColumnModel().getColumn(0).setWidth(80);

        dataAlternatif.getColumnModel().getColumn(1).setMinWidth(0);
        dataAlternatif.getColumnModel().getColumn(1).setMaxWidth(0);
        dataAlternatif.getColumnModel().getColumn(1).setWidth(0);

        dataAlternatif.getColumnModel().getColumn(2).setMinWidth(300);
        dataAlternatif.getColumnModel().getColumn(2).setMaxWidth(0);
        dataAlternatif.getColumnModel().getColumn(2).setWidth(300);

        dataAlternatif.getColumnModel().getColumn(3).setMinWidth(100);
        dataAlternatif.getColumnModel().getColumn(3).setMaxWidth(100);
        dataAlternatif.getColumnModel().getColumn(3).setWidth(100);

        dataAlternatif.getColumnModel().getColumn(4).setMinWidth(100);
        dataAlternatif.getColumnModel().getColumn(4).setMaxWidth(100);
        dataAlternatif.getColumnModel().getColumn(4).setWidth(100);

        dataAlternatif.getColumnModel().getColumn(5).setMinWidth(100);
        dataAlternatif.getColumnModel().getColumn(5).setMaxWidth(100);
        dataAlternatif.getColumnModel().getColumn(5).setWidth(100);

        dataAlternatif.getColumnModel().getColumn(6).setMinWidth(100);
        dataAlternatif.getColumnModel().getColumn(6).setMaxWidth(100);
        dataAlternatif.getColumnModel().getColumn(6).setWidth(100);

        dataAlternatif.getColumnModel().getColumn(7).setMinWidth(100);
        dataAlternatif.getColumnModel().getColumn(7).setMaxWidth(100);
        dataAlternatif.getColumnModel().getColumn(7).setWidth(100);

    }

    private void setHeaderTableNormalisation() {

        dataNormalisation.getColumnModel().getColumn(0).setMinWidth(80);
        dataNormalisation.getColumnModel().getColumn(0).setMaxWidth(80);
        dataNormalisation.getColumnModel().getColumn(0).setWidth(80);

        dataNormalisation.getColumnModel().getColumn(1).setMinWidth(0);
        dataNormalisation.getColumnModel().getColumn(1).setMaxWidth(0);
        dataNormalisation.getColumnModel().getColumn(1).setWidth(0);

        dataNormalisation.getColumnModel().getColumn(7).setMinWidth(0);
        dataNormalisation.getColumnModel().getColumn(7).setMaxWidth(0);
        dataNormalisation.getColumnModel().getColumn(7).setWidth(0);

        dataNormalisation.getColumnModel().getColumn(8).setMinWidth(0);
        dataNormalisation.getColumnModel().getColumn(8).setMaxWidth(0);
        dataNormalisation.getColumnModel().getColumn(8).setWidth(0);

        calculationNormalisation.getColumnModel().getColumn(0).setMinWidth(80);
        calculationNormalisation.getColumnModel().getColumn(0).setMaxWidth(80);
        calculationNormalisation.getColumnModel().getColumn(0).setWidth(80);

        calculationNormalisation.getColumnModel().getColumn(1).setMinWidth(0);
        calculationNormalisation.getColumnModel().getColumn(1).setMaxWidth(0);
        calculationNormalisation.getColumnModel().getColumn(1).setWidth(0);

        calculationNormalisation.getColumnModel().getColumn(2).setMinWidth(190);
        calculationNormalisation.getColumnModel().getColumn(2).setMaxWidth(190);
        calculationNormalisation.getColumnModel().getColumn(2).setWidth(190);

        calculationNormalisation.getColumnModel().getColumn(3).setMinWidth(240);
        calculationNormalisation.getColumnModel().getColumn(3).setMaxWidth(240);
        calculationNormalisation.getColumnModel().getColumn(3).setWidth(240);

        calculationNormalisation.getColumnModel().getColumn(4).setMinWidth(150);
        calculationNormalisation.getColumnModel().getColumn(4).setMaxWidth(150);
        calculationNormalisation.getColumnModel().getColumn(4).setWidth(150);

        calculationNormalisation.getColumnModel().getColumn(5).setMinWidth(240);
        calculationNormalisation.getColumnModel().getColumn(5).setMaxWidth(240);
        calculationNormalisation.getColumnModel().getColumn(5).setWidth(240);

        calculationNormalisation.getColumnModel().getColumn(6).setMinWidth(150);
        calculationNormalisation.getColumnModel().getColumn(6).setMaxWidth(150);
        calculationNormalisation.getColumnModel().getColumn(6).setWidth(150);

        calculationNormalisation.getColumnModel().getColumn(7).setMinWidth(0);
        calculationNormalisation.getColumnModel().getColumn(7).setMaxWidth(0);
        calculationNormalisation.getColumnModel().getColumn(7).setWidth(0);

        calculationNormalisation.getColumnModel().getColumn(8).setMinWidth(0);
        calculationNormalisation.getColumnModel().getColumn(8).setMaxWidth(0);
        calculationNormalisation.getColumnModel().getColumn(8).setWidth(0);

    }

    private void setHeaderTableRangking() {

        dataRangking.getColumnModel().getColumn(0).setMinWidth(80);
        dataRangking.getColumnModel().getColumn(0).setMaxWidth(80);
        dataRangking.getColumnModel().getColumn(0).setWidth(80);

        dataRangking.getColumnModel().getColumn(1).setMinWidth(0);
        dataRangking.getColumnModel().getColumn(1).setMaxWidth(0);
        dataRangking.getColumnModel().getColumn(1).setWidth(0);

        dataRangking.getColumnModel().getColumn(2).setMinWidth(300);
        dataRangking.getColumnModel().getColumn(2).setMaxWidth(300);
        dataRangking.getColumnModel().getColumn(2).setWidth(300);

        dataRangking.getColumnModel().getColumn(4).setMinWidth(150);
        dataRangking.getColumnModel().getColumn(4).setMaxWidth(150);
        dataRangking.getColumnModel().getColumn(4).setWidth(150);

        dataRangking.getColumnModel().getColumn(6).setMinWidth(0);
        dataRangking.getColumnModel().getColumn(6).setMaxWidth(0);
        dataRangking.getColumnModel().getColumn(6).setWidth(0);

        calculationRangking.getColumnModel().getColumn(0).setMinWidth(80);
        calculationRangking.getColumnModel().getColumn(0).setMaxWidth(80);
        calculationRangking.getColumnModel().getColumn(0).setWidth(80);

        calculationRangking.getColumnModel().getColumn(1).setMinWidth(110);
        calculationRangking.getColumnModel().getColumn(1).setMaxWidth(110);
        calculationRangking.getColumnModel().getColumn(1).setWidth(110);

        calculationRangking.getColumnModel().getColumn(3).setMinWidth(80);
        calculationRangking.getColumnModel().getColumn(3).setMaxWidth(80);
        calculationRangking.getColumnModel().getColumn(3).setWidth(80);

        calculationRangking.getColumnModel().getColumn(4).setMinWidth(80);
        calculationRangking.getColumnModel().getColumn(4).setMaxWidth(80);
        calculationRangking.getColumnModel().getColumn(4).setWidth(80);

        calculationRangking.getColumnModel().getColumn(5).setMinWidth(110);
        calculationRangking.getColumnModel().getColumn(5).setMaxWidth(110);
        calculationRangking.getColumnModel().getColumn(5).setWidth(110);

        calculationRangking.getColumnModel().getColumn(6).setMinWidth(0);
        calculationRangking.getColumnModel().getColumn(6).setMaxWidth(0);
        calculationRangking.getColumnModel().getColumn(6).setWidth(0);

    }

}
