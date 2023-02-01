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
public class cocktailFull {

    

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

    public String getGlass() {
        return Glass;
    }

    public void setGlass(String Glass) {
        this.Glass = Glass;
    }

    public Double getAlcohol() {
        return Alcohol;
    }

    public void setAlcohol(Double Alcohol) {
        this.Alcohol = Alcohol;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String Group) {
        this.Group = Group;
    }
    public cocktailFull() {
        this.Id = 3000;
        this.Name = "";
        this.Glass = "";
        this.Alcohol = 0.00;
        this.Group = "";
        this.Recipe = "";
    }

    public cocktailFull(int Id, String Name, String Glass, Double Alcohol, String Group, String Recipe) {
        this.Id = Id;
        this.Name = Name;
        this.Glass = Glass;
        this.Alcohol = Alcohol;
        this.Group = Group;
        this.Recipe = Recipe;
    }
    private int Id;
    private String Name;
    private String Glass;
    private Double Alcohol;
    private String Group;
    private String Recipe;


    
}
