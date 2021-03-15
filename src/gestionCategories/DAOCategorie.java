/*
 * DAOSousCategorie.java 		28/02/2021
 * Data Access Object pour la classe Categorie
 */

package gestionCategories;

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
 * Categorie en base de donnÃ©e
 * @author Nicolas.A
 * @version 2.0
 */

public class DAOCategorie {

	private static Statement st=null;
	
		/** Objet Connexion */
	private static Connection cn =null;

	/**
		 * Mï¿½thode qui crï¿½e dans la base de donnï¿½e les catï¿½gories par dï¿½faut si 
		 * elles n'existent pas encore
		 */
		public static void creerTableDefault() {
			boolean tableExiste = false;
			
			try {
				// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
				cn = ConnexionBD.getInstance();
				// Etape 2 : Crï¿½ation d'un statement
				st = cn.createStatement();
				
				// Vï¿½rification que la table des catï¿½gories existe
				ResultSet resultSet = cn.getMetaData().getTables(null, null, "categorie", new String[] {"TABLE"});
				// itï¿½ration qui prend un ï¿½ un toutes les bases inscrites en localhost
				if (resultSet.next()) {
					tableExiste = true;
				}
				
				if (!tableExiste) {
					// Requï¿½te sql  pour crï¿½er la table catï¿½gorie de l'application en localhost
					String sql = "CREATE TABLE categorie ( nom VARCHAR(255), defaut NUMERIC(10), lienphoto VARCHAR(255), id SMALLINT PRIMARY KEY)";
					// Etape 4 : Execution de la requï¿½te
					st.executeUpdate(sql);
					System.out.println("Crï¿½ation de la table categorie");
					
					// Requï¿½te sql pour initialiser les catï¿½gories avec gï¿½nï¿½ral
					sql = "INSERT INTO categorie (nom, defaut, lienphoto, id) VALUES ('General', '1', 'null', '0') ";
					st.executeUpdate(sql);
					System.out.println("Initialisation de la table avec la catÃ©gorie 'gÃ©nÃ©ral'");
					
				} else {
					System.out.println("Table catï¿½gorie dï¿½jï¿½ existante");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	/**
	 * Mï¿½thode qui crï¿½e une catï¿½gorie dans la base de donnï¿½e avec le nom 
	 * passï¿½ en paramï¿½tre 
	 * @param obj catï¿½gorie rentrï¿½e
	 */
	public static void create(Categorie obj) {
	
		boolean tableExiste = false;
	
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Vï¿½rification que la table des catï¿½gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, obj.getNom(), new String[] {"TABLE"});
			// itï¿½ration qui prend un ï¿½ un toutes les bases inscrites en localhost
			while(resultSet.next()) {
				if (resultSet.next()) {
					tableExiste = true;
				}
			}
			if (!tableExiste) {
				
				// Requï¿½te sql pour initialiser les catï¿½gories 
				String sql = "INSERT INTO categorie (nom, defaut, lienphoto, id)"
		                 + " VALUES (?, ?, ?, ?) ";
				//Valeur associe a la requete
				PreparedStatement initCategorie = cn.prepareStatement(sql);
				initCategorie.setString(1,  obj.getNom());
				initCategorie.setString(2, "0");
				initCategorie.setString(3, "null");
				initCategorie.setLong(4, getNextPrimaryKey());
				
				initCategorie.executeUpdate();
				
				System.out.println("Crï¿½ation de la categorie " + obj.getNom());
				
			} else {
				System.out.println("Table " + obj.getNom() + " deja existante");
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

	/**
	 * Mï¿½thode qui supprime dans la base de donnï¿½e la catï¿½gorie dont le nom 
	 * est passï¿½ en parametre
	 * @param nomCategorieASupprimer nom de la catï¿½gorie ï¿½ supprimer
	 */
	public static void delete(String nomCategorieASupprimer ) {
		// Modification des donnï¿½es en Base de donnï¿½e avec cette requï¿½te SQL
		String sql = "DELETE FROM categorie WHERE nom = ?";
		String sqlVerifDefaut ="SELECT defaut FROM categorie WHERE nom = '"
				+ ""+nomCategorieASupprimer +"'";
	
			
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requï¿½te
			//Valeur associï¿½e a la requete
			PreparedStatement deleteCategorie = cn.prepareStatement(sql);
			deleteCategorie.setString(1,nomCategorieASupprimer);
			
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			res.next();
			if (res.getInt(1) == 1) {
				System.out.println("La catï¿½gorie par defaut Gï¿½nï¿½ral ne peut pas ï¿½tre supprimï¿½e");
			} else {
				ArrayList<SousCategorie> listeSousCat = DAOSousCategorie.getSousCategories();
				for (int i = 0; i < listeSousCat.size(); i++) {
					System.out.println(listeSousCat.get(i).toString());
					DAOSousCategorie.supprSousCategorie(listeSousCat.get(i).getNom());
				}
				deleteCategorie.executeUpdate();
				System.out.println("Suppression effectuï¿½e");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Mï¿½thode qui supprime dans la base de donnï¿½e la catï¿½gorie passï¿½ en 
	 * paramï¿½tre 
	 * @param categorieASupprimer catï¿½gorie ï¿½ supprimer
	 */
	public void delete(Categorie categorieASupprimer) {
		// Modification des donnï¿½es en Base de donnï¿½e avec cette requï¿½te SQL
		String sql = "DELETE FROM categorie WHERE nom = ?";
		String sqlVerifDefaut ="SELECT * FROM categorie WHERE nom = '" 
					+ categorieASupprimer.getNom() +"'";
				
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requï¿½te
			PreparedStatement deleteCategorie = cn.prepareStatement(sql);
			deleteCategorie.setString(1,categorieASupprimer.getNom());
			
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			System.out.println(sql);
			res.next();
			if (res.getInt(1) == 1) {
				System.out.println("La catï¿½gorie par defaut Gï¿½nï¿½ral ne peut pas ï¿½tre supprimï¿½e");
			} else {
				ArrayList<SousCategorie> listeSousCat = DAOSousCategorie.getSousCategories();
				for (int i = 0; i < listeSousCat.size(); i++) {
					System.out.println(listeSousCat.get(i).toString());
					DAOSousCategorie.supprSousCategorie(listeSousCat.get(i).toString());
				}
				deleteCategorie.executeUpdate();
				System.out.println("Suppression effectuï¿½e");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Mï¿½thode qui va vï¿½rifier si une catï¿½gorie existe
	 * @return boolean pour dire si ï¿½a existe ou pas
	 */
	public static boolean existe(String categorie) {
		boolean catExiste = false;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
	        ResultSet rset = st.executeQuery("SELECT * FROM categorie WHERE nom ='" + categorie + "'");
	        if(rset.next()) {
	        	catExiste = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		return catExiste;
	}

	/**
	 * Mï¿½thode : rï¿½cupï¿½re toutes les catï¿½gories prï¿½sentes dans
	 * la base de donnï¿½es, et les regroupe dans une ArrayList
	 * @return categories, ArrayList contenant toutes les catï¿½gories
	 */
	public static ArrayList<Categorie> findCategorie() {
		ArrayList<Categorie> categories = new ArrayList<Categorie>();
	
			
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
	        final ResultSet rset = st.executeQuery("SELECT * FROM categorie;");
	
	        while (rset.next()) {
	            final Categorie cat = rsetToCat(rset);
	            categories.add(cat);
	        }
	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return categories;
	}

	/**
	 * Mï¿½thode qui rï¿½cupï¿½re l'identifiant de la catï¿½gorie dont le nom est
	 * passï¿½ en paramï¿½tre
	 * @param nom de la categorie 
	 * @return identifiant de la catï¿½gorie
	 */
	public static int getId(String nomCategorie) {
		int idCategorie = -1;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
	        ResultSet rset = st.executeQuery("SELECT id FROM categorie WHERE nom ='" + nomCategorie + "'");
	
	        rset.next();
	        idCategorie = rset.getInt(1);
	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return idCategorie;
	}

	/**
	 * Retourne le nom d'une categorie, identifiée par son identifiant
	 * @param categorie identifiant de la categorie
	 * @return le nom de le categorie
	 */
	public static String getNomCategorie(String categorie) {

		String nomCategorie = "";
		
		//Requete SQL
		String sql = "SELECT nom FROM categorie WHERE id = '" + categorie + "'" ;
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requ�te
			ResultSet res = st.executeQuery(sql);
			
		   res.next();
		   nomCategorie = res.getString("nom");
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nomCategorie;
	}
	
	/**
	 * Mï¿½thode qui rï¿½cupï¿½re la prochaine clef-primaire afin de crï¿½er une nouvelle
	 * catï¿½gorie sans qu'il n'y est de doublon de clef-primaire
	 * @return la prochaine clef-primaire
	 */
	public static int getNextPrimaryKey() {
		int resultat=0;
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Requï¿½te sql pour initialiser les sous-catï¿½gories 
			String sql = "SELECT MAX(id) FROM categorie";
			ResultSet res = st.executeQuery(sql);
			res.next();
			resultat = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultat+1;
	}

	/**
	 * Methode qui prend en paramï¿½tres un rï¿½sultat renvoyï¿½ par la base de donnï¿½e et renvoie
	 *  une instance Categorie instanciï¿½e grï¿½ce aux donnï¿½es du rï¿½sultat
	 * @param rset rï¿½sultat renvoyï¿½ par la base de donnï¿½e 
	 * @return une instance Categorie instanciï¿½e grï¿½ce aux donnï¿½es du rï¿½sultat
	 * @throws SQLException
	 */
	private static Categorie rsetToCat(final ResultSet rset) throws SQLException {
	    
	    String nom = rset.getString("nom");
	    String photo = rset.getString("lienphoto");
	    
	
	    Categorie eleve = new Categorie(nom, photo);
	    return eleve;
	}

	/**
	 * Mï¿½thode qui change le nom d'une catï¿½gorie passï¿½ en paramï¿½tre dans la 
	 * base de donï¿½e par un nouveau nom passï¿½ en paramï¿½tre 
	 * @param newNom  nouveau nom de la catï¿½gorie 
	 * @param ancienNom  ancien nom de la catï¿½gorie 
	 */
	public static void update(String newNom, String ancienNom) {
		// Modification des donnï¿½es en Base de donnï¿½e avec cette requï¿½te SQL
		String sql = "UPDATE categorie SET nom = ? WHERE nom = ?";
		
		try {
			// Etape 1 : Rï¿½cupï¿½ration de l'ï¿½lï¿½ment de connexion ï¿½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requï¿½te
			PreparedStatement updateCategorie = cn.prepareStatement(sql);
			updateCategorie.setString(1, newNom);
			updateCategorie.setString(2, ancienNom);
			
			updateCategorie.executeUpdate();
			System.out.println("Modification effectuï¿½e");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	
}