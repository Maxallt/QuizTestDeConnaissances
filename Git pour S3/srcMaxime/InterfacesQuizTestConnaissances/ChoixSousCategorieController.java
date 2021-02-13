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

public class ChoixSousCategorieController implements Initializable {

    private Stage stage;
	
    private static boolean initialisation = false;
    private static boolean initialisationFormat = false;
    
    public static int nombreQuestion = -1;
    
    private static int[] tabFormat = {5,10,20};
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    @FXML
    AnchorPane dynamicPane;
    
    @FXML
    private Button buttonRetour;
    
    @FXML
    private Button buttonModif;
    
    @FXML
    private Button buttonAjouter;
    
    @FXML
    private Button buttonValider;
    
    @FXML
    private ComboBox<String> listeSousCat;
    
    @FXML
    private ComboBox<String> listeFormat = new ComboBox();
	
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
    @FXML
	public void retour() {

		try {
			initialisation = false;
			initialisationFormat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    @FXML
	public void modifier() {
		try {
			initialisation = false;
			initialisationFormat = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationSousCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    @FXML
    public void initializeFormat() {
    	if (!initialisationFormat) {
	    	for (int nombre: tabFormat) {
	    		listeFormat.getItems().add(nombre + " questions");
	    	}
	    	initialisationFormat = true;
    	}
    }
    
    @FXML
    public void setFormat() {
    	if (listeFormat.getSelectionModel().getSelectedItem() != null) {
	    	if (listeFormat.getSelectionModel().getSelectedItem().equals("5 questions")) {
	    		nombreQuestion = 5;
	    	} else if (listeFormat.getSelectionModel().getSelectedItem().equals("10 questions")) {
	    		nombreQuestion = 10;
	    	} else {
	    		nombreQuestion = 20;
	    	}
    	}
    }
    
    @FXML
    public void initialize() {
    	if (!initialisation) {
	    	// Récupération des données en Base de donnée
	    	String sql = "SELECT nom FROM souscategorie";
			
			// Infos accès BD
			String login = "root";
			String passwd = "root";
			Connection cn =null;
			Statement st=null;
			String url = "jdbc:mysql://localhost/quizztestconnaissances?serverTimezone=UTC";
			
			try {
				// Etape 1 : Chargement du driver
				Class.forName("com.mysql.cj.jdbc.Driver");
				// Etape 2 : récupération de la connexion
				cn = DriverManager.getConnection(url, login, passwd);
				// Etape 3 : Création d'un statement
				st = cn.createStatement();
	
				// Etape 4 : Execution de la requête
				ResultSet res = st.executeQuery(sql);
	
				while(res.next()) {
					listeSousCat.getItems().add(res.getString("nom"));
					initialisation = true;
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
    }
    
    @FXML
    public void valider() {
    	System.out.println(nombreQuestion);
    }
}
