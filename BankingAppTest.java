import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

// Note: This test suite uses static members and directly modifies the internal state 
// of the BankingApp class for testing purposes.

public class BankingAppTest {

    // Indices for easy reference, matching the constants in BankingApp
    private final int TEST_INDEX = 0;
    private final int RECEIVER_INDEX = 1;
    private final int IDINDEX = 0, PININDEX = 1, BALANCEINDEX = 2, TRANSFERINDEX = 3;

    @BeforeEach
    void setUp() {
        // --- 1. Clean up Static State ---
        // Reset the static arrays to a known, clean state before each test
        Arrays.fill(BankingApp.accountExists, false);
        Arrays.fill(BankingApp.accountNames, null);
        for (int i = 0; i < BankingApp.accountCredentials.length; i++) {
            Arrays.fill(BankingApp.accountCredentials[i], 0);
        }
        BankingApp.totalAccounts = 0;

        // --- 2. Setup a Standard Test User (Account ID 101) ---
        BankingApp.accountNames[TEST_INDEX] = "Test User";
        BankingApp.accountCredentials[TEST_INDEX][IDINDEX] = 101;
        BankingApp.accountCredentials[TEST_INDEX][PININDEX] = 1234;
        BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX] = 500;
        // Set a predictable Transfer ID
        BankingApp.accountCredentials[TEST_INDEX][TRANSFERINDEX] = 1000000; 
        BankingApp.accountExists[TEST_INDEX] = true;
        BankingApp.totalAccounts = 1;

