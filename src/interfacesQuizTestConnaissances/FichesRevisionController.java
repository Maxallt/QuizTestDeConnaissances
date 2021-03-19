/* FichesRevisionController.java			03/03/2020
 * Controller de la fenêtre qui permet de générer des fiches en JSON
 */
package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ResourceBundle;
import java.net.URL;

import gestionCategories.Categorie;
import gestionCategories.DAOCategorie;
import gestionCategories.DAOSousCategorie;
import gestionCategories.SousCategorie;
import gestionQuestion.DaoQuestions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller de la fenêtre qui permet de modifer une sous catégorie
 * @author Nino TIZIEN
 *
 */
public class FichesRevisionController implements Initializable {

	/** Conteneur : FenetrFichesRevision.fxml */
    @FXML
    private AnchorPane dynamicPane;
	
    /** Création fenêtre */
	@SuppressWarnings("unused") //Empêche l'affichage du warning 
    private Stage stage;
		
    /** Permet de vérifier que la fiche comporte bien un nom lorsque l'utilisateur va la générer */
	private boolean valid = true;
	
    /** 
     * Permet de vérifier si la ComboBox avec les catégories a été 
     * initialisée, c'est à dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
	private static boolean initCat = false;
	
    /** 
     * Permet de vérifier si la ComboBox avec les sous-catégories a été 
     * initialisée, c'est à dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
	private static boolean initSousCat = false;
	
    /** 
     * Permet de vérifier si la ComboBox avec l'intitulé a été 
     * initialisée, c'est à dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
	private static boolean initQuestion = false;
	
    /** 
     * Permet de vérifier si la ComboBox avec les intitulés choisis a été 
     * initialisée, c'est à dire remplie.
     * Ca permet aussi de ne pas l'initialiser plusieurs fois
     */
	private static boolean initQuestionsChoisies = false;
	
	/** String qui contiendra le nom du fichier entré par l'utilisateur */
    private static String NomFichier = null;
    
    /** Tableau qui contiendra tous les intitulés choisis
     * avant de générer la fiche
     */
    private ArrayList<String> TabIntitule = new ArrayList<String>();

	
	/**
	 * M�thode obligatoire pour tous les controller
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

   }
    
    
    /**
     * Button qui permettra de revenir sur la page précédente
     * Ici le menu de gestion des des catégories et sous-catégories
     */
    @FXML
    private Button buttonRetour;
       
    /**
     * Endroit où le nom de la fiche 
     * sera tapé par l'utilisateur
     */
    @FXML
    private TextField nameFiche;
    
    /**
     * Liste qui regroupe toutes les catégories 
     */
    @FXML
    private ComboBox<String> listeCategorie;
    
    /**
     * Liste qui regroupe toutes les sous catégories de la catégorie
     * choisie 
     */
    @FXML
    private ComboBox<String> listeSousCat;
    
    
    /**
     * Liste qui regroupe toutes les questions choisies 
     */
    @FXML
    private ComboBox<String> listeQuestion;
        
    /**
     * Ce bouton sert à ajouter une question à la fiche
     */
    @FXML
    private Button buttonAjouter;
        
    /**
     * Ce bouton set à valider le choix de modification
     */
    @FXML
    private Button buttonGenerer;
    
    /**
     * Liste qui regroupe toutes les questions choisies pour la fiche
     */
    @FXML
    private ComboBox<String> listeQuestionsChoisies;
    
