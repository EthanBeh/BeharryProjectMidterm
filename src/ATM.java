import java.sql.SQLOutput;
import java.util.Scanner;

public class ATM {
    private Customer c;
    private TransactionHistory h;
    private final Scanner s = new Scanner(System.in);

    public ATM() {
        c = null;
        h = new TransactionHistory();
    }

    public int start() {
        System.out.println("Hello, welcome to the Bank of Beharria ATM\nPlease enter your card");
        //incluye un opcion para espanol :) //y tmb quiza un conlang >:) pq si es de un pais ficcional debe haber una lingua ficcional q no
        System.out.println("If you don't have a BoB card, please press 1");
        String answer = s.nextLine();
        while (answer.equals("1")) {
            System.out.println("That is now a valid BoB card, please try again or press 1 to create an account;");
            answer = s.nextLine();
        }
        if (answer.equals("1")) { //needed for line 40 to work properly
            System.out.print("Would you like to create an account with us? (y/n): ");
            answer = s.nextLine();
            while (!answer.equals("n") && !answer.equals("no") && !answer.equals("y") && !answer.equals("yes")) {
                System.out.println("That's not a valid option, please try again: ");
                answer = s.nextLine();
            }
            if (answer.equals("y") || answer.equals("yes")) {
                c = new Customer();
                System.out.print("Please enter the name for your account: ");
                c.setName(s.nextLine());
                System.out.print("Please enter the PIN you would like for your account");
                c.setPin(s.nextInt());
            } else if (answer.equals("n") || answer.equals("no")) {
                System.out.println("Thank you! Goodbye!");
                return 1; //added return to be able to finish method execution early
            }
            start();
        }
        if (c != null && c.getPin() != 1) {
            System.out.print("Please enter your pin: (if you would like to cancel this interaction, please press 1) ");
            int ans = s.nextInt();
            while (ans != c.getPin() && ans != 1) {
                System.out.print("That was incorrect, please try again: ");
            }
            if (ans == 1) {
                return 2;
            }
        } else if (c != null && c.getPin() == 1) {
            System.out.print("Please enter your pin: (if you would like to cancel this interaction, please press 0) ");
            int ans = s.nextInt();
            while (ans != 1 && ans != 0) {
                System.out.print("That was incorrect, please try again: ");
            }
            if (ans == 0) {
                return 2;
            }
        } else {
            System.out.println("You don't have an account with us! Please try again.");
            try {
                Thread.sleep(1500);
            } catch (Exception e) { }
            System.out.println();
            start();
        }
        interFace();
        return 0;
    }

    public void interFace() {
        System.out.println("What would you like to do today?");
        System.out.println("1. Withdraw money\n2. Deposit money\n3. Transfer money between account\n4. Get account balances\n5. Get transaction history\n6. Change PIN\n7. Exit");
        int answer = tryForInt();
        while (answer != 7) {
            if (answer == 1) {
                System.out.print("Would you like to withdraw from your (c)hecking or (s)avings account?: ");
                String ans = s.nextLine();
                while (!ans.toLowerCase().equals("c") && !ans.toLowerCase().equals("s") && !ans.toLowerCase().equals("checking") && !ans.toLowerCase().equals("savings")) {
                    System.out.print("Please input a valid choice: ");
                    ans = s.nextLine();
                }
                System.out.print("How much would you like to withdraw?: ");
                int money = tryForInt();
                if (money % 5 != 0) {
                    System.out.println("That's not a valid amount, we are only able to give out bills of $5 or $20 value, sorry for the inconvenience");
                    System.out.print("Please try again: ");
                    money = Integer.parseInt(s.nextLine());
                }
                if (ans.toLowerCase().equals("c")) {
                    if (c.getChecking().addMoney(-money)) {
                        System.out.println(money / 20 + " $20 dollar bills and " + (money % 20) / 5 + "$5 bills successfully withdrawn from Checking account");
                    } else {
                        System.out.println("Failed to withdraw money");
                    }
                } else if (ans.toLowerCase().equals("s")) {
                    if (c.getSavings().addMoney(-money)) {
                        System.out.println(money / 20 + " $20 dollar bills and " + (money % 20) / 5 + "$5 bills successfully withdrawn from Savings account");
                    } else {
                        System.out.println("Failed to withdraw money");
                    }
                }
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
                    } else {
                        System.out.println("Failed to deposit money");
                    }
                } else if (ans.toLowerCase().equals("s")) {
                    if (c.getSavings().addMoney(money)) {
                        System.out.println("Money deposited successfully!");
                    } else {
                        System.out.println("Failed to deposit money");
                    }
                }
            } else if (answer == 3) {
                System.out.print("From which account would you like to transfer money?");
                String transfer = s.nextLine();
                while (!transfer.equalsIgnoreCase("savings") && !transfer.equalsIgnoreCase("checking")) {
                    System.out.println("That's not a valid account, you have only a Savings and Checking account");
                }
                Account.Type from = null;
                if (transfer.equalsIgnoreCase("savings")) {
                    from = Account.Type.Savings;
                    System.out.print("To which account would you like to transfer money?");
                    transfer = s.nextLine();
                    while (!transfer.equalsIgnoreCase("checking")) {
                        System.out.println("That's not a valid account, you have only a Checking account to transfer to");
                    }
                    Account.Type to = Account.Type.Checking;
                } else if (transfer.equalsIgnoreCase("checking")) {
                    from = Account.Type.Checking;
                    System.out.print("To which account would you like to transfer money?");
                    transfer = s.nextLine();
                    while (!transfer.equalsIgnoreCase("savings")) {
                        System.out.println("That's not a valid account, you have only a Savings account to transfer to");
                    }
                    Account.Type to = Account.Type.Savings;
                }
                System.out.print("And how much would you like to deposit?: ");
                double amt = tryForDouble();
                boolean success = c.transferFunds(amt, from);
                if (success) {
                    System.out.println("Funds deposited successfully!");
                } else {
                    System.out.println("Unable to deposit funds");
                }
            } else if (answer == 4) {
                System.out.println("Savings account balance: " + Account.round(c.getSavings().getBalance()) + "\nChecking account balance: " + Account.round(c.getChecking().getBalance()));
            } else if (answer == 5) {

            }
        }
    }

    public void decision() {
        //finish with screen clear
    }

    public static double tryForDouble() {
        Scanner s = new Scanner(System.in);
        boolean r = true;
        while (r) {
            try {
                double amt = Double.parseDouble(s.nextLine());
                return amt;
            } catch (Exception e) {
                System.out.print("That's an invalid amount, please try again");
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
                System.out.print("That's an invalid amount, please try again");
            }
        }
        return 0;
    }
}