import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String str;
        while(true) {
            System.out.print("SHELL > ");
            Scanner sc = new Scanner(System.in);
            str = sc.next();

            if (str.equals("exit")) {
                System.out.println("BYE");
                break;
            }
        }

    }
}