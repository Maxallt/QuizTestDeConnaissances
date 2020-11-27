package gestionCategories;

import java.util.Scanner;
import java.util.ArrayList;

public class TestCategorie {
	static Scanner entree = new Scanner(System.in);
	static ArrayList<String> nomsCategories = new ArrayList<>();
	
	public static boolean continuer() {
		boolean correct = false;
		int choix = -1; //valeur impossible
		
		System.out.println("Voulez vous continuer(1) ou quitter(0) ?");
		do {
			if (entree.hasNextInt()) {
				choix = entree.nextInt();
			}
			if (choix == 0 || choix == 1) {
				correct = true;
			} else {
				System.out.println("Vous n'avez pas saisi 0 ou 1, recommencez !");
			}
			entree.nextLine(); //vide le tampon
		} while(!correct);
		
		return choix == 1 ? true : false;
	}
	
	public static void testCategorieSansArgument() {
		System.out.println("Test : Création d'une catégorie sans argument =>\n"
				+ "-----------------------------------");
		Categorie premiereCategorie = new Categorie();
		System.out.println("Affichage de la catégorie créée : \n"
				+ premiereCategorie.toString());
		System.out.println("Test réussi !\n");
	}
	
	public static void testCategorieAvecNom() {
		boolean correct = true;
		System.out.println("Test : Création d'une catégorie avec un nom =>\n"
				+ "-----------------------------------");
		System.out.print("Entrez le nom de votre catégorie : ");
		String nomDeuxiemeCategorie;
		do {
			nomDeuxiemeCategorie = entree.nextLine();
			/* Réduction si nécessaire du nom à sa taille max */
			if (nomDeuxiemeCategorie.length() >= Categorie.NOMBRE_CARACTERE_MAX ) {
				nomDeuxiemeCategorie = nomDeuxiemeCategorie.substring(0,Categorie.NOMBRE_CARACTERE_MAX);
			}
			/* Vérification inexsitant pour éviter les homonymes NE MARCHE PAS*/   
/*			for (int i = 0; i < nomsCategories.size(); i++) {
				if (nomDeuxiemeCategorie.equalsIgnoreCase(nomsCategories.get(i))) {
					correct = false;
					System.out.println("Nom déjà existant veuillez en trouver un autre !");
				} else if (i == nomsCategories.size()-1) {
					correct = true;
				}
			}*/
		} while (!correct);
		
		Categorie deuxiemeCategorie = new Categorie(nomDeuxiemeCategorie);
		/* En attendant la mise en BD */
		nomsCategories.add(nomDeuxiemeCategorie);
		System.out.println(deuxiemeCategorie.toString());
		System.out.println("Test réussi !\n");
	}
	
	//TODO
	public static void testCategorieAvecNomPhoto() {
		System.out.println("A voir avec affichage de photo pour vérifier");
	}
	
	public static void affichNomCategorie() {
		System.out.print("Noms des catégories enregistrés[ ");
		for(int i = 0; i < nomsCategories.size(); i++) {
			System.out.print(nomsCategories.get(i) +" | ");
		}
		System.out.println("]");
	}
	
	public static void testCategorie() {
		boolean correct = false;
		int choix = -1; //valeur impossible
		
		System.out.println("Que voulez-vous tester : \n"
				+ "(1) Création sans argument\n"
				+ "(2) Création avec un nom\n"
				+ "(3) Création avec un nom et une photo\n"
				+ "(4) Les 3\n"
				+ "(5) Afficher les noms des catégories créées");
		
		do {
			if (entree.hasNextInt()) {
				choix = entree.nextInt();
			}
			if (choix == 1 || choix == 2 || choix == 3 || choix == 4 || choix == 5) {
				correct = true;
			} else {
				System.out.println("Vous n'avez pas saisi 1, 2, 3 ou 4, recommencez !");
			}
			entree.nextLine(); //vide le tampon
		} while(!correct);
		
		switch(choix) {
		case 1:
			testCategorieSansArgument();
			break;
		case 2:
			testCategorieAvecNom();
			break;
		case 3:
			testCategorieAvecNomPhoto();
			break;
		case 4:
			testCategorieSansArgument();
			testCategorieAvecNom();
			testCategorieAvecNomPhoto();
			break;
		case 5:
			affichNomCategorie();
			break;
		default:
			break;
		}
	}
	
	//TODO
	public static void testSousCategorie() {
		
	}
	
	public static void menu() {
		boolean correct = false;
		int choix = -1; //valeur impossible
		
		System.out.println("Voulez vous créer une catégorie(1) ou une sous-catégorie(2) ?");
		do {
			if (entree.hasNextInt()) {
				choix = entree.nextInt();
			}
			if (choix == 1 || choix == 2) {
				correct = true;
			} else {
				System.out.println("Vous n'avez pas saisi 1 ou 2, recommencez !");
			}
			entree.nextLine(); //vide le tampon
		} while(!correct);
		
		switch(choix) {
		case 1:
			testCategorie();
			break;
		case 2:
			testSousCategorie();
			break;
		default:
			break;
		}
	}
	
	public static void main(String[] args) {
		do {
			menu();
		} while (continuer());
	}
}
