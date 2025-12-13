import java.io.FileNotFoundException;

// load Admin data from the file.

public static void loadAdminData()
{
    try(FileInputStream fis = new FileInputStream("AdminData.txt"); Scanner reader = new Scanner(fis))
    {
        int i =0;
        while(reader.hasNextLine())
        {
            String line = reader.nextLine();
            String[] allAdminData = line.split(",");
            
            adminCredentials[i][0] = Integer.parseInteger(allAdminData[0]);
            adminCredentials[i][1] = Integer.parseInt(allAdminData[1]);
            adminName[i] = Integer.(allAdminData[2]);
            adminExists[i] = Boolean.parseBoolean(allAdminData[3]);
            i++;
        }
    }
    catch(ArrayIndexOutOfBoundsException e1)
    {
        System.out.println("There are more than five admin data in the file, pls delete old admin data.");
        loadingScreen();
    }
    catch(IOException e2)
    {
        System.out.println("Something went wrong with the files..");
        loadingScreen();
    }
    catch(Execption e3)
    {
        System.out.println("An unexpected error has occured");
        loadingScreen();
    }
}// end of loadAdminData()