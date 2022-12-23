import java.util.ArrayList;
import java.util.Collections;

public class RTree {
    static final int ORDER = 3;
    RNode root; // rectangle that contains all other rectangles

    public RTree() {
        root = new RNode("root",null, null, new ArrayList<>(), null);
        root.parent = root;
    }

    // current starts with root
    public void insert(RNode current, RNode point) {
        // if current is a leaf
        if (current.children == null || current.children.isEmpty()) {
            //current.parent.children = new ArrayList<>();
            current.parent.children.add(point);
            point.parent = current.parent;
            // check for overflow
            if (current.parent.children.size() > ORDER) {
                handleOverflow(current.parent.children);
            } else {
                updateRectangleSize(current.parent);
            }
        } else {
            RNode subtree = chooseSubtree(current, point);
            insert(subtree, point);
        }
    }

    // return the child whose rectangle requires the minimum increase in perimeter to include the point
    private RNode chooseSubtree(RNode current, RNode point) {
        float perimeterIncrease = Float.MAX_VALUE;
        RNode chosen = null;

        // if children are points
        if (current.children.get(0).children == null || current.children.get(0).children.isEmpty()) {
            return current.children.get(0);
        }

        // if children are rectangles
        for (int i = 0; i < current.children.size(); i++) {
            // check if it fits inside the rectangle
            if (current.children.get(i).rectangle.upperLeft.coordX < point.point.coordX && current.children.get(i).rectangle.lowerRight.coordX > point.point.coordX
                && current.children.get(i).rectangle.upperLeft.coordY > point.point.coordY && current.children.get(i).rectangle.lowerRight.coordY < point.point.coordY) {
                return current.children.get(i);
            }

            float perimeter = calculatePerimeter(current.children.get(i).rectangle);
            Point upperLeft = new Point(Math.min(current.children.get(i).rectangle.upperLeft.coordX, point.point.coordX), Math.max(current.children.get(i).rectangle.upperLeft.coordY, point.point.coordY));
            Point lowerRight = new Point(Math.max(current.children.get(i).rectangle.lowerRight.coordX, point.point.coordX), Math.min(current.children.get(i).rectangle.lowerRight.coordY, point.point.coordY));

            float finalPerimeter = calculatePerimeter(new Rectangle(upperLeft, lowerRight));
            if (finalPerimeter - perimeter < perimeterIncrease) {
                perimeterIncrease = finalPerimeter - perimeter;
                chosen = current.children.get(i);
            }
        }

        return chosen;
    }

    private float calculatePerimeter(Rectangle rectangle) {
        float base = rectangle.lowerRight.coordX - rectangle.upperLeft.coordX;
        float height = rectangle.upperLeft.coordY - rectangle.lowerRight.coordY;

        return (base + height) * 2;
    }

    public Rectangle calculateRectangle(Point p1, Point p2) {
        Point upperLeft = new Point(Math.min(p1.coordX, p2.coordX), Math.max(p1.coordY, p2.coordY));
        Point lowerRight = new Point(Math.max(p1.coordX, p2.coordX), Math.min(p1.coordY, p2.coordY));

        return new Rectangle(upperLeft, lowerRight);
    }

    private Point calculateCenter(Rectangle r) {
        float x = (r.lowerRight.coordX - r.upperLeft.coordX)/2;
        float y = (r.upperLeft.coordY - r.lowerRight.coordY)/2;

        return new Point(x, y);
    }

