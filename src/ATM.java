import java.sql.SQLOutput;
import java.util.Scanner;

public class ATM {
    private Customer c;
    private TransactionHistory h;
    private final Scanner s = new Scanner(System.in);
    private static boolean doInterface = true;

    public ATM() {
        c = null;
        h = new TransactionHistory();
    }

    public void start() {
        System.out.println("Hello, welcome to the Bank of Beharria ATM\nPlease enter your card");
        System.out.println("If you don't have a BoB card, please press 1");
        String answer = s.nextLine();
        while (answer.equals("1") && c != null) { //making it so that they can't recreate an account <:)
            System.out.println("You already have an account with us!");
            answer = s.nextLine();
        }
        if (answer.equals("1")) { //needed for line 40 to work properly
            System.out.print("\nWould you like to create an account with us? (y/n): ");
            answer = s.nextLine();
            while (!answer.equals("n") && !answer.equals("no") && !answer.equals("y") && !answer.equals("yes")) {
                System.out.println("That's not a valid option, please try again: ");
                answer = s.nextLine();
            }
            if (answer.equals("y") || answer.equals("yes")) {
                c = new Customer();
                System.out.print("Please enter the name for your account: ");
                c.setName(s.nextLine());
                System.out.print("Please enter the PIN you would like for your account: ");
                c.setPin(tryForInt());
                System.out.println("\nResetting...\n");
            } else if (answer.equals("n") || answer.equals("no")) {
                System.out.println("Thank you! Goodbye!");
                doInterface = false;
                return;
            }
            start();
        }
        if (c != null && c.getPin() != 1) {
            if (doInterface) {
                System.out.print("Please enter your pin: (if you would like to cancel this interaction, please press 1) ");
                int ans = tryForInt();
                while (ans != c.getPin() && ans != 1) {
                    System.out.print("That was incorrect, please try again: ");
                }
                if (ans == 1) {
                    doInterface = false;
                    return;
                }
            }
        } else if (c != null && c.getPin() == 1) {
            if (doInterface) {
                System.out.print("Please enter your pin: (if you would like to cancel this interaction, please press 0) ");
                int ans = tryForInt();
                while (ans != 1 && ans != 0) {
                    System.out.print("That was incorrect, please try again: ");
                }
                if (ans == 0) {
                    doInterface = false;
                    return;
                }
            }
        } else {
            System.out.println("You don't have an account with us! Please try again.");
            try {
                Thread.sleep(1500);
            } catch (Exception e) { }
            System.out.println();
            start();
        }
        if (doInterface) {
            System.out.println();
            doInterface = false;
            interFace();
        }
    }

