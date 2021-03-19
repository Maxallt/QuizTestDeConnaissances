package interfacesQuizTestConnaissances;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpFichierVideOuEnBase implements Initializable {
	
	/** Conteneur : FenetrePopUpSupprimerSousCategorie.fxml */
    @FXML
    private AnchorPane dynamicPane;
	
    /** Création fenêtre */
    private Stage stage;
    
    /** Bouton qui annule le choix d'une suppression */
	@FXML
	private Button buttonAnnuler;

	
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	}
}
