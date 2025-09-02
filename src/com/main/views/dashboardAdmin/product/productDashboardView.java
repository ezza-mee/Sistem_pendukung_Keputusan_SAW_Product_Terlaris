package com.main.views.dashboardAdmin.product;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Image;
import java.util.ArrayList;
import java.awt.Component;
import java.awt.Dimension;

import com.main.components.*;
import com.main.components.panelApps.contentPanel;
import com.main.controller.searchableView;
import com.main.models.entity.dataProduct;
import com.main.models.product.loadDataProduct;
import com.main.routes.dashboardAdminView;
import com.main.routes.mainFrame;
import com.main.services.authDataProduct;
import com.main.views.popUp.popUpConfrim;

public class productDashboardView extends contentPanel implements searchableView {

    private mainFrame parentApp;

    private dashboardAdminView parentView;

    private textLabel headerLabel;

    private panelRounded contentPanel;
    private panelRounded headerPanel;
    private scrollPane scrollPane;

    private buttonCustom buttonAdd;

    private linkLabel allProductLabel, foodProductLabel, coffeProductLabel, drinkProductLabel;

    private comboBox<String> statusProductField;

    private appIcons appIcon = new appIcons();
    private imageIcon iconDelete = appIcon.getDeleteIconWhite(20, 20);
    private imageIcon iconEdit = appIcon.getEditIconWhite(20, 20);

    private int quantityAllDataProduct = loadDataProduct.getAllQuantityDataProduct();
    private int quantityAllDataFoodProduct = loadDataProduct.getAllQuantityDataFoodProduct();
    private int quantityAllDataCoffeeProduct = loadDataProduct.getAllQuantityDataCoffeeProduct();
    private int quantityAllDataDrinkProduct = loadDataProduct.getAllQuantityDataDrinkProduct();

    private String currentCategory = "ALL";

    private void setColor() {
        headerLabel.setForeground(color.BLACK);
        headerPanel.setBackground(color.WHITE);
        contentPanel.setBackground(color.DARKGREY);
    }

