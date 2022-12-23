import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class AgeHistogram extends JFrame {
    int[] ages;

    public AgeHistogram(int[] ages) {
        super("Age Histogram");
        this.ages = ages;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GraphAges graphAges = new GraphAges(ages);
        graphAges.setPreferredSize(new Dimension(1000, 600));
        add(graphAges, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

class GraphAges extends JPanel {
    private static final int PADDING = 30;
    int[] ages;

    public GraphAges(int[] ages) {
        this.ages = ages;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // white background
        g2.setColor(Color.white);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // bottom line
        g2.setColor(Color.black);
        g2.drawLine(0,getHeight()-PADDING, getWidth(), getHeight()-PADDING);

        float maxAge = 0;
        for (int age : ages) {
            if (age > maxAge) {
                maxAge = age;
            }
        }
        float scaleFactor = 0;
        if (maxAge > 0) {
            scaleFactor = ((getHeight()-PADDING*2)/maxAge);
        }

        //System.out.println("scale factor: " + scaleFactor);


        // bottom numbers
        for (int i = 0; i < ages.length; i++) {
            String yString = i+14 + "";
            FontMetrics metrics = g2.getFontMetrics();
            int stringWidth = metrics.stringWidth(yString);
            g2.setColor(Color.black);
            g2.drawString(yString, (getWidth()/ages.length)*i - stringWidth, getHeight()-PADDING/2);
            g2.setColor(Color.red);
            g2.fillRect((getWidth()/ages.length)*i - stringWidth, (int)(getHeight()-PADDING-ages[i]*scaleFactor), stringWidth, (int)(ages[i]*scaleFactor));
        }
    }
}
