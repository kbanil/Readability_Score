import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String input = scanner.nextLine();
        final String[] split = input.split("");
        StringBuilder sb = new StringBuilder();
        int count = 0;
        String prev = split[0];
        for (String s :
                split) {
            if (s.equals(prev)) {
                count++;
            } else {
                sb.append(prev).append(count);
                prev = s;
                count = 1;
            }
        }
        sb.append(prev).append(count);
        System.out.println(sb.toString());
    }
}