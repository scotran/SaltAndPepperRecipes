import javax.swing.SwingUtilities;


public class App {
    public static void main(String [] Args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
}