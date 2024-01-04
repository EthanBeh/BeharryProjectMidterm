public class Account {
    public enum Type {
        Savings, Checking
    }

    private Type type;
    private double balance;

    public Account(Type type) {
        this.type = type;
    }

    public void addMoney(double add) { //make add negative to subtract
        balance += round(add);
    }

    public static double round(double round) {
        return Math.round(round * 100) / 100.00;
    }
}