import java.util.*;
import java.io.*;

public class AlliedBankLimited
{
    //  Scanner for user input

    static Scanner scanner = new Scanner(System.in);
    

    //  ANSI escape codes for colors

    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    //  bright colors
    public static final String BRIGHTBLACK = "\u001B[90m";
    public static final String BRIGHTRED = "\u001B[91m";
    public static final String BRIGHTGREEN = "\u001B[92m";
    public static final String BRIGHTYELLOW = "\u001B[93m";
    public static final String BRIGHTBLUE = "\u001B[94m";
    public static final String BRIGHTMAGENTA = "\u001B[95m";
    public static final String BRIGHTCYAN = "\u001B[96m";
    public static final String BRIGHTWHITE = "\u001B[97m";
    
    //  for formatting colors.
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";

    //  logs variables

    static ArrayList<String> logs = new ArrayList<>();

    //  owner data variables

    static ArrayList<Boolean> accountExists = new ArrayList<>();
    static int[] ownerCredentials = {02, 72766};
    static String ownerName = "Solitary Monarch";
    
    //  admin data variables

    static int[][] adminCredentials = new int[5][2]; // Index 0, and 1 are id number, and pin
    static String[] adminName = new String[5];
    static boolean[] adminExists = new boolean[5];

    //  user data variables  

    static ArrayList<ArrayList<Integer>> accountCredentials = new ArrayList<>();
    static ArrayList<String> securityQuestion = new ArrayList<>();
    static ArrayList<String> accountNames = new ArrayList<>();
    
    //  ArrayLists to keep track of user's account state and keep action history of the entire bank account.

    static ArrayList<String> logs = new ArrayList<>();
    static ArrayList<Boolean> blocked = new ArrayList<Boolean>();
    static int totalAccounts = 0;
    static int userID;
    
    //  constants representing index number of user's each information.

    static final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;
    

    //  main method finally starts.

