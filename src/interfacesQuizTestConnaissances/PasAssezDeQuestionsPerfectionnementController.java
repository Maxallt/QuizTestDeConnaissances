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

public class PasAssezDeQuestionsPerfectionnementController implements Initializable {
	
	/** Demande à l'utilisateur de continuer 
	 * malgré le fait qu'il n'y ait pas beaucoup de questions 
	 */
	@FXML
	private Button oui;
	@FXML
	private Button non;
	
	/** Création fenêtre */
	private Stage stage;
	
	/** Conteneur : FenetrePopUpAvertissement.fxml */
    @FXML
    AnchorPane dynamicPane;

    /**
     * Méthode par défaut
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	
	/**
     * Méthode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface à afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
    @FXML
	private void accepter() {
		/* On affiche la fenetre de demande */
		try {
			stage = (Stage)oui.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreLancementPerfectionnementAvances.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    @FXML
	private void annuler() {
		/* On affiche la fenetre de demande */
		try {
			stage = (Stage)non.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreChoixFormatQuestionnaire.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
