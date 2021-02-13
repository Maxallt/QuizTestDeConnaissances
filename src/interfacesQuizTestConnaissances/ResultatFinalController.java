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

public class ResultatFinalController implements Initializable {

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
    
    /** Bouton de fin de jeu pour aller � la fen�tre ModificationQuestions */
    @FXML
    private Button boutonModififierQuestions;
    
    /** Bouton de fin de jeu pour aller � la fen�tre ChoixCategorie */
    @FXML
    private Button boutonRetourJeu;
    
    /** Bouton de fin de jeu pour aller � la fen�tre d'acceuil du jeu */
    @FXML
    private Button boutonRetourAccueil;
    
    private String[] APPRECIATION_GENERALE = 
    	{"Parfait","Tr�s bien","Bien, continue comme �a","Moyen :/",
    	"Pas tr�s bon","Mauvais :("};
    
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
    	
    	resultatCalcule.setText(""+ LancementJeuController.getNbBonnesReponses());
		format.setText("" + ChoixSousCategorieController.getNombreQuestion());
		appreciation.setText(affichageAppreciation
				(LancementJeuController.getNbBonnesReponses(),
				ChoixSousCategorieController.getNombreQuestion()));
		
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
    	double resultatObtenu = noteFinal/noteMaximum;
    	String apreciationObtenu = "";
    	
    	if (resultatObtenu < 6/20) {
    		apreciationObtenu = APPRECIATION_GENERALE[5];
    	} else if (6/20 <= resultatObtenu && resultatObtenu < 10/20) {
    		apreciationObtenu = APPRECIATION_GENERALE[4];
    	} else if (10/20 <= resultatObtenu && resultatObtenu < 13.5/20) {
    		apreciationObtenu = APPRECIATION_GENERALE[3];
    	} else if (13.5/20 <= resultatObtenu && resultatObtenu < 16/20) {
    		apreciationObtenu = APPRECIATION_GENERALE[2];
    	} else if (16/20 <= resultatObtenu && resultatObtenu < 19/20) {
    		apreciationObtenu = APPRECIATION_GENERALE[1];
    	} else if (19/20 <= resultatObtenu && resultatObtenu < 20/20) {
    		apreciationObtenu = APPRECIATION_GENERALE[0];
    	} else { //Au cas o� un probl�me surviendrai
    		apreciationObtenu = "Erreur lors du comptage de points";
    	}
    	
    	return apreciationObtenu;
    }
    
	/**
     * M�thode qui affiche la fen�tre du r�sultat final
     */
    @FXML
    public void retourJeu() {
		try {
			stage = (Stage)boutonRetourJeu.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass()
					.getResource("FenetreChoixCategorie.fxml")));
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
