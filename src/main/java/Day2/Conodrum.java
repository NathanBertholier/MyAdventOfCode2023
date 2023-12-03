package Day2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Conodrum {

    public static Map<Integer, List<Map<String, Integer>>> readFile(URI uri) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(uri));
        return lines.stream().map(s -> {
            String[] game = s.split(":");
            int id = Integer.parseInt(game[0].split(" ")[1]);
            return Map.entry(id, roundToList(game[1]));
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
    }

    public static Map.Entry<String, Integer> colorAmountToMap(String s) {
        MatchResult matcher = Pattern.compile("(\\d+) (\\w+)").matcher(s).results().findFirst().orElseThrow();
        return Map.entry(matcher.group(2), Integer.parseInt(matcher.group(1)));
    }

    public static Map<String, Integer> splitColor(String s) {
        String[] split = s.split(",");
        return Arrays.stream(split).map(Conodrum::colorAmountToMap).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
    }


    public static List<Map<String, Integer>> roundToList(String s) {
        String[] split = s.split(";");
        return Arrays.stream(split).map(Conodrum::splitColor).toList();
    }

    public static int partOne(Map<Integer, List<Map<String, Integer>>> entries) {
        return entries.entrySet().stream()
                .filter(integerListEntry -> integerListEntry.getValue().stream()
                        .allMatch(stringIntegerMap -> stringIntegerMap.computeIfAbsent("red", s -> 0) <= 12 && stringIntegerMap.computeIfAbsent("green", s -> 0) <= 13 && stringIntegerMap.computeIfAbsent("blue", s -> 0) <= 14))
                .map(Map.Entry::getKey).reduce(Integer::sum).orElseThrow();
    }

    public static int partTwo(Map<Integer, List<Map<String, Integer>>> entries) {
        List<Map<String, Integer>> maps = entries.get(3);
        return entries.values().stream().map(list -> {
            Integer red = list.stream().map(stringIntegerMap -> stringIntegerMap.computeIfAbsent("red", s -> 1)).max(Integer::compare).orElseThrow();
            Integer green = list.stream().map(stringIntegerMap -> stringIntegerMap.computeIfAbsent("green", s -> 1)).max(Integer::compare).orElseThrow();
            Integer blue = list.stream().map(stringIntegerMap -> stringIntegerMap.computeIfAbsent("blue", s -> 1)).max(Integer::compare).orElseThrow();
            return red * green * blue;
        }).reduce(Integer::sum).orElseThrow();
    }


    public static void main(String[] args) throws URISyntaxException, IOException {
        Map<Integer, List<Map<String, Integer>>> entries = readFile(Objects.requireNonNull(ClassLoader.getSystemResource("Day2/input.txt").toURI()));
        System.out.println(partOne(entries));
        System.out.println(partTwo(entries));
    }
}
