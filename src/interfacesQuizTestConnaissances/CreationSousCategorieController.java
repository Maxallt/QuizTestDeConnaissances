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

	/** Emp�che l'affichage du warning */
	@SuppressWarnings("unused")
	
	/** Cr�ation fen�tre */
	private Stage stage;
	
	/** 
	 * Nombre maximal de caract�res pour un nom de sous-cat�gorie 
	 * ou de lien photo 
	 */
	private static final int CARACTERES_MAX
	= gestionCategories.SousCategorie.NOMBRE_CARACTERE_MAX;
	
	/** Conteneur : FenetreCreationSousCategorie.fxml */
	@FXML
	private AnchorPane dynamicPane;
	 
	/** Bouton : appelle m�thode creationSousCategorie() */
	@FXML 
	private Button createSousCatButton;
	
	/** Bouton : appelle m�thode retour() */
	@FXML 
	private Button buttonRetour;
	
	/** Champ : o� l'utilisateur rentre le nom de la nouvelle sous-cat�gorie */
	@FXML 
	private TextField nomSousCategorie;
	
	/** 
	 * Champ : o� l'utilisateur rentre le lien de la photo correspondant
	 * � la nouvelle sous-cat�gorie
	 */
	@FXML 
	private TextField lienPhoto;
	
	/** Liste d�roulante : avec toutes les cat�gories cr��es */
	@FXML
	private ComboBox<String> listeCategorie;
	
	/** Cat�gorie selectionn�e dans la  liste d�roulante listeCategorie */
	private String surCategorie;
	
	/**  Initilisation des cat�gories correctes ? */
	private static boolean initCat = false;
	
	/**
	 * M�thode : dynamise le changement de fen�tre lorsque l'utilisateur
	 * fait une action
	 * @param dynamicPane
	 */
	private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
	/**
	 * M�thode : � partir de la classe DAOCategorie
	 * Initialise la cat�gorie qui repr�sente les sous-cat�gories cr�ent
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
	 * M�thode : quand l'utilisateur clique sur le bouton -> buttonretour
	 * Ram�ne l'utilisateur � la page FenetreGestion.fxml
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
	 * M�thode : v�rifie si le texte �crit dans le TextField nomSousCategorie
	 * est non-nul et inf�rieur au nombre de carat�res maximum (150)
	 * @return vrai si le 0<TextField<=150
	 *         faux si non
	 */
	public boolean nomSousCategorieValide() {
		
		return !(nomSousCategorie.getText()==null 
				|| nomSousCategorie.getText().length()== 0 
				|| nomSousCategorie.getText().length() > CARACTERES_MAX);
	}
	
	/**
	 * Non utilis� pour l'instant
	 * M�thode : v�rifie si le texte �crit dans le TextField lienPhoto
	 * est non-nul et inf�rieur au nombre de carat�res maximum (150)
	 * @return vrai si le 0<TextField<=150
	 *         faux si non
	 */
	public boolean nomLienPhotoValide() {
			
		return !(lienPhoto.getText()==null 
				 || lienPhoto.getText().length()== 0
				 || lienPhoto.getText().length() > CARACTERES_MAX);
	}
	
	/**
	 * M�thode : v�rifie s'il y a une possibilit� qu'il y ai des homonymes
	 * au sein de la base de donn�es
	 * @return true s'il ny a pas d'homonyme
	 *         false s'il y au moins un homonyme entre deux sous-cat�gorie
	 *         ou entre une cat�gorie et une sous-cat�gorie
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
	 * M�thode : � partir de la classe DAOSousCategorie
	 * lorsque l'utilisateur clique sur le Button createSousCatButton
	 * am�ne les variables entr�es dans la base de donn�es correspondant aux
	 * sous cat�gorie
	 * Si nomSousCategorie() est valide alors l'insertion des ces donn�es 
	 * seront valides
	 * Si non, application affiche une fen�tre PopUp avertissant l'utilisateur
	 * Le cas du lien de la photo n'est pas encore pris en compte
	 */
	@FXML
	public void creationSousCategorie() {
		surCategorie = listeCategorie.getSelectionModel()
								     .getSelectedItem();
		
		if(!(surCategorie==null)) {
			if (nomSousCategorieValide() && !(testHomonymes())) {
				//mise en place de ces variables dans la base de donn�es
				DAOSousCategorie.creerEnBase(nomSousCategorie.getText(),
											lienPhoto.getText(),surCategorie);
				//affichage FenetrePopUpCreationSousCatValide
				creationPopUpSousCatValide();
			} else {
				//averti le programmeur si nomSousCategorieValide() -> false
				System.out.println("Erreur lors de la saisie du nom de " 
						+ "la sous-cat�gorie ou du lien de la photo");
				//affichage FenetrePopUpNomInvalide
				creationPopUpNomInvalide();
			}
		}
	}
	
	/**
	 * Si nomSousCategorieValide -> true
	 * alors cette fen�tre avertie l'utilisateur de la r�ussite
	 */
	public void creationPopUpSousCatValide() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUpCreation"
            		                              + "SousCatValide.fxml");
            // Cr�ation du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Sous-cat�gorie cr�e");
        dialog.show();
	}
	
	/**
	 * Si nomSousCategorieValide -> false
	 * alors cette fen�tre averti l'utilisateur de son erreur
	 */
	public void creationPopUpNomInvalide() {
        Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUp"
            		                               + "NomInvalide.fxml");
            // Cr�ation du loader.
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
	 * M�thode par d�faut : initialise les variables situ�es dans la fen�tre
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
	
}
