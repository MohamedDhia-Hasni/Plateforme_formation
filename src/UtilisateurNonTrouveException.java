// File: UtilisateurNonTrouveException.java
// Exception levée lorsqu'un utilisateur tente de se connecter avec des informations incorrectes.
public class UtilisateurNonTrouveException extends Exception {
    public UtilisateurNonTrouveException(String message) {
        super(message);
    }
}