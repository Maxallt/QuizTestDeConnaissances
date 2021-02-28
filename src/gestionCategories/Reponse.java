package gestionCategories;

public class Reponse {
	
	/** Boolean qui indique la valaidité de la réponse initilatisé à faux par défaut */ 
	private static final boolean VALID_PAR_DEFAUT = false;
	
	/** Réponse qui, par défaut, sera vide */
	private static final String INTITULE_PAR_DEFAUT = " ";
	
	/** Nombre de caratère maximum possible à mettre dans une réponse */
	private static final int NOMBRE_CARACTERES_MAXIMUM = 150;
	
	/** Boolean qui est vrai si la réponse est vraie, faux sinon */
	private boolean validite;
	
	/** Intitulé de la réponse */
	String intituleReponse;
	
	/** Constructeur par défaut : la validité des reponse sont initialisé à 
	 * false et l'intitulé est initialisé avec les valeurs par défaut 
	 */
	public Reponse() {
		validite = false;
		intituleReponse = INTITULE_PAR_DEFAUT;
	}
	
	/** Constructeur avec argument pour initialiser  une réponse 
	 * @param valid  validité de laa réponse 
	 * @param intitule  intitulé de la réponse
	 */
	public Reponse(boolean valid, String intitule) {
		validite = valid;
		intituleReponse=(intitule==null||intitule.length()==0
			       ||intitule.length() > NOMBRE_CARACTERES_MAXIMUM)?
			    	INTITULE_PAR_DEFAUT : intitule;
	}
	
	/** 
	 * Accesseur sur l'intitulé de la réponse courante
	 * @retrun intituleReponse intitulé de la réponse 
	 */
	public String getReponseTexte () {
		return intituleReponse;	
	}
	
	/** 
	 * Modifie l'intitulé de la reponse courante avec l'argument.
	 * Si le nouveau intitulé est invalide, c'est l'intitulé par défaut qui
	 * est attribué
	 * @param nouveauIntitule  nouveau intitulé pour la réponse
	 */
	public void setReponseTexte (String nouveauIntitule) {
		intituleReponse = (nouveauIntitule == null || nouveauIntitule.length() == 0 
				? INTITULE_PAR_DEFAUT : nouveauIntitule);
	}
	
	/** 
	 * Modifie la validité de la question courante avec le boolean en argument
	 * @param nouvelleValidite  boolean qui indique la nouvelle validité
	 */
	public void setValadite (boolean nouvelleValidite) {
		this.validite = nouvelleValidite;	
	}
	
	/**
	 * Acceseur sur la validité d'une réponse
	 * @return un booléen qui correspond à la validité de la réponse courante
	 */
	public boolean getValidite() {
		return this.validite;
	}

}
