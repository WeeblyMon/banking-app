package Package;

import java.time.LocalDateTime;

/*
   deposit
    -subclass of transaction class
    -bunch of getters and setters for anything related to deposits
 */
public class Deposit extends Transaction {

    private String depositSource;

    public Deposit(double amount, LocalDateTime date, String description, String depositSource) {
        super(amount, date, description);
        this.depositSource = depositSource;
    }

    public String getDepositSource() {
        return depositSource;
    }

    public void setDepositSource(String depositSource) {
        this.depositSource = depositSource;
    }

    @Override
    public String getType() {
        return "DEPOSIT";
    }
}
