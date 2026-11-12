package view;

import java.awt.*;
import javax.swing.*;

public class InstrukturFrame extends JFrame {

    public InstrukturFrame() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Master Instruktur");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLayout(new BorderLayout());

        JLabel lblJudul = new JLabel("Form Master Instruktur", JLabel.CENTER);
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(lblJudul, BorderLayout.CENTER);
    }
}