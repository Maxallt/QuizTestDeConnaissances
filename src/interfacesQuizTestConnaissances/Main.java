/*
 * Main.java		10/12/2020
 * Classe qui controlle la fenetre d'acceuil, et qui lance la première interface.
 */

package interfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gestionQuestion.DaoQuestions;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Classe qui va permettre de lancer la première interface
 * Elle va aussi permettre de créer la base de données utile à l'application
 * ainsi que les tables
 * @author Maxime Alliot
 *
 */
public class Main extends Application {
	
	// Informations pour la connection à phpMyAdmin
	private static String login = "root";
	private static String passwd = "root";
	
	/** Element utile pour recupérer la connexion à la BD */
	private static Connection cn =null;
	/** Element utile pour effectuer les requetes dans la BD */
	private static Statement st=null;
	
	/** Element graphique, c'est le corps de l'interface*/
	@FXML
	AnchorPane dynamicPane;
	
	/**
	 * Méthode qui permet de lancer une fenetre
	 * Elle lance ici la première fenetre de l'application, l'accueil
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
	        // Localisation du fichier FXML
	        final URL url = getClass().getResource("FenetreAccueil.fxml");
	        // Création du loader.
	        final FXMLLoader fxmlLoader = new FXMLLoader(url);
	        // Chargement du FXML
	        AnchorPane root = (AnchorPane) fxmlLoader.load();
	        // Création de la scène
	        final Scene scene = new Scene(root, 700, 500);
	        primaryStage.setScene(scene);
	    } catch (IOException ex) {
	        System.err.println("Erreur au chargement: " + ex);
	    }
	    primaryStage.setTitle("Quiz Test de Connaissances");
	    primaryStage.show();
	}
	
	/**
	 * Méthode qui vérifie si il y a une base de donnée qui correspond
	 * à l'application et si non elle la créer
	 */
	public static void creationBase() {
		boolean BDExiste = false;
		// Infos accès BD
		String url = "jdbc:mysql://localhost/?serverTimezone=UTC";
		try {
			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Etape 2 : récupération de la connexion
			cn = DriverManager.getConnection(url, login, passwd);
			// Etape 3 : Création d'un statement
			st = cn.createStatement();
			
			// Vérification que la base de donnée existe
			ResultSet resultSet = cn.getMetaData().getCatalogs();
			// itération qui prend un à un toutes les bases inscrites en localhost
			while (resultSet.next()) {
				// créée pour la lisibilité, permet de prendre que le nom
				String databaseName = resultSet.getString(1);
				if (databaseName.equals("quizztestconnaissances")) {
					BDExiste = true;
				}
			}
			if (!BDExiste) {
				// Requête sql qui permet de créer une base de données pour l'application en localhost
				String sql = "CREATE DATABASE QuizzTestConnaissances";
				// Etape 4 : Execution de la requête
				st.executeUpdate(sql);
				System.out.println("Création de la base de donnée");
			} else {
				System.out.println("Base de donnée de l'application déjà existante");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 5 : libérer ressources de la mémoire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Méthode qui va créer les tables par défaut
	 */
	public static void creationTables() {
		gestionCategories.DAOCategorie.creerTableDefault();
		gestionCategories.DAOSousCategorie.creerTableDefaut();
		gestionQuestion.DaoQuestions.creationTablesDefault();
		gestionEnregistrementPartie.DAOHistoriquePartie.TableDefautHistoriquePartie();
		gestionEnregistrementPartie.DAODetailPartieJoue.TableDefautPartieJouer();
	}

	public static void main(String[] args) {
		creationBase();
		creationTables();
	
		DaoQuestions.updateQuestions(DaoQuestions.getQuestions().get(0),DaoQuestions.QUESTIONS_REPONSES_DEFAUT_FACILE[0][0]);
		/* Lance la méthode start qui va lancer une interface*/
		launch(args);
	}
}
