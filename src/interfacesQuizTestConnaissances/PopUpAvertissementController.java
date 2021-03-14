package interfacesQuizTestConnaissances;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpAvertissementController implements Initializable {

	/** Cr�ation fen�tre */
	private Stage stage;
	
	/** Conteneur : FenetrePopUpAvertissement.fxml */
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
	 * M�thode : quand l'utilisateur clique sur le bouton -> buttonretour
	 * Ram�ne l'utilisateur � la page FenetreGestion.fxml
	 */
    @FXML
	public void retour() {
    	stage = (Stage) buttonRetour.getScene().getWindow();
		stage.close();
	}
}
