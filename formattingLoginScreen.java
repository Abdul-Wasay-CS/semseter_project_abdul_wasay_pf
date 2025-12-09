import java.util.Scanner;
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


  
  public static void main (String args[]) throws InterruptedException
  {
    
    // Load Previously Saved Data
    //loadSavedData();
    for(int i=1; i<=5; i++)
    {
      final int DELAYTIME = 50;
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
    System.out.println(BOLD+CYAN+"                          ╔═══════════════════════════════════════════════════════════════════════╗"+RESET);
    System.out.println(BOLD+CYAN+"                          ║"+RESET+"                       "+BRIGHTGREEN+"BANKING MANAGEMENT SYSTEM"+RESET+"                       "+BOLD+CYAN+"║"+RESET);
    System.out.println(BOLD+CYAN+"                          ╚═══════════════════════════════════════════════════════════════════════╝"+RESET);   


    int chosenOption = 0;

    // Loop for the choice in the first menu
      System.out.println(
                BOLD+YELLOW+"     ╔═════════════════════════════════════════════════════════════════════════════════╗\n"+RESET
              + BOLD+YELLOW+"     ║"+RESET+"   Welcome To the System, Please Select what do you want to do:                  ║\n"
              + BOLD+YELLOW+"     ║"+RESET+"    "+WHITE+"1. Create New Account "+RESET+"                                                       "+YELLOW+"║"+RESET+"\n"
              + BOLD+YELLOW+"     ║"+RESET+"    "+WHITE+"2. Log Into Account"+RESET+"                                                          "+YELLOW+"║"+RESET+"\n"
              + BOLD+YELLOW+"     ║"+RESET+"    "+WHITE+"3. Recover Account "+RESET+"                                                          "+YELLOW+"║"+RESET+"\n"
              + BOLD+YELLOW+"     ║"+RESET+"    "+RED+"4. Exit            "+RESET+"                                                          "+YELLOW+"║"+RESET+"\n"
              + BOLD+YELLOW+"     ╚═════════════════════════════════════════════════════════════════════════════════╝"+RESET+"\n"
              + "\n"+BRIGHTCYAN+"   Choose an option: "+RESET);

      // Choice Switch
      chosenOption = scanner.nextInt();
  }
}
