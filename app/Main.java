package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Configura o look and feel do sistema operacional
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Executa a interface na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            new Interface();
        });
    }
}
