package interfacesQuizTestConnaissances;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpAvertissementController implements Initializable {

	/** Création fenêtre */
	private Stage stage;
	
	/** Conteneur : FenetrePopUpAvertissement.fxml */
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
	 * Méthode : quand l'utilisateur clique sur le bouton -> buttonretour
	 * Ramène l'utilisateur à la page FenetreGestion.fxml
	 */
    @FXML
	public void retour() {
    	stage = (Stage) buttonRetour.getScene().getWindow();
		stage.close();
	}
}
