import java.util.Scanner;

public class Menu {

    /**
     * Method to display in screen the main menu of the program
     * @return returns an Integer with the number correspondent to the option selected by the user
     */
    public static Integer mainMenu() {
        // Method variables
        String option;
        boolean leave = false;

        // Displaying the menu until the user enters a correct option
        do {
            System.out.println("\n\n-= Pirates of the Mediterranean =-\n");
            System.out.println("\t1. Routes (Graphs)");
            System.out.println("\t2. Inventory (Binary Trees)");
            System.out.println("\t3. Deck (R trees)");
            System.out.println("\t4. Crew (Tables)\n");
            System.out.println("\t5. Exit the program\n");
            System.out.println("\t6. Change Dataset\n");

            System.out.print("Choose an option: ");

            // Entering the option
            option = new Scanner(System.in).nextLine();

            // Checking if the option entered by the user is correct
            try {
                if (Integer.parseInt(option) > 6 || Integer.parseInt(option) < 1 ){
                    System.out.println("\nError. Please input an integer value between 1 to 5");
                }
                else{
                    leave = true;
                }
            } catch (NumberFormatException inputNumberException){
                System.out.println("\nError. Please input an integer value between 1 to 5");
            }

        } while (!leave);

        return Integer.parseInt(option);
    }

    /**
     * Method to display to display in screen the Routes Graph submenu
     * @return returns a string corresponding to the option chosen by the user
     */
    public static String  routesSubmenu(){
        // Method variables
        boolean leave = false;
        String option;

        // Displaying the Routes Submenu until the user enters a correct option
        do {
            System.out.println("\nRoutes with Graphs Submenu: ");
            System.out.println("\n\tA. Find points of interest (DFS)");
            System.out.println("\tB. Find dangerous places (BFS)");
            System.out.println("\tC. Show the universal Nautical Chart (MST)");
            System.out.println("\tD. Find the optimal route (Dijkstra)\n");
            System.out.println("\tE. Go back\n");
            System.out.print("Which functionality do you want to run ? ");

            // Entering the option
            option = new Scanner(System.in).nextLine();

            // Checking if the letter entered by the user is correct
            if (!option.matches("A|B|C|D|E")){
                System.out.println("Error. Please enter one of the following letters: A,B,C,D or E");
            }
            else {
                leave = true;
            }
        } while (!leave);

        return option;
    }

    /**
     * Method to display in screen the Trees Menu
     * @return returns a string with the option chosen by the user
     */
    public static String lootMenu(){
        // Method variables
        boolean leave = false;
        String option;

        do {
            System.out.println("\nLoots with Binary Trees Submenu: ");
            System.out.println("\n\tA. Add Treasure");
            System.out.println("\tB. Remove Treasure");
            System.out.println("\tC. List Loot");
            System.out.println("\tD. Search by Value (exact)");
            System.out.println("\tE. Search by Value (range)\n");
            System.out.println("\tF. Go back\n");
            System.out.print("Which functionality do you want to run ? ");
            // Entering the option
            option = new Scanner(System.in).nextLine();

            // Checking if the letter entered by the user is correct
            if (!option.matches("A|B|C|D|E|F")){
                System.out.println("Error. Please enter one of the following letters: A,B,C,D or E");
            }
            else {
                leave = true;
            }


        } while (!leave);

        return option;
    }


    public static String deckMenu(){
        // Method variables
        boolean leave = false;
        String option;

        do {
            System.out.println("\nDeck Menu  R-Trees Submenu: ");
            System.out.println("\n\tA. Add Treasure");
            System.out.println("\tB. Remove Treasure");
            System.out.println("\tC. Visualize");
            System.out.println("\tD. Search by area");
            System.out.println("\tE. Search by proximity\n");
            System.out.println("\tF. Go back\n");
            System.out.print("Which functionality do you want to run ? ");
            // Entering the option
            option = new Scanner(System.in).nextLine();

            // Checking if the letter entered by the user is correct
            if (!option.matches("A|B|C|D|E|F")){
                System.out.println("Error. Please enter one of the following letters: A,B,C,D or E");
            }
            else {
                leave = true;
            }


        } while (!leave);


        return option;
    }


    public static String crewMenu(){
        // Method variables
        boolean leave = false;
        String option;

        do {
            System.out.println("\nCrew Menu - Hash Tables : ");
            System.out.println("\n\tA. Add Pirate");
            System.out.println("\tB. Remove Pirate");
            System.out.println("\tC. Show Pirate");
            System.out.println("\tD. Age histogram\n");
            System.out.println("\tE. Go back\n");
            System.out.print("Which functionality do you want to run ? ");
            // Entering the option
            option = new Scanner(System.in).nextLine();

            // Checking if the letter entered by the user is correct
            if (!option.matches("A|B|C|D|E")){
                System.out.println("Error. Please enter one of the following letters: A,B,C,D or E");
            }
            else {
                leave = true;
            }


        } while (!leave);


        return option;
    }


    public static String fileChangerMenu(){
        // Method variables
        boolean leave = false;
        String option;

        do {
            System.out.println("\nChange File submenu: ");
            System.out.println("\n\tA. Change Graph file");
            System.out.println("\tB. Change BST file");
            System.out.println("\tC. Change RTree file");
            System.out.println("\tD. Change Hash Table file\n");
            System.out.println("\tE. Go back\n");
            System.out.print("Which functionality do you want to run ? ");
            // Entering the option
            option = new Scanner(System.in).nextLine();

            // Checking if the letter entered by the user is correct
            if (!option.matches("A|B|C|D|E")){
                System.out.println("Error. Please enter one of the following letters: A,B,C,D or E");
            }
            else {
                leave = true;
            }


        } while (!leave);


        return option;
    }






}


