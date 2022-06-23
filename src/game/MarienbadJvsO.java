package game;

import utilities.SimpleInput;

/**
 * Programme simulant le jeu du Marienbad en joueur contre ordinateur.
 * @author N.Jomaa
*/
public class MarienbadJvsO
{
	//Variable fin présente ici car utilisées dans les différentes méthodes
	static int fin = 0; //Variable pour l'arrêt du jeu lorsqu'il n'y a plus d'allumettes.
	
	/**
	 * Cette fonction est la fonction principale du programme, c'est celle qui est appelée au lancement du
	 * programme. C'est dans cette fonction qu'il est demandé à l'utilisateur d'entrer le nom du joueur
	 * qui jouera contre l'ordinateur, le nombre de lignes du plateau de jeu et qui commencera la partie.
	 */
	public static void lancement() {
		//Partie initialisation des variables
		String nomJ = SimpleInput.getString("Saisir le nom du joueur qui jouera contre l'ordinateur : ");
		boolean AQuiLeTour = SimpleInput.getBoolean("Saisir 'true' pour que le joueur commence, 'false' pour que l'ordinateur commence : ");
		
		//Définition de la taille et initialisation du tableau.
		int[] plateau = tableau(SimpleInput.getInt("Saisir le nombre de lignes du plateau : "));
		
		System.out.println("Jeu du Marienbad en joueur contre ordinateur, le gagnant est celui qui prend une ou des allumette(s) en dernier");
		jeu(plateau, nomJ, AQuiLeTour);
		
		//Activation des méthodes de test, test = true.
		boolean test = false;
		if (test)
		{
			testPairsImpairs();
		}	
	}
	
	/**
	* Méthode pour créer er remplir le tableau pour la création du plateau.
	* @param taille ; variable pour définir la taille du tableau.
	* @return le plateau.
	*/
	static int[] tableau(int taille)
	{
		while (taille <= 0) {																			//Boucle pour vérifier que le valeur de taille est > 0
			taille = SimpleInput.getInt("Valeur incorrect, saisir le nombre de lignes du plateau : ");	//int[] plateau doit être placé après
		}
		
		int[] plateau = new int[taille]; //Doit être placé après la boucle de vérification de la taille.
		plateau[0] = 1; //Définission de la première case du tableau pour éviter les bug dans la boucle.
		for (int i = 1; i < plateau.length; i++) //Boucle pour remplir le tableau.
		{
			plateau[i] = plateau[i - 1] + 2; //+2 pour ajouter le nombre de barre à partir de la case précédente.
		}
		return plateau;
	}
	
	/**
	* Méthode pour afficher le plateau depuis le tableau.
	* @param t ; le tableau à afficher.
	*/
	static void affichageJeu(int[] t)
	{
		int i = 0;
		//Boucle pour passer à la case suivant du tableau.
		while (i < t.length)
		{
			System.out.println();
			System.out.print(i + " : ");
			//Boucle pour afficher un nombre de barre en fonction du nombre contenu dans la case du tableau.
			for (int nbrBarre = 0; nbrBarre < t[i]; nbrBarre++) //Variable nbrBarre qui s'incrémente à chaque barre affiché
																//tant que la valeur de la case du tableau n'est pas dépassée.
			{
				System.out.print ("|  "); //Affichage des barres.
			}
			System.out.println();
			System.out.println();
			i = i + 1;
		}
	
	}
	