        // --- 3. Setup a Receiver User (Account ID 102) ---
        BankingApp.accountNames[RECEIVER_INDEX] = "Receiver";
        BankingApp.accountCredentials[RECEIVER_INDEX][IDINDEX] = 102;
        BankingApp.accountCredentials[RECEIVER_INDEX][PININDEX] = 4321;
        BankingApp.accountCredentials[RECEIVER_INDEX][BALANCEINDEX] = 100;
        // Set a predictable Transfer ID
        BankingApp.accountCredentials[RECEIVER_INDEX][TRANSFERINDEX] = 2000000;
        BankingApp.accountExists[RECEIVER_INDEX] = true;
        BankingApp.totalAccounts = 2;
    }

    @AfterEach
    void tearDown() {
        // Clean up any files created by saveData/loadSavedData if necessary
        // Ensures tests are independent of file system changes.
        File dataFile = new File("accountsData.csv");
        if (dataFile.exists()) {
             dataFile.delete(); // Delete the file after each test
        }
    }


    // --- Core Logic Tests ---

    @Test
    void testCheckingDigitValid() {
        // Tests the validation logic for PINs
        assertTrue(BankingApp.checkingDigit("1234"), "PIN should be valid (4 digits)");
        assertTrue(BankingApp.checkingDigit("87654321"), "PIN should be valid (8 digits)");
        assertFalse(BankingApp.checkingDigit("123a"), "PIN should be invalid (contains non-digit)");
        assertFalse(BankingApp.checkingDigit(""), "PIN should be invalid (empty string)");
    }

    @Test
    void testAccountIDToBeAssigned() {
        // The ID assignment logic is based on the first '0' ID slot plus an offset (101).
        // Since the first two slots (0, 1) are occupied in setUp, the next available
        // ID should be based on index 2.
        int expectedID = 2 + 101; // Expected ID is 103
        int assignedID = BankingApp.accountIDToBeAssigned();
        assertEquals(expectedID, assignedID, "Should assign the next available ID (103)");
    }

    @Test
    void testFindReceiverIDIndexFound() {
        // Search for the receiver's known Transfer ID (2000000)
        int index = BankingApp.findReceiverIDIndex(2000000);
        assertEquals(RECEIVER_INDEX, index, "Should find the receiver at index 1");
    }

    @Test
    void testFindReceiverIDIndexNotFound() {
        // Search for a non-existent Transfer ID
        int index = BankingApp.findReceiverIDIndex(9999999);
        assertEquals(-1, index, "Should return -1 for a non-existent Transfer ID");
    }

    // --- State Manipulation Tests (Requires Mock Setup) ---

    @Test
    void testDepositMoney() {
        int initialBalance = BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX]; // 500
        int depositAmount = 250;
        
        // Mock the user input by calling the function directly
        BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX] = initialBalance;
        BankingApp.depositMoney(TEST_INDEX, depositAmount);

        int expectedBalance = initialBalance + depositAmount; // 750
        assertEquals(expectedBalance, BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX], 
                     "Balance should increase after deposit.");
    }

    @Test
    void testWithdrawMoney() {
        int initialBalance = BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX]; // 500
        int withdrawAmount = 150;

        // Mock the user input by calling the function directly
        BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX] = initialBalance;
        BankingApp.withdrawMoney(TEST_INDEX, withdrawAmount);

        int expectedBalance = initialBalance - withdrawAmount; // 350
        assertEquals(expectedBalance, BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX], 
                     "Balance should decrease after withdrawal.");
        
        // --- Potential Bug Check ---
        // Test for overdrawing (The current implementation allows negative balances, 
        // which might be an unintended bug in the original code.)
        int overdrawAmount = 600;
        BankingApp.withdrawMoney(TEST_INDEX, overdrawAmount);
        assertEquals(350 - 600, BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX],
                     "Original code allows negative balance. Test confirms this behavior.");
    }

    @Test
    void testTransferMoney() {
        int senderInitialBalance = BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX]; // 500
        int receiverInitialBalance = BankingApp.accountCredentials[RECEIVER_INDEX][BALANCEINDEX]; // 100
        int transferAmount = 200;

        // Mock the user input by calling the function directly (Transfer ID: 2000000)
        BankingApp.transferMoney(TEST_INDEX, 2000000, transferAmount);

        // Check sender balance
        int expectedSenderBalance = senderInitialBalance - transferAmount; // 300
        assertEquals(expectedSenderBalance, BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX], 
                     "Sender balance should decrease after transfer.");

        // Check receiver balance
        int expectedReceiverBalance = receiverInitialBalance + transferAmount; // 300
        assertEquals(expectedReceiverBalance, BankingApp.accountCredentials[RECEIVER_INDEX][BALANCEINDEX], 
                     "Receiver balance should increase after transfer.");
    }

    @Test
    void testDeleteAccount() {
        // Delete the Test User (Index 0)
        BankingApp.deleteAccount(TEST_INDEX);

        // Verify the flag is set to false
        assertFalse(BankingApp.accountExists[TEST_INDEX], "accountExists flag must be set to false.");
        
        // Verify all credentials are reset to 0
        assertEquals(0, BankingApp.accountCredentials[TEST_INDEX][IDINDEX], "ID must be reset to 0.");
        assertEquals(0, BankingApp.accountCredentials[TEST_INDEX][PININDEX], "PIN must be reset to 0.");
        assertEquals(0, BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX], "Balance must be reset to 0.");
        assertEquals(0, BankingApp.accountCredentials[TEST_INDEX][TRANSFERINDEX], "Transfer ID must be reset to 0.");
        
        // Verify the name is reset to an empty string
        assertEquals("", BankingApp.accountNames[TEST_INDEX], "Name must be reset to empty string.");
    }
    
    // --- File I/O Tests (These require the saveData method to successfully write a file) ---

    @Test
    void testSaveAndLoadData() {
        // 1. Save Current State (TEST_INDEX: 101, RECEIVER_INDEX: 102)
        BankingApp.saveData();

        // 2. Clear all in-memory data to simulate a fresh start
        Arrays.fill(BankingApp.accountExists, false);
        BankingApp.totalAccounts = 0;
        Arrays.fill(BankingApp.accountNames, null);
        for (int i = 0; i < BankingApp.accountCredentials.length; i++) {
            Arrays.fill(BankingApp.accountCredentials[i], 0);
        }
        
        // 3. Load data from the saved file
        BankingApp.loadSavedData();

        // 4. Verify data was loaded correctly for the TEST_INDEX
        assertTrue(BankingApp.accountExists[TEST_INDEX], "Account 101 should exist after loading.");
        assertEquals(101, BankingApp.accountCredentials[TEST_INDEX][IDINDEX], "Account ID 101 should match.");
        assertEquals(500, BankingApp.accountCredentials[TEST_INDEX][BALANCEINDEX], "Balance 500 should match.");
        assertEquals("Test User", BankingApp.accountNames[TEST_INDEX], "Name should match.");
        assertEquals(2, BankingApp.totalAccounts, "Total accounts count should be 2.");
    }

}