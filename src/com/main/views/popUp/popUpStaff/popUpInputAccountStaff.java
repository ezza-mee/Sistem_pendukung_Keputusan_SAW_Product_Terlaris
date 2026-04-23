package com.main.views.popUp.popUpStaff;

import javax.swing.JLabel;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.main.components.*;
import com.main.components.panelApps.popUpPanel;
import com.main.models.entity.accountDataStaff;
import com.main.models.entity.dataStaff;
import com.main.models.entity.entityDataStaff;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;
import com.main.services.authDataStaff;

public class popUpInputAccountStaff extends popUpPanel {

    private mainFrame parentFrame;

    private dashboardAdminView parentView;

    private textLabel headerLabel, emailLabel, passwordLabel, confirmPasswordLabel;

    private textLabel emailEmptyLabel, passwordEmptyLabel, confirmPasswordEmptyLabel;

    private textField emailField;

    private passwordField passwordField, confirmPasswordField;

    private buttonCustom buttonSave, buttonCancel;

    private authDataStaff insertStaff = new authDataStaff();

    private boolean isEditMode;
    private int staffIdToEdit = -1;
    private int idStaff;
    private String oldEmail = "";
    private String oldPhoneNumber = "";
    private String name, email, phoneNumber, gender, jobdesk, address;

    private appIcons appIcons = new appIcons();

    private imageIcon showPasswordIcon = appIcons.getShowPasswordIcon(25, 25);
    private imageIcon hidePasswordIcon = appIcons.getHidePasswordIcon(25, 25);
    private imageIcon backIcon = appIcons.getBackIconWhite(20, 20);
    private imageIcon saveIcon = appIcons.getSaveIconWhite(20, 20);

    private imageIcon showConfrimPasswordIcon = appIcons.getShowPasswordIcon(25, 25);
    private imageIcon hideConfrimPasswordIcon = appIcons.getHidePasswordIcon(25, 25);

    public popUpInputAccountStaff(mainFrame parentFrame, dashboardAdminView parentView,
            String name,
            String email,
            String phoneNumber,
            String gender,
            String jobdesk,
            String address, boolean isEdit, int idStaff) {
        super();
        this.parentFrame = parentFrame;
        this.parentView = parentView;
        this.isEditMode = isEdit;
        this.idStaff = idStaff;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.jobdesk = jobdesk;
        this.address = address;

        setSize(500, 500);
        initComponent();
    }

    private void initComponent() {
        setPosition();
        setColor();
        setFont();
        handleButtonApps();
        handelShowIconPassword();

        add(headerLabel);
        add(emailLabel);
        add(passwordLabel);
        add(confirmPasswordLabel);

        add(emailField);
        add(passwordField);
        add(confirmPasswordField);

        add(buttonSave);
        add(buttonCancel);

        setVisible(true);
    }

