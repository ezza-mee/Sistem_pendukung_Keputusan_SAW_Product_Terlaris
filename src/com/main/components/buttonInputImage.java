package com.main.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class buttonInputImage extends buttonCustom {
    private int radius;
    private Color originalBackground;
    private Color hoverBackground;
    private Color pressedBackground;

    private JTextField inputField;
    private textLabel labelInfo;

    public buttonInputImage(String text, int x, int y, int width, int height, int radius,
            JTextField inputField, textLabel labelInfo) {
        super(text, x, y, width, height, radius);
        this.radius = radius;
        this.inputField = inputField;
        this.labelInfo = labelInfo;

        setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 15f));
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setFocusPainted(false);
        setContentAreaFilled(false);

        originalBackground = color.DARKGREEN;
        hoverBackground = color.GREEN;
        pressedBackground = color.GREENLIGHT;

        setBackground(originalBackground);
        setForeground(color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(originalBackground);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (contains(e.getPoint())) {
                    setBackground(hoverBackground);
                } else {
                    setBackground(originalBackground);
                }
            }
        });

        addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openImageChooser();
            }
        });
    }

    private void openImageChooser() {
        // Gunakan FileDialog bawaan OS lewat AWT (lebih native dibanding JFileChooser)
        java.awt.FileDialog fileDialog = new java.awt.FileDialog((Frame) null, "Pilih Gambar", java.awt.FileDialog.LOAD);

        // Set filter file untuk gambar (sayangnya FileDialog tidak support filter langsung, tapi kita bisa validasi manual)
        fileDialog.setFilenameFilter((dir, name) -> {
            String lower = name.toLowerCase();
            return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png") || lower.endsWith(".gif");
        });

        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();

        if (directory == null || filename == null) {
            return; // User cancel
        }

        String path = new File(directory, filename).getAbsolutePath();

        inputField.setText(path);
        labelInfo.setText("<html><body style='width:200px;'>Path:<br>" + path + "</body></html>");

        setText("Image Selected");
        setBackground(color.LIGHTGREY);
        inputField.setBackground(color.LIGHTGREY);
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
        g2.dispose();
    }
}
