/* 
 * SousCategorie.java							  31/10/2020
 * Classe qui regoupera les sous catégories de l'application
 */

package gestionCategories;

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
    
    
    public int getNOMBRE_MAX_CARACTERE() {
		return NOMBRE_MAX_CARACTERE;
	}

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
	}

    /**
     * Méthode qui permet de renommer la sous catégorie
     * @param nouveauNom Nom qui sera le nouveau nom de la 
     * 					 sous-catégorie
     */
    public void setIdentifiant(String nouveauNom) {
    	identifiant = nouveauNom;
    }

}
