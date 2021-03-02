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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModificationCategorieController implements Initializable {
	
	/** 
	 * Nombre maximal de caractères pour un nom de sous-catégorie 
	 * ou de lien photo 
	 */
	private static final int CARACTERES_MAX
	= gestionCategories.SousCategorie.NOMBRE_CARACTERE_MAX;
	
	/** Conteneur : FenetreModificationCategorie.fxml */
    @FXML
    private AnchorPane dynamicPane;
	
    /** Création fenêtre */
	@SuppressWarnings("unused") //Empêche l'affichage du warning 
    private Stage stage;
	
	/** Liste déroulante des catégories */
	@FXML 
	private ComboBox<String> listeCategorie;
	
	/** Liste déroulante des sous-catégories */
	@FXML 
	private ComboBox<String> listeSousCat;
	
	/** Nom de la catégorie selectionnée*/
	@FXML 
	private Text nomCategorie;
	
	/** Champ où l'utilisateur peut choisir le nouveau nom pour sa catégorie */
	@FXML 
	private TextField nouveauNomCategorie;
	
	/** Bouton permettant à l'uilisateur de revenir à la fenêtre précédente */
	@FXML
    private Button buttonRetour;
	
	/**Bouton qui permet de supprimer la sous-catégorie selectionnée */
//	private Button buttonSupprSousCat;
	
	/** Représente la categorie sélectionnée dans la liste déroulante */
	static String categorieSelectionnee; 
	
	/** Représente la sous-categorie sélectionnée dans la liste déroulante */
	static String sousCategorieSelectionnee;
	
	/** Intitialisation de l'application correcte ? */
	private static boolean initialisation = false;
	
	/**  Initilisation des catégories correctes ? */
	private static boolean initCat = false;
    
	/**
	 * Méthode : dynamise le changement de fenêtre lorsque l'utilisateur
	 * fait une action
	 * @param dynamicPane
	 */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
    /**
	 * Méthode : quand l'utilisateur clique sur le bouton -> buttonretour
	 * Ramène l'utilisateur à la page FenetreGestion.fxml
	 */
    @FXML
	public void retour() {
		try {
			initialisation = false;
			initCat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass()
					       .getResource("FenetreGestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * Méthode : à partir de la classe DAOCategorie
	 * Initialise la catégorie qui représente les sous-catégories créent
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
	 * Initialise la listes déroulante des sous-catégories
	 * Toutes les sous-catégories -> par défaut
	 * Les sous-catégories de la catégorie que l'utilisateur a choisis
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
	 * Remet la liste déroulante quand on change de catégorie
	 */
	@FXML
    public void resetListeSousCat() {
    	listeSousCat.getItems().clear();
    	initialisation = false;
    }
	
	/**
	 * Méthode: vérifie si le texte écrit dans le TextField nouveauNomCategorie
	 * est non-nul et inférieur au nombre de caratères maximum (150)
	 * @return vrai si le 0<TextField<=150
	 *         faux si non
	 */
	public boolean nomCategorieValide() {
		
		return (!( nouveauNomCategorie.getText()==null
				|| nouveauNomCategorie.getText().length()==0
				|| nouveauNomCategorie.getText().length()>=CARACTERES_MAX));
	}
	
	/**
	 * Méthode : vérifie s'il y a une possibilité qu'il y ai des homonymes
	 * au sein de la base de données
	 * @return true s'il ny a pas d'homonyme
	 *         false s'il y a un homnyme
	 */
	private boolean testHomonymes() {
		ArrayList<Categorie> existante = DAOCategorie.findCategorie();
				
		for(Categorie cat: existante) {
			if (cat.getNom().equals(nouveauNomCategorie.getText())) {
				return true;
			}
		}
		return false;
    }
	
	/** 
	 * Méthode : permet à l'utilisateur de changer le nom de la catégorie
	 * Si 0<TextField<=150 alors la modification s'opère
	 * Si non ou la il s'agit de modifier Générale 
	 * alors une fenêtre PopUp s'affichera concernant un invalidité 
	 */
	@FXML
	public void modificationNomCategorie() {
		
		categorieSelectionnee = listeCategorie.getSelectionModel()
				.getSelectedItem();

		if (!(categorieSelectionnee==null)) {
			if (nomCategorieValide() && !(testHomonymes()) && !(categorieSelectionnee.equals("Général"))) {
	
				DAOCategorie.update(nouveauNomCategorie.getText(), 
						categorieSelectionnee);
				creationPopUpModificationCatValide();
			} else if (categorieSelectionnee.equals("Général")) {
				System.out.println("Impossible de modifier Général");
				creationPopUpGeneralChoisis();
			} else {
				System.out.println("Erreur de saisie");
				creationPopUpNomInvalide();
			}
		}
	}
	
	/**
	 * Si nomSousCategorieValide -> true
	 * alors cette fenêtre averti l'utilisateur du succès
	 * de la modification
	 */
	public void creationPopUpModificationCatValide() {
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
	
	/**
	 * Si nomSousCategorieValide -> false
	 * alors cette fenêtre averti l'utilisateur de son erreur
	 */
	public void creationPopUpNomInvalide() {
        Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUp"
            		                               + "NomInvalide.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Nom invalide");
        dialog.show();
	}
	
	/**
	 * Si l'utilisateur essaie d'effectuer une action sur une seletion vide
	 * alors cette fenêtre averti l'utilisateur de son erreur
	 */
	public void creationPopUpNomSelectionVide() {
        Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUpSelectionVide.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Selection Vide !");
        dialog.show();
	}
	
	/**
	 * Affichage d'une fenêtre PopUp pour savoir si l'utilisateur veut vraiment
	 * supprimer la catégorie sélectionnée
	 */
	@FXML 
	public void supprimerCategorie() {
		categorieSelectionnee = listeCategorie.getSelectionModel()
                .getSelectedItem();

		if (!(categorieSelectionnee==null)) {

			if(categorieSelectionnee.equals("Général")) {
				System.out.println("Impossible de supprimer Général");
				creationPopUpGeneralChoisis();
			} else {
				Stage dialog = new Stage();
				try {
					dialog.initModality(Modality.APPLICATION_MODAL);
					// Localisation du fichier FXML
					final URL url = getClass().getResource("FenetrePopUpSupprimerCategorie.fxml");
					// Création du loader.
					final FXMLLoader fxmlLoader = new FXMLLoader(url);
					// Chargement du FXML
					AnchorPane root = (AnchorPane) fxmlLoader.load();
					Scene dialogScene = new Scene(root, 300, 200);
					dialog.setScene(dialogScene);
				} catch (IOException e) {
					e.printStackTrace();
				}
				dialog.setTitle("Suppression d'une catégorie");
				dialog.show();
			}
		} else {
			creationPopUpNomSelectionVide();
			System.out.println("aucune cat selectionné");
		}
	}
	
	/**
	 * Si l'utilisateur veut changer général
	 * alors cette fenêtre l'averti que c'est impossible
	 */
	public void creationPopUpGeneralChoisis() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUpGeneralChoisis.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Sous-catégorie crée");
        dialog.show();
	}
	
	/**
	 * Affichage d'une fenêtre PopUp pour savoir si l'utilisateur veut vraiment
	 * supprimer la sous-catégorie sélectionnée
	 */
	@FXML
	public void supprimerSousCategorie() {
		
		categorieSelectionnee = listeSousCat.getSelectionModel()
				  							  .getSelectedItem();
		
		if (!(categorieSelectionnee==null)) {
			
			if(categorieSelectionnee.equals("Général")) {
				System.out.println("Impossible de supprimer Général");
				creationPopUpGeneralChoisis();
			} else {
				Stage dialog = new Stage();
		        try {
		        	dialog.initModality(Modality.APPLICATION_MODAL);
		            // Localisation du fichier FXML
		            final URL url = getClass().getResource("FenetrePopUpSupprimerSousCategorie.fxml");
		            // Création du loader.
		            final FXMLLoader fxmlLoader = new FXMLLoader(url);
		            // Chargement du FXML
		            AnchorPane root = (AnchorPane) fxmlLoader.load();
		            Scene dialogScene = new Scene(root, 300, 200);
		            dialog.setScene(dialogScene);
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
		        dialog.setTitle("Suppression d'une sous-catégorie");
		        dialog.show();
			}
		} else {
			creationPopUpNomSelectionVide();
			System.out.println("aucune Sous-cat selectionné");
			
		}
	}
	
	/**
     * Méthode par défaut : qui permet d'initialiser des variables
     * @param url nom utilisé
     * @param rb  non utilisé
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
}