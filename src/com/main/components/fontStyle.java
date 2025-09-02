package com.main.components;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class fontStyle {

    private static final Map<String, Font> FONT_CACHE = new HashMap<>();
    private static final String FONT_PATH = "/com/main/resources/fonts/";

    public enum FontStyle {
        REGULAR("Poppins-Regular.ttf"),
        MEDIUM("Poppins-Medium.ttf"),
        SEMIBOLD("Poppins-SemiBold.ttf"),
        BOLD("Poppins-Bold.ttf"),
        BLACK("Poppins-Black.ttf"),
        LIGHT("Poppins-Light.ttf"),
        THIN("Poppins-Thin.ttf"),
        EXTRABOLD("Poppins-ExtraBold.ttf");

        private final String fileName;

        FontStyle(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    /**
     * Load font based on style and size.
     *
     * @param style Enum FontStyle (e.g., SEMIBOLD, BOLD, etc.)
     * @param size  float size of the font
     * @return Font object with the specified style and size
     */
    public static Font getFont(FontStyle style, float size) {
        try {
            // cek cache dulu biar nggak load ulang
            if (!FONT_CACHE.containsKey(style.name())) {
                try (InputStream is = fontStyle.class.getResourceAsStream(FONT_PATH + style.getFileName())) {
                    if (is == null) {
                        System.err.println("Font file not found: " + style.getFileName());
                        return new Font("SansSerif", Font.PLAIN, (int) size);
                    }
                    Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
                    FONT_CACHE.put(style.name(), baseFont);
                }
            }
            return FONT_CACHE.get(style.name()).deriveFont(size);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size); // fallback font
        }
    }
}
