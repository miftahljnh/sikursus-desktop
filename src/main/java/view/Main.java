package view;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Form Pendaftaran Kursus");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(new FromPendaftaranAwal());

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}