/**
 * FenetreHistoriqueTestController.java
 * 05/03/2021
 */
package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gestionCategories.DAOCategorie;
import gestionCategories.DAOSousCategorie;
import gestionEnregistrementPartie.DAODetailPartieJoue;
import gestionEnregistrementPartie.DAOHistoriquePartie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller pour gerer l'historique des tests
 * @author Nicolas.A
 *
 */
public class FenetreHistoriqueTestController implements Initializable {
	
	/** 
	 * Est utile pour le changement de fenetre, il récupère la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
	/**
	 * Correspondance de chaque colonne 
	 */
	@FXML
	private TableView<Historique> view ;
	
	@FXML
	private TableColumn<Historique, Long> numPartie;
	
	@FXML
	private TableColumn<Historique, String> date ;
	
	@FXML
	private TableColumn<Historique, String> heure;
	
	@FXML
	private TableColumn<Historique, String> categorie;
	
	@FXML
	private TableColumn<Historique, String> sousCategorie;
	
	@FXML
	private TableColumn<Historique, String> difficulte;
	
	@FXML
	private TableColumn<Historique, String> score;
	
	@FXML
	private Button buttonRejouer;
	
	@FXML
	private Button buttonSupprimer;

	@FXML
	private Button buttonRetour;

	private ArrayList<Historique> listeHist;
	
	private static ArrayList<Long> listeNumPartie;
	
	private static ArrayList<String> listeDate;
	
	private static ArrayList<String> listeHeure;
	
	private static ArrayList<String> listeCategorie;
	
	private static ArrayList<String> listeSousCategorie;
	
	private static ArrayList<String> listeDifficulte;
	
	private static ArrayList<String> listeScore;
	
	
	 private ObservableList<Historique> listHistorique;
	 
	 /**
	   * Tableau qui regroupe les différentes valeurs d'affichage
	   */
	private static int[] tabAffichage = {5,10,20,30};
	
	/**
	 * Liste qui regroupe tous les possibilités d'affichage 
	 */
	@FXML
	private static ComboBox<String> listeValeur = new ComboBox<String>();
	
	/**
     * Boolean d'indication pour la liste d'affichage
     * True s'il est nulle
     * False sinon
     */
    private static boolean initListe = false;
    
    private static String nbQuestions;
	
	/** Element graphique, c'est le corps de l'interface*/
	@FXML
	AnchorPane dynamicPane;


	/**
     * M�thode qui permet d'initialiser la ComboBox d'affichage
     * avec le tableau qui regroupe les diff�rentes possibilit�s
     */
    @FXML
    public void initialize() {
    	if (!initListe) {
	    	for (int nombre: tabAffichage) {
	    		listeValeur.getItems().add(nombre + " tests");
	    	}
	    	initListe = true;
    	}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*Initialisation des listes */
		listeHist = new ArrayList<Historique>();
		listeNumPartie = new ArrayList<Long>();
		listeDate = new ArrayList<String>();
		listeHeure = new ArrayList<String>();
		listeCategorie = new ArrayList<String>();
		listeSousCategorie = new ArrayList<String>();
		listeDifficulte = new ArrayList<String>();
		listeScore = new ArrayList<String>();
		
		listeNumPartie= reverse(DAOHistoriquePartie.getNumPartie());
		ajoutValeurListe();
		
		
		// Defines how to fill data for each cell.
	    // Get value from property of Historique
		numPartie.setCellValueFactory(new PropertyValueFactory<>("numPartie"));
	    date.setCellValueFactory(new PropertyValueFactory<>("date"));
	    heure.setCellValueFactory(new PropertyValueFactory<>("heure"));
	    categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
	    sousCategorie.setCellValueFactory(new PropertyValueFactory<>("sousCategorie"));
	    difficulte.setCellValueFactory(new PropertyValueFactory<>("difficulte"));
	    score.setCellValueFactory(new PropertyValueFactory<>("score"));
	
		
		ObservableList<Historique> list = getUserList();
		view.setItems(list);
	}
	
	/**
	 * Methode qui recupere les données des test passé et les ajoute aux arrayList
	 */
	private static void ajoutValeurListe() {
		int i = 0;
		
		for (i = 0; i < listeNumPartie.size(); i++ ) {
			Long partieActuelle = listeNumPartie.get(i);
			
			String chaine = DAOHistoriquePartie.getDateHeure(partieActuelle);
			int pos = chaine.indexOf(' ');
			String date = chaine.substring(0, pos);
			String heure = chaine.substring(pos);
			
			String categorie = DAOHistoriquePartie.getCategorie(partieActuelle);
			String nomCategorie = DAOCategorie.getNomCategorie(categorie);
			
			
			String sousCategorie =  DAOHistoriquePartie.getSousCategorie(partieActuelle);
			String	nomSousCategorie = DAOSousCategorie.getNomSousCategorie(sousCategorie);
			
			
			String difficulte =  DAOHistoriquePartie.getDifficulte(partieActuelle);
			String score =  DAOHistoriquePartie.getScore(partieActuelle);
	
			listeDate.add(date);
			listeHeure.add(heure);
			listeCategorie.add(nomCategorie);
	        listeSousCategorie.add(nomSousCategorie);
	        listeDifficulte.add(difficulte);
	        listeScore.add(score);
		}
	}

