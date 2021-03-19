package interfacesQuizTestConnaissances;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ImageController implements Initializable {
	
	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
    @SuppressWarnings("unused")
	private Stage stage;

	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
	
    /** Image de la sous-cat�gorie */
	@FXML
	ImageView imageSousCat;
	
	Image img;
	
	/** Titre de la cat�gorie lanc�e*/
	@FXML
	Text intituleCategorie;
	
	/** Titre de la sous-cat�gorie lanc�e */
	@FXML
	Text intituleSousCategorie;
	
	/** Bouton qui permmettra de lancer le jeu */
	@FXML
	Button boutonContinuer;
	
	/** Permet l'upload de l'image */
	InputStream stream;
	
	/** La cat�gorie choisis par l'utilisateur */
	String categorieChoisis = ChoixCategorieController.getSurCategorie();
	
	/** La sous-cat�gorie choisis par l'utilisateur */
	String sousCategorieChoisis = ChoixSousCategorieController.getSousCategorie();
	
	/** Image r�cup�r� dans la base de donn�e */
	String imageRecup = gestionCategories.DAOSousCategorie.getLienPhoto(sousCategorieChoisis);
	
	/** Image par d�faut s'il n'y a pas d'image li�e � la sous-cat�gorie */
	private final String NO_IMAGE = "./images/interrogation.png";
	
	/** Hauteur maximale de l'image */
	private final int HAUTEUR_MAX = 250;
	
	/** Largeur maximale de l'image */
	private final int LARGEUR_MAX = 350;
	
	/** Corddon�es de l'image */
	private final int COORDONNEE_X = 175;
	private final int COORDONNEE_Y = 50;
	
	/**
	 * M�thode par d�faut qui initialise les instances de la fen�tre
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			if (imageRecup!=null) { // Si il y a une image -> on l'upload
				// D�finition de l' ImageView
				stream = new FileInputStream(imageRecup);
				img = new Image(stream);
				imageSousCat.setImage(img);
				
			} else { // S'il n'y en a pas, alors imageSousCat -> invisible
				// D�finition de l' ImageView
				stream = new FileInputStream(NO_IMAGE);
				img = new Image(stream);
				imageSousCat.setImage(img);
			}
			
			// Esth�tique de l' ImageView
			imageSousCat.setLayoutX(COORDONNEE_X);
			imageSousCat.setLayoutY(COORDONNEE_Y);
			imageSousCat.setFitHeight(HAUTEUR_MAX);
			imageSousCat.setFitWidth(LARGEUR_MAX);
			
		} catch (FileNotFoundException e) {
			System.out.println("Image non charg�e !");
		} finally {
			// D�finition des Text
			intituleCategorie.setText(ChoixSousCategorieController.categorieChoisie);
			intituleSousCategorie.setText(ChoixSousCategorieController.sousCategorieChoisie);
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
    
    /** 
     * Une fois que l'utilisateur use le bouton "continuer"
     */
    @FXML
    public void continuer()  {
    	lancementJeuClassique();
    }
	
	/**
     * Lancement vers FenetreLancementJeu
     */
    public void lancementJeuClassique() {
    	try {
			stage = (Stage)boutonContinuer.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreLancementJeu.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}