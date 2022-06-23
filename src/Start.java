import java.io.IOException;
import game.*;
import utilities.SimpleInput;

/**
 * Programme de lancement du jeu Marienbad dans le mode souhaité.
 * @since JDK 18.0.1.1
 * @author N.Jomaa
*/
public class Start {
    /**
     * Méthode principale du programme. Choix du mode de jeu.
     */
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le jeu Marienbad !");
        try {
            // Vide la console pour l'affichage du jeu.
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Bienvenue dans le jeu du Marienbad.");
        System.out.println("Vous pouvez jouer en mode solo contre l'ordinateur (JvsO) ou en mode multijoueur joueur contre joueur (JvsJ).");

        modeJeu();
    }

    static void modeJeu() {
        boolean choixJeu = true;
        do {
            // Demande à l'utilisateur de choisir un mode de jeu.
            String modeJeu = SimpleInput.getString("Veuillez choisir le mode de jeu ('JvsO' OU 'JvsJ') : ");
            // Vérifie si l'entrée utilisateur est correct et lance le mode de jeu choisi,
            // sinon, affiche un message d'erreur et demande à l'utilisateur de choisir un mode de jeu valide.
            if (modeJeu.equals("JvsO")) {
                choixJeu = false;
                System.out.println("Mode de jeu choisi : JvsO");
                System.out.println("Vous allez jouer en mode solo contre l'ordinateur.");
                System.out.println("Lancement du jeu...");
                try {
                    // Attente de 5 seconde pour permettre la lecture.
                    Thread.sleep(5000);
                    // Vide la console pour l'affichage du jeu.
                    if (System.getProperty("os.name").contains("Windows")) {
                        // Méthode pour Windows.
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    }
                    else {
                        // Méthode pour Linux.
                        System.out.print("\033\143");
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
                MarienbadJvsO.lancement();
            } else if (modeJeu.equals("JvsJ")) {
                choixJeu = false;
                System.out.println("Mode de jeu choisi : JvsJ");
                System.out.println("Vous allez jouer en mode multijoueur joueur contre joueur.");
                System.out.println("Lancement du jeu...");
                try {
                    // Attente de 5 seconde pour permettre la lecture.
                    Thread.sleep(5000);
                    // Vide la console pour l'affichage du jeu.
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
                MarienbadJvsJ.lancement();
            } else {
                System.out.println("Veuillez choisir un mode de jeu valide. \n");
            }
        } while (choixJeu);
        relance();
    }

    static void relance() {
        boolean rejouer = true;
        do {
            String rejouerStr = SimpleInput.getString("Voulez-vous rejouer ? ('Oui' OU 'Non') : ");
            // Vérifie si l'entrée utilisateur est correct et relance ou non le jeu,
            // sinon, affiche un message d'erreur et demande à l'utilisateur de choisir une option valide.
            if (rejouerStr.equals("Oui") || rejouerStr.equals("oui") || rejouerStr.equals("O") || rejouerStr.equals("o")) {
                rejouer = false;
                modeJeu();
            } else if (rejouerStr.equals("Non") || rejouerStr.equals("non") || rejouerStr.equals("N") || rejouerStr.equals("n")) {
                rejouer = false;
            } else {
                System.out.println("Veuillez choisir une option valide. \n");
            }
        } while (rejouer);
    }
}
