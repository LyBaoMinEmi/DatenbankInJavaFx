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
public class ing_unique {
    private int Id;
    private String Ingredient;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getIngredient() {
        return Ingredient;
    }

    public void setIngredient(String Ingredient) {
        this.Ingredient = Ingredient;
    }

    public ing_unique(int Id, String Ingredient) {
        this.Id = Id;
        this.Ingredient = Ingredient;
    }

    @Override
    public String toString() {
        return Ingredient;
    }
    
    
    
}
