package com.main.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class datePickerField extends JPanel {
    private textField dateField;
    private JDialog calendarDialog;
    private Calendar selectedDate;
    private String placeholder;
    private JPanel calendarPanel;
    private JLabel monthYearLabel;
    private Calendar currentViewDate;
    private ActionListener dateSelectionListener;
    private JButton prevButton, nextButton;
    private buttonCustom dropdownButton, confrimButton, cancelButton;

    private appIcons appIcons = new appIcons();
    private imageIcon calenderIcon = appIcons.getCalenderWhite(20, 20);

    public datePickerField(int x, int y, int width, int height, String placeholder) {
        this.placeholder = placeholder;
        this.selectedDate = Calendar.getInstance();
        this.currentViewDate = Calendar.getInstance();

        setLayout(null);
        setOpaque(false);
        setBounds(x, y, width, height);

        initializeComponents(width, height);
        setupCalendarDialog();

        if (placeholder != null && !placeholder.isEmpty()) {
            setSelectedDate(placeholder);
        }
    }

    private void initializeComponents(int width, int height) {
        // Main input field
        dateField = new textField(0, 0, 0, 15);
        dateField.setBounds(0, 0, width - 40, height);
        dateField.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 14f));
        dateField.setForeground(color.BLACK);
        dateField.setEditable(false);
        dateField.setBackground(color.DARKGREY);
        dateField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(color.WHITE, 1, true),
                new EmptyBorder(8, 12, 8, 12)));

        // Placeholder handling
        if (placeholder != null && !placeholder.isEmpty()) {
            dateField.setText(placeholder);
            dateField.setForeground(color.BLACK);
        }

        // Dropdown button
        dropdownButton = new buttonCustom("", width - 40, 0, 40, height, 10);
        dropdownButton.setIcon(calenderIcon);

        // Add components
        add(dateField);
        add(dropdownButton);

        // Add click listeners
        dateField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showCalendar();
            }
        });

        dropdownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCalendar();
            }
        });
    }

    private void setupCalendarDialog() {
        // Create dialog
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        if (parentWindow instanceof Frame) {
            calendarDialog = new JDialog((Frame) parentWindow, "Select Date", true);
        } else if (parentWindow instanceof Dialog) {
            calendarDialog = new JDialog((Dialog) parentWindow, "Select Date", true);
        } else {
            calendarDialog = new JDialog((Frame) null, "Select Date", true);
        }

        calendarDialog.setUndecorated(true);
        calendarDialog.setLayout(new BorderLayout());
        calendarDialog.setBackground(color.WHITE);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(color.WHITE);
        mainPanel.setBorder(new LineBorder(color.WHITE, 1));

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Calendar panel
        calendarPanel = new JPanel(new GridLayout(6, 6, 2, 2));
        calendarPanel.setBackground(color.WHITE);
        calendarPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        mainPanel.add(calendarPanel, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        calendarDialog.add(mainPanel);
        calendarDialog.setSize(320, 400);

        // Make dialog close when clicking outside
        calendarDialog.addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
            }

            public void windowLostFocus(java.awt.event.WindowEvent e) {
                calendarDialog.setVisible(false);
            }
        });

        updateCalendarView();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(color.WHITE);
        headerPanel.setBorder(new EmptyBorder(15, 15, 10, 15));

        // Previous button
        prevButton = new JButton("‹");
        prevButton.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));
        prevButton.setForeground(color.BLACK);
        prevButton.setBackground(color.WHITE);
        prevButton.setBorder(null);
        prevButton.setFocusPainted(false);
        prevButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentViewDate.add(Calendar.MONTH, -1);
                updateCalendarView();
            }
        });

        // Next button
        nextButton = new JButton("›");
        nextButton.setFont(fontStyle.getFont(fontStyle.FontStyle.BOLD, 20f));
        nextButton.setForeground(color.BLACK);
        nextButton.setBackground(color.WHITE);
        nextButton.setBorder(null);
        nextButton.setFocusPainted(false);
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentViewDate.add(Calendar.MONTH, 1);
                updateCalendarView();
            }
        });

        // Month/Year label
        monthYearLabel = new JLabel();
        monthYearLabel.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 16f));
        monthYearLabel.setForeground(color.BLACK);
        monthYearLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthYearLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(color.WHITE);
        footerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Remove button
        cancelButton = new buttonCustom("Cancel", 0, 0, 100, 20, 10);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendarDialog.setVisible(false);
            }
        });

        // Done button
        confrimButton = new buttonCustom("Done", 0, 0, 100, 20, 10);
        confrimButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendarDialog.setVisible(false);
            }
        });

        footerPanel.add(cancelButton);
        footerPanel.add(Box.createHorizontalStrut(70));
        footerPanel.add(confrimButton);

        return footerPanel;
    }

    private void updateCalendarView() {
        calendarPanel.removeAll();

        // Update month/year label
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy");
        monthYearLabel.setText(monthYearFormat.format(currentViewDate.getTime()));

        // Day headers
        String[] dayHeaders = { "M", "T", "W", "T", "F", "S", "S" };
        for (String dayHeader : dayHeaders) {
            JLabel label = new JLabel(dayHeader);
            label.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 12f));
            label.setForeground(color.BLACK);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            calendarPanel.add(label);
        }

        // Get first day of month and number of days
        Calendar firstDayOfMonth = (Calendar) currentViewDate.clone();
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
        int startDay = (firstDayOfWeek == Calendar.SUNDAY) ? 6 : firstDayOfWeek - 2;

        int daysInMonth = firstDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Add empty cells for days before month starts
        for (int i = 0; i < startDay; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // Add day buttons
        for (int day = 1; day <= daysInMonth; day++) {
            final int currentDay = day;
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setFont(fontStyle.getFont(fontStyle.FontStyle.SEMIBOLD, 14f));
            dayButton.setPreferredSize(new Dimension(40, 40));
            dayButton.setFocusPainted(false);
            dayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Check if this is the selected date
            Calendar tempCal = (Calendar) currentViewDate.clone();
            tempCal.set(Calendar.DAY_OF_MONTH, day);

            final boolean isSelected;
            if (selectedDate != null) {
                isSelected = isSameDay(tempCal, selectedDate);
            } else {
                isSelected = false;
            }

            if (isSelected) {
                dayButton.setBackground(color.DARKGREEN);
                dayButton.setForeground(color.WHITE);
                dayButton.setBorder(null);
            } else {
                dayButton.setBackground(color.WHITE);
                dayButton.setForeground(color.BLACK);
                dayButton.setBorder(null);
            }

            dayButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectDate(currentDay);
                }
            });

            // Hover effect
            dayButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if (!isSelected) {
                        dayButton.setBackground(color.DARKGREEN);
                    }
                }

                public void mouseExited(MouseEvent e) {
                    if (!isSelected) {
                        dayButton.setBackground(color.WHITE);
                    }
                }
            });

            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void selectDate(int day) {
        selectedDate = (Calendar) currentViewDate.clone();
        selectedDate.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateField.setText(displayFormat.format(selectedDate.getTime()));
        dateField.setForeground(color.BLACK);

        updateCalendarView();

        // Trigger the action listener if one is set
        if (dateSelectionListener != null) {
            ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "dateSelected");
            dateSelectionListener.actionPerformed(event);
        }
    }

    private void showCalendar() {
        if (calendarDialog != null) {
            Point fieldLocation = dateField.getLocationOnScreen();
            calendarDialog.setLocation(fieldLocation.x, fieldLocation.y + dateField.getHeight() + 5);
            calendarDialog.setVisible(true);
        }
    }

    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public String getSelectedDate() {
        if (selectedDate != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(selectedDate.getTime());
        }
        return null;
    }

    public void setSelectedDate(String dateString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(dateString);
            selectedDate = Calendar.getInstance();
            selectedDate.setTime(date);
            currentViewDate = (Calendar) selectedDate.clone();

            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateField.setText(displayFormat.format(selectedDate.getTime()));
            dateField.setForeground(color.BLACK);

            if (calendarPanel != null) {
                updateCalendarView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date getSelectedDateAsDate() {
        if (selectedDate != null) {
            return selectedDate.getTime();
        }
        return null;
    }

    // Method untuk menambahkan ActionListener
    public void addActionListener(ActionListener listener) {
        this.dateSelectionListener = listener;
    }

    // Method untuk menghapus ActionListener
    public void removeActionListener() {
        this.dateSelectionListener = null;
    }

    // Method kompatibilitas untuk kode legacy
    public datePickerField getDatePicker() {
        return this;
    }
}