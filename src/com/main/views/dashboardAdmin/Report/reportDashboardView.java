package com.main.views.dashboardAdmin.Report;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.models.connectionDatabase;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JRViewer;

public class reportDashboardView extends contentPanel {

    private textLabel headerLabel;
    private panelRounded headerPanel, contentPanel;
    private comboBox<String> reportField;
    private buttonCustom buttonPrint;

    // Menyimpan report terakhir yang di-preview
    private JasperPrint jasperPrint;

    public reportDashboardView() {
        super();
        initContent();
        initActions(); // Tambahan: event handler
    }

    @Override
    public void initContent() {
        setPosition();
        setColor();
        setFont();

        headerPanel.add(buttonPrint);
        headerPanel.add(reportField);

        add(headerLabel);
        add(headerPanel);
        add(contentPanel);

        setVisible(true);
    }

    private void setPosition() {
        headerLabel = new textLabel("Data Report", 40, 0, 200, 80);
        headerPanel = new panelRounded(40, 80, 1050, 110, 10, 10);
        contentPanel = new panelRounded(40, 220, 1050, 410, 10, 10);
        contentPanel.setLayout(new BorderLayout()); // Penting untuk JRViewer

        String[] reportItems = {
                null,
                "Data Product",
                "Data Supplier",
                "Data Table",
                "Data Transaction",
                "Data Alternatif",
                "Data Kriteria",
                "Data Perangkingan", 
                "Data Normalisasi"
        };

        reportField = new comboBox<>(reportItems, 40, 40, 300, 30, 10);
        reportField.setPlaceholder("Select Report");

        buttonPrint = new buttonCustom("Print", 900, 35, 100, 40, 10);
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
        // Event ComboBox untuk preview report
        reportField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedReport = (String) reportField.getSelectedItem();
                if (selectedReport != null) {
                    previewReport(selectedReport);
                }
            }
        });

        // Event Button untuk print report
        buttonPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printReport();
            }
        });
    }

    private void previewReport(String reportName) {
    try (Connection conn = connectionDatabase.getConnection()) {

        String reportPath = getReportPath(reportName);

        if (reportPath == null) {
            JOptionPane.showMessageDialog(this, "Report belum tersedia untuk pilihan ini.");
            return;
        }

        // Ambil report dari classpath
        java.io.InputStream reportStream = getClass().getResourceAsStream(reportPath);

        if (reportStream == null) {
            JOptionPane.showMessageDialog(this, "File report tidak ditemukan di classpath.");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        // params.put("parameterName", value);

        jasperPrint = JasperFillManager.fillReport(reportStream, params, conn);

        contentPanel.removeAll();
        contentPanel.add(new JRViewer(jasperPrint), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memuat report: " + ex.getMessage());
    }
}


private void printReport() {
    if (jasperPrint == null) {
        JOptionPane.showMessageDialog(this, "Silakan preview report terlebih dahulu.");
        return;
    }

    try {
        // Gunakan FileDialog bawaan OS (lebih native dibanding JFileChooser)
        java.awt.FileDialog fileDialog = new java.awt.FileDialog((java.awt.Frame) null, "Simpan Laporan", java.awt.FileDialog.SAVE);
        
        String selectedReport = (String) reportField.getSelectedItem();
        if (selectedReport == null) selectedReport = "Laporan";

        // Default nama file
        fileDialog.setFile(selectedReport.replace(" ", "_") + ".pdf");

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

        JOptionPane.showMessageDialog(this, "Laporan berhasil disimpan:\n" + fileToSave.getAbsolutePath());

        // Opsional: buka foldernya
        try {
            java.awt.Desktop.getDesktop().open(new java.io.File(directory));
        } catch (Exception ex) {
            // Abaikan jika gagal
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal menyimpan laporan: " + e.getMessage());
    }
}




    private String getReportPath(String reportName) {
        if ("Data Product".equals(reportName)) {
            return "/com/main/report/reportDataProduct.jasper";
        } else if ("Data Supplier".equals(reportName)) {
            return "/com/main/report/reportDataSupplier.jasper";
        } else if ("Data Table".equals(reportName)) {
            return "/com/main/report/reportDataTable.jasper";
        } else if ("Data Transaction".equals(reportName)) {
            return "/com/main/report/reportDataTransaction.jasper";
        } else if ("Data Alternatif".equals(reportName)) {
            return "/com/main/report/reportDataAlternatif.jasper";
        } else if ("Data Kriteria".equals(reportName)) {
            return "/com/main/report/reportDataKriteria.jasper";
        } else if ("Data Perangkingan".equals(reportName)) {
            return "/com/main/report/reportDataPerangking.jasper";
        } else if ("Data Normalisasi".equals(reportName)) {
            return "/com/main/report/reportDataNormalisation.jasper";
        }
        return null;
    }
}
