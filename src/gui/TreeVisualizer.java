package gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import trees.BST;
import trees.RedBlackTree;

public class TreeVisualizer extends JPanel {
    private BST bst;
    private final Random random;
    private final Map<BST.Node, Color> nodeColors;

    public TreeVisualizer(BST bst) {
        this.bst = bst;
        this.random = new Random();
        this.nodeColors = new HashMap<>();
    }

    public void setBST(BST bst) {
        this.bst = bst;
        this.nodeColors.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bst != null) {
            drawTree(g, bst.getRoot(), getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void drawTree(Graphics g, BST.Node node, int x, int y, int hGap) {
        if (node != null) {
            String keyString = String.valueOf(node.key);
            int diameter = Math.max(30, keyString.length() * 10);
            int radius = diameter / 2;

            Color nodeColor;
            if (bst instanceof RedBlackTree) {
                RedBlackTree.RedBlackNode rbNode = (RedBlackTree.RedBlackNode) node;
                nodeColor = rbNode.color == RedBlackTree.RED ? Color.RED : Color.BLACK;
            } else {
                nodeColor = nodeColors.computeIfAbsent(node, _ -> new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            }

            g.setColor(nodeColor);
            g.fillOval(x - radius, y - radius, diameter, diameter);

            int brightness = (int) Math.sqrt(
                    nodeColor.getRed() * nodeColor.getRed() * 0.241 +
                            nodeColor.getGreen() * nodeColor.getGreen() * 0.691 +
                            nodeColor.getBlue() * nodeColor.getBlue() * 0.068
            );
            g.setColor(brightness < 130 ? Color.WHITE : Color.BLACK);
            g.drawString(keyString, x - g.getFontMetrics().stringWidth(keyString) / 2, y + g.getFontMetrics().getAscent() / 2);

            if (node.left != null) {
                int childX = x - hGap;
                int childY = y + 50;
                drawLineFromEdge(g, x, y, childX, childY, radius);
                drawTree(g, node.left, childX, childY, hGap / 2);
            }

            if (node.right != null) {
                int childX = x + hGap;
                int childY = y + 50;
                drawLineFromEdge(g, x, y, childX, childY, radius);
                drawTree(g, node.right, childX, childY, hGap / 2);
            }
        }
    }

    private void drawLineFromEdge(Graphics g, int x1, int y1, int x2, int y2, int radius) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int startX = (int) (x1 + radius * Math.cos(angle));
        int startY = (int) (y1 + radius * Math.sin(angle));
        int endX = (int) (x2 - radius * Math.cos(angle));
        int endY = (int) (y2 - radius * Math.sin(angle));
        g.setColor(Color.BLACK);
        g.drawLine(startX, startY, endX, endY);
    }
}