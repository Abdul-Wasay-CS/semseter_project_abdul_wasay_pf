import java.io.*;
import java.util.*;

public class LoadCustomerData {
    
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



    public static void main(String[] args) {
        
        loadCustomerData();
        System.out.println("Loading successfull.....");


        printAllAccountDataSimple();
    }

    public static void loadCustomerData()
    {
        //  File data format:
        //  Id,Pin,Balance,TransferIndex,SecurityQuesiton,Name,accountExists,Blocked
    
        //  Each value is seperated by a comma, each line = one user record
        
        try(FileInputStream fis = new FileInputStream("CustomerData.txt"); Scanner reader = new Scanner(fis))
        {
            // clear out any previous garbage data.
            accountCredentials.clear();
            accountNames.clear();
            accountExists.clear();
            securityQuestion.clear();
            blocked.clear();

            int i =0;
            while(reader.hasNextLine())
            {
                String line = reader.nextLine();
                String[] customerData = line.split(",");
                if(!line.isEmpty())
                {
                    accountCredentials.add(new ArrayList<>());
                    accountCredentials.get(i).add(Integer.parseInt(customerData[0]));   //  gets ID
                    accountCredentials.get(i).add(Integer.parseInt(customerData[1]));   //  gets pin
                    accountCredentials.get(i).add(Integer.parseInt(customerData[2]));   //  gets Balance
                    accountCredentials.get(i).add(Integer.parseInt(customerData[3]));   //  gets TransferIndex
                    securityQuestion.add(customerData[4]);
                    accountNames.add(customerData[5]);
                    accountExists.add(Boolean.parseBoolean(customerData[6]));
                    blocked.add(Boolean.parseBoolean(customerData[7]));
                    i++;
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Something went wrong with the files!"+e.toString());
        }
        catch(Exception e)
        {
            System.out.println("An unexpected error occured.");
        }
    }// loadCustomerData

    public static void printAllAccountDataSimple() 
    {
        System.out.println("=== ALL USER ACCOUNTS ===");
        
        for(int i = 0; i < accountCredentials.size(); i++) 
        {
            System.out.println("\n--- User #" + (i+1) + " ---");
            System.out.println("Name: " + accountNames.get(i));
            System.out.println("ID: " + accountCredentials.get(i).get(IDINDEX));
            System.out.println("PIN: " + accountCredentials.get(i).get(PININDEX));
            System.out.println("Balance: $" + accountCredentials.get(i).get(BALANCEINDEX));
            System.out.println("Security Question: " + securityQuestion.get(i));
            System.out.println("Transfer Amount: $" + accountCredentials.get(i).get(TRANSFERINDEX));
            System.out.println("Account Exists: " + (accountExists.get(i) ? "Yes" : "No"));
            System.out.println("Blocked: " + (blocked.get(i) ? "Yes" : "No"));
        }
    }
}
