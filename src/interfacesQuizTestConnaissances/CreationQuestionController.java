/*
 * CreationQuestionController.java			10/12/2020
 * Controller de la fenetre de création de question
 */
package interfacesQuizTestConnaissances;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import gestionCategories.Categorie;
import gestionCategories.DAOCategorie;
import gestionCategories.DAOSousCategorie;
import gestionCategories.SousCategorie;
import gestionQuestion.DaoQuestions;
import gestionQuestion.Question;


/**
 * TODO
 * @author TODO

 */
public class CreationQuestionController implements Initializable {

	/** 
	 * Est utile pour le changement de fenetre, il récupère la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
	/**
	 * Méthode obligatoire pour tous les controller
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /**
     * Méthode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface à afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    

    private gestionQuestion.Question quest;
    
    @FXML
    private TextField txtQuestion;
    
    @FXML
    private TextField txtRepVrai;
    
    @FXML
    private TextField txtRepFaux1;
    
    @FXML
    private TextField txtRepFaux2;
    
    @FXML
    private TextField txtRepFaux3;
    
    @FXML
    private TextField txtRepFaux4;
    
    @FXML
    private Button btnValider;
    
    @FXML
    private Button btnFileChooser;
    
    private static File fichierXls;
    
    @FXML
    private Button btnValiderFich;
    
    @FXML
    private Label txtLien;
    
    /** Liste déroulante : avec toutes les catégories créées */
	@FXML
	private ComboBox<String> listeCategorie;
	
	/** Liste déroulante : avec toutes les sous catégories créées dans la 
	 * categorie selectionné
	 */
	@FXML
	private ComboBox<String> listeSousCategorie;
	
	/** Liste déroulante : avec les difficultés */
	@FXML
	private ComboBox<String> listeDifficulte;
	
	/** Tableau des diffcultés */
	private static String[] tabDifficulte = {"Facile","Moyen","Difficile","Indifférent"};
	
	private boolean initCat = false;
    private boolean initSousCat = false;
    private boolean initDifficulte = false;
    
    /**
     * Button qui permettra de revenir sur la page précédente
     * Ici le menu de gestion
     */
	@FXML
	private Button buttonFlecheBack;
    
    @FXML
    public void setCategorie() {
		if (!initCat) {
			ArrayList<Categorie> categories = DAOCategorie.findCategorie();
			for (Categorie element: categories) {
				listeCategorie.getItems().add(element.getNom());
			}
			initCat = true;
		}
	}
    
    @FXML
    public void setSousCategorie() {
		if (!initSousCat) {
			ArrayList<SousCategorie> sousCategories = 
					listeCategorie.getSelectionModel().getSelectedItem()==null?
					DAOSousCategorie.getSousCategories():
					DAOSousCategorie.getSousCategories(listeCategorie.getSelectionModel().getSelectedItem());
			
			for (SousCategorie element: sousCategories) {
				listeSousCategorie.getItems().add(element.getNom());
			}
			initSousCat = true;
		}
	}
    
    @FXML
    public void setDifficulte() {
		if(!initDifficulte) {
			for (String element: tabDifficulte) {
				listeDifficulte.getItems().add(element);
			}
			initDifficulte = true;
		}
	}
    
    /**
     * Remet à zéro la ComboBox des sous-catégories lors de la sélection
     * d'une nouvelle catégorie
     */
    @FXML
    public void resetListeSousCat() {
    	listeSousCategorie.getItems().clear();
    	initSousCat = false;
    }
    
    /**
     * Méthode qui affiche la fenêtre précédente
     * Ici le menu de gestion
     * Liée à buttonFlecheBack
     */
	@FXML
	private void actionButtonFlecheBack() {
		try {
			stage = (Stage)buttonFlecheBack.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreGestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * Si la question est valide 
	 * alors cette fenêtre avertie l'utilisateur de la réussite
	 */
	public void creationPopUpCreationQuestionValide() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUp"
            		                              + "QuestionValide.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Question crée");
        dialog.show();
	}
	
	/**
	 * Si la question est invalide 
	 * alors cette fenêtre avertie l'utilisateur l'echec
	 */
	public void creationPopUpCreationQuestionInvalide() {
		Stage dialog = new Stage();
        try {
        	dialog.initModality(Modality.APPLICATION_MODAL);
            // Localisation du fichier FXML
            final URL url = getClass().getResource("FenetrePopUp"
            		                              + "QuestionInvalide.fxml");
            // Création du loader.
            final FXMLLoader fxmlLoader = new FXMLLoader(url);
            // Chargement du FXML
            AnchorPane root = (AnchorPane) fxmlLoader.load();
            Scene dialogScene = new Scene(root, 300, 200);
            dialog.setScene(dialogScene);
        } catch (IOException e) {
        	e.printStackTrace();
        }
        dialog.setTitle("Question invalide");
        dialog.show();
	}
	
	

