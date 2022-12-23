import java.util.*;

class Walk {
    Place walk;

    public Walk(Place walk) {
        this.walk = walk;
    }
}

class Place {
    int id;
    int nodeIndex;
    String name;
    String type;

    public Place(int id, String name, String type, int nodeIndex) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.nodeIndex = nodeIndex;
    }
}

class Edge implements Comparable<Edge> {
    Place place;
    float distance;

    public Edge(Place place, float distance) {
        this.place = place;
        this.distance = distance;
    }

    @Override
    public int compareTo(Edge o) {
        return distance >= o.distance ? (distance == o.distance ? 0 : 1) : -1;
    }
}

class Node {
    Place place;
    List<Edge> edges;
    boolean visited;

    public Node(Place place, List<Edge> edges) {
        this.place = place;
        this.edges = edges;
        this.visited = false;
    }

}


public class Graph {
    List<Node> nodes;
    List<Integer> ids;


    public Graph() {
        nodes = new ArrayList<>();
        ids = new ArrayList<>();
    }

    public void addVertex(Place place) {
        Node n = new Node(place, new ArrayList<>());
        nodes.add(n);
    }

    public void addEdge(Place origin, Place destination, float distance) {
        Edge e = new Edge(destination, distance);
        nodes.get(origin.nodeIndex).edges.add(e);
        Edge ed = new Edge(origin, distance);
        nodes.get(destination.nodeIndex).edges.add(ed);
    }

    public Place findPlace(int id) {
        for (Node node : nodes) {
            if (node.place.id == id) {
                return node.place;
            }
        }

        return null;
    }

    public void DFS(Graph g, Node n) {
        n.visited = true;
        if (n.place.type.equals("INTEREST")) {
            System.out.println("\t" + n.place.name);
        }
        for (int i = 0; i < n.edges.size(); i++) {
            int adjIndex = n.edges.get(i).place.nodeIndex;
            Node adj = g.nodes.get(adjIndex);
            if (!adj.visited) {
                DFS(g, adj);
            }
        }
    }


    public void BFS(Graph g, Node n) {
        Queue<Node> q = new LinkedList<>();
        q.add(n);
        n.visited = true;
        while (!q.isEmpty()) {
            Node node = q.remove();
            if (node.place.type.equals("DANGER")) {
                System.out.println("\t" + node.place.name);
            }
            for (int i = 0; i < node.edges.size(); i++) {
                int adjIndex = node.edges.get(i).place.nodeIndex;
                Node adj = g.nodes.get(adjIndex);
                if (!adj.visited) {
                    q.add(adj);
                    adj.visited = true;
                }
            }
        }
    }

    public void resetVisited(Graph g) {
        for (int i = 0; i < g.nodes.size(); i++) {
            g.nodes.get(i).visited = false;
        }
    }

    public Graph mstKruskal(Graph graph) {
        Graph mst = addAllVertices(graph);
        List<Edge> edges = addAllEdges(graph);

        while (!edges.isEmpty() && !isSpanningTree(mst)) {
            resetVisited(mst);
            // We get the top two edges from a list of sorted edges. Since they are already sorted and the graph is bidirectional, the top edges will always have the same distance.
            // In each one we find the node they are connected to with the distance. Therefore, we can have both nodes of the same edge by extracting pairs from the sorted list.
            Edge e1 = edges.remove(0);
            Edge e2 = edges.remove(0);
            // Get all connected places to a particular node
            List<Integer> subset = DFSKruskal(mst, mst.nodes.get(e1.place.nodeIndex), new ArrayList<>());
            resetVisited(mst);
            // Check if the other node of the edge is part of the same graph or subset
            if (!subset.contains(e2.place.id)) {
                mst.addEdge(e1.place, e2.place, e1.distance);
            }
        }

        return mst;
    }

    public void printMST(Graph graph) {
        for (int i = 0; i < graph.nodes.size(); i++) {
            System.out.println("Place: " + graph.nodes.get(i).place.name);
            for (int j = 0; j < graph.nodes.get(i).edges.size(); j++) {
                System.out.println("\tAdjacent: " + graph.nodes.get(i).edges.get(j).place.name + "\t\tWeight: " + graph.nodes.get(i).edges.get(j).distance);
            }
        }
    }

    // DFS used in Kruskal algorithm to determine all the connected vertices to a given node
    private List<Integer> DFSKruskal(Graph g, Node n, List<Integer> placesId) {
        n.visited = true;
        placesId.add(n.place.id);
        for (int i = 0; i < n.edges.size(); i++) {
            int adjIndex = n.edges.get(i).place.nodeIndex;
            Node adj = g.nodes.get(adjIndex);
            if (!adj.visited) {
                DFSKruskal(g, adj, placesId);
            }
        }

        return placesId;
    }


