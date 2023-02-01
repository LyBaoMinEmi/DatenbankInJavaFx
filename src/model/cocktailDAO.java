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

public class cocktailDAO {
    public int getMaxCocktailNr () {
        int max = 0;
        Connection con;
        
        try {
            con = DBConnector.connect();
            
            String sql = "SELECT MAX(CocktailNr) as max FROM tblCocktail";
                   
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            if (rs.next()) {
                
                     max = rs.getInt("max");
                //System.out.println("Cocktailnr"+max);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return max;
    }
    public int getMaxCocktailZutatenNr () {
        int max = 0;
        Connection con;
        
        try {
            con = DBConnector.connect();
            
            String sql = "SELECT MAX(CocktailZutatenNr) as max FROM tblCocktailzutaten";
                   
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            if (rs.next()) {
                
                     max = rs.getInt("max");
                //System.out.println("Cocktailnr"+max);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return max;
    }
    
    public ObservableList<cocktail> getCocktailsFromSearch (String CocktailName,String Ingredient) {
        ObservableList<cocktail> cocktailList = FXCollections.observableArrayList();
        Connection con;
        String text = "";
        if(Ingredient.equals("") && (!CocktailName.equals("")) ){
            text=" WHERE c.Cocktail LIKE '%" + CocktailName + "%' ";            
        }
        else if (!Ingredient.equals("") && (CocktailName.equals("")) )
        {
                    text=" WHERE z.zutat LIKE '%" + Ingredient + "%'";

        }
        else if  (Ingredient.equals("") && (CocktailName.equals("")) )
        {
        text = " WHERE z.zutat LIKE '%" + Ingredient + "%' "+
                " AND c.Cocktail LIKE '%" + CocktailName + "%' ";
        }
               
        try {
            con = DBConnector.connect();
           
            String sql = "SELECT DISTINCT c.CocktailNr, c.Cocktail, c.Zubereitung "
                    +"FROM tblCocktail c "
                    + "LEFT JOIN tblCocktailZutaten cz ON c.CocktailNr = cz.Cocktailnr "
                    + "LEFT JOIN tblzutat z ON cz.Zutatennr = z.Zutatennr "
                    + text                   
                    +" ORDER BY c.Cocktail ASC";
            
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while (rs.next()) {
                cocktailList.add(new cocktail(
                        rs.getInt("CocktailNr"),
                     rs.getString("Cocktail"),
                     rs.getString("Zubereitung")                                           
                ));
                //System.out.println(rs.getString("Cocktail"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return cocktailList;
    }
    
    public cocktailFull getCocktailsFull (int Id) {
        cocktailFull selectedCocktailFull = new cocktailFull();       
        
        ObservableList<cocktailFull> cocktailList = FXCollections.observableArrayList();
        Connection con;
        
        try {
            con = DBConnector.connect();
            String sql = "SELECT c.CocktailNr, c.Cocktail, g.Glas, c.Alkoholgehalt,gr.Gruppe, c.Zubereitung "
                +"FROM tblCocktail c "
                    + "LEFT JOIN tblglas g ON c.GlasNr = g.GlasNr "
                    + "LEFT JOIN tblgruppe gr ON c.GruppeNr = gr.gruppeNr "
                    +"WHERE c.Cocktailnr = "+Id+"";
            
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            if (rs.next()) {
                selectedCocktailFull = new cocktailFull(
                        rs.getInt("CocktailNr"),
                        rs.getString("Cocktail"),
                        rs.getString("Glas"),
                        rs.getDouble("Alkoholgehalt"),
                        rs.getString("Gruppe"),
                        rs.getString("Zubereitung")                                           
                );
                System.out.println(rs.getString("Cocktail"));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
              
        return selectedCocktailFull;  
                
            
    }
    
    
    
}
