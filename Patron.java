/*
Brian Oldham
CEN3024C-14877
09SEP2025
MainMenu.java
This class containerizes the necessary information for patrons in our system
 */
public class Patron {
    private int IDNum; //Storage variables
    private String firstName;
    private String lastName;
    private String address;
    private double fineAmount;

    //Default constructor
    public Patron(){
        firstName = "";
        lastName = "";
        address = "";
        IDNum = 0;
        fineAmount = 0;
    }

    //Overload constructor
    public Patron(int IDNum, String firstName, String lastName, String address, double fineAmount){
        this.IDNum = IDNum;
        this.firstName = String.format("%-10s", firstName); //Formatting
        this.lastName = String.format("%-15s", lastName);
        this.address = String.format("%-20s", address);
        this.fineAmount = fineAmount;
    }

    //SETTERS
    public void SetFirstName(String firstName){
        this.firstName = firstName;
    }
    public void SetLastName(String lastName){
        this.lastName = lastName;
    }
    public void SetAddress(String address){
        this.address = address;
    }
    public void SetIDNum(int IDNum){
        this.IDNum = IDNum;
    }
    public void SetFineAmount(double fineAmount){
        this.fineAmount = fineAmount;
    }
    // END SETTERS

    //GETTERS
    public String GetFirstName(){
        return firstName;
    }
    public String GetLastName(){
        return lastName;
    }
    public String GetAddress(){
        return address;
    }
    public int GetIDNum(){
        return IDNum;
    }
    public double getFineAmount(){
        return fineAmount;
    }
    //END GETTERS

    //Prints relevant information
    public String toString(){
        return IDNum + "\t\t\t\t\t" + firstName + lastName + address + "\t" + fineAmount;
    }
}
