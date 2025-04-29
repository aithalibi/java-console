import javax.swing.SwingUtilities;

public class Application {
    public static void main(String[] args) {
        GestionnaireTaches gestionnaire = new GestionnaireTaches();
        
        SwingUtilities.invokeLater(() -> {
            InterfaceGraphique interfaceGui = new InterfaceGraphique(gestionnaire);
            interfaceGui.setVisible(true);
        });
    }
}