    @FXML
    public void valider() {
    	
    	/** ArrayList qui va contenir les intitules que l'utilisateur aura saisi */
    	ArrayList<String> intitules = new ArrayList<>();
    	
    	/* Boolean initialisé a true qui passe a false si les information
    	 *  rentrée sont fausse 
    	 */
    	boolean valid = true;
    	
    	if (txtQuestion.getText().equals("") || txtRepVrai.getText().equals("") || txtRepFaux1.getText().equals("") ) {
    		valid = false;
    		
    		//popup d'erreur
    		creationPopUpCreationQuestionInvalide();
    	}
    	
    	if (valid) {
    		intitules.add(txtRepVrai.getText());
    		intitules.add(txtRepFaux1.getText());
    		
    		if (txtRepFaux2 != null)
    			intitules.add(txtRepFaux2.getText());
    		
    		if(txtRepFaux3 != null)
    			intitules.add(txtRepFaux3.getText());
    		
    		if(txtRepFaux4 != null)
    			intitules.add(txtRepFaux4.getText());
    		
    		quest = new gestionQuestion.Question(txtQuestion.getText(), 
    				intitules,listeSousCategorie.getSelectionModel().getSelectedItem(),
    				listeCategorie.getSelectionModel().getSelectedItem(),
    				listeDifficulte.getSelectionModel().getSelectedItem() );
    		
    		DaoQuestions.createQuestions(quest);
    		creationPopUpCreationQuestionValide();
    	}
    }
    
    @FXML
    public void fileChooser() {
    	//Fenetre pop-up pour choisir un fichier
        Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		
		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(dialog);
		
		if(file!=null) {
			String regex = "[A-Za-z\\d-_]+.xls";
			if(!file.getName().matches(regex)) {
				System.out.println("Problème avec le format du fichier ce n'est pas un xls !");
			} else {
				txtLien.setText(file.getAbsolutePath());
				System.out.println(file.getName());
				try {
					ArrayList<String> element= new ArrayList<>();
					//Récupération du fichier avec un workbook
					HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file.getAbsolutePath()));
					//Récupération de la première feuille
					HSSFSheet sheet = workbook.getSheetAt(0);
					//Récupération d'un itérateur pour parcourir les lignes
					Iterator<Row> rowIterator = sheet.iterator();
					int compteurCellule = 0;
					int compteurLigne = 0;
					boolean catExiste;
					boolean sousCatExiste;
					//Parcours des lignes de la feuille
					while(rowIterator.hasNext()) {
						//Récupération de la prochaine ligne
						Row row = rowIterator.next();
						//Récupération d'un itérateur pour les cellules
						Iterator<Cell> cellIterator = row.cellIterator();
						compteurCellule = 0;
						catExiste = false;
						sousCatExiste = false;
						element.clear();
						//Parcours des cellules de la ligne
						while(cellIterator.hasNext() && compteurLigne > 0) {
							Cell cell = cellIterator.next();
							CellType cellType = cell.getCellType();
							switch (compteurCellule) {
							case 0: // Catégorie
								if(DAOCategorie.existe(cell.getStringCellValue())) {
									element.add(cell.getStringCellValue());
									catExiste = true;
								} 
								break;
							case 1: // Sous-catégorie
								if(DAOSousCategorie.existe(cell.getStringCellValue())) {
									element.add(cell.getStringCellValue());
									sousCatExiste = true;
								}
								break;
							case 2: // Niveau
								if(cell.getNumericCellValue() <= 3 && cell.getNumericCellValue() >= 1) {
									element.add(""+cell.getNumericCellValue());
								}
								break;
							case 3: // Nom TODO vérifier si utile
								break;
							case 4: // Libellé
								element.add(cell.getStringCellValue());
								break;
							case 5: // Réponse juste
								if(cellType.toString().equals("NUMERIC")) {
									element.add(""+cell.getNumericCellValue());
								} else if (cellType.toString().equals("STRING")) {
									element.add(cell.getStringCellValue());
								}
								break;
							case 6: // Réponse fausse 1
								if(cellType.toString().equals("NUMERIC")) {
									element.add(""+cell.getNumericCellValue());
								} else if (cellType.toString().equals("STRING")) {
									element.add(cell.getStringCellValue());
								}
								break;
							case 7: // Réponse fausse 2
								if(cellType.toString().equals("NUMERIC")) {
									element.add(""+cell.getNumericCellValue());
								} else if (cellType.toString().equals("STRING") && cell.getStringCellValue() != null) {
									element.add(cell.getStringCellValue());
								}
								break;
							case 8: // Réponse fausse 3
								if(cellType.toString().equals("NUMERIC")) {
									element.add(""+cell.getNumericCellValue());
								} else if (cellType.toString().equals("STRING") && cell.getStringCellValue() != null) {
									element.add(cell.getStringCellValue());
								}
								break;
							case 9: // Réponse fausse 4
								if(cellType.toString().equals("NUMERIC")) {
									element.add(""+cell.getNumericCellValue());
								} else if (cellType.toString().equals("STRING") && cell.getStringCellValue() != null) {
									element.add(cell.getStringCellValue());
								}
								break;
							}
							compteurCellule++;
						}
						if (catExiste && sousCatExiste && compteurCellule == 10) {
							for(int i = 0; i < element.size(); i++) {
								System.out.print(element.get(i)+"\t");
							}
							ArrayList<String> reponses = new ArrayList<String>();
							for  (int i = 4; i < element.size(); i++) {
								if (element.get(i)!=null) {
									reponses.add(element.get(i));
								}
							}
							
							while(reponses.size() < 5) {
								reponses.add("null");
							}
							
							System.out.println();
							
							System.out.println("reponses : " + reponses.size());
							DaoQuestions.createQuestions(new Question(element.get(3),
														 reponses,
														 DAOSousCategorie.getId(element.get(1))+"",
														 DAOCategorie.getId(element.get(0))+"",
														 element.get(2)));
						}
						compteurLigne++;
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
    }

    
    
}