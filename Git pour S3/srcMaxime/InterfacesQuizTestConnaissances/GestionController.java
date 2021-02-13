package InterfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GestionController implements Initializable {

    private Stage stage;
	
    private static boolean initialisation = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    @FXML
    AnchorPane dynamicPane;
    
    @FXML
    private Button buttonRetour;
    
    @FXML
    private Button createCategorie;
    
    @FXML
    private Button createSousCat;
    
    @FXML
    private Button modifCategorie;
    
    @FXML
    private Button modifSousCat;
	
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
    @FXML
	public void retour() {
		try {
			initialisation = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    @FXML
	public void modifSousCat() {
		try {

			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationSousCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
