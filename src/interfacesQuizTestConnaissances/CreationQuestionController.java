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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

import gestionCategories.DAOCategorie;
import gestionCategories.DAOSousCategorie;
import gestionQuestion.DaoQuestions;
import gestionQuestion.Question;


/**
 * TODO
 * @author TODO

 */
public class CreationQuestionController implements Initializable {

	/** 
	 * Est utile pour le changement de fenetre, il r�cup�re la fenetre
	 * courante pour la changer
	 */
	@SuppressWarnings("unused")
    private Stage stage;
	
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
    private TextField txtQuestion;
    
    @FXML
    private Button btnFileChooser;
    
    private static File fichierXls;
    
    @FXML
    private Button btnValiderFich;
    
    @FXML
    private Label txtLien;
    
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
				System.out.println("Probl�me avec le format du fichier ce n'est pas un xls !");
			} else {
				txtLien.setText(file.getAbsolutePath());
				System.out.println(file.getName());
				try {
					ArrayList<String> element= new ArrayList<>();
					//R�cup�ration du fichier avec un workbook
					HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file.getAbsolutePath()));
					//R�cup�ration de la premi�re feuille
					HSSFSheet sheet = workbook.getSheetAt(0);
					//R�cup�ration d'un it�rateur pour parcourir les lignes
					Iterator<Row> rowIterator = sheet.iterator();
					int compteurCellule = 0;
					int compteurLigne = 0;
					boolean catExiste;
					boolean sousCatExiste;
					//Parcours des lignes de la feuille <<<<<<<<<<< <, 
					while(rowIterator.hasNext()) {
						//R�cup�ration de la prochaine ligne
						Row row = rowIterator.next();
						//R�cup�ration d'un it�rateur pour les cellules
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
							case 0: // Cat�gorie
								if(DAOCategorie.existe(cell.getStringCellValue())) {
									element.add(cell.getStringCellValue());
									catExiste = true;
								} 
								break;
							case 1: // Sous-cat�gorie
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
							case 3: // Nom TODO v�rifier si utile
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