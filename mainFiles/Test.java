import java.util.*;
import java.io.*;
import static java.lang.System.out;

public class Test {
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

  //  Owner Data Variables  

  static int[] ownerCredentials = { 02, 72766 };
  static String ownerName = "Solitary Monarch";
  
  //  Admin Data Variables 

  static int[][] adminCredentials = new int[5][2]; // Index 1, and 2 are id number, and pin
  static String[] adminName = new String[5];
  static boolean[] adminExists = new boolean[5];
  
  //  User Data Variables

  static ArrayList<ArrayList<Integer>> accountCredentials = new ArrayList<>();
  static ArrayList<String> securityQuestion = new ArrayList<>();
  static ArrayList<String> accountNames = new ArrayList<>();
  
  //  Logs Variable

  static ArrayList<String> logs = new ArrayList<>();
  
 //  ArrayLists to keep track of user's account state and keep action history of the entire bank account.

  static ArrayList<Boolean> accountExists = new ArrayList<>();
  static ArrayList<Boolean> blocked = new ArrayList<Boolean>();
  static int totalAccounts = 0;

  //  Constants representing index number of user's each information.

  static final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;

  public static void main(String[] args) throws InterruptedException
  {
    int choice = -1;
    do {
      try {
        out.print("""
            ==================Bank Of Valhalla==================
            |                                                  |
            |   What do you want to login as?                  |
            |                                                  |
            |   1) Owner                3) Customer            |
            |                                                  |
            |   2) Admin                4) Recover Account     |
            |                                                  |
            |                5) Exit                           |
            |                                                  |
            ====================================================


            Enter Your Choice: """);
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) 
        {
          case 1:
            loadingScreen("Loading Owner Data");
            loadOwnerData();
            int ownerID, ownerPass;
            while(true)
            {
              try
              {
                out.print("Enter your ID: ");
                ownerID = scanner.nextInt();
                scanner.nextLine();
                out.print("Enter your Passkey: ");
                ownerPass = scanner.nextInt();
                
                break;
              }
              catch(InputMismatchException e)
              {
                out.println("Please enter only An Integer.");
                loadingScreen("Going back");
                scanner.nextLine();
              } 
            }// end of while

            
            if (ownerID == ownerCredentials[0] && ownerPass == ownerCredentials[1])
              ownerMenu();
            else
              out.println("Access Denied!");
            break;
          case 2:
            loadAdminData();
            out.print("Admin ID: ");
            int adminID = scanner.nextInt();
            adminMenu(adminID);
            break;
          case 3:
            loadCustomerData();
            customerMenu();
            break;
          case 4:
            out.print("Account ID: ");
            int id = scanner.nextInt();
            recoverAccount(id);
            break;
          default:
            out.println("Incorrect Choice!");
        }
      } catch (Exception e) {
        out.println(e.toString());
      }
    } while (choice != 5);
    out.println("""
        ====================================================
        |                                                  |
        |         Thank you for visiting the Abyss         |
        |                                                  |
        |                   Good Bye!                      |
        |                                                  |
        ====================================================""");
  }

  public static void ownerMenu() {
    int choice = -1;
    do {
      try {
        out.println("""
            ==========================Owner Menu=======================
            |                 What do you want to do?                 |
            |                                                         |
            |   1) Create an Admin account                            |
            |                                                         |
            |   2) Delete an Admin account                            |
            |                                                         |
            |   3) Exit                                               |
            ===========================================================

            Enter Your Choice:  """);
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
          case 1:
            createAdmin();
            break;
          case 2:
            deleteAdmin();
            break;
          default:
            out.println("Incorrect Option.");
        }
      } catch (Exception e) {
        out.println(e.toString());
      }
    } while (choice != 3);
  }

  public static void createAdmin() {
    int adminIndex = -1;
    do {
      out.print("Enter Admin Name: ");
      String adminName = scanner.nextLine();
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
            } else
              out.println("PINs Mismatch. Try Again!");
          } else
            out.println("Incorrect Format!");
        } catch (Exception e) {
          out.println(e.toString());
        }
      } while (!matchingPINs);

      try {
        adminIndex = adminIDToBeAssigned() - 505;
        adminCredentials[adminIndex][0] = adminIDToBeAssigned();
        adminCredentials[adminIndex][1] = finalPIN;
        adminExists[adminIndex] = true;
        out.println("Admin Account Created!");
        logs.add("Admin Account with ID-" + adminIDToBeAssigned() + " created.");
      } catch (Exception e) {
        out.println(e.toString());
      }
    } while (!adminExists[adminIndex]);
  }

  public static int adminIDToBeAssigned() {
    int idToAssign = 0;
    for (int i = 0; i < 5; i++) {
      if (adminExists[i] == false) {
        idToAssign = i + 505;
        break;
      }
    }
    return idToAssign;
  }

  public static void deleteAdmin() {
    int indexToDelete = -1;
    do {
      try {
        out.print("Admin ID to be Deleted: ");
        int idToDelete = scanner.nextInt();
        indexToDelete = idToDelete - 505;

        adminCredentials[indexToDelete][0] = 0;
        adminCredentials[indexToDelete][1] = 0;
        adminName[indexToDelete] = "Null";
        adminExists[indexToDelete] = false;
        out.println("Admin Account Deleted!");
        logs.add("Admin Account with ID-" + idToDelete + " deleted.");
      } catch (Exception e) {
        out.println(e.toString());
      }
    } while (adminExists[indexToDelete]);
  }

  public static void adminMenu(int adminID) {
    int adminIndex = adminID - 505;
    if (adminExists[adminIndex] == false) {
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
        int index = 0;
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
            deleteAccount(index);
            break;
          case 4:
            System.out.print("Enter index of the account: ");
            index = scanner.nextInt();
            blockAccount(index);
            break;
          default:
            System.out.println("Please try again with a valid option!");
        }
      } catch (Exception e) {
        out.println(e.toString());
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

  public static void createAccount() {
    int indexNumber = accountIDToBeAssigned() - 101;
    scanner.nextLine();

    while ((indexNumber >= accountNames.size()))
      accountNames.add("");
    while ((indexNumber >= accountCredentials.size()))
      accountCredentials.add(new ArrayList<>());
    while ((indexNumber >= securityQuestion.size()))
      securityQuestion.add("");
    while ((indexNumber >= accountExists.size()))
      accountExists.add(false);
    while ((indexNumber >= blocked.size()))
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

    out.println(
        "Answer the following security question for future account recovery:\nWhat's your favourite car name?");
    securityQuestion.set(indexNumber, scanner.nextLine());

    accountCredentials.get(indexNumber).set(IDINDEX, accountIDToBeAssigned());
    accountCredentials.get(indexNumber).set(PININDEX, finalPIN);
    accountCredentials.get(indexNumber).set(BALANCEINDEX, 0);
    accountCredentials.get(indexNumber).set(TRANSFERINDEX, random.nextInt(10000000, 100000000));
    accountExists.set(indexNumber, true);
    out.println(GREEN + "Account Successfully Created!" + RESET);
    String prompt = ("A new account created by name " + accountNames.get(indexNumber));
    logs.add(prompt);
    if (indexNumber >= totalAccounts)
      totalAccounts++;

    viewAccountDetails(indexNumber);
  }

  public static void deleteAccount(int index) {
    do {
      try {
        accountNames.set(index, "");
        accountCredentials.get(index).set(IDINDEX,0);
        accountCredentials.get(index).set(PININDEX,0);
        accountCredentials.get(index).set(BALANCEINDEX,0);
        accountCredentials.get(index).set(TRANSFERINDEX,0);
        accountCredentials.get(index).set(IDINDEX,0);
        
        securityQuestion.set(index, "");
        accountExists.set(index, false);
        System.out.println("Account Deleted");
        logs.add("Account with ID-" + (index + 101) + " deleted!");
      } catch (Exception e) {
        out.println(e.toString());
      }
    } while (accountExists.get(index) != false);
  }

  public static int accountIDToBeAssigned() {
    int idToBeAssigned = 0;
    Boolean idFound = false;
    for (int i = 0; i < accountExists.size(); i++) {
      if (accountExists.get(i) == false) {
        idFound = true;
        idToBeAssigned = i + 101;
        break;
      }
    }

    if (idFound == false) {
      idToBeAssigned = accountExists.size() + 101;
    }

    return idToBeAssigned;
  }

  public static boolean checkingDigit(String userPINsString) {
    for (int i = 0; i < userPINsString.length(); i++) {
      char currentChar = userPINsString.charAt(i);

      if (!(Character.isDigit(currentChar)))
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

  public static void customerMenu() {
    boolean repeat = true;
    int option = -1;
    if (repeat == false)
      return;
    do {

      out.println("Enter your Login id: ");
      int userID = scanner.nextInt();
      scanner.nextLine();
      if (userID > accountExists.size() || userID < 101) {
        out.println("Invalid ID!");
        out.print("Do you wanna exit?(yes or no) ");
        String exit = scanner.nextLine();
        if (exit.equalsIgnoreCase("yes"))
          repeat = false;

        continue;
      }

      if (accountExists.get(userID - 101) && !blocked.get(userID - 101)) {
        out.println("Login Succesfull!");
        while (true) {
          try {
            loadingScreen();
            out.printf("""
                =============================User Menu===============================
                |                                                                   |
                |   1)  View Account Detials            2)  Withrdraw Money         |
                |                                                                   |
                |   3)  Transfer Money                  4)  Check Balance           |
                |                                                                   |
                |                       5)  Log out                                 |
                |                                                                   |
                =====================================================================

                Enter Your Choice:  """);

            option = scanner.nextInt();
            break;
          } catch (InputMismatchException e1) {
            out.println("Please Enter an Integer Only. ");
            scanner.nextLine();
            loadingScreen();
          }
        }
        switch (option) {
          case 1:
            loadingScreen();
            viewAccountDetails(userID);
            break;
          case 2:
            loadingScreen();
            withdrawMoney(userID);
            break;
          case 3:
            loadingScreen();
            transferMoney(userID);
            break;
          case 4:
            loadingScreen();
            checkBalance(userID);
            break;
          default:
            System.out.println("Invalid Choice!");
            loadingScreen();
        }
      } else {
        out.print("""
            Account Id Not found
            Do you want to go back to main Menu (Y = yes , Any other key for "no" ) :  """);
        String choice = scanner.nextLine();
        loadingScreen();
        if (choice.equals("y") || choice.equals("Y"))
          return;
      }
    } while (option != 5);
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

        out.println();

        accountCredentials.get(indexNumber).set(BALANCEINDEX,
            accountCredentials.get(indexNumber).get(BALANCEINDEX) + moneyToDeposit);
        logs.add("A deposit of " + moneyToDeposit + "$ made by Account ID-"
            + accountCredentials.get(indexNumber).get(IDINDEX));
        break;
      } catch (Exception e) {
        out.println(e.toString());
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

      if (moneyToWithdraw > accountCredentials.get(indexNumber).get(BALANCEINDEX)) {
        System.out.print("Insufficient Balance.");
      }

      System.out.println();

      accountCredentials.get(indexNumber).set(BALANCEINDEX,
          accountCredentials.get(indexNumber).get(BALANCEINDEX) - moneyToWithdraw);

      logs.add("A withdrawal of " + moneyToWithdraw + "$ made by Account ID-"
          + accountCredentials.get(indexNumber).get(IDINDEX));
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
      accountCredentials.get(receiverIndex).set(BALANCEINDEX,
          (accountCredentials.get(receiverIndex).get(BALANCEINDEX) + moneyToTransfer));
      accountCredentials.get(indexNumber).set(BALANCEINDEX,
          (accountCredentials.get(indexNumber).get(BALANCEINDEX) - moneyToTransfer));
      System.out.println(
          "Money Transfered to Mr/Mrs."
              + accountNames.get(receiverIndex)
              + ": "
              + moneyToTransfer
              + "€");
    }
  }

  public static int findReceiverIDIndex(int transferID) {
    int receiverIndex = -1;
    boolean transferIDFound = false;

    for (int i = 0; i < accountCredentials.size(); i++) {
      if (accountCredentials.get(i).get(TRANSFERINDEX) == transferID) {
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
    System.out.println(
        "\nACCOUNT DETAILS:\nAccount ID: "
            + accountCredentials.get(indexNumber).get(IDINDEX)
            + "\nAccount Holder Name: "
            + accountNames.get(indexNumber)
            + "\nAccount Balance: "
            + accountCredentials.get(indexNumber).get(BALANCEINDEX)
            + "\nTransfer ID: "
            + accountCredentials.get(indexNumber).get(TRANSFERINDEX));
  }

  public static void blockAccount(int index) {
    blocked.set(index, true);
  }

  public static void recoverAccount(int accountID) {
    int indexNumber = accountID - 101;
    while (true) {
      try {
        if (indexNumber < 0 || indexNumber >= accountExists.size() || !accountExists.get(indexNumber)) {
          System.out.println(RED + "Account doesn't exist!" + RESET);
          return;
        }
        System.out.println("RECOVER ACCOUNT:");
        System.out.print("Enter your account ID: ");
        int idToRecover = scanner.nextInt();
        scanner.nextLine();
        int indexToRecover = idToRecover - 101;
        String answer = "";

        for (int i = 3; i > 0; i--) {
          System.out.println("Answer the following security question:\nWhat's your favourite car?");
          answer = scanner.nextLine();
          if (!answer.equalsIgnoreCase(securityQuestion.get(indexToRecover))) {
            System.out.println(RED + "Wrong Answer!" + RESET);
            System.out.println(i + " tries remaining.");
          }
        }

        if (answer.equalsIgnoreCase(securityQuestion.get(indexToRecover))) 
        {
          System.out.println("No more tries remaining.\nExiting...");
          return;
        }

        blocked.set(indexToRecover, false);
        out.println();
        break;
      } 
      catch (Exception e) 
      {
        out.println(e.toString());
      }
    }
  }// recover Account

  public static void loadingScreen() throws InterruptedException 
  {
    System.out.print("\nLoading ");

    char[] spinner = { '|', '/', '-', '\\' };

    for (int i = 0; i < 5; i++) {
      for (char j : spinner) {
        System.out.printf("\rPlease Wait %c", j);
        Thread.sleep(150);
      }
    }

    System.out.println("\r✅Transaction completed!    ");
  }

  public static void loadingScreen(String operation) throws InterruptedException 
  {
    System.out.print("\nLoading ");

    char[] spinner = { '|', '/', '-', '\\' };

    for (int i = 0; i < 5; i++) {
      for (char j : spinner) {
        System.out.printf("\r%s %c", operation, j);
        Thread.sleep(150);
      }
    }
    System.out.println("\r✅Transaction completed!    ");
  }

  //  file handling methods

  //  Admin data saving  and loading methods

   public static void saveAdminData() throws InterruptedException
  {
    while(true)
    {
      try(FileOutputStream fos  = new FileOutputStream("AdminData.txt"))
      {
          PrintWriter writer = new PrintWriter(fos);

          for(int i =0; i<5; i++)
              writer.printf("%d,%d,%s,%b\n", adminCredentials[i][0],adminCredentials[i][1], adminName[i],adminExists[i]);
          writer.close();
      }
      catch(FileNotFoundException e1)
      {
          System.out.println("File was not found, creating file.");
          File file = new File("AdminData.txt");
          try
          {
              if(file.createNewFile())
                  System.out.println("File Succesfully Created. ");
              else
                  System.out.println("File coulnd be created");
          }
          catch(IOException e)
          {
              System.out.println("Something went Wrong. ");
          }
      }
      catch(IOException e2)
      {
          System.out.println("Something went wrong with the files..");
          loadingScreen("Please wait");
      }
    }
  }


  // load Admin data from the file.

  public static void loadAdminData()
  {
      try(FileInputStream fis = new FileInputStream("AdminData.txt"); Scanner reader = new Scanner(fis))
      {
          int i =0;
          while(reader.hasNextLine())
          {
              String line = reader.nextLine();
              String[] allAdminData = line.split(",");
              
              adminCredentials[i][0] = Integer.parseInteger(allAdminData[0]);
              adminCredentials[i][1] = Integer.parseInt(allAdminData[1]);
              adminName[i] = Integer.(allAdminData[2]);
              adminExists[i] = Boolean.parseBoolean(allAdminData[3]);
              i++;
          }
      }
      catch(ArrayIndexOutOfBoundsException e1)
      {
          System.out.println("There are more than five admin data in the file, pls delete old admin data.");
          loadingScreen();
      }
      catch(IOException e2)
      {
          System.out.println("Something went wrong with the files..");
          loadingScreen();
      }
      catch(Exception e3)
      {
          System.out.println("An unexpected error has occured");
          loadingScreen();
      }
  }// end of loadAdminData()

}//class
