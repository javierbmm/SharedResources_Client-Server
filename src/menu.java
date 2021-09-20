import java.util.Scanner;

public class menu {
    public void showMenu () {
        Integer repeat = 0;

        System.out.println("Welcome to Project 0! Please, select the behaviour of your client.");
        System.out.println("1. Read & Update");
        System.out.println("2. Only read");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        if (input.length() > 1) {
            repeat = 1;
            System.out.println("You must only one number!");
        } else {
            //call client-server
        }
    }
}
