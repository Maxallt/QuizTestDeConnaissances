/*
 * ModificationSousCategorieController.java			10/12/2020
 * Controller de la fenêtre qui permet de modifer une sous catégorie
 */
package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gestionCategories.Categorie;
import gestionCategories.DAOCategorie;
import gestionCategories.DAOSousCategorie;
import gestionCategories.SousCategorie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller de la fenêtre qui permet de modifer une sous catégorie
 * @author Maxime Alliot
 *
 */
public class ModificationSousCategorieController implements Initializable {

	/** 
	 * Est utile pour le changement de fenetre, il récupère la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
	private Stage stage;
	
    /** 
     * Permet de vérifier si la ComboBox avec les sous-catégories a été 
     * initialisée, c'est à dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
    private static boolean initialisation = false;
    
    /** 
     * Permet de vérifier si la ComboBox avec les catégories a été 
     * initialisée, c'est à dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
	private static boolean initCat = false;

	/**
	 * Méthode obligatoire pour tous les controller
	 */
	@Override
    public void initialize(URL url, ResourceBundle rb) {}
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /**
     * Button qui permettra de revenir sur la page précédente
     * Ici le menu de gestion des des catégories et sous-catégories
     */
    @FXML
    private Button buttonRetour;
    
    /**
     * Ce bouton set à valider le choix de modification
     */
    @FXML
    private Button buttonValider;
    
    /**
     * Ce bouton set à valider le choix de suppression
     */
    @FXML
    private Button buttonSupprimer;
    
    /**
     * Endroit où le nouveau nom d'une sous-catégorie 
     * sera tapé par l'utilisateur
     */
    @FXML
    private TextField newName;
    
    /**
     * Endroit où le nouveau lien photo d'une sous-catégorie 
     * sera tapé par l'utilisateur
     */
    @FXML
    private TextField newLienPhoto;
    
    /**
     * Liste qui regroupe toutes les sous catégories de la catégorie
     * choisie 
     */
    @FXML
    private ComboBox<String> listeSousCat;
    
    /**
     * Liste qui regroupe toutes les catégories 
     */
    @FXML
    private ComboBox<String> listeCategorie;
    
    /**
     * Nom de la sous-catégorie à supprimer
     * utiliser dans la pop-up de validation de suppression
     */
    private static String valeurASupprimer = null;
	
    /**
     * Getter du nom de la sous-cat à supprimer
     * @return le nom de la sous-catégorie à supprimer
     */
    public static String getValeurASupprimer() {
		return valeurASupprimer;
	}

    /**
     * Méthode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface à afficher
     */
	private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
    /**
     * Méthode qui affiche la fenêtre précédente
     * Ici le menu de gestion
     * Liée à buttonRetour
     */
    @FXML
	public void retour() {
		try {
			initialisation = false;
			initCat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreGestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * Méthode qui vérifie si le nom de la sous-catégorie est déjà existant
     * @return true s'il existe dejà,
     * 		   false sinon
     */
    private boolean testHomonymes() {
		ArrayList<SousCategorie> existante= listeCategorie.getSelectionModel().isEmpty()?
											DAOSousCategorie.getSousCategories():
									        DAOSousCategorie.getSousCategories(listeCategorie.getSelectionModel().getSelectedItem());
		for(SousCategorie sousCat: existante) {
			if (sousCat.getNom().equals(newName.getText())) {
				return true;
			}
		}
		return false;
    }
    
    
    
    /**
     * Méthode qui permet d'effectuer un renomage d'une sous-catégorie selectionnée
     * Avec une vérification pour éviter les homonymes
     */
    @FXML
    public void modifEnBase() {
    	
    	if ((!(listeSousCat.getSelectionModel().getSelectedItem().isEmpty()) && !(newName.getText().equals("")))
    		&& !testHomonymes() ) {
	    	DAOSousCategorie.modifierNom(listeSousCat.getSelectionModel().getSelectedItem(), newName.getText());
	    	creationPopUpModificationSousCatValide();
			// Rafraîchissement de la page
			try {
				initialisation = false;
				initCat = false;
				stage = (Stage)buttonRetour.getScene().getWindow();
				setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationSousCategorie.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
    	} else {
    		creationPopUp("FenetrePopUpNomInvalide.fxml");
    	}
    }
    
    /**
     * Méthode qui permet de lancer une pop-up
     * @param fxml Fichier fxml de la pop-up à choisir
     */
    public void creationPopUp(String fxml) {
    	Stage dialog = new Stage();
    	try {
	        dialog.initModality(Modality.APPLICATION_MODAL);
	        // Localisation du fichier FXML
	        final URL url = getClass().getResource(fxml);
	        // Création du loader.
	        final FXMLLoader fxmlLoader = new FXMLLoader(url);
	        // Chargement du FXML
	        AnchorPane root = (AnchorPane) fxmlLoader.load();
	        Scene dialogScene = new Scene(root, 300, 200);
	        dialog.setScene(dialogScene);

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	dialog.setTitle("Confirmer la suppression ?");
        dialog.show();
    }
    
    /**
     * Méthode qui permet d'effectuer la suppression d'une sous-catégorie selectionnée
     */
    @FXML
    public void supprEnBase() {
    	if (!(listeSousCat.getSelectionModel().isEmpty())) {
    		valeurASupprimer = listeSousCat.getSelectionModel().getSelectedItem();
    		// Rafraichissement de la page
    		try {
    			initialisation = false;
    			initCat = false;
    			stage = (Stage)buttonRetour.getScene().getWindow();
    			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationSousCategorie.fxml")));
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		creationPopUp("FenetrePopUpSuppression.fxml");
    	}
    }
    
    /**
     * Méthode qui permet d'initialiser la ComboBox des catégories
     */
    @FXML
    public void setCategories() {
		if (!initCat) {
			ArrayList<Categorie> categories = DAOCategorie.findCategorie();
			for (Categorie element: categories) {
				listeCategorie.getItems().add(element.getNom());
			}
			initCat = true;
		}
    }
    
    
    /**
     * Méthode qui permet d'initialiser la ComboBox des sous-catégories
     * en fonction de la catégorie choisie avant (facultatif)
     */
    @FXML
    public void initialize() {
    	if (!initialisation) {
    		ArrayList<SousCategorie> listeSousCategorie = 
    				listeCategorie.getSelectionModel().getSelectedItem()==null?
    						DAOSousCategorie.getSousCategories()
    						:DAOSousCategorie.getSousCategories(listeCategorie.getSelectionModel().getSelectedItem());
    		for (SousCategorie sousCat: listeSousCategorie) {
    			listeSousCat.getItems().add(sousCat.getNom());
    		}
    		initialisation = true;
    	}
    }
    
    /**
     * Remet à zéro la ComboBox des sous-catégories lors de la sélection
     * d'une nouvelle catégorie
     */
    @FXML
    public void resetListeSousCat() {
    	listeSousCat.getItems().clear();
    	initialisation = false;
    }
    
    
    /**
     * Lance une pop-up qui confirme la modification
     */
	public void creationPopUpModificationSousCatValide() {
        Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUp"
            		                           + "ModificationCatValide.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Modifification effectuée");
        dialog.show();
	}
}
