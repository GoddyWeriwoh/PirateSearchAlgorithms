import com.sun.source.doctree.ReferenceTree;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DecksRtrees {


    public static void executeDeckRTees(RTree rTree){
        // Method variables
        boolean leave = false;
        String option;
        String name;
        String aux;
        float x, y;
        Point p1, p2;

        do {
            option = Menu.deckMenu();
            // Executing the option entered by the user
            switch (option) {
                case "A" -> {
                    System.out.print("Enter the treasure's name: ");
                    name = new Scanner(System.in).nextLine();
                    System.out.print("Enter the X coordinate of the treasure's position: ");
                    x = Float.parseFloat(new Scanner(System.in).nextLine());
                    System.out.print("Enter the Y coordinate of the treasure's position: ");
                    y = Float.parseFloat(new Scanner(System.in).nextLine());
                    Point point = new Point(x, y);
                    rTree.insert(rTree.root, new RNode(name, point, null, null, null));
                    System.out.println("\nThe treasure was correctly added to the deck.");
                }
                case "B" -> {
                    System.out.println("B");
                }
                case "C" -> {
                    System.out.println("\nGenerating the deck representation...");
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new RTreeVisualization(rTree).setVisible(true);
                        }
                    });
                }
                case "D" -> {
                    System.out.print("Enter the rectangle's first point (X,Y): ");
                    aux = new Scanner(System.in).nextLine();
                    String[] line = aux.split(",");
                    p1 = new Point(Float.parseFloat(line[0]), Float.parseFloat(line[1]));
                    System.out.print("Enter the rectangle's second point (X,Y): ");
                    aux = new Scanner(System.in).nextLine();
                    line = aux.split(",");
                    p2 = new Point(Float.parseFloat(line[0]), Float.parseFloat(line[1]));
                    Rectangle rectangle = rTree.calculateRectangle(p1, p2);
                    ArrayList<RNode> result = new ArrayList<>();
                    rTree.searchByArea(rectangle, rTree.root, result);

                    System.out.println("\n" + result.size() + " treasures were found in this area:\n");
                    for (RNode node : result) {
                        System.out.println("\t" + node.name + " (" + node.point.coordX + ", " + node.point.coordY + ")");
                    }
                }
                case "E" -> {
                    System.out.print("Enter the number of treasures to find: ");
                    int numTreasures = Integer.parseInt(new Scanner(System.in).nextLine());
                    System.out.print("Enter the point to search around (X,Y): ");
                    aux = new Scanner(System.in).nextLine();
                    String[] line = aux.split(",");
                    p1 = new Point(Float.parseFloat(line[0]), Float.parseFloat(line[1]));
                    ArrayList<RNode> result = rTree.searchByProximity(p1, numTreasures);

                    System.out.println("\nThe " + numTreasures + " nearest treasures to this point are:\n");
                    for (RNode node : result) {
                        System.out.println("\t" + node.name + " (" + node.point.coordX + ", " + node.point.coordY + ")");
                    }
                }
                case "F" -> leave = true;
            }
        } while(!leave);



    }

}
