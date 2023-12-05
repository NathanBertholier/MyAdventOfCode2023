package Day3;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class GearRatios {

    public static String readFile(URI uri) throws IOException {
        return Files.readString(Paths.get(uri));
    }

    public static boolean asNeighbor(String[] lines, int line, int position) {
        for (int j = line - 1; j <= line + 1; j++) {
            if (j < 0 || j > lines.length) {
                continue;
            }
            for (int i = position - 1; i <= position + 1; i++) {
                char c = '.';
                try {
                    c = lines[j].charAt(i);
                } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException ignored) {
                    continue;
                }
                if (!(c == '.' || Character.isDigit(c))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int partOne(String[] lines) {
        int totalcount = 0;
        for (int j = 0; j < lines.length; j++) {
            String line = lines[j];
            int finalJ = j;
            int count = Pattern.compile("\\d+").matcher(line).results().filter(matchResult -> {
                for (int i = matchResult.start(); i < matchResult.end(); i++) {
                    if (asNeighbor(lines, finalJ, i)) {
                        return true;
                    }
                }
                return false;
            }).map(MatchResult::group).map(Integer::parseInt).reduce(Integer::sum).orElse(0);
            totalcount += count;
        }
        return totalcount;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        String file = readFile(Objects.requireNonNull(ClassLoader.getSystemResource("Day3/input.txt").toURI()));
        Integer total = Pattern.compile("\\d+").matcher(file).results().map(MatchResult::group).map(Integer::parseInt).reduce(Integer::sum).orElseThrow();
        System.out.println("Total : " + total);
        String[] lines = file.split("\n");
        System.out.println(partOne(lines));
    }
}
