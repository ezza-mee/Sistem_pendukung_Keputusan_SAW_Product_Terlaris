package com.main.views.dashboardAdmin.staff;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.models.entity.entityDataStaff;
import com.main.routes.dashboardAdminView;
import com.main.services.authDataStaff;

public class staffFormView extends contentPanel {

    private dashboardAdminView parentView;

    private textLabel headerLabel;

    private panelRounded contentPanel;

    private textLabel nameLabel, emailLabel, phoneLabel, jobdeskLabel, genderLabel, addressLabel;
    private textLabel nameEmptyLabel, emailEmptyLabel, phoneEmptyLabel, genderEmptyLabel, jobdeskEmptyLabel,
            addressEmptyLabel;

    private textField nameField, emailField, phoneField;

    private textArea addresField;

    private scrollPane scrollAddres;

    private comboBox<String> genderField;
    private comboBox<String> jobdeskField;

    private buttonCustom buttonBack, buttonReset, buttonSave;

    private authDataStaff insertStaff = new authDataStaff();

    private int staffIdToEdit = -1;
    private String oldEmail = "";
    private String oldPhoneNumber = "";

    public staffFormView(dashboardAdminView parentView) {
        super();
        this.parentView = parentView;
        initContent();
    }

    @Override
    public void initContent() {
        setPosition();
        setColor();
        setFont();
        handleButton();

        contentPanel.add(nameLabel);
        contentPanel.add(emailLabel);
        contentPanel.add(phoneLabel);
        contentPanel.add(addressLabel);
        contentPanel.add(jobdeskLabel);
        contentPanel.add(genderLabel);

        contentPanel.add(nameField);
        contentPanel.add(emailField);
        contentPanel.add(phoneField);
        contentPanel.add(scrollAddres);

        contentPanel.add(genderField);
        contentPanel.add(jobdeskField);

        contentPanel.add(buttonBack);
        contentPanel.add(buttonSave);
        contentPanel.add(buttonReset);

        add(contentPanel);
        add(headerLabel);

        setVisible(true);
    }

    private void setPosition() {
        headerLabel = new textLabel("Input Data Staff", 40, 0, 400, 80);
        contentPanel = new panelRounded(40, 80, 1050, 550, 10, 10);

        nameLabel = new textLabel("Name", 180, 30, 200, 80);
        emailLabel = new textLabel("Email", 180, 130, 200, 80);
        phoneLabel = new textLabel("Phone Number", 180, 230, 200, 80);
        genderLabel = new textLabel("Gender", 180, 330, 200, 80);
        jobdeskLabel = new textLabel("Jobdesk", 580, 30, 200, 80);
        addressLabel = new textLabel("Address", 580, 130, 200, 80);

        nameEmptyLabel = new textLabel("Name is Empty", 180, 90, 200, 80);
        emailEmptyLabel = new textLabel("Email is Empty", 180, 190, 200, 80);
        phoneEmptyLabel = new textLabel("Phone is Empty", 180, 290, 200, 80);
        genderEmptyLabel = new textLabel("Gender is Empty", 180, 390, 200, 80);
        jobdeskEmptyLabel = new textLabel("Jobdesk is Empty", 580, 90, 200, 80);
        addressEmptyLabel = new textLabel("Address is Empty", 580, 375, 200, 80);

        nameField = new textField(180, 85, 300, 10);
        emailField = new textField(180, 185, 300, 10);
        phoneField = new textField(180, 285, 300, 10);
        addresField = new textArea(580, 190, 320, 220, 10);

        String[] genderItems = { null, "Male", "Famale" };
        genderField = new comboBox<>(genderItems, 180, 385, 300, 30, 10);
        genderField.setPlaceholder("Select your Gender");

        String[] jobdeskItems = { null, "Barista", "Cashier", "Waiter", "Chef", "Assistant Chef",
                "Supplier", "Admin", "Manager", "Housekeeping",
                "Receptionist", "Courier", "Supervisor", "Technician",
                "Quality Control", "Customer Service" };
        jobdeskField = new comboBox<>(jobdeskItems, 580, 90, 300, 30, 10);
        jobdeskField.setPlaceholder("Select your Jobdesk");

        nameField.setPlaceholder("Enter your name");
        emailField.setPlaceholder("Enter your email");
        phoneField.setPlaceholder("Enter your phone number");
        addresField.setPlaceholder("Enter your address");

        scrollAddres = new scrollPane(addresField, 580, 180, 320, 220);

        buttonBack = new buttonCustom("Back", 180, 470, 100, 40, 10);
        buttonReset = new buttonCustom("Reset", 640, 470, 100, 40, 10);
        buttonSave = new buttonCustom("Save", 780, 470, 100, 40, 10);

    }

