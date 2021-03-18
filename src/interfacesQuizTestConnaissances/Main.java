/*
 * Main.java		10/12/2020
 * Classe qui controlle la fenetre d'acceuil, et qui lance la premi�re interface.
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
 * Classe qui va permettre de lancer la premi�re interface
 * Elle va aussi permettre de cr�er la base de donn�es utile � l'application
 * ainsi que les tables
 * @author Maxime Alliot
 *
 */
public class Main extends Application {
	
	// Informations pour la connection � phpMyAdmin
	private static String login = "root";
	private static String passwd = "root";
	
	/** Element utile pour recup�rer la connexion � la BD */
	private static Connection cn =null;
	/** Element utile pour effectuer les requetes dans la BD */
	private static Statement st=null;
	
	/** Element graphique, c'est le corps de l'interface*/
	@FXML
	AnchorPane dynamicPane;
	
	/**
	 * M�thode qui permet de lancer une fenetre
	 * Elle lance ici la premi�re fenetre de l'application, l'accueil
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
	        // Localisation du fichier FXML
	        final URL url = getClass().getResource("FenetreAccueil.fxml");
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
	    primaryStage.setTitle("Quiz Test de Connaissances");
	    primaryStage.show();
	}
	
	/**
	 * M�thode qui v�rifie si il y a une base de donn�e qui correspond
	 * � l'application et si non elle la cr�er
	 */
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
	
	/**
	 * M�thode qui va cr�er les tables par d�faut
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
		/* Lance la m�thode start qui va lancer une interface*/
		launch(args);
	}
}
