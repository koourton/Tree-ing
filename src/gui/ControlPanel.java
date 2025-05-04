package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import trees.AVLTree;
import trees.BST;
import trees.RedBlackTree;

public class ControlPanel extends JPanel {
    private final JComboBox<String> treeTypeComboBox;
    private final BSTWrapper bstWrapper;

    public ControlPanel(BST bst, TreeVisualizer visualizer) {
        this.bstWrapper = new BSTWrapper(bst);

        treeTypeComboBox = new JComboBox<>(new String[]{"BST", "AVL", "Red-Black"});
        JTextField inputField = new JTextField(10);
        JButton insertButton = new JButton("Insert");
        JButton deleteButton = new JButton("Delete");

        treeTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTree = (String) treeTypeComboBox.getSelectedItem();
                switch (Objects.requireNonNull(selectedTree)) {
                    case "BST" -> bstWrapper.bst = new BST();
                    case "AVL" -> bstWrapper.bst = new AVLTree();
                    case "Red-Black" -> bstWrapper.bst = new RedBlackTree();
                }
                visualizer.setBST(bstWrapper.bst);
                visualizer.repaint();
            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int key = Integer.parseInt(inputField.getText());
                    bstWrapper.bst.insert(key);
                    visualizer.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int key = Integer.parseInt(inputField.getText());
                    bstWrapper.bst.deleteKey(key);
                    visualizer.repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid integer.");
                }
            }
        });

        add(treeTypeComboBox);
        add(inputField);
        add(insertButton);
        add(deleteButton);
    }

    private static class BSTWrapper {
        private BST bst;

        public BSTWrapper(BST bst) {
            this.bst = bst;
        }
    }
}