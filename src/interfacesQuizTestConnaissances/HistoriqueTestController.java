/*
 * AccueilController.java			10/12/2020
 * Controller de la fenetre accueil
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
 * Cette classe est le centre de l'application, elle permet
 * de lancer le premier menu qui pourra nous rediriger
 * o� on veut.
 * Elle contient donc 4 boutons
 * 		Un pour lancer le jeu
 * 		Un pour lancer le perfectionnement avanc� (non disponible)
 * 		Un pour aller voir les anciens r�sultats (non disponible)
 * 		Un pour g�rer les cat�gories et les sous-cat�gories de la base de donn�e
 * @author Maxime Alliot
 *
 */
public class HistoriqueTestController implements Initializable {

	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
	@FXML
	private Button buttonFlecheBack;
	
	/**
	 * M�thode obligatoire pour tous les controller
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;

	
    /**
     * M�thode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface � afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
    @FXML
	private void actionButtonFlecheBack() {
		try {
			stage = (Stage)buttonFlecheBack.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    

}
