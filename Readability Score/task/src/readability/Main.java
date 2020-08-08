package readability;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        final byte[] bytes = Files.readAllBytes(Paths.get(fileName));
        String inputText = new String(bytes);
        final TextAnalysisReport textAnalysisReport = TextAnalyzer.analyze(inputText);
        System.out.println(textAnalysisReport);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        final String inputFunction = scanner.next();
        switch (inputFunction) {
            case "ARI":
                ARIFunction(textAnalysisReport);
                break;
            case "FK":
                FKFunction(textAnalysisReport);
                break;
            case "SMOG":
                SMOGFunction(textAnalysisReport);
                break;
            case "CL":
                CLFunction(textAnalysisReport);
                break;
            case "all":
                final double ariScore = ARIFunction(textAnalysisReport);
                final double fkScore = FKFunction(textAnalysisReport);
                final double smogScore = SMOGFunction(textAnalysisReport);
                final double clScore = CLFunction(textAnalysisReport);
                final double avgScore = (ariScore + fkScore + smogScore + clScore) / 4.0;
                System.out.printf("This text should be understood in average by %.2f year olds.", avgScore);
        }
    }

    private static void printGrade(double score) {
        final int round = (int) Math.round(score);
        final GradeLevel gradeLevel = GradeLevel.from(round);
        System.out.printf("%.2f (about %s year olds).%n", score, gradeLevel.getAgeGroup());
    }

    private static double ARIFunction(TextAnalysisReport report) {
        final double score = 4.71 * report.numberOfCharacters / report.numberOfWords + 0.5 * report.numberOfWords / report.numberOfSentences - 21.43;
        System.out.print("Automated Readability Index: ");
        printGrade(score);
        return score;
    }

    private static double FKFunction(TextAnalysisReport report) {
        final double score = (0.39 * report.numberOfWords / report.numberOfSentences) + (11.8 * report.numberOfSyllables / report.numberOfWords) - 15.59;
        System.out.print("Flesch–Kincaid readability tests: ");
        printGrade(score);
        return score;
    }

    private static double SMOGFunction(TextAnalysisReport report) {
        final double score = 1.043 * Math.sqrt(report.numberOfPolySyllables * 30.0 / report.numberOfSentences) + 3.1291;
        System.out.print("Simple Measure of Gobbledygook: ");
        printGrade(score);
        return score;
    }

    private static double CLFunction(TextAnalysisReport report) {
        double L = report.numberOfCharacters * 100.0 / report.numberOfWords;
        double S = report.numberOfSentences * 100.0 / report.numberOfWords;
        final double score = 0.0588 * L - 0.296 * S - 15.8;
        System.out.print("Coleman–Liau index: ");
        printGrade(score);
        return score;
    }

    private enum GradeLevel {
        KINDERGARTEN(1, "6"),
        FIRST_SECOND_GRADE(2, "7"),
        THIRD_GRADE(3, "9"),
        FOURTH_GRADE(4, "10"),
        FIFTH_GRADE(5, "11"),
        SIXTH_GRADE(6, "12"),
        SEVENTH_GRADE(7, "13"),
        EIGHTH_GRADE(8, "14"),
        NINTH_GRADE(9, "15"),
        TENTH_GRADE(10, "16"),
        ELEVENTH_GRADE(11, "17"),
        TWELFTH_GRADE(12, "18"),
        COLLEGE_STUDENT(13, "24"),
        PROFESSOR(14, "24+");

        private static final Map<Integer, GradeLevel> scoresMap;

        static {
            scoresMap = Arrays.stream(GradeLevel.values()).collect(toUnmodifiableMap(GradeLevel::getScore, Function.identity()));
        }

        private final int score;
        private final String ageGroup;

        GradeLevel(int score, String ageGroup) {
            this.score = score;
            this.ageGroup = ageGroup;
        }

        private static GradeLevel from(int score) {
            return scoresMap.getOrDefault(score, PROFESSOR);
        }

        public int getScore() {
            return score;
        }

        public String getAgeGroup() {
            return ageGroup;
        }
    }

    private static class TextAnalyzer {
        private static final Set<Character> VOWELS = Set.of('a', 'e', 'i', 'o', 'u', 'y');

        public static TextAnalysisReport analyze(String inputText) {
            final String[] sentences = inputText.split("[.!?]");
            final String[] words = inputText.split("\\s");
            int numberOfSentences = sentences.length;
            int numberOfWords = words.length;
            int numberOfCharacters = 0;
            int numberOfSyllables = 0;
            int numberOfPolySyllables = 0;
            for (String word : words) {
                numberOfCharacters += word.length();
                final char[] chars = word.toLowerCase().toCharArray();
                int numberOfVowelsInWord = 0;
                for (int i = 0; i < chars.length; i++) {
                    if (isVowel(chars[i])) {
                        numberOfVowelsInWord++;
                        numberOfSyllables++;
                        if (i != chars.length - 1 && isVowel(chars[i + 1])) {
                            numberOfVowelsInWord--;
                            numberOfSyllables--;
                        }
                    }
                    if (i == chars.length - 1 && chars[i] == 'e') {
                        numberOfVowelsInWord--;
                        numberOfSyllables--;
                    }
                    if (i == chars.length - 1) {
                        if (numberOfVowelsInWord == 0) {
                            numberOfSyllables++;
                        }
                        if (numberOfVowelsInWord > 2) {
                            numberOfPolySyllables++;
                        }
                    }
                }
            }
            return new TextAnalysisReport(inputText, numberOfSentences, numberOfWords, numberOfCharacters, numberOfSyllables, numberOfPolySyllables);
        }

        private static boolean isVowel(char c) {
            return VOWELS.contains(c);
        }

        private static double calculateReadablilityScore(int numberOfSentences, int numberOfWords, int numberOfCharacters) {
            return 4.71 * numberOfCharacters / numberOfWords + 0.5 * numberOfWords / numberOfSentences - 21.43;
        }
    }

    private static class TextAnalysisReport {
        private final String inputText;
        private final int numberOfSentences;
        private final int numberOfWords;
        private final int numberOfCharacters;
        private final int numberOfSyllables;
        private final int numberOfPolySyllables;

        private TextAnalysisReport(String inputText, int numberOfSentences, int numberOfWords, int numberOfCharacters, int numberOfSyllables, int numberOfPolySyllables) {
            this.inputText = inputText;
            this.numberOfSentences = numberOfSentences;
            this.numberOfWords = numberOfWords;
            this.numberOfCharacters = numberOfCharacters;
            this.numberOfSyllables = numberOfSyllables;
            this.numberOfPolySyllables = numberOfPolySyllables;
        }

        @Override
        public String toString() {
            return "The text is: " + "\n" +
                    inputText.replaceAll("\\n", "") + "\n\n" +
                    "Words: " + numberOfWords + "\n" +
                    "Sentences: " + numberOfSentences + "\n" +
                    "Characters: " + numberOfCharacters + "\n" +
                    "Syllables: " + numberOfSyllables + "\n" +
                    "Polysyllables: " + numberOfPolySyllables;
        }
    }
}
