/*
 * Apprentissage de JavaFX
 * MainApp.java                                                12/11/2020
 */

package quizz.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Test des m�thodes et possibilit� avec JavaFX
 * 
 * @author Nicolas.A
 * @version 1.0
 */
public class MainResultat extends Application {
    

	@Override
	public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ressources/fxml/Main.fxml"));
		
        primaryStage.setTitle("Compteur Java FX");
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Pacifico");
		scene.getStylesheets().add(getClass().getResource("/ressources/css/Main.css").toString());
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}

