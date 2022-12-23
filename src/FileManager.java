import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class FileManager {

     public static Graph readGraph() {
        Graph graph = new Graph();

        try {
            File myObj = new File("Graphs/graphXXS.paed");
            Scanner myReader = new Scanner(myObj);
            boolean isVertex = false;

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                try {
                    if (isVertex) {
                        String[] vertex = data.split(",");
                        Place nPlace = new Place(Integer.parseInt(vertex[0]), vertex[1], vertex[2], graph.nodes.size());
                        graph.ids.add(Integer.parseInt(vertex[0]));
                        graph.addVertex(nPlace);
                    }
                    else {
                        String[] edge = data.split(",");
                        graph.addEdge(graph.findPlace(Integer.parseInt(edge[0])), graph.findPlace(Integer.parseInt(edge[1])), Float.parseFloat(edge[2]));
                    }
                }
                catch (Exception e) {
                    isVertex = !isVertex;
                }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return graph;
    }



    public static BinaryTree readTree() {
        BinaryTree tree = new BinaryTree();
        tree.nodes = new ArrayList<>();

        try {
            File myObj = new File("Trees/treeXXS.paed");
            Scanner myReader = new Scanner(myObj);
            int total = Integer.parseInt(myReader.nextLine());
            for (int i = 0; i < total; i++) {
                String data = myReader.nextLine();
                String[] line = data.split(",");
                //tree.addNode(Long.parseLong(line[1]), line[0]);
                tree.AVLAddNode(Long.parseLong(line[1]), line[0]);
                tree.nodes.add(new TreeList(line[0], Long.parseLong(line[1])));
            }
            System.out.println();
        } catch (Exception e){
            System.out.println();
        }

        return tree;
    }

    public static RTree readRTree() {
        RTree rTree = new RTree();

        try {
            File myObj = new File("R-Trees/r-treeL.paed");
            Scanner myReader = new Scanner(myObj);
            int total = Integer.parseInt(myReader.nextLine());
            for (int i = 0; i < total; i++) {
                String data = myReader.nextLine();
                String[] line = data.split(",");
                Point point = new Point(Float.parseFloat(line[1]), Float.parseFloat(line[2]));
                rTree.insert(rTree.root, new RNode(line[0], point, null, null, null));
            }
            System.out.println();
        } catch (Exception e){
            System.out.println();
        }

        return rTree;
    }
}























