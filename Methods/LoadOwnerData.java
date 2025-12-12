import java.util.*;
import java.io.*;

public class LoadOwnerData
{

    static int[][] adminCredentials = new int[5][2]; // Index 0, and 1 are id number, and pin
    static String[] adminName = new String[5];
    static boolean[] adminExists = new boolean[5];

    public static void main(String[] args) 
    {
        loadAdminData();
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
    }
}
