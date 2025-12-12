import java.util.*;
import java.io.*;

public class AlliedBankLimited
{
    //  Scanner for user input

    static Scanner scanner = new Scanner(System.in);

    //  logs variables

    static ArrayList<String> logs = new ArrayList<>();

    //  owner data variables

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
    static ArrayList<Boolean> accountExists = new ArrayList<>();
    
    //  ArrayLists to keep track of user's account state and keep action history of the entire bank account.

    static ArrayList<Boolean> blocked = new ArrayList<Boolean>();
    static int totalAccounts = 0;
    static int userID;
    
    //  constants representing index number of user's each information.

    static final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;
    

    //  main method finally starts.


    public static void main(String[] args)
    {
     
        do 
        {
            try
            {
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

                loadingScreen();
                switch (choice) 
                {
                    case 1:
                        System.out.println("Enter the owner ID: ");
                        int ownerID = scanner.nextInt();
                        System.out.println("Enter the pin: ");
                        
                        //  Put an while , try-catch, So no id isnt asked repititevly.
                        while(true)
                        {
                            try
                            {
                                int ownerPIN = scanner.nextInt();
                                if (ownerID == ownerCredentials[0] && ownerPIN == ownerCredentials[1]) 
                                    ownerMenu();
                                else 
                                    System.out.println("Incorrect Credentials");
                                break;
                            }
                            catch(InputMismatchException e)
                            {
                                System.out.print("Pin is always in integer, decimal values not allowed. ");
                                scanner.nextLine();
                            }
                            catch(Exception e1)
                            {
                                System.out.print("Something Went Wrong.");
                            }
                        }
                        break;
        
                    case 2:

                        loadCustomerData();
                        userMenu();
                        break;
                    case 3:
                        loadAdminData();
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

            loadingScreen();
            }
            catch(Exception e)
            {
            System.out.print("Unxepected error: "+e.toString());
            loadingScreen();  // time for user to see the message 
            }
        } while (true);
    }// main
    
    //  Add logs to the array and file.
    
    public static void addLogs(String activity) 
    {
        logs.add(activity);

        try
        {
            PrintWriter pw = new PrintWriter(new FileWriter("BankLogs.txt",true));

            //  save the activity in the file.

            pw.println(activity);

            pw.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.print("File was not found. Couldnt Update logs. ");

        }
        catch( IOException e1)
        {
            System.out.print("Something went wrong with the files.");
        }
        catch( Exception e2)
        {
            System.out.print("An Unexpected Error Occurred.");
        }
    }// addLogs
    

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
            accountNames.add("");   //  Creates a new index 

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
            catch(ArrayIndexOutOfBoundsException e2)
            {
                System.out.println("Invalid Admin ID");
            }
            catch(Exception e3)
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
        catch(InputMismatchException e)
        {
            System.out.println("Please Enter An Integer Only! ");
            scanner.nextLine();
        }
    }// OwnerMenu

