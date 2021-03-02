package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gestionEnregistrementPartie.DAOHistoriquePartie;
import gestionQuestion.DaoQuestions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LancementJeuController implements Initializable {
	
	/** 
	 * Est utile pour le changement de fenetre, il récupère la fenetre
	 * courante pour la changer
	 */
    @SuppressWarnings("unused")
	private Stage stage;
    
    /** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
	
	@FXML 
	private Text titreQuestion;
	
	@FXML 
	private Text numeroQuestion;
	
	@FXML 
	private Button buttonRetour;
	
	@FXML
	private Button reponseA;
	
	@FXML
	private Button reponseB;
	
	@FXML 
	private Button reponseC;
	
	@FXML
	private Button reponseD;
	
	@FXML
	private Button reponseE;
	
	private int numQuestion = 0;
	
	/** Nombre de bonne réponse quand le joueur aura juste */
	static int nbBonnesReponses;
	
	/** Récupère ele nombre de bonnes réponses dans le résultat final */
	public static int getNbBonnesReponses() {
		return nbBonnesReponses;
	}

	private final int REPONSE_A = 0;
	private final int REPONSE_B = 1;
	private final int REPONSE_C = 2;
	private final int REPONSE_D = 3;
	private final int REPONSE_E = 4;
	
	private static ArrayList<String> reponsesCourantes;
	
	private static String reponseCorrecte;
	
	private static String questionActuelle;
	
	/**
	 *  Initialise les champs de texte pour les questions et les réponses
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    /* Lancement du jeu avec les questions */
		if (ChoixSousCategorieController.listeQuestions.size() == 0) {
			System.out.println("Impossible de lancerle jeu, aucune question n'existe avec ces criteres. Veuillez en créer");
		} else if (ChoixSousCategorieController.listeQuestions.size() < ChoixSousCategorieController.getNombreQuestion()) {
			System.out.println("Pas assez de questionsen base. Continuer ?");
		} else {
			nbBonnesReponses = 0;
			numeroQuestion.setText("Question n° " + (numQuestion+1));
			
			questionActuelle = ChoixSousCategorieController.listeQuestions.get(0);
			reponsesCourantes = DaoQuestions.getReponses(questionActuelle);
			reponseCorrecte = reponsesCourantes.get(0);
			
			titreQuestion.setText(questionActuelle);
			
			reponseA.setText(reponsesCourantes.get(REPONSE_A));
			reponseB.setText(reponsesCourantes.get(REPONSE_B));
			reponseC.setText(reponsesCourantes.get(REPONSE_C));
			reponseD.setText(reponsesCourantes.get(REPONSE_D));
			reponseE.setText(reponsesCourantes.get(REPONSE_E));
		}
		
		/* On enregistre la partie en cours dans la base de données */
		System.out.println("Enregistrement de la partie");
		System.out.println("Categorie --> " +  ChoixCategorieController.getIdCatActuelle()
		                 + "Sous-catégorie --> " + ChoixSousCategorieController.getIdSousCatActuelle());
		DAOHistoriquePartie.EnregistrerPartieEnCours(ChoixCategorieController.getIdCatActuelle(), 
				                                     ChoixSousCategorieController.getIdSousCatActuelle());
			
	}
	
	@FXML
	public void changementQuestion(ActionEvent e) {
	
		
		compteurBonneReponse(e);
		numQuestion++;
		if (ChoixSousCategorieController.listeQuestions.size() > numQuestion) {
			
			questionActuelle = ChoixSousCategorieController.listeQuestions.get(numQuestion);
			reponsesCourantes = DaoQuestions.getReponses(questionActuelle);
			reponseCorrecte = reponsesCourantes.get(0);
			
			numeroQuestion.setText("Question n° " + (numQuestion+1));
	
			titreQuestion.setText(questionActuelle);
			
			reponseA.setText(reponsesCourantes.get(REPONSE_A));
			reponseB.setText(reponsesCourantes.get(REPONSE_B));
			reponseC.setText(reponsesCourantes.get(REPONSE_C));
			reponseD.setText(reponsesCourantes.get(REPONSE_D));
			reponseE.setText(reponsesCourantes.get(REPONSE_E));
		
		} else { 
			System.out.println("Pas assez de questions en bases");
			resultatFinal();
		}
		
		if (numQuestion == ChoixSousCategorieController.getNombreQuestion()) {
			resultatFinal();
		}
	}
	
	/** Methode qui va incrementer le score de 1 
	 * chaque fois que l'utilisateur aura la bonne reponse
	 * Dans le cas contraire la methode enregistre la question
	 * pour l'utiliser dans le perfectionnement avancés
	 * @param e Bouton appuyer par l'utilisateur
	 */
	private void compteurBonneReponse(ActionEvent e) {
        Button btn = (Button) e.getSource();
		if (btn.getText().equals(reponsesCourantes.get(0))) {
			nbBonnesReponses++;	
		} else {
			// TODO: Enregistrer la question pour le perfectionnement 
		}
			
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
    
    /**
     * Méthode qui affiche la fenêtre du résultat final
     */
    public void resultatFinal() {
		try {
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreResultatFinal.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Méthode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface à afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
}