package com.main.views.dashboardAdmin;

import com.main.components.*;
import com.main.components.panelApps.navigationPanel;
import com.main.routes.dashboardAdminView;

public class navigationDashboardView extends navigationPanel {

    private dashboardAdminView contentView;

    private appIcons appIcons = new appIcons();

    private navigation navHome = new navigation(
            appIcons.getHomeIconDefault(25, 25),
            appIcons.getHomeIconHover(25, 25),
            "Beranda",
            70

    );

    private navigation navProduct = new navigation(
            appIcons.getProductIconDefault(25, 25),
            appIcons.getProductIconHover(25, 25),
            "Produk",
            70 + 50

    );

    private navigation navSupplier = new navigation(
            appIcons.getSupplierIconDefault(25, 25),
            appIcons.getSupplierIconHover(25, 25),
            "Supplier",
            70 + 50 + 50

    );

    private navigation navTable = new navigation(
            appIcons.getTableIconDefault(25, 25),
            appIcons.getTableIconHover(25, 25),
            "Meja",
            70 + 50 + 50 + 50

    );

    private navigation navTransaction = new navigation(
            appIcons.getTransactionIconDefault(25, 25),
            appIcons.getTransactionIconHover(25, 25),
            "Transaksi",
            70 + 50 + 50 + 50 + 50

    );

    private navigation navConvertion = new navigation(
            appIcons.getConvertionIconDefault(25, 25),
            appIcons.getConvertionIconHover(25, 25),
            "Konversi",
            70 + 50 + 50 + 50 + 50 + 50

    );

    private navigation navCalculation = new navigation(
            appIcons.getCalculationIconDefault(25, 25),
            appIcons.getCalculationIconHover(25, 25),
            "Perhitungan",
            70 + 50 + 50 + 50 + 50 + 50 + 50

    );

    private navigation navStaff = new navigation(
            appIcons.getStaffIconDefault(25, 25),
            appIcons.getStaffIconHover(25, 25),
            "Staff",
            70 + 50 + 50 + 50 + 50 + 50 + 50 + 50

    );

    private navigation navReport = new navigation(
            appIcons.getReportIconDefault(25, 25),
            appIcons.getReportIconHover(25, 25),
            "Report",
            70 + 50 + 50 + 50 + 50 + 50 + 50 + 50 + 50

    );

    private navigation navLogout = new navigation(
            appIcons.getLogoutIconDefault(25, 25),
            appIcons.getLogoutIconHover(25, 25),
            "Keluar",
            70 + 50 + 50 + 50 + 50 + 50 + 50 + 50 + 50 + 50

    );

    private void resetNavigation() {
        navHome.setForeground(color.WHITE);
        navHome.setBackground(color.DARKGREEN);
        navHome.setNavigationInAktif();

        navProduct.setForeground(color.WHITE);
        navProduct.setBackground(color.DARKGREEN);
        navProduct.setNavigationInAktif();

        navSupplier.setForeground(color.WHITE);
        navSupplier.setBackground(color.DARKGREEN);
        navSupplier.setNavigationInAktif();

        navTable.setForeground(color.WHITE);
        navTable.setBackground(color.DARKGREEN);
        navTable.setNavigationInAktif();

        navTransaction.setForeground(color.WHITE);
        navTransaction.setBackground(color.DARKGREEN);
        navTransaction.setNavigationInAktif();

        navConvertion.setForeground(color.WHITE);
        navConvertion.setBackground(color.DARKGREEN);
        navConvertion.setNavigationInAktif();

        navCalculation.setForeground(color.WHITE);
        navCalculation.setBackground(color.DARKGREEN);
        navCalculation.setNavigationInAktif();

        navStaff.setForeground(color.WHITE);
        navStaff.setBackground(color.DARKGREEN);
        navStaff.setNavigationInAktif();

        navReport.setForeground(color.WHITE);
        navReport.setBackground(color.DARKGREEN);
        navReport.setNavigationInAktif();

        navLogout.setForeground(color.WHITE);
        navLogout.setBackground(color.DARKGREEN);
        navLogout.setNavigationInAktif();

    }

    public navigationDashboardView(dashboardAdminView contentView) {
        super();
        this.contentView = contentView;

        handelNavigation();

        add(navHome);
        add(navProduct);
        add(navSupplier);
        add(navTable);
        add(navTransaction);
        add(navConvertion);
        add(navCalculation);
        add(navStaff);
        add(navReport);
        add(navLogout);
    }

    private void handelNavigation() {

        navHome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showHomeView();
            }
        });

        navProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showProductView();
            }
        });

        navSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showSupplierView();
            }
        });

        navTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showTableView();
            }
        });

        navTransaction.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showTransactionView();
            }
        });

        navConvertion.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showConvertionView();
            }
        });

        navCalculation.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showCalculationView();
            }
        });

        navStaff.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showStaffView();
            }
        });

        navReport.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showReportView();
            }
        });

        navLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                showLogoutView();
            }
        });

    }

    public void showHomeView() {
        resetNavigation();

        navHome.setForeground(color.DARKGREEN);
        navHome.setBackground(color.WHITE);
        navHome.setNavigationAktif();

        contentView.showDashboardHome();

        setVisible(true);
    }

    public void showProductView() {
        resetNavigation();

        navProduct.setForeground(color.DARKGREEN);
        navProduct.setBackground(color.WHITE);
        navProduct.setNavigationAktif();

        contentView.showDashboardProduct();

        setVisible(true);
    }

    public void showSupplierView() {
        resetNavigation();

        navSupplier.setForeground(color.DARKGREEN);
        navSupplier.setBackground(color.WHITE);
        navSupplier.setNavigationAktif();

        contentView.showDashboardSupplier();

        setVisible(true);
    }

    public void showTableView() {
        resetNavigation();

        navTable.setForeground(color.DARKGREEN);
        navTable.setBackground(color.WHITE);
        navTable.setNavigationAktif();

        contentView.showDashboardTable();

        setVisible(true);
    }

    public void showTransactionView() {
        resetNavigation();

        navTransaction.setForeground(color.DARKGREEN);
        navTransaction.setBackground(color.WHITE);
        navTransaction.setNavigationAktif();

        contentView.showDashboardTransaction();

        setVisible(true);
    }

    public void showConvertionView() {
        resetNavigation();

        navConvertion.setForeground(color.DARKGREEN);
        navConvertion.setBackground(color.WHITE);
        navConvertion.setNavigationAktif();

        contentView.showDashboardConvertion();

        setVisible(true);
    }

    public void showCalculationView() {
        resetNavigation();

        navCalculation.setForeground(color.DARKGREEN);
        navCalculation.setBackground(color.WHITE);
        navCalculation.setNavigationAktif();

        contentView.showDashboardCalculation();

        setVisible(true);
    }

    public void showStaffView() {
        resetNavigation();

        navStaff.setForeground(color.DARKGREEN);
        navStaff.setBackground(color.WHITE);
        navStaff.setNavigationAktif();

        contentView.showDashboardStaff();

        setVisible(true);
    }

    public void showReportView() {
        resetNavigation();

        navReport.setForeground(color.DARKGREEN);
        navReport.setBackground(color.WHITE);
        navReport.setNavigationAktif();

        contentView.showDashboardReport();

        setVisible(true);
    }

    public void showLogoutView() {
        resetNavigation();

        navLogout.setForeground(color.DARKGREEN);
        navLogout.setBackground(color.WHITE);
        navLogout.setNavigationAktif();

        contentView.showLogoutApp();

        setVisible(true);
    }
}
