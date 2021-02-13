package interfacesQuizTestConnaissances;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpGeneralChoisisController {

	/** Cr�ation fen�tre */
	private Stage stage;
	
	/** Conteneur : FenetrePopUpModificationCatValide.fxml */
    @FXML
    AnchorPane dynamicPane;
    
    /** Bouton qui ram�ne � la page pr�c�dente  */
    @FXML
	private Button buttonRetour;
    
    /**
     * M�thode par d�faut : qui permet d'initialiser des variables
     * @param url nom utilis�
     * @param rb  non utilis�
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
