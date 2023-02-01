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
public class ingredients {

    

    public String getIngName() {
        return IngName;
    }

    public void setIngName(String IngName) {
        this.IngName = IngName;
    }

    public Float getQuantity() {
        return Quantity;
    }

    public void setQuantity(Float Quantity) {
        this.Quantity = Quantity;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }


    public int getIngId() {
        return IngId;
    }

    public void setIngId(int IngId) {
        this.IngId = IngId;
    }

    public ingredients(int CocktailIngId, int IngId, String IngName, Float Quantity, String Unit) {
        this.CocktailIngId = CocktailIngId;
        this.IngId = IngId;
        this.IngName = IngName;
        this.Quantity = Quantity;
        this.Unit = Unit;
    }

    public int getCocktailIngId() {
        return CocktailIngId;
    }

    public void setCocktailIngId(int CocktailIngId) {
        this.CocktailIngId = CocktailIngId;
    }
    private int CocktailIngId;
    private int IngId;
    private String IngName;
    private Float Quantity;
    private String Unit;

    @Override
    public String toString() {
        return IngName;
    }
    
    
    
}
