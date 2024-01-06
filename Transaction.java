package Package;

import java.time.LocalDateTime;

/*
    Transaction
    -abstract class
    -super class for deposit and withdrawal
    -bunch of getters and setters for anything related to transaction
 */
public abstract class Transaction {
    private double amount;
    private LocalDateTime date;
    private String description;

    //constructor
    public Transaction(double amount, LocalDateTime date, String description) {
        // Fields to store the transaction amount, date, and a description
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // This method is abstract because the type of transaction will be determined by the subclasses.
    public abstract String getType();
}
