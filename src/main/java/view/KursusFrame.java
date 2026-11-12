package view;

import java.awt.*;
import javax.swing.*;

public class KursusFrame extends JFrame {

    public KursusFrame() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Master Kursus");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLayout(new BorderLayout());

        JLabel lblJudul = new JLabel("Form Master Kursus", JLabel.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(lblJudul, BorderLayout.CENTER);
    }
}