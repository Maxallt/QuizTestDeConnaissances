package InterfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	// Informations pour la connection à phpMyAdmin
	private static String login = "root";
	private static String passwd = "root";
	private static Connection cn =null;
	private static Statement st=null;
	
	@FXML
	AnchorPane dynamicPane;
	
	@Override
	public void start(Stage primaryStage) {

		try {
	        // Localisation du fichier FXML
	        final URL url = getClass().getResource("FenetreConnexion.fxml");
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
	    primaryStage.setTitle("Quizz Test de Connaissances");
	    primaryStage.show();
	}
	
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
	
	public static void creationTables() {
		boolean tableExiste = false;
		// Infos accès BD
		String url = "jdbc:mysql://localhost/quizztestconnaissances?serverTimezone=UTC";
		
		try {
			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Etape 2 : récupération de la connexion
			cn = DriverManager.getConnection(url, login, passwd);
			// Etape 3 : Création d'un statement
			st = cn.createStatement();
			
			// Vérification que la table des catégories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "categorie", new String[] {"TABLE"});
			// itération qui prend un à un toutes les bases inscrites en localhost
			while(resultSet.next()) {
				if (resultSet.next()) {
					tableExiste = true;
				}
			}
			if (!tableExiste) {
				// Requête sql  pour créer la table catégorie de l'application en localhost
				String sql = "CREATE TABLE categorie ( nom VARCHAR(255), defaut NUMERIC(10), lienphoto VARCHAR(255), id VARCHAR(255))";
				// Etape 4 : Execution de la requête
				st.executeUpdate(sql);
				System.out.println("Création de la table catégorie");
				
				// Requête sql pour initialiser les catégories avec général
				sql = "INSERT INTO categorie (nom, defaut, lienphoto, id) VALUES ('Général', '1', 'null', '0') ";
				st.executeUpdate(sql);
				System.out.println("Initialisation de la table avec la catégorie 'général'");
				
			} else {
				System.out.println("Table catégorie déjà existante");
			}
			
			// Raz tableExiste
			tableExiste = false;
			// Vérification que la table des sous-catégories existe
			resultSet = cn.getMetaData().getTables(null, null, "souscategorie", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			if (!tableExiste) {
				// Requête sql  pour créer la table souscatégorie de l'application en localhost
				String sql = "CREATE TABLE souscategorie ( nom VARCHAR(255), defaut NUMERIC(10), lienphoto VARCHAR(255), idSurCategorie VARCHAR(255), id VARCHAR(255))";
				// Etape 4 : Execution de la requête
				st.executeUpdate(sql);
				System.out.println("Création de la table sous-catégorie");
				
				// Requête sql pour initialiser les sous-catégories avec général
				sql = "INSERT INTO souscategorie (nom, defaut, lienphoto, idSurCategorie, id) VALUES ('Général', '1', 'null', '0', '0') ";
				st.executeUpdate(sql);
				System.out.println("Initialisation de la table avec la sous-catégorie 'général'");
				
			} else {
				System.out.println("Table des sous-catégories déjà existante");
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

	public static void main(String[] args) {
		creationBase();
		creationTables();
		launch(args);
	}
}
