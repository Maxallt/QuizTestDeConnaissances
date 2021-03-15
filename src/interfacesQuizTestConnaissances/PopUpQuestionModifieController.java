package interfacesQuizTestConnaissances;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PopUpQuestionModifieController implements Initializable {
	
	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
    private Stage stage;
	
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
	/**
	 * M�thode obligatoire pour tous les controller
	 */
	@Override
    public void initialize(URL url, ResourceBundle rb) {}
	
	/**
	 * Button qui va renvoyer vers la fenetre pr�c�dente 
	 * apr�s avoir afficher le message
	 */
	@FXML
	private Button buttonAnnuler;
	
	/**
	 * M�thode qui permet de fermer la fenetre pop-up
	 */
	@FXML
	public void annuler() {
		stage = (Stage) buttonAnnuler.getScene().getWindow();
		stage.close();
	}

}
