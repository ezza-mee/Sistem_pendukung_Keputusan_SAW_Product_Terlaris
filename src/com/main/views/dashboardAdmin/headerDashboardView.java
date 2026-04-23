package com.main.views.dashboardAdmin;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

import com.main.components.panelApps.contentPanel;
import com.main.components.panelApps.headerPanel;
import com.main.controller.searchableView;
import com.main.components.*;

public class headerDashboardView extends headerPanel {

    private parentDashboardAdmin parentDashboard;

    private panelRounded brandPanel;

    private textLabel welcomeLabel, tujuLangitLabel, forestparkLabel;

    private textField searchField;

    public headerDashboardView(parentDashboardAdmin parentDashboard) {
        super();
        this.parentDashboard = parentDashboard;
        initContent();
    }

    private void initContent() {
        setPosition();
        setColor();
        setFont();

        brandPanel.add(tujuLangitLabel);
        brandPanel.add(forestparkLabel);

        add(brandPanel);
        add(searchField);
        add(welcomeLabel);

        setVisible(true);
    }

    private void setPosition() {
        brandPanel = new panelRounded(0, 0, 240, 80, 0, 0);
        welcomeLabel = new textLabel("Selamat Datang", 300, 0, 200, 80);
        tujuLangitLabel = new textLabel("TujuLangit", 0, 0, 240, 80);
        forestparkLabel = new textLabel("ForestPark", 0, 40, 240, 40);

        searchField = new textField(500, 25, 400, 10);
        searchField.setPlaceholder("Cari");

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = searchField.getText();

                contentPanel current = parentDashboard.getCurrentContent();
                if (current instanceof searchableView) {
                    ((searchableView) current).filterDataByKeyword(keyword);
                }
            }
        });

    }

    private void setColor() {
        setBackground(color.WHITE);
        brandPanel.setBackground(color.DARKGREEN);
        welcomeLabel.setForeground(color.DARKGREEN);

        tujuLangitLabel.setForeground(color.WHITE);
        forestparkLabel.setForeground(color.WHITE);

    }

    private void setFont() {
        welcomeLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 20f));
        tujuLangitLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 18f));
        forestparkLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));

        tujuLangitLabel.setHorizontalAlignment(JLabel.CENTER);
        forestparkLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    public textField getSearchField() {
        return searchField;
    }

}
