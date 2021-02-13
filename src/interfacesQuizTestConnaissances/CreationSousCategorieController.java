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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class CreationSousCategorieController implements Initializable {

	/** Empêche l'affichage du warning */
	@SuppressWarnings("unused")
	
	/** Création fenêtre */
	private Stage stage;
	
	/** 
	 * Nombre maximal de caractères pour un nom de sous-catégorie 
	 * ou de lien photo 
	 */
	private static final int CARACTERES_MAX
	= gestionCategories.SousCategorie.NOMBRE_CARACTERE_MAX;
	
	/** Conteneur : FenetreCreationSousCategorie.fxml */
	@FXML
	private AnchorPane dynamicPane;
	 
	/** Bouton : appelle méthode creationSousCategorie() */
	@FXML 
	private Button createSousCatButton;
	
	/** Bouton : appelle méthode retour() */
	@FXML 
	private Button buttonRetour;
	
	/** Champ : où l'utilisateur rentre le nom de la nouvelle sous-catégorie */
	@FXML 
	private TextField nomSousCategorie;
	
	/** 
	 * Champ : où l'utilisateur rentre le lien de la photo correspondant
	 * à la nouvelle sous-catégorie
	 */
	@FXML 
	private TextField lienPhoto;
	
	/** Liste déroulante : avec toutes les catégories créées */
	@FXML
	private ComboBox<String> listeCategorie;
	
	/** Catégorie selectionnée dans la  liste déroulante listeCategorie */
	private String surCategorie;
	
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
	 * Méthode : à partir de la classe DAOCategorie
	 * Initialise la catégorie qui représente les sous-catégories créent
	 */
	@FXML
    public void setCategorie() {
		if (!initCat) {
			ArrayList<Categorie> categories = DAOCategorie.findCategorie();
			for (Categorie element: categories) {
				listeCategorie.getItems().add(element.getNom());
			}
			initCat = true;
		}
	}
	
	/**
	 * Méthode : quand l'utilisateur clique sur le bouton -> buttonretour
	 * Ramène l'utilisateur à la page FenetreGestion.fxml
	 */
	@FXML
	public void retour() {
		try {
			initCat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass()
					       .getResource("FenetreGestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode : vérifie si le texte écrit dans le TextField nomSousCategorie
	 * est non-nul et inférieur au nombre de caratères maximum (150)
	 * @return vrai si le 0<TextField<=150
	 *         faux si non
	 */
	public boolean nomSousCategorieValide() {
		
		return !(nomSousCategorie.getText()==null 
				|| nomSousCategorie.getText().length()== 0 
				|| nomSousCategorie.getText().length() > CARACTERES_MAX);
	}
	
	/**
	 * Non utilisé pour l'instant
	 * Méthode : vérifie si le texte écrit dans le TextField lienPhoto
	 * est non-nul et inférieur au nombre de caratères maximum (150)
	 * @return vrai si le 0<TextField<=150
	 *         faux si non
	 */
	public boolean nomLienPhotoValide() {
			
		return !(lienPhoto.getText()==null 
				 || lienPhoto.getText().length()== 0
				 || lienPhoto.getText().length() > CARACTERES_MAX);
	}
	
	/**
	 * Méthode : vérifie s'il y a une possibilité qu'il y ai des homonymes
	 * au sein de la base de données
	 * @return true s'il ny a pas d'homonyme
	 *         false s'il y au moins un homonyme entre deux sous-catégorie
	 *         ou entre une catégorie et une sous-catégorie
	 */
	private boolean testHomonymes() {
		ArrayList<SousCategorie> existante= listeCategorie.getSelectionModel().isEmpty()?
											DAOSousCategorie.getSousCategories():
									        DAOSousCategorie.getSousCategories(listeCategorie.getSelectionModel().getSelectedItem());
											
		for(SousCategorie sousCat: existante) {
			if (sousCat.getNom().equals(nomSousCategorie.getText())) {
				return true;
			}
		}
		return false;
    }
		
	/**
	 * Méthode : à partir de la classe DAOSousCategorie
	 * lorsque l'utilisateur clique sur le Button createSousCatButton
	 * amène les variables entrées dans la base de données correspondant aux
	 * sous catégorie
	 * Si nomSousCategorie() est valide alors l'insertion des ces données 
	 * seront valides
	 * Si non, application affiche une fenêtre PopUp avertissant l'utilisateur
	 * Le cas du lien de la photo n'est pas encore pris en compte
	 */
	@FXML
	public void creationSousCategorie() {
		surCategorie = listeCategorie.getSelectionModel()
								     .getSelectedItem();
		
		if(!(surCategorie==null)) {
			if (nomSousCategorieValide() && !(testHomonymes())) {
				//mise en place de ces variables dans la base de données
				DAOSousCategorie.creerEnBase(nomSousCategorie.getText(),
											lienPhoto.getText(),surCategorie);
				//affichage FenetrePopUpCreationSousCatValide
				creationPopUpSousCatValide();
			} else {
				//averti le programmeur si nomSousCategorieValide() -> false
				System.out.println("Erreur lors de la saisie du nom de " 
						+ "la sous-catégorie ou du lien de la photo");
				//affichage FenetrePopUpNomInvalide
				creationPopUpNomInvalide();
			}
		}
	}
	
	/**
	 * Si nomSousCategorieValide -> true
	 * alors cette fenêtre avertie l'utilisateur de la réussite
	 */
	public void creationPopUpSousCatValide() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUpCreation"
            		                              + "SousCatValide.fxml");
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
	 * Méthode par défaut : initialise les variables situées dans la fenêtre
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	
}
