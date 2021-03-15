	package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gestionCategories.Categorie;
import gestionCategories.DAOCategorie;
import gestionCategories.DAOSousCategorie;
import gestionCategories.SousCategorie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import gestionQuestion.DaoQuestions;

public class ModificationQuestion2Controller implements Initializable {
	
	/** 
	 * Est utile pour le changement de fenetre, il récupère la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
	/**
	 * Méthode obligatoire pour tous les controller
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	// ArrayList avec la réponse vrai et la/les reponse(s) fausse
    	ArrayList<String> intitulesRep = DaoQuestions.getReponses(QuestionChoisie);

    	// Initialisation des TextField avec les infos de la question choisie
    	txtNouvelleQuestion.setText(QuestionChoisie);
    	txtNouvelleRepVrai.setText(intitulesRep.get(0));
    	txtNouvelleRepFausse1.setText(intitulesRep.get(1));
    	txtNouvelleRepFausse2.setText(intitulesRep.get(2));
    	txtNouvelleRepFausse3.setText(intitulesRep.get(3));
    	txtNouvelleRepFausse4.setText(intitulesRep.get(4));

    	// Initialisation des comboBox avec les info correspondante
    	listeCategorie.getSelectionModel().select(CategorieChoisie);
    	
    	//setCategorie();
    	//setSousCategorie();
    	
    	listeSousCategorie.getSelectionModel().select(SousCategorieChoisie);
    	listeDifficulte.getSelectionModel().select(DifficulteChoisie);
    }
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /**
     * Méthode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface à afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
    /** Liste déroulante : avec toutes les catégories créées */
	@FXML
	private ComboBox<String> listeCategorie;
	
	/** Liste déroulante : avec toutes les sous catégories créées dans la 
	 * categorie selectionné
	 */
	@FXML
	private ComboBox<String> listeSousCategorie; 
	
	/** Liste déroulante : avec les difficultés */
	@FXML
	private ComboBox<String> listeDifficulte;
	
	/** Bouton qui permet de supprimer la question */
	@FXML
    private Button btnSuprQuestion;
	
	/** Bouton qui permet de modifier la question */
	@FXML 
	private Button btnModifQuestion;
	
	/**
     * Boutton qui permetde revenir sur la page précédente
     * Ici l'interface de choix de question 
     */
	@FXML
	private Button buttonFlecheBack;
	
	@FXML
	private TextField txtNouvelleQuestion;
	
	@FXML
	private TextField txtNouvelleRepVrai;
	
	@FXML
	private TextField txtNouvelleRepFausse1;
	
	@FXML
	private TextField txtNouvelleRepFausse2;
	
	@FXML
	private TextField txtNouvelleRepFausse3;
	
	@FXML
	private TextField txtNouvelleRepFausse4;
	
	/** Tableau des diffcultés */
	private static String[] tabDifficulte = {"facile","moyen","difficile","indifférent"};
	
	private boolean initCat = false;
    private boolean initSousCat = false;
    private boolean initDifficulte = false;
    private boolean valid = true;
    
    private String CategorieChoisie = ModificationQuestion1Controller.getCategorieChoisie();
	private String SousCategorieChoisie = ModificationQuestion1Controller.getSousCategorieChoisie();
	public String QuestionChoisie = ModificationQuestion1Controller.getQuestionChoisie();
	private String DifficulteChoisie = DaoQuestions.getDifficulte(QuestionChoisie);
	
	
	
    
    @FXML
    public void setCategorie() {
		if (!initCat) {
			ArrayList<Categorie> categories = DAOCategorie.findCategorie();
			for (Categorie element: categories) {
				listeCategorie.getItems().add(element.getNom());
			}
			initCat = true;
		}
	}
    
