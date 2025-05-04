import javax.swing.*;
import java.awt.*;
import gui.TreeVisualizer;
import gui.ControlPanel;
import trees.BST;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tree Visualization");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            BST bst = new BST();
            TreeVisualizer visualizer = new TreeVisualizer(bst);
            ControlPanel controlPanel = new ControlPanel(bst, visualizer);

            frame.setLayout(new BorderLayout());
            frame.add(controlPanel, BorderLayout.SOUTH);
            frame.add(visualizer, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}