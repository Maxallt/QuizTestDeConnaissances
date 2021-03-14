/* ConnexionBD 02/2021 
 * connexion à la base de donnée
 * */
package gestionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnexionBD va permettre de retourner l'instance de la base de donnéee
 * La créer si elle n'existe pas (si on n'est pas connecté)
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
     * Méthode qui va nous retourner notre instance
     * et la créer si elle n'existe pas...
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