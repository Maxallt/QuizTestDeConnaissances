package gestionQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gestionBD.ConnexionBD;

public class DaoQuestions {
	
	private static Statement st=null;

	/**
	 * Objet Connexion
	 */
	private static Connection cn =null;
	
	/**
	 * MÃ©thode qui va nous retourner notre instance
	 * et la crÃ©er si elle n'existe pas...
	 * @throws SQLException 
	 */
	
	
	
	public static void creationTablesDefault() {
		boolean tableExiste = false;		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃ©ation d'un statement
			st = cn.createStatement();
			
			// VÃ©rification que la table des questions existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "questions", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
			if (!tableExiste) {
				// RequÃªte sql  pour crÃ©er la table catÃ©gorie de l'application en localhost
				String sql = "CREATE TABLE questions (question VARCHAR(255),"
						+ " reponseVraie VARCHAR(255),"
						+ " reponseFausse1 VARCHAR(255),"
						+ " reponseFausse2 VARCHAR(255),"
						+ " reponseFausse3 VARCHAR(255),"
						+ " reponseFausse4 VARCHAR(255),"
						+ " difficulte VARCHAR (255),"
						+ " idSousCat SMALLINT(255),"
						+ " idCat SMALLINT(255),"
						+ " CONSTRAINT fk_idSousCat FOREIGN KEY (idSousCat) REFERENCES souscategorie(id),"
						+ " CONSTRAINT fk_idCat FOREIGN KEY (idCat) REFERENCES categorie(id))";
				// Etape 4 : Execution de la requÃªte
				st.executeUpdate(sql);
				System.out.println("Cr�ation de la table questions");
				
				
				//Ajout des questions par defaults
				for (int i=0 ; i < QUESTIONS_REPONSES_DEFAUT_FACILE.length ; i++) {
					addQuestionsDefauts(QUESTIONS_REPONSES_DEFAUT_FACILE[i], "facile");
					//addQuestionsDefauts(QUESTIONS_REPONSES_DEFAUT_MOYEN[i], "moyen");
					//addQuestionsDefauts(QUESTIONS_REPONSES_DEFAUT_DIFFICILE[i], "difficile");
				}
			} else {
				
				System.out.println("Table questions d�j� existante");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createQuestions(Question question) {
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : Crï¿½ation d'un statement
			st = cn.createStatement();
				
			// Requï¿½te sql pour initialiser les catï¿½gories 
			String sql = "INSERT INTO questions (question, reponseVraie, reponseFausse1, reponseFausse2, reponseFausse3, reponseFausse4, difficulte, idSousCat, idCat)"
				  	 + "  VALUES (?,?,?,?,?,?,?,?,?)";
			
			//Valeur associée a la requete
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
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Méthode qui créé un objet de type Question 
	 * pour initialiser les questions par defaults
	 * @param questions tableaux contenant les questions  et réponses a ajouter
	 * @param difficulte niveau de difficulte des questions
	 */
	public static void addQuestionsDefauts(String[] questions, String difficulte) {
	
		ArrayList<String> listeReponse = new ArrayList<String>();
		
		listeReponse.add(questions[1]);
		listeReponse.add(questions[2]);
		listeReponse.add(questions[3]);
		listeReponse.add(questions[4]);
		listeReponse.add(questions[5]);
		
		Question questionCourante = new Question(questions[0], listeReponse ,"0", "0", difficulte);
	    
		createQuestions(questionCourante);
	}
	
	public static boolean existe(String titreQuestion) {
		boolean questExiste = false;
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			String sql = "SELECT * FROM questions WHERE question = ?";
			
			PreparedStatement verifQuestion = cn.prepareStatement(sql);
			verifQuestion.setString(1, titreQuestion);
			
            ResultSet rset = verifQuestion.executeQuery();
            if(rset.next()) {
            	questExiste = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return questExiste;
	}
	
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
	 * @param idCat liées a une categorie
	 */
	public static ArrayList<String> getQuestions(int idCat) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT question FROM questions WHERE id =" + idCat + "'";
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
    	String sql = "SELECT question FROM questions WHERE idCat =" + id;
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
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
	 * @param difficulte 
	 * @return une ArrayList avec toutes les questions cr��es dans la base+
	 * @param id liées a une sous categorie
	 */
	public static ArrayList<String> getQuestionsSousCat(int idSousCat, String difficulte) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT question FROM questions WHERE idSousCat ='" + idSousCat  + "' AND difficulte = '" + difficulte + "'";
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
	 * @param idCat liées a une categorie
	 * @param idSousCat liées a une sous categorie
	 */
	public static ArrayList<String> getQuestions(int idCat, String idSousCat) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT question FROM questions WHERE idCat =" + idCat + " AND idSousCat = '" + idSousCat  + "'";
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
	 * @param idCat liées a une categorie
	 * @param idSousCat liées a une sous categorie
	 * @param difficulte niveau de la question
	 */
	public static ArrayList<String> getQuestions(int idCat, int idSousCat, String difficulte) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT question FROM questions WHERE idCat =" + idCat + " AND idSousCat =" + idSousCat  + " AND difficulte = '" 
    	                                                               + difficulte  + "'";
    	System.out.println(sql);
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
	 * @param idCat liées a une categorie
	 * @param idSousCat liées a une sous categorie
	 * @param difficulte niveau de la question
	 */
	public static ArrayList<String> getQuestions(int idCat, int idSousCat) {
		
		ArrayList<String> listeQuestion = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT question FROM questions WHERE idCat =" + idCat + " AND idSousCat ='" + idSousCat  +  "'";
    	System.out.println(sql);
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
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT reponseVraie, reponseFausse1, reponseFausse2, reponseFausse3, reponseFausse4 FROM questions where question = ?" ;
    	
    	ResultSet res = null;

		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
			//ResultSet res = st.executeQuery(sql);
			try {
				PreparedStatement rep = cn.prepareStatement(sql);
				rep.setString(1, questions );
				res = rep.executeQuery();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
			while(res.next()) {
				//Ajout des differentes reponses a la liste
				listeReponses.add(res.getString("reponseVraie"));
				listeReponses.add(res.getString("reponseFausse1"));
				listeReponses.add(res.getString("reponseFausse2"));
				listeReponses.add(res.getString("reponseFausse3"));
				listeReponses.add(res.getString("reponseFausse4"));
			}
			
		

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
    	String sql = "SELECT reponseVraie FROM questions WHERE question = '" + titreQuestion + "'" ;
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
			while(res.next()) {
				reponse = res.getString("reponseVraie");
			}
				

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return reponse;
	}

	
	/**
	 * Cette méthode permet de modifier le titred'une préexistante dans la base de données
	 * @param questionEnBase question présentes dans la base de données
	 * @param aModifier contient la question que l'on souhaite ajouter
	 */
	public static void updateQuestions(String questionEnBase, String aModifier) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "UPDATE questions  SET question = ? WHERE question =? ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();


			//Valeur associe a la requete
			PreparedStatement update = cn.prepareStatement(sql);
			update.setString(1,  aModifier );
			update.setString(2,   questionEnBase );
			
			update.executeUpdate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	
	/**
	 * Cette méthode permet de modifier la reponse correcte d'une 
	 * question préexistante dans la base de données
	 * @param questionEnBase question présentes dans la base de données
	 * @param aModifier contient la reponse correcte que l'on souhaite modifier
	 */
	public static void updateReponseVraie(String questionEnBase, String aModifier) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "UPDATE reponseVraie  SET reponseVraie = ? WHERE question =? ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();


			//Valeur associe a la requete
			PreparedStatement update = cn.prepareStatement(sql);
			update.setString(1,  aModifier );
			update.setString(2,   questionEnBase );
			
			update.executeUpdate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	
	/**
	 * Cette méthode permet de modifier la reponse fausse 1 d'une 
	 * question préexistante dans la base de données
	 * @param questionEnBase question présentes dans la base de données
	 * @param aModifier contient la reponse fausse 1 que l'on souhaite modifier
	 */
	public static void updateReponseFausse1(String questionEnBase, String aModifier) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "UPDATE reponseFausse1  SET reponseFausse1 = ? WHERE question =? ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();


			//Valeur associe a la requete
			PreparedStatement update = cn.prepareStatement(sql);
			update.setString(1,  aModifier );
			update.setString(2,   questionEnBase );
			
			update.executeUpdate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	
	/**
	 * Cette méthode permet de modifier la reponse fausse 2 d'une 
	 * question préexistante dans la base de données
	 * @param questionEnBase question présentes dans la base de données
	 * @param aModifier contient la reponse fausse 1 que l'on souhaite modifier
	 */
	public static void updateReponseFausse2(String questionEnBase, String aModifier) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "UPDATE reponseFausse2  SET reponseFausse2 = ? WHERE question =? ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();


			//Valeur associe a la requete
			PreparedStatement update = cn.prepareStatement(sql);
			update.setString(1,  aModifier );
			update.setString(2,   questionEnBase );
			
			update.executeUpdate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	
	/**
	 * Cette méthode permet de modifier la reponse fausse 3 d'une 
	 * question préexistante dans la base de données
	 * @param questionEnBase question présentes dans la base de données
	 * @param aModifier contient la reponse fausse 1 que l'on souhaite modifier
	 */
	public static void updateReponseFausse3(String questionEnBase, String aModifier) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "UPDATE reponseFausse3  SET reponseFausse3 = ? WHERE question =? ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();


			//Valeur associe a la requete
			PreparedStatement update = cn.prepareStatement(sql);
			update.setString(1,  aModifier );
			update.setString(2,   questionEnBase );
			
			update.executeUpdate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	
	/**
	 * Cette méthode permet de modifier la reponse fausse 4 d'une 
	 * question préexistante dans la base de données
	 * @param questionEnBase question présentes dans la base de données
	 * @param aModifier contient la reponse fausse 4 que l'on souhaite modifier
	 */
	public static void updateReponseFausse4(String questionEnBase, String aModifier) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "UPDATE reponseFausse4  SET reponseFausse4 = ? WHERE question =? ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();


			//Valeur associe a la requete
			PreparedStatement update = cn.prepareStatement(sql);
			update.setString(1,  aModifier );
			update.setString(2,   questionEnBase );
			
			update.executeUpdate();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	}
	
	/**
	 * Cette méthode permet de modifier la difficulte d'une 
	 * question préexistante dans la base de données
	 * @param questionEnBase question présentes dans la base de données
	 * @param aModifier contient la difficulte que l'on souhaite modifier
	 */
	public static void updateDifficulte(String questionEnBase, String aModifier) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "UPDATE difficulte  SET difficulte = ? WHERE question =? ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();


			//Valeur associe a la requete
			PreparedStatement update = cn.prepareStatement(sql);
			update.setString(1,  aModifier );
			update.setString(2,   questionEnBase );
			
			update.executeUpdate();
				
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
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			//Valeur associe a la requete
			PreparedStatement delete = cn.prepareStatement(sql);
			delete.setString(1,  question.getTitreQuestion() );
				
			delete.executeUpdate();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/* 30 Questions par défaut niveau facile*/
	public final static String[][] QUESTIONS_REPONSES_DEFAUT_FACILE  = 
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
	            {"","","","","",""}, //21
	            {"","","","","",""}, //22
	            {"","","","","",""}, //23
	            {"","","","","",""}, //24
	            {"","","","","",""}, //25
	            {"","","","","",""}, //26
	            {"","","","","",""}, //27
	            {"","","","","",""}, //28
	            {"","","","","",""}, //29
	            {"","","","","",""}, //30
		};
	
	/* 30 Questions par défaut niveau moyen*/
	public final static String[][] QUESTIONS_REPONSES_DEFAUT_MOYEN  = 
		{   
			{"","","","","",""}, //1
            {"","","","","",""}, //2
            {"","","","","",""}, //3
            {"","","","","",""}, //4
            {"","","","","",""}, //5
            {"","","","","",""}, //6
            {"","","","","",""}, //7
            {"","","","","",""}, //8
            {"","","","","",""}, //9
            {"","","","","",""}, //10
            {"","","","","",""}, //11
            {"","","","","",""}, //12
            {"","","","","",""}, //13
            {"","","","","",""}, //14
            {"","","","","",""}, //15
            {"","","","","",""}, //16
            {"","","","","",""}, //17
            {"","","","","",""}, //18
            {"","","","","",""}, //19
            {"","","","","",""}, //20
            {"","","","","",""}, //21
            {"","","","","",""}, //22
            {"","","","","",""}, //23
            {"","","","","",""}, //24
            {"","","","","",""}, //25
            {"","","","","",""}, //26
            {"","","","","",""}, //27
            {"","","","","",""}, //28
            {"","","","","",""}, //29
            {"","","","","",""}, //30
		};
	
	/* 30 Questions par défaut niveau difficile*/
	public final static String[][] QUESTIONS_REPONSES_DEFAUT_DIFFICILE  = 
		{   
			{"","","","","",""}, //1
            {"","","","","",""}, //2
            {"","","","","",""}, //3
            {"","","","","",""}, //4
            {"","","","","",""}, //5
            {"","","","","",""}, //6
            {"","","","","",""}, //7
            {"","","","","",""}, //8
            {"","","","","",""}, //9
            {"","","","","",""}, //10
            {"","","","","",""}, //11
            {"","","","","",""}, //12
            {"","","","","",""}, //13
            {"","","","","",""}, //14
            {"","","","","",""}, //15
            {"","","","","",""}, //16
            {"","","","","",""}, //17
            {"","","","","",""}, //18
            {"","","","","",""}, //19
            {"","","","","",""}, //20
            {"","","","","",""}, //21
            {"","","","","",""}, //22
            {"","","","","",""}, //23
            {"","","","","",""}, //24
            {"","","","","",""}, //25
            {"","","","","",""}, //26
            {"","","","","",""}, //27
            {"","","","","",""}, //28
            {"","","","","",""}, //29
            {"","","","","",""}, //30
		};
	
	/**
	 * Méthode qui renvoie difficulté d'une question
	 * @param titreQuestion question a traiter
	 * @return difficulté de la question
	 */
	public static String getDifficulte(String titreQuestion) {
		
		String difficulte = "" ;
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT difficulte FROM questions WHERE question = '" + titreQuestion + "'" ;
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
			while(res.next()) {
				difficulte = res.getString("difficulte");
			}
				

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return difficulte;
	}

	
	/**
     * Cette méthode permet de modifier le titre d'une préexistante dans la base de données
     * @param questionEnBase question présentes dans la base de données
     * @param aModifier contient la question que l'on souhaite ajouter
     */
    public static void updateQuestions(String questionEnBase, 
    		String nouvelleQuestion, String nouvelleRepVrai,
    		String nouvelleReponseFausse1, String nouvelleReponseFausse2, 
    		String nouvelleReponseFausse3, String nouvelleReponseFausse4,
    		String nouvelleCat, String nouvelleSousCat, String nouvelleDifficulte) {
        // Modification des donn�es en Base de donn�e avec cette requ�te SQL
        String sql = "UPDATE questions  SET question = ?, reponseVraie = ?,"
        		+ " reponseFausse1 = ?,  reponseFausse2 = ?,  reponseFausse3 = ?,"
        		+ " reponseFausse4 = ?, idCat = ?, idSousCat = ?,"
        		+ " difficulte = ?  WHERE question = ? ";

        try {
            // RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
            cn = ConnexionBD.getInstance();
            // Etape 3 : CrÃƒÂ©ation d'un statement
            st = cn.createStatement();


            //Valeur associe a la requete
            PreparedStatement update = cn.prepareStatement(sql);
            update.setString(1,  nouvelleQuestion );
            update.setString(2,  nouvelleRepVrai );
            update.setString(3,  nouvelleReponseFausse1 );
            update.setString(4,  nouvelleReponseFausse2 );
            update.setString(5,  nouvelleReponseFausse3 );
            update.setString(6,  nouvelleReponseFausse4 );
            update.setString(7,  nouvelleCat );
            update.setString(8,  nouvelleSousCat );
            update.setString(9,  nouvelleDifficulte );
            update.setString(10, questionEnBase );

            update.executeUpdate();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
    }
    
    /**
     * Cette méthode permet de modifier le titre d'une préexistante dans la base de données
     * @param questionEnBase question présentes dans la base de données
     * @param aModifier contient la question que l'on souhaite ajouter
     */
    public static void updateQuestions(String questionEnBase, 
    		String nouvelleCat, String nouvelleSousCat,
    		String nouvelleDifficulte) {
    	
        // Modification des donn�es en Base de donn�e avec cette requ�te SQL
        String sql = "UPDATE questions  SET difficulte = ?, idSousCat = ?,"
        		+ " idCat = ? WHERE question =? ";

        try {
            // RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
            cn = ConnexionBD.getInstance();
            // Etape 3 : CrÃƒÂ©ation d'un statement
            st = cn.createStatement();

            //Valeur associe a la requete
            PreparedStatement update = cn.prepareStatement(sql);
            update.setString(1,  nouvelleDifficulte );
            update.setString(2,  nouvelleSousCat );
            update.setString(3,  nouvelleCat );
            update.setString(7,  questionEnBase );

            update.executeUpdate();

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
    }

	/**
	 * Cette méthode permet de supprimer une question dans la base de données
	 * @param question objet contenant les valeurs de la question a supprimer
	 */
	public static void delete(String question) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "DELETE FROM questions WHERE question = ? ";
		
		try {
			// RÃ©cupÃ©ration de l'Ã©lÃ©ment de connexion Ã  la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃƒÂ©ation d'un statement
			st = cn.createStatement();

			//Valeur associe a la requete
			PreparedStatement delete = cn.prepareStatement(sql);
			delete.setString(1,  question );
				
			delete.executeUpdate();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}