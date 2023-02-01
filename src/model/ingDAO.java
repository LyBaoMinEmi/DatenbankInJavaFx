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
public class ingDAO {
    
    public ObservableList<ingredients> getIngredients (int CocktailNr) {
        ObservableList<ingredients> IngredientsList = FXCollections.observableArrayList();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT cz.CocktailZutatenNr, cz.Zutatennr, z.Zutat, cz.Menge, e.Einheit "
                    +"FROM tblCocktail c, tblCocktailZutaten cz, tblzutat z, tblEinheiten e "
                    +"WHERE c.CocktailNr = cz.Cocktailnr and cz.Zutatennr = z.Zutatennr"
                    +" and cz.EinheitenNr = e.EinheitenNr and c.CocktailNr = "+ CocktailNr+"";
            ResultSet rs = con.createStatement().executeQuery(sql);
         
            while (rs.next()) {
                IngredientsList.add(new ingredients(  
                        rs.getInt("CocktailZutatenNr"),
                        rs.getInt("Zutatennr"),
                     rs.getString("Zutat"),
                     rs.getFloat("Menge"),
                     rs.getString("Einheit")
                             ));
                //System.out.println("zutaten: "+rs.getString("Zutat"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return IngredientsList;
    }
    
     public ObservableList<ing_unique> getUniqueIngredients () {
        ObservableList<ing_unique> IngList = FXCollections.observableArrayList();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT ZutatenNr, Zutat " 
                    + " FROM tblZutat"
                    +" ORDER BY Zutat ASC";
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                IngList.add(new ing_unique(
                        rs.getInt("ZutatenNr"),
                     rs.getString("Zutat")                        
                ));
                //System.out.println(rs.getString("Einheit"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return IngList;
    }
    
}
