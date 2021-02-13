package gestionQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
						+ " idSousCat VARCHAR(255),"
						+ " idCat VARCHAR(255),"
						+ " CONSTRAINT fk_idSousCat FOREIGN KEY (idSousCat) REFERENCES souscategorie(id),"
						+ " CONSTRAINT fk_idCat FOREIGN KEY (idCat) REFERENCES categorie(id))";
				// Etape 4 : Execution de la requÃªte
				st.executeUpdate(sql);
				System.out.println("Création de la table questions");
				
			} else {
				System.out.println("Table questions déjà  existante");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void createQuestions(Question question) {

		boolean tableExiste = false;
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Vï¿½rification que la table des catï¿½gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, question.getTitreQuestion(), new String[] {"TABLE"});
			// itï¿½ration qui prend un ï¿½ un toutes les bases inscrites en localhost
			while(resultSet.next()) {
				if (resultSet.next()) {
					tableExiste = true;
				}
			}
			if (!tableExiste) {
				
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
				
			} else {
				System.out.println("Table " + question.getTitreQuestion() + " deja existante");
			}
			
			// Raz tableExiste
			tableExiste = false;
			// Vï¿½rification que la table des sous-catï¿½gories existe
			resultSet = cn.getMetaData().getTables(null, null, "categorie", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}