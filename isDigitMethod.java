import java.util.Scanner;

public class checkingDigit
{
    public static void main(String args[])
    {
      Scanner input = new Scanner(System.in);

      String s = "48002";
      
      char currentChar;
      boolean isdigit = true;
      for(int i =0; i<s.lenght(); i++)
      {
        currentChar = s.charAt(i);
        if(!Character.isDigit(currentChar))
          isDigit = false;
      }

      return isdigit;
    }
}