	/**
	* Méthode pour faire avancer le jeu.
	* @param plateau ; le tableau à utiliser.
	* @param nomJ ; le nom du joueur.
	* @param AQuiLeTour ; le booléen pour déterminer qui joue.
	*/
	static void jeu(int[] plateau, String nomJ, boolean AQuiLeTour)
	{
		//Boucle pour ajouter le nombre totales d'allumettes dans la variable fin
		int i = 0;
		while (i < plateau.length)
		{
			fin = fin + plateau[i]; //Ajout des valeurs du tableau dans la variable.
			i = i + 1;
		}
		
		while (fin > 0)
		{
			affichageJeu(plateau);
			if (AQuiLeTour)
			{
				System.out.println("Au tour de " + nomJ);
				AQuiLeTour = false; //Inversion de la variable pour faire jouer l'ordinateur
				modifJeuJ(plateau);
			}
			else
			{
				System.out.println("Au tour de l'ordinateur");
				AQuiLeTour = true; //Inversion de la variable pour faire jouer le joueur
				int[] tab = conversionBinaireCalculColonne(plateau);
				boolean res = pairsImpairs(tab);
				if (res)
				{
					modifJeuOrdiPerdant(plateau);
				}
				else
				{
					strategieGagnante(plateau);
				}
			}
		}
		if (AQuiLeTour)
		{
			System.out.println("Fin du jeu, le gagnant est l'ordinateur");
		}
		else
		{
			System.out.println("Fin du jeu, le gagnant est " + nomJ);
		}
		System.out.println("\n****************************************************************************************************\n");
	}
	
	/**
	* Méthode pour modifier le tableau par le joueur.
	* @param t ; le tableau à modifier.
	*/
	static void modifJeuJ(int[] t)
	{
		int ligne = SimpleInput.getInt("Choisir la ligne : "); //Variable pour choisir la ligne.
		int nbrAllumettes = SimpleInput.getInt("Choisir le nombre d allumette(s) a enlever : "); //Variable pour choisir le nombre d'allumettes.
		if (ligne < t.length && ligne >= 0 && nbrAllumettes <= t[ligne] && nbrAllumettes > 0)
		{
			t[ligne] = t[ligne] - nbrAllumettes; //Modification de la case du tableau.
			fin = fin - nbrAllumettes; //Supression des allumettes présentes dans le total.
		}
		else
		{
			System.out.println("Les valeurs choisies sont incorrectes.");
			modifJeuJ(t);
		}
	}
	
	/**
	* Méthode pour modifier le tableau du plateau de jeu par l'ordinateur en stratégie perdante.
	* @param t ; le tableau à modifier.
	*/
	static void modifJeuOrdiPerdant(int[] t)
	{
		int ligne = 0 + (int)(Math.random() * t.length); //Variable pour choisir la ligne.
		System.out.println("Ligne choisi par l ordinateur : " + ligne);
		int nbrAllumettes = 1 + (int)(Math.random() * t[ligne]); //Variable pour choisir le nombre d'allumettes.
		System.out.println("Nombre d allumettes enlever par l ordinateur : " + nbrAllumettes);
		if (ligne < t.length && ligne >= 0 && nbrAllumettes <= t[ligne] && nbrAllumettes > 0)
		{
			t[ligne] = t[ligne] - nbrAllumettes; //Modification de la case du tableau.
			fin = fin - nbrAllumettes; //Supression des allumettes présentes dans le total.
		}
		else
		{
			modifJeuOrdiPerdant(t);
		}
	}
	
	/**
	* Méthode pour modifier le tableau du plateau de jeu par l'ordinateur en stratégie gagnante.
	* @param t ; le tableau à modifier.
	*/
	static void strategieGagnante (int[] t)
	{
		//int ligne = 0;
		int nbrAllumettes = 0;
		
		//Copie du tableau pour récupérer les valeurs d'origines des colonnes testés.
		int[] copieT = new int [t.length];
        for(int i = 0 ; i < t.length ; i++)
        {
           copieT[i] = t[i];
           
        }
        
		//Boucle pour modifier colonne par colonne et tester si le tableau est pair/impair
		int i = 0;
		boolean win = false;
		
		while (i < copieT.length && !win)
		{
			
			if (copieT[i]  != 0)
			{
				copieT[i] = copieT[i] - 1;
				nbrAllumettes = nbrAllumettes + 1;
				int[] tab = conversionBinaireCalculColonne(copieT);
				win = pairsImpairs(tab);
			}
			else 
			{
				
				copieT[i] = t[i];
				nbrAllumettes = 0;
				
				i = i + 1;
			}
		}
		t[i] = t[i] - nbrAllumettes; //Modification de la case du tableau.
		System.out.println("Ligne choisi par l ordinateur : " + i);
		System.out.println("Nombre d allumettes enlever par l ordinateur : " + nbrAllumettes);
		fin = fin - nbrAllumettes; //Supression des allumettes présentes dans le total.
	}
	
