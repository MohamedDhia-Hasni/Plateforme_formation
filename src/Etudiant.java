// File: Etudiant.java
// Cette classe représente un étudiant, qui peut s'inscrire à des formations.
import java.util.ArrayList;
import java.util.List;

public class Etudiant extends Utilisateur {
    private List<Formation> inscriptions;

    public Etudiant(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
        this.inscriptions = new ArrayList<>();
    }

    public List<Formation> getInscriptions() {
        return inscriptions;
    }

    public void sinscrireFormation(Formation formation) throws FormationDejaInscriteException {
        if (inscriptions.contains(formation)) {
            throw new FormationDejaInscriteException("Vous êtes déjà inscrit à cette formation.");
        }
        inscriptions.add(formation);
    }
}