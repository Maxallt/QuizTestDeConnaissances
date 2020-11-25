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
 * Test des méthodes et possibilité avec JavaFX
 * 
 * @author Nicolas.A
 * @version 1.0
 */
public class MainChoixCategorie extends Application {
    

	@Override
	public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ressources/fxml/MainChoixCategorie.fxml"));
		
        primaryStage.setTitle("Compteur Java FX");
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}