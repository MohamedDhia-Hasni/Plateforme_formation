// File: FormationDejaInscriteException.java
// Exception levée lorsqu'un étudiant tente de s'inscrire à une formation déjà suivie.
public class FormationDejaInscriteException extends Exception {
    public FormationDejaInscriteException(String message) {
        super(message);
    }
}