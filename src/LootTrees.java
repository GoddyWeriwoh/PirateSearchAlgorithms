import java.util.ArrayList;
import java.util.Scanner;

public class LootTrees {


    public static void executeLootTrees(BinaryTree tree) {
        // Method variables
        boolean leave = false;
        String option;
        String name;
        long value;
        long startTime, elapsedTime;


        // Execute the different options in Loot (Trees) until the user decides to go back to the main menu
        do {
            // Displaying the Loot Submenu and returning the option entered by the user
            option = Menu.lootMenu();

            // Executing the option entered by the user
            switch (option) {
                case "A" -> {
                    System.out.print("Enter the treasure's name: ");
                    name = new Scanner(System.in).nextLine();
                    System.out.print("Enter the treasure's value: ");
                    //value = new Scanner(System.in).nextLong();
                    // Calculating the time to run The addition of a tree
                    startTime = System.nanoTime();
                    try {
                        value = Long.parseLong(new Scanner(System.in).nextLine());
                        tree.addNode(value, name);
                        System.out.println("The treasure was correctly added to the loot.");
                        elapsedTime = System.nanoTime() - startTime;
                        System.out.println("\nTotal execution time to add a node to the tree: " + (elapsedTime/Math.pow(10,6)) + "ms");
                    } catch (Exception e) {
                        System.out.println("Error, the value entered is not a Long");
                    }


                }
                case "B" -> {
                    System.out.print("Enter the treasure's name: ");
                    name = new Scanner(System.in).nextLine();
                    value = tree.findValueFrom(name);
                    if (value == -1) {
                        System.out.println("The treasure was not found.");
                    } else {
                        // delete the node searching from the root node
                        //tree.root = tree.deleteNode(tree.root, value);
                        tree.root = tree.AVLDeleteNode(tree.root, value);
                        System.out.println("The treasure was correctly removed from the loot.");
                        System.out.println();
                    }
                }
                case "C" -> {
                    System.out.print("\nI. Preorder\n" +
                                    "II. Postorder\n" +
                                    "III. Inorder\n" +
                                    "IV. By level\n\n" +
                                    "Pick a traversal: ");
                    String input = new Scanner(System.in).nextLine();
                    // Calculating the time to run the addition
                    startTime = System.nanoTime();
                    switch (input) {
                        case "I" -> {
                            tree.preorder(tree.root);
                        }
                        case "II" -> {
                            tree.postorder(tree.root);
                        }
                        case "III" -> {
                            tree.inorder(tree.root);
                        }
                        case "IV" -> {
                            tree.levels();
                        }
                        default -> {
                            System.out.println("Error, no valid option (I, II, III, IV) was selected");
                        }
                    }
                    elapsedTime = System.nanoTime() - startTime;
                    System.out.println("\nTotal execution time for the traversals: " + (elapsedTime/Math.pow(10,6)) + "ms");
                }
                case "D" -> {
                    System.out.print("Enter the value to search for: ");
                    try {
                        value = Long.parseLong(new Scanner(System.in).nextLine());
                        if (!tree.isNode(value)) {
                            System.out.println("The treasure with this value was not found.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error, the value entered is not a Long");
                    }
                }
                case "E" -> {
                    System.out.print("Enter the minimum value to search for: ");
                    try {
                        long low, high;
                        low = Long.parseLong(new Scanner(System.in).nextLine().replaceAll("[.]", ""));
                        System.out.print("Enter the maximum value to search for: ");
                        high = Long.parseLong(new Scanner(System.in).nextLine().replaceAll("[.]", ""));

                        if (high < low) {
                            long temp = high;
                            high = low;
                            low = temp;
                            System.out.println("Maximum value is higher than minimum value, they will be swapped.");
                        }
                        // Calculating the time to run the range search
                        startTime = System.nanoTime();
                        // We store the nodes to print in rangeNodes
                        tree.rangeNodes = new ArrayList<>();

                        int treasuresInRange = tree.searchRange(tree.root, low, high);

                        System.out.println(treasuresInRange + " treasures were found in this range:\n");

                        for (int i = 0; i < tree.rangeNodes.size(); i++) {
                            System.out.println(tree.rangeNodes.get(i));
                        }
                        elapsedTime = System.nanoTime() - startTime;
                        System.out.println("\nTotal execution time for the range search: " + (elapsedTime/Math.pow(10,6)) + "ms");

                    } catch (Exception e) {
                        System.out.println("Error, the value entered is not a Long");
                    }
                }
                case "F" -> leave = true;
            }
        } while (!leave);
    }
}
