package gestionCategories;

public class Reponse {
	
	/** Boolean qui indique la valaidit� de la r�ponse initilatis� � faux par d�faut */ 
	private static final boolean VALID_PAR_DEFAUT = false;
	
	/** R�ponse qui, par d�faut, sera vide */
	private static final String INTITULE_PAR_DEFAUT = " ";
	
	/** Nombre de carat�re maximum possible � mettre dans une r�ponse */
	private static final int NOMBRE_CARACTERES_MAXIMUM = 150;
	
	/** Boolean qui est vrai si la r�ponse est vraie, faux sinon */
	private boolean validite;
	
	/** Intitul� de la r�ponse */
	String intituleReponse;
	
	/** Constructeur par d�faut : la validit� des reponse sont initialis� � 
	 * false et l'intitul� est initialis� avec les valeurs par d�faut 
	 */
	public Reponse() {
		validite = false;
		intituleReponse = INTITULE_PAR_DEFAUT;
	}
	
	/** Constructeur avec argument pour initialiser  une r�ponse 
	 * @param valid  validit� de laa r�ponse 
	 * @param intitule  intitul� de la r�ponse
	 */
	public Reponse(boolean valid, String intitule) {
		validite = valid;
		intituleReponse=(intitule==null||intitule.length()==0
			       ||intitule.length() > NOMBRE_CARACTERES_MAXIMUM)?
			    	INTITULE_PAR_DEFAUT : intitule;
	}
	
	/** 
	 * Accesseur sur l'intitul� de la r�ponse courante
	 * @retrun intituleReponse intitul� de la r�ponse 
	 */
	public String getReponseTexte () {
		return intituleReponse;	
	}
	
	/** 
	 * Modifie l'intitul� de la reponse courante avec l'argument.
	 * Si le nouveau intitul� est invalide, c'est l'intitul� par d�faut qui
	 * est attribu�
	 * @param nouveauIntitule  nouveau intitul� pour la r�ponse
	 */
	public void setReponseTexte (String nouveauIntitule) {
		intituleReponse = (nouveauIntitule == null || nouveauIntitule.length() == 0 
				? INTITULE_PAR_DEFAUT : nouveauIntitule);
	}
	
	/** 
	 * Modifie la validit� de la question courante avec le boolean en argument
	 * @param nouvelleValidite  boolean qui indique la nouvelle validit�
	 */
	public void setValadite (boolean nouvelleValidite) {
		this.validite = nouvelleValidite;	
	}
	
	/**
	 * Acceseur sur la validit� d'une r�ponse
	 * @return un bool�en qui correspond � la validit� de la r�ponse courante
	 */
	public boolean getValidite() {
		return this.validite;
	}

}
