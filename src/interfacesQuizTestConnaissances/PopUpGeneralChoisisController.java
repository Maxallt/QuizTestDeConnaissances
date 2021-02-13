package interfacesQuizTestConnaissances;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpGeneralChoisisController {

	/** Création fenêtre */
	private Stage stage;
	
	/** Conteneur : FenetrePopUpModificationCatValide.fxml */
    @FXML
    AnchorPane dynamicPane;
    
    /** Bouton qui ramène à la page précédente  */
    @FXML
	private Button buttonRetour;
    
    /**
     * Méthode par défaut : qui permet d'initialiser des variables
     * @param url nom utilisé
     * @param rb  non utilisé
     */
	public void initialize(URL url, ResourceBundle rb) {}
	
	/**
	 * Action qui permet le retour sur la page FenetreModificationCategorie
	 */
	@FXML
	public void retour() {
		stage = (Stage) buttonRetour.getScene().getWindow();
		stage.close();
	}
}
