/*
 * Categorie.java							 31/10/2020
 * Classe qui regoupera les catégories de l'application
 */

package gestionCategories;

import java.util.ArrayList;

/**
 * Classe instanciable qui va gérer les catégories et ses attributs.
 * Elle permettra de lier les sous-catégories dans un élément plus 
 * général.
 * @author Alliot Maxime
 *
 */
public class Categorie {
	
	/** Nom de la catégorie */
    private String nom;
    
    /**
     * Attribut qui va désigner si la catégorie est créée 
     * par défaut ou non et la protège de modifications dans 
     * ce cas là 
     */
    private boolean parDefaut;
    
    /** Lien de la photo qui va décrire la catégorie */
    private String photo;
    
    /** Nombre maximum de caractère pour le nom d'une catégorie */
    public static final int NOMBRE_CARACTERE_MAX = 20;
    
    /** 
     * Liste qui va contenir toutes les sous-catégorie qu'inclut 
     * cette catégorie 
     */
    private ArrayList<SousCategorie> listeSousCat;

    /**
     * Constructeur par défaut de catégorie
     */
    public Categorie() {
    	nom = null;
    	parDefaut = false;
    	photo = null;
    	listeSousCat = null;
    }

    /**
     * Constructeur de catégorie
     * @param nomADonner Nom de la Catégorie
     */
    public Categorie(String nomADonner) {
    	nom = nomADonner;
    	parDefaut = false;
    	photo = null;
    	listeSousCat = null;
    }

    /**
     * Constructeur de catégorie
     * @param nomADonner Nom de la Catégorie
     * @param lienPhoto Lien vers la photo de Catégorie
     */
    public Categorie(String nomADonner, String lienPhoto) {
    	nom = nomADonner;
    	parDefaut = false;
    	photo = lienPhoto;
    	listeSousCat = null;
    }
    
    /**
     * Setter pour modifier le nom de la Catégorie
     * @param nouveauNom
     */
    public void setNom(String nouveauNom) {
    	nom = nouveauNom;
    }

    /**
     * Getter pour récupérer le nom de la catégorie
     * @return nom de la catégorie
     */
    public String getNom() {
    	return nom;
    }
    
    /**
     * Getter pour récupérer le lien vers la photo de la catégorie
     * @return lien vers la photo de la catégorie
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
