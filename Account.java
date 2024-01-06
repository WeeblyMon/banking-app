package Package;

import java.util.ArrayList;
import java.util.List;

/*
   Account
    -abstract class
    -bunch of getters and setters for anything related to accounts
 */
public class Account {
    private final String name;
    private double balance;
    private final List<Transaction> transactions;

    public Account(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        // Depending on the transaction type, adjust the balance
        if (transaction instanceof Deposit) {
            this.balance += transaction.getAmount();
        } else if (transaction instanceof Withdrawal withdrawalTransaction) {
            // Explicit cast
            this.balance -= withdrawalTransaction.getAmount();
            // Apply withdrawal fee if any
            this.balance -= withdrawalTransaction.getFee();
        }
    }

    public void changeBalance(double amount) {
        this.balance += amount;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // Return a copy to protect the encapsulation
    }


}
