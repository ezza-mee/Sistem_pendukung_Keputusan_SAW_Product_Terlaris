package com.main.components;

import javax.swing.*;

public class linkLabel extends JPanel {

    private JLabel label;
    private JLabel quantityLabel;
    private boolean active = false;

    public java.awt.event.MouseAdapter labelActive = new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, color.DARKGREEN));
            label.setForeground(color.DARKGREEN);
            quantityLabel.setBackground(color.RED);
        }

        public void mouseExited(java.awt.event.MouseEvent e) {
            setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, color.DARKGREEN));
            label.setForeground(color.DARKGREEN);
            quantityLabel.setBackground(color.RED);
        }
    };

    public java.awt.event.MouseAdapter labelInActive = new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, color.DARKGREEN));
            label.setForeground(color.DARKGREEN);
            quantityLabel.setBackground(color.RED);
        }

        public void mouseExited(java.awt.event.MouseEvent e) {
            setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, color.DARKGREY));
            label.setForeground(color.DARKGREY);
            quantityLabel.setBackground(color.DARKGREY);
        }
    };

    public linkLabel(String text, int x, int y, int width, int height) {
        setLayout(null);
        setOpaque(false);
        setBounds(x, y, width, height);
        setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, color.DARKGREEN));

        label = new JLabel(text);
        label.setForeground(color.DARKGREEN);
        label.setBounds(0, 0, width, height);

        quantityLabel = new RoundedLabel("0");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setBackground(color.DARKGREY);
        quantityLabel.setForeground(color.WHITE);
        quantityLabel.setBounds(width - 25, 5, 20, 20);
        quantityLabel.setBorder(BorderFactory.createEmptyBorder());

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });

        add(label);
        setLabelActive();
        revalidate();
        repaint();
    }

    public void setLabelActive() {
        try {
            removeMouseListener(labelInActive);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addMouseListener(labelActive);
        setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, color.DARKGREEN));
        label.setForeground(color.DARKGREEN);
        updateQuantityStyle(true);

        active = true; // <-- tandai sebagai aktif
    }

    public void setLabelInActive() {
        try {
            removeMouseListener(labelActive);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addMouseListener(labelInActive);
        setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, color.DARKGREY));
        label.setForeground(color.DARKGREY);
        updateQuantityStyle(false);

        active = false; // <-- tandai sebagai tidak aktif
    }

    public boolean isActive() {
        return active;
    }

    public void setLinkLabelFont(java.awt.Font font) {
        label.setFont(font);
        quantityLabel.setFont(font);
    }

    public void setQuantity(int qty) {
        if (qty > 0) {
            quantityLabel.setText(String.valueOf(qty));
            add(quantityLabel);
        } else {
            remove(quantityLabel);
        }
    }

    private void updateQuantityStyle(boolean isActive) {
        if (isActive) {
            quantityLabel.setBackground(color.RED);
        } else {
            quantityLabel.setBackground(color.DARKGREY);
        }
    }

    class RoundedLabel extends JLabel {
        public RoundedLabel(String text) {
            super(text);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

            // Warna background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            super.paintComponent(g);
            g2.dispose();
        }
    }

}
