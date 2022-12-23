import javax.swing.*;
import java.util.Scanner;

public class CrewTables {
    public static void executeCrewTables(HashTable hashTable) {
        // Method variables
        boolean leave = false;
        String option;
        String name;
        String role;
        String age;
        long startTime, elapsedTime;


        // Execute the different options in Loot (Trees) until the user decides to go back to the main menu
        do {
            // Displaying the Loot Submenu and returning the option entered by the user
            option = Menu.crewMenu();

            // Executing the option entered by the user
            switch (option) {
                case "A" -> {
                    // Getting the new pirate info
                    System.out.print("Enter the pirates's name: ");
                    name = new Scanner(System.in).nextLine();
                    System.out.print("Enter the pirates's age: ");
                    age = new Scanner(System.in).nextLine();
                    System.out.print("Enter the pirates's role: ");
                    role = new Scanner(System.in).nextLine();
                    // Adding the pirate to the table if the information is correct
                    if(name != null && role!= null && Integer.parseInt(age)>=14 && Integer.parseInt(age)<=50) {
                        hashTable.addPirate(name, new Pirate(name, role, Integer.parseInt(age), name));
                    }else{
                        System.out.println("The pirate could not be added correctly, age has to be between 14 and 50");
                    }
                }
                case "B" -> {
                    // Getting the requested pirate info
                    System.out.print("Enter the pirates's name: ");
                    name = new Scanner(System.in).nextLine();
                    if(hashTable.containsPirate(name)){
                        hashTable.removePirate(name);
                        System.out.println("The pirate was correctly removed from the crew. F.");
                    }else{
                        System.out.println("This pirate does not exist");
                    }

                }
                case "C" -> {
                    // Asking the pirate to the user
                    System.out.print("Enter the pirates's name: ");
                    name = new Scanner(System.in).nextLine();
                    // Getting the pirate details and printing those
                    try{
                        Pirate auxPirate = hashTable.getPirate(name);
                        System.out.println("\n\tName: "+auxPirate.getName()+"\n\tAge: "+auxPirate.getAge()+"\n\tRole: " + auxPirate.getRole());

                    }catch(Exception e){
                        System.out.println("The pirate does not exist");
                    }
                }
                case "D" -> {
                    System.out.println("Generating Histogram: ");
                    for (int i = 0; i < 37; i++) {
                        System.out.println("Number of pirates with age "+(i+14)+": "+hashTable.getAges()[i]);
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new AgeHistogram(hashTable.getAges());
                        }
                    });
                }
                case "E" -> {
                    leave = true;
                }
            }
        } while (!leave);
    }
}
