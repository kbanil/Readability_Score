import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* Please, do not rename it */
class Problem {

    public static void main(String[] args) {
        String operator = args[0];
        final String[] numbers = Arrays.copyOfRange(args, 1, args.length);
        final IntStream intStream = Stream.of(numbers).mapToInt(Integer::parseInt);
        if ("MAX".equals(operator)) {
            System.out.println(intStream.max().getAsInt());
        } else if ("MIN".equals(operator)) {
            System.out.println(intStream.min().getAsInt());
        } else if ("SUM".equals(operator)) {
            System.out.println(intStream.sum());
        }
    }
}