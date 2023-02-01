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
public class units {

    public units(int UnitId, String Unit) {
        this.UnitId = UnitId;
        this.Unit = Unit;
    }

    public int getUnitId() {
        return UnitId;
    }

    public void setUnitId(int UnitId) {
        this.UnitId = UnitId;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String Unit) {
        this.Unit = Unit;
    }
    private int UnitId;
    private String Unit;
    
    @Override
    public String toString() {
        return Unit;
    }

    
    
}
