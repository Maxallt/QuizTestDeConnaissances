package interfacesQuizTestConnaissances;

import gestionCategories.DAOSousCategorie;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpSupprimerSousCategorieController {
	
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
	private Button buttonSupprimer;
	
    /**
	 * Méthode : annule et supprime la fenêtre PopUp
	 * concernant la validation d'une future suppression
	 */
	@FXML
	public void annuler() {
		stage = (Stage) buttonAnnuler.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Méthode : valide le choix de suppression d'une sous-catégorie par 
	 * l'utilisateur
	 */
	@FXML
	public void confirmationSupprimerSousCategorie() {
		String sousCategorieASupprimer = ModificationCategorieController.sousCategorieSelectionnee;
		DAOSousCategorie.supprSousCategorie(sousCategorieASupprimer);
		
		// Fermeture de la fenêtre
		stage = (Stage) buttonSupprimer.getScene().getWindow();
		stage.close();
	}
}
