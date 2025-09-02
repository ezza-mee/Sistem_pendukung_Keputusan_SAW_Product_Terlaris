package com.main.views.popUp;

import javax.swing.JLabel;

import com.main.components.*;
import com.main.components.panelApps.popUpPanel;
import com.main.routes.mainFrame;

public class popUpConfrim extends popUpPanel {
    private mainFrame parentFrame;

    private textLabel messageLabel, confrimLabel;

    private buttonCustom buttonCancel;
    private buttonCustom buttonConfrim;

    private appIcons appIcons = new appIcons();
    private imageIcon alertIcon = appIcons.getAlertRed(40, 40);

    public popUpConfrim(mainFrame parentFrame) {
        super();
        this.parentFrame = parentFrame;
        setSize(400, 220);
        initComponent();
    }

    private void initComponent() {
        setPosition();
        setFont();
        setColor();

        add(alertIcon);
        add(confrimLabel);
        add(messageLabel);

        add(buttonCancel);
        add(buttonConfrim);

        setVisible(true);
    }

    private void setPosition() {
        confrimLabel = new textLabel("Confrim Data", 0, 40, 400, 100);
        messageLabel = new textLabel(
                "Are your sure you want to Confrim the application?",
                0, 70, 400, 100);
        buttonCancel = new buttonCustom("No", 50, 160, 130, 40, 10);
        buttonConfrim = new buttonCustom("Yes", 220, 160, 130, 40, 10);

        alertIcon.setBounds(185, 30, 400, 40);
    }

    private void setColor() {
        buttonCancel.setOriginalBackground(color.RED);
        buttonCancel.setHoverBackground(color.DARKRED);
        buttonCancel.setPressedBackground(color.RED);
        buttonCancel.setBackground(color.RED);

        buttonConfrim.setOriginalBackground(color.DARKGREEN);
        buttonConfrim.setHoverBackground(color.GREEN);
        buttonConfrim.setPressedBackground(color.DARKGREEN);
        buttonConfrim.setBackground(color.DARKGREEN);

        buttonConfrim.setForeground(color.WHITE);
    }

    private void setFont() {
        confrimLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 16f));
        messageLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 13f));

        confrimLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    public buttonCustom getButtonConfrim() {
        return buttonConfrim;
    }

    public buttonCustom getButtonCancel() {
        return buttonCancel;
    }

    public void setNotificationMessage(String message) {
        messageLabel.setText(message);
        messageLabel.revalidate();
        messageLabel.repaint();
    }
}
