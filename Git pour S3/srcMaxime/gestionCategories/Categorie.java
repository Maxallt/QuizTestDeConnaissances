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
     * M�thode qui permet de supprimer la sous-cat�gorie dans 
     * la base de donn�es
     */
	public void supprimer() {
    	// STUB
    	// TODO suppression en Base de donn�es
    }
	
	public static void sauverEnBase(String nomADonner, String lienPhoto) {
		String sql = "INSERT INTO `categorie`(`nom`, `defaut`, `lienphoto`, `id`) VALUES ('" + nomADonner + "','0','" + lienPhoto + "','1')";
	}

}
