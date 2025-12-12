public static void userMenu()
{
    while(true)
    {
        System.out.println("Enter your Login id: ");
        userID = scanner.nextInt();
        if(userID > accountExists.size() || userID < 101)
        {
            System.out.println("Invalid ID, Please enter a valid Id: ");
            continue;
        }
        
        if(accountExists.get(userID-101) ==true && blocked.get(userID-101))
        {
            System.out.println("Login Succesfull!");
            loadingScreen();
            System.out.printf("""
                =============================User Menu===============================
                |                                                                   |
                |   1)  View Account Detials            2)  Withrdraw Money         |
                |                                                                   |
                |   3)  Transfer Money                  4)  Check Balance           |
                |                                                                   |
                |                       5)  Log out                                 |
                |                                                                   |
                =====================================================================

                Enter Your Choice:  """);
            
            //  incase user enters something other than integer.
            int userChoice;
            while(true)
            {
                try
                {
                    userChoice = scanner.nexInt();
                    break;
                }
                catch(InputMismatchExcpetion e1)
                {
                    System.out.println("Please Enter an Integer Only. ")
                    scanner.nextLine()  //  stops the infinte catch madness
                    loadingScreen();
                }
            }//end of while
            switch(userChoice)
            {
                case 1:
                    loadingScreen();
                    viewAccountDetials();
                    break;
                case 2:
                    loadingScreen();
                    withdrawMoney();
                    break;
                case 3:
                    loadingScreen();
                    transferMoney();
                    break;
                case 4:
                    loadingScreen();
                    checkBalance();
                    break;
                case 5:
                    return;
                default:
                    System.out.print("Choice Not Valid! ");
                    loadingScreen()
            }
        }
        else
        {
            System.out.print("""
                Account Id Not found
                Do you want to go back to main Menu (y = yes , n = no ) :  """);
            char userChoice = input.next().charAt(0); //  made string only to use string methods
            loadingScreen();
            if(userChoice == 'y' || userChoice == 'Y')
                return;
        }
    }// end of while
}   //  end of user Menu