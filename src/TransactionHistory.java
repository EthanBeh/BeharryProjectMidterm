public class TransactionHistory {
    private String hist;

    public TransactionHistory() { }

    public void logDeposit(Customer c, double value, Account.Type a) {
        hist += "Customer " + c.getName() + " deposited $" + value + " to their ";
        if (a == Account.Type.Savings) {
            hist += "Savings";
        } else if (a == Account.Type.Checking) {
            hist += "Checking";
        } hist += " account.\n";
    }
    public void logWithdrawal(Customer c, double value, Account.Type a) {
        hist += "Customer " + c.getName() + " withdrew $" + value + " from their ";
        if (a == Account.Type.Savings) {
            hist += "Savings";
        } else if (a == Account.Type.Checking) {
            hist += "Checking";
        } hist += " account.\n";
    }
    public void logTransfer(Customer c, double value, Account.Type from, Account.Type to) {

    }
    public String getHistory() {
        return hist;
    }
}