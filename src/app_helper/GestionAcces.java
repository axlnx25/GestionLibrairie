package app_helper;

import app_model.Utilisateur;
import javafx.scene.Node;

public class GestionAcces {

    /**
     * Vérifie le rôle de l'utilisateur et applique les restrictions sur les éléments passés.
     * @param elements Les éléments (boutons, menus...) à masquer pour les non-admins(utilisation d'une varargs)
     */
    public static void restrictForNonAdmin(Node... elements) {
        Utilisateur user = Session.getInstance().getUtilisateur();

        if (user == null) return;

        String role = user.getRoleUtilisateur();
        if (!"Admin".equalsIgnoreCase(role)) {
            for (Node node : elements) {
                if (node != null) {
                    node.setVisible(false);
                }
            }
        }
    }

    /**
     * Vérifie si l'utilisateur connecté est administrateur.
     * peut être utilisé dans les écrans dont l'accès n'est autorisé qu'à l'admin
     */
    public static boolean isAdmin() {
        Utilisateur user = Session.getInstance().getUtilisateur();
        return user != null && "Administrateur".equalsIgnoreCase(user.getRoleUtilisateur());
    }
}

