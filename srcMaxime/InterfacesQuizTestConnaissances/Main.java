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
	
	// Informations pour la connection � phpMyAdmin
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
	        // Cr�ation du loader.
	        final FXMLLoader fxmlLoader = new FXMLLoader(url);
	        // Chargement du FXML
	        AnchorPane root = (AnchorPane) fxmlLoader.load();
	        // Cr�ation de la sc�ne
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
		
		// Infos acc�s BD
		String url = "jdbc:mysql://localhost/?serverTimezone=UTC";
		
		try {
			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Etape 2 : r�cup�ration de la connexion
			cn = DriverManager.getConnection(url, login, passwd);
			// Etape 3 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// V�rification que la base de donn�e existe
			ResultSet resultSet = cn.getMetaData().getCatalogs();
			// it�ration qui prend un � un toutes les bases inscrites en localhost
			while (resultSet.next()) {
				// cr��e pour la lisibilit�, permet de prendre que le nom
				String databaseName = resultSet.getString(1);
				if (databaseName.equals("quizztestconnaissances")) {
					BDExiste = true;
				}
			}
			
			if (!BDExiste) {
				// Requ�te sql qui permet de cr�er une base de donn�es pour l'application en localhost
				String sql = "CREATE DATABASE QuizzTestConnaissances";
				// Etape 4 : Execution de la requ�te
				st.executeUpdate(sql);
				System.out.println("Cr�ation de la base de donn�e");
			} else {
				System.out.println("Base de donn�e de l'application d�j� existante");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 5 : lib�rer ressources de la m�moire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void creationTables() {
		boolean tableExiste = false;
		// Infos acc�s BD
		String url = "jdbc:mysql://localhost/quizztestconnaissances?serverTimezone=UTC";
		
		try {
			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Etape 2 : r�cup�ration de la connexion
			cn = DriverManager.getConnection(url, login, passwd);
			// Etape 3 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// V�rification que la table des cat�gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "categorie", new String[] {"TABLE"});
			// it�ration qui prend un � un toutes les bases inscrites en localhost
			while(resultSet.next()) {
				if (resultSet.next()) {
					tableExiste = true;
				}
			}
			if (!tableExiste) {
				// Requ�te sql  pour cr�er la table cat�gorie de l'application en localhost
				String sql = "CREATE TABLE categorie ( nom VARCHAR(255), defaut NUMERIC(10), lienphoto VARCHAR(255), id VARCHAR(255))";
				// Etape 4 : Execution de la requ�te
				st.executeUpdate(sql);
				System.out.println("Cr�ation de la table cat�gorie");
				
				// Requ�te sql pour initialiser les cat�gories avec g�n�ral
				sql = "INSERT INTO categorie (nom, defaut, lienphoto, id) VALUES ('G�n�ral', '1', 'null', '0') ";
				st.executeUpdate(sql);
				System.out.println("Initialisation de la table avec la cat�gorie 'g�n�ral'");
				
			} else {
				System.out.println("Table cat�gorie d�j� existante");
			}
			
			// Raz tableExiste
			tableExiste = false;
			// V�rification que la table des sous-cat�gories existe
			resultSet = cn.getMetaData().getTables(null, null, "souscategorie", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			if (!tableExiste) {
				// Requ�te sql  pour cr�er la table souscat�gorie de l'application en localhost
				String sql = "CREATE TABLE souscategorie ( nom VARCHAR(255), defaut NUMERIC(10), lienphoto VARCHAR(255), idSurCategorie VARCHAR(255), id VARCHAR(255))";
				// Etape 4 : Execution de la requ�te
				st.executeUpdate(sql);
				System.out.println("Cr�ation de la table sous-cat�gorie");
				
				// Requ�te sql pour initialiser les sous-cat�gories avec g�n�ral
				sql = "INSERT INTO souscategorie (nom, defaut, lienphoto, idSurCategorie, id) VALUES ('G�n�ral', '1', 'null', '0', '0') ";
				st.executeUpdate(sql);
				System.out.println("Initialisation de la table avec la sous-cat�gorie 'g�n�ral'");
				
			} else {
				System.out.println("Table des sous-cat�gories d�j� existante");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 5 : lib�rer ressources de la m�moire.
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
