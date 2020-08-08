import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String input = scanner.nextLine();
        final String[] split = input.split("");
        int sumFirst =
                Integer.parseInt(split[0]) +
                        Integer.parseInt(split[1]) +
                        Integer.parseInt(split[2]);
        int sumLast =
                Integer.parseInt(split[3]) +
                        Integer.parseInt(split[4]) +
                        Integer.parseInt(split[5]);
        if (sumFirst == sumLast) {
            System.out.println("Lucky");
        } else {
            System.out.println("Regular");
        }
    }
}