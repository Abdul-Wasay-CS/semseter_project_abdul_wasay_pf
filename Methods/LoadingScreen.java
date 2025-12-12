import java.util.*;
public class LoadingScreen
{

    public static void main(String args[]) throws InterruptedException 
    {
        // System.out.println("\nProcessing Transaction");
        // System.out.print("[");
        
        // int total = 50;
        // for (int i = 0; i <= total; i++) {
        //     // Progress bar
        //     System.out.print("\r[");
        //     for (int j = 0; j < i; j++) 
        //         System.out.print("█");
        //     for (int j = i; j < total; j++) System.out.print(" ");
        //         System.out.print("] " + (i*2) + "%");
            
        //     try {
        //         Thread.sleep(30 + (int)(Math.random() * 50));
        //     } catch (InterruptedException e) {
        //         Thread.currentThread().interrupt();
        //     }
        // }
        // System.out.println("\r✅ Transaction processed successfully!                        ");
        // In login method:
        loadingScreen("Authenticating");

        // After withdrawal:
        loadingScreen("Processing cash");

        // During transfer:
        loadingScreen("Validating transfer");

        // On logout:
        loadingScreen("Saving session");
    }
    
        //  just a simple , default loading screen

    public static void loadingScreen() throws InterruptedException
    {  
        System.out.print("\nLoading ");
        
        char[] spinner = {'|', '/', '-', '\\'};
        
        for (int i = 0; i < 5; i++) 
        {
            for(char j: spinner)
            {
                System.out.printf("\rPlease Wait %c",j);
                Thread.sleep(150);
            }
        }

        System.out.println("\r✅Transaction completed!    ");
    }


    //  For  waiting time between each action.
    //  Keep this method at the last of the program. For Easy Modification    

    public static void loadingScreen(String operation) throws InterruptedException
    {  
        System.out.print("\nLoading ");
        
        char[] spinner = {'|', '/', '-', '\\'};
        
        for (int i = 0; i < 5; i++) 
        {
            for(char j: spinner)
            {
                System.out.printf("\r%s %c",operation,j);
                Thread.sleep(150);
            }
        }
        System.out.println("\r✅Transaction completed!    ");
    }


}