    private void setColor() {
        contentPanel.setBackground(color.WHITE);

        headerLabel.setForeground(color.DARKGREEN);
        nameLabel.setForeground(color.DARKGREEN);
        emailLabel.setForeground(color.DARKGREEN);
        phoneLabel.setForeground(color.DARKGREEN);
        genderLabel.setForeground(color.DARKGREEN);
        jobdeskLabel.setForeground(color.DARKGREEN);
        addressLabel.setForeground(color.DARKGREEN);

        nameEmptyLabel.setForeground(color.RED);
        emailEmptyLabel.setForeground(color.RED);
        phoneEmptyLabel.setForeground(color.RED);
        genderEmptyLabel.setForeground(color.RED);
        jobdeskEmptyLabel.setForeground(color.RED);
        addressEmptyLabel.setForeground(color.RED);

        scrollAddres.setBackground(color.WHITE);
        scrollAddres.getViewport().setOpaque(false);

        genderField.setBackground(color.LIGHTGREY);
        jobdeskField.setBackground(color.LIGHTGREY);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));

        nameLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        emailLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        phoneLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        genderLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        jobdeskLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        addressLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));

        nameEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        emailEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        phoneEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        genderEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        jobdeskEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        addressEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
    }

    public void setFormData(entityDataStaff dataStaff) {
        nameField.setText(dataStaff.getName());
        emailField.setText(dataStaff.getEmail());
        phoneField.setText(dataStaff.getPhoneNumber());
        genderField.setSelectedItem(dataStaff.getGender());
        jobdeskField.setSelectedItem(dataStaff.getJobdesk());
        addresField.setText(dataStaff.getAddress());

        staffIdToEdit = dataStaff.getIdStaff();
        oldEmail = dataStaff.getEmail();
        oldPhoneNumber = dataStaff.getPhoneNumber();
    }

    private void handleButton() {
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showDashboardStaff();
            }
        });

        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                nameField.setText(null);
                emailField.setText(null);
                phoneField.setText(null);
                genderField.setSelectedItem(null);
                jobdeskField.setSelectedItem(null);
                addresField.setText(null);

            }
        });

        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String phoneNumber = phoneField.getText().trim();
                    String gender = (String) genderField.getSelectedItem();
                    String jobdesk = (String) jobdeskField.getSelectedItem();
                    String address = addresField.getText().trim();

                    System.out.println("Save button clicked.");
                    System.out.println("Mode edit? " + (staffIdToEdit != -1));
                    System.out.println("Old email: " + oldEmail + ", New email: " + email);
                    System.out.println("Old phone: " + oldPhoneNumber + ", New phone: " + phoneNumber);

                    contentPanel.remove(nameEmptyLabel);
                    contentPanel.remove(emailEmptyLabel);
                    contentPanel.remove(phoneEmptyLabel);
                    contentPanel.remove(genderEmptyLabel);
                    contentPanel.remove(jobdeskEmptyLabel);
                    contentPanel.remove(addressEmptyLabel);

                    String validation = insertStaff.validateStaffInput(name, email, phoneNumber, gender, jobdesk,
                            address);

                    switch (validation) {
                        case "ALL_FIELDS_EMPTY":
                            contentPanel.add(nameEmptyLabel);
                            contentPanel.add(emailEmptyLabel);
                            contentPanel.add(phoneEmptyLabel);
                            contentPanel.add(genderEmptyLabel);
                            contentPanel.add(jobdeskEmptyLabel);
                            contentPanel.add(addressEmptyLabel);
                            break;
                        case "NAME_EMPTY":
                            contentPanel.add(nameEmptyLabel);
                            break;
                        case "EMAIL_EMPTY":
                            contentPanel.add(emailEmptyLabel);
                            break;
                        case "PHONE_EMPTY":
                            contentPanel.add(phoneEmptyLabel);
                            break;
                        case "GENDER_EMPTY":
                            contentPanel.add(genderEmptyLabel);
                            break;
                        case "JOBDESK_EMPTY":
                            contentPanel.add(jobdeskEmptyLabel);
                            break;
                        case "ADDRESS_EMPTY":
                            contentPanel.add(addressEmptyLabel);
                            break;
                        case "VALID":
                            String uniquenessCheck = authDataStaff.validateStaffDataExistence(email, phoneNumber,
                                    oldEmail, oldPhoneNumber, staffIdToEdit);
                            if (!uniquenessCheck.equals("VALID")) {
                                switch (uniquenessCheck) {
                                    case "EMAIL_ALREADY_EXISTS":
                                        parentView.showFailedPopUp("Email is already used.");
                                        break;
                                    case "PHONE_ALREADY_EXISTS":
                                        parentView.showFailedPopUp("Phone number is already used.");
                                        break;
                                    case "PHONE_TOO_LONG":
                                        parentView.showFailedPopUp("Phone number cannot exceed 13 digits.");
                                        break;
                                    default:
                                        parentView.showFailedPopUp("Unknown validation error.");
                                        break;
                                }
                                return;
                            }

                            boolean success = false;
                            boolean isEdit = (staffIdToEdit != -1);

                            if (jobdesk.equalsIgnoreCase("Admin") || jobdesk.equalsIgnoreCase("Cashier")
                                    || jobdesk.equalsIgnoreCase("Supplier")) {
                                // Untuk Cashier & Supplier: arahkan ke form pembuatan akun
                                parentView.showFormAccountStaff(name, email, phoneNumber, gender, jobdesk,
                                        address, isEdit, staffIdToEdit);
                            } else {
                                if (staffIdToEdit == -1) {
                                    // INSERT DATA STAFF tanpa akun
                                    success = authDataStaff.insertDataStaff(name, email, phoneNumber, gender, jobdesk,
                                            address);
                                    if (success) {
                                        parentView.showSuccessPopUp("Data Staff Successfully Saved");
                                        parentView.showDashboardStaff();
                                    } else {
                                        parentView.showFailedPopUp("Failed to Save Data Staff");
                                    }
                                } else {
                                    // UPDATE DATA STAFF tanpa akun
                                    success = authDataStaff.updateDataStaff(staffIdToEdit, name, email,
                                            phoneNumber, gender, jobdesk, address);
                                    if (success) {
                                        parentView.showSuccessPopUp("Data Staff Successfully Updated");
                                        parentView.showDashboardStaff();
                                        staffIdToEdit = -1; // reset ID agar tidak update terus-menerus
                                    } else {
                                        parentView.showFailedPopUp("Failed to Update Data Staff");
                                    }
                                }
                            }
                            break;
                    }
                    contentPanel.revalidate();
                    contentPanel.repaint();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
