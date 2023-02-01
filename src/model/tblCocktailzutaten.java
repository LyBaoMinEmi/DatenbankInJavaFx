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
public class tblCocktailzutaten {
    private int CocktailZutatenNr;
    private int CocktailNr;
    private int ZutatenNr;
    private Float Menge;
    private int EinheitenNr;

    public tblCocktailzutaten(int CocktailZutatenNr, int CocktailNr, int ZutatenNr, Float Menge, int EinheitenNr) {
        this.CocktailZutatenNr = CocktailZutatenNr;
        this.CocktailNr = CocktailNr;
        this.ZutatenNr = ZutatenNr;
        this.Menge = Menge;
        this.EinheitenNr = EinheitenNr;
    }

    public int getCocktailZutatenNr() {
        return CocktailZutatenNr;
    }

    public void setCocktailZutatenNr(int CocktailZutatenNr) {
        this.CocktailZutatenNr = CocktailZutatenNr;
    }

    public int getCocktailNr() {
        return CocktailNr;
    }

    public void setCocktailNr(int CocktailNr) {
        this.CocktailNr = CocktailNr;
    }

    public int getZutatenNr() {
        return ZutatenNr;
    }

    public void setZutatenNr(int ZutatenNr) {
        this.ZutatenNr = ZutatenNr;
    }

    public Float getMenge() {
        return Menge;
    }

    public void setMenge(Float Menge) {
        this.Menge = Menge;
    }

    public int getEinheitenNr() {
        return EinheitenNr;
    }

    public void setEinheitenNr(int EinheitenNr) {
        this.EinheitenNr = EinheitenNr;
    }
    
    
    
    
    
    
}
