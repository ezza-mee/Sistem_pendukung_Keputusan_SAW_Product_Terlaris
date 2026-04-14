package com.main.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class comboBox<T> extends JComboBox<T> {

    private String placeholder;
    private int radius;
    private Color arrowColor = color.DARKGREEN;
    private boolean isPopupVisible = false;

    public comboBox(T[] items, int x, int y, int width, int height, int radius) {
        super(items);
        this.radius = radius;
        setOpaque(false);
        setFocusable(false);
        setBounds(x, y, width, height);
        setRenderer(new PlaceholderRenderer());
        setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 12f));
        setForeground(color.BLACK);
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setMaximumRowCount(4);
        setSelectedItem(null);

        setUI(new customComboBoxUI(this));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });

        addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                isPopupVisible = true;
                repaint();
            }

            @Override
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
                isPopupVisible = false;
                repaint();
            }

            @Override
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
                isPopupVisible = false;
                repaint();
            }
        });
    }

    @Override
    public T getSelectedItem() {
        return (T) super.getSelectedItem();
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public void setArrowColor(Color color) {
        this.arrowColor = color;
        repaint();
    }

    public boolean isPopupVisible() {
        return isPopupVisible;
    }

    public Color getArrowColor() {
        return arrowColor;
    }

    public int getCornerRadius() {
        return radius;
    }

    private class PlaceholderRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            boolean isDisplayArea = index == -1;

            if (isDisplayArea) {
                if (comboBox.this.getSelectedItem() == null && placeholder != null) {
                    setText(placeholder);
                    setForeground(color.DARKGREY); // placeholder style
                    setFont(getFont().deriveFont(Font.ITALIC));
                } else {
                    setText(value != null ? value.toString() : "");
                    setForeground(color.BLACK); // selected item
                    setFont(getFont().deriveFont(Font.PLAIN));
                }
            } else {
                setText(value != null ? value.toString() : "");
                setForeground(isSelected ? color.WHITE : color.BLACK);
                setBackground(isSelected ? color.DARKGREY : color.LIGHTGREY);
                setFont(getFont().deriveFont(Font.PLAIN));
            }

            return this;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color.LIGHTGREY);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setClip(clip);
        super.paintComponent(g2);
        g2.dispose();
    }

}
