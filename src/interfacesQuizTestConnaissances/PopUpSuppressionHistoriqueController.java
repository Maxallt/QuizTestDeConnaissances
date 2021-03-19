package interfacesQuizTestConnaissances;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import gestionCategories.DAOSousCategorie;
import gestionEnregistrementPartie.DAOHistoriquePartie;

public class PopUpSuppressionHistoriqueController implements Initializable {
	
	/** 
	 * Est utile pour le changement de fenetre, il récupère la fenetre
	 * courante pour la changer
	 */
    private Stage stage;
	
    /** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
	/**
	 * Méthode obligatoire pour tous les controller
	 */
	@Override
    public void initialize(URL url, ResourceBundle rb) {}
	
	/**
	 * Button qui permet de valider la suppression
	 */
	@FXML
	private Button buttonValider;
	
	/** 
	 * Button qui permet d'annuler la suppression en fermant la pop-up
	 */
	@FXML
	private Button buttonAnnuler;
	
	/**
	 * Méthode qui permet de supprimer en base la sous catégorie
	 * sélectionnée précedemment
	 * Puis qui ferme la pop-up
	 */
	@FXML
	public void valider() {
		DAOHistoriquePartie.deleteHistorique(FenetreHistoriqueTestController.getValeurASupprimer());
		
		// Fermeture de la fenêtre
		stage = (Stage) buttonValider.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Méthode qui permet d'annuler la suppression en fermant la pop-up
	 */
	@FXML
	public void annuler() {
		FenetreHistoriqueTestController.continuer = true;
		stage = (Stage) buttonValider.getScene().getWindow();
		stage.close();
	}
	
}