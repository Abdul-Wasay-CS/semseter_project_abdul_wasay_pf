import java.util.*;
import java.io.*;
import static java.lang.System.out;

public class Main {

  static Scanner scanner = new Scanner(System.in);
  static Random random = new Random();

  // colors
  public static final String BLACK = "\u001B[30m";
  public static final String RED = "\u001B[31m";
  public static final String GREEN = "\u001B[32m";
  public static final String YELLOW = "\u001B[33m";
  public static final String BLUE = "\u001B[34m";
  public static final String MAGENTA = "\u001B[35m";
  public static final String CYAN = "\u001B[36m";
  public static final String WHITE = "\u001B[37m";
  public static final String BRIGHTBLACK = "\u001B[90m";
  public static final String BRIGHTRED = "\u001B[91m";
  public static final String BRIGHTGREEN = "\u001B[92m";
  public static final String BRIGHTYELLOW = "\u001B[93m";
  public static final String BRIGHTBLUE = "\u001B[94m";
  public static final String BRIGHTMAGENTA = "\u001B[95m";
  public static final String BRIGHTCYAN = "\u001B[96m";
  public static final String BRIGHTWHITE = "\u001B[97m";
  public static final String RESET = "\u001B[0m";
  public static final String BOLD = "\u001B[1m";
  public static final String UNDERLINE = "\u001B[4m";

  // Owner Data Variables
  static int[] ownerCredentials = { 2, 72766 };
  static String ownerName = "Solitary Monarch";

  // Admin Data Variables
  // Index 0,1 are id number and pin
  static int[][] adminCredentials = new int[5][2];
  static String[] adminName = new String[5];
  static boolean[] adminExists = new boolean[5];

  // User Data Variables
  // List of [id, pin, balance, transferId]
  static ArrayList<ArrayList<Integer>> accountCredentials = new ArrayList<>();
  static ArrayList<String> securityQuestion = new ArrayList<>();
  static ArrayList<String> accountNames = new ArrayList<>();
  static ArrayList<Boolean> accountExists = new ArrayList<>();
  static ArrayList<Boolean> blocked = new ArrayList<>();
  static int totalAccounts = 0;

  // Logs
  static ArrayList<String> logs = new ArrayList<>();

  // Constants
  static final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;

  // ================== MAIN ==================
  public static void main(String[] args) throws InterruptedException {
    loadlogs();
    int choice = -1;
    do {
      try {
        out.print("""
            ==================Bank Of Valhalla==================
            |                                                  |
            | What do you want to login as?                    |
            |                                                  |
            | 1) Owner                 3) Customer             |
            | 2) Admin                 4) Recover Account      |
            |                                                  |
            | 5) Exit                                          |
            |                                                  |
            ====================================================

            Enter Your Choice: """);

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
          case 1 -> {
            loadingScreen("Loading Owner Data");
            int ownerID, ownerPass;
            while (true) {
              try {
                out.print("Enter your ID: ");
                ownerID = scanner.nextInt();
                scanner.nextLine();
                out.print("Enter your Passkey: ");
                ownerPass = scanner.nextInt();
                scanner.nextLine();
                break;
              } catch (InputMismatchException e) {
                out.println("Please enter only an integer.");
                loadingScreen("Going back");
                scanner.nextLine();
              }
            }
            if (ownerID == ownerCredentials[0] && ownerPass == ownerCredentials[1]) {
              ownerMenu();
            } else {
              out.println("Access Denied!");
            }
          }
          case 2 -> {
            loadAdminData();
            out.print("Admin ID: ");
            int adminID = scanner.nextInt();
            out.print("Passkey: ");
            int adminPass = scanner.nextInt();
            scanner.nextLine();
            adminMenu(adminID, adminPass);
          }
          case 3 -> {
            loadCustomerData();
            customerMenu();
          }
          case 4 -> {
            out.print("Account ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            recoverAccount(id);
          }
          case 5 -> {
            // exit
          }
          default -> out.println("Incorrect Choice!");
        }

      } catch (Exception e) {
        out.println(e.toString());
        scanner.nextLine();
      }
    } while (choice != 5);

    out.println("""
        ====================================================
        |                                                  |
        | Thank you for visiting the Abyss                 |
        |                                                  |
        | Good Bye!                                        |
        |                                                  |
        ====================================================""");
  }

