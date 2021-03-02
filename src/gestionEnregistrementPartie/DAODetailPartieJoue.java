package gestionEnregistrementPartie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import gestionBD.ConnexionBD;

public class DAODetailPartieJoue {

	private static Statement st=null;
	
		/** Objet Connexion */
	private static Connection cn =null;


	/**
	 * Methode qui va créer la table par defauts
	 * Pour l'enregistrements de l'historique des parties de l'utilisateur
	 * @return 
	 * 
	 */
	public static void TableDefautPartieJouer() {

		boolean tableExiste = false;
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// V�rification que la table des cat�gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "detailpartie", new String[] {"TABLE"});
			// it�ration qui prend un � un toutes les bases inscrites en localhost
			if (resultSet.next()) {
				tableExiste = true;
			}

			if (!tableExiste) {
				// Requ�te sql  pour cr�er la table DetailPartie de l'application en localhost
				String sql = "CREATE TABLE DetailPartie ( idPartie NUMERIC, question NUMERIC(10), reponseDonnee VARCHAR(255), reponseCorrecte VARCHAR(255) ";
				// Etape 4 : Execution de la requ�te
				st.executeUpdate(sql);
				System.out.println("Création de la table DetailPartie");
				
			} else {
				System.out.println("Table détailPartie déja existante");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @return 
	 */
	public static void EnregistrerPartieEnCours() {
		

		
	}
	
}