    // Checks if a graph is connected
    private boolean isSpanningTree(Graph g) {
        int connectedNodes = DFSKruskal(g, g.nodes.get(0), new ArrayList<>()).size();
        return connectedNodes == g.nodes.size();
    }


    // Creates a new graph that only contains vertices of another graph
    private Graph addAllVertices(Graph g) {
        Graph graph = new Graph();

        for (int i = 0; i < g.nodes.size(); i++) {
            graph.addVertex(g.nodes.get(i).place);
        }

        return graph;
    }

    // Extracts all edges from the graph and adds them to a list
    private ArrayList<Edge> addAllEdges(Graph g) {
        ArrayList<Edge> edges = new ArrayList<>();

        for (int i = 0; i < g.nodes.size(); i++) {
            edges.addAll(g.nodes.get(i).edges);
        }

        Collections.sort(edges);

        return edges;
    }



    public void dijkstra(Graph graph, Node start, Node end) {
        // Initialize the walks
        List<Walk> walks = initWalks(graph.nodes.size(), start);
        // Initialize the distances
        List<Float> distances = initDistances(graph, start);
        Float newDistance;
        Node current = start;

        while (!end.visited) {
            // Search all edges of the current node
            for (int i = 0; i < current.edges.size(); i++) {
                // findNode() returns the index of the node at a given place.
                int nodeIndex = current.edges.get(i).place.nodeIndex;
                Node adjacent = graph.nodes.get(nodeIndex);

                if (!adjacent.visited) {
                    // The indices of the distances and walks correspond to the indices of the nodes in the list.
                    newDistance = distances.get(current.place.nodeIndex) + current.edges.get(i).distance;
                    if (distances.get(adjacent.place.nodeIndex) > newDistance) {
                        distances.set(adjacent.place.nodeIndex, newDistance);
                        walks.set(adjacent.place.nodeIndex, new Walk(current.place));
                    }
                }
            }
            current.visited = true;
            current = findMinDistance(distances, graph, current);
        }

        Place place = end.place;
        List<String> path = new ArrayList<>();

        // Find the shortest path starting from the end place and going to the start place
        while (place.id != start.place.id) {
            path.add("ID: " + place.id + "\tName: " + place.name);
            place = walks.get(place.nodeIndex).walk;
        }

        // print the path
        System.out.println("\t\tID: " + place.id + "\tName: " + place.name);
        System.out.println("\t\t↓");
        while (!path.isEmpty()) {
            System.out.println("\t\t"+path.remove(path.size()-1));
            if (!path.isEmpty()) {
                System.out.println("\t\t↓");
            }
        }
    }

    private List<Float> initDistances(Graph graph, Node start){
        List<Float> distances = new ArrayList<>();
        // Setting infinity to all distances except the start place
        for (int i = 0; i < graph.nodes.size() ; i++) {
            distances.add(Float.MAX_VALUE);
        }

        distances.set(start.place.nodeIndex, 0.0f);

        return distances;
    }

    private List<Walk> initWalks(int graphLength, Node start){
        List<Walk> walks = new ArrayList<>();
        // Setting the start place to each walk
        for (int i = 0; i < graphLength; i++) {
            walks.add(new Walk(start.place));
        }

        return walks;
    }

    private Node findMinDistance(List<Float> distances, Graph graph, Node current) {
        // Create a copy of distances, so we can sort them and don't lose track of their initial index
        List<Float> sorted = new ArrayList<>(distances);
        // Order them from shortest to largest
        Collections.sort(sorted);

        // Search for the closest place of interest
        for (int i = 0; i < sorted.size(); i++) {
            // find the index of the shortest distance in the original array. The node associated to that distance has the same index.
            int index = distances.indexOf(sorted.get(i));
            if (!graph.nodes.get(index).visited && graph.nodes.get(index).place.type.equals("INTEREST") && isAdjacent(graph, current, graph.nodes.get(index))) { // graph.nodes.get(index).place.id == end.place.id
                return graph.nodes.get(index);
            }
        }

        // In case all places are dangerous we go to the closest one not visited and adjacent.
        for (int i = 0; i < sorted.size(); i++) {
            int index = distances.indexOf(sorted.get(i));
            if (!graph.nodes.get(index).visited && isAdjacent(graph, current, graph.nodes.get(index))) { // graph.nodes.get(index).place.id == end.place.id
                return graph.nodes.get(index);
            }
        }

        // Should never reach this point, unless the graph is disconnected.
        return graph.nodes.get(distances.indexOf(sorted.get(0)));
    }

    private boolean isAdjacent(Graph g, Node n1, Node n2) {
        // Verify that the current node is adjacent to the new node
        for (int i = 0; i < g.nodes.get(n1.place.nodeIndex).edges.size(); i++) {
            if (g.nodes.get(n1.place.nodeIndex).edges.get(i).place.id == n2.place.id) {
                return true;
            }
        }

        return false;
    }

}


