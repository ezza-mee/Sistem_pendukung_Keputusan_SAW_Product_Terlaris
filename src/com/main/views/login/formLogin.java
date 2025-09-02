package com.main.views.login;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.main.auth.sessionLogin;
import com.main.auth.sessionManager;
import com.main.auth.utils.Role;
import com.main.components.*;
import com.main.components.panelApps.containerPanel;
import com.main.models.entity.dataStaff;
import com.main.models.staff.loadDataStaff;
import com.main.models.staff.staffService;
import com.main.routes.loginView;
import com.main.routes.mainFrame;
import com.main.services.authLogin;
import com.main.views.popUp.popUpExit;

public class formLogin extends containerPanel {

    private loginView parentView;
    private mainFrame parentFrame;

    private panelRounded wrapperPanel;
    private panelRounded cardPanel;
    private panelRounded shapeOne;
    private panelRounded shapeTwo;
    private panelRounded shapeThree;
    private panelRounded lineShape;

    private textLabel headerLabel;
    private textLabel EmailLabel;
    private textLabel passwordLabel;
    private textLabel warningEmailLabel;
    private textLabel warningPasswordLabel;

    private textField EmailField;
    private passwordField passwordField;

    private buttonCustom buttonLogin;

    private appIcons appIcons = new appIcons();

    private imageIcon exitIcon = appIcons.getExitIconWhite(30, 30);
    private imageIcon imageCoffeOne = appIcons.getCoffeOneIcon(80, 80);
    private imageIcon imageCoffeTwo = appIcons.getCoffeTwoIcon(80, 80);
    private imageIcon imageCoffeApp = appIcons.getCoffeAppIcon(300, 300);

    private imageIcon EmailIcon = appIcons.getEmailIcon(25, 25);
    private imageIcon passwordIcon = appIcons.getPasswordIcon(25, 25);
    private imageIcon showPasswordIcon = appIcons.getShowPasswordIcon(25, 25);
    private imageIcon hidePasswordIcon = appIcons.getHidePasswordIcon(25, 25);

    public formLogin(loginView parentView, mainFrame parentFrame) {
        super();
        this.parentView = parentView;
        this.parentFrame = parentFrame;

        initsComponent();
    }