  // ================== OWNER ==================
  public static void ownerMenu() throws InterruptedException {
    while (true) {
      try {
        System.out.println("""
            =====================Owner Menu============================
            |                                                         |
            | What do you want to do?                                 |
            |                                                         |
            | 1) Create an Admin account                              |
            | 2) Delete an Admin account                              |
            | 3) View Admins                                          |
            | 4) Exit                                                 |
            ===========================================================
            Enter Your Choice: """);
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
          case 1:
            createAdmin();
            saveAdminData();
            break;
          case 2:
            deleteAdmin();
            saveAdminData();
            break;
          case 3:
            viewAdmins();
            break;
          case 4:
            return;

          default:
            System.out.println("Please select from the given options!");
        }
      } catch (InputMismatchException e) {
        System.out.println("Please Enter An Integer Only! ");
        scanner.nextLine();
      }
    }
  }

  public static void createAdmin() throws InterruptedException {
    int adminIndex;
    while (true) {
      out.print("Enter Admin Name: ");
      String name = scanner.nextLine();
      String PINs;
      String PINsConfirm;
      int finalPIN = 0;
      boolean matchingPINs = false;
      do {
        try {
          out.print("Enter new PIN (4 or 8 digits only): ");
          PINs = scanner.nextLine();
          if ((checkingDigit(PINs)) && (PINs.length() == 4 || PINs.length() == 8)) {
            out.print("Confirm the PIN: ");
            PINsConfirm = scanner.nextLine();
            if (PINs.equals(PINsConfirm)) {
              finalPIN = Integer.parseInt(PINs);
              matchingPINs = true;
            } else {
              out.println("PINs Mismatch. Try Again!");
            }
          } else {
            out.println("Incorrect Format!");
          }
        } catch (Exception e) {
          out.println(e.toString());
        }
      } while (!matchingPINs);

      try {
        int idToAssign = adminIDToBeAssigned();
        if (idToAssign >= 0) {
          adminIndex = idToAssign - 505;
          adminCredentials[adminIndex][0] = idToAssign;
          adminCredentials[adminIndex][1] = finalPIN;
          adminName[adminIndex] = name;
          adminExists[adminIndex] = true;
          out.println("Admin Account Created!");
          logs.add("Admin Account with ID-" + idToAssign + " created.");
        }
        break;
      } catch (Exception e) {
        out.println(e.toString());
        break;
      }
    }
  }

  public static int adminIDToBeAssigned() {
    int idToAssign = -1;
    for (int i = 0; i < 5; i++) {
      if (!adminExists[i]) {
        idToAssign = i + 505;
        break;
      }
    }
    return idToAssign;
  }

  public static void deleteAdmin() throws InterruptedException {
    while (true) {
      try {
        out.print("Admin ID to be Deleted: ");
        int idToDelete = scanner.nextInt();
        scanner.nextLine();
        int indexToDelete = idToDelete - 505;
        if (indexToDelete < 0 || indexToDelete >= adminExists.length || !adminExists[indexToDelete]) {
          out.println("No such admin.");
          return;
        }
        adminCredentials[indexToDelete][0] = 0;
        adminCredentials[indexToDelete][1] = 0;
        adminName[indexToDelete] = "Null";
        adminExists[indexToDelete] = false;
        out.println("Admin Account Deleted!");
        logs.add("Admin Account with ID-" + idToDelete + " deleted.");
        return;
      } catch (Exception e) {
        out.println(e.toString());
        scanner.nextLine();
      }
    }
  }

  // ================== ADMIN MENU ==================
  public static void adminMenu(int adminID, int adminPass) throws InterruptedException {
    int adminIndex = adminID - 505;
    if (adminIndex < 0 || adminIndex >= adminExists.length ||
        !adminExists[adminIndex] ||
        adminPass != adminCredentials[adminIndex][PININDEX]) {
      System.out.println("Wrong Admin Credentials.");
      return;
    }

    int option = -1;
    do {
      try {
        System.out.println("===========Admin Menu===========");
        System.out.println(
            "What do you want to do?\n1) View the logs\n2) Create an account\n3) Delete an account\n4) Block an account\n5) Exit");
        option = scanner.nextInt();
        scanner.nextLine();
        int index;
        switch (option) {
          case 1:
            viewLogs();
            break;
          case 2:
            createAccount();
            break;
          case 3:
            System.out.print("Enter index of the account: ");
            index = scanner.nextInt();
            scanner
                .nextLine();
            deleteAccount(index);
            break;
          case 4:
            System.out.print("Enter index of the account: ");
            index = scanner.nextInt();
            scanner
                .nextLine();
            blockAccount(index);
            break;
          case 5:
            /* exit */
            break;
          default:
            System.out.println("Please try again with a valid option!");
        }
      } catch (Exception e) {
        out.println(e.toString());
        scanner.nextLine();
      }
    } while (option != 5);
  }

  public static void viewLogs() {
    for (int i = logs.size() - 1; i >= 0; i--) {
      try {
        out.println(i + ". " + logs.get(i));
      } catch (Exception e) {
        out.println(e.toString());
      }
    }
  }

  // ================== CUSTOMER CREATION ==================
  public static void createAccount() {
    int indexNumber = accountIDToBeAssigned() - 101;

    while (indexNumber >= accountNames.size())
      accountNames.add("");
    while (indexNumber >= accountCredentials.size())
      accountCredentials.add(new ArrayList<>());
    while (indexNumber >= securityQuestion.size())
      securityQuestion.add("");
    while (indexNumber >= accountExists.size())
      accountExists.add(false);
    while (indexNumber >= blocked.size())
      blocked.add(false);

    out.print("Enter Account Holder Name: ");
    accountNames.set(indexNumber, scanner.nextLine());

    boolean matchingPINs = false;
    int finalPIN = 0;
    String userPINs, userPINsConfirm;

    do {
      try {
        out.print("Enter new PIN (4 or 8 digits only): ");
        userPINs = scanner.nextLine();
        if (checkingDigit(userPINs) && (userPINs.length() == 4 || userPINs.length() == 8)) {
          out.print("Confirm the PIN: ");
          userPINsConfirm = scanner.nextLine();
          if (userPINs.equals(userPINsConfirm)) {
            finalPIN = Integer.parseInt(userPINs);
            matchingPINs = true;
          } else {
            out.println(RED + "PINs don't match! Try again." + RESET);
          }
        } else {
          out.println(RED + "Use the correct format for the PIN (4 or 8 digits only)." + RESET);
        }
      } catch (Exception e) {
        out.println(e.toString());
      }
    } while (!matchingPINs);

    out.println("Answer the following security question for future account recovery:\nWhat's your favourite car name?");
    securityQuestion.set(indexNumber, scanner.nextLine());

    // Ensure 4 elements in this account's list
    ArrayList<Integer> creds = accountCredentials.get(indexNumber);
    while (creds.size() < 4)
      creds.add(0);

    creds.set(IDINDEX, accountIDToBeAssigned());
    creds.set(PININDEX, finalPIN);
    creds.set(BALANCEINDEX, 0);
    creds.set(TRANSFERINDEX, random.nextInt(10000000, 100000000));

    accountExists.set(indexNumber, true);
    out.println(GREEN + "Account Successfully Created!" + RESET);
    String prompt = "A new account created by name " + accountNames.get(indexNumber);
    logs.add(prompt);

    if (indexNumber >= totalAccounts)
      totalAccounts++;

    viewAccountDetails(indexNumber);
  }

  public static void deleteAccount(int index) {
    while (true) {
      try {
        if (index < 0 || index >= accountExists.size() || !accountExists.get(index)) {
          System.out.println("Account does not exist.");
          return;
        }
        accountNames.set(index, "");
        ArrayList<Integer> creds = accountCredentials.get(index);
        while (creds.size() < 4)
          creds.add(0);
        creds.set(IDINDEX, 0);
        creds.set(PININDEX, 0);
        creds.set(BALANCEINDEX, 0);
        creds.set(TRANSFERINDEX, 0);
        securityQuestion.set(index, "");
        accountExists.set(index, false);
        System.out.println("Account Deleted");
        logs.add("Account with ID-" + (index + 101) + " deleted!");
        return;
      } catch (Exception e) {
        out.println(e.toString());
        return;
      }
    }
  }

  public static int accountIDToBeAssigned() {
    int idToBeAssigned = 0;
    boolean idFound = false;
    for (int i = 0; i < accountExists.size(); i++) {
      if (!accountExists.get(i)) {
        idFound = true;
        idToBeAssigned = i + 101;
        break;
      }
    }
    if (!idFound) {
      idToBeAssigned = accountExists.size() + 101;
    }
    return idToBeAssigned;
  }

  public static void viewAdmins() {
    System.out.printf("""
        ┏━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━┓
        ┃%15s  ┃%15s  ┃%15s  ┃%15s    ┃
        """, "Admin Name", "Admin Exists", "Admin ID", "Admin Pin ");
    for (int i = 0; i < adminName.length; i++) {
      if (adminExists[i]) {
        System.out.printf("""
            ┣━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━┫
            ┃%15s  ┃%15s  ┃%15s  ┃%15s    ┃
            """, adminName[i], adminExists[i], adminCredentials[i][0], adminCredentials[i][1]);
      }
    }
    System.out.println("┗━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━┛");
  }

  // ================== UTILITIES ==================
  public static boolean checkingDigit(String userPINsString) {
    for (int i = 0; i < userPINsString.length(); i++) {
      char currentChar = userPINsString.charAt(i);
      if (!Character.isDigit(currentChar))
        return false;
    }
    return true;
  }

  public static boolean isDigits(String userPin) {
    char currentChar;
    boolean isDigit = true;
    for (int i = 0; i < userPin.length(); i++) {
      currentChar = userPin.charAt(i);
      if (!Character.isDigit(currentChar))
        isDigit = false;
    }
    return isDigit;
  }

  // ================== CUSTOMER MENU ==================
  public static void customerMenu() throws InterruptedException {
    boolean repeat = true;
    int option;

    while (repeat) {
      out.println("Enter your Login id: ");
      int userID = scanner.nextInt();
      scanner.nextLine();

      if (userID < 101 || userID - 101 >= accountExists.size()) {
        out.println("Invalid ID!");
        out.print("Do you wanna exit?(yes or no) ");
        String exit = scanner.nextLine();
        if (exit.equalsIgnoreCase("yes"))
          repeat = false;
        continue;
      }

      int idx = userID - 101;
      if (accountExists.get(idx) && !blocked.get(idx)) {
        out.println("Login Succesfull!");

        do {
          try {
            loadingScreen();
            out.printf("""
                =============================User Menu===============================
                |                                                                   |
                | 1) View Account Details        2) Withdraw Money                  |
                | 3) Transfer Money              4) Check Balance                   |
                | 5) Log out                                                        |
                =====================================================================

                Enter Your Choice: """);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
              case 1:
                loadingScreen();
                viewAccountDetails(idx);
                break;

              case 2:
                loadingScreen();
                withdrawMoney(idx);
                break;

              case 3:
                loadingScreen();
                transferMoney(idx);

              case 4:
                loadingScreen();
                checkBalance(idx);
                break;

              case 5:
                break;
              // logout

              default:
                System.out.println("Invalid Choice!");
                loadingScreen();

            }
          } catch (InputMismatchException e1) {
            out.println("Please Enter an Integer Only. ");
            scanner.nextLine();
            loadingScreen();
            option = -1;
          }
        } while (option != 5);

      } else {
        out.print("""
            Account Id Not found or blocked.
            Do you want to go back to main Menu (Y = yes , Any other key for "no" ) : """);
        String choice = scanner.nextLine();
        loadingScreen();
        if (choice.equalsIgnoreCase("y"))
          return;
      }
    }
  }

  public static void depositMoney(int indexNumber) {
    while (true) {
      try {
        out.print("DEPOSIT:\nHow much money do you want to deposit: ");
        int moneyToDeposit = scanner.nextInt();
        scanner.nextLine();
        if (moneyToDeposit <= 0) {
          out.print("Deposit amount must be positive.");
          return;
        }
        ArrayList<Integer> creds = accountCredentials.get(indexNumber);
        creds.set(BALANCEINDEX, creds.get(BALANCEINDEX) + moneyToDeposit);
        logs.add("A deposit of " + moneyToDeposit + "$ made by Account ID-" + creds.get(IDINDEX));
        break;
      } catch (Exception e) {
        out.println(e.toString());
        scanner.nextLine();
      }
    }
  }

  public static void withdrawMoney(int indexNumber) {
    while (true) {
      System.out.print("WITHDRAW:\nHow much money do you want to withdraw: ");
      int moneyToWithdraw = scanner.nextInt();
      scanner.nextLine();
      if (moneyToWithdraw <= 0) {
        System.out.print("Withdraw amount can't be zero or negative.");
        return;
      }
      ArrayList<Integer> creds = accountCredentials.get(indexNumber);
      if (moneyToWithdraw > creds.get(BALANCEINDEX)) {
        System.out.print("Insufficient Balance.");
        return;
      }
      System.out.println();
      creds.set(BALANCEINDEX, creds.get(BALANCEINDEX) - moneyToWithdraw);
      logs.add("A withdrawal of " + moneyToWithdraw + "$ made by Account ID-" + creds.get(IDINDEX));
      break;
    }
  }

  public static void checkBalance(int indexNumber) {
    System.out.println("Balance:" + accountCredentials.get(indexNumber).get(BALANCEINDEX));
  }

  public static void transferMoney(int indexNumber) {
    System.out.print("Enter the Reciever's Transfer ID: ");
    int transferID = scanner.nextInt();
    scanner.nextLine();
    int receiverIndex = findReceiverIDIndex(transferID);
    System.out.print("How much money do you want to transfer? ");
    int moneyToTransfer = scanner.nextInt();
    scanner.nextLine();

    if (receiverIndex != -1) {
      ArrayList<Integer> receiverCreds = accountCredentials.get(receiverIndex);
      ArrayList<Integer> senderCreds = accountCredentials.get(indexNumber);
      if (moneyToTransfer <= 0 || moneyToTransfer > senderCreds.get(BALANCEINDEX)) {
        System.out.println("Invalid amount or insufficient balance.");
        return;
      }
      receiverCreds.set(BALANCEINDEX, receiverCreds.get(BALANCEINDEX) + moneyToTransfer);
      senderCreds.set(BALANCEINDEX, senderCreds.get(BALANCEINDEX) - moneyToTransfer);
      System.out
          .println("Money Transfered to Mr/Mrs." + accountNames.get(receiverIndex) + ": " + moneyToTransfer + "€");
    }
  }

  public static int findReceiverIDIndex(int transferID) {
    int receiverIndex = -1;
    boolean transferIDFound = false;
    for (int i = 0; i < accountCredentials.size(); i++) {
      ArrayList<Integer> creds = accountCredentials.get(i);
      if (creds.size() > TRANSFERINDEX && creds.get(TRANSFERINDEX) == transferID) {
        receiverIndex = i;
        transferIDFound = true;
        break;
      }
    }
    if (!transferIDFound) {
      System.out.println("No such account found! Please enter a valid Transfer ID.");
    }
    return receiverIndex;
  }

  public static void viewAccountDetails(int indexNumber) {
    ArrayList<Integer> creds = accountCredentials.get(indexNumber);
    System.out.println("\nACCOUNT DETAILS:\nAccount ID: " + creds.get(IDINDEX)
        + "\nAccount Holder Name: " + accountNames.get(indexNumber)
        + "\nAccount Balance: " + creds.get(BALANCEINDEX)
        + "\nTransfer ID: " + creds.get(TRANSFERINDEX));
  }

  public static void blockAccount(int index) {
    if (index >= 0 && index < blocked.size())
      blocked.set(index, true);
  }

  public static void recoverAccount(int accountID) {
    int indexNumber = accountID - 101;
    if (indexNumber < 0 || indexNumber >= accountExists.size() || !accountExists.get(indexNumber)) {
      System.out.println(RED + "Account doesn't exist!" + RESET);
      return;
    }

    System.out.println("RECOVER ACCOUNT:");
    System.out.print("Enter your account ID: ");
    int idToRecover = scanner.nextInt();
    scanner.nextLine();
    int indexToRecover = idToRecover - 101;

    if (indexToRecover < 0 || indexToRecover >= accountExists.size() || !accountExists.get(indexToRecover)) {
      System.out.println(RED + "Account doesn't exist!" + RESET);
      return;
    }

    for (int i = 3; i > 0; i--) {
      System.out.println("Answer the following security question:\nWhat's your favourite car?");
      String answer = scanner.nextLine();
      if (!answer.equalsIgnoreCase(securityQuestion.get(indexToRecover))) {
        System.out.println(RED + "Wrong Answer!" + RESET);
        System.out.println(i - 1 + " tries remaining.");
      } else {
        blocked.set(indexToRecover, false);
        out.println("Account recovered and unblocked.");
        return;
      }
    }
    System.out.println("No more tries remaining.\nExiting...");
  }

  // ================== LOADING ==================
  public static void loadingScreen() throws InterruptedException {
    System.out.print("\nLoading ");
    char[] spinner = { '|', '/', '-', '\\' };
    for (int i = 0; i < 5; i++) {
      for (char j : spinner) {
        System.out.printf("\rPlease Wait %c", j);
        Thread.sleep(150);
      }
    }
    System.out.println("\rLoading completed!                   ");
  }

  public static void loadingScreen(String operation) throws InterruptedException {
    System.out.print("\nLoading ");
    char[] spinner = { '|', '/', '-', '\\' };
    for (int i = 0; i < 5; i++) {
      for (char j : spinner) {
        System.out.printf("\r%s %c", operation, j);
        Thread.sleep(150);
      }
    }
    System.out.println("\rcompleted!                        ");
  }

  // ================== FILE HANDLING ==================
  public static void saveAdminData() {
    while (true) {
      try (FileOutputStream fos = new FileOutputStream("AdminData.txt")) {
        PrintWriter writer = new PrintWriter(fos);
        for (int i = 0; i < 5; i++)
          writer.printf("%d,%d,%s,%b%n", adminCredentials[i][0], adminCredentials[i][1], adminName[i], adminExists[i]);
        writer.close();
        return;
      } catch (FileNotFoundException e1) {
        System.out.println("File was not found, creating file.");
        File file = new File("AdminData.txt");
        try {
          if (file.createNewFile())
            System.out.println("File Succesfully Created. ");
          else
            System.out.println("File could not be created");
        } catch (IOException e) {
          System.out.println("Something went Wrong. ");
        }
      } catch (IOException e2) {
        System.out.println("Something went wrong with the files..");
        return;
      }
    }
  }

  public static void loadAdminData() {
    try (FileInputStream fis = new FileInputStream("AdminData.txt");
        Scanner reader = new Scanner(fis)) {
      int i = 0;
      while (reader.hasNextLine() && i < 5) {
        String line = reader.nextLine();
        String[] allAdminData = line.split(",");
        if (allAdminData.length < 4)
          continue;
        adminCredentials[i][0] = Integer.parseInt(allAdminData[0]);
        adminCredentials[i][1] = Integer.parseInt(allAdminData[1]);
        adminName[i] = allAdminData[2];
        adminExists[i] = Boolean.parseBoolean(allAdminData[3]);
        i++;
      }
    } catch (ArrayIndexOutOfBoundsException e1) {
      System.out.println("There are more than five admin data in the file, pls delete old admin data.");
    } catch (IOException e2) {
      System.out.println("Something went wrong with the files..");
    } catch (Exception e3) {
      System.out.println("An unexpected error has occured");
    }
  }

  public static void saveCustomerData() {
    try (FileOutputStream fos = new FileOutputStream("CustomerData.txt");
        PrintWriter writer = new PrintWriter(fos)) {

      for (int i = 0; i < accountCredentials.size(); i++) {
        ArrayList<Integer> creds = accountCredentials.get(i);
        while (creds.size() < 4)
          creds.add(0);
        writer.printf("%d,%d,%d,%d,%s,%s,%b,%b%n",
            creds.get(IDINDEX),
            creds.get(PININDEX),
            creds.get(BALANCEINDEX),
            creds.get(TRANSFERINDEX),
            securityQuestion.get(i),
            accountNames.get(i),
            accountExists.get(i),
            blocked.get(i));
      }

    } catch (FileNotFoundException e) {
      System.out.println("File was not found, creating file.");
      File file = new File("CustomerData.txt");
      try {
        if (file.createNewFile())
          System.out.println("File Succesfully Created. ");
        else
          System.out.println("File could not be created");
      } catch (IOException e1) {
        System.out.println("Something went Wrong. ");
      }
    } catch (IOException e) {
      System.out.println("File is misbehaving");
    } catch (Exception e) {
      System.out.println("Unexpected error, displayed for debugging " + e.toString());
    }
  }

  public static void loadCustomerData() {
    try (FileInputStream fis = new FileInputStream("CustomerData.txt");
        Scanner reader = new Scanner(fis)) {

      accountCredentials.clear();
      accountNames.clear();
      accountExists.clear();
      securityQuestion.clear();
      blocked.clear();

      int i = 0;
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        if (line.isEmpty())
          continue;
        String[] customerData = line.split(",");
        if (customerData.length < 8)
          continue;

        accountCredentials.add(new ArrayList<>());
        accountCredentials.get(i).add(Integer.parseInt(customerData[0]));
        accountCredentials.get(i).add(Integer.parseInt(customerData[1]));
        accountCredentials.get(i).add(Integer.parseInt(customerData[2]));
        accountCredentials.get(i).add(Integer.parseInt(customerData[3]));
        securityQuestion.add(customerData[4]);
        accountNames.add(customerData[5]);
        accountExists.add(Boolean.parseBoolean(customerData[6]));
        blocked.add(Boolean.parseBoolean(customerData[7]));
        i++;
      }

    } catch (IOException e) {
      System.out.println("Something went wrong with the files!" + e.toString());
    } catch (Exception e) {
      System.out.println("An unexpected error occured.");
    }
  }// loadingScreen

  public static void saveLogs() {
    try (FileOutputStream fos = new FileOutputStream("logs.txt", true); FilePrinter writer = new FilePrinter(fos)) {
      for (String line : logs)
        writer.println(line);
    } catch (FileNotFoundException e) {
      try (File file = new File("logs.txt")) {
        if (file.createNewFile())
          out.println("File Succesfully Created. ");
        else
          out.println("File could not be created");
      } catch (IOException e1) {
        out.println("Something went wrong with the files!" + e1.toString());
      }
    } catch (IOException e) {
      out.println("Something went wrong with the files!" + e.toString());
    }
  }// savelogs

  public static void loadLogs() {
    try (FileInputStream fis = new FileInputStream("logs.txt"); Scanner reader = new Scanner(fis)) {
      while (reader.hasNextLine()) {
        String line = reader.nextLine();

        if (line.isEmpty())
          break;

        logs.add(line);
      }
    } catch (FileNotFoundException e) {
      out.println("File was not found" + e.toString());
    } catch (IOException e) {
      out.println("Something went wrong with the files!" + e.toString());
    }
  }
}
