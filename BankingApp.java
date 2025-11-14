import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class BankingApp {
  static Scanner scanner = new Scanner(System.in);
  static Random random = new Random();

  // Global Variables
  // The first index of the accountCredentials is reserved for account Ids.
  // The second index for PIN, and the third for the Account Balance.
  static boolean[] accountExists = new boolean[100];
  static int[][] accountCredentials = new int[100][4];
  static String[] accountNames = new String[100];
  static String[] securityAnswers = new String[100];
  static String securityQuestion =
      "What's your favorite car and in what color? (Format: <Car color> <Car brand>)";
  static int totalAccounts = 0;
  static int userID;
  static final int idIndex = 0, pinIndex = 1, balanceIndex = 2, transferIDIndex = 3;

  // Main Method
  public static void main(String[] args) {

    // Load Previously Saved Data
    loadSavedData();

    int chosenOption = 0;

    // Loop for the choice in the first menu
    do {
      System.out.println("╔═════════════════════════════════════════════╗");
      System.out.println("║           BANKING MANAGEMENT SYSTEM         ║");
      System.out.println("╚═════════════════════════════════════════════╝");
      System.out.print(
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
          createAccount();
          totalAccounts++;
          break;
        case 2:
          System.out.print("Enter your Account ID: ");
          userID = scanner.nextInt();
          login(userID);
          break;
        case 3:
          System.out.print("Enter your Account ID: ");
          userID = scanner.nextInt();
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

    // Save Changes in the Data
    saveData();
  } // Main Method Closed

  // Create Account Method
  public static void createAccount() {

    scanner.nextLine();
    int indexNumber = totalAccounts;

    // Account Name
    System.out.print("Enter Account Holder Name: ");
    accountNames[totalAccounts] = scanner.nextLine();

    // Account Passwords
    boolean matchingPINs = false;
    int finalPIN = 0;
    String userPINs = "", userPINsConfirm = "";

    do {
      System.out.print("Enter new PIN (4 or 8 digits only): ");
      userPINs = scanner.nextLine();

      // Checking Validity of the PIN
      if (checkingDigit(userPINs) == true && (userPINs.length() == 4 || userPINs.length() == 8)) {
        System.out.print("Confirm the PIN: ");
        userPINsConfirm = scanner.nextLine();
      } else {
        System.out.println("Use the correct format for the PIN.");
        createAccount();
      }

      // Check if the Passwords match
      if (userPINs.equals(userPINsConfirm)) {
        matchingPINs = true;
        finalPIN = Integer.parseInt(userPINs);
      } else {
        matchingPINs = false;
        System.out.print("PINs don't match!\nTry again.");
      }
    } while (matchingPINs == false); // Close password loop

    // Entry of the new account into the System
    accountCredentials[indexNumber][idIndex] = accountIDToBeAssigned();
    accountCredentials[indexNumber][pinIndex] = finalPIN;
    accountCredentials[indexNumber][balanceIndex] = 0;
    accountCredentials[indexNumber][transferIDIndex] =
        random.nextInt(1000000, 100000000); // Any random number (10000000 not included)

    // Security Question
    System.out.println(
        "\nAnswer the following security question, in case you forget your PIN in the future:\n"
            + securityQuestion);
    securityAnswers[indexNumber] = scanner.nextLine();

    accountExists[indexNumber] = true;
    System.out.println("Account Successfully Created!");

    // Overview of newly created account
    viewAccountDetails(indexNumber);
  } // Create Account Method Closed

  // Checking Digits Method
  public static boolean checkingDigit(String userPINsString) {
    char currentChar;
    boolean isDigit = true;
    for (int i = 0; i < userPINsString.length(); i++) {
      currentChar = userPINsString.charAt(i);
      if (!Character.isDigit(currentChar)) isDigit = false;
    }

    return isDigit;
  }

  // Assigning Account ID to a Newly Created Account
  public static int accountIDToBeAssigned() {
    int vacantID = totalAccounts;
    for (int i = 0; i < accountCredentials.length; i++) {
      if (accountCredentials[i][idIndex] == 0) {
        vacantID = i + 101;
        break;
      }
    }

    return vacantID;
  }

  // Logging into the account, Method
  public static void login(int accountID) {
    // Index Number
    int indexNumber = accountID - 101;

    if (!accountExists[indexNumber]) {
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

      // Account Blocked
      if (PINAuthenticity != true) {
        System.out.println("Incorrect PIN limit reached!\nAccount deleted.");
        deleteAccount(indexNumber);
        return;
      }
    }

    System.out.println("Successfully logged in");
    loginMenu(indexNumber);
  }

  // Login Menu after Successfully Logging In, Method
  public static void loginMenu(int indexNumber) {
    // Variables Declaration
    int chosenOption = 0;

    // Options Selection
    do {
      System.out.println(
          "\n╔═════════════════════════════════════════════════════╗\n"
              + "║     1. Check Balance                                ║\n"
              + "║     2. Deposit Money                                ║\n"
              + "║     3. Withdraw Money                               ║\n"
              + "║     4. Transfer Money                               ║\n"
              + "║     5. View Account Details                         ║\n"
              + "║     6. Delete Account                               ║\n"
              + "║     7. Exit                                         ║\n"
              + "║     Choose an option:                               ║\n"
              + "║                                                     ║\n"
              + "╚═════════════════════════════════════════════════════╝\n");
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
          transferMoney(indexNumber);
        case 5:
          viewAccountDetails(indexNumber);
          break;
        case 6:
          deleteAccount(indexNumber);
          return;
        case 7:
          break;
        default:
          System.out.println("Please try again! Choose a valid option.");
      } // Close switch
    } while (chosenOption != 7); // Close do-while loop
    System.out.println();
  } // Login Menu Method Closed

  // Check Balance Method
  public static void checkBalance(int indexNumber) {
    System.out.println("Balance:" + accountCredentials[indexNumber][balanceIndex]);
  } // Check Balance Method Closed

  // Deposit Money Method
  public static void depositMoney(int indexNumber) {
    // Prompt the user
    System.out.print("DEPOSIT:\nHow much money do you want to deposit: ");
    int moneyToDeposit = scanner.nextInt();
    System.out.println();

    // Balance Updated
    accountCredentials[indexNumber][balanceIndex] += moneyToDeposit;
  } // Deposit Money Method Closed

  // Withdraw Money Method
  public static void withdrawMoney(int indexNumber) {
    // Prompt the user
    System.out.print("WITHDRAW:\nHow much money do you want to withdraw: ");
    int moneyToWithdraw = scanner.nextInt();
    System.out.println();

    // Balance Updated
    accountCredentials[indexNumber][balanceIndex] -= moneyToWithdraw;
  } // Withdraw Money Method Closed

  // Transfer Money Method
  public static void transferMoney(int indexNumber) {

    System.out.print("Enter the Reciever's Transfer ID: ");
    int transferID = scanner.nextInt();
    int receiverIndex = findReceiverIDIndex(transferID);

    System.out.print("How much money do you want to transfer? ");
    int moneyToTransfer = scanner.nextInt();
    if (receiverIndex != -1) {
      accountCredentials[receiverIndex][balanceIndex] += moneyToTransfer;
      accountCredentials[indexNumber][balanceIndex] -= moneyToTransfer;
      System.out.println(
          "Money Transfered to Mr/Mrs."
              + accountNames[receiverIndex]
              + ": "
              + moneyToTransfer
              + "€");
    }
  }

  // Find the Index Number from Transfer ID Method
  public static int findReceiverIDIndex(int transferID) {
    int receiverIndex = -1;
    boolean transferIDFound = false;

    // For-Loop
    for (int i = 0; i < accountCredentials.length; i++) {
      if (accountCredentials[i][transferIDIndex] == transferID) {
        receiverIndex = i;
        transferIDFound = true;
        break;
      }
    } // For-Loop Closed

    // Check the existance of Receiver's Index
    if (!transferIDFound) {
      System.out.println("No such account found! Please enter a valid Transfer ID.");
    }

    return receiverIndex;
  } // Find Receiver ID Index closed

  // View Account Details Method
  public static void viewAccountDetails(int indexNumber) {
    System.out.println(
        "\nACCOUNT DETAILS:\nAccount ID: "
            + accountCredentials[indexNumber][idIndex]
            + "\nAccount Holder Name: "
            + accountNames[indexNumber]
            + "\nAccount Balance: "
            + accountCredentials[indexNumber][balanceIndex]
            + "\nAuthenticity PIN: "
            + accountCredentials[indexNumber][pinIndex]
            + "\nTransfer ID: "
            + accountCredentials[indexNumber][transferIDIndex]);
  }

  // Delete Account Method
  public static void deleteAccount(int indexNumber) {
    System.out.println("\nACCOUNT DELETION:\nAccount deleted.");
    accountNames[indexNumber] = "";
    accountCredentials[indexNumber][idIndex] = 0;
    accountCredentials[indexNumber][pinIndex] = 0;
    accountCredentials[indexNumber][balanceIndex] = 0;
    accountCredentials[indexNumber][transferIDIndex] = 0;
    securityAnswers[indexNumber] = "";
    accountExists[indexNumber] = false;
  } // Delete Account Method Closed

  // Recover Account Method
  public static void recoverAccount(int accountID) {
    int indexNumber = accountID - 101;

    if (!accountExists[indexNumber]) {
      System.out.println("Account doesn't exist!");
      return;
    }
    System.out.println("RECOVER ACCOUNT:");

    // Checks Validity of the Account
    if (indexNumber < 0 || indexNumber >= totalAccounts) {
      System.out.println("Invalid Account.");
      return;
    }

    scanner.nextLine();
    System.out.println(securityQuestion);
    String answer = scanner.nextLine();

    // Check if the security question's answer is correct
    if (answer.equalsIgnoreCase(securityAnswers[indexNumber])) {
      System.out.println("Your PIN is: " + accountCredentials[indexNumber][pinIndex]);
    } else {
      System.out.println("Incorrect answer.");
    }
  } // Recover Account Method Closed

  // File Management Load Saved Data
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

        accountNames[i] = fileReader.nextLine().trim();
        i++;
      }
      dataFile.close();
    } catch (Exception e) {
      System.out.println("An error Occurred: " + e.getMessage());
      return;
    }

    totalAccounts = i;
  } // Load Saved Data Method Closed

  // Save New Data
  static void saveData() {
    try {
      FileOutputStream datafile = new FileOutputStream("accountsData.txt");
      PrintWriter fileWriter = new PrintWriter(datafile);
      // cleared the file before writing data, and put the updated data again in the file.
      for (int i = 0; i < totalAccounts; i++) {
        fileWriter.print(accountCredentials[i][idIndex] + " ");
        fileWriter.print(accountCredentials[i][pinIndex] + " ");
        fileWriter.print(accountCredentials[i][balanceIndex] + " ");
        fileWriter.println(accountNames[i]);
      }
      fileWriter.close();

    } catch (Exception e) {
      System.out.println("An error Occurred: " + e.getMessage());
      return;
    }
  } // end of saveData method
} // BankingApp Class Closed
