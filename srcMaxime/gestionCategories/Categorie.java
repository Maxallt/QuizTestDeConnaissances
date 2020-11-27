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
     * 
     */
    public Categorie() {
    	nom = null;
    	parDefaut = false;
    	photo = null;
    	listeSousCat = null;
    	sauverEnBase(nom,photo);
    }

    /**
     * 
     * @param nomADonner
     */
    public Categorie(String nomADonner) {
    	nom = nomADonner;
    	parDefaut = false;
    	photo = null;
    	listeSousCat = null;
    	sauverEnBase(nom,photo);
    }

    /**
     * 
     * @param nomADonner
     * @param lienPhoto
     */
    public Categorie(String nomADonner, String lienPhoto) {
    	nom = nomADonner;
    	parDefaut = false;
    	photo = lienPhoto;
    	listeSousCat = null;
    	sauverEnBase(nom,photo);
    }
    
    /**
     * 
     * @param nouveauNom
     */
    public void setNom(String nouveauNom) {
    	nom = nouveauNom;
    }

    /**
     * 
     * @return
     */
    public String getNom() {
    	return nom;
    }
    
    /**
     * 
     * @return
     */
    public String getPhoto() {
		return photo;
	}

    
    
    @Override
	public String toString() {
		return "Categorie [nom=" + nom + ", parDefaut=" + parDefaut + ", photo=" + photo + ", listeSousCat="
				+ listeSousCat + "]";
	}

	/**
     * Méthode qui permet de supprimer la sous-catégorie dans 
     * la base de données
     */
	public void supprimer() {
    	// STUB
    	// TODO suppression en Base de données
    }
	
	public static void sauverEnBase(String nomADonner, String lienPhoto) {
		String sql = "INSERT INTO `categorie`(`nom`, `defaut`, `lienphoto`, `id`) VALUES ('" + nomADonner + "','0','" + lienPhoto + "','1')";
	}

}
