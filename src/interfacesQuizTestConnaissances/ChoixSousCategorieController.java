/*
 * ChoixSousCategorieController.java		10/12/2020
 * Controller de l'affichage du choix de la sous-catï¿½gorie
 */
package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import gestionCategories.SousCategorie;
import gestionQuestion.DaoQuestions;
import gestionCategories.DAOCategorie;
import gestionCategories.DAOSousCategorie;

/**
 * Classe qui va permettre de choisir la sous-catï¿½gorie
 * pour le jeu en fonction de la catï¿½gorie choisie prï¿½cï¿½demment
 * @author Maxime Alliot
 *
 */
public class ChoixSousCategorieController implements Initializable {

	 /** Identifiant de la sous-catï¿½gorie choisis par l'utilisateur */
    private static int idSousCat;
    
    /** Liste des questions correspondant ï¿½ la sous-catï¿½gorie */
    static ArrayList<String> listeQuestions = new ArrayList<String>();
    
	/** 
	 * Est utile pour le changement de fenetre, il rï¿½cupï¿½re la fenetre
	 * courante pour la changer
	 */
    @SuppressWarnings("unused")
	private Stage stage;
	
    /** 
     * Permet de vï¿½rifier si la ComboBox avec les sous-catï¿½gories a ï¿½tï¿½ 
     * initialisï¿½e, c'est ï¿½ dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
    private static boolean initialisation = false;
    
    /**
     * Permet de vï¿½rifier si la ComboBox avec les formats a ï¿½tï¿½ 
     * initialisï¿½e, c'est ï¿½ dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
    private static boolean initialisationFormat = false;
    
    /**
     * Valeur du nombre de question selectionnï¿½e en format
     */
    private static int nombreQuestion = -1;
    
    /**
     * Boolean d'indication pour la liste de sous categorie
     * True s'il est nulle
     * False sinon
     */
    private static boolean listeVide = true;

	/**
     * Getter pour rï¿½cupï¿½rer le nombre de question selectionnï¿½
     * @return la valeur du format voulu
     */
    public static int getNombreQuestion() {
		return nombreQuestion;
	}

    /**
     * Setter pour mettre ï¿½ jour la valeur du format voulu
     * @param nombreQuestion Nouveau format voulu
     */
	public static void setNombreQuestion(int nombreQuestion) {
		ChoixSousCategorieController.nombreQuestion = nombreQuestion;
	}

	/**
	 * Tableau qui regroupe les diffï¿½rents formats de QCM
	 */
	private static int[] tabFormat = {5,10,20,30};
    
	/**
	 * Contient la valeurde la difficulte choisi sur l'ecran precedent dans le jeu
	 */
	private static String difficulteChoisi = "";
	
	/**
	 * Mï¿½thode obligatoire pour tous les controller
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /**
     * Button qui permettra de revenir sur la page prï¿½cï¿½dente
     * Ici l'accueil
     */
    @FXML
    private Button buttonRetour;
    
    /**
     * Ce bouton set ï¿½ valider le choix de sous-catï¿½gorie (facultatif)
     * et le choix du format
     */
    @FXML
    private Button buttonValider;
    
    /**
     * Liste qui regroupe toutes les sous catï¿½gories de la catï¿½gorie
     * choisie dans l'interface d'avant
     */
    @FXML
    private ComboBox<String> listeSousCat;
    
    /**
     * Liste qui regroupe tous les formats de QCM possibles
     */
    @FXML
    private ComboBox<String> listeFormat = new ComboBox<String>();
    
    /** La catégorie choisis par l'utilisateur */
    String categorieChoisie;
    
    /** La sous-catégorie choisis par l'utilisateur */
	static String sousCategorieChoisie;
	
	public static String getSousCategorie() {
		return sousCategorieChoisie;
	}
	
	/** Le format choisis par l'utilisateur */
	String formatChoisi;
	
