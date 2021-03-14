package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResultatFinalPerfectionnementAvanceesController implements Initializable {

	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /** 
	 * Est utile pour le changement de fenetre, il récupère la fenetre
	 * courante pour la changer
	 */
    @SuppressWarnings("unused")
	private Stage stage;
    
    /** Première partie de la note qui permet 
     * de montrer à l'utilisateur son score  
     */
    @FXML
    private Text resultatCalcule;
    
    /** Seconde partie de la note qui permet 
     * d'avoir un résultat sur 20 par ex 
     */
    @FXML
    private Text format;
    
    /** Appréciation correspondant à la note/format */
    @FXML
    private Text appreciation;
    
    /** Bouton de fin de jeu pour rejouer la partie */
    @FXML
    private Button boutonRejouer;
    
    /** Bouton de fin de jeu pour aller à la fenêtre ChoixCategorie */
    @FXML
    private Button boutonRetourJeu;
    
    /** Bouton de fin de jeu pour aller à la fenêtre d'acceuil du jeu */
    @FXML
    private Button boutonRetourAccueil;
    
    private String[] APPRECIATION_GENERALE = 
    	{"Mauvais","Pas très bon","Moyen :/",
    	 "Bien","Très bien","Parfait"};
    
    private final int NB_BONNES_REPONSES 
    				  = LancementPerfectionnementAvanceesController.getNbBonnesReponses();
    private final int NB_QUESTIONS 
    				  = ChoixFormatQuestionnaireController.getNombreQuestion();
    
    /**
     * Méthode par défaut : qui permet d'initialiser les champs de texte
     * Affiche : le résultat calculé lors du jeu
     * 			 le format choisis sur l'utilisateur
     * 			 l'appréciation en fonction de la note
     * @param arg0  non utilisé
     * @param arg1  non utilisé
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("resultat perfectionnement");
    	resultatCalcule.setText(""+ LancementPerfectionnementAvanceesController.getNbBonnesReponses());
		format.setText(""+ ChoixFormatQuestionnaireController.getNombreQuestion());
		appreciation.setText(affichageAppreciation(NB_BONNES_REPONSES,NB_QUESTIONS));
		
	}
    
    /**
     * Affiche une appréciation en fonction de la note sur la note maximum
     * On partira du principe qu'au monimum : 19/20   => Parfait
     * 										  16/20   => Très bien
     * 								          13,5/20 => Bien
     * 								          10/20   => Moyen
     * 								          6/20    => Pas trèsbon
     * 								          0/20    => Mauvais
     * @param noteFinal 
     * @param noteMaximum
     * @return l'appréciation retenue
     */
    public String affichageAppreciation(int noteFinal, int noteMaximum) {
    	double resultatObtenu = (double) noteFinal/ (double) noteMaximum;
    	String apreciationObtenu;
    	
    	// Mauvais :(		note € [0 , 6[     sur 20
    	if (0 <= resultatObtenu && resultatObtenu < 0.3) {
    		apreciationObtenu = APPRECIATION_GENERALE[0];
    	// Pas très bon		note € [6 , 10[    sur 20
    	} else if (0.3 <= resultatObtenu && resultatObtenu < 0.5) {   
    		apreciationObtenu = APPRECIATION_GENERALE[1];
    	// Moyen :/			note € [10 , 13.5[ sur 20
    	} else if (0.5 <= resultatObtenu && resultatObtenu < 0.675) {
    		apreciationObtenu = APPRECIATION_GENERALE[2];
    	// Bien				note € [13.5 , 16[ sur 20
    	} else if (0.675 <= resultatObtenu && resultatObtenu < 0.8) {
    		apreciationObtenu = APPRECIATION_GENERALE[3];
    	// Très bien		note € [16 , 19[  sur 20
    	} else if (0.8 <= resultatObtenu && resultatObtenu < 0.95) {
    		apreciationObtenu = APPRECIATION_GENERALE[4];
    	// Parfait			note € [19 , 20]  sur 20
    	} else if (0.95 <= resultatObtenu && resultatObtenu <= 1) {
    		apreciationObtenu = APPRECIATION_GENERALE[5];
    	// Erreur de comptage
    	} else {
    		apreciationObtenu = "Erreur : tu triches !";
    	}
    	
    	// Affichage sur la console
    	System.out.println((resultatObtenu*noteMaximum) + " sur " + noteMaximum
				+ " : " + apreciationObtenu);
    	
    	return apreciationObtenu;
    }
    
    /**
     * Méthode qui permet de rejouer la partie
     */
    @FXML
    public void rejouer() {
    	System.out.println("Ici");
		try {
			stage = (Stage)boutonRetourJeu.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreLancementPerfectionnementAvances.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	/**
     * Méthode qui affiche la fenêtre du résultat final
     */
    @FXML
    public void retourJeu() {
		try {
			stage = (Stage)boutonRetourJeu.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass()
					.getResource("FenetreChoixFormatQuestionnaire.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Méthode qui affiche la fenêtre du résultat final
     */
    @FXML
    public void retourAccueil() {
		try {
			stage = (Stage)boutonRetourAccueil.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass()
					.getResource("FenetreAccueil.fxml")));
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
