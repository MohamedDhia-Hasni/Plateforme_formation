// File: DatabaseSetup.java
// Cette classe configure la base de données en créant les tables nécessaires.
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    // Méthode pour configurer la base de données (création des tables)
    public static void configuration() {
        try (Connection connexion = DatabaseConnection.obtenirConnexion();
             Statement instruction = connexion.createStatement()) {

            String creerTableUtilisateurs = "CREATE TABLE IF NOT EXISTS Utilisateurs (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(255), " +
                    "email VARCHAR(255) UNIQUE, " +
                    "motDePasse VARCHAR(255), " +
                    "type VARCHAR(50)" +
                    ");";

            String creerTableFormations = "CREATE TABLE IF NOT EXISTS Formations (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "titre VARCHAR(255), " +
                    "description TEXT, " +
                    "formateurId INT, " +
                    "prix DOUBLE, " +
                    "FOREIGN KEY(formateurId) REFERENCES Utilisateurs(id)" +
                    ");";

            String creerTableInscriptions = "CREATE TABLE IF NOT EXISTS Inscriptions (" +
                    "etudiantId INT, " +
                    "formationId INT, " +
                    "PRIMARY KEY(etudiantId, formationId), " +
                    "FOREIGN KEY(etudiantId) REFERENCES Utilisateurs(id), " +
                    "FOREIGN KEY(formationId) REFERENCES Formations(id)" +
                    ");";

            instruction.execute(creerTableUtilisateurs);
            instruction.execute(creerTableFormations);
            instruction.execute(creerTableInscriptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
