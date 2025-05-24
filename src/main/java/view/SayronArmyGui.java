/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.ArmyController;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import model.Ork;
import model.Ork.OrkType;
import model.Ork.Tribe;

public class SayronArmyGui extends JFrame {
    private final ArmyController controller;
    private final Map<Tribe, DefaultMutableTreeNode> tribeNodes = new HashMap<>();
    private final DefaultTreeModel treeModel;
    private final JTree armyTree;
    private final JPanel infoPanel;
    
    private final Color backgroundColor = Color.WHITE;               
    private final Color panelColor = Color.lightGray;      
    private final Color textColor = Color.BLACK;                    
    private final Color accentColor = Color.BLUE;      
    private final Color progressBarColor = Color.GREEN;

    public SayronArmyGui(ArmyController controller) {
        super("Армия Саурона");
        this.controller = controller;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundColor);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Армия Мордора");
        for (Tribe tribe : Tribe.values()) {
            DefaultMutableTreeNode tribeNode = new DefaultMutableTreeNode(tribe.toString());
            tribeNodes.put(tribe, tribeNode);
            root.add(tribeNode);
        }
        
        treeModel = new DefaultTreeModel(root);
        armyTree = new JTree(treeModel);
        styleTreeComponents();
        
        infoPanel = new JPanel();
        styleInfoPanel();

        JPanel controlPanel = createControlPanel();

        add(new JScrollPane(armyTree), BorderLayout.WEST);
        add(new JScrollPane(infoPanel), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void styleTreeComponents() {
        armyTree.setBackground(panelColor);
        armyTree.setForeground(textColor);
        armyTree.setFont(new Font("Serif", Font.PLAIN, 20));
        UIManager.put("Tree.selectionBackground", accentColor);
        UIManager.put("Tree.selectionForeground", Color.WHITE);
        armyTree.addTreeSelectionListener(e -> updateInfoPanel());
    }

    private void styleInfoPanel() {
        infoPanel.setLayout(new GridLayout(0, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setBackground(panelColor);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(panelColor);

        JComboBox<Tribe> tribeCombo = new JComboBox<>(Tribe.values());
        JComboBox<OrkType> typeCombo = new JComboBox<>(OrkType.values());
        JButton createButton = new JButton("Создать орка");

        styleComboBox(tribeCombo);
        styleComboBox(typeCombo);
        styleButton(createButton);

        createButton.addActionListener(e -> {
            Tribe tribe = (Tribe) tribeCombo.getSelectedItem();
            OrkType type = (OrkType) typeCombo.getSelectedItem();
            controller.createOrk(tribe, type);
        });

        JLabel tribeLabel = new JLabel("Племя:");
        tribeLabel.setForeground(textColor);
        panel.add(tribeLabel);
        panel.add(tribeCombo);
        
        JLabel typeLabel = new JLabel("Тип:");
        typeLabel.setForeground(textColor);
        panel.add(typeLabel);
        panel.add(typeCombo);
        
        panel.add(createButton);

        return panel;
    }

    private void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(textColor);
    }

    private void styleButton(JButton button) {
        button.setBackground(accentColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
    }

    public void addOrkToDisplay(Ork ork) {
        DefaultMutableTreeNode orkNode = new DefaultMutableTreeNode(ork.getName());
        tribeNodes.get(ork.getTribe()).add(orkNode);
        treeModel.reload();
        
        TreePath path = new TreePath(orkNode.getPath());
        armyTree.setSelectionPath(path);
        armyTree.scrollPathToVisible(path);
    }

    private void updateInfoPanel() {
        infoPanel.removeAll();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) armyTree.getLastSelectedPathComponent();
        
        if (selectedNode != null && !selectedNode.isRoot()) {
            String orkName = selectedNode.getUserObject().toString();
            Ork ork = controller.getOrkByName(orkName);
            
            if (ork != null) {
                addInfoRow("Имя:", ork.getName());
                addInfoRow("Племя:", ork.getTribe().toString());
                addInfoRow("Тип:", ork.getType().toString());
                addInfoRow("Оружие:", ork.getWeapon());
                addInfoRow("Броня:", ork.getArmor());
                addInfoRow("Знамя:", ork.getBanner() != null ? ork.getBanner() : "нет");
                
                addCustomProgressBar("Сила:", ork.getStrength());
                addCustomProgressBar("Ловкость:", ork.getAgility());
                addCustomProgressBar("Интеллект:", ork.getIntelligence());
                addCustomProgressBar("Здоровье:", ork.getHealth());
            }
        }
        
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    private void addInfoRow(String label, String value) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(textColor);
        lbl.setFont(new Font("Serif", Font.BOLD, 20));
        
        JLabel val = new JLabel(value);
        val.setForeground(textColor);
        val.setFont(new Font("Serif", Font.PLAIN, 20));
        
        infoPanel.add(lbl);
        infoPanel.add(val);
    }

    private void addCustomProgressBar(String label, int value) {
        JLabel lbl = new JLabel(label);
        lbl.setForeground(textColor);
        lbl.setFont(new Font("Serif", Font.BOLD, 20));
        infoPanel.add(lbl);

        JPanel progressPanel = new JPanel(new BorderLayout(5, 0));
        progressPanel.setBackground(panelColor);
        
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(Math.min(value, 100));
        progressBar.setStringPainted(false);
        progressBar.setForeground(progressBarColor);
        progressBar.setBackground(Color.WHITE);
        
        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setForeground(textColor);
        valueLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        
        progressPanel.add(progressBar, BorderLayout.CENTER);
        progressPanel.add(valueLabel, BorderLayout.EAST);
        
        infoPanel.add(progressPanel);
    }
}