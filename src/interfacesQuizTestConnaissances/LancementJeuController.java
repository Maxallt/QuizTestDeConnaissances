package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
	
	private int testIteration = 0;
	
	/** Nombre de bonne réponse quand le joueur aura juste */
	private static int nbBonnesReponses = 10; // pour les tests
	
	/** Récupère ele nombre de bonnes réponses dans le résultat final */
	public static int getNbBonnesReponses() {
		return nbBonnesReponses;
	}
	
	private final int TITRE_QUESTION = 0;
	private final int REPONSE_A      = 1;
	private final int REPONSE_B      = 2;
	private final int REPONSE_C      = 3;
	private final int REPONSE_D      = 4;
	private final int REPONSE_E      = 5;
	
	/* 20 Questions par défaut */
	public final String[][] QUESTIONS_REPONSES_DEFAUT  = 
		{
				{"Quel terme désigne une importation ?","import","extends","throws","private",""},
	            {"Qui est le créateur de Java ?","James Gosling","James Cameron","James Bond","James Charles",""},
	            {"Quel est le terme pour désigner un commentaire javadoc ?","/** */","/* */","<!-- -->","//",""},
	            {"Quel méthode permet de récupérer la taille d'un tableau ?","length()","taille()","getLength","width()",""},
	            {"Quelle est la différence entre J2SDK 1.5 et J2SDK 5.0?","Aucune","Ajout de nouvelles fonctionnalités",
	                    											"Patch sur les classes abstraites","NQNTMQMQMB",""},
	            {"","","","","",""}, //6
	            {"","","","","",""}, //7
	            {"","","","","",""}, //8
	            {"","","","","",""}, //9
	            {"","","","","",""}, //10
	            {"","","","","",""}, //11
	            {"","","","","",""}, //12
	            {"","","","","",""}, //13
	            {"","","","","",""}, //14
	            {"","","","","",""}, //15
	            {"","","","","",""}, //16
	            {"","","","","",""}, //17
	            {"","","","","",""}, //18
	            {"","","","","",""}, //19
	            {"","","","","",""}, //20
	            {"","","","","",""}, //21
	            {"","","","","",""}, //22
	            {"","","","","",""}, //23
	            {"","","","","",""}, //24
	            {"","","","","",""}, //25
	            {"","","","","",""}, //26
	            {"","","","","",""}, //27
	            {"","","","","",""}, //28
	            {"","","","","",""}, //29
	            {"","","","","",""}, //30
	            {"","","","","",""}, //31
		};
	
	/**
	 *  Initialise les champs de texte pour les questions et les réponses
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		numeroQuestion.setText("Question n° " + (testIteration+1));
		titreQuestion.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][TITRE_QUESTION]);
		reponseA.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_A]);
		reponseB.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_B]);
		reponseC.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_C]);
		reponseD.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_D]);
		reponseE.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_E]);
	}
	
	@FXML
	public void changementQuestion() {
		testIteration++;
		
		if (testIteration == ChoixSousCategorieController.getNombreQuestion()) {
			resultatFinal();
		}
		
		numeroQuestion.setText("Question n° " + (testIteration+1));
		titreQuestion.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][TITRE_QUESTION]);
		reponseA.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_A]);
		reponseB.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_B]);
		reponseC.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_C]);
		reponseD.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_D]);
		reponseE.setText(QUESTIONS_REPONSES_DEFAUT[testIteration][REPONSE_E]);
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
    
    /**
     * Méthode qui va mettre sous forme de tableaux les questions avec leurs réponses
     * @param listeDeQuestions désigné
     * @return
     */
    public String[] tableauxQuestions(ArrayList<String> listeDeQuestions) {
    	String[] tableauQuestions = new String[30]; //30 questions maximum
    	
    	/* Création d'un tableau à partir des ressources de la bd */
    	for(int i = 0 ; i <=listeDeQuestions.size() ; i++) {
    		tableauQuestions[i] = listeDeQuestions.get(i);
    	}
    	
    	return tableauQuestions;
    }
}