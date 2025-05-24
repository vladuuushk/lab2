/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.gearFactories;

/**
 *
 * @author vladshuvaev
 */
public class GreyMountainsGearFactory implements OrkGearFactory {
    @Override 
    public String createWeapon() {
        return "Длинный меч";
    }
    @Override 
    public String createArmor() {
        return "Чешуйчатая броня";
    }
    @Override 
    public String createBanner() {
        return "Знамя с Драконом";
    }
}
