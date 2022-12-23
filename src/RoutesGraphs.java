import java.util.*;

public class RoutesGraphs {

    /**
     * Method to execute the first option Routes (Graphs)
     */
    public static void executeRoutesGraphs(Graph graph){
        // Method variables
        boolean leave = false;
        String option;
        long startTime, elapsedTime;


        // Execute the different options in Routes (Graphs) until the user decides to go back to the main menu
        do {
            // Displaying the Routes Submenu and returning the option entered by the user
            option = Menu.routesSubmenu();

            // Executing the option entered by the user
            switch (option) {
                case "A" -> {
                    System.out.print("Enter the origin node's identifier: ");
                    String id = new Scanner(System.in).nextLine();
                    try {
                        int i = graph.ids.indexOf(Integer.parseInt(id));
                        Node n = graph.nodes.get(i);
                        graph.resetVisited(graph);
                        // Calculating the time to run DFS
                        startTime = System.nanoTime();
                        // Running DFS
                        System.out.println("\nDFS found the following points of interest:\n");
                        graph.DFS(graph, n);
                        // Showing the time it took for the DFS
                        elapsedTime = System.nanoTime() - startTime;
                        System.out.println("\nTotal execution time: " + (elapsedTime/Math.pow(10,6)) + "ms");
                    } catch (Exception e) {
                        System.out.println("Error: The id does not correspond to any place.");
                    }

                    System.out.println();
                }
                case "B" -> {

                    System.out.print("Enter the origin node's identifier: ");
                    String id = new Scanner(System.in).nextLine();
                    try {
                        int i = graph.ids.indexOf(Integer.parseInt(id));
                        Node n = graph.nodes.get(i);
                        graph.resetVisited(graph);
                        // Calculating the time to run BFS
                        startTime = System.nanoTime();
                        System.out.println("\nBFS found the following dangerous places:\n");
                        graph.BFS(graph, n);
                        elapsedTime = System.nanoTime() - startTime;
                        System.out.println("\nTotal execution time: " + (elapsedTime/Math.pow(10,6)) + "ms");
                    } catch (Exception e) {
                        System.out.println("Error: The id does not correspond to any place.");
                    }

                    System.out.println();

                }
                case "C" -> {
                    graph.resetVisited(graph);
                    //List<Integer> ids = graph.DFSKruskal(graph, graph.nodes.get(0), new ArrayList<>());
                    // Calculating the time to run MST
                    startTime = System.nanoTime();
                    Graph mst = graph.mstKruskal(graph);
                    mst.printMST(mst);
                    elapsedTime = System.nanoTime() - startTime;
                    System.out.println("\nTotal execution time for the MST: " + (elapsedTime/Math.pow(10,6)) + "ms");
                    System.out.println();

                }
                case "D" -> {
                    // Reset the graph
                    graph.resetVisited(graph);
                    // Asking for the origin Id to the user
                    System.out.print("Enter the origin node's identifier: ");
                    String originId = new Scanner(System.in).nextLine();

                    try {
                        // Obtaining the origin ID
                        int indexOrigin = graph.ids.indexOf(Integer.parseInt(originId));
                        Node origin = graph.nodes.get(indexOrigin);
                        // Asking the destination place ID to the user
                        System.out.print("Enter the destination node's identifier: ");
                        String destinationID = new Scanner(System.in).nextLine();
                        try{
                            // Calculating the time to run the addition
                            startTime = System.nanoTime();
                            // Obtaining the destination place
                            int indexDestination = graph.ids.indexOf(Integer.parseInt(destinationID));
                            Node destination = graph.nodes.get(indexDestination);
                            System.out.println("\nShortest Path from origin to destination avoiding Danger places: \n");
                            // If all index are correct, run the algorithm
                            graph.dijkstra(graph,origin,destination);
                            elapsedTime = System.nanoTime() - startTime;
                            System.out.println("\nTotal execution time for the Dijkstra: " + (elapsedTime/Math.pow(10,6)) + "ms");
                        }catch(Exception e){
                            System.out.println("Error: The id of the destination node does not correspond to any place.");
                        }

                    } catch (Exception e) {
                        System.out.println("Error: The id of the origin node does not correspond to any place.");
                    }


                }

                case "E" -> leave = true;

            }
        } while (!leave);

    }


}
/*
// Count starting time
        startTime = System.nanoTime();



        // Compute the time it took to run the algorithm
        elapsedTime = System.nanoTime() - startTime;
        System.out.println("\nTotal execution time: " + (elapsedTime/Math.pow(10,6)) + "ms");
* */