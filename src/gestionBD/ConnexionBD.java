/* ConnexionBD 02/2021 
 * connexion � la base de donn�e
 * */
package gestionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnexionBD va permettre de retourner l'instance de la base de donn�ee
 * La cr�er si elle n'existe pas (si on n'est pas connect�)
 * @author ninotizien
 *
 */
public class ConnexionBD{
	
	/** INFOS SUR LA  BD*/

    /** URL de connexion  */
    private static String url = "jdbc:mysql://localhost/quizztestconnaissances?serverTimezone=UTC";
    
    /** Nom du user */
    private static String user = "root";
    
    /** Mot de passe du user */
    private static String passwd = "root";
    
    /** Objet Connexion */
    private static Connection connect;
    
    
    /**
     * M�thode qui va nous retourner notre instance
     * et la cr�er si elle n'existe pas...
     * @return
     * @throws SQLException 
     */
    public static Connection getInstance() throws SQLException{
        if(connect == null){
            try {
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }        
        return connect;    
    }    
}