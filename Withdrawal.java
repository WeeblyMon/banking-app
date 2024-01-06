package Package;

import java.time.LocalDateTime;

/*
   withdrawal
    -subclass of transaction class
    -bunch of getters and setters for anything related to withdrawals
 */
public class Withdrawal extends Transaction {

    private String withdrawMethod;
    private double withdrawFee;

    public Withdrawal(double amount, LocalDateTime date, String description, String withdrawMethod, double withdrawFee) {
        super(amount, date, description);
        this.withdrawMethod = withdrawMethod;
        this.withdrawFee = withdrawFee;
    }

    public String getWithdrawMethod() {
        return withdrawMethod;
    }

    public void setWithdrawMethod(String withdrawMethod) {
        this.withdrawMethod = withdrawMethod;
    }

    public double getFee() {
        return withdrawFee;
    }

    public void setFee(double withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    @Override
    public String getType() {
        return "WITHDRAWAL";
    }
}