    public static void main(String[] args)
    {
        loadAdminData();
        loadCostumerData();
        loadOwnerData();
        do 
        {
            try{
            System.out.print("""
            ================Allied Bank Limited=================
            |                                                  |
            |   What do you want to login as?                  |
            |                                                  |
            |   1) Owner                3) Customer            |
            |                                                  |
            |   2) Admin                4) Exit                |
            |                                                  |
            ====================================================


            Enter Your Choice: """);
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) 
            {
                case 1:
                    System.out.println("Enter the owner ID: ");
                    int ownerID = scanner.nextInt();
                    System.out.println("Enter the pin: ");
                    int ownerPIN = scanner.nextInt();
                    if (ownerID == ownerCredentials[0] && ownerPIN == ownerCredentials[1]) {
                        ownerMenu();
                    } else System.out.println("Incorrect Credentials");
                    break;
                case 2:
                    userMenu();
                    break;
                case 3:
                    System.out.println("Enter your ID number: ");
                    int idNumber = scanner.nextInt();
                    adminMenu(idNumber);
                    break;
                case 4:

                    System.out.println("""
                    ====================================================
                    |                                                  |
                    |         Thank you for using Allied Bank          |
                    |                                                  |
                    |                   Good Bye!                      |
                    |                                                  |
                    =====================================================""");

                    System.exit(0);
                    break;
                default:
                    System.out.println("Choose a valid option from the given ones.\n");
                    Thread.sleep(1500);  // time for user to see the message 
            }
            }
            catch(InputMismatchException e1)
            {
            System.out.println(" \n Please enter an Integer only.");    
            scanner.nextLine(); //  stops the infinite loop madness.

            Thread.sleep(1000);  // time for user to see the message 
            }
            catch(Exception e)
            {
            System.out.print("Unxepected error: "+e.toString());
            Thread.sleep(1000);  // time for user to see the message 
            }
        } while (true);
    }
    
    // Add logs
    
    public static void addLogs(String activity) 
    {
        logs.add(activity);
    }
    

    //  Admin ID To Be Assigned, 
    //  currently, this method fails when all 5 IDS are assigned.
    
    public static int adminIDToBeAssigned() 
    {
        int idToAssign = 0;
        for (int i = 0; i < 5; i++) 
        {
            if (adminExists[i] == true) 
            {
                idToAssign = i - 505;
                break;
            }
        }// for
        return idToAssign;
    }// adminIDToBeAssigned

    // Menu for Admins
    public static void adminMenu(int idNumber) 
    {
        // this will raise exception if the number recieved from main isnt checked to be less than 5.
        if (adminExists[idNumber - 505] == false) 
        {
            System.out.println("Wrong Admin Credentials.");
            return;
        }

        while(true)
        {
            System.out.println("""
                    =====================Admin Menu============================
                    |               What do you want to do?                   |
                    |                                                         |
                    |   1) View the log            2) Delete an account       |
                    |                                                         |
                    |   3) Create an account       4) Block an account        |
                    |                                                         |
                    |                     5)Log Out                           |
                    ===========================================================
                    
                    Enter Your Choice:  """);
            int choice = scanner.nextInt();
            scanner.nextLine();
            loadingScreen();
            int index = 0;
            switch (choice) 
            {
                case 1:
                    viewLogs();
                    break;
                case 2:
                    System.out.print("Enter index of the account: ");
                    index = scanner.nextInt();  
                    deleteAccount(index);   
                    break;
                case 3:
                    createAccount();
                    break;
                case 4:
                    System.out.print("Enter index of the account: ");
                    index = scanner.nextInt();
                    blockAccount(index);
                    break;
                case 5:
                    return;
                default:
                System.out.println("Please try again with a valid option!");
            }//  switch
        }// while
    }// adminMenu
   
    // Block Account
   
    public static void blockAccount(int index) 
    {
        blocked.set(index, true);
    }// block account



    // Create new account
    
    public static void createAccount() 
    {
        int indexNumber = accountIDToBeAssigned() - 101;
        scanner.nextLine();

        //  index number is out of current indexes

        if(indexNumber > accountNames.size())
            accountNames.add("");

        System.out.print("Enter Account Holder Name: ");
        accountNames.set(indexNumber, scanner.nextLine());

        boolean matchingPINs = false;
        int finalPIN = 0;
        String userPINs, userPINsConfirm;

        do 
        {
            try
            {
                System.out.print("Enter new PIN (4 or 8 digits only): ");
                userPINs = scanner.nextLine();
            }
            catch( InputMismatchException e1 )
            {
                System.out.print("Please Enter only Integer.");
                continue;
            }
            catch ( Exception e2 )
            {
                System.out.println("Something unexpected happened");
            }

            loadingScreen();

            //  confriming pin.

            if (checkingDigit(userPINs) && (userPINs.length() == 4 || userPINs.length() == 8)) 
            {
                System.out.print("Confirm the PIN: ");
                userPINsConfirm = scanner.nextLine();

                if (userPINs.equals(userPINsConfirm)) 
                {
                    finalPIN = Integer.parseInt(userPINs);
                    matchingPINs = true;
                } 
                else 
                {
                    System.out.println(RED + "PINs don't match! Try again." + RESET);
                }
            } 
            else 
            {
                System.out.println(RED + "Use the correct format for the PIN (4 or 8 digits only)." + RESET);
            }
        } while (!matchingPINs);

        System.out.println("Answer the following security question for future account recovery:\nWhat's your favourite car name?");
        securityQuestion.add(scanner.nextLine());


        while (accountCredentials.size() <= indexNumber) 
        {
            accountCredentials.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        }
        while (accountExists.size() <= indexNumber) 
        {
            accountExists.add(false);
        }

        ArrayList<Integer> credentialsOfCurrentAccount = accountCredentials.get(indexNumber);
        credentialsOfCurrentAccount.set(IDINDEX, accountIDToBeAssigned());
        credentialsOfCurrentAccount.set(PININDEX, finalPIN);
        credentialsOfCurrentAccount.set(BALANCEINDEX, 0);
        credentialsOfCurrentAccount.set(TRANSFERINDEX, random.nextInt(1000000, 100000000));

        accountExists.set(indexNumber, true);
        System.out.println(GREEN + "Account Successfully Created!" + RESET);
        String prompt = ("A new account created by name " + accountNames.get(indexNumber));
        addLogs(prompt);
        if (indexNumber == totalAccounts) totalAccounts++;

        viewAccountDetails(indexNumber);
    }
    
    // Add Admin Account
    
    public static void createAdmin() 
    {
        System.out.println("Enter Admin Name: ");
        String adminName = scanner.nextLine();

        String userPINs;
        String userPINsConfirm;
        int finalPIN = 0;
        boolean matchingPINs = false;

        do 
        {
            System.out.print("Enter new PIN (4 or 8 digits only): ");
            userPINs = scanner.nextLine();
     
            if (checkingDigit(userPINs) && (userPINs.length() == 4 || userPINs.length() == 8)) 
            {
                System.out.print("Confirm the PIN: ");
                userPINsConfirm = scanner.nextLine();
     
                if (userPINs.equals(userPINsConfirm)) 
                {
                    finalPIN = Integer.parseInt(userPINs);
                    matchingPINs = true;
                } 
                else 
                {
                    System.out.println(RED + "PINs don't match! Try again." + RESET);
                }
            } 
            else
            {
                System.out.println(RED + "Use the correct format for the PIN (4 or 8 digits only)." + RESET);
            }
        } while (!matchingPINs);

        //  store the index number of the current account

        int accountIndex = accountIDToBeAssigned()-505;
        
        adminCredentials[accountIndex][0] = adminIDToBeAssigned();
        adminCredentials[accountIndex][1] = finalPIN;
        adminExists[accountIndex] = true;

        System.out.println("Admin Account Created!");
        String prompt = (RED+"A new account created by name " + adminName[adminIDToBeAssigned-505]);
        addLogs(prompt);
       
    }// create Admin account.

    // Delete Admin Account
    
    public static void deleteAdmin() 
    {
        while(true)
        {
            try
            {

                System.out.println("Enter the Admin ID you want to delete: ");
                int idToDelete = scanner.nextInt(); //possible exception
                scanner.nextLine();
                
                if(idToDelete<505 )
                {
                    System.out.println("      Account Not  Found    ");
                    return;
                }
                
                int indexToDelete = idToDelete - 505;
                
                // set to zero as an solution for array's non-mutable nature
                adminCredentials[indexToDelete][0] = 0;
                adminCredentials[indexToDelete][1] = 0;
                adminName[indexToDelete] = "";
                adminExists[indexToDelete] = false;

                break;
            }
            catch(InputMismatchException e1)
            {
                System.out.println("Please enter an integer only.");
            }
            catch(ArrayIndexOutOfBoundsException)
            {
                System.out.println("Invalid Admin ID");
            }
            catch(Exception e2)
            {
                System.out.println("Unexpected Error");
            }
        }
    }// deleteAdmin

    // Menu for the Owner

    public static void ownerMenu() 
    {
        try
        {
            while(true)
            {
                System.out.println("""
                            =====================Owner Menu============================
                            |                 What do you want to do?                 |
                            |                                                         |
                            |   1) Create an Admin account                            |
                            |                                                         |
                            |   2) Delete an Admin account                            |
                            |                                                         |
                            |   3)Exit                                                |
                            ===========================================================
                            
                            Enter Your Choice:  """);
                
                int choice = scanner.nextInt();
                switch (choice) 
                {
                    case 1:
                        createAdmin();
                        break;
                    case 2:
                        deleteAdmin();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Please select from the given options!");
                }

                break;  //  Only executes if no exception raised so far
            }
        }
    }// OwnerMenu

    // Recover account
    public static void recoverAccount(int accountID) 
    {
        int indexNumber = accountID - 101;

        if (indexNumber < 0 || indexNumber >= accountExists.size() || !accountExists.get(indexNumber)) {
            System.out.println(RED + "Account doesn't exist!" + RESET);
            return;
        }
        System.out.println("RECOVER ACCOUNT:");
        System.out.print("Enter your account ID: ");
        int idToRecover = scanner.nextInt();
        int indexToRecover = idToRecover - 101;
        String answer = "";

        for (int i = 3; i > 0; i--) {
            System.out.println("Answer the following security question:\nWhat's your favourite car?");
            answer = scanner.nextLine();
            if (answer != securityQuestion.get(indexToRecover)) {
                System.out.println(RED + "Wrong Answer!" + RESET);
                System.out.println(i + " tries remaining.");
            }
        }

        if (answer != securityQuestion.get(indexToRecover)) {
            System.out.println("No more tries remaining.\nExiting...");
            return;
        }
    
        blocked.set(indexToRecover, false);
        System.out.println();
    }

    // View logs (for admin only)

    public static void viewLogs() 
    {
        for (int i = logs.size() - 1; i >= 0; i--) 
        {
            System.out.println(i + ") " + logs.get(i));
        }
    }

    //  For  waiting time between each action.

    //  Keep this method at the last of the program. For Easy Modification
    

    public void loadingScreen() throws InterruptedException
    {
        final int FRAME_DELAY = 200;  // ms per frame
        final String[] FRAMES = {
            "     ",
            ".    ",
            "..   ",
            "...  ",
            ".... ",
            "....."
        };

        System.out.print("Please wait");
        for(int i = 0; i < 30; i++)
        {  
        // Duration in frames
            System.out.print("\rPlease wait" + FRAMES[i % FRAMES.length]);
            Thread.sleep(FRAME_DELAY);
        }
        System.out.println("\rDone!           ");
    }

}//class