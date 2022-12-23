import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class Main{
    public static void main(String[]args){
        // Program variables
        boolean leaveProgram = false;
        long startTime, elapsedTime;
        FileManager fileManager = new FileManager();

        // Calculating the time to read and create the graph
        startTime = System.nanoTime();
        Graph graph = fileManager.readGraph("XXS");
        // Showing the time it took for the DFS
        elapsedTime = System.nanoTime() - startTime;
        System.out.println("\nTime to create the graph: " + (elapsedTime/Math.pow(10,6)) + "ms");

        startTime = System.nanoTime();
        BinaryTree tree = fileManager.readTree("XXS");
        elapsedTime = System.nanoTime() - startTime;
        System.out.println("\nTime to create the BST: " + (elapsedTime/Math.pow(10,6)) + "ms");

        // RTrees
        startTime = System.nanoTime();
        RTree rTree = fileManager.readRTree("XXS");
        elapsedTime = System.nanoTime() - startTime;
        System.out.println("\nTime to create the RTree: " + (elapsedTime/Math.pow(10,6)) + "ms");
        // copy to clipboard:
        String myString = (elapsedTime/Math.pow(10,6)) + "";
        myString = myString.replace(".",",");
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

        HashTable hashTable = fileManager.readTable("XXS");
        FileManager f = new FileManager();

        // Displaying the menu until the user decides to leave
        do {
           // User enters the option to run
           switch (Menu.mainMenu()){
               // Option 1 Routes
               case 1->{
                    // Run the Graph option
                   RoutesGraphs.executeRoutesGraphs(graph);
               }
               // Option 2 Inventory
               case 2->{
                    // Run the Binary Trees Option
                   LootTrees.executeLootTrees(tree);
               }
               case 3->{
                   DecksRtrees.executeDeckRTees(rTree);
               }
               case 4->{
                   CrewTables.executeCrewTables(hashTable);
               }
               case 5->{
                   System.out.println("So long, comrade!");
                   leaveProgram = true;
               }
               case 6->{
                   fileManager = fileManager.changeDataset(graph, tree, rTree, hashTable);
                   // Updating the data structures according to the new ones read
                   graph = fileManager.graph;
                   tree = fileManager.binaryTree;;
                   rTree = fileManager.rTree;
                   hashTable = fileManager.hashTable;
               }

           }
        } while (!leaveProgram);

    }
}
