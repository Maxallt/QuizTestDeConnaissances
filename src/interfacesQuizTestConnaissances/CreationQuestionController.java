/*
 * CreationQuestionController.java			10/12/2020
 * Controller de la fenetre de cr�ation de question
 */
package interfacesQuizTestConnaissances;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

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

	private static String catACreer;
	private static String sousCatACreer;
	private static String catActuelle;
	
	
	public static boolean suivant;	
	public static boolean creationOK;
	
	

	public static String getCatActuelle() {
		return catActuelle;
	}

	public static String getCatACreer() {
		return catACreer;
	}

	public static String getSousCatACreer() {
		return sousCatACreer;
	}

	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
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
    
    /** Liste d�roulante : avec toutes les cat�gories cr��es */
	@FXML
	private ComboBox<String> listeCategorie;
	
	/** Liste d�roulante : avec toutes les sous cat�gories cr��es dans la 
	 * categorie selectionn�
	 */
	@FXML
	private ComboBox<String> listeSousCategorie;
	
	/** Liste d�roulante : avec les difficult�s */
	@FXML
	private ComboBox<String> listeDifficulte;
	
	/** Tableau des diffcult�s */
	private static String[] tabDifficulte = {"Facile","Moyen","Difficile","Indiff�rent"};
	
	private boolean initCat = false;
    private boolean initSousCat = false;
    private boolean initDifficulte = false;
	
    /* **************************************** PARTIE AYMERIC *****************************************/
    


    
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
     * Remet � z�ro la ComboBox des sous-cat�gories lors de la s�lection
     * d'une nouvelle cat�gorie
     */
    @FXML
    public void resetListeSousCat() {
    	listeSousCategorie.getItems().clear();
    	initSousCat = false;
    }
    

	
	

    @FXML
    public void valider() {
    	
    	/** ArrayList qui va contenir les intitules que l'utilisateur aura saisi */
    	ArrayList<String> intitules = new ArrayList<>();
    	
    	/* Boolean initialis� a true qui passe a false si les information
    	 *  rentr�e sont fausse 
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
    		
    		// R�cup�ration de l'ID de la categorie selectionn�e dans la combobox
    		String idCat = DAOCategorie.getId(listeCategorie.getSelectionModel().getSelectedItem())+"";
    		
    		// R�cup�ration de l'ID de la sous cat�gorie s�lectionn�e dans la combobox
    		String idSousCat = DAOSousCategorie.getId(listeSousCategorie.getSelectionModel().getSelectedItem())+"";
    		
    		// R�cup�ration du libell� de la difficult�
    		String difficulteChoisis = listeDifficulte.getSelectionModel().getSelectedItem();
    		
    		// Cr�ation de la question 
    		quest = new gestionQuestion.Question(txtQuestion.getText(), intitules,idSousCat,idCat,difficulteChoisis );
    		
    		DaoQuestions.createQuestions(quest);
    		creationPopUpCreationQuestionValide();
    	}
    }

	
    /**
   	 * Si la question est valide 
   	 * alors cette fen�tre avertie l'utilisateur de la r�ussite
   	 */
   	public void creationPopUpCreationQuestionValide() {
   		Stage dialog = new Stage();
           try {
           	dialog.initModality(Modality.APPLICATION_MODAL);
               // Localisation du fichier FXML
               final URL url = getClass().getResource("FenetrePopUp"
               		                              + "QuestionValide.fxml");
               // Cr�ation du loader.
               final FXMLLoader fxmlLoader = new FXMLLoader(url);
               // Chargement du FXML
               AnchorPane root = (AnchorPane) fxmlLoader.load();
               Scene dialogScene = new Scene(root, 300, 200);
               dialog.setScene(dialogScene);
           } catch (IOException e) {
           	e.printStackTrace();
           }
           dialog.setTitle("Question cr�e");
           dialog.show();
   	}
   	
   	/**
   	 * Si la question est invalide 
   	 * alors cette fen�tre avertie l'utilisateur l'echec
   	 */
   	public void creationPopUpCreationQuestionInvalide() {
   		Stage dialog = new Stage();
           try {
           	dialog.initModality(Modality.APPLICATION_MODAL);
               // Localisation du fichier FXML
               final URL url = getClass().getResource("FenetrePopUp"
               		                              + "QuestionInvalide.fxml");
               // Cr�ation du loader.
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
   	

    
    /* **************************************** PARTIE MAXIME *****************************************/
       
	/**
	 * M�thode obligatoire pour tous les controller
	 */
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
	/** Element graphique, c'est le corps de l'interface*/
    @FXML
    AnchorPane dynamicPane;
    
    /**
     * M�thode qui permet de changer de fenetre
     * @param dynamicPane Prochain corps d'interface � afficher
     */
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
    
    @FXML
    private Button btnFileChooser;
    
    private static File fichierXls;
    
    @FXML
    private Button btnValiderFich;
    
    @FXML
    private Label txtLien;
    
    /**
     * Button qui permettra de revenir sur la page pr�c�dente
     * Ici le menu de gestion
     */
	@FXML
	private Button buttonFlecheBack;
    
    /**
     * M�thode qui affiche la fen�tre pr�c�dente
     * Ici le menu de gestion
     * Li�e � buttonFlecheBack
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
    
	
	
    @FXML
    public void fileChooser() {
    	//Fenetre pop-up pour choisir un fichier
        Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);

		final FileChooser fileChooser = new FileChooser();
		fichierXls = fileChooser.showOpenDialog(dialog);

		if(fichierXls!=null) {
			String regex = "[A-Za-z\\d-_]+.xls";
			if(!fichierXls.getName().matches(regex)) {
				System.out.println("Probl�me avec le format du fichier ce n'est pas un xls !");
			} else {
				txtLien.setText(fichierXls.getAbsolutePath());
				System.out.println(fichierXls.getName());
			}
		}
    }
    
    /**
     * M�thode qui permet de lancer une pop-up
     * @param fxml Fichier fxml de la pop-up � choisir
     */
    public void creationPopUp(String fxml) {
    	Stage dialog = new Stage();
    	try {
	        dialog.initModality(Modality.APPLICATION_MODAL);
	        // Localisation du fichier FXML
	        final URL url = getClass().getResource(fxml);
	        // Cr�ation du loader.
	        final FXMLLoader fxmlLoader = new FXMLLoader(url);
	        // Chargement du FXML
	        AnchorPane root = (AnchorPane) fxmlLoader.load();
	        Scene dialogScene = new Scene(root, 300, 200);
	        dialog.setScene(dialogScene);

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	dialog.setTitle("Importation ok !");
        dialog.show();
    }
    
    /**
     * M�thode qui permet de lancer une pop-up en cas d'erreur
     * @param fxml Fichier fxml de la pop-up � choisir
     */
    public void creationPopUpErreur(String fxml) {
    	Stage dialog = new Stage();
    	try {
	        dialog.initModality(Modality.APPLICATION_MODAL);
	        // Localisation du fichier FXML
	        final URL url = getClass().getResource(fxml);
	        // Cr�ation du loader.
	        final FXMLLoader fxmlLoader = new FXMLLoader(url);
	        // Chargement du FXML
	        AnchorPane root = (AnchorPane) fxmlLoader.load();
	        Scene dialogScene = new Scene(root, 300, 200);
	        dialog.setScene(dialogScene);

    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	dialog.setTitle("Erreur dans l'importation !");
        dialog.show();
    }
    
	@FXML
    public void validerFich() {
    	boolean importation = false;
    	try {
			ArrayList<String> element= new ArrayList<>();
			//R�cup�ration du fichier avec un workbook
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fichierXls.getAbsolutePath()));
			//R�cup�ration de la premi�re feuille
			HSSFSheet sheet = workbook.getSheetAt(0);
			//R�cup�ration d'un it�rateur pour parcourir les lignes
			Iterator<Row> rowIterator = sheet.iterator();
			int compteurCellule = 0;
			int compteurLigne = 0;
			boolean catExiste;
			boolean sousCatExiste;
			int verifFormat=0;
			
			//Parcours des lignes de la feuille
			while(rowIterator.hasNext()) {
				//R�cup�ration de la prochaine ligne
				Row row = rowIterator.next();
				//R�cup�ration d'un it�rateur pour les cellules
				Iterator<Cell> cellIterator = row.cellIterator();
				compteurCellule = 0;
				catExiste = false;
				sousCatExiste = false;

				//CellType cellTypePremiereLigne = ;
				element.clear();
				while(cellIterator.hasNext() && compteurLigne == 0  && cellIterator.next().getCellType().toString().equals("STRING")) {
					verifFormat++;
				}
				//Parcours des cellules de la ligne
				while(cellIterator.hasNext() && compteurLigne > 0) {
					suivant =false;
					creationOK = false;
					Cell cell = cellIterator.next();
					CellType cellType = cell.getCellType();
					switch (compteurCellule) {
					case 0: // Cat�gorie
						//System.out.println(cell.getStringCellValue() + "Existe ? " + DAOCategorie.existe(cell.getStringCellValue()));
						
						if(DAOCategorie.existe(cell.getStringCellValue())) {
							element.add(cell.getStringCellValue());
							catActuelle = cell.getStringCellValue();
							catExiste = true;
						} else {
							catACreer = cell.getStringCellValue();
						}
						break;
					case 1: // Sous-cat�gorie
						if(DAOSousCategorie.existe(cell.getStringCellValue())) {
							element.add(cell.getStringCellValue());
							sousCatExiste = true;
						} else {
							sousCatACreer = cell.getStringCellValue();
						}
						break;
					case 2: // Niveau
						
						element.add(""+cell.getStringCellValue());
						
						break;
					case 3: // Nom  pas utile
						break;
					case 4: // Libell�
						element.add(cell.getStringCellValue());
						break;
					case 5: // R�ponse juste
						if(cellType.toString().equals("NUMERIC")) {
							element.add(""+cell.getNumericCellValue());
						} else if (cellType.toString().equals("STRING")) {
							element.add(cell.getStringCellValue());
						}
						break;
					case 6: // R�ponse fausse 1
						if(cellType.toString().equals("NUMERIC")) {
							element.add(""+cell.getNumericCellValue());
						} else if (cellType.toString().equals("STRING")) {
							element.add(cell.getStringCellValue());
						}
						break;
					case 7: // R�ponse fausse 2
						if(cellType.toString().equals("NUMERIC")) {
							element.add(""+cell.getNumericCellValue());
						} else if (cellType.toString().equals("STRING") && cell.getStringCellValue() != null) {
							element.add(cell.getStringCellValue());
						}
						break;
					case 8: // R�ponse fausse 3
						if(cellType.toString().equals("NUMERIC")) {
							element.add(""+cell.getNumericCellValue());
						} else if (cellType.toString().equals("STRING") && cell.getStringCellValue() != null) {
							element.add(cell.getStringCellValue());
						}
						break;
					case 9: // R�ponse fausse 4
						if(cellType.toString().equals("NUMERIC")) {
							element.add(""+cell.getNumericCellValue());
						} else if (cellType.toString().equals("STRING") && cell.getStringCellValue() != null) {
							element.add(cell.getStringCellValue());
						}
						break;
					}

					
					
					/*if (!catExiste && compteurCellule == 0) {
						creationPopUpErreur("FenetrePopUpCategorieInexistante.fxml");
						try {
							
							Thread.sleep(5000);
						
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (!sousCatExiste && compteurCellule == 1) {
						creationPopUpErreur("FenetrePopUpSousCategorieInexistante.fxml");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}*/
					
						
					
					
					if (creationOK) { // TODO Mettre un boolean qui v�rifie si la cat ou sous cat a �t� cr��e apr�s les pop-up
						switch (compteurCellule) {
						case 0: // Cat�gorie
							if(DAOCategorie.existe(cell.getStringCellValue())) {
								element.add(cell.getStringCellValue());
								catExiste = true;
								catActuelle = cell.getStringCellValue();
							} 
							break;
						case 1: // Sous-cat�gorie
							if(DAOSousCategorie.existe(cell.getStringCellValue())) {
								element.add(cell.getStringCellValue());
								sousCatExiste = true;
							}
							break;
						}
					}
					compteurCellule++;
				}
				
				if (verifFormat < 9 ) {
					creationPopUpErreur("FenetrePopUpErreurImportationFich.fxml");
				}
				compteurLigne++;
				if (catExiste && sousCatExiste && compteurCellule == 10) {

					ArrayList<String> reponses = new ArrayList<String>();
					for  (int i = 4; i < element.size(); i++) {
						if (element.get(i)!=null) {
							reponses.add(element.get(i));
						}
					}
					
					while(reponses.size() < 5) {
						reponses.add("null");
					}
					
					/* Element : 
					 * 0 : Cat�gorie
					 * 1 : Sous-cat�gorie
					 * 2 : Difficult�
					 * 3 : Libell� question
					 * 4 : R�ponse Juste
					 * 5 : R�ponse Fausse 1
					 * 6 : etc... jusqu'� 4
					 */
					
					
					
					if (!DaoQuestions.existe(element.get(3))) {
						DaoQuestions.createQuestions(new Question(element.get(3),
													 reponses,
													 DAOSousCategorie.getId(element.get(1))+"",
													 DAOCategorie.getId(element.get(0))+"",
													 element.get(2)));
						/*TODO enlever �a, juste pour les tests de r�cup�ration de Question*/
						for(int i = 0; i < element.size(); i++) {
							System.out.print(element.get(i)+"\t");
							compteurLigne++;
						}
						compteurLigne++;
						importation = true;
					} else {
						System.out.println("Question d�j� en base : " + element.get(3));
					}
				}
			
				
			}
			if (importation) {
				creationPopUp("FenetrePopUpQuestionValide.fxml");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
    }
    
}