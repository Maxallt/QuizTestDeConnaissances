package gestionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD{

    /**
     * URL de connexion
     */
    private static String url = "jdbc:mysql://localhost/quizztestconnaissances?serverTimezone=UTC";
    /**
     * Nom du user
     */
    private static String user = "root";
    /**
     * Mot de passe du user
     */
    private static String passwd = "root";
    /**
     * Objet Connexion
     */
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