package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import gestionQuestion.DaoQuestions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LancementPerfectionnementAvanceesController implements Initializable {
	
	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
    @SuppressWarnings("unused")
	private Stage stage;
    
    /** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
	
	@FXML 
	private Text titreQuestion;
	
	@FXML 
	private Text numeroQuestion;
	
	@FXML 
	private Button buttonRetour;
	
	@FXML
	private Button reponseA;
	
	@FXML
	private Button reponseB;
	
	@FXML 
	private Button reponseC;
	
	@FXML
	private Button reponseD;
	
	@FXML
	private Button reponseE;
	
	private int numQuestion = 0;
	
	/** Nombre de bonne r�ponse quand le joueur aura juste */
	static int nbBonnesReponses;
	
	/** R�cup�re ele nombre de bonnes r�ponses dans le r�sultat final */
	public static int getNbBonnesReponses() {
		return nbBonnesReponses;
	}

	private static ArrayList<String> reponsesCourantes;
	
	private static String reponseCorrecte;
	
	private static String questionActuelle;
	
   private static int tailleListe = ChoixFormatQuestionnaireController.listeQuestions.size();
	
	private static int nbQuestions = ChoixFormatQuestionnaireController.getNombreQuestion();
	
	/**
	 *  Initialise les champs de texte pour les questions et les r�ponses
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		reponseA.setWrapText(true);
		reponseB.setWrapText(true);
		reponseC.setWrapText(true);
		reponseD.setWrapText(true);
		reponseE.setWrapText(true);
		
	    /* Lancement du jeu avec les questions */
		if (tailleListe == 0) {
			// TODO: Fenetre pop up d'avertissement
			
			nbBonnesReponses = -1;
			resultatFinal();
			System.out.println("Impossible de lancer le jeu, aucune question n'existe avec ces criteres. Veuillez en cr�er");
			
		} else {
			nbBonnesReponses = 0;
			numeroQuestion.setText("Question n� " + (numQuestion+1));
			
			questionActuelle = ChoixFormatQuestionnaireController.listeQuestions.get(0);
			reponsesCourantes = DaoQuestions.getReponses(questionActuelle);
			reponseCorrecte = reponsesCourantes.get(0);
			
			titreQuestion.setText(questionActuelle);
			
			// R�ponses possibles de la question
			affichageAleatoireReponses(reponsesCourantes);
		}
	
			
	}
	
	@FXML
	public void changementQuestion(ActionEvent e) {
		
		compteurBonneReponse(e);
		numQuestion++;
		if (tailleListe > numQuestion+1) {
			
			questionActuelle = ChoixFormatQuestionnaireController.listeQuestions.get(numQuestion);
			reponsesCourantes = DaoQuestions.getReponses(questionActuelle);
			reponseCorrecte = reponsesCourantes.get(0);
			
			numeroQuestion.setText("Question n� " + (numQuestion+1));
	
			titreQuestion.setText(questionActuelle);
			
			// R�ponses possibles de la question
			affichageAleatoireReponses(reponsesCourantes);
		} 
		
		if (tailleListe == 0
				|| numQuestion == nbQuestions) {
			resultatFinal();
		}

	}
	
	
	/** Methode qui va incrementer le score de 1 
	 * chaque fois que l'utilisateur aura la bonne reponse
	 * Dans le cas contraire la methode enregistre la question
	 * pour l'utiliser dans le perfectionnement avanc�s
	 * @param e Bouton appuyer par l'utilisateur
	 */
	private void compteurBonneReponse(ActionEvent e) {
        Button btn = (Button) e.getSource();
        System.out.println("Compteur de point\n");
		if (btn.getText().equals(reponseCorrecte)) {
			nbBonnesReponses++;	
		} 
			
	}
	
	/**
     * M�thode qui affiche la fen�tre pr�c�dente
     * Ici l'accueil
     * Li�e � buttonRetour
     */
    @FXML
	public void retour() {
		try {
		
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * M�thode qui affiche la fen�tre du r�sultat final
     */
    public void resultatFinal() {
		try {
		
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreResultatFinalPerfectionnementAvancees.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * M�thode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface � afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
    /**
	 * M�thode : apparition des r�ponses de mani�res al�atoires 
	 * sur les 5 boutons de jeu
	 * Fait appel � 4 autres m�thodes ainsi permettant de designer correctement
	 * la fen�tre de jeu
	 * @param la liste contenant toutes les r�ponses de la question pos�
	 */
	public void affichageAleatoireReponses(ArrayList<String> listeReponses) {
		
		/* V�rifie si il y certaine r�ponse fausses vides ou nulles */
		boolean reponseFausse4EstNull = listeReponses.get(4)==null 
									 || listeReponses.get(4).equals("");
		boolean reponseFausse3EstNull = listeReponses.get(3)==null 
									 || listeReponses.get(3).equals("");
		boolean reponseFausse2EstNull = listeReponses.get(2)==null 
									 || listeReponses.get(2).equals("");
		
		/* Si il y a seulement la reponse-vrai et la reponse_fausse1
		 * alors affichage sur les 2 premiers boutons
		 * 
		 * Si il y a la reponse-vrai, la reponse_fausse1,  et la reponse_fausse2
		 * alors affichage sur les 3 premiers boutons
		 * 
		 * Si il y a la reponse-vrai, la reponse_fausse1, la reponse_fausse2 
		 * et la reponse_fausse3
		 * alors affichage sur les 4 premiers boutons
		 * 
		 * Si il y la reponse-vrai et toutes les reponses_fausses
		 * alors affichage sur tous les boutons
		 */
		if (reponseFausse2EstNull && reponseFausse3EstNull
		 && reponseFausse4EstNull ) {
			affichage2Reponses(listeReponses);
		} else if (reponseFausse3EstNull && reponseFausse4EstNull) {
			affichage3Reponses(listeReponses);
		} else if (reponseFausse4EstNull) {
			affichage4Reponses(listeReponses);
		} else  {
			affichage5Reponses(listeReponses);
		}
		
	}
	
	/**
	 * M�thode : qui affiche les r�ponses, al�atoirement, 
	 * dans l'ordre des 2 premiers boutons
	 * @param la liste contenant toutes les r�ponses de la question pos�
	 */
	public void affichage2Reponses(ArrayList<String> listeReponses) {
		Random r = new Random();
		int tailleListe = listeReponses.size()-3;
		int aleatoireA;
		int aleatoireB;
		
		// Tirage aux sorts des r�ponses
		do { //premi�re mesure
			aleatoireA = r.nextInt(tailleListe);
			aleatoireB = r.nextInt(tailleListe);
					
				// Si des r�ponses sont identiques, on red�marre la boucle
		} while(aleatoireA==aleatoireB);
				
				reponseA.setText(listeReponses.get(aleatoireA));
				reponseB.setText(listeReponses.get(aleatoireB));
				// R�ponses vides
				reponseC.setText("");
				reponseD.setText("");
				reponseE.setText("");
	}
		
	/**
	 * M�thode : qui affiche les r�ponses, al�atoirement, 
	 * dans l'ordre des 3 premiers boutons
	 * @param la liste contenant toutes les r�ponses de la question pos�
	 */
	public void affichage3Reponses(ArrayList<String> listeReponses) {
		Random r = new Random();
		int tailleListe = listeReponses.size()-2;
		int aleatoireA;
		int aleatoireB;
		int aleatoireC;
		
		// Tirage aux sorts des r�ponses
				do { //premi�re mesure
					aleatoireA = r.nextInt(tailleListe);
					aleatoireB = r.nextInt(tailleListe);
					aleatoireC = r.nextInt(tailleListe);
					
				// Si des r�ponses sont identiques, on red�marre la boucle
				} while(   aleatoireA==aleatoireB || aleatoireA==aleatoireC
						|| aleatoireB==aleatoireC);
				
				reponseA.setText(listeReponses.get(aleatoireA));
				reponseB.setText(listeReponses.get(aleatoireB));
				reponseC.setText(listeReponses.get(aleatoireC));
				// R�ponses vides
				reponseD.setText("");
				reponseE.setText("");
	}
	
	/**
	 * M�thode : qui affiche les r�ponses, al�atoirement, 
	 * dans l'ordre des 4 premiers boutons
	 * @param la liste contenant toutes les r�ponses de la question pos�
	 */
	public void affichage4Reponses(ArrayList<String> listeReponses) {
		Random r = new Random();
		int tailleListe = listeReponses.size()-1;
		int aleatoireA;
		int aleatoireB;
		int aleatoireC;
		int aleatoireD;
		
		System.out.println("Taille liste:" + tailleListe);
		// Tirage aux sorts des r�ponses
				do { //premi�re mesure
					aleatoireA = r.nextInt(tailleListe);
					aleatoireB = r.nextInt(tailleListe);
					aleatoireC = r.nextInt(tailleListe);
					aleatoireD = r.nextInt(tailleListe);
					
				// Si des r�ponses sont identiques, on red�marre la boucle
				} while(   aleatoireA==aleatoireB || aleatoireA==aleatoireC
						|| aleatoireA==aleatoireD || aleatoireB==aleatoireC
						|| aleatoireB==aleatoireD || aleatoireC==aleatoireD);
				
				
				reponseA.setText(listeReponses.get(aleatoireA));
				reponseB.setText(listeReponses.get(aleatoireB));
				reponseC.setText(listeReponses.get(aleatoireC));
				reponseD.setText(listeReponses.get(aleatoireD));
				// R�ponse vide
				reponseE.setText("");
	}
	
	/**
	 * M�thode : qui affiche les r�ponses, al�atoirement, 
	 * dans l'ordre des 5 boutons
	 * @param la liste contenant toutes les r�ponses de la question pos�
	 */
	public void affichage5Reponses(ArrayList<String> listeReponses) {
		Random r = new Random();
		int tailleListe = listeReponses.size();
		int aleatoireA;
		int aleatoireB;
		int aleatoireC;
		int aleatoireD;
		int aleatoireE;
		
		// Tirage aux sorts des r�ponses
		do { //premi�re mesure
			aleatoireA = r.nextInt(tailleListe);
			aleatoireB = r.nextInt(tailleListe);
			aleatoireC = r.nextInt(tailleListe);
			aleatoireD = r.nextInt(tailleListe);
			aleatoireE = r.nextInt(tailleListe);
			 
		// Si des r�ponses sont identiques, on red�marre la boucle
		} while(   aleatoireA==aleatoireB || aleatoireA==aleatoireC
				|| aleatoireA==aleatoireD || aleatoireA==aleatoireE
				|| aleatoireB==aleatoireC || aleatoireB==aleatoireD
				|| aleatoireB==aleatoireE || aleatoireC==aleatoireD
				|| aleatoireC==aleatoireE || aleatoireD==aleatoireE);
		
		// Toutes les r�ponses
		reponseA.setText(listeReponses.get(aleatoireA));
		reponseB.setText(listeReponses.get(aleatoireB));
		reponseC.setText(listeReponses.get(aleatoireC));
		reponseD.setText(listeReponses.get(aleatoireD));
		reponseE.setText(listeReponses.get(aleatoireE));
	}
}