    private void setPosition() {
        headerLabel = new textLabel("Input Data Akun Staff", 0, 30, 500, 50);

        emailLabel = new textLabel("Email", 70, 100, 300, 40);
        passwordLabel = new textLabel("Password", 70, 190, 300, 40);
        confirmPasswordLabel = new textLabel("Konfirmasi Password", 70, 280, 300, 40);

        emailEmptyLabel = new textLabel("Email is Empty", 70, 155, 300, 40);
        passwordEmptyLabel = new textLabel("Password is Empty", 70, 245, 300, 40);
        confirmPasswordEmptyLabel = new textLabel("Konfirmasi Password is Empty", 70, 335, 300, 40);

        emailField = new textField(70, 135, 350, 10);
        passwordField = new passwordField(70, 225, 350, 10);
        confirmPasswordField = new passwordField(70, 315, 350, 10);

        buttonCancel = new buttonCustom("    " + "Batal", 70, 410, 150, 40, 10);
        buttonSave = new buttonCustom("    " + "Simpan", 270, 410, 150, 40, 10);

        buttonCancel.setIcon(backIcon);
        buttonSave.setIcon(saveIcon);

        showPasswordIcon.setBounds(390, 210, 60, 60);
        hidePasswordIcon.setBounds(390, 210, 60, 60);

        showConfrimPasswordIcon.setBounds(390, 300, 60, 60);
        hideConfrimPasswordIcon.setBounds(390, 300, 60, 60);

        this.emailField.setText(email);
        emailField.setEditable(false);

    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        emailLabel.setForeground(color.BLACK);
        passwordLabel.setForeground(color.BLACK);
        confirmPasswordLabel.setForeground(color.BLACK);

        emailEmptyLabel.setForeground(color.RED);
        passwordEmptyLabel.setForeground(color.RED);
        confirmPasswordEmptyLabel.setForeground(color.RED);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 20f));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);

        emailLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        passwordLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        passwordField.setPlaceholder("Masukan Password");
        confirmPasswordLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        confirmPasswordField.setPlaceholder("Masukan Konfirmasi Password");

        emailEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        passwordEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        confirmPasswordEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
    }

    public void setFormAccountData(accountDataStaff accountData, entityDataStaff dataStaff) {
        if (accountData != null && dataStaff != null) {
            passwordField.setText(accountData.getPassword());
            confirmPasswordField.setText(accountData.getPassword());

            staffIdToEdit = dataStaff.getIdStaff();
            oldEmail = accountData.getEmail();
            oldPhoneNumber = dataStaff.getPhoneNumber();

            System.out.println("Checking uniqueness with:");
            System.out.println("email = " + email + ", oldEmail = " + oldEmail);
            System.out.println("phone = " + phoneNumber + ", oldPhone = " + oldPhoneNumber);
            System.out.println("staffIdToEdit = " + staffIdToEdit);
        }
    }

    private void handelShowIconPassword() {
        final boolean[] isPasswordVisible = { false };

        add(hidePasswordIcon);
        add(showPasswordIcon);
        add(hideConfrimPasswordIcon);
        add(showConfrimPasswordIcon);

        hidePasswordIcon.setBounds(390, 210, 60, 60);
        showPasswordIcon.setBounds(390, 210, 60, 60);
        showPasswordIcon.setVisible(false);

        hideConfrimPasswordIcon.setBounds(390, 300, 60, 60);
        showConfrimPasswordIcon.setBounds(390, 300, 60, 60);
        showConfrimPasswordIcon.setVisible(false);

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

        hideConfrimPasswordIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPasswordVisible[0] = true;
                confirmPasswordField.setEchoChar((char) 0);
                confirmPasswordField.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 13f));

                hideConfrimPasswordIcon.setVisible(false);
                showConfrimPasswordIcon.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hideConfrimPasswordIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        showConfrimPasswordIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPasswordVisible[0] = false;
                confirmPasswordField.setEchoChar('•');
                confirmPasswordField.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 13f));

                showConfrimPasswordIcon.setVisible(false);
                hideConfrimPasswordIcon.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                showConfrimPasswordIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
    }

    private void handleButtonApps() {

        buttonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentFrame.hideGlassNotificationPanel();
            }
        });

        buttonSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String emailAccount = emailField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                System.out.println("Save button clicked.");
                System.out.println("Mode edit? " + (staffIdToEdit != -1));
                System.out.println("Old email: " + oldEmail + ", New email: " + email);
                System.out.println("Old phone: " + oldPhoneNumber + ", New phone: " + phoneNumber);

                String validationResult = insertStaff.validateAccountInput(emailAccount, password, confirmPassword);

                remove(emailEmptyLabel);
                remove(passwordEmptyLabel);
                remove(confirmPasswordEmptyLabel);

                switch (validationResult) {
                    case "ACCOUNT_EMAIL_PASSWORD_EMPTY":
                        add(emailEmptyLabel);
                        add(passwordEmptyLabel);
                        break;
                    case "ACCOUNT_EMAIL_EMPTY":
                        add(emailEmptyLabel);
                        break;
                    case "ACCOUNT_PASSWORD_EMPTY":
                        add(passwordEmptyLabel);
                        break;
                    case "CONFIRM_PASSWORD_EMPTY":
                        add(confirmPasswordEmptyLabel);
                        break;
                    case "PASSWORD_MISMATCH":
                        add(confirmPasswordEmptyLabel);
                        break;
                }

                if (!validationResult.equals("VALID") || confirmPassword.isEmpty()) {
                    revalidate();
                    repaint();
                    return;
                }

                confirmPasswordEmptyLabel.setText("Password dan konfirmasi password tidak sama");
                String uniquenessCheck = authDataStaff.validateStaffDataExistence(email, phoneNumber, oldEmail,
                        oldPhoneNumber, staffIdToEdit);
                if (!uniquenessCheck.equals("VALID")) {
                    switch (uniquenessCheck) {
                        case "EMAIL_ALREADY_EXISTS":
                            parentView.showFailedPopUp("Email sudah digunakan.");
                            break;
                        case "PHONE_ALREADY_EXISTS":
                            parentView.showFailedPopUp("Nomor telpon sudah digunakan.");
                            break;
                        case "PHONE_TOO_LONG":
                            parentView.showFailedPopUp("Nomor telpon perlu 13 digits.");
                            break;
                        default:
                            parentView.showFailedPopUp("Nomor telpon gagal.");
                            break;
                    }
                    return;
                }

                boolean result;
                if (isEditMode) {
                    // UPDATE
                    result = authDataStaff.updateStaffWithAccount(idStaff, name, email, phoneNumber, gender, jobdesk,
                            address, emailAccount, password);
                } else {
                    // INSERT
                    result = authDataStaff.insertStaffWithAccount(
                            name, email, phoneNumber, gender, jobdesk, address, emailAccount, password);
                }

                if (result) {
                    parentFrame.hideGlassNotificationPanel();
                    parentView.showDashboardStaff();
                    parentView.showSuccessPopUp(isEditMode ? "Data dan akun staff berhasil di ubah"
                            : "Data dan akun staff berhasil disimpan");
                } else {
                    parentFrame.hideGlassNotificationPanel();
                    parentView.showFailedPopUp(
                            "Gagal " + (isEditMode ? "Ubah" : "Simpan") + " Data and Akun Staff");
                    System.out.println("isEditMode: " + isEditMode);
                    System.out.println("idStaff: " + idStaff);
                    System.out.println("jobdesk : " + jobdesk);
                }

                revalidate();
                repaint();
            }
        });
    }

}
