// File: Formateur.java
// Cette classe étend 'Utilisateur' et gère les formations proposées par un formateur.
import java.util.ArrayList;
import java.util.List;

public class Formateur extends Utilisateur {
    private List<Formation> formations;

    public Formateur(String nom, String email, String motDePasse) {
        super(nom, email, motDePasse);
        this.formations = new ArrayList<>();
    }

    public List<Formation> getFormations() {
        return formations;
    }

    public void ajouterFormation(Formation formation) {
        formations.add(formation);
    }
}