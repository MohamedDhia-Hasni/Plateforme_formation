// File: DatabaseConnection.java
// Cette classe gère la connexion à la base de données MySQL.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/plateforme_formation";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = "";

    // Méthode pour obtenir la connexion à la base de données
    public static Connection obtenirConnexion() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }
}