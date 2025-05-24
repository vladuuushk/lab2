/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.builders;

import model.Ork;

/**
 *
 * @author vladshuvaev
 */
public class GreyMountainsOrkBuilder extends OrkBuilder {

    public GreyMountainsOrkBuilder() {
        super(Ork.Tribe.GREY_MOUNTAINS);
    }

    @Override
    protected void generateBaseAttributes() {
        strength = random.nextInt(61) + 40;  
        agility = random.nextInt(61) + 40;
        intelligence = random.nextInt(26) + 25;
        health = random.nextInt(101) + 100;
    }
}