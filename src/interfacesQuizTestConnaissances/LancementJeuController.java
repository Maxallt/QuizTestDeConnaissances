package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gestionQuestion.DaoQuestions;
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
	private static int nbBonnesReponses = 0;
	
	/** Récupère ele nombre de bonnes réponses dans le résultat final */
	public static int getNbBonnesReponses() {
		return nbBonnesReponses;
	}

	private final int REPONSE_A = 0;
	private final int REPONSE_B = 1;
	private final int REPONSE_C = 2;
	private final int REPONSE_D = 3;
	private final int REPONSE_E = 4;
	
	/**
	 *  Initialise les champs de texte pour les questions et les réponses
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		numeroQuestion.setText("Question n° " + (numQuestion+1));
		
		String questionActuelle = ChoixSousCategorieController.listeQuestions.get(0);
		ArrayList<String> reponsesCourantes = DaoQuestions.getReponses(questionActuelle);
		
		titreQuestion.setText(questionActuelle);
		
		reponseA.setText(reponsesCourantes.get(REPONSE_A));
		reponseB.setText(reponsesCourantes.get(REPONSE_B));
		reponseC.setText(reponsesCourantes.get(REPONSE_C));
		reponseD.setText(reponsesCourantes.get(REPONSE_D));
		reponseE.setText(reponsesCourantes.get(REPONSE_E));
	}
	
	@FXML
	public void changementQuestion() {
		
		numQuestion++;
		
		if (ChoixSousCategorieController.listeQuestions.size() > numQuestion) {
			
			String questionActuelle = ChoixSousCategorieController.listeQuestions.get(numQuestion);
			ArrayList<String> reponsesCourantes = DaoQuestions.getReponses(questionActuelle);
			
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