import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class InterfaceGraphique extends JFrame {
    private GestionnaireTaches gestionnaire;
    private JTextField champDescription;
    private DefaultListModel<String> modeleListe;
    private JList<String> listeTaches;

    public InterfaceGraphique(GestionnaireTaches gestionnaire) {
        this.gestionnaire = gestionnaire;
        
        // Configuration de la fenêtre principale
        setTitle("Gestionnaire de Tâches");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Création des panneaux
        JPanel panneauPrincipal = new JPanel(new BorderLayout(10, 10));
        panneauPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel du haut pour l'ajout de tâches
        JPanel panneauHaut = new JPanel(new BorderLayout(5, 0));
        champDescription = new JTextField();
        JButton boutonAjouter = new JButton("Ajouter");
        boutonAjouter.addActionListener(e -> ajouterTache());
        panneauHaut.add(new JLabel("Nouvelle tâche:"), BorderLayout.WEST);
        panneauHaut.add(champDescription, BorderLayout.CENTER);
        panneauHaut.add(boutonAjouter, BorderLayout.EAST);

        // Liste des tâches avec défilement
        modeleListe = new DefaultListModel<>();
        listeTaches = new JList<>(modeleListe);
        listeTaches.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listeTaches);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des tâches"));

        // Panneau de boutons en bas
        JPanel panneauBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JButton boutonSupprimer = new JButton("Supprimer");
        boutonSupprimer.addActionListener(e -> supprimerTache());
        
        JButton boutonTerminer = new JButton("Marquer comme terminée");
        boutonTerminer.addActionListener(e -> marquerTerminee());
        
        JButton boutonTrier = new JButton("Trier les tâches");
        boutonTrier.addActionListener(e -> {
            gestionnaire.trierTaches();
            rafraichirListeTaches();
        });

        panneauBoutons.add(boutonSupprimer);
        panneauBoutons.add(boutonTerminer);
        panneauBoutons.add(boutonTrier);

        // Assemblage de l'interface
        panneauPrincipal.add(panneauHaut, BorderLayout.NORTH);
        panneauPrincipal.add(scrollPane, BorderLayout.CENTER);
        panneauPrincipal.add(panneauBoutons, BorderLayout.SOUTH);

        // Ajout d'un raccourci clavier pour ajouter une tâche avec Entrée
        champDescription.addActionListener(e -> ajouterTache());

        setContentPane(panneauPrincipal);
        rafraichirListeTaches();
    }

    private void ajouterTache() {
        String description = champDescription.getText().trim();
        if (!description.isEmpty()) {
            gestionnaire.ajouterTache(description);
            rafraichirListeTaches();
            champDescription.setText("");
            champDescription.requestFocus();
        }
    }

    private void supprimerTache() {
        int index = listeTaches.getSelectedIndex();
        if (index != -1) {
            int reponse = JOptionPane.showConfirmDialog(
                this,
                "Voulez-vous vraiment supprimer cette tâche ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
            );
            if (reponse == JOptionPane.YES_OPTION) {
                gestionnaire.supprimerTache(index);
                rafraichirListeTaches();
            }
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Veuillez sélectionner une tâche à supprimer",
                "Aucune sélection",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void marquerTerminee() {
        int index = listeTaches.getSelectedIndex();
        if (index != -1) {
            gestionnaire.marquerTacheTerminee(index);
            rafraichirListeTaches();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Veuillez sélectionner une tâche à marquer comme terminée",
                "Aucune sélection",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void rafraichirListeTaches() {
        modeleListe.clear();
        // Capturer la sortie de afficherTaches
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        java.io.PrintStream old = System.out;
        System.setOut(ps);
        
        gestionnaire.afficherTaches();
        
        System.out.flush();
        System.setOut(old);
        
        // Traiter la sortie et l'ajouter au modèle
        String[] lignes = baos.toString().split("\n");
        for (String ligne : lignes) {
            if (!ligne.equals("Aucune tâche enregistrée.")) {
                // Enlever le numéro au début de la ligne (ex: "1. ")
                if (ligne.contains(". ")) {
                    ligne = ligne.substring(ligne.indexOf(". ") + 2);
                }
                modeleListe.addElement(ligne);
            }
        }
    }
} 