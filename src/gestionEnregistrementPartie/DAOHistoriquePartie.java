/*
 * DAOHistoriquePartie.java 		28/02/2021
 * Data Access Object pour l'historique des parties jouées
 */


package gestionEnregistrementPartie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import gestionBD.ConnexionBD;

/**
 * Classe qui va permettre de sauvegarder l'historique des tests
 * Effectués par l'utilisateur
 * @author Nicolas.A
 * @version 2.0
 */
public class DAOHistoriquePartie {

	private static Statement st=null;
	
		/** Objet Connexion */
	private static Connection cn =null;

	/**
	 * Methode qui va créer la table par defauts
	 * Pour l'enregistrements de l'historique des parties de l'utilisateur
	 * @return 
	 * 
	 */
	public static void TableDefautHistoriquePartie() {

		boolean tableExiste = false;
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// V�rification que la table des cat�gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "historiquepartie", new String[] {"TABLE"});
			// it�ration qui prend un � un toutes les bases inscrites en localhost
			if (resultSet.next()) {
				tableExiste = true;
			}
			
			if (!tableExiste) {
				// Requ�te sql  pour cr�er la table historiquePartie de l'application en localhost
				String sql = "CREATE TABLE historiquepartie ( numPartie INT PRIMARY KEY, dateHeure DATETIME, idCat VARCHAR(255), idSousCat VARCHAR(255), "
						                                     + "score TINYINT, nbQuestion TINYINT, difficulte VARCHAR(255) ) ";
				// Etape 4 : Execution de la requ�te
				st.executeUpdate(sql);
				System.out.println("Cr�ation de la table historiquePartie");
				
			} else {
				System.out.println("Table historiquePartie d�ja existante");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Methode qui enregistre une partie en cours
	 * Stocke dans la base:
	 *     -L'identifiant de la partie
	 *     -L'heure
	 *     -La date
	 *     -L'identifiant de catégorie
	 *     -L'identifiant de sous-catégorie
	 * @param idPartie identifiant de partie
	 * @param idCat identifiant de la categorie
	 * @param idSousCat identifiant de la sous-catégorie
	 */
	public static void EnregistrerPartieEnCours(int idCat, int idSousCat, int score, int nbQuestion, String difficulte) {

		boolean tableExiste = false;
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Vï¿½rification que la table des historiquePartie existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "historiquepartie", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
			if (tableExiste) {
				
				// Requï¿½te sql pour initialiser les sous-catï¿½gories avec gï¿½nï¿½ral
				String sql = "INSERT INTO historiquepartie (numPartie, dateHeure, idCat, idSousCat, score, nbquestion, difficulte) VALUES (?, ?, ?, ?, ?, ?, ?) ";
				
				//Valeur associe a la requete
				PreparedStatement ajoutHistorique = cn.prepareStatement(sql);
				ajoutHistorique.setLong(1, getNextPrimaryKey() );
				ajoutHistorique.setTimestamp(2, getDateHeure());
				ajoutHistorique.setLong(3, idCat);
				ajoutHistorique.setLong(4, idSousCat);
				ajoutHistorique.setLong(5, score);
				ajoutHistorique.setLong(6, nbQuestion);
				ajoutHistorique.setString(7, difficulte);
				
				
				ajoutHistorique.executeUpdate();
				
				System.out.println("Initialisation de la table avec la partie jou�'");
				
			} else {
				System.out.println("ici");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * Méthode qui renvoie l'heure et la date courante
	 * sous la forme "dd/MM/yyyy hh:mm:ss"
	 * @return time contient l'heure et la date
	 */
	private static Timestamp getDateHeure() {
	    Timestamp time = new Timestamp(System.currentTimeMillis());
        System.out.println(time);

		return time;
	}
	
	/**
	 * M�thode qui récupère la prochaine clef-primaire afin d'identifier
	 * chaque partie sans qu'il n'y est de doublon de clef-primaire
	 * @return resultat la prochaine clef-primaire
	 */
	public static int getNextPrimaryKey() {
		int resultat=0;
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// Requ�te sql pour initialiser les sous-cat�gories 
			String sql = "SELECT MAX(numPartie) FROM historiquePartie";
			ResultSet res = st.executeQuery(sql);
			res.next();
			resultat = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultat+1;
	}
	
	/**
	 * Méthode qui récupère le numéro de la partie actuelle
	 * @return numPartie
	 */
	public static int getNumPartieActuelle() {
		int numPartie=0;
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// Requ�te sql pour initialiser les sous-cat�gories 
			String sql = "SELECT MAX(numPartie) FROM historiquePartie";
			ResultSet res = st.executeQuery(sql);
			res.next();
			numPartie = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numPartie;
	}
}
	