    /**
     * Liste qui regroupe toutes les questions choisies pour la fiche
     */
    @FXML
    private CheckBox AMR;
    
    
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
     * Ici l'accueil
     * Liée à buttonRetour
     */
    @FXML
	public void retour() {
		try {
			initCat = false;
			initSousCat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
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
    public void setSousCategorie() {
		if (!initSousCat) {
			ArrayList<SousCategorie> sousCategories = 
					listeCategorie.getSelectionModel().getSelectedItem()==null?
					DAOSousCategorie.getSousCategories():
					DAOSousCategorie.getSousCategories(listeCategorie.getSelectionModel().getSelectedItem());
			
			for (SousCategorie element: sousCategories) {
				listeSousCat.getItems().add(element.getNom());
			}
			initSousCat = true;
		}
	}
    
    /**
     * Remet à zéro la ComboBox des sous-catégories lors de la sélection
     * d'une nouvelle catégorie
     */
    @FXML
    public void resetListeSousCat() {
    	listeSousCat.getItems().clear();
    	initSousCat = false;
    }
    
    
    /**
     * Remet à zéro la combobox de des question lors de la sélection 
     * d'une nouvelle sous catégorie 
     */
    @FXML
    public void resetListeQuestion() {
    	
    	listeQuestion.getItems().clear();
        initQuestion = false;
    	
    }
    
    /**
     * Initialise la combobox questions avec les intitulés des questions
     * présente en base
     */
    @FXML
    public void setQuestion() {
ArrayList<String> tabQuestion = new ArrayList<String>();
    	
    	boolean remplir = true; 
    	
    	if(!initQuestion) {
    		
    		// Les combobox Catégorie et sous catégorie sont vide => aucune question n'est affiché
    		if (listeCategorie.getSelectionModel().getSelectedItem()==null
    			&& listeSousCat.getSelectionModel().getSelectedItem()==null){
    			
    			remplir = false;
    		}
    		
    		/* Il n'y a que la combobox des categorie qui est rempli 
    		 *	=> affiche toutes les question de la catégorie selectionné 
    		 */
    		else if (listeCategorie.getSelectionModel().getSelectedItem()!=null
    				 && listeSousCat.getSelectionModel().getSelectedItem()==null) {
    			
    			tabQuestion = DaoQuestions.getQuestions
    					(DAOCategorie.getId(listeCategorie.getSelectionModel().getSelectedItem())+"");
    		
    		}
    	
    		/* Il n'y a que la combobox des sous categorie qui est rempli 
    		 *  => affiche toutes les question de  sous categorie selectionné
    		 */
    		else if (listeCategorie.getSelectionModel().getSelectedItem()==null
   				 && listeSousCat.getSelectionModel().getSelectedItem()!=null) {
    			
    			tabQuestion = DaoQuestions.getQuestions
    					(DAOSousCategorie.getId(listeSousCat.getSelectionModel().getSelectedItem())+"");
    		}
    		
    		/* Les deux combobox ( catégorie et sous categorie) sont rempli
    		 *  => affiches toutes les question de la sous catégorie selectionné
    		 */
    		else {
    			tabQuestion = DaoQuestions.getQuestions
    					(DAOSousCategorie.getId(listeSousCat.getSelectionModel().getSelectedItem())+"");
    		}
    		
    		if (remplir) {

    	    	for (String element2 : tabQuestion) {
    				listeQuestion.getItems().add(element2);
    			}
    			initQuestion = true;
    			
    		}
    	}
    	}
    
    
    /**
     * Méthode qui ajoute les intitulés dans un array et dans la combo box
     * Liée à buttonRetour
     */
    @FXML
	public void ajouter() {
    	/* Une question est choisie lorsqu'on appuie sur le bouton
    	 * on l'ajoute à tabIntitule
    	 */
    	if (!(listeQuestion.getSelectionModel().isEmpty())) {
	    	TabIntitule.add(listeQuestion.getSelectionModel().getSelectedItem()); 
	    	listeQuestionsChoisies.getItems().clear();
	    	initQuestionsChoisies = false;
			if(!initQuestionsChoisies) {
				for (String element: TabIntitule) {
					listeQuestionsChoisies.getItems().add(element);
				}
				initQuestionsChoisies = true;
				
			}
    	}
	}
    
    
    /**
     * Méthode qui permet d'initialiser la ComboBox des questions choisies
     * par l'utilisateur avant de générer la fiche
     */
    @FXML
    public void setQuestionsChoisies() {
		if(!initQuestionsChoisies) {
			for (String element: TabIntitule) {
				listeQuestionsChoisies.getItems().add(element);
			}
			initQuestionsChoisies = true;
			
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
    	dialog.setTitle("");
        dialog.show();
    }
    
    
    
    
    /**
     * Méthode qui construit le json qui sera écrit 
     * Avec des json Array et des json Object
     */
    public JSONObject creationJson() {

    	// Tableau des questions
    	JSONArray jl = null;
    	// Une question
    	JSONObject jq;
    	// Réponses Fausse
    	JSONArray ja;
    	// Fichier
    	JSONObject jo;
    	
    	ArrayList<JSONObject> questions = new ArrayList<>();
    	
    	for(String intitule : listeQuestionsChoisies.getItems()) {
	    	ArrayList<String> InfoRep = DaoQuestions.getInfoFiche(intitule);
	    	
	    	ja = new JSONArray();
	    	ja.add(InfoRep.get(1));
	    	ja.add(InfoRep.get(2));
	    	ja.add(InfoRep.get(3));
	    	ja.add(InfoRep.get(4));	
		 
	       jq = new JSONObject();
		   jq.put("libelleQuestion", InfoRep.get(6));
		   jq.put("difficulte", InfoRep.get(5));
		   jq.put("reponseVraie", InfoRep.get(0));
		   jq.put("reponseFausse", ja);
		   
		   jl = new JSONArray();
		   
		   questions.add(jq);
		   
		   System.out.println("Question " + intitule);
		   
	    }
    	
    	for (JSONObject test : questions) {
    		jl.add(test);
    	}
		   
	   jo = new JSONObject();
	   	if(AMR.isSelected()) {
	   		jo.put("booleanBadAnswer", "1");
		} else {
			jo.put("booleanBadAnswer", "0");
		}
  	   
  	   jo.put("questions", jl);
	   
	  
	   return(jo);
	    	
    }
    
    
    
    /**
     * Méthode qui génère le fichier JSON lorsqu'on clique sur le bouton generer
     */
    @FXML
	public void genererJ() {
    	/* Un nom est choisi pour la fiche
    	 * On crée et ecrit dans notre fichier
    	 * sinon on ouvre la popUp FenetrePopNomFicheJsonInvalide.fxml
    	 */
    	if (!(nameFiche.getText().equals(""))) {
    		valid = false;
    		
    		NomFichier = nameFiche.getText();
	    	Path path = Paths.get("./fichesJSON/"+NomFichier+".json");
	    	
	    	
			try {
				String str = creationJson().toString();
				byte[] bs = str.getBytes();
				Path writtenFilePath = Files.write(path, bs );
				System.out.println("Written content in file:\n"+ new String(Files.readAllBytes(writtenFilePath)));
				
				// on réinitialise une fois que le fichier est crée pour que l'utilisateur puisse générer une deuxième fiche 
				initSousCat = false;
				initCat = false;
				initQuestion = false;
				initQuestionsChoisies = false;
				
				stage = (Stage)buttonRetour.getScene().getWindow();
				setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreFichesRevision.fxml")));
				//on ouvre la pop up de confirmation
				creationPopUp("FenetrePopUpFicheJsonGeneree.fxml");
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    	}else {	
    		creationPopUp("FenetrePopNomFicheJsonInvalide.fxml");
    		
    	}
	}     	
        	
    
    
}
