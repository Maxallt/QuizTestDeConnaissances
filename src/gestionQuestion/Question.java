package gestionQuestion;

import java.util.ArrayList;

public class Question {
	
	/** Question qui, par d�faut, sera vide */
	private static final String PAR_DEFAUT = "Question vide !";
	
	/** L'�nonc� de la question pos� */
	private String titreQuestion;
	
	private String idCat;
	
	private String idSousCat;
	
	private String difficulte;
	
	/** 
	 * Liste des r�ponses 
	 * La premi�re valeur est la r�ponse vraie les suivantes, jusqu'� 5, sont fausses
	 */
	private ArrayList<String> reponses = new ArrayList<>();
	
	/**
	 *  Constructeur qui initialise les diff�renrs attributs
	 *  d'une question pos� dans le quiz
	 * @param titreQuestion  intitule de la question
	 * @param intitules  une ArrayList qui contient les intitules des reponses
	 */
	public Question(String titreQuestion, 
			        ArrayList<String> reponsesAcreer, String sousCat, String cat, String difficulte) {
		
		//TODO ajouter une v�rification de l'ArrayList avec au moins 2 �l�ments et max 5
		
		
		//Tant que l'intitul� de la question n'est pas null
		//TODO Mettre un pop up d'erreur titre question vide
		this.titreQuestion=(titreQuestion==null || titreQuestion.length()==0)? 
				            PAR_DEFAUT : titreQuestion;
		/* 
		 * Cr�er autant de r�ponses qu'il y en a dans l'ArrayList intitules
		 * la 1ere est vrai, les autres sont fausses
		*/
		for (String intituleReponse : reponsesAcreer) {
			reponses.add(intituleReponse);
		}
		idSousCat = sousCat;
		idCat = cat;
		this.difficulte = difficulte;
	}
	
	/*	ACCESSEURS & SETTERS SUR QUESTION	*/
	
	/**
	 * Accesseur sur le titre de la question courrante
	 * @return titreQuestion  intitul� de la question 
	 */
	public String getTitreQuestion() {
		return titreQuestion;
	}
	
	
	
	public String getDifficulte() {
		return difficulte;
	}

	/**
	 * TODO � v�rifier si vraiment utile
	 * Modifie le titre de la question courante 
	 * @param nouvelleQuestion  nouvel intitul� de la question
	 */
	public void setTitreQuestion(String nouvelleQuestion) {
		titreQuestion = nouvelleQuestion;
	}
	
	
	/* ----------------------------------*/
	/*	ACCESSEURS SUR L'INTITULE REPONSE	*/
	
	/**
	 * Accesseur sur l'intitul� de la reponse juste
	 * @return une chaine de caracteres correspondant � l'intitul� de la
	 */
	public String getReponseJuste () {
		return reponses.get(0);
	}
	
	/**
	 * Accesseur sur l'intitul� des reponses
	 * @return une ArrayList des r�ponses
	 */
	public ArrayList<String> getReponses () {
		return reponses;
	}

	public String getIdCat() {
		return idCat;
	}

	public String getIdSousCat() {
		return idSousCat;
	}

}