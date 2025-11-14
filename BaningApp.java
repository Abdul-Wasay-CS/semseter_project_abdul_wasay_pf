import java.io.*;
import java.util.*;

public class BankingApp3{
  static Scanner scanner = new Scanner(System.in);

  // Global Variables
  // The first index of the accountCredentials is reserved for account Ids.
  // The second index for PIN, and the third for the Account Balance.
  static int[][] accountCredentials = new int[100][3];
  static String[][] accountNames = new String[100][1];
  static String[] securityQuestions = new String[100];
  static int totalAccounts = 0;
  static final int idIndex = 0, pinIndex = 1, balanceIndex = 2;
  static final int nameIndex = 0; // for account name

  // Main Method
  public static void main(String[] args) {

    loadSavedData();

    int chosenOption = 0;

    // TODO: Put this in a method named showmain menu

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
      chosenOption = scanner.nextInt();

      switch (chosenOption) {
        case 1:
          createAccount(totalAccounts);
          totalAccounts++;
          break;
        case 2:
          System.out.print("Enter your Account ID: ");
          int userID = scanner.nextInt();
          login(userID);
          break;
        case 3:
          System.out.print("Enter your Account ID: ");
          int userID2 = scanner.nextInt();
          recoverAccount(userID2);
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
    scanner.nextLine();
    System.out.print("Enter Account Holder Name: ");
    accountNames[indexNumber][nameIndex] = scanner.nextLine();

    // Account Passwords
    boolean matchingPINs = false;
    String finalPIN = "";
    do {

      System.out.print("Enter new PIN: ");
      String userPINs = scanner.nextLine();

      System.out.print("Confirm the PIN: ");
      String userPINsConfirm = scanner.nextLine();

      // Check if the Passwords match
      if (userPINs.equals(userPINsConfirm)) {
        matchingPINs = true;
        finalPIN = userPINs;
      } else {
        matchingPINs = false;
        System.out.print("PINs don't match!\nTry again.");
      }

    } while (matchingPINs == false); // Close password loop

    // Entry of the new account into the System
    accountCredentials[indexNumber][idIndex] = indexNumber + 101;
    accountCredentials[indexNumber][pinIndex] = Integer.parseInt(finalPIN);
    accountCredentials[indexNumber][balanceIndex] = 0;

    // TODO Gotta check this one to change the value of global variable
    // Security Question
    scanner.nextLine();
    System.out.print(
        "Answer the following security question, in case you forget your PIN in the future: ");
    securityQuestions[indexNumber] = scanner.nextLine();

    // Overview of newly created account
    viewAccountDetails(indexNumber);
  } // Create Account Method Closed

  // Logging into the account, Method
  public static void login(int accountID) {
    // Index Number
    int indexNumber = accountID - 101;

    if (indexNumber < 0 || indexNumber >= totalAccounts) {
      System.out.println("Invalid Account.");
      return;
    }

    // Prompt user to enter password
    System.out.print("Enter your PIN: ");
    int userPIN = scanner.nextInt();

    // Check password authenticity
    if (userPIN != accountCredentials[indexNumber][pinIndex]) {
      boolean PINAuthenticity = false;
      int triesRemaining = 3;

      // Incorrect Password Loop
      while (PINAuthenticity == false && triesRemaining > 0) {
        System.out.println("Incorrect PIN! " + triesRemaining + " remaining.");
        System.out.print("Enter your PIN again: ");
        userPIN = scanner.nextInt();
        triesRemaining--;

        // Check PIN Authenticity again
        if (userPIN == accountCredentials[indexNumber][pinIndex]) {
          PINAuthenticity = true;
        }
      }

      // Account Blocked TODO A way to delete or block an account
      if (PINAuthenticity != true) {
        System.out.println("Incorrect PIN limit reached!\nAccount locked.");
        return;
      }
    }

    System.out.print("Successfully logged in");
    loginMenu(indexNumber);
  }

  // Login Menu after Successfully Logging In, Method
  public static void loginMenu(int indexNumber) {
    // Variables Declaration
    int chosenOption = 0;

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
          viewAccountDetails(indexNumber);
          break;
        case 5:
          deleteAccount(indexNumber);
          return;
        default:
          System.out.println("Please try again! Choose a valid option.");
      } // Close switch
    } while (chosenOption != 6); // Close do-while loop
  } // Login Menu Method Closed

  // Check Balance Method
  public static void checkBalance(int indexNumber) {
    System.out.println("Balance: " + accountCredentials[indexNumber][balanceIndex]);
  } // Check Balance Method Closed

  // Deposit Money Method
  public static void depositMoney(int indexNumber) {
    // Prompt the user
    System.out.print("How much money do you want to deposit: ");
    int moneyToDeposit = scanner.nextInt();

    // Balance Updated
    accountCredentials[indexNumber][balanceIndex] += moneyToDeposit;
  } // Deposit Money Method Closed

  // Withdraw Money Method
  public static void withdrawMoney(int indexNumber) {
    // Prompt the user
    System.out.print("How much money do you want to withdraw: ");
    int moneyToWithdraw = scanner.nextInt();

    // Balance Updated
    accountCredentials[indexNumber][balanceIndex] -= moneyToWithdraw;
  } // Withdraw Money Method Closed

  // View Account Details Method
  public static void viewAccountDetails(int indexNumber) {
    System.out.println(
        "Account ID: "
            + accountCredentials[indexNumber][idIndex]
            + "\nAccount Holder Name: "
            + accountNames[indexNumber][nameIndex]
            + "\nAccount Balance: "
            + accountCredentials[indexNumber][balanceIndex]
            + "\nAuthenticity PIN: "
            + accountCredentials[indexNumber][pinIndex]);
  }

  // Delete Account Method
  public static void deleteAccount(int indexNumber) {
    System.out.println("Account deleted.");
    accountCredentials[indexNumber][balanceIndex] = 0;
  } // Delete Account Method Closed

  // Recover Account Method
  public static void recoverAccount(int accountID) {
    int indexNumber = accountID - 101;

    // Checks Validity of the Account
    if (indexNumber < 0 || indexNumber >= totalAccounts) {
      System.out.println("Invalid Account.");
      return;
    }

    System.out.println(securityQuestions);
    String answer = scanner.nextLine();

    // Check if the security question's answer is correct
    if (answer == securityQuestions[indexNumber]) {
      System.out.println("Your PIN is: " + accountCredentials[indexNumber][pinIndex]);
    } else {
      System.out.println("Incorrect answer.");
    }
  } // Recover Account Method Closed

  public static void loadSavedData() {
    totalAccounts = 0;
    // loading saved data into an array
    try {
      int i = 0;
      FileInputStream dataFile = new FileInputStream("accountsData.txt");
      Scanner fileReader = new Scanner(dataFile);
      while (fileReader.hasNext()) {
        accountCredentials[i][idIndex] = fileReader.nextInt();
        accountCredentials[i][pinIndex] = fileReader.nextInt();
        accountCredentials[i][balanceIndex] = fileReader.nextInt();

        fileReader.nextInt(); // clearing the buffer created by nextInt()

        accountNames[i][nameIndex] = fileReader.nextLine().trim();
        i++;
      }//end of while
      totalAccounts = i;
      dataFile.close();
    } catch (Exception e) {
      System.out.println("An error Occurred: " + e.getMessage());
      return;
    }

    
  } // Load Saved Data Method Closed

  static void saveData() {
    try {
      FileOutputStream datafile = new FileOutputStream("accountsData.txt");
      PrintWriter fileWriter = new PrintWriter(datafile);
      // cleared the file before writing data, and put the updated data again in the file.
      for (int i = 0; i < totalAccounts; i++) {
        fileWriter.print(accountCredentials[i][idIndex] + " ");
        fileWriter.print(accountCredentials[i][pinIndex] + " ");
        fileWriter.print(accountCredentials[i][balanceIndex] + " ");
        fileWriter.println(accountNames[i][nameIndex]);
      }
      fileWriter.close();

    } catch (Exception e) {
      System.out.println("An error Occurred: " + e.getMessage());
      return;
    }
  } // end of saveData method
} // BankingApp Class Closed
