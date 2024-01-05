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
}