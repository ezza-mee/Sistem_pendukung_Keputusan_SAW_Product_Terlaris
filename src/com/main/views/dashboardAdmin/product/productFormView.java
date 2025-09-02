package com.main.views.dashboardAdmin.product;

import com.main.components.panelApps.contentPanel;
import com.main.models.entity.dataProduct;
import com.main.models.entity.listCompositionData;
import com.main.models.product.loadCompositionProduct;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;
import com.main.services.authDataProduct;
import com.main.views.popUp.popUpConfrim;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.main.components.*;

public class productFormView extends contentPanel {

    private mainFrame parentApp;

    private dashboardAdminView parentView;

    private panelRounded contentPanel;

    private textLabel headerLabel, nameProductLabel, priceProductLabel, categoryProductLabel, imageProductLabel,
            descriptionProductLabel;

    private textLabel productEmptyLabel, priceEmptyLabel, categoryEmptyLabel, descriptionEmptyLabel;

    private textField nameProductField, priceProductField;
    private textField imagePathField;
    private textArea descriptionProductField;

    private buttonInputImage buttonInputImage;
    private textLabel pathImageLabel;

    private comboBox<String> categoryProductField;

    private buttonCustom buttonBack, buttonReset, buttonSave;

    private scrollPane scrollDescription;

    private authDataProduct authData = new authDataProduct();

    private int productIdToEdit = -1;

    public productFormView(mainFrame parentApp, dashboardAdminView parentView) {
        super();
        this.parentApp = parentApp;
        this.parentView = parentView;
        initContent();
    }

    @Override
    public void initContent() {
        setPosition();
        setColor();
        setFont();
        handleButton();

        contentPanel.add(nameProductLabel);
        contentPanel.add(priceProductLabel);
        contentPanel.add(categoryProductLabel);
        contentPanel.add(descriptionProductLabel);
        contentPanel.add(imageProductLabel);

        contentPanel.add(nameProductField);
        contentPanel.add(priceProductField);
        contentPanel.add(categoryProductField);
        contentPanel.add(scrollDescription);

        contentPanel.add(buttonInputImage);
        contentPanel.add(imagePathField);
        contentPanel.add(pathImageLabel);

        contentPanel.add(buttonBack);
        contentPanel.add(buttonReset);
        contentPanel.add(buttonSave);

        add(headerLabel);
        add(contentPanel);

        setVisible(true);
    }

    private void setPosition() {
        contentPanel = new panelRounded(40, 80, 1050, 550, 10, 10);

        headerLabel = new textLabel("Input Data Product", 40, 0, 300, 80);
        nameProductLabel = new textLabel("Name Product", 180, 30, 300, 80);
        priceProductLabel = new textLabel("Price Product", 180, 130, 300, 80);
        categoryProductLabel = new textLabel("Category Product", 580, 130, 300, 80);
        imageProductLabel = new textLabel("Image Product", 580, 30, 300, 80);
        descriptionProductLabel = new textLabel("Description Product", 180, 230, 300, 80);
        pathImageLabel = new textLabel("path : ", 580, 90, 300, 80);

        productEmptyLabel = new textLabel("Name Product is Empty", 180, 90, 200, 80);
        priceEmptyLabel = new textLabel("Price Product is Empty", 180, 190, 200, 80);
        categoryEmptyLabel = new textLabel("Category Product is Empty", 580, 190, 200, 80);
        descriptionEmptyLabel = new textLabel("Description Product is Empty", 180, 395, 200, 80);

        nameProductField = new textField(180, 85, 300, 10);
        priceProductField = new textField(180, 185, 300, 10);
        descriptionProductField = new textArea(180, 285, 700, 140, 10);
        imagePathField = new textField(580, 85, 300, 10);

        nameProductField.setPlaceholder("Enter Name Product");
        priceProductField.setPlaceholder("Enter Price Product");
        descriptionProductField.setPlaceholder("Enter Description Product");

        buttonInputImage = new buttonInputImage("Selected Image", 580, 85, 300, 30, 10, imagePathField, pathImageLabel);

        String[] catergoryItems = { null, "Food", "Coffee", "Drink", "Snack" };
        categoryProductField = new comboBox<>(catergoryItems, 580, 185, 300, 30, 10);
        categoryProductField.setPlaceholder("Select Category Product");

        buttonBack = new buttonCustom("Back", 180, 470, 100, 40, 10);
        buttonReset = new buttonCustom("Reset", 640, 470, 100, 40, 10);
        buttonSave = new buttonCustom("Save", 780, 470, 100, 40, 10);

        scrollDescription = new scrollPane(descriptionProductField, 180, 285, 700, 140);
    }

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        contentPanel.setBackground(color.WHITE);

        scrollDescription.setBackground(color.WHITE);
        scrollDescription.getViewport().setOpaque(false);

        nameProductLabel.setForeground(color.BLACK);
        priceProductLabel.setForeground(color.BLACK);
        categoryProductLabel.setForeground(color.BLACK);
        descriptionProductLabel.setForeground(color.BLACK);
        pathImageLabel.setForeground(color.BLACK);

