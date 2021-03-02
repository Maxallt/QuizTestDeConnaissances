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
 * Classe qui va permettre de récupérer des instances
 * Categorie en base de donnée
 * @author Nicolas.A
 * @version 2.0
 */

public class DAOCategorie {

	private static Statement st=null;
	
		/** Objet Connexion */
	private static Connection cn =null;

	/**
	 * M�thode : r�cup�re toutes les cat�gories pr�sentes dans
	 * la base de donn�es, et les regroupe dans une ArrayList
	 * @return categories, ArrayList contenant toutes les cat�gories
	 */
	public static ArrayList<Categorie> findCategorie() {
		ArrayList<Categorie> categories = new ArrayList<Categorie>();

			
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
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
	 * Methode qui prend en param�tres un r�sultat renvoy� par la base de donn�e et renvoie
	 *  une instance Categorie instanci�e gr�ce aux donn�es du r�sultat
	 * @param rset r�sultat renvoy� par la base de donn�e 
	 * @return une instance Categorie instanci�e gr�ce aux donn�es du r�sultat
	 * @throws SQLException
	 */
	private static Categorie rsetToCat(final ResultSet rset) throws SQLException {
        
        String nom = rset.getString("nom");
        String photo = rset.getString("lienphoto");
        

        Categorie eleve = new Categorie(nom, photo);
        return eleve;
    }
	
	/**
	 * M�thode qui cr�e une cat�gorie dans la base de donn�e avec le nom 
	 * pass� en param�tre 
	 * @param obj cat�gorie rentr�e
	 */
	public static void create(Categorie obj) {

		boolean tableExiste = false;

		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// V�rification que la table des cat�gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, obj.getNom(), new String[] {"TABLE"});
			// it�ration qui prend un � un toutes les bases inscrites en localhost
			while(resultSet.next()) {
				if (resultSet.next()) {
					tableExiste = true;
				}
			}
			if (!tableExiste) {
				
				// Requ�te sql pour initialiser les cat�gories 
				String sql = "INSERT INTO categorie (nom, defaut, lienphoto, id)"
		                 + " VALUES (?, ?, ?, ?) ";
				//Valeur associe a la requete
				PreparedStatement initCategorie = cn.prepareStatement(sql);
				initCategorie.setString(1,  obj.getNom());
				initCategorie.setString(2, "0");
				initCategorie.setString(3, "null");
				initCategorie.setLong(4, getNextPrimaryKey());
				
				initCategorie.executeUpdate();
				
				System.out.println("Cr�ation de la categorie " + obj.getNom());
				
			} else {
				System.out.println("Table " + obj.getNom() + " deja existante");
			}
			
			// Raz tableExiste
			tableExiste = false;
			// V�rification que la table des sous-cat�gories existe
			resultSet = cn.getMetaData().getTables(null, null, "categorie", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * M�thode qui change le nom d'une cat�gorie pass� en param�tre dans la 
	 * base de don�e par un nouveau nom pass� en param�tre 
	 * @param newNom  nouveau nom de la cat�gorie 
	 * @param ancienNom  ancien nom de la cat�gorie 
	 */
	public static void update(String newNom, String ancienNom) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "UPDATE categorie SET nom = ? WHERE nom = ?";
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requ�te
			PreparedStatement updateCategorie = cn.prepareStatement(sql);
			updateCategorie.setString(1, newNom);
			updateCategorie.setString(2, ancienNom);
			
			updateCategorie.executeUpdate();
			System.out.println("Modification effectu�e");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * M�thode qui supprime dans la base de donn�e la cat�gorie dont le nom 
	 * est pass� en parametre
	 * @param nomCategorieASupprimer nom de la cat�gorie � supprimer
	 */
	public static void delete(String nomCategorieASupprimer ) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "DELETE FROM categorie WHERE nom = ?";
		String sqlVerifDefaut ="SELECT defaut FROM categorie WHERE nom = '"
				+ ""+nomCategorieASupprimer +"'";

			
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requ�te
			//Valeur associ�e a la requete
			PreparedStatement deleteCategorie = cn.prepareStatement(sql);
			deleteCategorie.setString(1,nomCategorieASupprimer);
			
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			res.next();
			if (res.getInt(1) == 1) {
				System.out.println("La cat�gorie par defaut G�n�ral ne peut pas �tre supprim�e");
			} else {
				ArrayList<SousCategorie> listeSousCat = DAOSousCategorie.getSousCategories();
				for (int i = 0; i < listeSousCat.size(); i++) {
					System.out.println(listeSousCat.get(i).toString());
					DAOSousCategorie.supprSousCategorie(listeSousCat.get(i).getNom());
				}
				deleteCategorie.executeUpdate();
				System.out.println("Suppression effectu�e");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * M�thode qui supprime dans la base de donn�e la cat�gorie pass� en 
	 * param�tre 
	 * @param categorieASupprimer cat�gorie � supprimer
	 */
	public void delete(Categorie categorieASupprimer) {
		// Modification des donn�es en Base de donn�e avec cette requ�te SQL
		String sql = "DELETE FROM categorie WHERE nom = ?";
		String sqlVerifDefaut ="SELECT * FROM categorie WHERE nom = '" 
					+ categorieASupprimer.getNom() +"'";
				
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requ�te
			PreparedStatement deleteCategorie = cn.prepareStatement(sql);
			deleteCategorie.setString(1,categorieASupprimer.getNom());
			
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			System.out.println(sql);
			res.next();
			if (res.getInt(1) == 1) {
				System.out.println("La cat�gorie par defaut G�n�ral ne peut pas �tre supprim�e");
			} else {
				ArrayList<SousCategorie> listeSousCat = DAOSousCategorie.getSousCategories();
				for (int i = 0; i < listeSousCat.size(); i++) {
					System.out.println(listeSousCat.get(i).toString());
					DAOSousCategorie.supprSousCategorie(listeSousCat.get(i).toString());
				}
				deleteCategorie.executeUpdate();
				System.out.println("Suppression effectu�e");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * M�thode qui cr�e dans la base de donn�e les cat�gories par d�faut si 
	 * elles n'existent pas encore
	 */
	public static void creerTableDefault() {
		boolean tableExiste = false;
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// V�rification que la table des cat�gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "categorie", new String[] {"TABLE"});
			// it�ration qui prend un � un toutes les bases inscrites en localhost
			if (resultSet.next()) {
				tableExiste = true;
			}
			
			if (!tableExiste) {
				// Requ�te sql  pour cr�er la table cat�gorie de l'application en localhost
				String sql = "CREATE TABLE categorie ( nom VARCHAR(255), defaut NUMERIC(10), lienphoto VARCHAR(255), id VARCHAR(255) PRIMARY KEY)";
				// Etape 4 : Execution de la requ�te
				st.executeUpdate(sql);
				System.out.println("Cr�ation de la table categorie");
				
				// Requ�te sql pour initialiser les cat�gories avec g�n�ral
				sql = "INSERT INTO categorie (nom, defaut, lienphoto, id) VALUES ('G�n�ral', '1', 'null', '0') ";
				st.executeUpdate(sql);
				System.out.println("Initialisation de la table avec la cat�gorie 'g�n�ral'");
				
			} else {
				System.out.println("Table cat�gorie d�j� existante");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�thode qui r�cup�re l'identifiant de la cat�gorie dont le nom est
	 * pass� en param�tre
	 * @param nom de la categorie 
	 * @return identifiant de la cat�gorie
	 */
	public static int getId(String nomCategorie) {
		int idCategorie = -1;
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
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
	 * M�thode qui r�cup�re la prochaine clef-primaire afin de cr�er une nouvelle
	 * cat�gorie sans qu'il n'y est de doublon de clef-primaire
	 * @return la prochaine clef-primaire
	 */
	public static int getNextPrimaryKey() {
		int resultat=0;
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// Requ�te sql pour initialiser les sous-cat�gories 
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
	 * M�thode qui va v�rifier si une cat�gorie existe
	 * @return boolean pour dire si �a existe ou pas
	 */
	public static boolean existe(String categorie) {
		boolean catExiste = false;
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
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

	
}