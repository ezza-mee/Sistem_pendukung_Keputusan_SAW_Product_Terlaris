package com.main.views.dashboardStaff.transaction;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.Dimension;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.controller.searchableView;
import com.main.models.entity.dataProduct;
import com.main.models.entity.listTransactionProduct;
import com.main.models.product.loadDataProduct;
import com.main.routes.dashboardStaffView;
import com.main.services.authDataProduct;

public class transactionFormView extends contentPanel implements searchableView {

    private dashboardStaffView parentView;

    private textLabel headerLabel, listProductLabel, listTransactionLabel, subTotalLabel, valueSubTotal;

    private panelRounded headerPanel, listTransactionPanel, listProductPanel, bottomPanel, parentListTransactionPanel;

    private scrollPane scrollListProduct, scrollListTransaction;

    private buttonCustom buttonBack, buttonPayment;

    private Map<Integer, panelRounded> addedProductCards = new HashMap<>();
    private Map<Integer, textField> productQuantities = new HashMap<>();
    private Map<Integer, dataProduct> productList = new HashMap<>();

    private appIcons appIcons = new appIcons();
    private imageIcon iconAdd = appIcons.getAddIconWhite(20, 20);
    private imageIcon iconDetail = appIcons.getDetailIconWhite(20, 20);
    private imageIcon iconMinus = appIcons.getMinusIconWhite(20, 20);

    public transactionFormView(dashboardStaffView parentView) {
        super();
        this.parentView = parentView;
        initContent();
    }

    @Override
    public void initContent() {
        setLayout();
        setColor();
        setFont();
        setAction();

        headerPanel.add(listProductLabel);
        bottomPanel.add(buttonBack);

        listTransactionPanel.add(listTransactionLabel);
        listTransactionPanel.add(scrollListTransaction);
        listTransactionPanel.add(subTotalLabel);
        listTransactionPanel.add(valueSubTotal);
        listTransactionPanel.add(buttonPayment);

        add(headerLabel);
        add(headerPanel);
        add(bottomPanel);
        add(scrollListProduct);
        add(listTransactionPanel);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Input Transaction", 40, 0, 400, 80);
        headerPanel = new panelRounded(40, 80, 600, 80, 10, 10);

        listProductLabel = new textLabel("List Product", 450, 20, 300, 40);
        listProductPanel = new panelRounded(40, 160, 620, 400, 0, 0);
        listProductPanel.setLayout(new BoxLayout(listProductPanel, BoxLayout.Y_AXIS));
        scrollListProduct = new scrollPane(listProductPanel, 0, 0, getWidth(), getHeight());
        scrollListProduct.setBounds(40, 160, 620, 400);

        listTransactionPanel = new panelRounded(685, 80, 400, 550, 10, 10);
        listTransactionLabel = new textLabel("List Order", 30, 20, 200, 40);
        subTotalLabel = new textLabel("SubTotal : ", 20, 445, 300, 40);
        valueSubTotal = new textLabel("", 300, 445, 300, 40);
        parentListTransactionPanel = new panelRounded(0, 80, 400, 360, 0, 0);
        parentListTransactionPanel.setLayout(new BoxLayout(parentListTransactionPanel, BoxLayout.Y_AXIS));

        scrollListTransaction = new scrollPane(parentListTransactionPanel, 0, 0, getWidth(), getHeight());
        scrollListTransaction.setBounds(0, 80, 400, 360);

        bottomPanel = new panelRounded(40, 550, 600, 80, 10, 10);

        buttonBack = new buttonCustom("Back", 20, 20, 100, 40, 10);
        buttonPayment = new buttonCustom("Continue to Payment", 25, 490, 350, 40, 10);

    }

