import java.io.*;
import java.util.*;
public class BankingApp {
  static Scanner scanner = new Scanner(System.in);
  static Random random = new Random();

  //Colors for console output using ANSI escape codes

  // --- NORMAL FOREGROUND COLORS ---
  public static final String BLACK = "\u001B[30m";
  public static final String RED = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";
  public static final String YELLOW = "\u001B[33m";
  public static final String BLUE = "\u001B[34m";
  public static final String MAGENTA = "\u001B[35m";
  public static final String CYAN = "\u001B[36m";
  public static final String WHITE = "\u001B[37m";

  // --- BRIGHT FOREGROUND COLORS ---
  public static final String BRIGHTBLACK = "\u001B[90m";
  public static final String BRIGHTRED = "\u001B[91m";
  public static final String BRIGHTGREEN = "\u001B[92m";
  public static final String BRIGHTYELLOW = "\u001B[93m";
  public static final String BRIGHTBLUE = "\u001B[94m";
  public static final String BRIGHTMAGENTA = "\u001B[95m";
  public static final String BRIGHTCYAN = "\u001B[96m";
  public static final String BRIGHTWHITE = "\u001B[97m";

  // --- STYLES & RESET ---
  public static final String RESET = "\u001B[0m";
  public static final String BOLD = "\u001B[1m";
  public static final String UNDERLINE = "\u001B[4m";

  //Global Arrays, The first index of the accountCredentials is reserved for account Ids. The second index for PIN, and the third for the Account Balance.
  static ArrayList <Boolean> accountExists = new ArrayList<>();
  static ArrayList <ArrayList <Integer>> accountCredentials = new ArrayList<>();
  static ArrayList <String> accountNames = new ArrayList<>();
  static int totalAccounts = 0;
  static int userID;
  static final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;



  // Main Method, interruped Exception thrown due to use of thread.sleep()
  public static void main(String[] args) throws InterruptedException 
  {

    // Load Previously Saved Data
    loadSavedData();
    for(int i=1; i<=5; i++)
    {
      final int DELAYTIME = 60;
      System.out.print("\rLoading accuont id.....          ");
      Thread.sleep(DELAYTIME);
      System.out.print("\rLoading account pin.....         ");
      Thread.sleep(DELAYTIME);
      System.out.print("\rLoading account balance......    ");
      Thread.sleep(DELAYTIME);
      System.out.print("\rLoading account transfer id......");
      Thread.sleep(DELAYTIME);
      System.out.print("\rLoading account names......      ");
      Thread.sleep(DELAYTIME);
      System.out.print("\r                                 ");   // to clear out the last leftover message
    }
    System.out.println();
    System.out.println(BOLD+BLUE+"╔═══════════════════════════════════════════════════════════════════════╗"+RESET);
    System.out.println(BOLD+BLUE+"║"+RESET+"                       "+BLUE+"BANKING MANAGEMENT SYSTEM"+RESET+"                       "+BOLD+BLUE+"║"+RESET);
    System.out.println(BOLD+BLUE+"╚═══════════════════════════════════════════════════════════════════════╝"+RESET);   

    System.out.println("║Welcome To the System, Please Select what do you want to do: ");

    int chosenOption = 0;

    // Loop for the choice in the first menu
    do {

      System.out.println(
                BOLD+YELLOW+"╔═════════════════════════════════════════════╗\n"+RESET
              + BOLD+YELLOW+"║"+RESET+"    "+WHITE+"1. Create New Account "+RESET+"                   "+YELLOW+"║"+RESET+"\n"
              + BOLD+YELLOW+"║"+RESET+"    "+WHITE+"2. Log Into Account"+RESET+"                      "+YELLOW+"║"+RESET+"\n"
              + BOLD+YELLOW+"║"+RESET+"    "+WHITE+"3. Recover Account "+RESET+"                      "+YELLOW+"║"+RESET+"\n"
              + BOLD+YELLOW+"║"+RESET+"    "+RED+"4. Exit            "+RESET+"                      "+YELLOW+"║"+RESET+"\n"
              + BOLD+YELLOW+"╚═════════════════════════════════════════════╝"+RESET+"\n"
              + BRIGHTBLUE+"Choose an option: "+RESET);

      // Choice Switch
      chosenOption = scanner.nextInt();

      switch (chosenOption) {
        case 1:
          createAccount();
          break;
        case 2:
          System.out.print("Enter your Account ID: ");
          userID = scanner.nextInt();
          scanner.nextLine();
          login(userID);
          break;
        case 3:
          System.out.print("Enter your Account ID: ");
          userID = scanner.nextInt();
          scanner.nextLine();
          recoverAccount(userID);
          break;
        case 4:
          System.out.println("Goodbye!");
          saveData();
          return;
        default:
          System.out.println("Please try again! Choose a valid option.");
      } // Close the choice switch
    // Save Changes in the Data
    
    } while (true); // Close the first menu loop

  } // Main Method Closed

