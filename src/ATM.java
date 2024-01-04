import java.util.Scanner;

public class ATM {
    private Customer c;
    private TransactionHistory h;
    private final Scanner s = new Scanner(System.in);

    public ATM() {
        c = null;
        h = new TransactionHistory();
    }

    public void start() {
        System.out.println("Hello, welcome to the Bank of Beharria ATM\nPlease enter your card");
        //incluye un opcion para espanol :) //y tmb quiza un conlang >:) pa que es de un pais ficcional pues debe haber una lingua ficcional -- lo voy a considerar
        System.out.println("> Enter Card <");
        s.nextLine();

    }

    public void interFace() {
    }

    public void decision() {
        //finish with screen clear
    }
}