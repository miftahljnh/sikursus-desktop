package app;
    import javax.swing.SwingUtilities;
    import view.LoginFrame;
    public class Main {
     public static void main(String[] args) {
     SwingUtilities.invokeLater(
     () -> new LoginFrame().setVisible(true));
     }
    }