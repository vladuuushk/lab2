/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.laba2;


import controller.ArmyController;
import javax.swing.SwingUtilities;

/**
 *
 * @author vladshuvaev
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArmyController controller = new ArmyController();
            controller.launchApplication();
        });
    }
}
