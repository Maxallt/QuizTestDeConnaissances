/*
 * DAOCategorie.java 		28/02/2021
 * Data Access Object pour la classe Question
 */

package gestionQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gestionBD.ConnexionBD;

/**
 * Data Access Object pour la classe SousCategorie
 * Classe qui va permettre de rÃ©cupÃ©rer des instances
 * Question en base de donnÃ©e
 * @author Nicolas.A
 * @version 2.0
 */

public class DaoQuestions {
	
	private static Statement st=null;
	
	/**
	 * Objet Connexion
	 */
	private static Connection cn =null;
	
	/**
	 * Mï¿½thode qui permet de rï¿½cupï¿½rer toutes les questions en base
	 * @return une ArrayList avec toutes les questions crï¿½ï¿½es dans la base
	 */
	public static ArrayList<String> getQuestions() {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// Rï¿½cupï¿½ration des donnï¿½es en Base de donnï¿½e
    	String sql = "SELECT question FROM questions";
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÆ’Ã‚Â©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sql);

			while(res.next()) {
				listeQuestion.add(res.getString("question"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeQuestion;
	}
	
	/**
	 * Mï¿½thode qui permet de rï¿½cupï¿½rer toutes les questions en base
	 * @return une ArrayList avec toutes les questions crï¿½ï¿½es dans la base+
	 * @param id liÃ©es a une categorie
	 */
	public static ArrayList<String> getQuestions(String id) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// Rï¿½cupï¿½ration des donnï¿½es en Base de donnï¿½e
    	String sql = "SELECT question FROM questions WHERE idCat =" + id;
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÆ’Ã‚Â©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sql);

			while(res.next()) {
				listeQuestion.add(res.getString("question"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeQuestion;
	}
	
	/**
	 * Mï¿½thode qui permet de rï¿½cupï¿½rer toutes les questions en base
	 * @return une ArrayList avec toutes les questions crï¿½ï¿½es dans la base+
	 * @param id liÃ©es a une sous categorie
	 */
	public static ArrayList<String> getQuestionsSousCat(int i) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// Rï¿½cupï¿½ration des donnï¿½es en Base de donnï¿½e
    	String sql = "SELECT question FROM questions WHERE idSousCat ='" + i  + "'";
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÆ’Ã‚Â©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sql);

			while(res.next()) {
				listeQuestion.add(res.getString("question"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeQuestion;
	}
	
	/**
	 * Mï¿½thode qui permet de rï¿½cupï¿½rer toutes les questions en base
	 * @return une ArrayList avec toutes les questions crï¿½ï¿½es dans la base+
	 * @param id liÃ©es a une categorie
	 * @param idSousCat liÃ©es a une sous categorie
	 */
	public static ArrayList<String> getQuestions(String id, String idSousCat) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// Rï¿½cupï¿½ration des donnï¿½es en Base de donnï¿½e
    	String sql = "SELECT questions FROM questions WHERE id =" + id + " AND idSousCat =" + idSousCat  + "'";
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÆ’Ã‚Â©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sql);

			while(res.next()) {
				listeQuestion.add(res.getString("question"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeQuestion;
	}
	

	/**
	 * Mï¿½thode qui permet de rï¿½cupï¿½rer toutes les reponses d'une question en base
	 * @return une ArrayList avec toutes les sous-catï¿½gories crï¿½ï¿½es dans la base+
	 * @param questions questions a traiter 
	 */
	public static ArrayList<String> getReponses(String questions) {
		
		ArrayList<String> listeReponses = new ArrayList<>();
		System.out.println(questions);
    	// Rï¿½cupï¿½ration des donnï¿½es en Base de donnï¿½e
    	String sql = "SELECT reponseVraie, reponseFausse1, reponseFausse2, reponseFausse3, reponseFausse4 FROM questions where question = '" + questions + "'" ;
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÆ’Ã‚Â©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sql);

			while(res.next()) {
				//Ajout des differentes reponses a la liste
			listeReponses.add(res.getString("reponseVraie"));
			listeReponses.add(res.getString("reponseFausse1"));
			listeReponses.add(res.getString("reponseFausse2"));
			listeReponses.add(res.getString("reponseFausse3"));
			listeReponses.add(res.getString("reponseFausse4"));
			}
			System.out.println(listeReponses);
		

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return listeReponses;
	}
	
	/**
	 * MÃ©thode qui renvoie la reponse correcte d'une question
	 * @param titreQuestion question a traiter
	 * @return reponse contient la reponse correcte de la question
	 */
	public static String getReponseCorrecte(String titreQuestion) {
		
		String reponse = "" ;
    	// Rï¿½cupï¿½ration des donnï¿½es en Base de donnï¿½e
    	String sql = "SELECT reponseVraie FROM questions where question = ?" ;
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÆ’Ã‚Â©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sql);

			//Valeur associe a la requete
			PreparedStatement getReponseCorrecte = cn.prepareStatement(sql);
			getReponseCorrecte.setString(1, titreQuestion );
			
			reponse = res.getString("reponseVraie");

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return reponse;
	}

	
	/**
	 * MÃƒÆ’Ã‚Â©thode qui va nous retourner notre instance
	 * et la crÃƒÆ’Ã‚Â©er si elle n'existe pas...
	 * @throws SQLException 
	 */
	
	public static void creationTablesDefault() {
		boolean tableExiste = false;		
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÆ’Ã‚Â©ation d'un statement
			st = cn.createStatement();
			
			// VÃƒÆ’Ã‚Â©rification que la table des questions existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "questions", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
			if (!tableExiste) {
				// RequÃƒÆ’Ã‚Âªte sql  pour crÃƒÆ’Ã‚Â©er la table catÃƒÆ’Ã‚Â©gorie de l'application en localhost
				String sql = "CREATE TABLE questions (question VARCHAR(255),"
						+ " reponseVraie VARCHAR(255),"
						+ " reponseFausse1 VARCHAR(255),"
						+ " reponseFausse2 VARCHAR(255),"
						+ " reponseFausse3 VARCHAR(255),"
						+ " reponseFausse4 VARCHAR(255),"
						+ " difficulte VARCHAR (255),"
						+ " idSousCat VARCHAR(255),"
						+ " idCat VARCHAR(255),"
						+ " CONSTRAINT fk_idSousCat FOREIGN KEY (idSousCat) REFERENCES souscategorie(id),"
						+ " CONSTRAINT fk_idCat FOREIGN KEY (idCat) REFERENCES categorie(id))";
				// Etape 4 : Execution de la requÃƒÆ’Ã‚Âªte
				st.executeUpdate(sql);
				System.out.println("CrÃƒÂ©ation de la table questions");
						
			} else {
				System.out.println("Table questions dÃƒÂ©jÃƒ Ã‚  existante");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createQuestions(Question question) {

		boolean tableExiste = false;
		
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ¯Ã‚Â¿Ã‚Â½ation d'un statement
			st = cn.createStatement();
			
			// VÃƒÂ¯Ã‚Â¿Ã‚Â½rification que la table des catÃƒÂ¯Ã‚Â¿Ã‚Â½gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, question.getTitreQuestion(), new String[] {"TABLE"});
			// itÃƒÂ¯Ã‚Â¿Ã‚Â½ration qui prend un ÃƒÂ¯Ã‚Â¿Ã‚Â½ un toutes les bases inscrites en localhost
			while(resultSet.next()) {
				if (resultSet.next()) {
					tableExiste = true;
				}
			}
			if (!tableExiste) {
				
				// RequÃƒÂ¯Ã‚Â¿Ã‚Â½te sql pour initialiser les catÃƒÂ¯Ã‚Â¿Ã‚Â½gories 
				String sql = "INSERT INTO questions (question, reponseVraie, reponseFausse1, reponseFausse2, reponseFausse3, reponseFausse4, difficulte, idSousCat, idCat)"
					  	 + "  VALUES (?,?,?,?,?,?,?,?,?)";
				
				//Valeur associÃƒÂ©e a la requete
				PreparedStatement createQuestion = cn.prepareStatement(sql);
				createQuestion.setString(1, question.getTitreQuestion());
				createQuestion.setString(2, question.getReponses().get(0));
				createQuestion.setString(3, question.getReponses().get(1));
				createQuestion.setString(4, question.getReponses().get(2));
				createQuestion.setString(5, question.getReponses().get(3));
				createQuestion.setString(6, question.getReponses().get(4));
				createQuestion.setString(7, question.getDifficulte());
				createQuestion.setString(8, question.getIdSousCat());
				createQuestion.setString(9, question.getIdCat());
				
				
				createQuestion.executeUpdate();
				System.out.println("Initialisation de la table avec la question :  " + question.getTitreQuestion());
				
			} else {
				System.out.println("Table " + question.getTitreQuestion() + " deja existante");
			}
			
			// Raz tableExiste
			tableExiste = false;
			// VÃƒÂ¯Ã‚Â¿Ã‚Â½rification que la table des questions existe
			resultSet = cn.getMetaData().getTables(null, null, "questions", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Cette mÃ©thode permet de modifier le titre d'une question prÃ©existante dans la base de donnÃ©es
	 * @param questionEnBase question prÃ©sentes dans la base de donnÃ©es
	 * @param aModifier contient la question que l'on souhaite ajouter
	 */
	public static void updateQuestions(String questionEnBase, String aModifier) {
		// Modification des donnï¿½es en Base de donnï¿½e avec cette requï¿½te SQL
		String sql = "UPDATE questions  SET question = ? WHERE question =? ";
		String sqlVerifDefaut ="SELECT idCat FROM questions WHERE idCat = '0' ";
		
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÆ’Ã‚Â©ation d'un statement
			st = cn.createStatement();


			//Valeur associe a la requete
			PreparedStatement update = cn.prepareStatement(sql);
			update.setString(1,  questionEnBase );
			update.setString(2,  aModifier );
			
			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			res.next();
			if (res.getInt(1) == 0) {
				System.out.println("Les questions par defaut GÃ©nÃ©ral ne peut pas Ãªtre modifiÃ©e");
			} else {
				update.executeUpdate();
				System.out.println("Modification effectuÃ©e");
			}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}

	/**
	 * Cette mÃ©thode permet de supprimer une question dans la base de donnÃ©es
	 * @param question objet contenant les valeurs de la question a supprimer
	 */
	public static void delete(Question question) {
		// Modification des donnï¿½es en Base de donnï¿½e avec cette requï¿½te SQL
		String sql = "DELETE FROM questions WHERE question = ? ";
		String sqlVerifDefaut ="SELECT idCat FROM questions WHERE idCat = '0' ";
		
		try {
			// RÃƒÂ©cupÃƒÂ©ration de l'ÃƒÂ©lÃƒÂ©ment de connexion Ãƒ  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÆ’Ã‚Â©ation d'un statement
			st = cn.createStatement();

			
			//Valeur associe a la requete
			PreparedStatement delete = cn.prepareStatement(sql);
			delete.setString(1,  question.getTitreQuestion() );
			
			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			res.next();
			if (res.getInt(1) == 0) {
				System.out.println("Les questions par defaut GÃ©nÃ©ral ne peut pas Ãªtre supprimÃ©e");
			} else {
				delete.executeUpdate();
				System.out.println("Suppression effectuÃ©e");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Méthode qui renvoie la reponse fausse 1
	 * @param titreQuestion question a traiter
	 * @return reponse contient la reponse correcte de la question
	 */
	public static String getReponseFausse1(String titreQuestion) {

		String reponse = "" ;
		// R�cup�ration des donn�es en Base de donn�e
		String sql = "SELECT reponseFausse1 FROM questions WHERE question = '" + titreQuestion + "'" ;
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);

			while(res.next()) {
				reponse = res.getString("reponseFausse1");
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reponse;
	}
	
	
	/**
	 * M�thode qui permet de r�cup�rer toutes les reponses d'une question en base
	 * @return une ArrayList avec toutes les sous-cat�gories cr��es dans la base+
	 * @param questions questions a traiter
	 */
	public static ArrayList<String> getInfoFiche(String questions) {

		ArrayList<String> listeInfo = new ArrayList<>();

		// R�cup�ration des donn�es en Base de donn�e
		String sql = "SELECT reponseVraie, reponseFausse1, reponseFausse2, reponseFausse3, reponseFausse4, difficulte, question FROM questions where question = '" + questions + "'" ;
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);

			while(res.next()) {
				//Ajout des differentes reponses a la liste
				listeInfo.add(res.getString("reponseVraie"));
				listeInfo.add(res.getString("reponseFausse1"));
				listeInfo.add(res.getString("reponseFausse2"));
				listeInfo.add(res.getString("reponseFausse3"));
				listeInfo.add(res.getString("reponseFausse4"));
				listeInfo.add(res.getString("difficulte"));
				listeInfo.add(res.getString("question"));
				
			}



		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listeInfo;
	}
	

}