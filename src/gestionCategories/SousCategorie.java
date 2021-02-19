/* 
 * SousCategorie.java							  31/10/2020
 * Classe qui regoupera les sous cat�gories de l'application
 */

package gestionCategories;

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
    
    
    public int getNOMBRE_MAX_CARACTERE() {
		return NOMBRE_MAX_CARACTERE;
	}

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
	}

    /**
     * M�thode qui permet de renommer la sous cat�gorie
     * @param nouveauNom Nom qui sera le nouveau nom de la 
     * 					 sous-cat�gorie
     */
    public void setIdentifiant(String nouveauNom) {
    	identifiant = nouveauNom;
    }

}
