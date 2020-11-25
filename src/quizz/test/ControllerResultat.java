package quizz.test;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ControllerResultat implements Initializable {

	
	
	private int score = 0;
	
	@FXML
	private Label scoreLabel;
	
	@FXML
	 protected void getNombreAleatoire(ActionEvent e){
		updateScoreLabel();
	
	    }

	private void updateScoreLabel() {
		Random random = new Random();
		score = random.nextInt(21);
		
		String aRetourner = "" + score;
		if(0 < score && score < 10) {
			scoreLabel.setTextFill(Color.RED);
		} else {
			scoreLabel.setTextFill(Color.GREEN);
		}
		scoreLabel.setText(aRetourner);
	}

	@Override
	public void initialize(URL location, ResourceBundle ressource) {
		scoreLabel.setText("NA");
	   
		System.out.println("Execution normale");
		
	}

}
