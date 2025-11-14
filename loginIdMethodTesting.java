import java.util.Scanner;

public class loginIdMethodTesting{

	static int accountsCredentials[][] = {
					 {101,3045,15000}
				    ,{102,5023,40083}
				    ,{103,5293,73000}
				    ,{104,5882,80000}
				    ,{105,2724,88000}
		};
	static int currentAccountId,currentAccountPin,currentAccountBalance;
	public static void main(String[] args) {

		login();
	}

	static public void login()
	{
		Scanner input = new Scanner(System.in);
		int currentAccountIndex=0;
		boolean idNotFound;
		do
		{
			idNotFound = true;
			System.out.println("Enter the user id");
			int userId = input.nextInt();
			for(int i=0; i< accountsCredentials.length; i++)
			{
				if(accountsCredentials[i][0] == userId)
				{
					currentAccountIndex = i;
					idNotFound = false;
					break;
				}
			}//end of for

			System.out.println("Account Not Found, kindly put a valid Id");
		}while(idNotFound);

		currentAccountId = accountsCredentials[currentAccountIndex][0];
		currentAccountPin = accountsCredentials[currentAccountIndex][1];
		currentAccountBalance = accountsCredentials[currentAccountIndex][2];


		System.out.printf("\nCurrent id: %d \nCurrent pin: %d \nCurrent Balance: %d",currentAccountId,currentAccountPin,currentAccountBalance);
	//  checkPin();


	}//end of login

}//end of class