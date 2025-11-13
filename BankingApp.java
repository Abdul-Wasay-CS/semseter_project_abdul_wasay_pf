import java.util.Scanner;
import java.io.*;
import java.util.*;
// Class
public class BankingSelf {
  static Scanner scanner = new Scanner(System.in);

  // Global Variables
  //The first index of the accountCredentials is reserved for account Ids. The second index for PIN,and the third for the Account Balance.
  static int[][] accountCredentials = new int[100][3];
  static String[][] accountNames = new String[100][1];
  static int totalAccounts = 0;
  static final int idIndex = 0, pinIndex = 1, balanceIndex = 2;
  static final int nameIndex = 1; // for account name

  //to store the account data permanently  
  static FileOutputSream dataFile = new FileOutputSream("accountsData.txt", true);
  static PrintStream fileWriter = new PrintStream(dataFile);
  static Scanner fileReader = new Scanner(dataFile); 


  // Main Method
  public static void main(String[] args) {

    loadSavedData();
    


    int chosenOption;

    //TODO: Put this in a method named showmain  menu

    // Loop for the choice in the first menu
    do {
      System.out.println("╔═════════════════════════════════════════════╗");
      System.out.println("║           BANKING MANAGEMENT SYSTEM         ║");
      System.out.println("╚═════════════════════════════════════════════╝");
      System.out.println(
          "\n"
              + "1. Create New Account\n"
              + "2. Log Into Account\n"
              + "3. Recover Account\n"
              + "4. Exit\n"
              + "Choose an option: ");

      // Choice Switch
      switch (chosenOption) {
        case 1:
          createAccount(totalAccounts);
          totalAccounts++;
          break;
        case 2:
          System.out.print("Enter your Account ID");
          int userID = scanner.nextInt();
          login(userID);
          break;
        case 3:
          System.out.print("Enter your Account ID: ");
          int userID = scanner.nextInt();
          recoverAccount(userID);
          break;
        case 4:
          System.out.println("Goodbye!");
          System.exit(0);
          break;
        default:
          System.out.println("Please try again! Choose a valid option.");
      } // Close the choice switch
    } while (true); // Close the first menu loop
  } // Main Method Closed




  // Create Account Method
  public static void createAccount(int indexNumber) {
    // Account Name
    System.out.print("Enter Account Holder Name: ");
    accountNames[indexNumber] = scanner.nextLine();

    // Account Passwords
    boolean matchingPINs = true;
    do {

      System.out.print("Enter new PIN: ");
      String userPINs = scanner.nextLine();

      System.out.print("Confirm the PIN: ");
      String userPINsConfirm = scanner.nextLine();

      // Check if the Passwords match
      if (userPINs != userPINsConfirm) {
        matchingPINs = false;
        System.out.print("PINs don't match!\nTry again.");
      }

    } while (matchingPINs == false); // Close password loop

    // Entry of the new account into the System
    accountCredentials[indexNumber][0] = indexNumber + 101;
    accountCredentials[indexNumber][1] = userPIN;

    // TODO Gotta check this one to change the value of global variable
    // Security Question
    System.out.print(
        "Answer the following security question, in case you forget your PIN in the future: ");
    String securityQuestion = scanner.nextLine;

    // Overview of newly created account
    viewAccount(accountCredentials[indexNumber][0]);
  } // Create Account Method Closed

