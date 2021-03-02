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
 * Classe qui va permettre de récupérer des instances
 * Question en base de donnée
 * @author Nicolas.A
 * @version 2.0
 */

public class DaoQuestions {
	
	private static Statement st=null;
	

	
	/* 20 Questions par d�faut */
	public final static String[][] QUESTIONS_REPONSES_DEFAUT  = 
		{
				{"Quel terme désigne une importation ?","import","extends","throws","private",""},
	            {"Qui est le créateur de Java ?","James Gosling","James Cameron","James Bond","James Charles",""},
	            {"Quel est le terme pour désigner un commentaire javadoc ?","/** */","/* */","<!-- -->","//",""},
	            {"Quel méthode permet de récupérer la taille d un tableau en java ?","length()","taille()","getLength","width()",""},
	            {"Quelle est la différence entre J2SDK 1.5 et J2SDK 5.0?","Aucune","Ajout de nouvelles fonctionnalit�s",
	                    											"Patch sur les classes abstraites","NQNTMQMQMB",""},
	            {"Lequel de ces nom de variables est correcte en Java ?","aCorriger","acorriger","@corriger ","ACORRIGER",""}, //6
	            {"Quelle est la taille, en nombre d octets, du type byte ?","1 octet","2 octet","4 octet","8 octet",""}, //7
	            {"Quelle est la taille, en nombre d octets, du type double","8 octet","16 octet","2 octet","1 octet",""}, //8
	            {"A quoi sert le mot clé const ?","A définir une constante.","C est un mot clé réservé mais actuellement non utilisé en Java.",
	            	                               "Ce n est pas un mot clé du langage.","A définir un constituant.",""}, //9
	            {"Comment comparer deux chaines de caractères en java ?","s1.equals( s2 )","s1 == s2","s1.equal( s2 )","On ne peut pas en Java.",""}, //10
	            {"Le langage que l ordinateur comprend est","le binaire" ,"des animaux","Java","Visual Basic",""}, //11
	            {"Microsoft Word est ","Programme","Compilateur","Systeme d exploitation","Langage de programmation",""}, //12
	            {"Quel langage est très utilisé pour les pages web ?","Javascript","Cobol","C++","Vala",""}, //13
	            {"Qu est-ce que que le langage binaire ?","C est le langage de la machine","C est un langage interprété","C est un langage orientés objets","C est un langage de haut niveau",""}, //14
	            {"De quoi se sert-on pour programmer en C ou C++ ?","Un compilateur","Une console de commande","Un décompilateur","Un éditeur de texte",""}, //15
	            {"Quel est le framework le du C# ?"," . NET",". EXE",". BAT",". MTS",""}, //16
	            {"Qu est-ce qu un IDE ?"," Un programme qui fait en même temps : comilateur, éditeur de texte et débugger", "  Un programme pour injecter du JS dans le langage C",
	            	                                      " Un éditeur XHTML","Un jeu vidéo",""}, //17
	            {"Le raccourci : touche Windows+D sert à"," Réduire toutes le fenêtres"," Lancer, exécuter","Fermer la session","Redémarrer l'ordinateur",""}, //18
	            {"Que représente le sigle VB en Programmation ?","Visual Basic","Visual Boy","Virtuel Bord","Virtual Basic",""}, //19
	            {"Quelle est l extension d une application exécutable ?",".exe",".tab",".app",".txt",""}, //20
		};
	

	

	
	
	/**
	 * Objet Connexion
	 */
	private static Connection cn =null;
	
