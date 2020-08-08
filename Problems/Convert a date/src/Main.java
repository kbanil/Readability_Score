import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String input = scanner.nextLine();
        final String[] split = input.split("-");
        final String yyyy = split[0];
        final String mm = split[1];
        final String dd = split[2];
        System.out.printf("%s/%s/%s", mm,dd,yyyy);     
    }
}