    /**
     * Mï¿½thode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface ï¿½ afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
    /**
     * Mï¿½thode qui affiche la fenï¿½tre prï¿½cï¿½dente
     * Ici l'accueil
     * Liï¿½e ï¿½ buttonRetour
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
     * Mï¿½thode qui permet d'initialiser la ComboBox des formats
     * avec le tableau qui regroupe les diffï¿½rentes possibilitï¿½s
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
     * Mï¿½thode qui met ï¿½ jour lors de la sï¿½lection d'un format
     * la variable qui garde le nombre de question ï¿½ lancer
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
     * Mï¿½thode qui permet d'initialiser la ComboBox des sous-catï¿½gories
     * en fonction de la catï¿½gorie choisie avant
     */
    @FXML
    public void initialize() {
    	difficulteChoisi =  ChoixCategorieController.getDifficulteCatActuelle();
    	
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
     * Mï¿½thode qui permet de lancer le jeu (non disponible)
     * pour l'instant elle affiche ce qui a ï¿½tï¿½ choisi par l'utilisateur
     * en paramï¿½tres
     */
    @FXML
    public void valider() {
    	categorieChoisie = ChoixCategorieController.getSurCategorie();
    	sousCategorieChoisie = listeSousCat.getSelectionModel().getSelectedItem();
    	formatChoisi = listeFormat.getSelectionModel().getSelectedItem();
    	
    	int idCat = DAOCategorie.getId(categorieChoisie);
    	int idSousCat ;
    	
    	// Choix d'une categorie uniquement
    	if (sousCategorieChoisie == null && formatChoisi != null) {
    		listeVide = true;
            chargementQuestions(idCat);
    		System.out.println("Lancement du jeu avec" + getNombreQuestion() + " questions. Dans la catï¿½gorie " 
    		                                           + categorieChoisie );
    		
    		// Vï¿½rifie si la taille des questions est correcte
    		if (verificationTailleListe()) {
    			// On se dï¿½place vers le Jeu Classique
            	lancementJeuClassique();
    		}
        // Choix d'une categorie et d'une sous categorie
    	} else if (formatChoisi != null) {
    		idSousCat = DAOSousCategorie.getId(sousCategorieChoisie);
    		listeVide = false;
    		chargementQuestions(idCat, idSousCat);
    		System.out.println("Lancement du jeu avec " + getNombreQuestion()  + " questions. Dans la catï¿½gorie " 
    					+ categorieChoisie + " et la sous-catï¿½gorie " 
    				    + sousCategorieChoisie );
    		System.out.println("Niveau de difficultï¿½: " + difficulteChoisi + "\n");
    		// Rï¿½cupï¿½ration de la sous-catï¿½gorie choisis afin de rï¿½cupï¿½rer les questions
    		
    		
    		// Vï¿½rifie si la taille des questions est correcte
    		if (verificationTailleListe()) {
    			// On se dï¿½place vers le Jeu Classique
            	affichageImage();
    			
    		}
    	} else {
    		System.out.println("Vous n'avez pas saisi de format !");
    	}
	}
    
    /** 
     * Mï¿½thode chargant les questions dans une ArrayList pour le jeu classique
     * @param idCat par l'utilisateur
     */
    public void chargementQuestions(int idCat) {
    	if (difficulteChoisi.equals("IndiffÃ©rent")) {
    		listeQuestions = DaoQuestions.getQuestions(idCat);
    	} else {
    		listeQuestions = DaoQuestions.getQuestions(idCat, difficulteChoisi);
    	}
        
    }
    
    /** 
     * Mï¿½thode chargant les questions dans une ArrayList pour le jeu classique
     * @param idSousCat2 par l'utilisateur
     */
    public void chargementQuestions(int idCat, int idSousCat2) {
    	if (difficulteChoisi.equals("IndiffÃ©rent")) {
    		listeQuestions = DaoQuestions.getQuestions(idCat, idSousCat2);
    	} else {
    		listeQuestions = DaoQuestions.getQuestions(idCat, idSousCat2, difficulteChoisi);
    	}
        
    }
    
    /**
     * Mï¿½thode qui renvoie l'id de la sous catï¿½gorie actuelle
     * @return idSousCat contient l'id de la sous-catï¿½gorie
     *         Renvoie -1 si aucune sous-catï¿½gorie n'a ï¿½tï¿½ selectionnï¿½
     */
    public static int getIdSousCatActuelle() {
		if (listeVide) {
			return -1;
		} else {
			return idSousCat;
		}	
	}
    
    /**
     * Vï¿½rifie la taille de la liste des questions,
     * si cette derniï¿½re est vide -> retour ï¿½ ChoixSousCategorie
     * si cette derniï¿½re est trop courte -> demande ï¿½ l'utilisateur 
     * 										s'il veut continuer
     * sinon, lancement classique du jeu
     */
    public boolean verificationTailleListe() {
    	 int tailleListe = listeQuestions.size();
    	
    	if (tailleListe == 0) {
    		System.out.println("Pas de questions");
    		creationPopUpAvertissement();
    		return false;
    	} else if (tailleListe < getNombreQuestion()) {
    		// le nombre de question correspondra alors ï¿½ la liste
    		nombreQuestion = listeQuestions.size();
    		System.out.println("Pas assez de questions. Voulez-vous continuer ?");
    		affichagePeuDeQuestions();
    		return false;
    	} else {
    		return true;
    	}
    }
    
    /**
     * Lancement vers FenetreLancementJeu
     */
    public void lancementJeuClassique() {
    	try {
			initialisation = false;
			initialisationFormat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreLancementJeu.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void affichagePeuDeQuestions() {
    	/* On affiche la fenetre de demande */
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetrePasAssezDeQuestions.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void affichageImage() {
    	/* On affiche la fenetre de demande */
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreImage.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void creationPopUpAvertissement() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUpAvertissement.fxml");
            // Crï¿½ation du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Pas de questions");
        dialog.show();
	}
}