	/**
	 * M�thode qui permet de r�cup�rer toutes les questions en base
	 * @return une ArrayList avec toutes les questions cr��es dans la base
	 */
	public static ArrayList<String> getQuestions() {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT question FROM questions";
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
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
	 * M�thode qui permet de r�cup�rer toutes les questions en base
	 * @return une ArrayList avec toutes les questions cr��es dans la base+
	 * @param id liées a une categorie
	 */
	public static ArrayList<String> getQuestions(String id) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT question FROM questions WHERE id =" + id;
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
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
	 * M�thode qui permet de r�cup�rer toutes les questions en base
	 * @return une ArrayList avec toutes les questions cr��es dans la base+
	 * @param id liées a une sous categorie
	 */
	public static ArrayList<String> getQuestionsSousCat(int i) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT question FROM questions WHERE idSousCat ='" + i  + "'";
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
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
	 * M�thode qui permet de r�cup�rer toutes les questions en base
	 * @return une ArrayList avec toutes les questions cr��es dans la base+
	 * @param id liées a une categorie
	 * @param idSousCat liées a une sous categorie
	 */
	public static ArrayList<String> getQuestions(String id, String idSousCat) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT questions FROM questions WHERE id =" + id + " AND idSousCat =" + idSousCat  + "'";
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
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
	 * M�thode qui permet de r�cup�rer toutes les reponses d'une question en base
	 * @return une ArrayList avec toutes les sous-cat�gories cr��es dans la base+
	 * @param questions questions a traiter 
	 */
	public static ArrayList<String> getReponses(String questions) {
		
		ArrayList<String> listeReponses = new ArrayList<>();
		System.out.println(questions);
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT reponseVraie, reponseFausse1, reponseFausse2, reponseFausse3, reponseFausse4 FROM questions where question = '" + questions + "'" ;
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
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
	 * Méthode qui renvoie la reponse correcte d'une question
	 * @param titreQuestion question a traiter
	 * @return reponse contient la reponse correcte de la question
	 */
	public static String getReponseCorrecte(String titreQuestion) {
		
		String reponse = "" ;
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT reponseVraie FROM questions where question = ?" ;
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
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
	 * MÃƒÂ©thode qui va nous retourner notre instance
	 * et la crÃƒÂ©er si elle n'existe pas...
	 * @throws SQLException 
	 */
	
	public static void creationTablesDefault() {
		boolean tableExiste = false;		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();
			
			// VÃƒÂ©rification que la table des questions existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "questions", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
			if (!tableExiste) {
				// RequÃƒÂªte sql  pour crÃƒÂ©er la table catÃƒÂ©gorie de l'application en localhost
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
				// Etape 4 : Execution de la requÃƒÂªte
				st.executeUpdate(sql);
				System.out.println("CrÃ©ation de la table questions");
				
				//Ajout des questions par defaults
				for (int i=0 ; i < QUESTIONS_REPONSES_DEFAUT.length ; i++) {
					addQuestionsDefauts(QUESTIONS_REPONSES_DEFAUT[i]);
				}
				
				
			} else {
				System.out.println("Table questions dÃ©jÃ Â  existante");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui créé un objet de type Question 
	 * pour initialiser les questions par defaults
	 */
	public static void addQuestionsDefauts(String[] questions) {

		ArrayList<String> listeReponse = new ArrayList<String>();
		
		listeReponse.add(questions[1]);
		listeReponse.add(questions[2]);
		listeReponse.add(questions[3]);
		listeReponse.add(questions[4]);
		listeReponse.add(questions[5]);
		
		Question questionCourante = new Question(questions[0], listeReponse ,"0", "0", "facile");
        
		createQuestions(questionCourante);
	}
	
	public static void createQuestions(Question question) {

		boolean tableExiste = false;
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// VÃ¯Â¿Â½rification que la table des catÃ¯Â¿Â½gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, question.getTitreQuestion(), new String[] {"TABLE"});
			// itÃ¯Â¿Â½ration qui prend un Ã¯Â¿Â½ un toutes les bases inscrites en localhost
			while(resultSet.next()) {
				if (resultSet.next()) {
					tableExiste = true;
				}
			}
			if (!tableExiste) {
				
				// RequÃ¯Â¿Â½te sql pour initialiser les catÃ¯Â¿Â½gories 
				String sql = "INSERT INTO questions (question, reponseVraie, reponseFausse1, reponseFausse2, reponseFausse3, reponseFausse4, difficulte, idSousCat, idCat)"
					  	 + "  VALUES (?,?,?,?,?,?,?,?,?)";
				
				//Valeur associÃ©e a la requete
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
			// VÃ¯Â¿Â½rification que la table des questions existe
			resultSet = cn.getMetaData().getTables(null, null, "questions", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Cette méthode permet de modifier le titred'une préexistante dans la base de données
	 * @param questionEnBase question présentes dans la base de données
	 * @param aModifier contient la question que l'on souhaite ajouter
	 */
	public static void updateQuestions(String questionEnBase, String aModifier) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "UPDATE questions  SET question = ? WHERE question =? ";
		String sqlVerifDefaut ="SELECT idCat FROM questions WHERE idCat = '0' ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();


			//Valeur associe a la requete
			PreparedStatement update = cn.prepareStatement(sql);
			update.setString(1,  questionEnBase );
			update.setString(2,  aModifier );
			
			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			res.next();
			if (res.getInt(1) == 0) {
				System.out.println("Les questions par defaut Général ne peut pas être modifiée");
			} else {
				update.executeUpdate();
				System.out.println("Modification effectuée");
			}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}

	/**
	 * Cette méthode permet de supprimer une question dans la base de données
	 * @param question objet contenant les valeurs de la question a supprimer
	 */
	public static void delete(Question question) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "DELETE FROM questions WHERE question = ? ";
		String sqlVerifDefaut ="SELECT idCat FROM questions WHERE idCat = '0' ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			
			//Valeur associe a la requete
			PreparedStatement delete = cn.prepareStatement(sql);
			delete.setString(1,  question.getTitreQuestion() );
			
			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			res.next();
			if (res.getInt(1) == 0) {
				System.out.println("Les questions par defaut Général ne peut pas être supprimée");
			} else {
				delete.executeUpdate();
				System.out.println("Suppression effectuée");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}