  // Create Account Method
  public static void createAccount() {
    int indexNumber = accountIDToBeAssigned() - 101;
    scanner.nextLine();

    // Account Name
    System.out.print("Enter Account Holder Name: ");
    accountNames.add(scanner.nextLine());// put it to the last of the accountName arraylist

    // Account Passwords
    boolean matchingPINs;
    int finalPIN = 0;
    String userPINs, userPINsConfirm;

    do {
      System.out.print("Enter new PIN (4 or 8 digits only): ");
      userPINs = scanner.nextLine();

      // Checking Validity of the PIN
      if (checkingDigit(userPINs) == true && (userPINs.length() == 4 || userPINs.length() == 8)) 
      {
        System.out.print("Confirm the PIN: ");
        userPINsConfirm = scanner.nextLine();

        if(userPINs.equals(userPINsConfirm))
        {
          matchingPINs = true;
          finalPIN = Integer.parseInt(userPINs);
        } else 
        {
          matchingPINs = false;
          System.out.print("PINs don't match!\nTry again.");
        }
      } 
      else 
      {
        System.out.println("Use the correct format for the PIN.");
        matchingPINs = false;
      }

    } while (matchingPINs == false); // Close password loop

    // Entry of the new account into the System
    //for dynamic id assignment, put condition if  
    accountCredentials.add(new ArrayList<Integer>()).add(accountIDToBeAssigned());
    accountCredentials.get(indexNumber).add(finalPIN);
    accountCredentials.get(indexNumber).add(0);
    accountCredentials.get(indexNumber).add(random.nextInt(1000000, 100000000)); // Any random number (10000000 not included)
    accountExists.add(true);
    System.out.println("Account Successfully Created!");
    if(indexNumber == totalAccounts) totalAccounts++;

    // Overview of newly created account
    viewAccountDetails(indexNumber);
  } // Create Account Method Closed

  // Checking Digits Method
  public static boolean checkingDigit(String userPINsString) {
    boolean isDigit = true;
    for (int i = 0; i < userPINsString.length(); i++) {
      if (!Character.isDigit(userPINsString.charAt(i))) return false;
    }
    return true;
  }

