import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private static ArrayList<Patron> patrons = new ArrayList<>(); //ArrayList to allow for dynamic sizing
    private static Scanner scanner = new Scanner(System.in); //Scanner for reading user input

    public static void main(String[] args) {
        while(true){ //While-loop to keep us in the main menu until the switch is activated
            System.out.println("\nWelcome to Library Management System Menu\n");
            System.out.println("1. Add Patron Manually");
            System.out.println("2. Add Patron via text file");
            System.out.println("3. Remove Patron via ID number");
            System.out.println("4. View all patrons in the system");
            System.out.println("0. Exit");
            System.out.print("\nPlease enter your choice: ");
            try { //Try-catch so the system doesn't explode when the user enters a string
                int menuInput = scanner.nextInt();
                scanner.nextLine(); //Have to clear the scanner

                switch (menuInput) { //Each choice corresponds with printed menu options
                    case 1:
                        ManualAdd();
                        break;
                    case 2:
                        AddFromFile();
                        break;
                    case 3:
                        RemovePatron();
                        break;
                    case 4:
                        ViewAllPatrons();
                        break;
                    case 0:
                        System.out.println("\nNow closing the Library Management System...");
                        System.exit(0); //Terminates system
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nPlease enter a valid choice");
                scanner.nextLine(); //If you don't clear the scanner here, the input will remain in memory and the menu->switch->error will loop forever
            }
        }
    }

    private static void ManualAdd() { //Function for adding a patron manually
        int IDNum; //Need to define these at the start to keep them in scope
        double fineAmount;
        boolean flag = true;

        while (flag == true){ //Sentinel loop to keep us in the ManualAdd function until the user no longer wishes to add patrons
            System.out.println("\nPlease enter patron's ID number: ");
            while (true) { //Just like the menu while-loop, keeps programming from exploding form mismatched input
                try {
                    IDNum = scanner.nextInt();
                    scanner.nextLine();
                    break; //Once information is successfully received, break out of the while loop and continue the function
                } catch (InputMismatchException e) {
                    System.out.println("\nPlease enter a valid patron's ID number");
                    scanner.nextLine(); //Gotta clear these lines
                }
            }

            //Retrieve relevant string information
            System.out.println("\nEnter patron's first name: ");
            String firstName = scanner.nextLine();

            System.out.println("\nEnter patron's last name: ");
            String lastName = scanner.nextLine();

            System.out.println("\nEnter patron's address: ");
            String addy = scanner.nextLine();

            while (true) { //Another while-loop with a try/catch to catch mismatched input for the double
                try {
                    System.out.println("\nEnter patron's fine amount (Ex. 15.67): ");
                    fineAmount = scanner.nextDouble();
                    scanner.nextLine();

                    Patron newPatron = new Patron(IDNum, firstName, lastName, addy, fineAmount);
                    patrons.add(newPatron);

                    System.out.println("\nPatron added: " + newPatron + "\n");
                    System.out.println("Updated Patrons List:");
                    ViewAllPatrons(); //Instruction say to print the list after each add
                    break; //Leave loop once the information has been retrieved
                } catch (InputMismatchException e) {
                    System.out.println("\nPlease enter a valid fine amount");
                    scanner.nextLine(); //Please don't forget to do this, I have done so many times
                }
            }
            while (true) { //Keep us in this loop until a proper choice has been made
                System.out.println("\nWould you like to add another patron? (y/n):");
                try { //Try/catch so people don't try to push an int into the char
                    int cont = scanner.next().charAt(0);

                    if (cont == 'y' || cont == 'Y') {
                        flag = true; //Keep the flag true
                        break;
                    } else if (cont == 'n' || cont == 'N') {
                        flag = false; //Set flag to false to break the first sentinel loop
                        System.out.println("\n----Returning to main menu----");
                        break;
                    }
                    else {
                        System.out.println("\nPlease enter a valid choice"); //Catches chars that are neither y nor n
                        flag = false;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nPlease enter a valid choice");
                    scanner.nextLine(); //Don't forget
                }
            }
        }
    }

    private static void AddFromFile() { //Function to add a patron from a file, assuming the file is formatted correctly
        System.out.print("\nPlease enter the file path: ");
        ArrayList<Patron> tempList = new ArrayList<>();
        String filePath = scanner.nextLine(); //Whatever string the user enters will be used as a filepath for a File object

        try { //Try/catch for file not found and for chars being fed into the int variable
            File file = new File(filePath); //Instantiate File object with previously entered filepath
            Scanner fileScanner = new Scanner(file); //New scanner to read through the file

            int counter = 0; //This is just to look nice and tell the user how many patrons were added from the file
            while (fileScanner.hasNextLine()) { //While-loop to read to the end of the file
                String line = fileScanner.nextLine().trim(); //Trim off whitespace

                String[] patronInfo = line.split("-"); //String.split to take the line, split it at the delimiters,
                                                             //and shove each section into a String array

                int IDNum = Integer.parseInt(patronInfo[0].trim()); //Take the String IDnum and make it an int, then store it
                String fullName = patronInfo[1].trim(); //Full name is only separated by a space, so store it as one  for now
                String addy = patronInfo[2].trim(); //Store the address string
                double fineAmount = Double.parseDouble(patronInfo[3].trim()); //Make the String fine become a double and store it

                String[] nameParts = fullName.split(" ", 2); //Just like we split the line with a dash,
                                                                        //now we use a space as the delimiter
                                                                        //This information can go straight into the constructor
                                                                        //Assignment format showed only first name and last name, set limit to 2

                Patron newPatron = new Patron(IDNum, nameParts[0], nameParts[1], addy, fineAmount); //Instantiate a Patron
                patrons.add(newPatron); //Shove the new Patron into the ArrayList
                tempList.add(newPatron); //Shove the new Patron into my temp list to display new users added
                counter++; //Counter goes up!
            }

            fileScanner.close(); //Close your scanners
            System.out.println("\n" + counter + " patrons successfully loaded from file.\n\nUpdated Patron List:");

            ViewAllPatrons(); //Instruction say to print the list after each add
        } catch (FileNotFoundException e) { //Error-catching when the file is wrong or a string is read into an int
            System.out.println("\nError: File not found (" + filePath + ")");
        } catch (NumberFormatException e) {
            System.out.println("\nError reading numbers from file. Please check file formatting and try again.");
        }
    }

    private static void RemovePatron() { //Function for removing a patron
        int patronID; //The ID to be entered by the user
        System.out.println("\nPlease enter patron's ID: ");
        while (true) { //Keep us looped in for error, like the previous while-loops with try/catches
            try {
                patronID = scanner.nextInt();
                scanner.nextLine(); //Always next line

                Patron removedPatron = null; //Declared as null, so we can use it in an if-statement

                for (Patron patron : patrons) { //For-each loop to find a patron with a matching ID number to what was
                    if (patron.GetIDNum() == patronID) {//entered and then break immediately
                        removedPatron = patron;

                        break;
                    }
                }

                if (removedPatron != null) { //If no patron ID matched, this would still be null and not trigger
                    patrons.remove(removedPatron); //ArrayList.remove the patron we found in the for-each loop
                    System.out.println("\nRemoved " + removedPatron);

                    System.out.println("\nUpdated Patrons List:");
                    ViewAllPatrons(); //Instruction say to print the list after each add
                } else { //If null, inform the user that no patron was found and leave
                    System.out.println("No patron with ID: " + patronID + " was found in the system");
                    System.out.println("----Returning to main menu----");
                }
                break; //Exit the while-loop
            }
            catch (InputMismatchException e) { //Please don't enter strings into my int variable
                System.out.println("\nPlease enter a valid patron's ID number");
                scanner.nextLine(); //I probably forgot this every time I wrote it and ran the program afterward
            }
        }
    }

    private static void ViewAllPatrons() { //Function to view all patrons in the system
        if (patrons.isEmpty()){ //If our ArrayList is empty, inform the user and return to main
            System.out.println("\nThere are no patrons in the system\n----Returning to main menu----");
        }
        else { //If the ArrayList is not empty, use a for-each loop to iterate through the list and call toString
            System.out.println("Patron ID Number\t First Name\tLast Name\t\tAddress\t\t\tFine Amount");
            for (Patron patron : patrons) {
                System.out.println(patron.toString());
            }

            System.out.println("\nPlease press enter to return...");
            scanner.nextLine(); //This waits for any user input to return to previous menu
                                //Gives the user time to read the extensive list
        }
    }
}