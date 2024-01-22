public class TransactionHistory {
    private static String hist = "";
    private static int sec = 0;
    private static int acc = 0;

    public TransactionHistory() { }

    public static String logDeposit(Customer c, double value, Account.Type a) {
        acc++;
        hist += "Customer " + c.getName() + " deposited $" + value + " to their ";
        if (a == Account.Type.Savings) {
            hist += "Savings";
        } else if (a == Account.Type.Checking) {
            hist += "Checking";
        } hist += " account. A" + printNum(acc) + "\n";
        if (a == Account.Type.Savings) {
            return "" + value + " deposited to Savings account A" + printNum(acc);
        } else if (a == Account.Type.Checking) {
            return "" + value + " despoited to Checking account A" + printNum(acc);
        } return "";
    }
    public static String logWithdrawal(Customer c, double value, Account.Type a) {
        acc++;
        hist += "Customer " + c.getName() + " withdrew $" + value + " from their ";
        if (a == Account.Type.Savings) {
            hist += "Savings";
        } else if (a == Account.Type.Checking) {
            hist += "Checking";
        } hist += " account. A" + printNum(acc) + "\n";
        if (a == Account.Type.Savings) {
            return "" + value + " withdrawn from Savings account A" + printNum(acc);
        } else if (a == Account.Type.Checking) {
            return "" + value + " withdrawn from Checking account A" + printNum(acc);
        } return "";
    }
    public static String logTransfer(Customer c, double value, Account.Type from) {
        acc++;
        hist += "Customer " + c.getName() + " transfered $" + value + " from their ";
        if (from == Account.Type.Savings) {
            hist += "Savings account to their Checking";
        } else if (from == Account.Type.Checking) {
            hist += "Checking account to their Savings";
        } hist += " account. A" + printNum(acc) + "\n";
        if (from == Account.Type.Savings) {
            return "" + value + " transferred from Savings account to Checking account A" + printNum(acc);
        } else if (from == Account.Type.Checking) {
            return "" + value + " transferred from Checking account to Savings account A" + printNum(acc);
        } return "";
    }
    public static String logPinChange(Customer c, int oldPin, int newPin) {
        sec++;
        hist += "Customer " + c.getName() + " changed their pin from " + oldPin + " to " + newPin + ". S" + printNum(sec) + "\n";
        return "Pin changed from " + oldPin + " to " + newPin + " S" + printNum(sec);
    }
    public static String getHistory(Customer c) {
        sec++;
        hist += "Customer " + c.getName() + " accessed their account history. S" + printNum(sec) + "\n";
        return hist;
    }
    public static String getStatus(Customer c) {
        sec++;
        hist += "Customer " + c.getName() + " accessed their account balances. S" + printNum(sec) + "\n";
        return "Account statuses checked S" + printNum(sec);
    }

    private static String printNum(int num) {
        if (num < 10) {
            return "000" + num;
        } else if (num < 100) {
            return "00" + num;
        } else if (num < 1000) {
            return "0" + num;
        } return "" + num;
    }
}