    private void handleOverflow(ArrayList<RNode> children) {
        ArrayList<RNode> splitted = splitChildren(children);

        ArrayList<RNode> children1 = new ArrayList<>();
        ArrayList<RNode> children2 = new ArrayList<>();

        children1.add(splitted.get(0));
        children1.add(splitted.get(2));
        children2.add(splitted.get(1));
        children2.add(splitted.get(3));
        // check if the parent is the root
        if (children.get(0).parent.name.equals("root")) {
            // create two rectangles and split the four children into the rectangles
            RNode r1 = new RNode("rectangle", null, null, children1, root);
            RNode r2 = new RNode("rectangle", null, null, children2, root);
            updateRectangleSize(r1);
            updateRectangleSize(r2);
            r1.children.get(0).parent = r1;
            r1.children.get(1).parent = r1;
            r2.children.get(0).parent = r2;
            r2.children.get(1).parent = r2;
            root.children.set(1, r2);
            root.children.set(0, r1);
            root.children.remove(root.children.size()-1);
            root.children.remove(root.children.size()-1);
        } else {
            // go to the parent node and create a new sibling. Split the 4 children with the parent node and the new sibling.
            RNode parent = children.get(0).parent;
            RNode r2 = new RNode("rectangle", null, null, children1, parent.parent);
            updateRectangleSize(r2);
            children1.get(0).parent = r2;
            children1.get(1).parent = r2;
            parent.parent.children.add(r2);
//            updateRectangleSize(children.get(0).parent.parent.children.get(children.get(0).parent.parent.children.size()-1));
            parent.children = children2;
            children2.get(0).parent = parent;
            children2.get(1).parent = parent;
//                parent.children.set(0, children2.get(0));
//                parent.children.set(1, children2.get(1));
//                parent.children.remove(parent.children.size()-1);
//                parent.children.remove(parent.children.size()-1);
            updateRectangleSize(parent);

            if (parent.parent.children.size() > ORDER) {
                handleOverflow(parent.parent.children);
            }
        }
    }

    private ArrayList<RNode> splitChildren(ArrayList<RNode> children) {
        // Check if the nodes to split are points or rectangles then reorder them
        // if they are points
        if (children.get(0).children == null || children.get(0).children.isEmpty()) {
            ArrayList<RNode> aux = new ArrayList<>(children);
            ArrayList<RNode> splitted = new ArrayList<>(children);
            double maxDistance = Double.MIN_VALUE;
            // find the two most distant points
            for (int i = 0; i < children.size(); i++) {
                for (int j = i+1; j < children.size(); j++) {
                    double distance = Math.sqrt(Math.pow((children.get(i).point.coordX - children.get(j).point.coordX), 2) + Math.pow((children.get(i).point.coordY - children.get(j).point.coordY), 2));
                    if (distance > maxDistance) {
                        maxDistance = distance;
                        splitted.set(0, aux.get(i));
                        splitted.set(1, aux.get(j));
                    }
                }
            }
            // find the other two points
            aux.remove(splitted.get(0));
            aux.remove(splitted.get(1));

            Rectangle r1 = calculateRectangle(splitted.get(0).point, aux.get(0).point);
            Rectangle r2 = calculateRectangle(splitted.get(0).point, aux.get(1).point);

            float p1 = calculatePerimeter(r1);
            float p2 = calculatePerimeter(r2);

            if (p1 < p2) {
                splitted.set(2, aux.get(0));
                splitted.set(3, aux.get(1));
            } else {
                splitted.set(2, aux.get(1));
                splitted.set(3, aux.get(0));
            }
            return splitted;
        } else {
            // if they are rectangles
            // find the center
            ArrayList<Point> centers = new ArrayList<>();
            for (int i = 0; i < children.size(); i++) {
                centers.add(calculateCenter(children.get(i).rectangle));
            }
            ArrayList<RNode> splitted = new ArrayList<>();
            ArrayList<Integer> indices = reorderPoints(centers);
            for (int i = 0; i < children.size(); i++) {
                splitted.add(children.get(indices.get(i)));
            }
            return splitted;
        }
    }

