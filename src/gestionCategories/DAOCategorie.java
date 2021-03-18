package gestionCategories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gestionBD.ConnexionBD;

public class DAOCategorie {

	private static Statement st=null;
	
		/** Objet Connexion */
	private static Connection cn =null;

	/**
	 * Méthode : récupère toutes les catégories présentes dans
	 * la base de données, et les regroupe dans une ArrayList
	 * @return categories, ArrayList contenant toutes les catégories
	 */
	public static ArrayList<Categorie> findCategorie() {
		ArrayList<Categorie> categories = new ArrayList<Categorie>();

			
		try {
			// Etape 1 : Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Création d'un statement
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
	 * Methode qui prend en paramètres un résultat renvoyé par la base de donnée et renvoie
	 *  une instance Categorie instanciée grâce aux données du résultat
	 * @param rset résultat renvoyé par la base de donnée 
	 * @return une instance Categorie instanciée grâce aux données du résultat
	 * @throws SQLException
	 */
	private static Categorie rsetToCat(final ResultSet rset) throws SQLException {
        
        String nom = rset.getString("nom");
        String photo = rset.getString("lienphoto");
        

        Categorie eleve = new Categorie(nom, photo);
        return eleve;
    }
	
	/**
	 * Méthode qui crée une catégorie dans la base de donnée avec le nom 
	 * passé en paramètre 
	 * @param obj catégorie rentrée
	 */
	public static void create(Categorie obj) {

		boolean tableExiste = false;

		try {
			// Etape 1 : Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Création d'un statement
			st = cn.createStatement();
			
			// Vérification que la table des catégories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, obj.getNom(), new String[] {"TABLE"});
			// itération qui prend un à un toutes les bases inscrites en localhost
			while(resultSet.next()) {
				if (resultSet.next()) {
					tableExiste = true;
				}
			}
			if (!tableExiste) {
				
				// Requête sql pour initialiser les catégories 
				String sql = "INSERT INTO categorie (nom, defaut, lienphoto, id)"
		                 + " VALUES (?, ?, ?, ?) ";
				//Valeur associe a la requete
				PreparedStatement initCategorie = cn.prepareStatement(sql);
				initCategorie.setString(1,  obj.getNom());
				initCategorie.setString(2, "0");
				initCategorie.setString(3, "null");
				initCategorie.setLong(4, getNextPrimaryKey());
				
				initCategorie.executeUpdate();
				
				System.out.println("Création de la categorie " + obj.getNom());
				
			} else {
				System.out.println("Table " + obj.getNom() + " deja existante");
			}
			
			// Raz tableExiste
			tableExiste = false;
			// Vérification que la table des sous-catégories existe
			resultSet = cn.getMetaData().getTables(null, null, "categorie", new String[] {"TABLE"});
			if (resultSet.next()) {
				tableExiste = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Méthode qui change le nom d'une catégorie passé en paramètre dans la 
	 * base de donée par un nouveau nom passé en paramètre 
	 * @param newNom  nouveau nom de la catégorie 
	 * @param ancienNom  ancien nom de la catégorie 
	 */
	public static void update(String newNom, String ancienNom) {
		// Modification des données en Base de donnée avec cette requéte SQL
		String sql = "UPDATE categorie SET nom = ? WHERE nom = ?";
		
		try {
			// Etape 1 : Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Création d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requéte
			PreparedStatement updateCategorie = cn.prepareStatement(sql);
			updateCategorie.setString(1, newNom);
			updateCategorie.setString(2, ancienNom);
			
			updateCategorie.executeUpdate();
			System.out.println("Modification effectuée");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Méthode qui supprime dans la base de donnée la catégorie dont le nom 
	 * est passé en parametre
	 * @param nomCategorieASupprimer nom de la catégorie à supprimer
	 */
	public static void delete(String nomCategorieASupprimer ) {
		// Modification des données en Base de donnée avec cette requéte SQL
		String sql = "DELETE FROM categorie WHERE nom = ?";
		String sqlVerifDefaut ="SELECT defaut FROM categorie WHERE nom = '"
				+ ""+nomCategorieASupprimer +"'";

			
		try {
			// Etape 1 : Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Création d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requéte
			//Valeur associée a la requete
			PreparedStatement deleteCategorie = cn.prepareStatement(sql);
			deleteCategorie.setString(1,nomCategorieASupprimer);
			
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			res.next();
			if (res.getInt(1) == 1) {
				System.out.println("La catégorie par defaut Général ne peut pas être supprimée");
			} else {
				ArrayList<SousCategorie> listeSousCat = DAOSousCategorie.getSousCategories();
				for (int i = 0; i < listeSousCat.size(); i++) {
					System.out.println(listeSousCat.get(i).toString());
					DAOSousCategorie.supprSousCategorie(listeSousCat.get(i).getNom());
				}
				deleteCategorie.executeUpdate();
				System.out.println("Suppression effectuée");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui supprime dans la base de donnée la catégorie passé en 
	 * paramètre 
	 * @param categorieASupprimer catégorie à supprimer
	 */
	public void delete(Categorie categorieASupprimer) {
		// Modification des données en Base de donnée avec cette requéte SQL
		String sql = "DELETE FROM categorie WHERE nom = ?";
		String sqlVerifDefaut ="SELECT defaut FROM categorie WHERE nom = '" 
					+ categorieASupprimer.getNom() +"'";
				
		try {
			// Etape 1 : Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Création d'un statement
			st = cn.createStatement();
			
			// Etape 3 : Execution de la requéte
			PreparedStatement deleteCategorie = cn.prepareStatement(sql);
			deleteCategorie.setString(1,categorieASupprimer.getNom());
			
			ResultSet res = st.executeQuery(sqlVerifDefaut);
			System.out.println(sql);
			res.next();
			if (res.getInt(1) == 1) {
				System.out.println("La catégorie par defaut Général ne peut pas être supprimée");
			} else {
				ArrayList<SousCategorie> listeSousCat = DAOSousCategorie.getSousCategories();
				for (int i = 0; i < listeSousCat.size(); i++) {
					System.out.println(listeSousCat.get(i).toString());
					DAOSousCategorie.supprSousCategorie(listeSousCat.get(i).toString());
				}
				deleteCategorie.executeUpdate();
				System.out.println("Suppression effectuée");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui crée dans la base de donnée les catégories par défaut si 
	 * elles n'existent pas encore
	 */
	public static void creerTableDefault() {
		boolean tableExiste = false;
		
		try {
			// Etape 1 : Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Création d'un statement
			st = cn.createStatement();
			
			// Vérification que la table des catégories existe
			ResultSet resultSet = cn.getMetaData().getTables(null, null, "categorie", new String[] {"TABLE"});
			// itération qui prend un à  un toutes les bases inscrites en localhost
			while(resultSet.next()) {
				if (resultSet.next()) {
					tableExiste = true;
				}
			}
			if (!tableExiste) {
				// Requête sql  pour créer la table catégorie de l'application en localhost
				String sql = "CREATE TABLE categorie ( nom VARCHAR(255), defaut NUMERIC(10), lienphoto VARCHAR(255), id SMALLINT(255) PRIMARY KEY)";
				// Etape 4 : Execution de la requête
				st.executeUpdate(sql);
				System.out.println("Création de la table categorie");
				
				// Requête sql pour initialiser les catégories avec général
				sql = "INSERT INTO categorie (nom, defaut, lienphoto, id) VALUES ('Général', '1', 'null', '0') ";
				st.executeUpdate(sql);
				System.out.println("Initialisation de la table avec la catégorie 'général'");
				
			} else {
				System.out.println("Table catégorie déjà  existante");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode qui récupère l'identifiant de la catégorie dont le nom est
	 * passé en paramètre
	 * @param nom de la categorie 
	 * @return identifiant de la catégorie
	 */
	public static int getId(String nomCategorie) {
		int idCategorie = -1;
		
		try {
			// Etape 1 : Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Création d'un statement
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
	 * Méthode qui récupère la prochaine clef-primaire afin de créer une nouvelle
	 * catégorie sans qu'il n'y est de doublon de clef-primaire
	 * @return la prochaine clef-primaire
	 */
	public static int getNextPrimaryKey() {
		int resultat=0;
		try {
			// Etape 1 : Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Création d'un statement
			st = cn.createStatement();
			
			// Requête sql pour initialiser les sous-catégories 
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
	 * Méthode qui va vérifier si une catégorie existe
	 * @return boolean pour dire si ça existe ou pas
	 */
	public static boolean existe(String categorie) {
		boolean catExiste = false;
		
		try {
			// Etape 1 : Récupération de l'élément de connexion à la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : Création d'un statement
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
	 * Retourne le nom d'une categorie, identifiÃ©e par son identifiant
	 * @param categorie identifiant de la categorie
	 * @return le nom de le categorie
	 */
	public static String getNomCategorie(String categorie) {

		String nomCategorie = "";
		
		//Requete SQL
		String sql = "SELECT nom FROM categorie WHERE id = '" + categorie + "'" ;
		
		try {
			// Etape 1 : RÃ¯Â¿Â½cupÃ¯Â¿Â½ration de l'Ã¯Â¿Â½lÃ¯Â¿Â½ment de connexion Ã¯Â¿Â½ la bd
			cn = ConnexionBD.getInstance();
			// Etape 2 : CrÃƒÂ¯Ã‚Â¿Ã‚Â½ation d'un statement
			st = cn.createStatement();
			
			// Etape 4 : Execution de la requï¿½te
			ResultSet res = st.executeQuery(sql);
			
		   res.next();
		   nomCategorie = res.getString("nom");
			
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nomCategorie;
	}
	
}