    private void refreshContent() {
        try {
            cardPanel.revalidate();
            cardPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initsComponent() {
        setPostion();
        setColor();
        setFont();
        handelShowIconPassword();
        handleExitApps();
        handelLoginAdmin();

        add(exitIcon);
        add(imageCoffeOne);
        add(imageCoffeTwo);
        add(imageCoffeApp);
        add(wrapperPanel);

        cardPanel.add(EmailIcon);
        cardPanel.add(passwordIcon);
        cardPanel.add(showPasswordIcon);
        cardPanel.add(hidePasswordIcon);

        wrapperPanel.add(cardPanel);
        wrapperPanel.add(shapeOne);
        wrapperPanel.add(shapeTwo);
        wrapperPanel.add(shapeThree);

        cardPanel.add(headerLabel);
        cardPanel.add(lineShape);
        cardPanel.add(EmailLabel);
        cardPanel.add(passwordLabel);
        cardPanel.add(EmailField);
        cardPanel.add(passwordField);
        cardPanel.add(buttonLogin);
    }

    private void setPostion() {
        wrapperPanel = new panelRounded(0, 0, 1080, 720, 0, 0);
        cardPanel = new panelRounded(90, 120, 450, 500, 0, 0);
        shapeOne = new panelRounded(700, -40, 380, 800, 400, 0);
        shapeTwo = new panelRounded(630, -40, 450, 800, 400, 0);
        shapeThree = new panelRounded(550, -40, 600, 800, 400, 0);
        lineShape = new panelRounded(165, 65, 120, 5, 3, 3);

        headerLabel = new textLabel("Sign In", 0, 10, 450, 60);
        EmailLabel = new textLabel("Email", 120, 130, 200, 40);
        passwordLabel = new textLabel("Password", 120, 230, 200, 40);
        warningEmailLabel = new textLabel("Email is Empty!", 80, 210, 300, 10);
        warningPasswordLabel = new textLabel("Password is Empty!", 80, 310, 300, 10);

        EmailField = new textField(80, 170, 300, 10);
        EmailField.setPlaceholder("Enter your Email");
        passwordField = new passwordField(80, 270, 300, 10);
        passwordField.setPlaceholder("Enter your Password");

        buttonLogin = new buttonCustom("Login", 85, 360, 300, 40, 15);

        exitIcon.setBounds(1005, 30, 30, 30);
        EmailIcon.setBounds(85, 135, 25, 25);
        passwordIcon.setBounds(85, 235, 25, 25);
        showPasswordIcon.setBounds(350, 273, 60, 60);
        hidePasswordIcon.setBounds(350, 273, 60, 60);
        imageCoffeApp.setBounds(700, 190, 300, 300);
        imageCoffeOne.setBounds(730, 380, 80, 80);
        imageCoffeTwo.setBounds(900, 350, 80, 80);
    }

    private void setColor() {
        wrapperPanel.setBackground(color.WHITE);
        cardPanel.setBackground(color.WHITE);
        shapeOne.setBackground(color.DARKGREEN);
        shapeTwo.setBackground(color.GREEN);
        shapeThree.setBackground(color.GREENLIGHT);
        setBackground(color.GREEN);
        lineShape.setBackground(color.DARKGREEN);

        headerLabel.setForeground(color.BLACK);
        EmailLabel.setForeground(color.BLACK);
        passwordLabel.setForeground(color.BLACK);
        warningEmailLabel.setForeground(color.RED);
        warningPasswordLabel.setForeground(color.RED);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));
        EmailLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 16f));
        passwordLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 16f));
        warningEmailLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        warningPasswordLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));

        headerLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    private void handelShowIconPassword() {
        final boolean[] isPasswordVisible = { false };

        cardPanel.add(hidePasswordIcon);
        cardPanel.add(showPasswordIcon);

        hidePasswordIcon.setBounds(345, 273, 25, 25);
        showPasswordIcon.setBounds(345, 273, 25, 25);
        showPasswordIcon.setVisible(false);

        hidePasswordIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPasswordVisible[0] = true;
                passwordField.setEchoChar((char) 0);
                passwordField.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 13f));

                hidePasswordIcon.setVisible(false);
                showPasswordIcon.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hidePasswordIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        showPasswordIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPasswordVisible[0] = false;
                passwordField.setEchoChar('•');
                passwordField.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 13f));

                showPasswordIcon.setVisible(false);
                hidePasswordIcon.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                showPasswordIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
    }

    private void handleExitApps() {
        exitIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentFrame.showNotificationPopUp(new popUpExit(parentFrame));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exitIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
    }

    private void handelLoginAdmin() {
        buttonLogin.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                String Email = EmailField.getText().trim();
                String password = String.valueOf(passwordField.getPassword()).trim();

                cardPanel.remove(warningEmailLabel);
                cardPanel.remove(warningPasswordLabel);

                String result = authLogin.validateLogin(Email, password);

                switch (result) {
                    case "EMAIL_PASSWORD_EMPTY":
                        cardPanel.add(warningEmailLabel);
                        warningEmailLabel.setText("Email is Empty!");
                        cardPanel.add(warningPasswordLabel);
                        warningPasswordLabel.setText("Password is Empty!");
                        break;
                    case "EMAIL_EMPTY":
                        cardPanel.add(warningEmailLabel);
                        warningEmailLabel.setText("Email is Empty!");
                        break;
                    case "PASSWORD_EMPTY":
                        cardPanel.add(warningPasswordLabel);
                        warningPasswordLabel.setText("Password is Empty!");
                        break;
                    case "INVALID_CREDENTIALS":
                        warningEmailLabel.setText("Email is Incorrect!");
                        cardPanel.add(warningPasswordLabel);
                        warningPasswordLabel.setText("Password is Incorrect!");
                        break;
                    case "SUCCESS":
                        EmailField.setText(null);
                        passwordField.setText(null);

                         System.out.println("=== Session Login ===");
                         System.out.println("ID Account: " + sessionLogin.get().getIdAccount());
                         System.out.println("ID Staff: " + sessionLogin.get().getIdStaff());
                         System.out.println("Email: " + sessionLogin.get().getEmail());
                         System.out.println("Password: " + sessionLogin.get().getPassword());
                         System.out.println("Jobdesk: " + sessionLogin.get().getJobdesk()); // Harus tampil!

                        int idStaff = sessionLogin.get().getIdStaff();
                        dataStaff staffData = loadDataStaff.getStaffById(idStaff);
                        sessionManager.setStaffData(staffData);
                        Role role = sessionLogin.getRole();
                        staffService.staffLogin(idStaff); 

                        parentView.loginSuccess(role);
                        break;
                }

                refreshContent();
            }
        });
    }

}
