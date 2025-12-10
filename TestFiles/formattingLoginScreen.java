import java.util.*;
public class formattingLoginScreen
{
  static Scanner scanner = new Scanner(System.in);
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


  
 public static void main(String[] args) throws InterruptedException
 {
   loadSavedData();
    do {
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
}//main

}