        productEmptyLabel.setForeground(color.RED);
        priceEmptyLabel.setForeground(color.RED);
        categoryEmptyLabel.setForeground(color.RED);
        descriptionEmptyLabel.setForeground(color.RED);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));

        nameProductLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        priceProductLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        categoryProductLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        imageProductLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        descriptionProductLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));

        productEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        priceEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        categoryEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        descriptionEmptyLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
        pathImageLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 10f));
    }

    public void setFormProduct(dataProduct dataProduct) {
        nameProductField.setText(dataProduct.getNameProduct());
        String stringPrice = Integer.toString(dataProduct.getPrice());
        priceProductField.setText(stringPrice);
        categoryProductField.setSelectedItem(dataProduct.getCategory());
        descriptionProductField.setText(dataProduct.getDescription());

        byte[] imageBytes = dataProduct.getImageData();
        if (imageBytes != null && imageBytes.length > 0) {
            try {
                String fileExtension = (imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8) ? ".jpg" : ".png";
                File tempFile = File.createTempFile("product_image_", fileExtension);

                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(imageBytes);
                }

                String imageFilePath = tempFile.getAbsolutePath();
                imagePathField.setText(imageFilePath);
                pathImageLabel.setText("<html><body style='width:200px;'>Path : <br>" + imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
                imagePathField.setText("Error loading image");
                pathImageLabel.setText("Error loading image");
            }
        } else {
            imagePathField.setText("No image available");
            pathImageLabel.setText("No image available");
        }

        productIdToEdit = dataProduct.getIdProduct();
    }

    private void handleButton() {
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showDashboardProduct();
            }
        });

        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                nameProductField.setText(null);
                priceProductField.setText(null);
                categoryProductField.setSelectedItem(null);
                descriptionProductField.setText(null);
            }
        });

        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                try {
                    System.out.println("productIdToEdit = " + productIdToEdit);

                    String imageProduct = imagePathField.getText().trim();
                    String nameProduct = nameProductField.getText().trim();
                    String stringPrice = priceProductField.getText().trim();
                    String category = (String) categoryProductField.getSelectedItem();
                    String description = descriptionProductField.getText().trim();

                    contentPanel.remove(productEmptyLabel);
                    contentPanel.remove(priceEmptyLabel);
                    contentPanel.remove(categoryEmptyLabel);
                    contentPanel.remove(descriptionEmptyLabel);

                    String validation = authData.validateProductInput(imageProduct, nameProduct, category, stringPrice,
                            description);

                    switch (validation) {
                        case "ALL_FIELDS_EMPTY":
                            pathImageLabel.setText("Image Product is Empty");
                            pathImageLabel.setForeground(color.RED);
                            contentPanel.add(productEmptyLabel);
                            contentPanel.add(priceEmptyLabel);
                            contentPanel.add(categoryEmptyLabel);
                            contentPanel.add(descriptionEmptyLabel);
                            break;
                        case "IMAGE_PRODUCT_EMPTY":
                            pathImageLabel.setText("Image Product is Empty");
                            pathImageLabel.setForeground(color.RED);
                            break;
                        case "NAME_PRODUCT_EMPTY":
                            contentPanel.add(productEmptyLabel);
                            break;
                        case "PRICE_PRODUCT_EMPTY":
                            contentPanel.add(priceEmptyLabel);
                            break;
                        case "CATEGORY_PRODUCT_EMPTY":
                            contentPanel.add(categoryEmptyLabel);
                            break;
                        case "DESCRIPTION_PRODUCT_EMPTY":
                            contentPanel.add(descriptionEmptyLabel);
                            break;
                        case "VALID":
                            pathImageLabel.setForeground(color.BLACK);
                            int price = Integer.parseInt(stringPrice);
                            if (productIdToEdit == -1) {
                                parentView.showFormCompositionProduct(productIdToEdit, imageProduct, nameProduct, price,
                                        category, description, null);
                            } else {
                                popUpConfrim messagePopUp = parentView
                                        .showConfrimPopUp("Do you want to update the composition as well?");

                                messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                                    @Override
                                    public void actionPerformed(java.awt.event.ActionEvent ae) {
                                        parentApp.hideGlassNotificationPanel();
                                        parentView.setCompositionModified(true);
                                        List<listCompositionData> currentComposition = loadCompositionProduct
                                                .getDataCompositonByProduct(productIdToEdit);

                                        parentView.showFormCompositionProduct(productIdToEdit, imageProduct,
                                                nameProduct, price, category,
                                                description, currentComposition);
                                        System.out.println("idProduct : " + productIdToEdit);
                                    }
                                });

                                messagePopUp.getButtonCancel().addActionListener(new java.awt.event.ActionListener() {
                                    @Override
                                    public void actionPerformed(java.awt.event.ActionEvent ae) {
                                        parentApp.hideGlassNotificationPanel();
                                        authDataProduct.updateDataProduct(productIdToEdit, imageProduct, nameProduct,
                                                price, category, description);
                                        productIdToEdit = -1;
                                        parentView.showSuccessPopUp("Data Product Successfully Saved");
                                        parentView.showDashboardProduct();
                                    }
                                });

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
