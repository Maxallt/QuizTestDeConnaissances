/*
 * Categorie.java							 31/10/2020
 * Classe qui regoupera les cat�gories de l'application
 */

package gestionCategories;

import java.util.ArrayList;

/**
 * Classe instanciable qui va g�rer les cat�gories et ses attributs.
 * Elle permettra de lier les sous-cat�gories dans un �l�ment plus 
 * g�n�ral.
 * @author Alliot Maxime
 *
 */
public class Categorie {
	
	/** Nom de la cat�gorie */
    private String nom;
    
    /**
     * Attribut qui va d�signer si la cat�gorie est cr��e 
     * par d�faut ou non et la prot�ge de modifications dans 
     * ce cas l� 
     */
    private boolean parDefaut;
    
    /** Lien de la photo qui va d�crire la cat�gorie */
    private String photo;
    
    /** Nombre maximum de caract�re pour le nom d'une cat�gorie */
    public static final int NOMBRE_CARACTERE_MAX = 20;
    
    /** 
     * Liste qui va contenir toutes les sous-cat�gorie qu'inclut 
     * cette cat�gorie 
     */
    private ArrayList<SousCategorie> listeSousCat;

    /**
     * Constructeur par d�faut de cat�gorie
     */
    public Categorie() {
    	nom = null;
    	parDefaut = false;
    	photo = null;
    	listeSousCat = null;
    }

    /**
     * Constructeur de cat�gorie
     * @param nomADonner Nom de la Cat�gorie
     */
    public Categorie(String nomADonner) {
    	nom = nomADonner;
    	parDefaut = false;
    	photo = null;
    	listeSousCat = null;
    }

    /**
     * Constructeur de cat�gorie
     * @param nomADonner Nom de la Cat�gorie
     * @param lienPhoto Lien vers la photo de Cat�gorie
     */
    public Categorie(String nomADonner, String lienPhoto) {
    	nom = nomADonner;
    	parDefaut = false;
    	photo = lienPhoto;
    	listeSousCat = null;
    }
    
    /**
     * Setter pour modifier le nom de la Cat�gorie
     * @param nouveauNom
     */
    public void setNom(String nouveauNom) {
    	nom = nouveauNom;
    }

    /**
     * Getter pour r�cup�rer le nom de la cat�gorie
     * @return nom de la cat�gorie
     */
    public String getNom() {
    	return nom;
    }
    
    /**
     * Getter pour r�cup�rer le lien vers la photo de la cat�gorie
     * @return lien vers la photo de la cat�gorie
     */
    public String getPhoto() {
		return photo;
	}

    
    /**
     * ToString pour effectuer des tests
     */
    @Override
	public String toString() {
		return "Categorie [nom=" + nom + ", parDefaut=" + parDefaut + ", photo=" + photo + ", listeSousCat="
				+ listeSousCat + "]";
	}

}
