import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class GestionnaireTaches {
    private Connection connection;

    public GestionnaireTaches() {
        this.connection = DatabaseConnection.getConnection();
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS taches (
                id INT AUTO_INCREMENT PRIMARY KEY,
                description TEXT NOT NULL,
                est_terminee BOOLEAN DEFAULT FALSE,
                date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
        """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table taches vérifiée/créée avec succès!");
        } catch (SQLException e) {
            System.out.println("Erreur avec la table : " + e.getMessage());
        }
    }

    public void ajouterTache(String description) {
        String sql = "INSERT INTO taches (description) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    public void supprimerTache(int index) {
        String sql = "DELETE FROM taches WHERE id = (SELECT id FROM taches LIMIT ?, 1)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    public void marquerTacheTerminee(int index) {
        String sql = "UPDATE taches SET est_terminee = TRUE WHERE id = (SELECT id FROM (SELECT id FROM taches LIMIT ?, 1) AS t)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, index);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    public ArrayList<Tache> getTaches() {
        ArrayList<Tache> taches = new ArrayList<>();
        String sql = "SELECT * FROM taches ORDER BY date_creation";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String description = rs.getString("description");
                boolean estTerminee = rs.getBoolean("est_terminee");
                Timestamp dateCreation = rs.getTimestamp("date_creation");
                
                Tache tache = new Tache(description);
                tache.setEstTerminee(estTerminee);
                tache.setDateCreation(dateCreation.toLocalDateTime());
                taches.add(tache);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return taches;
    }

    public void afficherTaches() {
        ArrayList<Tache> taches = getTaches();
        if (taches.isEmpty()) {
            System.out.println("Aucune tâche.");
            return;
        }
        for (int i = 0; i < taches.size(); i++) {
            System.out.println((i + 1) + ". " + taches.get(i));
        }
    }

    public ArrayList<Tache> getTachesTriees() {
        ArrayList<Tache> taches = new ArrayList<>();
        String sql = "SELECT * FROM taches ORDER BY description";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String description = rs.getString("description");
                boolean estTerminee = rs.getBoolean("est_terminee");
                Timestamp dateCreation = rs.getTimestamp("date_creation");
                
                Tache tache = new Tache(description);
                tache.setEstTerminee(estTerminee);
                tache.setDateCreation(dateCreation.toLocalDateTime());
                taches.add(tache);
            }
            System.out.println("Tâches triées par ordre alphabétique !");
        } catch (SQLException e) {
            System.out.println("Erreur lors du tri : " + e.getMessage());
        }
        return taches;
    }

    public void trierTaches() {
        ArrayList<Tache> tachesTriees = getTachesTriees();
        if (tachesTriees.isEmpty()) {
            System.out.println("Aucune tâche à trier.");
            return;
        }
        for (int i = 0; i < tachesTriees.size(); i++) {
            System.out.println((i + 1) + ". " + tachesTriees.get(i));
        }
    }
} 