  // Logging into the account, Method
  public static void login(int accountID) {
    // Index Number
    int indexNumber = accountID - 101;

    // Prompt user to enter password
    System.out.print("Enter your PIN. ");
    int userPIN = scanner.nextInt();

    // Check password authenticity
    if (userPIN != accountCredentials[indexNumber][1]) {
      boolean PINAuthenticity = false;
      int triesRemaining = 0;

      // Incorrect Password Loop
      while (PINAuthenticity == false && triesRemaining >= 0) {
        System.out.println("Incorrect PIN! " + triesRemaining + " remaining.");
        System.out.print("Enter your PIN again: ");
        userPIN = scanner.nextInt();

        // Check PIN Authenticity again
        if (userPIN == accountCredentials[indexNumber][1]) {
          PINAuthenticity = true;
        }
      }

      // Account Blocked TODO A way to delete or block an account
      if (PINAuthenticity != true) {
        System.out.println("Incorrect PIN limit reached!\nAccount deleted.");
        System.exit(0);
      } else {
        System.out.print("Successfully logged in");
        loginMenu(indexNumber);
      }
    }
  }

  // Login Menu after Successfully Logging In, Method
  public static void loginMenu(int indexNumber) {
    // Variables Declaration
    int accountID = indexNumber + 101;
    int chosenOption;

    // Options Selection
    do {
      System.out.println(
                "╔═════════════════════════════════════════════════════╗\n"
              + "║     1. Check Balance                                ║\n"
              + "║     2. Deposit Money                                ║\n"
              + "║     3. Withdraw Money                               ║\n"
              + "║     4. View Account Details                         ║\n"
              + "║     5. Delete Account                               ║\n"
              + "║     6. Exit                                         ║\n"
              + "║     Choose an option:                               ║\n"
              + "║                                                     ║\n"
              + "╚═════════════════════════════════════════════════════╝");
      chosenOption = scanner.nextInt();
      switch (chosenOption) {
        case 1:
          checkBalance(indexNumber);
          break;
        case 2:
          depositMoney(indexNumber);
          break;
        case 3:
          withdrawMoney(indexNumber);
          break;
        case 4:
          viewAccount(indexNumber);
          break;
        case 5:
          deleteAccount(indexNumber);
          break;
        default:
          System.out.println("Please try again! Choose a valid option.");
      } // Close switch
    } while (chosenOption != 6); // Close do-while loop
  } // Login Menu Method Closed

  // Check Balance Method
  public static void checkBalance(int indexNumber) {
    System.out.println("Balance: " + accountBalances[indexNumber]);
  } // Check Balance Method Closed

  // Deposit Money Method
  public static void depositMoney(int indexNumber) {
    // Prompt the user
    System.out.print("How much money do you want to deposit: ");
    double moneyToDeposit = scanner.nextDouble();

    // Balance Updated
    accountBalances[indexNumber] += moneyToDeposit;
  } // Deposit Money Method Closed

  // Withdraw Money Method
  public static void withdrawMoney(int indexNumber) {
    // Prompt the user
    System.out.print("How much money do you want to withdraw: ");
    double moneyToWithdraw = scanner.nextDouble;

    // Balance Updated
    accountBalances[indexNumber] -= moneyToWithdraw;
  } // Withdraw Money Method Closed

  // View Account Details Method
  public static void viewAccountDetails(int indexNumber) {
    System.out.println(
        "Account ID: "
            + accountCredentials[indexNumber][0]
            + "\nAccount Holder Name: "
            + accountNames[indexNumber]
            + "\nAccount Balance: "
            + accountBalances[indexNumber]
            + "\nAuthenticity PIN: "
            + accountCredentials[indexNumber][1]);
  }

  // public static void deleteAccount(){} //TODO
  // public static void recoverAccount(int accountID){} //TODO



  public static void loadSavedData()
  {
    int i = 0;
    while(fileReader.hasNext())
    {
      try{
        accountCredentials[i][idIndex] = fileReader.nextInt();
        accountCredentials[i][pinIndex] = fileReader.nextInt();
        accountCredentials[i][balanceIndex] = fileReader.nextInt();

        fileReader.nextInt(); //clearing the buffer created by nextInt()
        
        accountNames[i][nameIndex] = fileReader.nextLine.trim();
        i++;
      } 
      catch (Exception e)
      {
        System.out.println("An error Occurred: " +e.getMessage());
        break;
      }
    }
  }
}
