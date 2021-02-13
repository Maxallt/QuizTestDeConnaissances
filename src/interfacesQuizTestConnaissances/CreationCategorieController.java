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
	 * Constante d�finie pour avoir un nombre de cract�res maximum
	 * pour le nom d'une cat�gorie 
	 */
	private static final int CARACTERES_MAX = gestionCategories.Categorie.NOMBRE_CARACTERE_MAX;
	
	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
    /**
     * Button qui permettra de revenir sur la page pr�c�dente
     * Ici le menu de gestion
     */
	@FXML
	private Button buttonFlecheBack;

	/**
	 * Emplacement pour mettre le nom de la future cat�gorie
	 * A remplir par l'utilisateur
	 */
	@FXML
	private TextField nomCategorie;
	
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
	
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
     * Li�e � buttonFlecheBack
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
	 * M�thode qui v�rifie si le nouveau nom entr�
	 * existe d�j� en base, pour �viter les homonymes
	 * @param aVerifier Nom de la future cat�gorie � tester
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
	 * M�thode qui va cr�er la cat�gorie demand�e
	 * Elle v�rifie les homonymes et si le champ
	 * pour entrer le nom est vide ou si le nom
	 * fait trop de caract�res
	 */
	@FXML
	private void creationCategorie() {
		if (nomCategorieValide()) {
			 if( !estPresent(nomCategorie.getText())) {
				 Categorie cadd = new Categorie(nomCategorie.getText());
			     DAOCategorie.create(cadd);
			     creationPopUpCatValide();
			 } else {
				 System.out.println("Erreur, cat�gorie d�ja existante");
				 creationPopUpCategorieExistante();				
			 }
		} else {
			System.out.println("Erreur lors de la saisie du nom de " 
					+ "cat�gorie");
			creationPopUp();
		}
	}
	
	/**
	 * M�thode qui v�rifie si le nom entr� par l'utilisateur
	 * est correct
	 * Il doit �tre non nul et inf�rieure � la taille max
	 * @return true si c'est correct
	 * 		   false sinon
	 */
    public boolean nomCategorieValide() {
		return !(nomCategorie.getText()==null 
				|| nomCategorie.getText().length()== 0 
				|| nomCategorie.getText().length() > CARACTERES_MAX);
	}
	
    /**
     * M�thode qui effectue la cr�ation d'une 
     * pop-up lorsque le nom saisi est un homonyme
     */
	private void creationPopUpCategorieExistante() {
		Stage dialog = new Stage();
        try {
                 dialog.initModality(Modality.APPLICATION_MODAL);
                 // Localisation du fichier FXML
                 final URL url = getClass().getResource("FenetrePopUpNomCategorieExistante.fxml"); //A MODIFIER
                 // Cr�ation du loader.
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
     * M�thode qui effectue la cr�ation d'une 
     * pop-up lorsque la cat�gorie est bien cr��e
     */
	public void creationPopUpCatValide() {
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
        dialog.setTitle("Cat�gorie cr�e");
        dialog.show();
	}
	
    /**
     * M�thode qui effectue la cr�ation d'une 
     * pop-up lorsque le nom saisi n'est pas correct
     */
	public void creationPopUp() {
        Stage dialog = new Stage();
        try {
                 dialog.initModality(Modality.APPLICATION_MODAL);
                 // Localisation du fichier FXML
                 final URL url = getClass().getResource("FenetrePopUpNomCategorieInvalide.fxml");
                 // Cr�ation du loader.
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
	 * M�thode obligatoire pour tous les controller
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

}
