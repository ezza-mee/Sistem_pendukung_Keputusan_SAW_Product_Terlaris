package com.main.routes;

import com.main.auth.utils.Role;
import com.main.components.panelApps.wrapperPanel;
import com.main.views.dashboardAdmin.*;
import com.main.views.dashboardStaff.*;

public class mainContent extends wrapperPanel {

    private mainFrame mainFrame;
    private loginView loginView;

    private dashboardAdminView dashboardAdminView;
    private dashboardStaffView dashboardStaffView;

    private parentDashboardAdmin parentDashboardAdmin;
    private parentDashboardStaff parentDashboardStaff;

    public mainContent(mainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
        loginView = new loginView(this);
        showLoginView();
    }

    private void refreshContent() {
        try {
            removeAll();
            revalidate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoginView() {
        refreshContent();
        setSize(1080, 720);
        add(loginView);
    }

    public void showDashboardByRole(Role role) {
        refreshContent();
        setSize(1366, 768);

        if (role == Role.ADMIN) {
            dashboardAdminView = new dashboardAdminView(mainFrame, role);
            parentDashboardAdmin = new parentDashboardAdmin(dashboardAdminView, role);
            dashboardAdminView.showDashboardHome();
            dashboardAdminView.resetLastContent();
            parentDashboardAdmin.getNavbar().showHomeView();
            // add class yang menampung semua content bukan parent dashboard
            add(dashboardAdminView);
        } else {
            dashboardStaffView = new dashboardStaffView(mainFrame, role);
            parentDashboardStaff = new parentDashboardStaff(dashboardStaffView, role);
            if (role == Role.CASHIER) {
                parentDashboardStaff.getNavbar().showHomeView();
                dashboardStaffView.showDashboardHome();
                dashboardStaffView.resetLastContent();
            } else if (role == Role.SUPPLIER) {
                parentDashboardStaff.getNavbar().showHomeView();
                dashboardStaffView.showDashboardHome();
                dashboardStaffView.resetLastContent();
            }
            add(dashboardStaffView);
            System.out.println("Role Staff : " + role);
        }

        revalidate();
        repaint();

        setVisible(true);
    }

    public mainFrame getMainFrame() {
        return mainFrame;
    }
}
