package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author user
 */
public class DAO {
    //static Global, muss nicht class.executeDML
    //DAO dao = new DAO;
    //dao.execute...
    public static void executeDML (String sql) {
        Connection con;
        
        try {
            con = DBConnector.connect();
            con.createStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
