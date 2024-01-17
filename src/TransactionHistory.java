public class TransactionHistory {
    private static String hist;

    public TransactionHistory() { }

    public static void logDeposit(Customer c, double value, Account.Type a) {
        hist += "Customer " + c.getName() + " deposited $" + value + " to their ";
        if (a == Account.Type.Savings) {
            hist += "Savings";
        } else if (a == Account.Type.Checking) {
            hist += "Checking";
        } hist += " account.\n";
    }
    public static void logWithdrawal(Customer c, double value, Account.Type a) {
        hist += "Customer " + c.getName() + " withdrew $" + value + " from their ";
        if (a == Account.Type.Savings) {
            hist += "Savings";
        } else if (a == Account.Type.Checking) {
            hist += "Checking";
        } hist += " account.\n";
    }
    public static void logTransfer(Customer c, double value, Account.Type from) {
        hist += "Customer " + c.getName() + "transfered $" + value + " from their ";
        if (from == Account.Type.Savings) {
            hist += "Savings account to their Checking";
        } else if (from == Account.Type.Checking) {
            hist += "Checking account to their Savings";
        } hist += " account.\n";
    }
    public static void logPinChange(Customer c, int oldPin, int newPin) {
        hist += "Customer " + c.getName() + " changed their pin from " + oldPin + " to " + newPin + ".\n";
    }
    public static String getHistory() {
        return hist;
    }
}