    private ArrayList<Integer> reorderPoints(ArrayList<Point> points) {
        ArrayList<Point> centers = new ArrayList<>(points);
        ArrayList<Point> aux = new ArrayList<>(points);

        double maxDistance = Double.MIN_VALUE;
        // find the two most distant points
        for (int i = 0; i < points.size(); i++) {
            for (int j = i+1; j < points.size(); j++) {
                double distance = Math.sqrt(Math.pow((points.get(i).coordX - points.get(j).coordX), 2) + Math.pow((points.get(i).coordY - points.get(j).coordY), 2));
                if (distance > maxDistance) {
                    maxDistance = distance;
                    centers.set(0, points.get(i));
                    centers.set(1, points.get(j));
                }
            }
        }
        // find the other two points
        points.remove(centers.get(0));
        points.remove(centers.get(1));

        Rectangle r1 = calculateRectangle(centers.get(0), points.get(0));
        Rectangle r2 = calculateRectangle(centers.get(0), points.get(1));

        float p1 = calculatePerimeter(r1);
        float p2 = calculatePerimeter(r2);

        if (p1 < p2) {
            centers.set(2, points.get(0));
            centers.set(3, points.get(1));
        } else {
            centers.set(2, points.get(1));
            centers.set(3, points.get(0));
        }

        ArrayList<Integer> centersIndices = new ArrayList<>();
        for (Point center : centers) {
            centersIndices.add(aux.indexOf(center));
        }
        return centersIndices;
    }

    private void updateRectangleSize(RNode node) {
        ArrayList<Float> pointsX = new ArrayList<>();
        ArrayList<Float> pointsY = new ArrayList<>();
        // if they are points
        if (node.children.get(0).children == null || node.children.get(0).children.isEmpty()) {
            for (int i = 0; i < node.children.size(); i++) {
                pointsX.add(node.children.get(i).point.coordX);
                pointsY.add(node.children.get(i).point.coordY);
            }
        } else {
            // if they are rectangles
            for (int i = 0; i < node.children.size(); i++) {
                pointsX.add(node.children.get(i).rectangle.upperLeft.coordX);
                pointsX.add(node.children.get(i).rectangle.lowerRight.coordX);
                pointsY.add(node.children.get(i).rectangle.upperLeft.coordY);
                pointsY.add(node.children.get(i).rectangle.lowerRight.coordY);
            }
        }

        Collections.sort(pointsX);
        Collections.sort(pointsY);

        node.rectangle = new Rectangle(new Point(pointsX.get(0), pointsY.get(pointsY.size()-1)), new Point(pointsX.get(pointsX.size()-1), pointsY.get(0)));

        // update all parents
        if (node != root) {
            updateRectangleSize(node.parent);
        }
    }


    // SEARCH BY AREA
    // searches for points inside a given rectangle
    public void searchByArea(Rectangle input, RNode current, ArrayList<RNode> result) {
        if (current.children.get(0).rectangle != null) {
            for (int i = 0; i < current.children.size(); i++) {
                if (isOverlapping(input, current.children.get(i).rectangle)) {
                    searchByArea(input, current.children.get(i), result);
                }
            }
        } else {
            for (int i = 0; i < current.children.size(); i++) {
                if (isValidResult(input, current.children.get(i).point)) {
                    result.add(current.children.get(i));
                }
            }
        }
    }
    /*
    private void search(final float[] coords, final float[] dimensions, final Node n,
            final LinkedList<T> results) {
        if (n.leaf) {
            for (Node e : n.children) {
                if (isOverlap(coords, dimensions, e.coords, e.dimensions)) {
                    results.add(((Entry<T>) e).entry);
                }
            }
        } else {
            for (Node c : n.children) {
                if (isOverlap(coords, dimensions, c.coords, c.dimensions)) {
                    search(coords, dimensions, c, results);
                }
            }
        }
    }
     */

