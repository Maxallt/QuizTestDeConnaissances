/*
 * DAOHistoriquePartie.java 		28/02/2021
 * Data Access Object pour l'historique des parties jouÃ©es
 */


package gestionEnregistrementPartie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import gestionBD.ConnexionBD;

/**
 * Classe qui va permettre de sauvegarder l'historique des tests
 * EffectuÃ©s par l'utilisateur
 * @author Nicolas.A
 * @version 2.0
 */
public class DAOHistoriquePartie {

	private static Statement st=null;
	
		/** Objet Connexion */
	private static Connection cn =null;

	
	/**
	 * Methode qui va crÃ©er la table par defauts
	 * Pour l'enregistrements de l'historique des parties de l'utilisateur
	 * @return 
	 * 
	 */
	public static void TableDefautHistoriquePartie() {

		boolean tableExiste = false;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Vï¿½rification que la table des catï¿½gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "historiquepartie", new String[] {"TABLE"});
			// itï¿½ration qui prend un ï¿½ un toutes les bases inscrites en localhost
			if (resultSet.next()) {
				tableExiste = true;
			}
			
			if (!tableExiste) {
				// Requï¿½te sql  pour crï¿½er la table historiquePartie de l'application en localhost
				String sql = "CREATE TABLE historiquepartie ( numPartie INT PRIMARY KEY, dateHeure DATETIME, idCat VARCHAR(255), idSousCat VARCHAR(255), "
						                                     + "score TINYINT, nbQuestion TINYINT, difficulte VARCHAR(255) ) ";
				// Etape 4 : Execution de la requï¿½te
				st.executeUpdate(sql);
				System.out.println("Cr�ation de la table historiquePartie");
				
			} else {
				System.out.println("Table historiquePartie d�j� existante");
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
	 *     -L'identifiant de catÃ©gorie
	 *     -L'identifiant de sous-catÃ©gorie
	 * @param idPartie identifiant de partie
	 * @param idCat identifiant de la categorie
	 * @param idSousCat identifiant de la sous-catÃ©gorie
	 */
	public static void EnregistrerPartieEnCours(int idCat, int idSousCat, int score, int nbQuestion, String difficulte) {

		boolean tableExiste = false;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// VÃ¯Â¿Â½rification que la table des historiquePartie existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "historiquepartie", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
			if (tableExiste) {
				
				// RequÃ¯Â¿Â½te sql pour initialiser les sous-catÃ¯Â¿Â½gories avec gÃ¯Â¿Â½nÃ¯Â¿Â½ral
				String sql = "INSERT INTO historiquepartie (numPartie, dateHeure, idCat, idSousCat, score, nbquestion, difficulte) VALUES (?, ?, ?, ?, ?, ?, ?) ";
				
				//Valeur associe a la requete
				PreparedStatement ajoutHistorique = cn.prepareStatement(sql);
				ajoutHistorique.setLong(1, getNextPrimaryKey() );
				ajoutHistorique.setTimestamp(2, getDateHeureActuelle());
				ajoutHistorique.setLong(3, idCat);
				ajoutHistorique.setLong(4, idSousCat);
				ajoutHistorique.setLong(5, score);
				ajoutHistorique.setLong(6, nbQuestion);
				ajoutHistorique.setString(7, difficulte);
				
				
				ajoutHistorique.executeUpdate();
				
				System.out.println("Initialisation de la table avec la partie joué'");
				
			} else {
				System.out.println("ici");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * MÃ©thode qui renvoie l'heure et la date courante
	 * sous la forme "dd/MM/yyyy hh:mm:ss"
	 * @return time contient l'heure et la date
	 */
	private static Timestamp getDateHeureActuelle() {
	    Timestamp time = new Timestamp(System.currentTimeMillis());
        System.out.println(time);

		return time;
	}
	
	/**
	 * Mï¿½thode qui rÃ©cupÃ¨re la prochaine clef-primaire afin d'identifier
	 * chaque partie sans qu'il n'y est de doublon de clef-primaire
	 * @return resultat la prochaine clef-primaire
	 */
	public static int getNextPrimaryKey() {
		int resultat=0;
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Requï¿½te sql pour initialiser les sous-catï¿½gories 
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
	 * MÃ©thode qui rÃ©cupÃ¨re le numÃ©ro de la partie actuelle
	 * @return numPartie
	 */
	public static int getNumPartieActuelle() {
		int numPartie=0;
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Requï¿½te sql pour initialiser les sous-catï¿½gories 
			String sql = "SELECT MAX(numPartie) FROM historiquePartie";
			ResultSet res = st.executeQuery(sql);
			res.next();
			numPartie = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("num partie: " + numPartie);
		return numPartie + 1;
	}
	
	/**
	 * Renvoie les numeros de partie enregistre dans la base de données
	 * @return
	 */
	public static ArrayList<Long> getNumPartie() {
		ArrayList<Long> listeNumPartie = new ArrayList<>();
	
		//Requete SQL
		String sql = "SELECT numPartie FROM historiquepartie ";
		
		try {
			// Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Création d'un statement
			st = cn.createStatement();
			
			// Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
			//Valeur associe a la requete
			while(res.next()) {
				listeNumPartie.add(res.getLong("numPartie"));
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeNumPartie;
	}
	
	/**
	 * Renvoie les date enregistre dans la base de données
	 * @return
	 */
	public static String getDateHeure(Long numPartie) {
		
		String dateHeure = "";
		
		//Requete SQL
		String sql = "SELECT dateHeure FROM historiquepartie WHERE numPartie = '" + numPartie + "'" ;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
		   res.next();
		   dateHeure = res.getString("dateHeure");
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dateHeure;
	}
	
	/**
	 * Renvoie les date enregistre dans la base de données
	 * @return
	 */
	public static String getCategorie(Long numPartie) {
		
		String categorie = "";
		
		//Requete SQL
		String sql = "SELECT idCat FROM historiquepartie WHERE numPartie = '" + numPartie + "'" ;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
		   res.next();
		   categorie = res.getString("idCat");
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return categorie;
	}
	
	/**
	 * Renvoie les sousCategorie enregistre dans la base de données
	 * @return
	 */
	public static String getSousCategorie(Long numPartie) {
		
		String sousCategorie = "";
		
		//Requete SQL
		String sql = "SELECT idSousCat FROM historiquepartie WHERE numPartie = '" + numPartie + "'" ;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
		   res.next();
		   sousCategorie = res.getString("idSousCat");
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sousCategorie;
	}

	/**
	 * Renvoie les difficulte enregistre dans la base de données
	 * @return
	 */
	public static String getDifficulte(Long numPartie) {

		String difficulte = "";
		
		//Requete SQL
		String sql = "SELECT difficulte FROM historiquepartie WHERE numPartie = '" + numPartie + "'" ;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
		   res.next();
		   difficulte = res.getString("difficulte");
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return difficulte;
	}

	/**
	 * Renvoie les score enregistre dans la base de données
	 * @return
	 */
	public static String getScore(Long numPartie) {

		String score = "";
		
		//Requete SQL
		String sql = "SELECT score FROM historiquepartie WHERE numPartie = '" + numPartie + "'" ;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
		   res.next();
		   score = res.getString("score");
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return score + "/" + getNbQuestions(numPartie);
	}
	
	/**
	 * Renvoie les score enregistre dans la base de données
	 * @return
	 */
	public static String getNbQuestions(Long numPartie) {

		String nbquestions = "";
		
		//Requete SQL
		String sql = "SELECT nbQuestion FROM historiquepartie WHERE numPartie = '" + numPartie + "'" ;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
		   res.next();
		   nbquestions = res.getString("nbQuestion");
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nbquestions;
	}
	
	/**
	 * Permet de supprimerune partie en base
	 */
	public static void deleteHistorique(Long numPartie) {

		//Requete SQL
		String sql = "DELETE FROM historiquepartie WHERE numPartie = ? ";
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
		    //Valeur associe a la requete
			PreparedStatement supprhistorique = cn.prepareStatement(sql);
			supprhistorique.setLong(1,numPartie);
		    supprhistorique.executeUpdate();
		    
		    DAODetailPartieJoue.supprimerPartie(numPartie);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
	
