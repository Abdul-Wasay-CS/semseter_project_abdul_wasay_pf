import java.io.*;
import java.util.*;

public class SaveCustomerData {
    public static void main(String[] args) {
        saveCustomerData();

    }

    public static void saveCustomerData()
    {
        try(FileOutputStream fos = new FileOutputStream("CustomerData.txt"); PrintWriter writer = new PrintWriter(fos))
        {
            
        }
        catch()
    }
}
