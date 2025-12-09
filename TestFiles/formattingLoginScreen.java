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


  
  public static void main (String args[])
  {
    
      System.out.println(
                GREEN+"╔═════════════════════════════════════════════╗\n"+RESET
              + GREEN+"║"+RESET+"    "+YELLOW+"1. Create New Account "+RESET+"                   "+BLUE+"║"+RESET+"\n"
              + GREEN+"║"+RESET+"    "+YELLOW+"2. Log Into Account"+RESET+"                      "+BLUE+"║"+RESET+"\n"
              + GREEN+"║"+RESET+"    "+YELLOW+"3. Recover Account "+RESET+"                      "+BLUE+"║"+RESET+"\n"
              + GREEN+"║"+RESET+"    "+YELLOW+"4. Exit            "+RESET+"                      "+BLUE+"║"+RESET+"\n"
              + GREEN+"╚═════════════════════════════════════════════╝"+RESET+"\n"
              + GREEN+"Choose an option: "+RESET);

      // Choice Switch
      int chosenOption = scanner.nextInt();
  }
}
