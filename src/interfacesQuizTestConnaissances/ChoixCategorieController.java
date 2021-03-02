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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ChoixCategorieController implements Initializable {

	private static boolean initCat = false;
	private static boolean initDifficulte = false;
	
	private static String surCategorie;
	private static String difficulteChoisie;
	
	public static String getSurCategorie() {
		return surCategorie;
	}

	public static String getDifficulteChoisie() {
		return difficulteChoisie;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		surCategorie = null;
		difficulteChoisie = null;
	}
	
	@SuppressWarnings("unused")
    private Stage stage;
	
    @FXML
    AnchorPane dynamicPane;
	
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
	
	@FXML
	private Button buttonFlecheBack;
	
	@FXML
	private Button valider;
	
	@FXML
	private ComboBox<String> listeCategorie = new ComboBox<String>();
	
	@FXML
	private ComboBox<String> listeDifficulte = new ComboBox<String>();
	
	private static String[] tabDifficulte = {"Facile","Moyen","Difficile","Indifférent"};
	
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
	
	@FXML
    public void setDifficulte() {
		if(!initDifficulte) {
			for (String element: tabDifficulte) {
				listeDifficulte.getItems().add(element);
			}
			initDifficulte = true;
		}
	}
	
	@FXML
	private void ActionButtonGo() {
		if (listeCategorie.getSelectionModel().getSelectedItem() != null && listeDifficulte.getSelectionModel().getSelectedItem()!=null) {
			surCategorie = listeCategorie.getSelectionModel().getSelectedItem();
			difficulteChoisie = listeDifficulte.getSelectionModel().getSelectedItem();
			try {
				initCat = false;
				initDifficulte = false;
				stage = (Stage)buttonFlecheBack.getScene().getWindow();
				setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreSousCategorie.fxml")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@FXML
	private void actionButtonFlecheBack() {
		try {
			initCat = false;
			initDifficulte = false;
			stage = (Stage)buttonFlecheBack.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * Méthode qui renvoie l'id de la catégorie actuelle
     * @return idSousCat contient l'id de la catégorie
     *         Renvoie -1 si aucune sous-catégorie n'a été selectionné
     */
    public static int getIdCatActuelle() {
		if (surCategorie == null) {
			return -1;
		} else {
			return DAOCategorie.getId(surCategorie);

		}	
	}

}
