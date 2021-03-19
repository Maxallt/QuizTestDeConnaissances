/*
 * ModificationSousCategorieController.java			10/12/2020
 * Controller de la fen�tre qui permet de modifer une sous cat�gorie
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
 * Controller de la fen�tre qui permet de modifer une sous cat�gorie
 * @author Maxime Alliot
 *
 */
public class ModificationSousCategorieController implements Initializable {

	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
	private Stage stage;
	
    /** 
     * Permet de v�rifier si la ComboBox avec les sous-cat�gories a �t� 
     * initialis�e, c'est � dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
    private static boolean initialisation = false;
    
    /** 
     * Permet de v�rifier si la ComboBox avec les cat�gories a �t� 
     * initialis�e, c'est � dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
	private static boolean initCat = false;

	/**
	 * M�thode obligatoire pour tous les controller
	 */
	@Override
    public void initialize(URL url, ResourceBundle rb) {}
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /**
     * Button qui permettra de revenir sur la page pr�c�dente
     * Ici le menu de gestion des des cat�gories et sous-cat�gories
     */
    @FXML
    private Button buttonRetour;
    
    /**
     * Ce bouton set � valider le choix de modification
     */
    @FXML
    private Button buttonValider;
    
    /**
     * Ce bouton set � valider le choix de suppression
     */
    @FXML
    private Button buttonSupprimer;
    
    /**
     * Endroit o� le nouveau nom d'une sous-cat�gorie 
     * sera tap� par l'utilisateur
     */
    @FXML
    private TextField newName;
    
    /**
     * Endroit o� le nouveau lien photo d'une sous-cat�gorie 
     * sera tap� par l'utilisateur
     */
    @FXML
    private TextField newLienPhoto;
    
    /**
     * Liste qui regroupe toutes les sous cat�gories de la cat�gorie
     * choisie 
     */
    @FXML
    private ComboBox<String> listeSousCat;
    
    /**
     * Liste qui regroupe toutes les cat�gories 
     */
    @FXML
    private ComboBox<String> listeCategorie;
    
    /**
     * Nom de la sous-cat�gorie � supprimer
     * utiliser dans la pop-up de validation de suppression
     */
    private static String valeurASupprimer = null;
	
    /**
     * Getter du nom de la sous-cat � supprimer
     * @return le nom de la sous-cat�gorie � supprimer
     */
    public static String getValeurASupprimer() {
		return valeurASupprimer;
	}

    /**
     * M�thode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface � afficher
     */
	private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
    /**
     * M�thode qui affiche la fen�tre pr�c�dente
     * Ici le menu de gestion
     * Li�e � buttonRetour
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
     * M�thode qui v�rifie si le nom de la sous-cat�gorie est d�j� existant
     * @return true s'il existe dej�,
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
     * M�thode qui permet d'effectuer un renomage d'une sous-cat�gorie selectionn�e
     * Avec une v�rification pour �viter les homonymes
     */
    @FXML
    public void modifEnBase() {
    	
    	if ((!(listeSousCat.getSelectionModel().getSelectedItem().isEmpty()) && !(newName.getText().equals("")))
    		&& !testHomonymes() ) {
	    	DAOSousCategorie.modifierNom(listeSousCat.getSelectionModel().getSelectedItem(), newName.getText());
	    	creationPopUpModificationSousCatValide();
			// Rafra�chissement de la page
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
     * M�thode qui permet de lancer une pop-up
     * @param fxml Fichier fxml de la pop-up � choisir
     */
    public void creationPopUp(String fxml) {
    	Stage dialog = new Stage();
    	try {
	        dialog.initModality(Modality.APPLICATION_MODAL);
	        // Localisation du fichier FXML
	        final URL url = getClass().getResource(fxml);
	        // Cr�ation du loader.
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
     * M�thode qui permet d'effectuer la suppression d'une sous-cat�gorie selectionn�e
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
     * M�thode qui permet d'initialiser la ComboBox des cat�gories
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
     * M�thode qui permet d'initialiser la ComboBox des sous-cat�gories
     * en fonction de la cat�gorie choisie avant (facultatif)
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
     * Remet � z�ro la ComboBox des sous-cat�gories lors de la s�lection
     * d'une nouvelle cat�gorie
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
            // Cr�ation du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Modifification effectu�e");
        dialog.show();
	}
}
