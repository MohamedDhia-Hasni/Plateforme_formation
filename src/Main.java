// File: Main.java
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseSetup.configuration();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Bienvenue sur la plateforme de formation !");
            System.out.println("1. Se connecter");
            System.out.println("2. S'inscrire");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne restante

            switch (choix) {
                case 1:
                    seConnecter(scanner);
                    break;
                case 2:
                    sInscrire(scanner);
                    break;
                case 3:
                    System.out.println("Merci d'avoir utilisé notre plateforme. À bientôt !");
                    return;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        }
    }

    private static void seConnecter(Scanner scanner) {
        try (Connection connexion = DatabaseConnection.obtenirConnexion()) {
            System.out.print("Entrez votre email : ");
            String email = scanner.nextLine();
            System.out.print("Entrez votre mot de passe : ");
            String motDePasse = scanner.nextLine();

            String verifierUtilisateur = "SELECT * FROM Utilisateurs WHERE email = ? AND motDePasse = ?;";
            try (PreparedStatement ps = connexion.prepareStatement(verifierUtilisateur)) {
                ps.setString(1, email);
                ps.setString(2, motDePasse);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String type = rs.getString("type");
                        Utilisateur utilisateur;

                        // Créez l'objet utilisateur basé sur son type
                        if (type.equals("Formateur")) {
                            utilisateur = new Formateur(rs.getString("nom"), rs.getString("email"), rs.getString("motDePasse"));
                            System.out.println("Connexion réussie. Bienvenue, Formateur " + utilisateur.getNom() + " !");
                        } else {
                            utilisateur = new Etudiant(rs.getString("nom"), rs.getString("email"), rs.getString("motDePasse"));
                            System.out.println("Connexion réussie. Bienvenue, " + utilisateur.getNom() + " !");
                            // Afficher les formations inscrites pour les étudiants
                            afficherFormationsInscrites(connexion, email);
                        }
                    } else {
                        throw new UtilisateurNonTrouveException("Utilisateur non trouvé ou mot de passe incorrect.");
                    }
                }
            }
        } catch (UtilisateurNonTrouveException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur de base de données : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Une erreur inattendue s'est produite : " + e.getMessage());
        }
    }

    private static void sInscrire(Scanner scanner) {
        try (Connection connexion = DatabaseConnection.obtenirConnexion()) {
            System.out.print("Entrez votre nom : ");
            String nom = scanner.nextLine();
            System.out.print("Entrez votre email : ");
            String email = scanner.nextLine();
            System.out.print("Entrez votre mot de passe : ");
            String motDePasse = scanner.nextLine();

            // Demander à l'utilisateur de choisir son type (Formateur ou Etudiant)
            System.out.print("Êtes-vous un Formateur ou un Etudiant ? ");
            String type = scanner.nextLine().trim();

            // Vérifier le type de l'utilisateur
            Utilisateur utilisateur;
            if (type.equalsIgnoreCase("Formateur")) {
                utilisateur = new Formateur(nom, email, motDePasse);
            } else if (type.equalsIgnoreCase("Etudiant")) {
                utilisateur = new Etudiant(nom, email, motDePasse);
            } else {
                System.out.println("Type d'utilisateur invalide.");
                return;
            }

            // Ajouter l'utilisateur à la base de données
            String insertUtilisateur = "INSERT INTO Utilisateurs (nom, email, motDePasse, type) VALUES (?, ?, ?, ?);";
            try (PreparedStatement ps = connexion.prepareStatement(insertUtilisateur)) {
                ps.setString(1, utilisateur.getNom());
                ps.setString(2, utilisateur.getEmail());
                ps.setString(3, utilisateur.getMotDePasse());
                ps.setString(4, type); // Ajouter le type dans la base de données
                ps.executeUpdate();
                System.out.println("Inscription réussie !");
            }

            // Inscrire l'étudiant à une formation si c'est un étudiant
            if (utilisateur instanceof Etudiant) {
                System.out.print("Entrez l'ID de la formation à laquelle vous voulez vous inscrire : ");
                int formationId = scanner.nextInt();
                scanner.nextLine(); // Consommer la ligne restante

                // Vérifier si l'étudiant est déjà inscrit à cette formation
                try {
                    verifierInscriptionFormation(connexion, email, formationId);
                    inscrireEtudiantFormation(connexion, email, formationId);
                    System.out.println("Inscription à la formation réussie !");
                } catch (FormationDejaInscriteException e) {
                    System.out.println("Erreur d'inscription à la formation : " + e.getMessage());
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur de base de données : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Une erreur inattendue s'est produite : " + e.getMessage());
        }
    }
    private static void afficherFormationsInscrites(Connection connexion, String email) throws SQLException {
        String recupererFormations =
                "SELECT f.titre " +
                        "FROM Formations f " +
                        "JOIN Inscriptions i ON f.id = i.formationId " +
                        "JOIN Utilisateurs u ON i.etudiantId = u.id " +
                        "WHERE u.email = ?;";

        System.out.println("Vos formations inscrites :");
        try (PreparedStatement ps = connexion.prepareStatement(recupererFormations)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                boolean hasFormations = false;
                while (rs.next()) {
                    hasFormations = true;
                    String nom = rs.getString("titre");
                    System.out.println("- " + nom);
                }
                if (!hasFormations) {
                    System.out.println("Vous n'êtes inscrit à aucune formation pour le moment.");
                }
            }
        }
    }


    private static void verifierInscriptionFormation(Connection connexion, String email, int formationId) throws SQLException, FormationDejaInscriteException {
        String verifierInscription = "SELECT * FROM Inscriptions WHERE etudiantId = (SELECT id FROM Utilisateurs WHERE email = ?) AND formationId = ?;";
        try (PreparedStatement ps = connexion.prepareStatement(verifierInscription)) {
            ps.setString(1, email);
            ps.setInt(2, formationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    throw new FormationDejaInscriteException("Vous êtes déjà inscrit à cette formation.");
                }
            }
        }
    }

    private static void inscrireEtudiantFormation(Connection connexion, String email, int formationId) throws SQLException {
        String obtenirIdEtudiant = "SELECT id FROM Utilisateurs WHERE email = ?;";
        int etudiantId = -1;
        try (PreparedStatement ps = connexion.prepareStatement(obtenirIdEtudiant)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    etudiantId = rs.getInt("id");
                }
            }
        }

        String inscrireEtudiant = "INSERT INTO Inscriptions (etudiantId, formationId) VALUES (?, ?);";
        try (PreparedStatement ps = connexion.prepareStatement(inscrireEtudiant)) {
            ps.setInt(1, etudiantId);
            ps.setInt(2, formationId);
            ps.executeUpdate();
        }
    }
}
