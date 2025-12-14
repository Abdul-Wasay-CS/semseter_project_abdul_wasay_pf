import java.io.*;
import java.util.*;

public class SaveCustomerData {


  //  User Data Variables

    static ArrayList<ArrayList<Integer>> accountCredentials = new ArrayList<>();
    static ArrayList<String> securityQuestion = new ArrayList<>();
    static ArrayList<String> accountNames = new ArrayList<>();
    static ArrayList<Boolean> accountExists = new ArrayList<>();
    static ArrayList<Boolean> blocked = new ArrayList<>();
    static int totalAccounts = 0;

    //  Logs Variable

    static ArrayList<String> logs = new ArrayList<>();

    //  Constants representing index number of user's each information.

    static final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;


    public static void main(String[] args) 
    {
        // Initialize accountCredentials with 3 sample users
        accountCredentials.add(new ArrayList<>(Arrays.asList(1001, 1234, 5000, 0)));
        accountCredentials.add(new ArrayList<>(Arrays.asList(1002, 5678, 10000, 500)));
        accountCredentials.add(new ArrayList<>(Arrays.asList(1003, 9999, 2500, 0)));

        // Initialize account names
        accountNames.add("Alice Johnson");
        accountNames.add("Bob Smith");
        accountNames.add("Charlie Brown");

        // Initialize security questions
        securityQuestion.add("What is your pet's name?");
        securityQuestion.add("What city were you born in?");
        securityQuestion.add("What is your mother's maiden name?");

        // Initialize account status
        accountExists.add(true);
        accountExists.add(true);
        accountExists.add(true);  // Account doesn't exist

        // Initialize blocked status
        blocked.add(false);
        blocked.add(false);
        blocked.add(true);  // Account is blocked

        // Initialize logs with some sample entries
        logs.add("2024-01-15 10:30: User 1001 logged in");
        logs.add("2024-01-15 11:45: User 1002 transferred $500");
        logs.add("2024-01-15 12:00: User 1003 account blocked");

        // Set total accounts
        totalAccounts = accountCredentials.size();

        saveCustomerData();

    }

    public static void saveCustomerData()
    {
        try(FileOutputStream fos = new FileOutputStream("CustomerData.txt"); PrintWriter writer = new PrintWriter(fos))
        {

            //  File data format:
            
            //  Id,Pin,Balance,TransferIndex,SecurityQuesiton,Name,accountExists,Blocked
            
            //  Each value is seperated by a comma, each line = one user record
            for(int i =0; i<accountCredentials.size(); i++)
            {
                writer.printf("%d,%d,%d,%d,%s,%s``,%b,%b",accountCredentials.get(i).get(IDINDEX),
                                                    accountCredentials.get(i).get(PININDEX),
                                                    accountCredentials.get(i).get(BALANCEINDEX),
                                                    accountCredentials.get(i).get(TRANSFERINDEX),
                                                    securityQuestion.get(i),
                                                    accountNames.get(i),
                                                    accountExists.get(i),
                                                    blocked.get(i)
                                                );

                writer.println();  // add a new line.
            }
        }
        catch(FileNotFoundException e)
        {
            //  incase the file is missing.

            System.out.println("File was not found, creating file.");
            File file = new File("AdminData.txt");
            try
            {
                if(file.createNewFile())
                    System.out.println("File Succesfully Created. ");
                else
                    System.out.println("File coulnd be created");
            }
            catch(IOException e1)
            {
                System.out.println("Something went Wrong. ");
            }
        }
        catch(IOException e)
        {
            System.out.println("File is misbehaving");
        }
        catch(Exception e)
        {
            System.out.println("UNexpected error, diplayed for debugging "+e.toString());
        }
    }
}
