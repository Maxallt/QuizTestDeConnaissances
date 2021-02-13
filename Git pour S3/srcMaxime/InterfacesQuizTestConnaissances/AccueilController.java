package InterfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AccueilController implements Initializable {

    private Stage stage;
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    @FXML
    AnchorPane dynamicPane;
    
    @FXML
    private Button buttonJeuClassique;
    
    @FXML
    private Button buttonModif;
    
    @FXML
    private Button buttonPerfectionnementAvance;
    
    @FXML
    private Button reviewTests;
	
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
    @FXML
	public void jeuPrincipal() {

		try {
			stage = (Stage)buttonJeuClassique.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreSousCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    @FXML
	public void gestion() {

		try {
			stage = (Stage)buttonJeuClassique.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreGestion.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
