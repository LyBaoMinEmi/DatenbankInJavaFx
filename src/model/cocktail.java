/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author baota
 */
public class cocktail {    
    private int Id;
    private String Name;
    private String Recipe;
  

    public cocktail(int Id, String Name, String Recipe) {
        this.Id = Id;
        this.Name = Name;
        this.Recipe = Recipe;
    } 
   
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
        

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRecipe() {
        return Recipe;
    }

    public void setRecipe(String Recipe) {
        this.Recipe = Recipe;
    } 
    
    
    
}
