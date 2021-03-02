/*
 * ChoixSousCategorieController.java		10/12/2020
 * Controller de l'affichage du choix de la sous-cat�gorie
 */
package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import gestionCategories.SousCategorie;
import gestionQuestion.DaoQuestions;
import gestionCategories.DAOCategorie;
import gestionCategories.DAOSousCategorie;

/**
 * Classe qui va permettre de choisir la sous-cat�gorie
 * pour le jeu en fonction de la cat�gorie choisie pr�c�demment
 * @author Maxime Alliot
 *
 */
public class ChoixSousCategorieController implements Initializable {

	 /** Identifiant de la sous-cat�gorie choisis par l'utilisateur */
    private static int idSousCat;
    
    /** Liste des questions correspondant � la sous-cat�gorie */
    static ArrayList<String> listeQuestions = new ArrayList<String>();
    
	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
    @SuppressWarnings("unused")
	private Stage stage;
	
    /** 
     * Permet de v�rifier si la ComboBox avec les sous-cat�gories a �t� 
     * initialis�e, c'est � dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
    private static boolean initialisation = false;
    
    /**
     * Permet de v�rifier si la ComboBox avec les formats a �t� 
     * initialis�e, c'est � dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
    private static boolean initialisationFormat = false;
    
    /**
     * Valeur du nombre de question selectionn�e en format
     */
    private static int nombreQuestion = -1;
    
    /**
     * Boolean d'indication pour la liste de sous categorie
     * True s'il est nulle
     * False sinon
     */
    private static boolean listeVide = true;
    /**
     * Getter pour r�cup�rer le nombre de question selectionn�
     * @return la valeur du format voulu
     */
    public static int getNombreQuestion() {
		return nombreQuestion;
	}

    /**
     * Setter pour mettre � jour la valeur du format voulu
     * @param nombreQuestion Nouveau format voulu
     */
	public static void setNombreQuestion(int nombreQuestion) {
		ChoixSousCategorieController.nombreQuestion = nombreQuestion;
	}

	/**
	 * Tableau qui regroupe les diff�rents formats de QCM
	 */
	private static int[] tabFormat = {5,10,20,30};
    
	/**
	 * M�thode obligatoire pour tous les controller
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /**
     * Button qui permettra de revenir sur la page pr�c�dente
     * Ici l'accueil
     */
    @FXML
    private Button buttonRetour;
    
    /**
     * Ce bouton set � valider le choix de sous-cat�gorie (facultatif)
     * et le choix du format
     */
    @FXML
    private Button buttonValider;
    
    /**
     * Liste qui regroupe toutes les sous cat�gories de la cat�gorie
     * choisie dans l'interface d'avant
     */
    @FXML
    private ComboBox<String> listeSousCat;
    
    /**
     * Liste qui regroupe tous les formats de QCM possibles
     */
    @FXML
    private ComboBox<String> listeFormat = new ComboBox<String>();
	
    /**
     * M�thode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface � afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
    /**
     * M�thode qui affiche la fen�tre pr�c�dente
     * Ici l'accueil
     * Li�e � buttonRetour
     */
    @FXML
	public void retour() {
		try {
			initialisation = false;
			initialisationFormat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreChoixCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * M�thode qui permet d'initialiser la ComboBox des formats
     * avec le tableau qui regroupe les diff�rentes possibilit�s
     */
    @FXML
    public void initializeFormat() {
    	if (!initialisationFormat) {
	    	for (int nombre: tabFormat) {
	    		listeFormat.getItems().add(nombre + " questions");
	    	}
	    	initialisationFormat = true;
    	}
    }
    
    /**
     * M�thode qui met � jour lors de la s�lection d'un format
     * la variable qui garde le nombre de question � lancer
     */
    @FXML
    public void setFormat() {
    	if (listeFormat.getSelectionModel().getSelectedItem() != null) {
	    	if (listeFormat.getSelectionModel().getSelectedItem().equals("5 questions")) {
	    		setNombreQuestion(5);
	    	} else if (listeFormat.getSelectionModel().getSelectedItem().equals("10 questions")) {
	    		setNombreQuestion(10);
	    	} else if (listeFormat.getSelectionModel().getSelectedItem().equals("20 questions")){
	    		setNombreQuestion(20);
	    	} else {
	    		setNombreQuestion(30);
	    	}
    	}
    }
    
    /**
     * M�thode qui permet d'initialiser la ComboBox des sous-cat�gories
     * en fonction de la cat�gorie choisie avant
     */
    @FXML
    public void initialize() {
    	if (!initialisation) {
    		ArrayList<SousCategorie> listeSousCategorie = 
    				ChoixCategorieController.getSurCategorie()==null?
    						DAOSousCategorie.getSousCategories()
    						:DAOSousCategorie.getSousCategories(ChoixCategorieController.getSurCategorie());
    		for (SousCategorie sousCat: listeSousCategorie) {
    			listeSousCat.getItems().add(sousCat.getNom());
    		}
    		initialisation = true;
    	}
    }
    
    /**
     * M�thode qui permet de lancer le jeu (non disponible)
     * pour l'instant elle affiche ce qui a �t� choisi par l'utilisateur
     * en param�tres
     */
    @FXML
    public void valider() {
    	//STUB Avant le lancement du jeu
    	if (listeSousCat.getSelectionModel().getSelectedItem() == null && listeFormat.getSelectionModel().getSelectedItem() != null) {
    		listeVide = true;
    		System.out.println("Lancement du jeu avec" + getNombreQuestion() + " questions. Dans la cat�gorie " + ChoixCategorieController.getSurCategorie());
    	} else if (listeFormat.getSelectionModel().getSelectedItem() != null) {
    		listeVide = false;
    		System.out.println("Lancement du jeu avec " + getNombreQuestion()  + " questions. Dans la cat�gorie " 
    					+ ChoixCategorieController.getSurCategorie() + " et la sous-cat�gorie " 
    				    + listeSousCat.getSelectionModel().getSelectedItem() );
    		// R�cup�ration de la sous-cat�gorie choisis afin de r�cup�rer les questions
    		chargementQuestions(listeSousCat.getSelectionModel().getSelectedItem());
    	} else {
    		System.out.println("Vous n'avez pas saisi de format !");
    	}
		try {
			initialisation = false;
			initialisationFormat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreLancementJeu.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /** 
     * M�thode chargant les questions dans une ArrayList pour le jeu classique
     * @param sousCatChoisis par l'utilisateur
     */
    public void chargementQuestions(String sousCatChoisis) {
    	idSousCat = DAOSousCategorie.getId(sousCatChoisis);
        listeQuestions = DaoQuestions.getQuestionsSousCat(idSousCat);
    }
    
    /**
     * M�thode qui renvoie l'id de la sous cat�gorie actuelle
     * @return idSousCat contient l'id de la sous-cat�gorie
     *         Renvoie -1 si aucune sous-cat�gorie n'a �t� selectionn�
     */
    public static int getIdSousCatActuelle() {
		if (listeVide) {
			return -1;
		} else {
			return idSousCat;
		}	
	}
}
