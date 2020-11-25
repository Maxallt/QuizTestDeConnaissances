package quizz.test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerChoixCategorie implements Initializable {

	@FXML
	private Button buttonReglages;
	
	@FXML
	private MenuButton textButtonCategorie;
	
	@FXML
	private MenuItem categorie1, categorie2, categorie3;
	
	@FXML
    protected void setCategorie(ActionEvent e){
		
			System.out.println(categorie1.getText());

	}
	
	@FXML
	private void setTextCategorie() {
	

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 ImageView menuIcon = new ImageView(new Image(getClass().getResource("/ressources/images/reglages.png").toString()));
		    menuIcon.setFitHeight(20);
		    menuIcon.setFitWidth(20);
		    buttonReglages.setGraphic(menuIcon);
		
	}

}
