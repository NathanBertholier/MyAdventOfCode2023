package Day1;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Trebuchet {

    public static List<List<String>> readFilep1(URI uri) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(uri));
        return lines.stream().map(s -> Pattern.compile("\\d").matcher(s).results().map(MatchResult::group).toList()).toList();
    }

    public static List<List<String>> readFilep2(URI uri) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(uri));
        return lines.stream().map(s -> Pattern.compile("(?=(\\d|one|two|three|four|five|six|seven|eight|nine))").matcher(s).results().map(matchResult -> matchResult.group(1)
        ).toList()).toList();
    }

    public static String digitConverter(String s) {
        return switch (s) {
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            default -> s;
        };
    }

    public static List<List<String>> digitListConverter(List<List<String>> lists) {
        return lists.stream().map(strings -> strings.stream().map(Trebuchet::digitConverter).toList()).toList();
    }

    public static int headTailSum(List<List<String>> lists) {
        return lists.stream()
                .map(strings -> strings.get(0) + strings.get(strings.size() - 1))
                .map(Integer::parseInt)
                .reduce(Integer::sum).orElseThrow();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<List<String>> lists = readFilep1(Objects.requireNonNull(ClassLoader.getSystemResource("Day1/input.txt").toURI()));
        System.out.println(headTailSum(lists));
        List<List<String>> lists2 = readFilep2(Objects.requireNonNull(ClassLoader.getSystemResource("Day1/input.txt").toURI()));
        System.out.println(headTailSum(digitListConverter(lists2)));


    }
}
