package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gestionCategories.Categorie;
import gestionCategories.DAOCategorie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CreationCategorieController implements Initializable {
		
	/** 
	 * Constante définie pour avoir un nombre de cractères maximum
	 * pour le nom d'une catégorie 
	 */
	private static final int CARACTERES_MAX = gestionCategories.Categorie.NOMBRE_CARACTERE_MAX;
	
	/** 
	 * Est utile pour le changement de fenetre, il récupère la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
    /**
     * Button qui permettra de revenir sur la page précédente
     * Ici le menu de gestion
     */
	@FXML
	private Button buttonFlecheBack;

	/**
	 * Emplacement pour mettre le nom de la future catégorie
	 * A remplir par l'utilisateur
	 */
	@FXML
	private TextField nomCategorie;
	
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
	
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
     * Liée à buttonFlecheBack
     */
	@FXML
	private void actionButtonFlecheBack() {
		try {
			stage = (Stage)buttonFlecheBack.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreGestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui vérifie si le nouveau nom entré
	 * existe déjà en base, pour éviter les homonymes
	 * @param aVerifier Nom de la future catégorie à tester
	 * @return true si il y a un homonyme
	 * 		   false sinon
	 */
	public boolean estPresent(String aVerifier) {
		ArrayList<Categorie> categoriePresente = DAOCategorie.findCategorie();
		int i = 0;
		
		for( i =0; i < categoriePresente.size() && !aVerifier.equalsIgnoreCase(categoriePresente.get(i).getNom()); i++);
		return i < categoriePresente.size();
	}
	
	/**
	 * Méthode qui va créer la catégorie demandée
	 * Elle vérifie les homonymes et si le champ
	 * pour entrer le nom est vide ou si le nom
	 * fait trop de caractères
	 */
	@FXML
	private void creationCategorie() {
		if (nomCategorieValide()) {
			 if( !estPresent(nomCategorie.getText())) {
				 Categorie cadd = new Categorie(nomCategorie.getText());
			     DAOCategorie.create(cadd);
			     creationPopUpCatValide();
			 } else {
				 System.out.println("Erreur, catégorie déja existante");
				 creationPopUpCategorieExistante();				
			 }
		} else {
			System.out.println("Erreur lors de la saisie du nom de " 
					+ "catégorie");
			creationPopUp();
		}
	}
	
	/**
	 * Méthode qui vérifie si le nom entré par l'utilisateur
	 * est correct
	 * Il doit être non nul et inférieure à la taille max
	 * @return true si c'est correct
	 * 		   false sinon
	 */
    public boolean nomCategorieValide() {
		return !(nomCategorie.getText()==null 
				|| nomCategorie.getText().length()== 0 
				|| nomCategorie.getText().length() > CARACTERES_MAX);
	}
	
    /**
     * Méthode qui effectue la création d'une 
     * pop-up lorsque le nom saisi est un homonyme
     */
	private void creationPopUpCategorieExistante() {
		Stage dialog = new Stage();
        try {
                 dialog.initModality(Modality.APPLICATION_MODAL);
                 // Localisation du fichier FXML
                 final URL url = getClass().getResource("FenetrePopUpNomCategorieExistante.fxml"); //A MODIFIER
                 // Création du loader.
                 final FXMLLoader fxmlLoader = new FXMLLoader(url);
                 // Chargement du FXML
                 AnchorPane root = (AnchorPane) fxmlLoader.load();
                 Scene dialogScene = new Scene(root, 300, 200);
                 dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Nom categorie incorrect");
        dialog.show();
	}
	
    /**
     * Méthode qui effectue la création d'une 
     * pop-up lorsque la catégorie est bien créée
     */
	public void creationPopUpCatValide() {
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
        dialog.setTitle("Catégorie crée");
        dialog.show();
	}
	
    /**
     * Méthode qui effectue la création d'une 
     * pop-up lorsque le nom saisi n'est pas correct
     */
	public void creationPopUp() {
        Stage dialog = new Stage();
        try {
                 dialog.initModality(Modality.APPLICATION_MODAL);
                 // Localisation du fichier FXML
                 final URL url = getClass().getResource("FenetrePopUpNomCategorieInvalide.fxml");
                 // Création du loader.
                 final FXMLLoader fxmlLoader = new FXMLLoader(url);
                 // Chargement du FXML
                 AnchorPane root = (AnchorPane) fxmlLoader.load();
                 Scene dialogScene = new Scene(root, 300, 200);
                 dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Nom categorie incorrect");
        dialog.show();
	}
	
	/**
	 * Méthode obligatoire pour tous les controller
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

}