    private void setColor() {
        headerPanel.setBackground(color.WHITE);
        listTransactionPanel.setBackground(color.WHITE);
        listProductPanel.setBackground(color.DARKGREY);
        parentListTransactionPanel.setBackground(color.WHITE);
        bottomPanel.setBackground(color.WHITE);

        headerLabel.setForeground(color.BLACK);
        listProductLabel.setForeground(color.BLACK);
        listTransactionLabel.setForeground(color.BLACK);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 30f));

        listProductLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));
        listTransactionLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 18f));

        subTotalLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 14f));
        valueSubTotal.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 14f));

    }

    private void updateTotalSubTotal() {
        int total = 0;
        for (Map.Entry<Integer, textField> entry : productQuantities.entrySet()) {
            int productId = entry.getKey();
            int qty = Integer.parseInt(entry.getValue().getText());

            // Pastikan produk ada sebelum ambil harga
            if (productList.containsKey(productId)) {
                int price = productList.get(productId).getPrice();
                total += qty * price;
            }
        }

        valueSubTotal.setText("Rp. " + total);
    }

    public void loadAllProductCards() {
        listProductPanel.removeAll();

        ArrayList<dataProduct> list = loadDataProduct.getAllProducts();
        for (dataProduct product : list) {
            loadDataProductInCard(product);
        }

        listProductPanel.revalidate();
        listProductPanel.repaint();
    }

    public void filterDataByKeyword(String keyword) {
        listProductPanel.removeAll();

        ArrayList<dataProduct> productList = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            productList = loadDataProduct.getAllProducts();
        } else {
            productList = authDataProduct.searchProductByKeyword(keyword);
        }

        for (dataProduct product : productList) {
            loadDataProductInCard(product);
        }

        listProductPanel.revalidate();
        listProductPanel.repaint();
    }

    private void loadDataProductInCard(dataProduct product) {
        panelRounded cardPanel = new panelRounded();
        Dimension cardSize = new Dimension(600, 100);
        cardPanel.setPreferredSize(cardSize);
        cardPanel.setMaximumSize(cardSize);
        cardPanel.setMinimumSize(cardSize);
        cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.setLayout(null);
        cardPanel.setBackground(color.WHITE);

        byte[] imageData = product.getImageData();
        ImageIcon icon = new ImageIcon(imageData);
        Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(20, 10, 80, 80);
        cardPanel.add(imageLabel);

        textLabel nameLabel = new textLabel(product.getNameProduct(), 130, 20, 400, 25);
        nameLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 18f));
        cardPanel.add(nameLabel);

        textLabel priceLabel = new textLabel("Rp. " + product.getPrice(), 360, 45, 200, 20);
        priceLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 14f));
        cardPanel.add(priceLabel);

        textLabel statusLabel = new textLabel(product.getStatus(), 130, 45, 200, 20);
        statusLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 14f));
        cardPanel.add(statusLabel);

        if (product.getStatus().equalsIgnoreCase("Out Of Stock")) {
            statusLabel.setForeground(color.RED);
        } else {
            statusLabel.setForeground(color.GREEN);
        }

        textLabel descriptionLabel = new textLabel(product.getDescription(), 130, 65, 300, 20);
        descriptionLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.REGULAR, 10f));
        cardPanel.add(descriptionLabel);

        buttonCustom buttonDetail = new buttonCustom("", 470, 30, 40, 40, 10);
        buttonCustom buttonAdd = new buttonCustom("", 530, 30, 40, 40, 10);

        buttonDetail.setIcon(iconDetail);
        buttonAdd.setIcon(iconAdd);

        cardPanel.add(buttonDetail);
        cardPanel.add(buttonAdd);

        Component padding = Box.createRigidArea(new Dimension(0, 20));

        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {

                if (product.getStatus().equalsIgnoreCase("Out Of Stock")) {
                    parentView.showFailedPopUp("Product is sold out.");
                    return;
                }

                int productId = product.getIdProduct();

                if (addedProductCards.containsKey(productId)) {
                    textField qtyField = productQuantities.get(productId);
                    int currentQty = Integer.parseInt(qtyField.getText());
                    qtyField.setText(String.valueOf(currentQty + 1));
                } else {
                    panelRounded cardPanel = new panelRounded();
                    Dimension cardSize = new Dimension(400, 80);
                    cardPanel.setPreferredSize(cardSize);
                    cardPanel.setMaximumSize(cardSize);
                    cardPanel.setMinimumSize(cardSize);
                    cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    cardPanel.setBackground(color.WHITE);
                    cardPanel.setLayout(null);

                    textLabel nameLabel = new textLabel(product.getNameProduct(), 20, 10, 200, 20);
                    nameLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
                    nameLabel.setForeground(color.BLACK);
                    cardPanel.add(nameLabel);

                    textField quantityField = new textField(280, 20, 50, 10);
                    quantityField.setSize(50, 40);
                    quantityField.setText("1");
                    quantityField.setHorizontalAlignment(JLabel.CENTER);
                    cardPanel.add(quantityField);

                    quantityField.addKeyListener(new java.awt.event.KeyAdapter() {
                        @Override
                        public void keyReleased(java.awt.event.KeyEvent e) {
                            String stringQuantity = quantityField.getText().trim();

                            if (!stringQuantity.matches("\\d+")) {
                                return;
                            }

                            int quantity = Integer.parseInt(stringQuantity);

                            if (quantity <= 0) {
                                parentListTransactionPanel.remove(cardPanel);
                                parentListTransactionPanel.remove(padding);
                                addedProductCards.remove(productId);
                                productQuantities.remove(productId);
                                productList.remove(productId);
                                parentListTransactionPanel.revalidate();
                                parentListTransactionPanel.repaint();
                            } else {
                                quantityField.setText(String.valueOf(quantity));
                            }
                            updateTotalSubTotal();
                        }
                    });

                    int price = product.getPrice();
                    textLabel priceLabel = new textLabel("Rp. " + price, 20, 45, 200, 20);
                    priceLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 14f));
                    priceLabel.setForeground(color.BLACK);
                    cardPanel.add(priceLabel);

                    buttonCustom buttonReduceAmount = new buttonCustom("", 230, 20, 40, 40, 10);
                    buttonReduceAmount.setIcon(iconMinus);
                    cardPanel.add(buttonReduceAmount);

                    buttonCustom buttonAddAmount = new buttonCustom("", 340, 20, 40, 40, 10);
                    buttonAddAmount.setIcon(iconAdd);
                    cardPanel.add(buttonAddAmount);

                    Component padding = Box.createRigidArea(new Dimension(0, 10));

                    // ➕ Tambah Quantity
                    buttonAddAmount.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            int qty = Integer.parseInt(quantityField.getText()) + 1;
                            quantityField.setText(String.valueOf(qty));
                            updateTotalSubTotal();
                        }
                    });

                    // ➖ Kurangi Quantity
                    buttonReduceAmount.addActionListener(new java.awt.event.ActionListener() {
                        @Override
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            int qty = Integer.parseInt(quantityField.getText()) - 1;

                            if (qty <= 0) {
                                parentListTransactionPanel.remove(cardPanel);
                                parentListTransactionPanel.remove(padding);
                                addedProductCards.remove(productId);
                                productQuantities.remove(productId);
                                productList.remove(productId);
                                parentListTransactionPanel.revalidate();
                                parentListTransactionPanel.repaint();
                            } else {
                                quantityField.setText(String.valueOf(qty));
                            }

                            updateTotalSubTotal();
                        }
                    });

                    parentListTransactionPanel.add(padding);
                    parentListTransactionPanel.add(cardPanel);
                    parentListTransactionPanel.revalidate();
                    parentListTransactionPanel.repaint();

                    addedProductCards.put(productId, cardPanel);
                    productQuantities.put(productId, quantityField);
                    productList.put(productId, product);
                }

                updateTotalSubTotal();
            }
        });

        listProductPanel.add(padding);
        listProductPanel.add(cardPanel, listProductPanel.getComponentCount());

        listProductPanel.revalidate();
        listProductPanel.repaint();
    }

    private void setAction() {
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showDashboardTransaction();
            }
        });

        buttonPayment.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if (productList.isEmpty()) {
                    parentView.showFailedPopUp("Please add product to make transaction!");
                    return;
                }

                // Simpan ke list untuk dikirim
                List<listTransactionProduct> listProducts = new ArrayList<>();
                int subQuantity = 0;
                int priceProduct = 0;
                int subPrice = 0;

                boolean isFirst = true;

                for (Map.Entry<Integer, dataProduct> entry : productList.entrySet()) {
                    int idProduct = entry.getKey();
                    dataProduct product = entry.getValue();
                    int quantity = Integer.parseInt(productQuantities.get(idProduct).getText());
                    int price = product.getPrice();
                    int calculationPrice = product.getPrice() * quantity;

                    listTransactionProduct transactionProduct = new listTransactionProduct(
                            idProduct,
                            product.getNameProduct(),
                            quantity,
                            price,
                            calculationPrice);

                    listProducts.add(transactionProduct);
                    subQuantity += quantity;
                    subPrice += calculationPrice;

                    if (isFirst) {
                        priceProduct = price;
                        isFirst = false;
                    }
                }

                // Buka pop-up pengisian data transaksi
                parentView.showPopUpTransaction(
                        listProducts,
                        subQuantity,
                        priceProduct,
                        subPrice);
            }
        });
    }

}
