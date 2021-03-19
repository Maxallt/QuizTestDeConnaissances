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
 * où on veut.
 * Elle contient donc 4 boutons
 * 		Un pour lancer le jeu
 * 		Un pour lancer le perfectionnement avancé (non disponible)
 * 		Un pour aller voir les anciens résultats (non disponible)
 * 		Un pour gérer les catégories et les sous-catégories de la base de donnée
 * @author Maxime Alliot
 *
 */
public class AccueilController implements Initializable {

	/** 
	 * Est utile pour le changement de fenetre, il récupère la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
	/**
	 * Méthode obligatoire pour tous les controller
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /** 
     * C'est le bouton qui redirigera vers la page pour générer des fiches 
     */
    @FXML
    private Button buttonFichesRevision;
    
    /** 
     * C'est le bouton qui redirigera vers le choix de catégorie afin de jouer 
     */
    @FXML
    private Button buttonJeuClassique;
    
    /**
     * Bouton qui va permettre d'envoyer vers la gestion des catégories
     * et des sous-catégories
     */
    @FXML
    private Button buttonModif;
    
    /**
     * Bouton (inactif) qui enverra vers le perfectionnement avancé
     */
    @FXML
    private Button buttonPerfectionnementAvance;
    
    /**
     * Bouton (inactif) qui enverra vers les résultats des précédents test
     */
    @FXML
    private Button reviewTests;
	
    /**
     * Méthode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface à afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
    
    /**
     * Méthode qui envoie vers la page des fiches,
     * Cette méthode est reliée à buttonFichesRevision
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
    
    
    /**
     * Méthode qui envoie vers le jeux principal,
     * ici d'abord vers le choix de la catégorie
     * Cette méthode est reliée à buttonJeuClassique
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
     * Méthode qui envoie vers la gestion des catégories et sous-catégories,
     * ici d'abord vers une interface à 4 choix
     * Cette méthode est reliée à buttonModif
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
}