    // Recover account
    public static void recoverAccount(int accountID) 
    {
        int indexNumber = accountID - 101;

        if (indexNumber < 0 || indexNumber >= accountExists.size() || !accountExists.get(indexNumber)) 
        {
            System.out.println(RED + "Account doesn't exist!" + RESET);
            return;
        }
        System.out.println("RECOVER ACCOUNT:");
        System.out.print("Enter your account ID: ");
        int idToRecover = scanner.nextInt();
        int indexToRecover = idToRecover - 101;
        String answer = "";

        for (int i = 3; i > 0; i--) 
        {
            System.out.println("Answer the following security question:\nWhat's your favourite car?");
            answer = scanner.nextLine();
            if (answer != securityQuestion.get(indexToRecover)) 
            {
                System.out.println(RED + "Wrong Answer!" + RESET);
                System.out.println(i + " tries remaining.");
            }
        }

        if (answer != securityQuestion.get(indexToRecover)) 
        {
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


    public static boolean checkingDigits(String userPINsString)
    {
        for(int i=0; i<userPINsString.length(); i++)
        {
            currentChar = userPINsString.charAt(i);

            if(!(Character.isDigit(currentChar)))
                return false;

        }

        //  only executes if all the characters were digits

        return true;    
    }// checkingDigits

    public static void userMenu()
    {
        while(true)
        {
            System.out.println("Enter your Login id: ");
            userID = scanner.nextInt();
            if(userID > accountExists.size() || userID < 101)
            {
                System.out.println("Invalid ID, Please enter a valid Id: ");
                continue;
            }
            
            if(accountExists.get(userID-101) ==true && blocked.get(userID-101))
            {
                System.out.println("Login Succesfull!");
                loadingScreen();
                System.out.printf("""
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
                
                //  incase user enters something other than integer.
                int userChoice;
                while(true)
                {
                    try
                    {
                        userChoice = scanner.nexInt();
                        break;
                    }
                    catch(InputMismatchException e1)
                    {
                        System.out.println("Please Enter an Integer Only. ");
                        scanner.nextLine();  //  stops the infinte catch madness
                        loadingScreen("Please Wait");
                    }
                }//end of while

                switch(userChoice)
                {
                    case 1:
                        loadingScreen("Loading Account Details");
                        viewAccountDetials();
                        break;
                    case 2:
                        loadingScreen("Loading balance");
                        withdrawMoney();
                        break;
                    case 3:
                        loadingScreen("Loading Resources");
                        transferMoney();
                        break;
                    case 4:
                        loadingScreen("Loading Current Balance");
                        checkBalance();
                        break;
                    case 5:
                        loadingScreen("Logging Out");
                        return;
                    default:
                        System.out.print("Choice Not Valid! ");
                        loadingScreen("Going Back to User Menu");
                }
            }
            else
            {
                System.out.print("""
                    Account Id Not found
                    Do you want to go back to main Menu (y = yes , n = no ) :  """);
                char userChoice = input.next().charAt(0); //  made string only to use string methods
                loadingScreen();
                if(userChoice == 'y' || userChoice == 'Y')
                    return;
            }
        }// end of while
    }   //  end of user Menu

    //  Finds the valid and suitable id for new account.

    public static int accountIDToBeAssigned()
    {
        int idToBeAssigned=0;
        Boolean  idFound = false;
        for(int i = 0; i < accountExists.size(); i++)
        {
            if(accountExists.get(i) == false &&)
            {
                idFound = true;
                idToBeAssigned = i + 101;
                break;
            }
        }

        if(idFound == false)
        {
            idToBeAssigned = accountExists.size() + 101;
        }

        return idToBeAssigned;
    }

    // Lists all the admins in table format

    public static void viewAdmins()
    {
                System.out.printf("""
                ┏━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━┓
                ┃%15s  ┃%15s  ┃%15s  ┃%15s    ┃      
                """, "Admin Name","Admin Exists","Admin ID","Admin Pin ");
        for(int i =0; i<adminName.length; i++)
        {

            if(adminExists[i])
            { 
                System.out.printf("""
                    ┣━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━┫
                    ┃%15s  ┃%15s  ┃%15s  ┃%15s    ┃      
                    """, adminName[i],adminExists[i],adminCredentials[i][0],adminCredentials[i][1]);
            }
        }
        System.out.println("┗━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━┛");
    }    

    //  just a simple , default loading screen

    public static void loadingScreen() throws InterruptedException
    {  
        System.out.print("\nLoading ");
        
        char[] spinner = {'|', '/', '-', '\\'};
        
        for (int i = 0; i < 5; i++) 
        {
            for(char j: spinner)
            {
                System.out.printf("\rPlease Wait %c",j);
                Thread.sleep(150);
            }
        }

        System.out.println("\r✅Transaction completed!    ");
    }


    //  For  waiting time between each action.
    //  Keep this method at the last of the program. For Easy Modification    

    public static void loadingScreen(String operation) throws InterruptedException
    {  
        System.out.print("\nLoading ");
        
        char[] spinner = {'|', '/', '-', '\\'};
        
        for (int i = 0; i < 5; i++) 
        {
            for(char j: spinner)
            {
                System.out.printf("\r%s %c",operation,j);
                Thread.sleep(150);
            }
        }
        System.out.println("\r✅Transaction completed!    ");
    }


    //  Keep File Handling Methods at last for better debugging

    public static void loadAdminData()
    {
        try(FileInputStream fis = new FileInputStream("AdminData.txt"))
        {
            Scanner reader  = new Scanner(fis);
            int i=0;
            while(reader.hasNextLine())
            {
                String line = reader.nextLine();
                String[] dataArray = line.trim().split(",");
                adminCredentials[i][0]= Integer.parseInt(dataArray[0]); 
                adminCredentials[i][1] = Integer.parseInt(dataArray[1]);
                adminName[i] = dataArray[2];
                adminExists[i] = Boolean.parseBoolean(dataArray[3]);
                i++;
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Admin File is missing!!!");
        }
        catch(IOException e1)
        {
            System.out.println("Something Went Wrong......");
        }
        catch(Exception e2)
        {
            System.out.println("Following error occured: "+e2.toString());
        }
    }// loadAdminData

}//class