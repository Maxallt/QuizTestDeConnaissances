/* 
 * SousCategorie.java							  31/10/2020
 * Classe qui regoupera les sous cat�gories de l'application
 */

package gestionCategories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe instanciable qui va g�rer les sous cat�gories et ses attributs.
 * Elle permettra de lier les questions dans une sous cat�gorie qui sera
 * plus pr�cise que la cat�gorie qui l'inclura.
 * @author Alliot Maxime
 *
 */
public class SousCategorie extends Categorie {
	
	/** Element qui identifiera le nom de la sous-cat�gorie */
    private String identifiant;
    
    /** Lien vers la photo (�ventuelle) qui d�signera la sous-cat�gorie*/
    private String photo;
    
    /** Tableau regroupant les questions de la sous-cat�gorie */
    private String[] questions;
    
    /** Tableau contenant les reponses aux questions de la sous-cat�gorie*/
    private String[][] reponses;

    /** Cat�gorie qui est associ� � la sous-cat�gorie */
    Categorie surCategorie;
    
    /** Nombre maximum de caract�re pour le nom d'une sous-cat�gorie */
    private final int NOMBRE_MAX_CARACTERE = 30;
    
    /**
     * Constructeur par d�faut d'une sous-cat�gorie
     * qui initialise tout � null
     * @param categorie Cat�gorie � laquelle va appartenir la 
     * 					sous-cat�gorie cr��e
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
     * Constructeur qui met � null tous les param�tres
     * sauf le nom qui est pass� en param�tre de la m�thode
     * @param identifiant nom donn� lors de la cr�ation de la sous-cat�gorie
     * @param categorie Cat�gorie � laquelle va appartenir la 
			sous-cat�gorie cr��e
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
     * Constructeur qui initialise le nom, les questions et les r�ponses 
     * @param identifiantInitialise
     * @param questionsInitialisees
     * @param reponsesInitialisees
     * @param categorie Cat�gorie � laquelle va appartenir la 
     * 					sous-cat�gorie cr��e
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
     * Constructeur qui initilaise tous les attributs de la sous-cat�gorie
     * @param identifiantInitialise
     * @param questionsInitialisees
     * @param reponsesInitialisees
     * @param lienPhoto
     * @param categorie Cat�gorie � laquelle va appartenir la 
     * 					sous-cat�gorie cr��e
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
     * M�thode qui permet de renommer la sous cat�gorie
     * @param nouveauNom Nom qui sera le nouveau nom de la 
     * 					 sous-cat�gorie
     */
    public void setIdentifiant(String nouveauNom) {
    	identifiant = nouveauNom;
    }

    /**
     * M�thode qui permet de supprimer la sous-cat�gorie dans 
     * la base de donn�es
     */
    @Override
    public void supprimer() {
    	// STUB
    	// TODO suppression en Base de donn�es
    }
    
    public static void sauverEnBase(String nomADonner, String lienPhoto, Categorie surCategorie) {
    	String sql = "INSERT INTO `souscategorie`(`nom`, `defaut`, `lienphoto`, `idSurCategorie`,'id') VALUES ('" + nomADonner + "','0','" + lienPhoto + "','1','1')";
		
		// Infos acc�s BD
		String login = "root";
		String passwd = "root";
		Connection cn =null;
		Statement st=null;
		String url = "jdbc:mysql://localhost/quizztestconnaissances?serverTimezone=UTC";
		
		try {
			// Etape 1 : Chargement du driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Etape 2 : r�cup�ration de la connexion
			cn = DriverManager.getConnection(url, login, passwd);
			// Etape 3 : Cr�ation d'un statement
			st = cn.createStatement();

			// Etape 4 : Execution de la requ�te
			st.executeUpdate(sql);
			System.out.println("Ajout de la sous-cat�gorie � la base de donn�e");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				// Etape 5 : lib�rer ressources de la m�moire.
				cn.close();
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }

}
