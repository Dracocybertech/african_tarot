import java.util.Scanner;

import game.Game;

public class Main {

    public static void main(String[] arg) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Start of the program.");
        game.start();
        scanner.close();
    }
}
