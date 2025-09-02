package com.main.components;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class imageIcon extends JLabel {
    public imageIcon(String imagePath, int width, int height) {
        super();

        // Ambil resource dari dalam JAR (classpath)
        URL resource = getClass().getResource(imagePath);
        if (resource != null) {
            ImageIcon originalIcon = new ImageIcon(resource);
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));
            setBounds(0, 0, width, height);
        } else {
            System.err.println("⚠️ Image not found: " + imagePath);
        }
    }

    public imageIcon(Icon icon, int width, int height) {
        super();
        if (icon instanceof ImageIcon) {
            Image scaledImage = ((ImageIcon) icon).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));
            setBounds(0, 0, width, height);
        } else {
            setIcon(icon);
        }
    }
}
