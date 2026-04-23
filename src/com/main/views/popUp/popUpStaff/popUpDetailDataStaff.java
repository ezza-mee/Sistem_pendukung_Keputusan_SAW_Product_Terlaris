package com.main.views.popUp.popUpStaff;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.main.components.*;
import com.main.components.panelApps.popUpPanel;
import com.main.models.staff.detailStaff;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;

public class popUpDetailDataStaff extends popUpPanel {

    private mainFrame parentFrame;
    private dashboardAdminView parentView;

    private panelRounded lineHeaderPanel;

    private textLabel headerLabel, nameLabel, emailLabel, phoneLabel, genderLabel, jobdeskLabel, addressLabel,
            statusLabel, passwordLabel;

    private textLabel valueNameLabel, valueEmailLabel, valuePhoneLabel, valueGenderLabel, valueJobdeskLabel,
            valueAddressLabel, valueStatusLabel, valuePasswordLabel;

    private buttonCustom buttonBack;

    private int idStaff;

    public popUpDetailDataStaff(mainFrame parentFrame, dashboardAdminView parentView, int idStaff) {
        super();
        this.parentFrame = parentFrame;
        this.parentView = parentView;
        this.idStaff = idStaff;
        setSize(800, 550);
        System.out.println("ID Staff: " + idStaff);
        initComponent();
    }

    public void initComponent() {
        setPosition();
        setColor();
        setFont();
        handleButton();
        setDetailData(idStaff);

        add(lineHeaderPanel);

        add(headerLabel);
        add(nameLabel);
        add(emailLabel);
        add(phoneLabel);
        add(genderLabel);
        add(jobdeskLabel);
        add(statusLabel);
        add(addressLabel);
        add(passwordLabel);

        add(valueNameLabel);
        add(valueEmailLabel);
        add(valuePhoneLabel);
        add(valueGenderLabel);
        add(valueJobdeskLabel);
        add(valueStatusLabel);
        add(valueAddressLabel);
        add(valuePasswordLabel);

        add(buttonBack);

        setVisible(true);
    }

    private void setPosition() {
        lineHeaderPanel = new panelRounded(250, 80, 300, 5, 10, 10);

        headerLabel = new textLabel("Detail Data Staff", 0, 20, 800, 80);
        nameLabel = new textLabel("Nama", 110, 90, 300, 80);
        genderLabel = new textLabel("Jenis Kelamin", 460, 90, 300, 80);
        emailLabel = new textLabel("Email", 110, 160, 300, 80);
        passwordLabel = new textLabel("Password", 460, 160, 300, 80);
        jobdeskLabel = new textLabel("Jobdesk", 110, 230, 300, 80);
        statusLabel = new textLabel("Status", 460, 230, 300, 80);
        addressLabel = new textLabel("Alamat", 110, 300, 300, 80);
        phoneLabel = new textLabel("Nomor Telpon", 460, 300, 300, 80);

        valueNameLabel = new textLabel("a", 110, 115, 300, 80);
        valueGenderLabel = new textLabel("a", 460, 115, 300, 80);
        valueEmailLabel = new textLabel("a", 110, 185, 300, 80);
        valuePasswordLabel = new textLabel("a", 460, 185, 300, 80);
        valueJobdeskLabel = new textLabel("a", 110, 255, 300, 80);
        valueStatusLabel = new textLabel("a", 460, 255, 300, 80);
        valueAddressLabel = new textLabel("a", 110, 325, 300, 80);
        valuePhoneLabel = new textLabel("a", 460, 325, 300, 80);

        buttonBack = new buttonCustom("Back", 200, 480, 400, 40, 10);
    }

    private void setColor() {
        lineHeaderPanel.setBackground(color.DARKGREEN);
        headerLabel.setForeground(color.BLACK);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 25f));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        nameLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        emailLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        phoneLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        genderLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        statusLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        jobdeskLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        addressLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        passwordLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));

        valueNameLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.REGULAR, 14f));
        valueEmailLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.REGULAR, 14f));
        valuePhoneLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.REGULAR, 14f));
        valueGenderLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.REGULAR, 14f));
        valueStatusLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.REGULAR, 14f));
        valueJobdeskLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.REGULAR, 14f));
        valueAddressLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.REGULAR, 14f));
        valuePasswordLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.REGULAR, 14f));

    }

    private void handleButton() {
        buttonBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentFrame.hideGlassNotificationPanel();
            }
        });
    }

    public void setDetailData(int idStaff) {
        Object[] detail = detailStaff.getDetailAccount(idStaff);

        if (detail != null) {
            valueNameLabel.setText((String) detail[3]);
            valueEmailLabel.setText((String) detail[4]);
            valuePhoneLabel.setText((String) detail[5]);
            valueGenderLabel.setText((String) detail[6]);
            valueJobdeskLabel.setText((String) detail[7]);
            valueAddressLabel.setText((String) detail[8]);

            String password = (detail[9] != null) ? (String) detail[9] : "-";
            valuePasswordLabel.setText(password);

            valueStatusLabel.setText((String) detail[10]);
        }
    }

}
