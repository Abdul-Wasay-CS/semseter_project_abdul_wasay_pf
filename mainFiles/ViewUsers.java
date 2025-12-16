import java.util.ArrayList;
import java.util.List;

public class ViewUsers {
     static ArrayList<ArrayList<Integer>> accountCredentials = new ArrayList<>();
  static ArrayList<String> securityQuestion = new ArrayList<>();
  static ArrayList<String> accountNames = new ArrayList<>();
  static ArrayList<Boolean> accountExists = new ArrayList<>();
  static ArrayList<Boolean> blocked = new ArrayList<>();
  static int totalAccounts = 0;
  // Constants
  static final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;

  
  public static void main(String[] args) {
      accountNames.add("John Smith");
        ArrayList<Integer> user1Creds = new ArrayList<>();
        user1Creds.add(1001);  // ID
        user1Creds.add(1234);  // PIN
        user1Creds.add(5000);  // Balance
        user1Creds.add(2001);  // Transfer ID
        accountCredentials.add(user1Creds);
        blocked.add(false);
        accountExists.add(true);
        // User 2
        accountNames.add("Emma Johnson");
        ArrayList<Integer> user2Creds = new ArrayList<>();
        user2Creds.add(1002);
        user2Creds.add(5678);
        user2Creds.add(12500);
        user2Creds.add(2002);
        accountCredentials.add(user2Creds);
        blocked.add(false);
        accountExists.add(true);
        
        // User 3
        accountNames.add("Robert Davis");
        ArrayList<Integer> user3Creds = new ArrayList<>();
        user3Creds.add(1003);
        user3Creds.add(9999);
        user3Creds.add(250);
        user3Creds.add(0);  // No transfer ID
        accountCredentials.add(user3Creds);
        blocked.add(true);  // Blocked account
        accountExists.add(true);
        
        // User 4
        accountNames.add("Sarah Williams");
        ArrayList<Integer> user4Creds = new ArrayList<>();
        user4Creds.add(1004);
        user4Creds.add(4321);
        user4Creds.add(75500);
        user4Creds.add(2003);
        accountCredentials.add(user4Creds);
        blocked.add(false);
        accountExists.add(true);
        
        // User 5 (Inactive/deleted account)
        accountNames.add("Michael Brown");
        ArrayList<Integer> user5Creds = new ArrayList<>();
        user5Creds.add(1005);
        user5Creds.add(7777);
        user5Creds.add(0);
        user5Creds.add(0);
        accountCredentials.add(user5Creds);
        blocked.add(false);
        accountExists.add(false);  // Account doesn't exist anymore
        
        // User 6 (Long name to test formatting)
        accountNames.add("Alexandria Rodriguez");
        ArrayList<Integer> user6Creds = new ArrayList<>();
        user6Creds.add(1006);
        user6Creds.add(2468);
        user6Creds.add(1000000);
        user6Creds.add(2004);
        accountCredentials.add(user6Creds);
        blocked.add(false);
        accountExists.add(true);
        
        // User 7 (Zero balance)
        accountNames.add("Tom Wilson");
        ArrayList<Integer> user7Creds = new ArrayList<>();
        user7Creds.add(1007);
        user7Creds.add(1357);
        user7Creds.add(0);
        user7Creds.add(0);
        accountCredentials.add(user7Creds);
        blocked.add(false);
        accountExists.add(true);
    viewUsers();
  }

    public static void viewUsers()
    {
        System.out.printf("""
        ┏━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┓
        ┃      Name       ┃         ID      ┃      Pin        ┃     Balance     ┃   Tranfer ID    ┃      Blocked    ┃     Exists      ┃                   
        """);
    for (int i = 0; i <accountCredentials.size(); i++) {
        // 7 coulumns

        System.out.printf("""
            ┣━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━┫━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━┫
            ┃%-17s┃%-17d┃%-17d┃%-17d┃%-17d┃%-17b┃%-17b┃
            """, accountNames.get(i), accountCredentials.get(i).get(IDINDEX),accountCredentials.get(i).get(PININDEX), accountCredentials.get(i).get(BALANCEINDEX),accountCredentials.get(i).get(TRANSFERINDEX),blocked.get(i),accountExists.get(i));
    }
    System.out.println("┗━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┛");
    }
}
