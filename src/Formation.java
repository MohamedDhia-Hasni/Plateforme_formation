// File: Formation.java
// Cette classe représente une formation disponible sur la plateforme.
public class Formation {
    private String titre;
    private String description;
    private Formateur formateur;
    private double prix;

    public Formation(String titre, String description, Formateur formateur, double prix) {
        this.titre = titre;
        this.description = description;
        this.formateur = formateur;
        this.prix = prix;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}