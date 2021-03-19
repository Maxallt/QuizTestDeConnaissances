package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gestionCategories.Categorie;
import gestionCategories.DAOCategorie;
import gestionCategories.DAOSousCategorie;
import gestionCategories.SousCategorie;
import gestionQuestion.DaoQuestions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModificationQuestion1Controller implements Initializable{
	
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
    public void initialize(URL url, ResourceBundle rb) {}
    
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
    
    /** Liste déroulante : avec toutes les question créées dans la 
	 * categorie ou sous-catégorie selectionné
	 */
    @FXML
	private ComboBox<String> listeQuestion;
    
    /**
     * Button qui permettra de revenir sur la page précédente
     * Ici le menu de gestion
     */
	@FXML
	private Button buttonFlecheBack;
	
	private boolean initCat = false;
    private boolean initSousCat = false;
    private boolean initQuestions = false;
	
	private static String CategorieChoisie;
	private static String SousCategorieChoisie;
	private static String QuestionChoisie;
	
	public static String getCategorieChoisie() {
		return CategorieChoisie;
	}
	
	public static String getSousCategorieChoisie() {
		return SousCategorieChoisie;
	}

	public static String getQuestionChoisie() {
		return QuestionChoisie;
	}
	
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
    
    /**
     * Initialise la combobox questions avec les intitulés des questions
     * présente en base
     */
    @FXML
    public void setQuesion() {
    	
    	ArrayList<String> tabQuestion = new ArrayList<String>();
    	
    	boolean remplir = true; 
    	
    	if(!initQuestions) {
    		
    		// Les combobox Catégorie et sous catégorie sont vide => aucune question n'est affiché
    		if (listeCategorie.getSelectionModel().getSelectedItem()==null
    			&& listeSousCategorie.getSelectionModel().getSelectedItem()==null){
    			
    			remplir = false;
    		}
    		
    		/* Il n'y a que la combobox des categorie qui est rempli 
    		 *	=> affiche toutes les question de la catégorie selectionné 
    		 */
    		else if (listeCategorie.getSelectionModel().getSelectedItem()!=null
    				 && listeSousCategorie.getSelectionModel().getSelectedItem()==null) {
    			
    			tabQuestion = DaoQuestions.getQuestions
    					(DAOCategorie.getId(listeCategorie.getSelectionModel().getSelectedItem())+"");
    		
    		}
    	
    		/* Il n'y a que la combobox des sous categorie qui est rempli 
    		 *  => affiche toutes les question de  sous categorie selectionné
    		 */
    		else if (listeCategorie.getSelectionModel().getSelectedItem()==null
   				 && listeSousCategorie.getSelectionModel().getSelectedItem()!=null) {
    			
    			tabQuestion = DaoQuestions.getQuestions
    					(DAOSousCategorie.getId(listeSousCategorie.getSelectionModel().getSelectedItem())+"");
    		}
    		
    		/* Les deux combobox ( catégorie et sous categorie) sont rempli
    		 *  => affiches toutes les question de la sous catégorie selectionné
    		 */
    		else {
    			tabQuestion = DaoQuestions.getQuestions
    					(DAOSousCategorie.getId(listeSousCategorie.getSelectionModel().getSelectedItem())+"");
    		}
    		
    		if (remplir) {

    	    	for (String element2 : tabQuestion) {
    				listeQuestion.getItems().add(element2);
    			}
    			initQuestions = true;
    			
    		}
    	}
    }
    
    /**
     * Remet à zéro la ComboBox des sous-catégories et des questions lors
     *  de la sélection d'une nouvelle catégorie
     */
    @FXML
    public void resetListe() {
   
    	listeSousCategorie.getItems().clear();
    	initSousCat = false;
    	
    	listeQuestion.getItems().clear();
        initQuestions = false;
    }
    
    /**
     * Remet à zéro la combobox de des question lors de la sélectioon 
     * d'une nouvelle sous catégorie 
     */
    @FXML
    public void resetListeQuestion() {
    	
    	listeQuestion.getItems().clear();
        initQuestions = false;
    	
    }
    
    /**
     * Méthode qui affiche la fenêtre précédente
     * Ici le menu de gestion
     * Liée à buttonFlecheBack
     */
	@FXML
	private void actionButtonFlecheBack() {
		try {
			stage = (Stage)buttonFlecheBack.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreGestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML	
	private void ActionButtonGo() {
	
		if (listeCategorie.getSelectionModel().getSelectedItem() != null 
				&& listeSousCategorie.getSelectionModel().getSelectedItem()!=null
				&& listeQuestion.getSelectionModel().getSelectedItem()!=null) {
			
			CategorieChoisie = listeCategorie.getSelectionModel().getSelectedItem();
			SousCategorieChoisie = listeCategorie.getSelectionModel().getSelectedItem();
			QuestionChoisie = listeQuestion.getSelectionModel().getSelectedItem();
			
			
			try {
				initCat = false;
				initSousCat = false;
				stage = (Stage)buttonFlecheBack.getScene().getWindow();
				setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationQuestion2.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			// TODO afficher popup d'erreur
		}
	}

}
