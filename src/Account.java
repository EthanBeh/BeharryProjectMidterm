public class Account {
    public enum Type {
        Savings, Checking
    }

    private Type type;
    private double balance;

    public Account(Type type) {
        this.type = type;
    }

    public boolean addMoney(double add) { //make add negative to subtract
        if (balance + round(add) > 0) {
            balance += round(add);
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public static double round(double round) {
        return Math.round(round * 100) / 100.00;
    }
}