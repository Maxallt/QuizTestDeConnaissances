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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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

    	
    	txtNouvelleQuestion.setText(QuestionChoisie);
    	txtNouvelleRepVrai.setText(intitulesRep.get(0));
    	txtNouvelleRepFausse1.setText(intitulesRep.get(1));
    	txtNouvelleRepFausse2.setText(intitulesRep.get(2));
    	txtNouvelleRepFausse3.setText(intitulesRep.get(3));
    	txtNouvelleRepFausse4.setText(intitulesRep.get(4));
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
	private static String[] tabDifficulte = {"Facile","Moyen","Difficile","Indifférent"};
	
	private boolean initCat = false;
    private boolean initSousCat = false;
    private boolean initDifficulte = false;
    
    private String CategorieChoisie = ModificationQuestion1Controller.getCategorieChoisie();
	private String SousCategorieChoisie = ModificationQuestion1Controller.getSousCategorieChoisie();
	private String QuestionChoisie = ModificationQuestion1Controller.getQuestionChoisie();
	
	
	
    
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
					listeCategorie.getSelectionModel().getSelectedItem()==null?
					DAOSousCategorie.getSousCategories():
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
    
    @FXML
    public void supprimerQuestion() {
    	
    	// Suppression de la question dans la base de données 
    	//DaoQuestions.delete(QuestionChoisie);
    	
    	// TODO popUp de confirmation
    }
    
    @FXML 
    public void modifierQuestion() {
    	
    	System.out.println(QuestionChoisie);
    	System.out.println(txtNouvelleQuestion.getText());
    	
    	DaoQuestions.updateQuestions(QuestionChoisie, txtNouvelleQuestion.getText());
    	
    	/*DaoQuestions.updateQuestions(QuestionChoisie, txtNouvelleQuestion.getText(), 
    			txtNouvelleRepVrai.getText(), txtNouvelleRepFausse1.getText(),
    			txtNouvelleRepFausse2.getText(), txtNouvelleRepFausse3.getText(),
    			txtNouvelleRepFausse4.getText(), listeDifficulte.getSelectionModel().getSelectedItem());*/
    	
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
