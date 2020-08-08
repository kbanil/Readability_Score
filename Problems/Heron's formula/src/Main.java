import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);        
        final int a = scanner.nextInt();
        final int b = scanner.nextInt();
        final int c = scanner.nextInt();
        final double p = (a + b + c) / 2.0;
        final double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        System.out.println(area);
    }
}
