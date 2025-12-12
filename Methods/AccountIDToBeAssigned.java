import java.util.*;
import java.io.*;

public class AccountIDToBeAssigned {

    static ArrayList<Boolean> accountExists = new ArrayList<>();

    public static void main(String[] args) {
        
        System.out.println(accountIDToBeAssigned());
    
    }
    
    public static int accountIDToBeAssigned()
    {
        int idToBeAssigned=0;
        Boolean  idFound = false;
        for(int i = 0; i < accountExists.size(); i++)
        {
            if(accountExists.get(i) == false )
            {
                idFound = true;
                idToBeAssigned = i + 101;
                break;
            }
        }

        if(idFound == false)
        {
            idToBeAssigned = accountExists.size() + 101;
        }

        return idToBeAssigned;
    }




    
}