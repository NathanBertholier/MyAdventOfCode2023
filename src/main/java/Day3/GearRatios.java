package Day3;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class GearRatios {
    public static String readFile(URI uri) throws IOException {
        return Files.readString(Paths.get(uri));
    }

    public static Number isolateNumber(String s, int position) {
        StringBuilder result = new StringBuilder();
        int start = position;
        int end = position;
        for (int i = position; i >= 0 && Character.isDigit(s.charAt(i)); i--) {
            result.insert(0, s.charAt(i));
            start = i;
        }
        for (int i = position + 1; i < s.length() && Character.isDigit(s.charAt(i)); i++) {
            result.append(s.charAt(i));
            end = i;
        }
        return new Number(Integer.parseInt(String.valueOf(result)), start, end);
    }

    public static Set<Number> asNeighbor(String[] lines, int line, int position) {
        Set<Number> numbers = new HashSet<>();
        for (int j = line - 1; j <= line + 1; j++) {
            if (j < 0 || j > lines.length) {
                continue;
            }
            for (int i = position - 1; i <= position + 1; i++) {
                if (i < 0 || i >= lines[j].length()) {
                    continue;
                }
                if (Character.isDigit(lines[j].charAt(i))) {
                    numbers.add(isolateNumber(lines[j], i));
                }
            }
        }
        return numbers;
    }

    public static int partOne(String[] lines) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (c != '.' && !Character.isDigit(c)) {
                    integers.addAll(asNeighbor(lines, j, i).stream().map(number -> number.value).toList());
                }
            }
        }
        return integers.stream().reduce(Integer::sum).orElseThrow();
    }

    public static int partTwo(String[] lines) {
        ArrayList<Integer> integers = new ArrayList<>();
        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (c != '.' && !Character.isDigit(c)) {
                    List<Integer> list = asNeighbor(lines, j, i).stream().map(number -> number.value).toList();
                    if (list.size()==2){
                        integers.add(list.stream().reduce((integer, integer2) -> integer*integer2).orElseThrow());
                    }

                }
            }
        }
        return integers.stream().reduce(Integer::sum).orElseThrow();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        String file = readFile(Objects.requireNonNull(ClassLoader.getSystemResource("Day3/input.txt").toURI()));
        Integer total = Pattern.compile("\\d+").matcher(file).results().map(MatchResult::group).map(Integer::parseInt).reduce(Integer::sum).orElseThrow();
        String[] lines = file.split("\n");
        System.out.println(partOne(lines));
        System.out.println(partTwo(lines));
    }

    record Number(int value, int start, int end) {
    }
}