  // Assigning Account ID to a Newly Created Account
  public static int accountIDToBeAssigned() {
    int vacantID = totalAccounts + 101;
    for (int i = 0; i < accountCredentials.length; i++) {
      if (accountExists.get(i) == false) {
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
    System.out.print(accountExists.get(indexNumber));

    if (indexNumber < 0 || indexNumber >= accountExists.length || !accountExists[indexNumber]) {
      System.out.println("Invalid Account.");
      return;
    }

    // Prompt user to enter password
    System.out.print("Enter your PIN: ");
    int userPIN = scanner.nextInt();
    scanner.nextLine();

    // Check password authenticity
    if (userPIN != accountCredentials.get(indexNumber).get(PININDEX)) {
      boolean PINAuthenticity = false;
      int triesRemaining = 3;

      // Incorrect Password Loop
      while (PINAuthenticity == false && triesRemaining > 0) {
        System.out.println("Incorrect PIN! " + triesRemaining + " remaining.");
        System.out.print("Enter your PIN again: ");
        userPIN = scanner.nextInt();
        scanner.nextLine();
        triesRemaining--;

        // Check PIN Authenticity again
        if (userPIN == accountCredentials.get(indexNumber).get(PININDEX)) {
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
      scanner.nextLine();
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
          break;
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
    System.out.println("Balance:" + accountCredentials.get(indexNumber).get(BALANCEINDEX));
  } // Check Balance Method Closed

  // Deposit Money Method
  public static void depositMoney(int indexNumber) {
    // Prompt the user
    System.out.print("DEPOSIT:\nHow much money do you want to deposit: ");
    int moneyToDeposit = scanner.nextInt();
    scanner.nextLine();

    if(moneyToDeposit <= 0)
    {
      System.out.print("Deposit amount must be positive.");
      return;
    }

    System.out.println();

    // Balance Updated
    accountCredentials.get(indexNumber).set(BALANCEINDEX,accountCredentials.get(indexNumber).get(BALANCEINDEX)+moneyToDeposit);
  } // Deposit Money Method Closed

  // Withdraw Money Method
  public static void withdrawMoney(int indexNumber) {
    // Prompt the user
    System.out.print("WITHDRAW:\nHow much money do you want to withdraw: ");
    int moneyToWithdraw = scanner.nextInt();
    scanner.nextLine();

    if(moneyToWithdraw <= 0)
    {
      System.out.print("Withdraw amount can't be zero or negative.");
      return;
    }

    if(moneyToWithdraw > accountCredentials.get(indexNumber).get(BALANCEINDEX))
    {
      System.out.print("Insufficient Balance.");
    }

    System.out.println();

    // Balance Updated
    accountCredentials.get(indexNumber).set(BALANCEINDEX, accountCredentials.get(indexNumber).get(BALANCEINDEX) - moneyToWithdraw);
  } // Withdraw Money Method Closed

  // Transfer Money Method
  public static void transferMoney(int indexNumber) {

    System.out.print("Enter the Reciever's Transfer ID: ");
    int transferID = scanner.nextInt();
    scanner.nextLine();
    int receiverIndex = findReceiverIDIndex(transferID);

    System.out.print("How much money do you want to transfer? ");
    int moneyToTransfer = scanner.nextInt();
    scanner.nextLine();
    if (receiverIndex != -1) {
      accountCredentials[receiverIndex][BALANCEINDEX] += moneyToTransfer;
      accountCredentials[indexNumber][BALANCEINDEX] -= moneyToTransfer;
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
      if (accountCredentials[i][TRANSFERINDEX] == transferID) {
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
            + accountCredentials[indexNumber][IDINDEX]
            + "\nAccount Holder Name: "
            + accountNames[indexNumber]
            + "\nAccount Balance: "
            + accountCredentials[indexNumber][BALANCEINDEX]
            + "\nTransfer ID: "
            + accountCredentials[indexNumber][TRANSFERINDEX]);
  }

  // Delete Account Method
  public static void deleteAccount(int indexNumber) {
    System.out.println("\nACCOUNT DELETION:\nAccount deleted.");
    accountNames[indexNumber] = "";
    accountCredentials[indexNumber][IDINDEX] = 0;
    accountCredentials[indexNumber][PININDEX] = 0;
    accountCredentials[indexNumber][BALANCEINDEX] = 0;
    accountCredentials[indexNumber][TRANSFERINDEX] = 0;
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
    //TODO: Add security number verification here in the future.
  } // Recover Account Method Closed


  // File Management Load Saved Data
  public static void loadSavedData() {
    int i =0;
    totalAccounts = 0;
    // loading saved data into an array
    try {
        FileInputStream dataFile = new FileInputStream("accountsData.csv");
        Scanner fileReader = new Scanner(dataFile);
        while (fileReader.hasNextLine()) {
            //read line by line
            String line = fileReader.nextLine();
            //split the line into parts by comma
            String[] parts = line.split(",");
            //assign the parts to respective arrays
            accountCredentials[i][IDINDEX] = Integer.parseInt(parts[0].trim());
            accountCredentials[i][PININDEX] = Integer.parseInt(parts[1].trim());
            accountCredentials[i][BALANCEINDEX] = Integer.parseInt(parts[2].trim());
            accountCredentials[i][TRANSFERINDEX] = Integer.parseInt(parts[3].trim());
            accountExists[i] = Boolean.parseBoolean(parts[4].trim());
            accountNames[i] = parts[5].trim();
            i++;
        }
      //counting the totalaccounts
      for(int j=0; j<accountExists.length; j++)
      {
        if(accountExists[j])
          totalAccounts++;
      }
      fileReader.close();
    } catch (Exception e) {
      System.out.println("An error Occurred: " + e);
    }
    
  } // Load Saved Data Method Closed

  // Save New Data
  public static void saveData() {
    try {
      FileOutputStream datafile = new FileOutputStream("accountsData.csv",false);
      PrintWriter fileWriter = new PrintWriter(datafile);
      // cleared the file before writing data, and put the updated data again in the file.
      for (int i = 0; i < totalAccounts; i++) {
        fileWriter.print(accountCredentials[i][IDINDEX] + ",");
        fileWriter.print(accountCredentials[i][PININDEX]+",");
        fileWriter.print(accountCredentials[i][BALANCEINDEX]+",");
        fileWriter.print(accountCredentials[i][TRANSFERINDEX]+",");
        fileWriter.print(accountExists[i]+",");
        fileWriter.println(accountNames[i]);
      }
      fileWriter.close();

    } catch (Exception e) {
      System.out.println("An error Occurred: " + e.getMessage());
    }
  } // end of saveData method
} //class