    @FXML
    public void setSousCategorie() {
		if (!initSousCat) {
			ArrayList<SousCategorie> sousCategories = 
				DAOSousCategorie.getSousCategories(listeCategorie.getSelectionModel().getSelectedItem());
			
			for (SousCategorie element: sousCategories) {
				listeSousCategorie.getItems().add(element.getNom());
			}
			initSousCat = true;
		}
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
     * Remet à zéro la ComboBox des sous-catégories lors
     *  de la sélection d'une nouvelle catégorie
     */
    @FXML
    public void resetListeSousCat() {
    
    	listeSousCategorie.getItems().clear();
    	initSousCat = false;
   
    }
    
    /**
	 * Si la question est valide 
	 * alors cette fenêtre avertie l'utilisateur de la réussite
	 */
	public void creationPopUpModifQuestionValide() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUp"
            		                              + "QuestionModifie.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Question modifiée");
        dialog.show();
	}
	
    /**
	 * Si la question est invalide 
	 * alors cette fenêtre avertie l'utilisateur l'echec
	 */
	public void creationPopUpModifQuestionInvalide() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUp"
            		                              + "QuestionNonModifie.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Modificatoion impossible");
        dialog.show();
	}
	
	/**
	 * Si la suppression est possible, alors cette fenetre avertie l'utilisateur 
	 * de la suppresion lorsqu'il clique sur le bouton  supprimer
	 */
	public void creationPopUpSuprQuestionValide() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUp"
            		                              + "QuestionSupprime.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Modificatoion impossible");
        dialog.show();
	}
	
	/**
	 * Si la suppression est impossible, alors cette fenetre avertie l'utilisateur 
	 * de la l'echec lorsqu'il clique sur le bouton  supprimer
	 */
	public void creationPopUpSuprQuestionInvalide() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUp"
            		                              + "QuestionNonSupprime.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Modificatoion impossible");
        dialog.show();
	}
    
    @FXML
    public void supprimerQuestion() {
    	
    	/* Si la question est dans la catégorie Général et dans la sous catégorie général
    	 * => suppression impossible
       	 */
    	if (CategorieChoisie == "Général" && SousCategorieChoisie == "Général") {
    		
    		// PopUp d'echec de la suppresion
    		creationPopUpSuprQuestionInvalide();
    		
    	}else {
    		
    		// Suppression de la question dans la base de données 
    		creationPopUpSuprQuestionValide(); 	
    		
    		// Renvoie l'utilisateur vers l'acceuil
	    	try {
				stage = (Stage)btnSuprQuestion.getScene().getWindow();
				setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    @FXML 
    public void modifierQuestion() {
    	
    	if (txtNouvelleQuestion.getText().equals("") 
    		|| txtNouvelleRepVrai.getText().equals("")  
    		|| txtNouvelleRepFausse1.getText().equals("") ) {
    		
    		valid = false;
    		
    		//popup d'erreur
    		creationPopUpModifQuestionInvalide();
    	}
    	
    	if (valid) {
    		
	    	DaoQuestions.updateQuestions(QuestionChoisie, txtNouvelleQuestion.getText(), 
	    			txtNouvelleRepVrai.getText(), txtNouvelleRepFausse1.getText(),
	    			txtNouvelleRepFausse2.getText(), txtNouvelleRepFausse3.getText(),
	    			txtNouvelleRepFausse4.getText(),
	    			DAOCategorie.getId(listeCategorie.getSelectionModel().getSelectedItem())+"",
	    			DAOSousCategorie.getId(listeSousCategorie.getSelectionModel().getSelectedItem())+"",
	    			listeDifficulte.getSelectionModel().getSelectedItem());
	    	
	    	creationPopUpModifQuestionValide();
	    	
	    	// Renvoie l'utilisateur vers l'acceuil
	    	try {
				stage = (Stage)btnModifQuestion.getScene().getWindow();
				setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
    	
    	}
    }
    
    /**
     * Méthode qui affiche la fenêtre précédente
     * Ici l'interface de choix de question 
     * Liée à buttonFlecheBack
     */
	@FXML
	private void actionButtonFlecheBack() {
		try {
			stage = (Stage)buttonFlecheBack.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationQuestion1.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
