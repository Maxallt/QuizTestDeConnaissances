package InterfacesQuizTestConnaissances;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModificationSousCategorieController implements Initializable {

    private Stage stage;
	
    private static boolean initialisation = false;
    
    public static boolean suppression = false;

	@Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    @FXML
    AnchorPane dynamicPane;
    
    @FXML
    private Button buttonRetour;
    
    @FXML
    private Button buttonValider;
    
    @FXML
    private Button buttonSupprimer;
    
    @FXML
    private TextField newName;
    
    @FXML
    private TextField newLienPhoto;
    
    @FXML
    private ComboBox<String> listeSousCat;
	
    private void setDynamicPane(AnchorPane dynamicPane){
        this.dynamicPane.getChildren().clear();
        this.dynamicPane.getChildren().add(dynamicPane);
    }
	
    @FXML
	public void retour() {

		try {
			initialisation = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreAccueil.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    @FXML
    public void modifEnBase() {
    	// Modification des données en Base de donnée avec cette requête SQL
    	String sql = "UPDATE souscategorie SET nom = '" + newName.getText() +"', lienphoto = '" + newLienPhoto.getText()+ "' WHERE nom = '" 
    	                                                                               + listeSousCat.getSelectionModel().getSelectedItem()+"'";
		
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
			st.executeUpdate(sql);
			System.out.println("Modification effectuée !");

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
		// Rafraîchissement de la page
		try {
			initialisation = false;
			stage = (Stage)buttonRetour.getScene().getWindow();
			setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationSousCategorie.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void supprEnBase() {
    	if (!(listeSousCat.getSelectionModel().isEmpty())) {
			if (suppression) {
		    	// Modification des données en Base de donnée avec cette requête SQL
		    	String sql = "DELETE FROM souscategorie WHERE nom = '" + listeSousCat.getSelectionModel().getSelectedItem() +"'";
		    	String sqlVerifDefaut ="SELECT defaut FROM souscategorie WHERE nom = '" + listeSousCat.getSelectionModel().getSelectedItem() +"'";
				
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
					ResultSet res = st.executeQuery(sqlVerifDefaut);
					res.next();
					if (res.getInt(1) == 1) {
						System.out.println("La sous-catégorie par défaut Général ne peut pas être supprimée");
					} else {
						st.executeUpdate(sql);
						System.out.println("Suppression effectuée !");
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
				buttonSupprimer.setText("Supprimer");
				// Rafraîchissement de la page
				try {
					initialisation = false;
					stage = (Stage)buttonRetour.getScene().getWindow();
					setDynamicPane(FXMLLoader.load(getClass().getResource("FenetreModificationSousCategorie.fxml")));
				} catch (IOException e) {
					e.printStackTrace();
				}
	
			} else {
				buttonSupprimer.setText("Rappuyer pour confirmer la suppression");
			}
			suppression = suppression ? false : true;
    	}
    }
    
    @FXML
    public void initialize() throws IOException {
    	if (!initialisation) {
	    	// Récupération des données en Base de donnée avec cette requête SQL
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
}