	private ObservableList<Historique> getUserList() {
    	int i = 0;
    	
    	for (i = 0; i < listeNumPartie.size(); i++ ) {
    		Long partieActuelle = listeNumPartie.get(i);
    		Historique hist = new Historique(partieActuelle,
    				                        listeDate.get(i),
    				                        listeHeure.get(i), 
    				                        listeCategorie.get(i),
    				                        listeSousCategorie.get(i), 
    				                        listeDifficulte.get(i),
    				                        listeScore.get(i));
    		listeHist.add( hist);
    		
    	}
       listHistorique = FXCollections.observableArrayList(listeHist);
        return listHistorique;
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
	 * Action du bouton rejouer
	 * permet de rejouer une partie qui a été enregistré
	 */
	@FXML
	private void buttonRejouerAction() {
		Long partieSelectionné = view.getFocusModel().getFocusedItem().getNumPartie();
		
		LancementJeuController.enregistrerPartie = false;
		
		ChoixSousCategorieController.listeQuestions = DAODetailPartieJoue.getQuestionsRejouer(partieSelectionné);
		LancementJeuController.tailleListe  = ChoixSousCategorieController.listeQuestions.size();
		
		 nbQuestions = view.getFocusModel().getFocusedItem().getScore();
		int pos = nbQuestions.indexOf('/');
		nbQuestions = nbQuestions.substring(pos+1);
		LancementJeuController.nbQuestions = Integer.parseInt(nbQuestions);
		
		lancementJeuClassique();
	}
	
	@FXML
	private void buttonSupprimerAction() {
		Long partieSelectionné = view.getFocusModel().getFocusedItem().getNumPartie();
        DAOHistoriquePartie.deleteHistorique(partieSelectionné);
        try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreHistoriqueTest.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lancement vers FenetreLancementJeu
	 * Initialise egalement les variables de la classe LancementJeuController
	 */
	public void lancementJeuClassique() {
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreLancementJeu.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * V�rifie la taille de la liste des questions,
	 * si cette derni�re est vide -> retour � ChoixSousCategorie
	 * si cette derni�re est trop courte -> demande � l'utilisateur 
	 * 										s'il veut continuer
	 * sinon, lancement classique du jeu
	 */
	public boolean verificationTailleListe() {
		 int tailleListe = ChoixSousCategorieController.listeQuestions.size();
		
		if (tailleListe == 0) {
			System.out.println("Pas de questions");
			creationPopUpAvertissement();
			return false;
		} else if (tailleListe < Integer.parseInt(nbQuestions)) {
			// le nombre de question correspondra alors � la liste
			LancementJeuController.enregistrerPartie = false;
			System.out.println("Pas assez de questions. Voulez-vous continuer ?");
			affichagePeuDeQuestions();
			return false;
		} else {
			return true;
		}
	}
	
	private void affichagePeuDeQuestions() {
		/* On affiche la fenetre de demande */
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetrePasAssezDeQuestions.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void creationPopUpAvertissement() {
		Stage dialog = new Stage();
	    try {
	    	dialog.initModality(Modality.APPLICATION_MODAL);
	        // Localisation du fichier FXML
	        final URL url = getClass().getResource("FenetrePopUpAvertissement.fxml");
	        // Cr�ation du loader.
	        final FXMLLoader fxmlLoader = new FXMLLoader(url);
	        // Chargement du FXML
	        AnchorPane root = (AnchorPane) fxmlLoader.load();
	        Scene dialogScene = new Scene(root, 300, 200);
	        dialog.setScene(dialogScene);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    dialog.setTitle("Pas de questions");
	    dialog.show();
	}


	/**
	 * Permet d'afficher l'historiques des test du plus recent au plus anciens
	 * @param arrayList
	 * @return
	 */
	private static ArrayList<Long> reverse(ArrayList<Long> arrayList) {
		ArrayList<Long> result = new ArrayList<Long>();
		for(int i=arrayList.size()-1; i>=0; i--)
		    result.add(arrayList.get(i));
		return result;
	    }

	/**
     * Méthode qui affiche la fenêtre précédente
     * Ici l'accueil
     * Liée à buttonRetour
     */
    @FXML
	public void retour() {
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    

}
