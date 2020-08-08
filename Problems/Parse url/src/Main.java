import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String input = scanner.nextLine();
        final String[] urlAndParams = input.split("\\?");
        final String[] params = urlAndParams[1].split("&");
        String password = "";
        for (String param :
                params) {
            final String[] nameValue = param.split("=");
            final String name = nameValue[0];
            final String value;
            if (nameValue.length == 1) {
                value = "not found";
            } else {
                value = nameValue[1];
            }
            if ("pass".equals(name)) {
                password = value;
            }
            System.out.printf("%s : %s%n", name, value);
        }
        if (!"".equals(password)) {
            System.out.printf("%s : %s%n", "password", password);

        }
    }
}