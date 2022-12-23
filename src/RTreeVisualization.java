import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RTreeVisualization extends JFrame {
    private static float multiplier = 1;
    private static final Color[] colors = {Color.black, Color.blue, Color.green, Color.orange, Color.yellow, Color.cyan, Color.gray, Color.magenta, Color.gray, Color.pink, new Color(255,215,0), new Color(0, 153, 255), new Color(157, 255, 0), new Color(200, 0, 255), new Color(255, 81,0), new Color(0, 255, 93)};
    RTree tree;

    public RTreeVisualization(RTree rTree) {
        super("RTree Visualization");
        tree = rTree;

        getContentPane().setBackground(Color.WHITE);

        multiplier = 750f/((tree.root.rectangle.upperLeft.coordY - tree.root.rectangle.lowerRight.coordY));

        int width = (int) ((tree.root.rectangle.lowerRight.coordX - tree.root.rectangle.upperLeft.coordX) * multiplier) + 10;
        int height = (int) ((tree.root.rectangle.upperLeft.coordY - tree.root.rectangle.lowerRight.coordY) * multiplier) + 10;
        multiplier = multiplier*0.8f;
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void drawRectangles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // draw rectangles and points
        RNode current = tree.root;
        draw(current, g2d, 0);
    }

    private void draw(RNode current, Graphics2D g2d, int level) {
        if (current.children.get(0).rectangle != null) {
            // draw rectangles
            for (int i = 0; i < current.children.size(); i++) {
                float x = (current.children.get(i).rectangle.upperLeft.coordX * multiplier) + 20;
                float y = (current.children.get(i).rectangle.lowerRight.coordY * multiplier) + 50;
                float width = (current.children.get(i).rectangle.lowerRight.coordX - current.children.get(i).rectangle.upperLeft.coordX) * multiplier;
                float height = (current.children.get(i).rectangle.upperLeft.coordY - current.children.get(i).rectangle.lowerRight.coordY) * multiplier;

                Rectangle2D.Float rect = new Rectangle2D.Float(x, y, width, height);
                g2d.setColor(colors[level]);
                g2d.setStroke(new BasicStroke(1f));
                g2d.draw(rect);

                draw(current.children.get(i), g2d, level+1);
            }
        } else {
            // draw points
            for (int i = 0; i < current.children.size(); i++) {
                float x = (current.children.get(i).point.coordX * multiplier) + 20;
                float y = (current.children.get(i).point.coordY * multiplier) + 50;

                Rectangle2D.Float rect = new Rectangle2D.Float(x, y, 1, 1);
                g2d.setColor(Color.red);
                g2d.setStroke(new BasicStroke(3f));
                g2d.draw(rect);
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        drawRectangles(g);
    }
}
