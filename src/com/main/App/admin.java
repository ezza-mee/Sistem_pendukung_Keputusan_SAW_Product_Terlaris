package com.main.App;

import com.main.components.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.main.controller.appsController;
import com.main.views.popUp.popUpDataKriteria.popUpDataKriteria;
import com.main.views.popUp.popUpDataKriteria.popUpInputKriteria;

public class admin {
    public static void main(String[] args) {
        appsController.showDashboardAdmin();

        // new testFrame().setVisible(true);
    }

    public static class testFrame extends JFrame {
        public testFrame() {
            setTitle("Test Component");
            setSize(1000, 550);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setBounds(0, 0, 1000, 550);
            panel.setBackground(color.DARKGREEN);
            panel.setLayout(null);

            // popUpInputBobotKriteria test = new popUpInputBobotKriteria();
            // test.setBounds(0, 0, 1000, 550);
            // panel.add(test);

            add(panel);
        }
    }
}
