/*
 * AccueilController.java			10/12/2020
 * Controller de la fenetre accueil
 */
package interfacesQuizTestConnaissances;

import java.awt.Desktop;
import java.io.File;
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
public class AccueilController implements Initializable {

	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
	/**
	 * M�thode obligatoire pour tous les controller
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /** 
     * C'est le bouton qui redirigera vers le choix de cat�gorie afin de jouer 
     */
    @FXML
    private Button buttonJeuClassique;
    
    /** 
     * C'est le bouton qui redirigera vers la page pour g�n�rer des fiches 
     */
    @FXML
    private Button buttonFichesRevision;
    
    /**
     * Bouton qui va permettre d'envoyer vers la gestion des cat�gories
     * et des sous-cat�gories
     */
    @FXML
    private Button buttonModif;
    
    @FXML
    private Button btnAide;
    
    /**
     * Bouton (inactif) qui enverra vers le perfectionnement avanc�
     */
    @FXML
    private Button buttonPerfectionnementAvance;
    
    /**
     * Bouton (inactif) qui enverra vers les r�sultats des pr�c�dents test
     */
    @FXML
    private Button reviewTests;
	
    /**
     * M�thode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface � afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
    
    /**
     * M�thode qui envoie vers le jeux principal,
     * ici d'abord vers le choix de la cat�gorie
     * Cette m�thode est reli�e � buttonJeuClassique
     */
    @FXML
	public void jeuPrincipal() {
		try {
			stage = (Stage)buttonJeuClassique.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreChoixCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * M�thode qui envoie vers le perfectionnement avances,
     * ici d'abord vers le format de questions et eventuellement la difficulte
     * Cette m�thode est reli�e � buttonPerfectionnementAvance
     */
    @FXML
	public void perfectionnementAvancees() {
		try {
			stage = (Stage)buttonPerfectionnementAvance.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreChoixFormatQuestionnaire.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * M�thode qui renvoie vers l'historique des tests effectu�s,
     * Cette m�thode est reli�e � reviewTests
     */
    @FXML
	public void reviewTestAction() {
		try {
			stage = (Stage)buttonPerfectionnementAvance.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreHistoriqueTest.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * M�thode qui envoie vers la gestion des cat�gories et sous-cat�gories,
     * ici d'abord vers une interface � 4 choix
     * Cette m�thode est reli�e � buttonModif
     */
    @FXML
	public void gestion() {
		try {
			stage = (Stage)buttonJeuClassique.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreGestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    @FXML
    public void aide() {
    	Desktop d = Desktop.getDesktop();
    	try {
			d.open(new File(".\\Manuel Utilisateur.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * M�thode qui envoie vers la page des fiches,
     * Cette m�thode est reli�e � buttonFichesRevision
     */
    @FXML
	public void fichesRevision() {
		try {
			stage = (Stage)buttonFichesRevision.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreFichesRevision.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
