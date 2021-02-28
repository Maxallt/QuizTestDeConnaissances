/*
 * ChoixSousCategorieController.java		10/12/2020
 * Controller de l'affichage du choix de la sous-cat�gorie
 */
package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Classe qui va permettre de choisir la sous-cat�gorie
 * pour le jeu en fonction de la cat�gorie choisie pr�c�demment
 * @author Maxime Alliot
 *
 */
public class ChoixFormatQuestionnaireController implements Initializable {

	private static boolean initDifficulte = false;
	private static String difficulteChoisie;
	
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
		ChoixFormatQuestionnaireController.nombreQuestion = nombreQuestion;
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
    
    @FXML
	private ComboBox<String> listeDifficulte = new ComboBox<String>();
	
	private static String[] tabDifficulte = {"Facile","Moyen","Difficile","Indiff�rent"};
    
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
	
    @FXML
    public void setDifficulte() {
		if(!initDifficulte) {
			for (String element: tabDifficulte) {
				listeDifficulte.getItems().add(element);
			}
			initDifficulte = true;
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
			initialisation = false;
			initialisationFormat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
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
     * M�thode qui permet de lancer le jeu (non disponible)
     * pour l'instant elle affiche ce qui a �t� choisi par l'utilisateur
     * en param�tres
     */
    @FXML
    public void valider() {
    	//TODO 
		try {
			initialisation = false;
			initialisationFormat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreLancementPerfectionnementAvances.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /** 
     * M�thode chargant les questions dans une ArrayList pour 
     */
    public void chargementQuestions(String sousCatChoisis) {
    	//TODO
    }
}
