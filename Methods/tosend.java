    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHTBLACK = "\u001B[90m";
    public static final String BRIGHTRED = "\u001B[91m";
    public static final String BRIGHTGREEN = "\u001B[92m";
    public static final String BRIGHTYELLOW = "\u001B[93m";
    public static final String BRIGHTBLUE = "\u001B[94m";
    public static final String BRIGHTMAGENTA = "\u001B[95m";
    public static final String BRIGHTCYAN = "\u001B[96m";
    public static final String BRIGHTWHITE = "\u001B[97m";
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";

    static ArrayList<Boolean> accountExists = new ArrayList<>();
    static int[] ownerCredentials = {02, 72766};
    static String ownerName = "Solitary Monarch";
    static int[][] adminCredentials = new int[5][2]; // Index 0, and 1 are id number, and pin
    static String[] adminName = new String[5];
    static boolean[] adminExists = new boolean[5];
    static ArrayList<ArrayList<Integer>> accountCredentials = new ArrayList<>();
    static ArrayList<String> securityQuestion = new ArrayList<>();
    static ArrayList<String> accountNames = new ArrayList<>();
    static ArrayList<String> logs = new ArrayList<>();
    static ArrayList<Boolean> blocked = new ArrayList<Boolean>();
    static int totalAccounts = 0;
    static int userID;
    static final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;































public static void main(String[] args) {
        loadSavedData();
        do {
            System.out.println("==================Bank Of Valhalla==================");
            System.out.println("What do you want to login as?\n1) Owner\n2) Admin\n3) Customer");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter the owner ID: ");
                    int ownerID = scanner.nextInt();
                    System.out.println("Enter the pin: ");
                    int ownerPIN = scanner.nextInt();
                    if (ownerID == ownerCredentials[0] && ownerPIN == ownerCredentials[1]) {
                        ownerMenu();
                    } else System.out.println("Incorrect Credentials");
                    break;
                case 2:
                    userMenu();
                    break;
                case 3:
                    System.out.println("Enter your ID number: ");
                    int idNumber = scanner.nextInt();
                    adminMenu(idNumber);
                    break;
                default:
                    System.out.println("Choose a valid option from the given ones.");
            }
        } while (true);
    }

    // Menu for the Owner
    public static void ownerMenu() {
        System.out.println("=================Owner Menu===============\n1) Create an admin account\n2) Delete an admin account\n3) Exit");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                createAdmin();
                break;
            case 2:
                deleteAdmin();
                break;
            case 3:
                return;
            default:
                System.out.println("Please select from the given options!");
        }
    }

    // Add Admin Account
    public static void createAdmin() {
        System.out.println("Enter Admin Name: ");
        String adminName = scanner.nextLine();

        String userPINs;
        String userPINsConfirm;
        int finalPIN = 0;
        boolean matchingPINs = false;

        do {
            System.out.print("Enter new PIN (4 or 8 digits only): ");
            userPINs = scanner.nextLine();

            if (checkingDigit(userPINs) && (userPINs.length() == 4 || userPINs.length() == 8)) {
                System.out.print("Confirm the PIN: ");
                userPINsConfirm = scanner.nextLine();

                if (userPINs.equals(userPINsConfirm)) {
                    finalPIN = Integer.parseInt(userPINs);
                    matchingPINs = true;
                } else {
                    System.out.println(RED + "PINs don't match! Try again." + RESET);
                }
            } else {
                System.out.println(RED + "Use the correct format for the PIN (4 or 8 digits only)." + RESET);
            }
        } while (!matchingPINs);
        System.out.print("Enter new PIN (4 or 8 digits only): ");

        adminCredentials[adminIDToBeAssigned() - 505][1] = finalPIN;
        adminCredentials[adminIDToBeAssigned() - 505][0] = adminIDToBeAssigned();
        adminExists[adminIDToBeAssigned() - 505] = true;
        System.out.println("Admin Account Created!");
    }

    // Delete Admin Account
    public static void deleteAdmin() {
        System.out.println("Enter the Admin ID you want to delete: ");
        int idToDelete = scanner.nextInt();
        int indexToDelete = idToDelete - 505;

        adminCredentials[indexToDelete][0] = 0;
        adminCredentials[indexToDelete][1] = 0;
        adminName[indexToDelete] = "";
        adminExists[indexToDelete] = false;
    }

    // Admin ID to be Assigned
    public static int adminIDToBeAssigned() {
        int idToAssign = 0;
        for (int i = 0; i < 5; i++) {
            if (adminExists[i] == true) {
                idToAssign = i - 505;
                break;
            }
        }
        return idToAssign;
    }

    // Menu for Admins
    public static void adminMenu(int idNumber) {
        if (adminExists[idNumber - 505] == false) {
            System.out.println("Wrong Admin Credentials.");
            return;
        }


        System.out.println("===========Admin Menu===========");
        System.out.println("What do you want to do?\n1) View the logs\n2) Delete an account\n3) Create an account\n4)Block an account");
        int choice = scanner.nextInt();
        scanner.nextLine();
        int index = 0;
        switch (choice) {
            case 1:
                viewLogs();
                break;
            case 2:
                System.out.print("Enter index of the account: ");
                index = scanner.nextInt();
                deleteAccount(index);
                break;
            case 3:
                createAccount();
                break;
            case 4:
                System.out.print("Enter index of the account: ");
                index = scanner.nextInt();
                blockAccount(index);
                break;
            default:
                System.out.println("Please try again with a valid option!");
        }
    }



































    // View logs (for admin only)
    public static void viewLogs() {
        for (int i = logs.size() - 1; i >= 0; i--) {
            System.out.println(i + ") " + logs.get(i));
        }
    }



    // Block Account
    public static void blockAccount(int index) {
        blocked.set(index, true);
    }






    // Recover account
    public static void recoverAccount(int accountID) {
        try {
            int indexNumber = accountID - 101;

            if (indexNumber < 0 || indexNumber >= accountExists.size() || !accountExists.get(indexNumber)) {
                System.out.println(RED + "Account doesn't exist!" + RESET);
                return;
            }
            System.out.println("RECOVER ACCOUNT:");
            System.out.print("Enter your account ID: ");
            int idToRecover = scanner.nextInt();
            int indexToRecover = idToRecover - 101;
            String answer = "";

            for (int i = 3; i > 0; i--) {
                System.out.println("Answer the following security question:\nWhat's your favourite car?");
                answer = scanner.nextLine();
                if (answer != securityQuestion.get(indexToRecover)) {
                    System.out.println(RED + "Wrong Answer!" + RESET);
                    System.out.println(i + " tries remaining.");
                }

            }
            if (answer != securityQuestion.get(indexToRecover)) {
                System.out.println("No more tries remaining.\nExiting...");
                return;
            }

            blocked.set(indexToRecover, false);
            System.out.println();
        } catch (Exception e) {
            System.out.println(RED + "Recovery error: " + e.getMessage() + RESET);
        }
    }


















    // Add logs
    public static void addLogs(String activity) {
        logs.add(activity);
    }

   
