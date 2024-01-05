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
        //incluye un opcion para espanol :) //y tmb quiza un conlang >:) pa que es de un pais ficcional pues debe haber una lingua ficcional -- lo voy a considerar
        System.out.println("If you don't have a BoB card, please press 1");
        String answer = s.nextLine();
        if (answer.equals("1")) {
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
                return 1; //added return type to be able to finish method execution early
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
    }

    public void decision() {
        //finish with screen clear
    }
}