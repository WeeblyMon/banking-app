/*
  Imports for mainBankingApp:
  - ArrayList and List: data storage as we're not using a database.
  - Scanner for user input
 - InputMismatchException for handling invalid user input.
  - LocalDateTime for transaction timestamps.
 */
package Package;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalDateTime;

/*
    mainBankingApp
    -This is the main class, this is where most of the functional events happen.
   - This has the command line interface menu that users can interact with.
  -  Includes functional methods that supports the menu and relevant validation.
*/

//Initial set up, creates scanner and accounts list to be used throughout.
public class mainBankingApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Account> accounts = new ArrayList<>();

    /*
        Main
        - Command Line interface for user
      - a simple while loop that allows the menu to persist until the user dismisses the menu using 0.
       - displays numbers that users can type in, that determines the action of the menu.
    */

    public static void main(String[] args) {

        boolean exit = false;

        // Pre-set 2 accounts when program is first run.
        Account aliceAccount = new Account("Alice", 1000.00);
        Transaction aliceInitialTransaction = new Deposit(1000.00, LocalDateTime.now(), "Initial deposit", "Bank Transfer");
        aliceAccount.addTransaction(aliceInitialTransaction);
        accounts.add(aliceAccount);

        Account bobAccount = new Account("Bob", 1500.00);
        Transaction bobInitialTransaction = new Deposit(1500.00, LocalDateTime.now(), "Initial deposit", "Salary Payment");
        bobAccount.addTransaction(bobInitialTransaction);
        accounts.add(bobAccount);

        //CLI menu- while loop.
        while (!exit) {
            System.out.println("\nWelcome to Omega Cool Banking App. Please choose an option:");
            System.out.println("1. List all bank accounts");
            System.out.println("2. View bank account details and transactions");
            System.out.println("3. Add a transaction to an account");
            System.out.println("4. Remove a bank account");
            System.out.println("5. Create a new bank account");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            //Switch, it's a bit cleaner to use than if statement as the user is only typing in single numbers.
            switch (choice) {
                case 1:
                    listAllAccounts();
                    break;
                case 2:
                    viewAccountDetails();
                    break;
                case 3:
                    addTransactionToAccount();
                    break;
                case 4:
                    removeAccount();
                    break;
                case 5:
                    createNewAccount();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        //if user presses 0, exits the loop.
        System.out.println("Goodbye!");
        scanner.close();
    }

    /*
        ListAllAccounts
        - is a method that gets all available bank accounts and basic details
        - Provides important overview of accounts.
        - iterates through and prints basic details such as name and balance.
     */

    private static void listAllAccounts() {
        //if user deletes all accounts, this will print out.
        if (accounts.isEmpty()) {
            System.out.println("No accounts available.");
        } else {
            //iterate and prints out preset accounts and users created ones and their basic details.
            System.out.println("\nList of All Bank Accounts:");
            for (Account account : accounts) {
                System.out.println("Account Name: " + account.getName() + ", Balance: " + account.getBalance());
            }
        }
    }

    /*
        viewAccountDetails
        - gets additional details of an account
        - user will have to type in the name of the account
        - it will show transactions in detail.
     */
    private static void viewAccountDetails() {
        //prompt user for account name
        System.out.print("Enter the name of the account to view details: ");
        String accountName = scanner.nextLine();

        //attempts to find account name
        Account account = findAccountByName(accountName);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Account Name: " + account.getName());
        System.out.println("Account Balance: " + account.getBalance());

        //gets transactions, returns no transactions available if there none.
        List<Transaction> transactions = account.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions available for this account.");
        } else {
            System.out.println("Transactions for this account:");
            for (Transaction transaction : transactions) {
                System.out.println("    - Transaction Date: " + transaction.getDate());
                System.out.println("      Type: " + transaction.getType());
                System.out.println("      Amount: " + transaction.getAmount());
                System.out.println("      Description: " + transaction.getDescription());
                // Include other relevant details here, like source for Deposit or method for Withdrawal
                if (transaction instanceof Deposit) {
                    System.out.println("      Source: " + ((Deposit) transaction).getDepositSource());
                } else if (transaction instanceof Withdrawal) {
                    System.out.println("      Method: " + ((Withdrawal) transaction).getWithdrawMethod());
                    System.out.println("      Fee: " + ((Withdrawal) transaction).getFee());
                }
                System.out.println();
            }
        }
    }

    /*
        addTransactionToAccount
        - this method allows users to add a transaction to an account.
        - could be deposit or withdrawal
        - user can determine amount, this will change balance
        - allows user to put in source and description to add to authenticity
     */
    private static void addTransactionToAccount() {
        //prompt user for name of account
        System.out.print("Enter the name of the account: ");
        String accountName = scanner.nextLine();

        //code attempts to find the account by its name
        Account account = findAccountByName(accountName);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        //asks what type of transaction the user wants
        System.out.println("Type of transaction (Deposit/Withdrawal): ");
        String transactionType = scanner.nextLine();

        System.out.print("Enter the amount: ");
        double amount;
        try {
            amount = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for amount. Please enter a valid number.");
            scanner.nextLine();
            return;
        }
        scanner.nextLine();

        //little bit of validation
        if (amount <= 0) {
            System.out.println("The amount must be greater than zero.");
            return;
        }

        System.out.print("Enter transaction description: ");
        String description = scanner.nextLine();

        Transaction transaction = null;

        if ("Deposit".equalsIgnoreCase(transactionType)) {
            System.out.print("Enter the source of the deposit: ");
            String source = scanner.nextLine();
            transaction = new Deposit(amount, LocalDateTime.now(), description, source);
        } else if ("Withdrawal".equalsIgnoreCase(transactionType)) {
            System.out.print("Enter the withdrawal method (e.g., ATM, check, online): ");
            String method = scanner.nextLine();
            System.out.print("Enter the withdrawal fee (if any, or enter 0): ");
            double fee;
            try {
                fee = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for fee. Please enter a valid number.");
                scanner.nextLine();
                return;
            }
            scanner.nextLine();
            transaction = new Withdrawal(amount, LocalDateTime.now(), description, method, fee);
        } else {
            System.out.println("Invalid transaction type.");
            return;
        }

        // adds transaction to account and changes balance
        account.addTransaction(transaction);
        System.out.println("Transaction added successfully. New Balance: " + account.getBalance());
    }

    /*
        removeAccount
        - removes the account from the list.
        - user is prompted to enter the name of the account
        - additional prompt is created to stop accidental removal
     */
    private static void removeAccount() {
        System.out.print("Enter the name of the account to be removed: ");
        String accountName = scanner.nextLine();

        Account accountToRemove = findAccountByName(accountName);
        if (accountToRemove == null) {
            System.out.println("Account not found.");
            return;
        }

        // Check if the account has a balance, prevents accidental removal. asks user if they're sure.
        if (accountToRemove.getBalance() > 0) {
            System.out.println("This account still has a balance of " + accountToRemove.getBalance() + ".");
            System.out.print("Are you sure you want to remove this account? (yes/no): ");
            String userResponse = scanner.nextLine();

            if (!"yes".equalsIgnoreCase(userResponse)) {
                System.out.println("Account removal cancelled.");
                return;
            }
        }

        accounts.remove(accountToRemove);
        System.out.println("Account removed successfully.");
    }

    /*
        createNewAccount
        - Allows user to create their own account
        - validation prevents same name account, this is not ideal- improvements could be made- see readme.
        - allows users to set initial balance.
        - stores new account in account list, faux persistent storage.
     */
    private static void createNewAccount() {
        System.out.print("Enter the name for the new account: ");
        String accountName = scanner.nextLine();

        // Checking if an account with the same name already exists
        if (findAccountByName(accountName) != null) {
            System.out.println("An account with this name already exists.");
            return;
        }

        //allow users to put intitial balance
        System.out.print("Enter the initial balance for the new account: ");
        double initialBalance;
        try {
            initialBalance = scanner.nextDouble();
        } catch (InputMismatchException e) {  //has to be integer
            System.out.println("Invalid input for balance. Please enter a valid number.");
            scanner.nextLine();
            return;
        }

        //stops inital balance from being negative.
        if (initialBalance < 0) {
            System.out.println("Initial balance cannot be negative.");
            return;
        }

        scanner.nextLine(); //new line

        //stores new account in account list.
        Account newAccount = new Account(accountName, initialBalance);
        accounts.add(newAccount);
        System.out.println("New account created successfully: " + accountName);
    }

    /*
        findAccountByName
        - simple method
        - for loop that iterates through accounts list,
        - returns account by name.
        - ignores case sensitivity.
     */
    private static Account findAccountByName(String name) {
        //for loop, iterates through accounts list.
        for (Account account : accounts) {
            if (account.getName().equalsIgnoreCase(name)) {
                return account;
            }
        }
        return null;
    }

}
