package com.main.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class numberField extends JSpinner {

    private int radius;

    public numberField(int x, int y, int width, int radius, int value, int min, int max, int step) {
        super();
        this.radius = radius;

        setBounds(x, y, width, 30);
        setOpaque(false);
        setBorder(null);
        setFocusable(false);

        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
        setModel(model);

        // Ambil editor spinner
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) getEditor();
        JTextField field = editor.getTextField();

        field.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 12f));
        field.setForeground(color.BLACK);
        field.setOpaque(false);
        field.setBorder(new EmptyBorder(5, 10, 5, 10));
        field.setHorizontalAlignment(JTextField.CENTER);

        editor.setOpaque(false);
        editor.setBorder(null);

        // Perbesar tombol spinner
        for (Component c : getComponents()) {
            if (c instanceof JButton) {
                c.setPreferredSize(new Dimension(20, 15));
                c.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        // Mouse click supaya bisa focus
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setFocusable(true);
                requestFocusInWindow();
            }
        });

        // Focus repaint
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // background rounded
        g2.setColor(color.LIGHTGREY);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // hilangkan border default spinner
    }
}
