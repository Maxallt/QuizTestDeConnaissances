package interfacesQuizTestConnaissances;

import java.net.URL;
import java.util.ResourceBundle;

import gestionCategories.Categorie;
import gestionCategories.DAOCategorie;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpCategorieInexistanteController implements Initializable {
	
	/** Conteneur : FenetrePopUpSupprimerSousCategorie.fxml */
    @FXML
    private AnchorPane dynamicPane;
	
    /** Création fenêtre */
    private Stage stage;
    
    /** Bouton qui annule le choix d'une suppression */
	@FXML
	private Button buttonAnnuler;
	
	/** Bouton qui valide le choix d'une suppression */
	@FXML
	private Button buttonCreer;
	
	@FXML
	private Label nomCat;
	
    /**
	 * Méthode : annule et supprime la fenêtre PopUp
	 * concernant la validation d'une future suppression
	 */
	@FXML
	public void annuler() {
		stage = (Stage) buttonAnnuler.getScene().getWindow();
		CreationQuestionController.suivant = true;
		stage.close();
	}
	
	/**
	 * Méthode : valide le choix de suppression d'une sous-catégorie par 
	 * l'utilisateur
	 */
	@FXML
	public void confirmationCreationCategorie() {
		DAOCategorie.create(new Categorie(CreationQuestionController.getCatACreer()));
		
		CreationQuestionController.suivant = true;
		CreationQuestionController.creationOK = true;
		// Fermeture de la fenêtre
		stage = (Stage) buttonCreer.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nomCat.setText(CreationQuestionController.getCatACreer());
	}
}
