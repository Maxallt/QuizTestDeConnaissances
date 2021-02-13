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
	
    /** Cr�ation fen�tre */
    private Stage stage;
    
    /** Bouton qui annule le choix d'une suppression */
	@FXML
	private Button buttonAnnuler;
	
	/** Bouton qui valide le choix d'une suppression */
	@FXML
	private Button buttonSupprimer;
	
    /**
	 * M�thode : annule et supprime la fen�tre PopUp
	 * concernant la validation d'une future suppression
	 */
	@FXML
	public void annuler() {
		stage = (Stage) buttonAnnuler.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * M�thode : valide le choix de suppression d'une sous-cat�gorie par 
	 * l'utilisateur
	 */
	@FXML
	public void confirmationSupprimerSousCategorie() {
		String sousCategorieASupprimer = ModificationCategorieController.sousCategorieSelectionnee;
		DAOSousCategorie.supprSousCategorie(sousCategorieASupprimer);
		
		// Fermeture de la fen�tre
		stage = (Stage) buttonSupprimer.getScene().getWindow();
		stage.close();
	}
}
