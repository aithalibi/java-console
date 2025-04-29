import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/taches_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection conn = null;
    
    public static Connection getConnection() {
        if (conn == null) {
            try {
                // Charger explicitement le driver
                System.out.println("Tentative de chargement du driver MySQL...");
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver MySQL chargé avec succès!");
                
                // Établir la connexion
                System.out.println("Tentative de connexion à : " + URL);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion réussie à la base de données!");
            } catch (SQLException e) {
                System.out.println("Erreur SQL lors de la connexion :");
                System.out.println("Code d'erreur : " + e.getErrorCode());
                System.out.println("Message : " + e.getMessage());
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Driver MySQL non trouvé dans le classpath!");
                System.out.println("Assurez-vous que mysql-connector-j est présent dans le dossier lib/");
                System.out.println("Message : " + e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
                System.out.println("Connexion à la base de données fermée.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
} 