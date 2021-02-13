/*
 * GestionController.java			10/12/2020
 * Controller du menu pour g�rer les cat�gories et sous-cat�gories
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
 * Controller du menu pour g�rer les cat�gories et sous-cat�gories
 * @author Maxime Alliot
 *
 */
public class GestionController implements Initializable {

	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
    
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
     * Button qui permettra de lancer la page de cr�ation de cat�gorie
     */
    @FXML
    private Button createCategorie;
    
    /**
     * Button qui permettra de lancer la page de cr�ation de sous-cat�gorie
     */
    @FXML
    private Button createSousCat;
    
    /**
     * Button qui permettra de lancer la page de modification de cat�gorie
     */
    @FXML
    private Button modifCategorie;
    
    /**
     * Button qui permettra de lancer la page de modification de sous-cat�gorie
     */
    @FXML
    private Button modifSousCat;
    
    /**
     * Button qui permettra de lancer la page de cr�ation de question
     */
    @FXML
    private Button createQuestion;
	
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
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * M�thode qui affiche la fen�tre de modification de 
     * sous-cat�gorie
     * Li�e � modifCategorie
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
     * M�thode qui affiche la fen�tre de cr�ation de 
     * sous-cat�gorie
     * Li�e � createSousCat
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
     * M�thode qui affiche la fen�tre de modification de 
     * cat�gorie
     * Li�e � modifCategorie
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
     * M�thode qui affiche la fen�tre de cr�ation de 
     * cat�gorie
     * Li�e � createCategorie
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
     * M�thode qui affiche la fen�tre de cr�ation de 
     * cat�gorie
     * Li�e � createCategorie
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
