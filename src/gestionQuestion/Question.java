package gestionQuestion;

import java.util.ArrayList;

public class Question {
	
	/** Question qui, par défaut, sera vide */
	private static final String PAR_DEFAUT = "Question vide !";
	
	/** L'énoncé de la question posé */
	private String titreQuestion;
	
	private String idCat;
	
	private String idSousCat;
	
	private String difficulte;
	
	/** 
	 * Liste des réponses 
	 * La première valeur est la réponse vraie les suivantes, jusqu'à 5, sont fausses
	 */
	private ArrayList<String> reponses = new ArrayList<>();
	
	/**
	 *  Constructeur qui initialise les différenrs attributs
	 *  d'une question posé dans le quiz
	 * @param titreQuestion  intitule de la question
	 * @param intitules  une ArrayList qui contient les intitules des reponses
	 */
	public Question(String titreQuestion, 
			        ArrayList<String> reponsesAcreer, String sousCat, String cat, String difficulte) {
		
		//TODO ajouter une vérification de l'ArrayList avec au moins 2 éléments et max 5
		
		
		//Tant que l'intitulé de la question n'est pas null
		//TODO Mettre un pop up d'erreur titre question vide
		this.titreQuestion=(titreQuestion==null || titreQuestion.length()==0)? 
				            PAR_DEFAUT : titreQuestion;
		/* 
		 * Créer autant de réponses qu'il y en a dans l'ArrayList intitules
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
	 * @return titreQuestion  intitulé de la question 
	 */
	public String getTitreQuestion() {
		return titreQuestion;
	}
	
	
	
	public String getDifficulte() {
		return difficulte;
	}

	/**
	 * TODO à vérifier si vraiment utile
	 * Modifie le titre de la question courante 
	 * @param nouvelleQuestion  nouvel intitulé de la question
	 */
	public void setTitreQuestion(String nouvelleQuestion) {
		titreQuestion = nouvelleQuestion;
	}
	
	
	/* ----------------------------------*/
	/*	ACCESSEURS SUR L'INTITULE REPONSE	*/
	
	/**
	 * Accesseur sur l'intitulé de la reponse juste
	 * @return une chaine de caracteres correspondant à l'intitulé de la
	 */
	public String getReponseJuste () {
		return reponses.get(0);
	}
	
	/**
	 * Accesseur sur l'intitulé des reponses
	 * @return une ArrayList des réponses
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