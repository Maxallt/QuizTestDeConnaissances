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
	 * Est utile pour le changement de fenetre, il rï¿½cupï¿½re la fenetre
	 * courante pour la changer
	 */
    @SuppressWarnings("unused")
	private Stage stage;

	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
	
    /** Image de la sous-catégorie */
	@FXML
	ImageView imageSousCat;
	
	Image img;
	
	/** Titre de la catégorie lancée*/
	@FXML
	Text intituleCategorie;
	
	/** Titre de la sous-catégorie lancée */
	@FXML
	Text intituleSousCategorie;
	
	/** Bouton qui permmettra de lancer le jeu */
	@FXML
	Button boutonContinuer;
	
	/** Permet l'upload de l'image */
	InputStream stream;
	
	/** La catégorie choisis par l'utilisateur */
	String categorieChoisis = ChoixCategorieController.getSurCategorie();
	
	/** La sous-catégorie choisis par l'utilisateur */
	String sousCategorieChoisis = ChoixSousCategorieController.getSousCategorie();
	
	/** Image récupéré dans la base de donnée */
	String imageRecup = gestionCategories.DAOSousCategorie.getLienPhoto(sousCategorieChoisis);
	
	/** Image par défaut s'il n'y a pas d'image liée à la sous-catégorie */
	private final String NO_IMAGE = "./images/interrogation.png";
	
	/** Hauteur maximale de l'image */
	private final int HAUTEUR_MAX = 250;
	
	/** Largeur maximale de l'image */
	private final int LARGEUR_MAX = 350;
	
	/** Corddonées de l'image */
	private final int COORDONNEE_X = 175;
	private final int COORDONNEE_Y = 50;
	
	/**
	 * Méthode par défaut qui initialise les instances de la fenêtre
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			if (imageRecup!=null) { // Si il y a une image -> on l'upload
				// Définition de l' ImageView
				stream = new FileInputStream(imageRecup);
				img = new Image(stream);
				imageSousCat.setImage(img);
				
			} else { // S'il n'y en a pas, alors imageSousCat -> invisible
				// Définition de l' ImageView
				stream = new FileInputStream(NO_IMAGE);
				img = new Image(stream);
				imageSousCat.setImage(img);
			}
			
			// Esthétique de l' ImageView
			imageSousCat.setLayoutX(COORDONNEE_X);
			imageSousCat.setLayoutY(COORDONNEE_Y);
			imageSousCat.setFitHeight(HAUTEUR_MAX);
			imageSousCat.setFitWidth(LARGEUR_MAX);
			
		} catch (FileNotFoundException e) {
			System.out.println("Image non chargée !");
		} finally {
			// Définition des Text
			intituleCategorie.setText(ChoixSousCategorieController.categorieChoisie);
			intituleSousCategorie.setText(ChoixSousCategorieController.sousCategorieChoisie);
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