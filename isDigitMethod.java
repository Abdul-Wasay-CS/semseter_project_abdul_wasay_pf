import java.util.Scanner;

public class checkingDigit
{
    public static void main(String args[])
    {
      Scanner input = new Scanner(System.in);

      String s = "48002";


	      

    }
    public static boolean isDigit(String userPin)
    {
    	char currentChar;
      	boolean isdigit = true;
      	for(int i =0; i<userPin.lenght(); i++)
	      {
		currentChar = userPin.charAt(i);
		if(!Character.isDigit(currentChar))
		  isDigit = false;
	      }

	return isdigit;
    }
}

