public class Customer {
    private String name;
    private int pin;
    private Account checking;
    private Account saving;

    public Customer() {
        name = null;
        pin = 0;
        checking = new Account(Account.Type.Checking);
        saving = new Account(Account.Type.Savings);
    }
    public void setName(String newName) {
        name = newName;
    }
    public void setPin(int newPin) {
        pin = newPin;
    }

    public String getName() {
        return name;
    }

    public int getPin() {
        return pin;
    }

    public boolean hasAccts() {
        if (checking != null && saving != null) {
            return true;
        } return false;
    }
    public Account getChecking() {
        return checking;
    }
    public Account getSavings() {
        return saving;
    }

    public boolean transferFunds(double amt, Account.Type from) {
        if (from == Account.Type.Checking) {
            boolean done = checking.addMoney(-amt);
            if (!done) {
                return false;
            }
            saving.addMoney(amt);
            return true;
        } else if (from == Account.Type.Savings) {
            boolean done = saving.addMoney(-amt);
            if (!done) {
                return false;
            }
            checking.addMoney(amt);
            return true;
        } return false;
    }

    public boolean checkPin(int i) {
        if (i == pin) {
            return true;
        }
        return false;
    }
}