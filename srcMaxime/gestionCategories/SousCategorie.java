/* 
 * SousCategorie.java							  31/10/2020
 * Classe qui regoupera les sous catégories de l'application
 */

package gestionCategories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe instanciable qui va gérer les sous catégories et ses attributs.
 * Elle permettra de lier les questions dans une sous catégorie qui sera
 * plus précise que la catégorie qui l'inclura.
 * @author Alliot Maxime
 *
 */
public class SousCategorie extends Categorie {
	
	/** Element qui identifiera le nom de la sous-catégorie */
    private String identifiant;
    
    /** Lien vers la photo (éventuelle) qui désignera la sous-catégorie*/
    private String photo;
    
    /** Tableau regroupant les questions de la sous-catégorie */
    private String[] questions;
    
    /** Tableau contenant les reponses aux questions de la sous-catégorie*/
    private String[][] reponses;

    /** Catégorie qui est associé à la sous-catégorie */
    Categorie surCategorie;
    
    /** Nombre maximum de caractère pour le nom d'une sous-catégorie */
    private final int NOMBRE_MAX_CARACTERE = 30;
    
    /**
     * Constructeur par défaut d'une sous-catégorie
     * qui initialise tout à null
     * @param categorie Catégorie à laquelle va appartenir la 
     * 					sous-catégorie créée
     */
    public SousCategorie(Categorie categorie) {
    	super();
    	identifiant = null;
    	photo = categorie.getPhoto();
    	questions = null;
    	reponses = null;
    	surCategorie = categorie;
    }

    /**
     * Constructeur qui met à null tous les paramètres
     * sauf le nom qui est passé en paramètre de la méthode
     * @param identifiant nom donné lors de la création de la sous-catégorie
     * @param categorie Catégorie à laquelle va appartenir la 
			sous-catégorie créée
     */
    public SousCategorie(Categorie categorie, String identifiant) {
    	super(identifiant);
    	identifiant = null;
    	photo = categorie.getPhoto();
    	questions = null;
    	reponses = null;
    	surCategorie = categorie;
		sauverEnBase(identifiant,photo,surCategorie);
    }

    /**
     * Constructeur qui initialise le nom, les questions et les réponses 
     * @param identifiantInitialise
     * @param questionsInitialisees
     * @param reponsesInitialisees
     * @param categorie Catégorie à laquelle va appartenir la 
     * 					sous-catégorie créée
     */
    public SousCategorie(String identifiantInitialise,
    		             String[] questionsInitialisees,
    		             String[][] reponsesInitialisees,
    		             Categorie categorie) {
    	super(identifiantInitialise);
    	questions = questionsInitialisees;
    	reponses = reponsesInitialisees;
    	photo = categorie.getPhoto();
    	surCategorie = categorie;
		sauverEnBase(identifiantInitialise,photo,surCategorie);
    }
    
    /**
     * Constructeur qui initilaise tous les attributs de la sous-catégorie
     * @param identifiantInitialise
     * @param questionsInitialisees
     * @param reponsesInitialisees
     * @param lienPhoto
     * @param categorie Catégorie à laquelle va appartenir la 
     * 					sous-catégorie créée
     */
    public SousCategorie(String identifiantInitialise,
            String[] questionsInitialisees,
            String[][] reponsesInitialisees,
            String lienPhoto,
            Categorie categorie) {
		super(identifiantInitialise);
		questions = questionsInitialisees;
		reponses = reponsesInitialisees;
		photo = lienPhoto;
		surCategorie = categorie;
		sauverEnBase(identifiantInitialise,lienPhoto,surCategorie);
	}

    /**
     * Méthode qui permet de renommer la sous catégorie
     * @param nouveauNom Nom qui sera le nouveau nom de la 
     * 					 sous-catégorie
     */
    public void setIdentifiant(String nouveauNom) {
    	identifiant = nouveauNom;
    }

    /**
     * Méthode qui permet de supprimer la sous-catégorie dans 
     * la base de données
     */
    @Override
    public void supprimer() {
    	// STUB
    	// TODO suppression en Base de données
    }
    
    public static void sauverEnBase(String nomADonner, String lienPhoto, Categorie surCategorie) {
    	String sql = "INSERT INTO `souscategorie`(`nom`, `defaut`, `lienphoto`, `idSurCategorie`,'id') VALUES ('" + nomADonner + "','0','" + lienPhoto + "','1','1')";
		
		// Infos accès BD
		String login = "root";
		String passwd = "root";
		Connection cn =null;
		Statement st=null;
		String url = "jdbc:mysql://localhost/quizztestconnaissances?serverTimezone=UTC";
		
		try {
			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Etape 2 : récupération de la connexion
			cn = DriverManager.getConnection(url, login, passwd);
			// Etape 3 : Création d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requête
			st.executeUpdate(sql);
			System.out.println("Ajout de la sous-catégorie à la base de donnée");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 5 : libérer ressources de la mémoire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }

}
