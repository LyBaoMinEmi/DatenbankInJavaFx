/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBConnector;

/**
 *
 * @author baota
 */
public class unitDAO {
    public ObservableList<units> getUnits () {
        ObservableList<units> unitList = FXCollections.observableArrayList();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT EinheitenNr, Einheit " 
                    + " FROM tbleinheiten"
                    +" ORDER BY Einheit ASC";
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                unitList.add(new units(
                     rs.getInt("EinheitenNr"),
                     rs.getString("Einheit")                        
                ));
                //System.out.println(rs.getString("Einheit"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return unitList;
    }
    
    public int getUnitId (String unit) {
        int id = 0;
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT EinheitenNr " 
                    + " FROM tbleinheiten WHERE Einheit LIKE '%"+unit+"%'";
                   
            ResultSet rs = con.createStatement().executeQuery(sql);
            
             if (rs.next()) {               
                     id = rs.getInt("EinheitenNr");}        
           
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return id;
    }
    
}
