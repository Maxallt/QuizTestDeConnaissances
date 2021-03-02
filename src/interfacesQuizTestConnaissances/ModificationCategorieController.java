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
	 * Nombre maximal de caract�res pour un nom de sous-cat�gorie 
	 * ou de lien photo 
	 */
	private static final int CARACTERES_MAX
	= gestionCategories.SousCategorie.NOMBRE_CARACTERE_MAX;
	
	/** Conteneur : FenetreModificationCategorie.fxml */
    @FXML
    private AnchorPane dynamicPane;
	
    /** Cr�ation fen�tre */
	@SuppressWarnings("unused") //Emp�che l'affichage du warning 
    private Stage stage;
	
	/** Liste d�roulante des cat�gories */
	@FXML 
	private ComboBox<String> listeCategorie;
	
	/** Liste d�roulante des sous-cat�gories */
	@FXML 
	private ComboBox<String> listeSousCat;
	
	/** Nom de la cat�gorie selectionn�e*/
	@FXML 
	private Text nomCategorie;
	
	/** Champ o� l'utilisateur peut choisir le nouveau nom pour sa cat�gorie */
	@FXML 
	private TextField nouveauNomCategorie;
	
	/** Bouton permettant � l'uilisateur de revenir � la fen�tre pr�c�dente */
	@FXML
    private Button buttonRetour;
	
	/**Bouton qui permet de supprimer la sous-cat�gorie selectionn�e */
