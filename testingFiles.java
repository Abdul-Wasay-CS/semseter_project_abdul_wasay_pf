import java.io.*;
import java.util.Scanner;
public class testingFiles {
    static Scanner input = new Scanner(System.in);
  //Global Arrays, The first index of the accountCredentials is reserved for account Ids. The second index for PIN, and the third for the Account Balance.
  static boolean[] accountExists = new boolean[100];
  static int[][] accountCredentials = new int[100][4];
  static String[] accountNames = new String[100];
  static int totalAccounts = 0;
  static int userID;
  static final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;
    public static void main(String[] args)
    {
        // accountCredentials[0][IDINDEX] = 12345;
        // accountCredentials[0][PININDEX] = 54321;
        // accountCredentials[0][BALANCEINDEX] = 1000;
        // accountCredentials[0][TRANSFERINDEX] = 2;
        // accountExists[0] = true;
        // accountNames[0] = "John Doe";
        // totalAccounts++;
        // accountCredentials[1][IDINDEX] = 67890;
        // accountCredentials[1][PININDEX] = 9876;
        // accountCredentials[1][BALANCEINDEX] = 2500;
        // accountCredentials[1][TRANSFERINDEX] = 5;
        // accountExists[1] = true;
        // accountNames[1] = "Jane Smith";
        // totalAccounts++;
        // saveData();
      
      
       loadSavedData();

        // Display loaded data for verification
        for (int i = 0; i < totalAccounts; i++) {
            System.out.println("                Account " + (i + 1) + ":");
            System.out.print("  ID: " + accountCredentials[i][IDINDEX]);
            System.out.print("  PIN: " + accountCredentials[i][PININDEX]);
            System.out.print("  Balance: " + accountCredentials[i][BALANCEINDEX]);
            System.out.print("  Transfers: " + accountCredentials[i][TRANSFERINDEX]);
            System.out.print("  Exists: " + accountExists[i]);
            System.out.print("  Name: " + accountNames[i]);
            System.out.println();
        }
    }

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
}