/*
 * DAOSousCategorie.java 		10/12/2020
 * Data Access Object pour la classe SousCategorie
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
 * SousCategorie en base de donnée
 * @author Maxime Alliot
 *
 */

public class DAOSousCategorie {

	// Informations pour la connection à phpMyAdmin
	private static Connection cn =null;
	private static Statement st=null;
	
	/**
	 * Mï¿½thode qui permet de rï¿½cupï¿½rer toutes les sous catï¿½gories en base
	 * @return une ArrayList avec toutes les sous-catï¿½gories crï¿½ï¿½es dans la base
	 */
	public static ArrayList<SousCategorie> getSousCategories() {
		ArrayList<SousCategorie> sousCategories = new ArrayList<>();
		
    	// Rï¿½cupï¿½ration des donnï¿½es en Base de donnï¿½e
    	String sql = "SELECT nom FROM souscategorie";
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : Crï¿½ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sql);

			while(res.next()) {
				sousCategories.add(new SousCategorie(new Categorie(),res.getString("nom")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sousCategories;
	}
	
	/**
	 * Mï¿½thode qui permet de rï¿½cupï¿½rer toutes les sous catï¿½gories en base
	 * en fonction d'une catï¿½gorie passï¿½e en paramï¿½tre
	 * @return une ArrayList avec toutes les sous-catï¿½gories crï¿½ï¿½es dans la base+
	 * @param categorie Nom de la catï¿½gorie qui possï¿½de des sous catï¿½gories
	 */
	public static ArrayList<SousCategorie> getSousCategories(String categorie) {
		ArrayList<SousCategorie> sousCategories = new ArrayList<>();
		
    	// Récupération des données en base de données
    	String sql = "SELECT nom FROM souscategorie WHERE idSurCategorie ='"+DAOCategorie.getId(categorie)+"'";
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : Créàtion d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requête
			ResultSet res = st.executeQuery(sql);

			while(res.next()) {
				sousCategories.add(new SousCategorie(new Categorie(),res.getString("nom")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sousCategories;
	}
	
	
	/**
	 * MÃ¯Â¿Â½thode qui supprime une sous-catÃ¯Â¿Â½gorie en base
	 * @param nom Nom de la sous-catÃ¯Â¿Â½gorie Ã¯Â¿Â½ supprimer
	 */
	public static void supprSousCategorie(String nom) {
		// Modification des donnÃ¯Â¿Â½es en Base de donnÃ¯Â¿Â½e avec cette requÃ¯Â¿Â½te SQL
		String sql = "DELETE FROM souscategorie WHERE nom = ?";
		String sqlVerifDefaut ="SELECT defaut FROM souscategorie WHERE nom = '"+ nom+"'";
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requÃ¯Â¿Â½te
			//Valeur associe a la requete
			PreparedStatement supprSousCategorie = cn.prepareStatement(sql);
			supprSousCategorie.setString(1,nom);

			ResultSet res = st.executeQuery(sqlVerifDefaut);
			
			res.next();
			if (res.getInt(1) == 1) {
				System.out.println("La sous-catégorie par défaut Général ne peut pas être supprimée");
			} else {
				supprSousCategorie.executeUpdate();
				System.out.println("Suppression effectuée !");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * MÃ¯Â¿Â½thode qui va crÃ¯Â¿Â½er la table par dÃ¯Â¿Â½faut dans la base de donnÃ¯Â¿Â½e
	 */
	public static void creerTableDefaut() {
		boolean tableExiste = false;
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// VÃ¯Â¿Â½rification que la table des sous-catÃ¯Â¿Â½gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "souscategorie", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			if (!tableExiste) {
				// Requête sql  pour créer la table sous-catégorie de l'application en localhost
				String sql = "CREATE TABLE souscategorie ( nom VARCHAR(255), defaut NUMERIC(10), lienphoto VARCHAR(255), idSurCategorie VARCHAR(255), id VARCHAR(255) PRIMARY KEY, CONSTRAINT fk_idSurCat FOREIGN KEY (idSurCategorie) REFERENCES categorie(id))";
				// Etape 4 : Execution de la requête
				st.executeUpdate(sql);
				System.out.println("Création de la table sous-catégorie");
				
				// RequÃ¯Â¿Â½te sql pour initialiser les sous-catÃ¯Â¿Â½gories avec gÃ¯Â¿Â½nÃ¯Â¿Â½ral
				sql = "INSERT INTO souscategorie (nom, defaut, lienphoto, idSurCategorie, id) VALUES (?, ?, ?, ?, ?) ";
				
				//Valeur associe a la requete
				PreparedStatement tableDefault = cn.prepareStatement(sql);
				tableDefault.setString(1, "General" );
				tableDefault.setLong(2, 1);
				tableDefault.setString(3, null);
				tableDefault.setString(4, "0");
				tableDefault.setString(5, "0");
				
				
				tableDefault.executeUpdate();
				System.out.println("Initialisation de la table avec la sous-catÃ¯Â¿Â½gorie 'gÃ¯Â¿Â½nÃ¯Â¿Â½ral'");
				
			} else {
				System.out.println("Table des sous-catéorie déjà existante");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * MÃ¯Â¿Â½thode qui va crÃ¯Â¿Â½er une sous-catÃ¯Â¿Â½gorie et la mettre dans la base
	 * @param nom nom de la sous-catÃ¯Â¿Â½gorie
	 * @param lienPhoto lien vers la photo de la sous-catÃ¯Â¿Â½gorie (facultatif)
	 * @param surCategorie nom de la catÃ¯Â¿Â½gorie qui contient la nouvelle sous-catÃ¯Â¿Â½gorie
	 */
	public static void creerEnBase(String nom, String lienPhoto, String surCategorie) {
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// RequÃ¯Â¿Â½te sql pour initialiser les sous-catÃ¯Â¿Â½gories 
			String sql = "INSERT INTO souscategorie (nom, defaut, lienphoto, idSurCategorie, id) VALUES (?, ?, ?, ?, ? )";
			
			//Valeur associe a la requete
			PreparedStatement creationSousCat = cn.prepareStatement(sql);
			creationSousCat.setString(1, nom );
			System.out.println("nom");
			creationSousCat.setLong(2, 0);
			System.out.println("default");
			creationSousCat.setString(3, lienPhoto);
			System.out.println("lienphoto");
			creationSousCat.setLong(4, DAOCategorie.getId(surCategorie));
			System.out.println("idSurCategorie");
			creationSousCat.setLong(5,  getNextPrimaryKey());
			System.out.println("id");
			
			creationSousCat.executeUpdate();
			System.out.println("Sous-catégorie " + nom + " correctement créée");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * RÃ¯Â¿Â½cupÃ¯Â¿Â½re la prochaine clÃ¯Â¿Â½ primaire pour crÃ¯Â¿Â½er la suivante
	 * @return La derniÃ¯Â¿Â½re clÃ¯Â¿Â½ primaire +1
	 */
	public static int getNextPrimaryKey() {
		int resultat=0;
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : CrÃ¯Â¿Â½ation d'un statement
			st = cn.createStatement();
			
			// RequÃ¯Â¿Â½te sql pour initialiser les sous-catÃ¯Â¿Â½gories 
			String sql = "SELECT MAX(id) FROM souscategorie";
			ResultSet res = st.executeQuery(sql);
			res.next();
			resultat = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultat+1;
	}
	
	/**
	 * Modifie le nom d'une sous-catÃ¯Â¿Â½gorie
	 * @param sousCategorie sous-catÃ¯Â¿Â½gorie Ã¯Â¿Â½ modifier
	 * @param newName nouveau nom
	 */
	public static void modifierNom(String sousCategorie, String newName) {
		// Modification des donnÃ¯Â¿Â½es en Base de donnÃ¯Â¿Â½e avec cette requÃ¯Â¿Â½te SQL
    	String sql = "UPDATE souscategorie SET nom = ?, lienphoto = ? WHERE nom = ? ";
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : Création d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requête
			
			//Valeur associe a la requete
			PreparedStatement tableDefault = cn.prepareStatement(sql);
			tableDefault.setString(1, newName );
			tableDefault.setString(2, " ");
			tableDefault.setString(3, sousCategorie);
			
			tableDefault.executeUpdate();
			System.out.println("Modification effectuée !");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui va vérifier si une sous-catégorie existe
	 * @return boolean pour dire si ça existe ou pas
	 */
	public static boolean existe(String sousCategorie) {
		boolean catExiste = false;
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : Création d'un statement
			st = cn.createStatement();
            ResultSet rset = st.executeQuery("SELECT * FROM souscategorie WHERE nom ='" + sousCategorie + "'");
            if(rset.next()) {
            	catExiste = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		
		return catExiste;
	}
	
	/**
	 * Méthode qui récupère l'identifiant de la catégorie dont le nom est
	 * passé en paramètre
	 * @param nom de la categorie 
	 * @return identifiant de la catégorie
	 */
	public static int getId(String nomSousCategorie) {
		int idCategorie = -1;
		
		try {
			// Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 3 : Création d'un statement
			st = cn.createStatement();
            ResultSet rset = st.executeQuery("SELECT id FROM souscategorie WHERE nom ='" + nomSousCategorie + "'");

            rset.next();
            idCategorie = rset.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
		return idCategorie;
	}
}