	/**
	* Méthode qui convertit le plateau de jeu en tableau binaire, puis qui aditionne
	* les "colonnes" pour utilisation par la méthode pairsImpairs().
	* @param t ; le tableau à convertir.
	* @return le tableau contenant les données utiliser par la méthode pairsImpairs() (ex: 2 2 4).
	*/
	static int[] conversionBinaireCalculColonne(int[] t)
	{
		String[] tab = new String[t.length];
		for (int i = 0 ; i < t.length ; i++)
		{
			tab[i] = Integer.toBinaryString(t[i]); //Conversion du nombre entier en chaîne contenant le nombre en binaire.
		}
		int total = 0;
		for (int i = 0 ; i < tab.length ; i++)
		{
			total = total + Integer.parseInt(tab[i]); //Convertis une chaîne de caractère de chiffres en nombre entier.
		}
		int taille = 0;
		String number = String.valueOf(total); //Convertissement en chaine de caractére (ASCII) pour pouvoir prendre chaque partie du nombre
        int[] stockage = new int[100];
        for(int i = 0; i < number.length(); i++)
        {
            int j = Character.digit(number.charAt(i), 10); //Si c'est une chiffre ASCII, renvoie la valeur numérique du caractère String (ASCII -> chifffre)                                                                    ///Don
            stockage[i] = j;
            taille = taille + 1;
        }
        int[] tabRepBinaire = new int[taille];
        for (int i = 0; stockage[i] != 0; i++)
        {
			tabRepBinaire[i] = stockage[i];
		}
		//affichage(tabRepBinaire);
        return tabRepBinaire;
	}
	
	/**
	* Méthode pour tester toutes les cases du tableau en paramètre.
	* @param t ; résultat donné par la méthode conversionBinaireCalculColonne()
	* @return <code> true </code> si le résultat est pair, <code> false </code> sinon.
	*/
	static boolean pairsImpairs (int[] t)
	{
		boolean impair = true;
		
		for (int i = 0; i < t.length; i++)
		{
			if (t[i]%2 != 0)
			{
				impair = false;
			}
		}
		return impair;
	}
	
	/**
	* Méthode de test pour la méthode pairsImpairs()
	*/
	static void testPairsImpairs()
	{
        int[] tab = new int [3];
        int val = 0;
        System.out.println();
        System.out.println();
        System.out.println("**** testPairsImpairs");
        
        //Accolades car réutilisation des mêmes noms de variables pour les tests.
		{ //Test n°1
		System.out.println();
        System.out.println("**** testPairsImpairs n 1");
        System.out.println("Le resultat doit etre true");
        System.out.println("Saisir 2, 2, 4");
        for (int i = 0; i < tab.length; i++)
        {
			val = SimpleInput.getInt("Entrez une valeur superieur a -1 : ");
			if (val > -1)
			{
				tab[i] = val;
			}
			else
			{
				i = i - 1;
			}
		}
		boolean res = pairsImpairs(tab);
		System.out.println("Resultat = " + res);
		}
		{ //Test n°2
		System.out.println();
        System.out.println("**** testPairsImpairs n 2");
        System.out.println("Le resultat doit etre false");
        System.out.println("Saisir 2, 2, 3");
        for (int i = 0; i < tab.length; i++)
        {
			val = SimpleInput.getInt("Entrez une valeur superieur a -1 : ");
			if (val > -1)
			{
				tab[i] = val;
			}
			else
			{
				i = i - 1;
			}
		}
		boolean res = pairsImpairs(tab);
		System.out.println("Resultat = " + res);
		}
	}
}
