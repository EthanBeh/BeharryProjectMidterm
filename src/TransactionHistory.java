public class TransactionHistory {
    private String hist;

    public TransactionHistory() { }

    public void logDeposit(Customer c, double value) {
        hist += "Customer " + c.getName() + " deposited $" + value + ".\n";
    }

    public void logWithdrawal(Customer c, double value) {
        hist += "Customer " + c.getName() + " withdrew $" + value + ".\n";
    }

    public String getHistory() {
        return hist;
    }
}