    private boolean isOverlapping(Rectangle input, Rectangle r2) {
        // check if any of the 4 corners of r2 rectangle is inside the input
        // upper left corner
        if (r2.upperLeft.coordX >= input.upperLeft.coordX && r2.upperLeft.coordX <= input.lowerRight.coordX && r2.upperLeft.coordY <= input.upperLeft.coordY && r2.upperLeft.coordY >= input.lowerRight.coordY) {
            return true;
        }
        // lower right corner
        if (r2.lowerRight.coordX >= input.upperLeft.coordX && r2.lowerRight.coordX <= input.lowerRight.coordX && r2.lowerRight.coordY <= input.upperLeft.coordY && r2.lowerRight.coordY >= input.lowerRight.coordY) {
            return true;
        }
        // upper right corner
        if (r2.lowerRight.coordX >= input.upperLeft.coordX && r2.lowerRight.coordX <= input.lowerRight.coordX && r2.upperLeft.coordY <= input.upperLeft.coordY && r2.upperLeft.coordY >= input.lowerRight.coordY) {
            return true;
        }
        // lower left corner
        if (r2.upperLeft.coordX >= input.upperLeft.coordX && r2.upperLeft.coordX <= input.lowerRight.coordX && r2.lowerRight.coordY <= input.upperLeft.coordY && r2.lowerRight.coordY >= input.lowerRight.coordY) {
            return true;
        }

        return false;
    }

    private boolean isValidResult(Rectangle input, Point p) {
        return p.coordX >= input.upperLeft.coordX && p.coordX <= input.lowerRight.coordX && p.coordY <= input.upperLeft.coordY && p.coordY >= input.lowerRight.coordY;
    }



    // SEARCH BY PROXIMITY
    public ArrayList<RNode> searchByProximity(Point point, int numTreasures) {
        Rectangle searchZone = new Rectangle(new Point(point.coordX-0.5f, point.coordY+0.5f), new Point(point.coordX+0.5f, point.coordY-0.5f));
        int foundTreasures = 0;
        ArrayList<RNode> nodesInArea = new ArrayList<>();

        while (foundTreasures < numTreasures) {
            nodesInArea = new ArrayList<>();
            searchByArea(searchZone, root, nodesInArea);
            foundTreasures = nodesInArea.size();
            searchZone.upperLeft.coordX = searchZone.upperLeft.coordX-1;
            searchZone.upperLeft.coordY = searchZone.upperLeft.coordY+1;
            searchZone.lowerRight.coordX = searchZone.lowerRight.coordX+1;
            searchZone.lowerRight.coordY = searchZone.lowerRight.coordY-1;
        }

        if (foundTreasures > numTreasures) {
            // filter nodesInArea by distance
            ArrayList<Double> distances = new ArrayList<>();
            ArrayList<Double> aux = new ArrayList<>();
            for (int i = 0; i < nodesInArea.size(); i++) {
                double distance = Math.sqrt(Math.pow((nodesInArea.get(i).point.coordX - point.coordX), 2) + Math.pow((nodesInArea.get(i).point.coordY - point.coordY), 2));
                distances.add(distance);
                aux.add(distance);
            }
            Collections.sort(distances);
            while (nodesInArea.size() > numTreasures) {
                int index = aux.indexOf(distances.remove(distances.size()-1));
                nodesInArea.remove(index);
                aux.remove(index);
            }
        }
        
        return nodesInArea;
    }

//    private int calculateNumPoints(RNode current, int currentPoints) {
//        if (current.children.get(0).rectangle != null) {
//            for (int i = 0; i < current.children.size(); i++) {
//                calculateNumPoints(current, currentPoints);
//            }
//        } else {
//            for (int i = 0; i < current.children.size(); i++) {
//                currentPoints++;
//            }
//        }
//        return currentPoints;
//    }
}

class RNode {
    String name; // name of the treasure
    Point point; // coordinates of the treasure
    Rectangle rectangle; // To store dimensions of rectangle made by child nodes. Leaf nodes have a rectangle of dimension 0
    ArrayList<RNode> children; // children nodes
    RNode parent; // Pointer to parent node

    public RNode(String name, Point point, Rectangle rectangle, ArrayList<RNode> children, RNode parent) {
        this.name = name;
        this.point = point;
        this.rectangle = rectangle;
        this.children = children;
        this.parent = parent;
    }
}

class Point {
    float coordX;
    float coordY;

    public Point(float coordX, float coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }
}

class Rectangle {
    Point upperLeft;
    Point lowerRight;

    public Rectangle(Point upperLeft, Point lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
    }
}

