/**
 * 
 */
package interfacesQuizTestConnaissances;

/**
 * @author nikos
 */
public class Historique {
	 
	   private Long numPartie;
	   private String date;
	   private String heure;
	   private String categorie;
	   private String sousCategorie;
	   private String difficulte;
	   private String score;
	   
	   public Historique(Long numPartie, String date, String heure, //
	           String categorie, String sousCategorie, String difficulte, String score) {
	       this.numPartie = numPartie;
	       this.date = date;
	       this.heure = heure;
	       this.categorie = categorie;
	       this.sousCategorie = sousCategorie;
	       this.difficulte = difficulte;
	       this.score = score;
	   }

	public Long getNumPartie() {
		return numPartie;
	}

	public void setNumPartie(Long numPartie) {
		this.numPartie = numPartie;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getSousCategorie() {
		return sousCategorie;
	}

	public void setSousCategorie(String sousCategorie) {
		this.sousCategorie = sousCategorie;
	}

	public String getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(String difficulte) {
		this.difficulte = difficulte;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	 
	  
	 
	}
