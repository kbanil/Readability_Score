import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        final Scanner scanner = new Scanner(System.in);
        final int u1 = scanner.nextInt();
        final int u2 = scanner.nextInt();
        final int v1 = scanner.nextInt();
        final int v2 = scanner.nextInt();

        final int uv = v1 * u1 + v2 * u2;

        final double lengthOfu = Math.sqrt(u1 * u1 + u2 * u2);
        final double lengthOfv = Math.sqrt(v1 * v1 + v2 * v2);

        final double angleInRadians = Math.acos(uv / (lengthOfu * lengthOfv));
        final double angleInDegrees = Math.toDegrees(angleInRadians);
        System.out.println(angleInDegrees);
    }
}