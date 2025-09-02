package com.main.views.dashboardAdmin;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.models.product.loadDataProduct;
import com.main.models.rangking.loadDataRangking;
import com.main.models.staff.loadDataStaff;
import com.main.models.supplier.loadDataSupplier;
import com.main.models.transaction.loadDataTransaction;
import com.main.routes.dashboardAdminView;

public class homeDashboardView extends contentPanel {

    private dashboardAdminView parentView;

    private panelRounded panelProduct;
    private panelRounded panelStaff;
    private panelRounded panelSupplier;
    private panelRounded panelTransaction;
    private panelRounded panelDiagramTransaction;

    private textLabel labelProduct;
    private textLabel labelSupplier;
    private textLabel labelStaff;
    private textLabel labelTransaction;

    private textLabel valueProduct;
    private textLabel valueSupplier;
    private textLabel valueStaff;
    private textLabel valueTransaction;

    private appIcons appIcons = new appIcons();

    private imageIcon iconProduct = appIcons.getProductIconHover(55, 55);
    private imageIcon iconSupplier = appIcons.getSupplierIconHover(55, 55);
    private imageIcon iconStaff = appIcons.getStaffIconHover(55, 55);
    private imageIcon iconTransaction = appIcons.getTransactionIconHover(55, 55);

    private int quantyAllDataProduct = loadDataProduct.getAllQuantityDataProduct();
    private int quantyAllDataSupplier = loadDataSupplier.getAllQuantityDataSupplier();
    private int quantyAllDataStaff = loadDataStaff.getAllQuantityDataStaff();
    private int quantyAllDataTransaction = loadDataTransaction.getAllQuantityDataTransaction();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String today = sdf.format(new Date());

    private String selectedPriode = null;

    private datePickerField dateField;

    private tableNoActionButton dataRangking;
    private scrollTable scrollDataRangking;

    public homeDashboardView(dashboardAdminView parentView) {
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

        panelProduct.add(iconProduct);
        panelProduct.add(labelProduct);
        panelProduct.add(valueProduct);

        panelSupplier.add(iconSupplier);
        panelSupplier.add(labelSupplier);
        panelSupplier.add(valueSupplier);

        panelStaff.add(iconStaff);
        panelStaff.add(labelStaff);
        panelStaff.add(valueStaff);

        panelTransaction.add(iconTransaction);
        panelTransaction.add(labelTransaction);
        panelTransaction.add(valueTransaction);

        panelDiagramTransaction.add(dateField);
        panelDiagramTransaction.add(scrollDataRangking);

        add(panelProduct);
        add(panelStaff);
        add(panelSupplier);
        add(panelTransaction);
        add(panelDiagramTransaction);

        setVisible(true);
    }

    private void setLayout() {
        panelProduct = new panelRounded(40, 40, 230, 150, 10, 10);
        panelSupplier = new panelRounded(310, 40, 230, 150, 10, 10);
        panelStaff = new panelRounded(580, 40, 230, 150, 10, 10);
        panelTransaction = new panelRounded(850, 40, 230, 150, 10, 10);
        panelDiagramTransaction = new panelRounded(40, 230, 1040, 400, 10, 10);

        labelProduct = new textLabel("Data Product", 0, 10, 230, 40);
        labelSupplier = new textLabel("Data Supplier", 0, 10, 230, 40);
        labelStaff = new textLabel("Data Staff", 0, 10, 230, 40);
        labelTransaction = new textLabel("Data Transaction", 0, 10, 230, 40);

        String stringDataProduct = String.valueOf(quantyAllDataProduct);
        String stringDataSupplier = String.valueOf(quantyAllDataSupplier);
        String stringDataStaff = String.valueOf(quantyAllDataStaff);
        String stringDataTransaction = String.valueOf(quantyAllDataTransaction);

        valueProduct = new textLabel(stringDataProduct, 100, 45, 100, 80);
        valueSupplier = new textLabel(stringDataSupplier, 100, 45, 100, 80);
        valueStaff = new textLabel(stringDataStaff, 100, 45, 100, 80);
        valueTransaction = new textLabel(stringDataTransaction, 100, 45, 100, 80);

        iconProduct.setBounds(20, 55, 55, 55);
        iconSupplier.setBounds(20, 55, 55, 55);
        iconStaff.setBounds(20, 55, 55, 55);
        iconTransaction.setBounds(20, 55, 55, 55);

        dateField = new datePickerField(20, 20, 300, 40, today);

        dataRangking = new tableNoActionButton(loadDataRangking.getAllDataRangkingByPeriode(selectedPriode));
        scrollDataRangking = new scrollTable(dataRangking, 0, 80, 1050, 300);

    }

    private void setColor() {
        panelProduct.setBackground(color.WHITE);
        panelStaff.setBackground(color.WHITE);
        panelSupplier.setBackground(color.WHITE);
        panelTransaction.setBackground(color.WHITE);
        panelDiagramTransaction.setBackground(color.WHITE);

        labelProduct.setForeground(color.DARKGREEN);
        labelSupplier.setForeground(color.DARKGREEN);
        labelStaff.setForeground(color.DARKGREEN);
        labelTransaction.setForeground(color.DARKGREEN);

        valueProduct.setForeground(color.DARKGREEN);
        valueSupplier.setForeground(color.DARKGREEN);
        valueStaff.setForeground(color.DARKGREEN);
        valueTransaction.setForeground(color.DARKGREEN);

    }

    private void setFont() {
        labelProduct.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));
        labelSupplier.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));
        labelStaff.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));
        labelTransaction.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));

        valueProduct.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 22f));
        valueSupplier.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 22f));
        valueStaff.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 22f));
        valueTransaction.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 22f));

        labelProduct.setHorizontalAlignment(JLabel.CENTER);
        labelSupplier.setHorizontalAlignment(JLabel.CENTER);
        labelStaff.setHorizontalAlignment(JLabel.CENTER);
        labelTransaction.setHorizontalAlignment(JLabel.CENTER);

        valueProduct.setHorizontalAlignment(JLabel.CENTER);
        valueSupplier.setHorizontalAlignment(JLabel.CENTER);
        valueStaff.setHorizontalAlignment(JLabel.CENTER);
        valueTransaction.setHorizontalAlignment(JLabel.CENTER);
    }

    private void setAction() {
        dateField.getDatePicker().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    String fullDate = dateField.getSelectedDate();
                    if (fullDate != null) {
                        Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(fullDate);
                        selectedPriode = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
                        loadTableRangking(selectedPriode);
                    } else {
                        parentView.showFailedPopUp("Please select a date to display the criteria data");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
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

        setHeaderTableRangking();
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
    }

}
