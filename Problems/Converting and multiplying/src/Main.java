import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        boolean shouldContinue = true;
        StringBuilder sb = new StringBuilder();
        while (shouldContinue) {
            final String input = scanner.nextLine();
            try {
                final int number = Integer.parseInt(input);
                if (number == 0) {
                    shouldContinue = false;
                    break;
                }
                sb.append(number * 10).append("\n");
            } catch (NumberFormatException e) {
                sb.append("Invalid user input: ").append(input).append("\n");
            }
        }
        System.out.println(sb.toString());
    }
}