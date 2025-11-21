import java.io.*;

public class UsingBufferReader 
{
    public static void main(String[] args) throws Exception
    {
        // Creating BufferedReader for Input
        BufferedReader bfri = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the Path : ");
        
        // Reading File name
        String path = bfri.readLine();
        
        // Creating an object of BufferedReader class
        BufferedReader bfro = new BufferedReader(new FileReader(path));

        // Declaring a string variable
        String st;
      
        // Condition holds true till there is character in a string
        while ((st = bfro.readLine()) != null)
          	System.out.println(st);
    }
}