    private void setFont() {
        headerLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 30f));

        allProductLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        foodProductLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        coffeProductLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
        drinkProductLabel.setLinkLabelFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 14f));
    }

    public productDashboardView(mainFrame parentApp, dashboardAdminView parentView) {
        super();
        this.parentApp = parentApp;
        this.parentView = parentView;
        initContent();
    }

    @Override
    public void initContent() {
        setLayout();
        setColor();
        setFont();
        setAction();
        showAllProduct();

        headerPanel.add(allProductLabel);
        headerPanel.add(foodProductLabel);
        headerPanel.add(coffeProductLabel);
        headerPanel.add(drinkProductLabel);
        headerPanel.add(statusProductField);
        headerPanel.add(buttonAdd);

        add(headerLabel);
        add(headerPanel);
        add(scrollPane);

        setVisible(true);
    }

    private void setLayout() {
        headerLabel = new textLabel("Data Product", 40, 0, 400, 80);
        headerPanel = new panelRounded(40, 80, 1050, 110, 10, 10);
        contentPanel = new panelRounded(40, 140, 1050, 410, 0, 0);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        scrollPane = new scrollPane(contentPanel, 0, 0, getWidth(), getHeight());
        scrollPane.setBounds(40, 220, 1050, 410);

        buttonAdd = new buttonCustom("Add", 900, 35, 100, 40, 10);

        allProductLabel = new linkLabel("ALL", 40, 40, 80, 30);
        allProductLabel.setQuantity(quantityAllDataProduct);
        foodProductLabel = new linkLabel("Food", 150, 40, 80, 30);
        foodProductLabel.setQuantity(quantityAllDataFoodProduct);
        coffeProductLabel = new linkLabel("Coffe", 260, 40, 80, 30);
        coffeProductLabel.setQuantity(quantityAllDataCoffeeProduct);
        drinkProductLabel = new linkLabel("Drink", 370, 40, 80, 30);
        drinkProductLabel.setQuantity(quantityAllDataDrinkProduct);

        String[] statusProductItems = { null, "Ready", "Out of Stock" };
        statusProductField = new comboBox<>(statusProductItems, 480, 40, 200, 30, 10);
        statusProductField.setPlaceholder("Select your Status");

    }

    private void setAction() {
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                parentView.showFormProduct();
            }
        });

        statusProductField.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                filterAndLoadProducts();
            }
        });

        allProductLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                statusProductField.setSelectedItem(null);
                currentCategory = "ALL";
                showAllProduct();
            }
        });

        foodProductLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                statusProductField.setSelectedItem(null);
                currentCategory = "FOOD";
                showFoodProduct();
            }
        });

        coffeProductLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                statusProductField.setSelectedItem(null);
                currentCategory = "COFFEE";
                showCoffeProduct();
            }
        });

        drinkProductLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent me) {
                statusProductField.setSelectedItem(null);
                currentCategory = "DRINK";
                showDrinkProduct();
            }
        });

    }

    private void filterAndLoadProducts() {
        contentPanel.removeAll();

        String selectedStatus = (String) statusProductField.getSelectedItem();

        ArrayList<dataProduct> productList = new ArrayList<>();

        switch (currentCategory) {
            case "ALL":
                if (selectedStatus == null) {
                    productList = loadDataProduct.getAllProducts();
                } else {
                    productList = loadDataProduct.getProductsByStatus(selectedStatus);
                }
                break;

            case "FOOD":
                productList = loadDataProduct.getProductsByCategoryAndStatus("Food", selectedStatus);
                break;

            case "COFFEE":
                productList = loadDataProduct.getProductsByCategoryAndStatus("Coffee", selectedStatus);
                break;

            case "DRINK":
                productList = loadDataProduct.getProductsByCategoryAndStatus("Drink", selectedStatus);
                break;
        }

        for (dataProduct product : productList) {
            loadDataProductInCard(product);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void filterDataByKeyword(String keyword) {
        contentPanel.removeAll();

        ArrayList<dataProduct> productList = new ArrayList<>();
        String selectedStatus = (String) statusProductField.getSelectedItem();

        if (keyword == null || keyword.trim().isEmpty()) {
            switch (currentCategory) {
                case "ALL":
                    if (selectedStatus == null) {
                        productList = loadDataProduct.getAllProducts();
                    } else {
                        productList = loadDataProduct.getProductsByStatus(selectedStatus);
                    }
                    break;

                case "FOOD":
                    productList = loadDataProduct.getProductsByCategoryAndStatus("Food", selectedStatus);
                    break;

                case "COFFEE":
                    productList = loadDataProduct.getProductsByCategoryAndStatus("Coffee", selectedStatus);
                    break;

                case "DRINK":
                    productList = loadDataProduct.getProductsByCategoryAndStatus("Drink", selectedStatus);
                    break;
            }
        } else {
            // Jika user memilih ALL status, kita anggap tidak perlu filter status
            boolean filterByStatus = selectedStatus != null && !selectedStatus.equalsIgnoreCase("All");

            if (currentCategory != null && !currentCategory.equalsIgnoreCase("ALL")) {
                if (filterByStatus) {
                    productList = authDataProduct.searchProductsByKeywordAndCategoryAndStatus(keyword, currentCategory,
                            selectedStatus);
                } else {
                    // Buat method baru tanpa status filter
                    productList = authDataProduct.searchProductsByKeywordAndCategory(keyword, currentCategory);
                }
            } else {
                // Kategori ALL
                if (filterByStatus) {
                    productList = authDataProduct.searchProductsByKeywordAndStatus(keyword, selectedStatus);
                } else {
                    productList = authDataProduct.searchProductByKeyword(keyword);
                }
            }
        }

        for (dataProduct product : productList) {
            loadDataProductInCard(product);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void resetLinkLabel() {
        allProductLabel.setForeground(color.DARKGREY);
        allProductLabel.setLabelInActive();

        foodProductLabel.setForeground(color.DARKGREY);
        foodProductLabel.setLabelInActive();

        coffeProductLabel.setForeground(color.DARKGREY);
        coffeProductLabel.setLabelInActive();

        drinkProductLabel.setForeground(color.DARKGREY);
        drinkProductLabel.setLabelInActive();
    }

    private void showAllProduct() {
        resetLinkLabel();
        allProductLabel.setForeground(color.DARKGREEN);
        allProductLabel.setLabelActive();

        loadAllProductCards();

    }

    private void showFoodProduct() {
        resetLinkLabel();
        foodProductLabel.setForeground(color.DARKGREEN);
        foodProductLabel.setLabelActive();

        loadAllFoodProductCards();

    }

    private void showCoffeProduct() {
        resetLinkLabel();
        coffeProductLabel.setForeground(color.DARKGREEN);
        coffeProductLabel.setLabelActive();

        loadAllCoffeProductCards();

    }

    private void showDrinkProduct() {
        resetLinkLabel();
        drinkProductLabel.setForeground(color.DARKGREEN);
        drinkProductLabel.setLabelActive();

        loadAllDrinkProductCards();

    }

    public void loadAllProductCards() {
        contentPanel.removeAll();

        ArrayList<dataProduct> list = loadDataProduct.getAllProducts();
        for (dataProduct product : list) {
            loadDataProductInCard(product);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void loadAllFoodProductCards() {
        contentPanel.removeAll();

        ArrayList<dataProduct> list = loadDataProduct.getAllFoodProducts();
        for (dataProduct product : list) {
            loadDataProductInCard(product);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void loadAllCoffeProductCards() {
        contentPanel.removeAll();

        ArrayList<dataProduct> list = loadDataProduct.getAllCoffeProducts();
        for (dataProduct product : list) {
            loadDataProductInCard(product);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void loadAllDrinkProductCards() {
        contentPanel.removeAll();

        ArrayList<dataProduct> list = loadDataProduct.getAllDrinkProducts();
        for (dataProduct product : list) {
            loadDataProductInCard(product);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void loadDataProductInCard(dataProduct product) {
        panelRounded cardPanel = new panelRounded();
        Dimension cardSize = new Dimension(1000, 100);
        cardPanel.setPreferredSize(cardSize);
        cardPanel.setMaximumSize(cardSize);
        cardPanel.setMinimumSize(cardSize);
        cardPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.setLayout(null);
        cardPanel.setBackground(color.WHITE);

        // Tampilkan gambar produk
        byte[] imageData = product.getImageData();
        ImageIcon icon = new ImageIcon(imageData);
        Image scaledImage = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(20, 10, 80, 80);
        cardPanel.add(imageLabel);

        // Nama produk
        JLabel nameLabel = new JLabel(product.getNameProduct());
        nameLabel.setBounds(130, 10, 400, 25);
        nameLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 18f));
        cardPanel.add(nameLabel);

        // Harga produk
        JLabel priceLabel = new JLabel("Rp " + product.getPrice());
        priceLabel.setBounds(130, 40, 200, 20);
        priceLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 14f));
        cardPanel.add(priceLabel);

        // Deskripsi produk
        JLabel descLabel = new JLabel(product.getDescription());
        descLabel.setBounds(130, 65, 600, 20);
        descLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 14f));
        cardPanel.add(descLabel);

        // status produk
        JLabel statusLabel = new JLabel(product.getStatus());
        statusLabel.setBounds(670, 40, 200, 20);
        statusLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 16f));
        cardPanel.add(statusLabel);

        // Tombol Hapus
        buttonCustom buttonEdit = new buttonCustom("", 860, 35, 40, 40, 10);
        buttonCustom buttonDelete = new buttonCustom("", 910, 35, 40, 40, 10);

        buttonDelete.setIcon(iconDelete);
        buttonEdit.setIcon(iconEdit);

        cardPanel.add(buttonEdit);
        cardPanel.add(buttonDelete);

        // Padding antar card
        Component padding = Box.createRigidArea(new Dimension(0, 20));

        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                popUpConfrim messagePopUp = parentView.showConfrimPopUp("do you want to delete product data?");

                messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent ae) {
                        int idProduct = product.getIdProduct();
                        boolean success = authDataProduct.deleteDataProduct(idProduct);

                        if (success) {
                            parentApp.hideGlassNotificationPanel();
                            parentView.showSuccessPopUp("Success Delete Data Product");
                            contentPanel.remove(cardPanel);
                            contentPanel.remove(padding);
                            contentPanel.revalidate();
                            contentPanel.repaint();
                        } else {
                            parentView.showFailedPopUp("Product Delete Data Product");
                        }

                    }
                });

                messagePopUp.getButtonCancel().addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent ae) {
                        parentApp.hideGlassNotificationPanel();
                    }
                });
            }
        });

        buttonEdit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                popUpConfrim messagePopUp = parentView.showConfrimPopUp("do you want to delete product data?");

                messagePopUp.getButtonConfrim().addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent ae) {
                        int idProduct = product.getIdProduct();
                        dataProduct selectedDataProduct = loadDataProduct.getProductById(idProduct);

                        if (selectedDataProduct != null) {
                            parentApp.hideGlassNotificationPanel();
                            parentView.setDataProductToEdit(selectedDataProduct);
                            parentView.showFormProduct();
                        } else {
                            parentView.showFailedPopUp("Product Data Not Found");
                        }
                    }
                });

                messagePopUp.getButtonCancel().addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent ae) {
                        parentApp.hideGlassNotificationPanel();
                    }
                });
            }
        });

        contentPanel.add(padding);
        contentPanel.add(cardPanel, contentPanel.getComponentCount());

        contentPanel.revalidate();
        contentPanel.repaint();
    }

}
