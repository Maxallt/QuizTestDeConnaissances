/*
 * GestionController.java			10/12/2020
 * Controller du menu pour gérer les catégories et sous-catégories
 */
package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller du menu pour gérer les catégories et sous-catégories
 * @author Maxime Alliot
 *
 */
public class GestionController implements Initializable {

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
     * Button qui permettra de revenir sur la page précédente
     * Ici l'accueil
     */
    @FXML
    private Button buttonRetour;
    
    /**
     * Button qui permettra de lancer la page de création de catégorie
     */
    @FXML
    private Button createCategorie;
    
    /**
     * Button qui permettra de lancer la page de création de sous-catégorie
     */
    @FXML
    private Button createSousCat;
    
    /**
     * Button qui permettra de lancer la page de modification de catégorie
     */
    @FXML
    private Button modifCategorie;
    
    /**
     * Button qui permettra de lancer la page de modification de sous-catégorie
     */
    @FXML
    private Button modifSousCat;
    
    /**
     * Button qui permettra de lancer la page de création de question
     */
    @FXML
    private Button createQuestion;
	
    /**
     * Méthode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface à afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
    /**
     * Méthode qui affiche la fenêtre précédente
     * Ici l'accueil
     * Liée à buttonRetour
     */
    @FXML
	public void retour() {
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * Méthode qui affiche la fenêtre de modification de 
     * sous-catégorie
     * Liée à modifCategorie
     */
    @FXML
	public void modifSousCat() {
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationSousCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * Méthode qui affiche la fenêtre de création de 
     * sous-catégorie
     * Liée à createSousCat
     */
    @FXML
    public void creationSousCat() {
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreCreationSousCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Méthode qui affiche la fenêtre de modification de 
     * catégorie
     * Liée à modifCategorie
     */
    @FXML
    public void modificationCategorie() {
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Méthode qui affiche la fenêtre de création de 
     * catégorie
     * Liée à createCategorie
     */
    @FXML
    public void creationCategorie() {
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreCreationCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Méthode qui affiche la fenêtre de création de 
     * catégorie
     * Liée à createCategorie
     */
    @FXML
    public void creationQuestion() {
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreCreationQuestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