    public void interFace() {
        System.out.println("What would you like to do today?");
        System.out.println("1. Withdraw money\n2. Deposit money\n3. Transfer money between account\n4. Get account balances\n5. Get transaction history\n6. Change PIN\n7. Exit");
        int answer = tryForInt();
        System.out.println();
        while (answer != 7) {
            if (answer <= 0 || answer >= 7) {
                System.out.print("That's not a valid option, please try again ");
                answer = tryForInt();
            } else {
                System.out.print("Please enter your pin: ");
                if (c.checkPin(tryForInt())) {
                    if (answer == 1) {
                        System.out.print("Would you like to withdraw from your (c)hecking or (s)avings account?: ");
                        String ans = s.nextLine();
                        while (!ans.toLowerCase().equals("c") && !ans.toLowerCase().equals("s") && !ans.toLowerCase().equals("checking") && !ans.toLowerCase().equals("savings")) {
                            System.out.print("Please input a valid choice: ");
                            ans = s.nextLine();
                        }
                        System.out.print("How much would you like to withdraw?: ");
                        int money = tryForInt();
                        boolean loop =  true;
                        while (money % 5 != 0 && loop) {
                            System.out.println("That's not a valid amount, we are only able to give out bills of $5 or $20 value, sorry for the inconvenience");
                            System.out.print("Please try again: ");
                            System.out.println("(To cancel this interaction, please enter \"1\")");
                            money = tryForInt();
                            if (money == 1) {
                                loop = false;
                            }
                        }
                        if (ans.toLowerCase().equals("c")) {
                            if (c.getChecking().addMoney(-money)) {
                                System.out.println(money / 20 + " $20 dollar bills and " + (money % 20) / 5 + " $5 bills successfully withdrawn from Checking account");
                                System.out.println(TransactionHistory.logWithdrawal(c, money, Account.Type.Checking));
                            } else {
                                System.out.println("Failed to withdraw money");
                            }
                        } else if (ans.toLowerCase().equals("s")) {
                            if (c.getSavings().addMoney(-money)) {
                                System.out.println(money / 20 + " $20 dollar bills and " + (money % 20) / 5 + " $5 bills successfully withdrawn from Savings account");
                                System.out.println(TransactionHistory.logWithdrawal(c, money, Account.Type.Savings));
                            } else {
                                System.out.println("Failed to withdraw money");
                            }
                        }
                        System.out.println();
                    } else if (answer == 2) {
                        System.out.print("Would you like to deposit to your (c)hecking or (s)avings account?: ");
                        String ans = s.nextLine();
                        while (!ans.toLowerCase().equals("c") && !ans.toLowerCase().equals("s") && !ans.toLowerCase().equals("checking") && !ans.toLowerCase().equals("savings")) {
                            System.out.print("Please input a valid choice: ");
                            ans = s.nextLine();
                        }
                        System.out.print("How much would you like to deposit?: ");
                        double money = tryForDouble();
                        if (ans.toLowerCase().equals("c")) {
                            if (c.getChecking().addMoney(money)) {
                                System.out.println("Money deposited successfully!");
                                System.out.println(TransactionHistory.logDeposit(c, money, Account.Type.Checking));
                            } else {
                                System.out.println("Failed to deposit money");
                            }
                        } else if (ans.toLowerCase().equals("s")) {
                            if (c.getSavings().addMoney(money)) {
                                System.out.println("Money deposited successfully!");
                                System.out.println(TransactionHistory.logDeposit(c, money, Account.Type.Savings));
                            } else {
                                System.out.println("Failed to deposit money");
                            }
                        }
                    } else if (answer == 3) {
                        System.out.print("From which account would you like to transfer money? (c/s) ");
                        String transfer = s.nextLine();
                        while (!transfer.equalsIgnoreCase("savings") && !transfer.equalsIgnoreCase("checking") && !transfer.equalsIgnoreCase("s") && !transfer.equalsIgnoreCase("c")) {
                            System.out.println("That's not a valid account, you have only a Savings and Checking account");
                            transfer = s.nextLine();
                        }
                        Account.Type from = null;
                        if (transfer.equalsIgnoreCase("savings") || transfer.equalsIgnoreCase("s")) {
                            from = Account.Type.Savings;
                            System.out.print("To which account would you like to transfer money? ");
                            transfer = s.nextLine();
                            while (!transfer.equalsIgnoreCase("checking") || transfer.equalsIgnoreCase("c")) {
                                System.out.println("That's not a valid account, you have only a Checking account to transfer to");
                                transfer = s.nextLine();
                            }
                            Account.Type to = Account.Type.Checking;
                        } else if (transfer.equalsIgnoreCase("checking") || transfer.equalsIgnoreCase("c")) {
                            from = Account.Type.Checking;
                            System.out.print("To which account would you like to transfer money?");
                            transfer = s.nextLine();
                            while (!transfer.equalsIgnoreCase("savings") || transfer.equalsIgnoreCase("s")) {
                                System.out.println("That's not a valid account, you have only a Savings account to transfer to");
                                transfer = s.nextLine();
                            }
                            Account.Type to = Account.Type.Savings;
                        }
                        System.out.print("And how much would you like to deposit?: ");
                        double amt = tryForDouble();
                        boolean success = c.transferFunds(amt, from);
                        if (success) {
                            System.out.println(TransactionHistory.logTransfer(c, amt, from));
                        } else {
                            System.out.println("Unable to transfer funds");
                        }
                    } else if (answer == 4) {
                        System.out.println("Savings account balance: " + Account.round(c.getSavings().getBalance()) + "\nChecking account balance: " + Account.round(c.getChecking().getBalance()));
                        System.out.println(TransactionHistory.getStatus(c));
                    } else if (answer == 5) {
                        System.out.println(h.getHistory(c));
                    } else if (answer == 6) {
                        System.out.print("Before changing your pin, please enter your current pin: ");
                        int pin = tryForInt();
                        boolean r = false; //just a way for them to choose that they don't actually want to change their pin
                        while (pin != c.getPin() && !r) {
                            System.out.println("That's incorrect, please try again\n(if you've changed your mind about changing your pin, please enter any negative number)");
                            pin = tryForInt();
                            if (pin < 0) {
                                r = true;
                            }
                        }
                        int oldPin = pin;
                        if (r) {
                            continue;
                        }
                        System.out.print("Please enter your new pin ");
                        int newPin = tryForInt();
                        while (newPin == c.getPin() && !r) {
                            System.out.println("Your old pin can't be the same as your new pin!\n(If you've changed your mind about changing your pin, please enter any negative number)");
                            newPin = tryForInt();
                            if (newPin < 0) {
                                r = true;
                            }
                        }
                        if (r) {
                            continue;
                        }
                        System.out.print("Please reënter that pin ");
                        int checkPin = tryForInt();
                        while (checkPin != newPin && !r) {
                            System.out.println("Those pins aren't the same, please try again\n(If you've changed your mind about changing your pin, please enter any negative number)");
                            checkPin = tryForInt();
                            if (checkPin < 0) {
                                r = true;
                            }
                        }
                        c.setPin(newPin);
                        System.out.println("Pin changed successfully to " + newPin + "!");
                        System.out.println(TransactionHistory.logPinChange(c, oldPin, newPin));
                    }
                    System.out.println("What would you like to do next?\n1. Withdraw money\n2. Deposit money\n3. Transfer money between account\n4. Get account balances\n5. Get transaction history\n6. Change PIN\n7. Exit\n");
                    answer = tryForInt();
                } else {
                    boolean pinCorrect = false;
                    while (!pinCorrect) {
                        System.out.println("That pin is incorrect, please try again: ");
                        pinCorrect = c.checkPin(tryForInt());
                    }
                }
            }
        }
        System.out.println("\nThank you, goodbye!");
    }

    public static double tryForDouble() {
        Scanner s = new Scanner(System.in);
        boolean r = true;
        while (r) {
            try {
                double amt = Double.parseDouble(s.nextLine());
                return amt;
            } catch (Exception e) {
                System.out.print("That's an invalid number, please try again: ");
            }
        }
        return 0;
    }

    public static int tryForInt() {
        Scanner s = new Scanner(System.in);
        boolean r = true;
        while (r) {
            try {
                int amt = Integer.parseInt(s.nextLine());
                return amt;
            } catch (Exception e) {
                System.out.print("That's an invalid number, please try again: ");
            }
        }
        return 0;
    }
}