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
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
    @SuppressWarnings("unused")
	private Stage stage;
    
    /** Premi�re partie de la note qui permet 
     * de montrer � l'utilisateur son score  
     */
    @FXML
    private Text resultatCalcule;
    
    /** Seconde partie de la note qui permet 
     * d'avoir un r�sultat sur 20 par ex 
     */
    @FXML
    private Text format;
    
    /** Appr�ciation correspondant � la note/format */
    @FXML
    private Text appreciation;
    
    /** Bouton de fin de jeu pour rejouer la partie */
    @FXML
    private Button boutonRejouer;
    
    /** Bouton de fin de jeu pour aller � la fen�tre ChoixCategorie */
    @FXML
    private Button boutonRetourJeu;
    
    /** Bouton de fin de jeu pour aller � la fen�tre d'acceuil du jeu */
    @FXML
    private Button boutonRetourAccueil;
    
    private String[] APPRECIATION_GENERALE = 
    	{"Mauvais","Pas tr�s bon","Moyen :/",
    	 "Bien","Tr�s bien","Parfait"};
    
    private final int NB_BONNES_REPONSES 
    				  = LancementPerfectionnementAvanceesController.getNbBonnesReponses();
    private final int NB_QUESTIONS 
    				  = ChoixFormatQuestionnaireController.getNombreQuestion();
    
    /**
     * M�thode par d�faut : qui permet d'initialiser les champs de texte
     * Affiche : le r�sultat calcul� lors du jeu
     * 			 le format choisis sur l'utilisateur
     * 			 l'appr�ciation en fonction de la note
     * @param arg0  non utilis�
     * @param arg1  non utilis�
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("resultat perfectionnement");
    	resultatCalcule.setText(""+ LancementPerfectionnementAvanceesController.getNbBonnesReponses());
		format.setText(""+ ChoixFormatQuestionnaireController.getNombreQuestion());
		appreciation.setText(affichageAppreciation(NB_BONNES_REPONSES,NB_QUESTIONS));
		
	}
    
    /**
     * Affiche une appr�ciation en fonction de la note sur la note maximum
     * On partira du principe qu'au monimum : 19/20   => Parfait
     * 										  16/20   => Tr�s bien
     * 								          13,5/20 => Bien
     * 								          10/20   => Moyen
     * 								          6/20    => Pas tr�sbon
     * 								          0/20    => Mauvais
     * @param noteFinal 
     * @param noteMaximum
     * @return l'appr�ciation retenue
     */
    public String affichageAppreciation(int noteFinal, int noteMaximum) {
    	double resultatObtenu = (double) noteFinal/ (double) noteMaximum;
    	String apreciationObtenu;
    	
    	// Mauvais :(		note � [0 , 6[     sur 20
    	if (0 <= resultatObtenu && resultatObtenu < 0.3) {
    		apreciationObtenu = APPRECIATION_GENERALE[0];
    	// Pas tr�s bon		note � [6 , 10[    sur 20
    	} else if (0.3 <= resultatObtenu && resultatObtenu < 0.5) {   
    		apreciationObtenu = APPRECIATION_GENERALE[1];
    	// Moyen :/			note � [10 , 13.5[ sur 20
    	} else if (0.5 <= resultatObtenu && resultatObtenu < 0.675) {
    		apreciationObtenu = APPRECIATION_GENERALE[2];
    	// Bien				note � [13.5 , 16[ sur 20
    	} else if (0.675 <= resultatObtenu && resultatObtenu < 0.8) {
    		apreciationObtenu = APPRECIATION_GENERALE[3];
    	// Tr�s bien		note � [16 , 19[  sur 20
    	} else if (0.8 <= resultatObtenu && resultatObtenu < 0.95) {
    		apreciationObtenu = APPRECIATION_GENERALE[4];
    	// Parfait			note � [19 , 20]  sur 20
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
     * M�thode qui permet de rejouer la partie
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
     * M�thode qui affiche la fen�tre du r�sultat final
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
     * M�thode qui affiche la fen�tre du r�sultat final
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
     * M�thode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface � afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
}