//	private Button buttonSupprSousCat;
	
	/** Repr�sente la categorie s�lectionn�e dans la liste d�roulante */
	static String categorieSelectionnee; 
	
	/** Repr�sente la sous-categorie s�lectionn�e dans la liste d�roulante */
	static String sousCategorieSelectionnee;
	
	/** Intitialisation de l'application correcte ? */
	private static boolean initialisation = false;
	
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
	 * M�thode : quand l'utilisateur clique sur le bouton -> buttonretour
	 * Ram�ne l'utilisateur � la page FenetreGestion.fxml
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
	 * M�thode : � partir de la classe DAOCategorie
	 * Initialise la cat�gorie qui repr�sente les sous-cat�gories cr�ent
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
	 * Initialise la listes d�roulante des sous-cat�gories
	 * Toutes les sous-cat�gories -> par d�faut
	 * Les sous-cat�gories de la cat�gorie que l'utilisateur a choisis
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
	 * Remet la liste d�roulante quand on change de cat�gorie
	 */
	@FXML
    public void resetListeSousCat() {
    	listeSousCat.getItems().clear();
    	initialisation = false;
    }
	
	/**
	 * M�thode: v�rifie si le texte �crit dans le TextField nouveauNomCategorie
	 * est non-nul et inf�rieur au nombre de carat�res maximum (150)
	 * @return vrai si le 0<TextField<=150
	 *         faux si non
	 */
	public boolean nomCategorieValide() {
		
		return (!( nouveauNomCategorie.getText()==null
				|| nouveauNomCategorie.getText().length()==0
				|| nouveauNomCategorie.getText().length()>=CARACTERES_MAX));
	}
	
	/**
	 * M�thode : v�rifie s'il y a une possibilit� qu'il y ai des homonymes
	 * au sein de la base de donn�es
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
	 * M�thode : permet � l'utilisateur de changer le nom de la cat�gorie
	 * Si 0<TextField<=150 alors la modification s'op�re
	 * Si non ou la il s'agit de modifier G�n�rale 
	 * alors une fen�tre PopUp s'affichera concernant un invalidit� 
	 */
	@FXML
	public void modificationNomCategorie() {
		
		categorieSelectionnee = listeCategorie.getSelectionModel()
				.getSelectedItem();

		if (!(categorieSelectionnee==null)) {
			if (nomCategorieValide() && !(testHomonymes()) && !(categorieSelectionnee.equals("G�n�ral"))) {
	
				DAOCategorie.update(nouveauNomCategorie.getText(), 
						categorieSelectionnee);
				creationPopUpModificationCatValide();
			} else if (categorieSelectionnee.equals("G�n�ral")) {
				System.out.println("Impossible de modifier G�n�ral");
				creationPopUpGeneralChoisis();
			} else {
				System.out.println("Erreur de saisie");
				creationPopUpNomInvalide();
			}
		}
	}
	
	/**
	 * Si nomSousCategorieValide -> true
	 * alors cette fen�tre averti l'utilisateur du succ�s
	 * de la modification
	 */
	public void creationPopUpModificationCatValide() {
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
	 * Si l'utilisateur essaie d'effectuer une action sur une seletion vide
	 * alors cette fen�tre averti l'utilisateur de son erreur
	 */
	public void creationPopUpNomSelectionVide() {
        Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUpSelectionVide.fxml");
            // Cr�ation du loader.
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
	 * Affichage d'une fen�tre PopUp pour savoir si l'utilisateur veut vraiment
	 * supprimer la cat�gorie s�lectionn�e
	 */
	@FXML 
	public void supprimerCategorie() {
		categorieSelectionnee = listeCategorie.getSelectionModel()
                .getSelectedItem();

		if (!(categorieSelectionnee==null)) {

			if(categorieSelectionnee.equals("G�n�ral")) {
				System.out.println("Impossible de supprimer G�n�ral");
				creationPopUpGeneralChoisis();
			} else {
				Stage dialog = new Stage();
				try {
					dialog.initModality(Modality.APPLICATION_MODAL);
					// Localisation du fichier FXML
					final URL url = getClass().getResource("FenetrePopUpSupprimerCategorie.fxml");
					// Cr�ation du loader.
					final FXMLLoader fxmlLoader = new FXMLLoader(url);
					// Chargement du FXML
					AnchorPane root = (AnchorPane) fxmlLoader.load();
					Scene dialogScene = new Scene(root, 300, 200);
					dialog.setScene(dialogScene);
				} catch (IOException e) {
					e.printStackTrace();
				}
				dialog.setTitle("Suppression d'une cat�gorie");
				dialog.show();
			}
		} else {
			creationPopUpNomSelectionVide();
			System.out.println("aucune cat selectionn�");
		}
	}
	
	/**
	 * Si l'utilisateur veut changer g�n�ral
	 * alors cette fen�tre l'averti que c'est impossible
	 */
	public void creationPopUpGeneralChoisis() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUpGeneralChoisis.fxml");
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
	 * Affichage d'une fen�tre PopUp pour savoir si l'utilisateur veut vraiment
	 * supprimer la sous-cat�gorie s�lectionn�e
	 */
	@FXML
	public void supprimerSousCategorie() {
		
		categorieSelectionnee = listeSousCat.getSelectionModel()
				  							  .getSelectedItem();
		
		if (!(categorieSelectionnee==null)) {
			
			if(categorieSelectionnee.equals("G�n�ral")) {
				System.out.println("Impossible de supprimer G�n�ral");
				creationPopUpGeneralChoisis();
			} else {
				Stage dialog = new Stage();
		        try {
		        	dialog.initModality(Modality.APPLICATION_MODAL);
		            // Localisation du fichier FXML
		            final URL url = getClass().getResource("FenetrePopUpSupprimerSousCategorie.fxml");
		            // Cr�ation du loader.
		            final FXMLLoader fxmlLoader = new FXMLLoader(url);
		            // Chargement du FXML
		            AnchorPane root = (AnchorPane) fxmlLoader.load();
		            Scene dialogScene = new Scene(root, 300, 200);
		            dialog.setScene(dialogScene);
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
		        dialog.setTitle("Suppression d'une sous-cat�gorie");
		        dialog.show();
			}
		} else {
			creationPopUpNomSelectionVide();
			System.out.println("aucune Sous-cat selectionn�");
			
		}
	}
	
	/**
     * M�thode par d�faut : qui permet d'initialiser des variables
     * @param url nom utilis�
     * @param rb  non utilis�
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
}