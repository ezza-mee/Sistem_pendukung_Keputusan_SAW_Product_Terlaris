package com.main.views.dashboardStaff.Report;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.models.connectionDatabase;
import com.main.routes.dashboardStaffView;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JRViewer;

public class reportSupplierDashboardView extends contentPanel {
    
    private dashboardStaffView parentView;

    private textLabel headerLabel;
    private panelRounded headerPanel, contentPanel;
    private buttonCustom buttonPrint;

    // Menyimpan report Data Transaction
    private JasperPrint jasperPrint;

    public reportSupplierDashboardView(dashboardStaffView parentView) {
        super();
        this.parentView = parentView;
        initContent();
        initActions();
        // Langsung load report Data Transaction saat class diinisialisasi
        loadTransactionReport();
    }

    @Override
    public void initContent() {
        setPosition();
        setColor();
        setFont();

        headerPanel.add(buttonPrint);

        add(headerLabel);
        add(headerPanel);
        add(contentPanel);

        setVisible(true);
    }

    private void setPosition() {
        headerLabel = new textLabel("Data Report", 40, 0, 300, 80);
        headerPanel = new panelRounded(40, 80, 1050, 110, 10, 10);
        contentPanel = new panelRounded(40, 220, 1050, 410, 10, 10);
        contentPanel.setLayout(new BorderLayout()); // Penting untuk JRViewer

        buttonPrint = new buttonCustom("Print", 900, 35, 120, 40, 10);
    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        headerPanel.setBackground(color.WHITE);
        contentPanel.setBackground(color.WHITE);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));
    }

    private void initActions() {
        // Event Button untuk print report
        buttonPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printReport();
            }
        });
    }

    private void loadTransactionReport() {
        try (Connection conn = connectionDatabase.getConnection()) {

            String reportPath = "/com/main/report/reportDataSupplier.jasper";

            // Ambil report dari classpath
            java.io.InputStream reportStream = getClass().getResourceAsStream(reportPath);

            if (reportStream == null) {
               parentView.showFailedPopUp("File report Data Supplier Not Found.");
                return;
            }

            Map<String, Object> params = new HashMap<>();
            // params.put("parameterName", value); // Tambahkan parameter jika diperlukan

            jasperPrint = JasperFillManager.fillReport(reportStream, params, conn);

            contentPanel.removeAll();
            contentPanel.add(new JRViewer(jasperPrint), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
            parentView.showFailedPopUp("Gagal memuat report Data Supplier: " + ex.getMessage());
        }
    }

    private void printReport() {
        if (jasperPrint == null) {
            parentView.showSuccessPopUp( "Report belum dimuat. Silakan tunggu sebentar.");
            return;
        }

        try {
            // Gunakan FileDialog bawaan OS
            java.awt.FileDialog fileDialog = new java.awt.FileDialog((java.awt.Frame) null, "Simpan Laporan Data Supplier", java.awt.FileDialog.SAVE);
            
            // Default nama file
            fileDialog.setFile("Data_Supplier_Report.pdf");

            fileDialog.setVisible(true);

            // Ambil lokasi yang dipilih user
            String directory = fileDialog.getDirectory();
            String filename = fileDialog.getFile();

            if (directory == null || filename == null) {
                // User menekan Cancel
                return;
            }

            java.io.File fileToSave = new java.io.File(directory, filename);

            // Ekspor ke PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, fileToSave.getAbsolutePath());

           parentView.showSuccessPopUp("Laporan Data Supplier berhasil disimpan:\n" + fileToSave.getAbsolutePath());

            // Opsional: buka foldernya
            try {
                java.awt.Desktop.getDesktop().open(new java.io.File(directory));
            } catch (Exception ex) {
                // Abaikan jika gagal
            }

        } catch (Exception e) {
            e.printStackTrace();
            parentView.showFailedPopUp("Gagal menyimpan laporan: " + e.getMessage());
        }
    }
}