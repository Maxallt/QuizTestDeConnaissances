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
 * Classe qui va permettre de r�cup�rer des instances
 * SousCategorie en base de donn�e
 * @author Maxime Alliot
 *
 */

public class DAOSousCategorie {

	// Informations pour la connection � phpMyAdmin
	private static Connection cn =null;
	private static Statement st=null;
	
	/**
	 * Mï¿½thode qui va crï¿½er la table par dï¿½faut dans la base de donnï¿½e
	 */
	public static void creerTableDefaut() {
		boolean tableExiste = false;
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Vï¿½rification que la table des sous-catï¿½gories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "souscategorie", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			if (!tableExiste) {
				// Requ�te sql  pour cr�er la table sous-cat�gorie de l'application en localhost
				String sql = "CREATE TABLE souscategorie ( nom VARCHAR(255), defaut NUMERIC(10), lienphoto VARCHAR(255), idSurCategorie NUMERIC, id NUMERIC PRIMARY KEY, CONSTRAINT fk_idSurCat FOREIGN KEY (idSurCategorie) REFERENCES categorie(id))";
				// Etape 4 : Execution de la requ�te
				st.executeUpdate(sql);
				System.out.println("Cr�ation de la table sous-cat�gorie");
				
				// Requï¿½te sql pour initialiser les sous-catï¿½gories avec gï¿½nï¿½ral
				sql = "INSERT INTO souscategorie (nom, defaut, lienphoto, idSurCategorie, id) VALUES (?, ?, ?, ?, ?) ";
				
				//Valeur associe a la requete
				PreparedStatement tableDefault = cn.prepareStatement(sql);
				tableDefault.setString(1, "General" );
				tableDefault.setLong(2, 1);
				tableDefault.setString(3, null);
				tableDefault.setString(4, "0");
				tableDefault.setString(5, "0");
				
				
				tableDefault.executeUpdate();
				System.out.println("Initialisation de la table avec la sous-catï¿½gorie 'gï¿½nï¿½ral'");
				
			} else {
				System.out.println("Table des sous-cat�orie d�j� existante");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mï¿½thode qui va crï¿½er une sous-catï¿½gorie et la mettre dans la base
	 * @param nom nom de la sous-catï¿½gorie
	 * @param lienPhoto lien vers la photo de la sous-catï¿½gorie (facultatif)
	 * @param surCategorie nom de la catï¿½gorie qui contient la nouvelle sous-catï¿½gorie
	 */
	public static void creerEnBase(String nom, String lienPhoto, String surCategorie) {
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Requï¿½te sql pour initialiser les sous-catï¿½gories 
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
			System.out.println("Sous-cat�gorie " + nom + " correctement cr��e");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * M�thode qui va v�rifier si une sous-cat�gorie existe
	 * @return boolean pour dire si �a existe ou pas
	 */
	public static boolean existe(String sousCategorie) {
		boolean catExiste = false;
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
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
	 * M�thode qui permet de r�cup�rer toutes les sous cat�gories en base
	 * @return une ArrayList avec toutes les sous-cat�gories cr��es dans la base
	 */
	public static ArrayList<SousCategorie> getSousCategories() {
		ArrayList<SousCategorie> sousCategories = new ArrayList<>();
		
    	// R�cup�ration des donn�es en Base de donn�e
    	String sql = "SELECT nom FROM souscategorie";
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();

			// Etape 3 : Execution de la requ�te
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
	 * M�thode qui permet de r�cup�rer toutes les sous cat�gories en base
	 * en fonction d'une cat�gorie pass�e en param�tre
	 * @return une ArrayList avec toutes les sous-cat�gories cr��es dans la base+
	 * @param categorie Nom de la cat�gorie qui poss�de des sous cat�gories
	 */
	public static ArrayList<SousCategorie> getSousCategories(String categorie) {
		ArrayList<SousCategorie> sousCategories = new ArrayList<>();
		
    	// R�cup�ration des donn�es en base de donn�es
    	String sql = "SELECT nom FROM souscategorie WHERE idSurCategorie ='"+DAOCategorie.getId(categorie)+"'";
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr��tion d'un statement
			st = cn.createStatement();

			// Etape 3 : Execution de la requ�te
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
	 * Rï¿½cupï¿½re la prochaine clï¿½ primaire pour crï¿½er la suivante
	 * @return La derniï¿½re clï¿½ primaire +1
	 */
	public static int getNextPrimaryKey() {
		int resultat=0;
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Requï¿½te sql pour initialiser les sous-catï¿½gories 
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
	 * M�thode qui r�cup�re l'identifiant de la cat�gorie dont le nom est
	 * pass� en param�tre
	 * @param nom de la categorie 
	 * @return identifiant de la cat�gorie
	 */
	public static int getId(String nomSousCategorie) {
		int idCategorie = -1;
		String sql = "SELECT id FROM souscategorie WHERE nom ='" + nomSousCategorie + "'";
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
	        ResultSet rset = st.executeQuery(sql);
	
	        rset.next();
	        idCategorie = rset.getInt(1);
	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return idCategorie;
	}

	/**
	 * Modifie le nom d'une sous-catï¿½gorie
	 * @param sousCategorie sous-catï¿½gorie ï¿½ modifier
	 * @param newName nouveau nom
	 */
	public static void modifierNom(String sousCategorie, String newName) {
		// Modification des donnï¿½es en Base de donnï¿½e avec cette requï¿½te SQL
    	String sql = "UPDATE souscategorie SET nom = ?, lienphoto = ? WHERE nom = ? ";
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Cr�ation d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requ�te
			
			//Valeur associe a la requete
			PreparedStatement tableDefault = cn.prepareStatement(sql);
			tableDefault.setString(1, newName );
			tableDefault.setString(2, " ");
			tableDefault.setString(3, sousCategorie);
			
			tableDefault.executeUpdate();
			System.out.println("Modification effectu�e !");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Mï¿½thode qui supprime une sous-catï¿½gorie en base
	 * @param nom Nom de la sous-catï¿½gorie ï¿½ supprimer
	 */
	public static void supprSousCategorie(String nom) {
		// Modification des donnï¿½es en Base de donnï¿½e avec cette requï¿½te SQL
		String sql = "DELETE FROM souscategorie WHERE nom = ?";
		String sqlVerifDefaut ="SELECT defaut FROM souscategorie WHERE nom = '"+ nom+"'";
		
		try {
			// Etape 1 : R�cup�ration de l'�l�ment de connexion � la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Crï¿½ation d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requï¿½te
			//Valeur associe a la requete
			PreparedStatement supprSousCategorie = cn.prepareStatement(sql);
			supprSousCategorie.setString(1,nom);
	
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			
			res.next();
			if (res.getInt(1) == 1) {
				System.out.println("La sous-cat�gorie par d�faut G�n�ral ne peut pas �tre supprim�e");
			} else {
				supprSousCategorie.executeUpdate();
				System.out.println("Suppression effectu�e !");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
