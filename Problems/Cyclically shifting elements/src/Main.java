import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        final int size = scanner.nextInt();
        int[] a = new int[size];
        for (int i = 1; i <= size; i++) {
            final int number = scanner.nextInt();
            a[index(i, size)] = number;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(a[i]).append(" ");
        }
        System.out.println(sb.toString());
    }

    private static int index(final int i, final int size) {
        return i % size;
    }
}