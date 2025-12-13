import java.util.*;
import java.io.*;

import static java.lang.System.out;
public class saveAdminData {
    public static void main(String[] args) throws InterruptedException 
    {
        
        
        // Initialize all at once
        int[][] adminCredentials = {
            {1001, 1234},  // Admin 1: ID=1001, PIN=1234
            {1002, 5678},  // Admin 2: ID=1002, PIN=5678  
            {1003, 9012},  // Admin 3: ID=1003, PIN=9012
            {1004, 3456},  // Admin 4: ID=1004, PIN=3456
            {1005, 7890}   // Admin 5: ID=1005, PIN=7890
        };

        String[] adminName = {
            "Alice Manager",
            "Bob Supervisor", 
            "Charlie Auditor",
            "Diana Teller",
            "Eve Security"
        };

        boolean[] adminExists = {true, true, true, false, true};


        //  saveAdminData method starts here
        while(true)
        {
            try(FileOutputStream fos  = new FileOutputStream("AdminData.txt"))
            {
                PrintWriter writer = new PrintWriter(fos);

                for(int i =0; i<5; i++)
                    writer.printf("%d,%d,%s,%b\n", adminCredentials[i][0],adminCredentials[i][1], adminName[i],adminExists[i]);
            writer.close();
                System.out.println("Written SUccessfully");

                break;
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
}
