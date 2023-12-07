package Day4;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Scratchcards {
    public static List<Scrathcard> readFile(URI uri) throws IOException {
        List<String> list = Files.readAllLines(Paths.get(uri));
        List<Scrathcard> list1 = list.stream().map(s -> {
            String[] split = s.split(":");
            int id = Integer.parseInt(Pattern.compile("\\d+").matcher(split[0]).results().findFirst().orElseThrow().group());
            String[] split1 = split[1].split("\\|");
            List<Integer> win = Pattern.compile("\\d+").matcher(split1[0]).results().map(MatchResult::group).map(Integer::parseInt).toList();
            List<Integer> selection = Pattern.compile("\\d+").matcher(split1[1]).results().map(MatchResult::group).map(Integer::parseInt).toList();
            return new Scrathcard(id, win, selection);
        }).toList();
        return list1;
    }

    public static int partOne(List<Scrathcard> scrathcards) {
        Integer result = scrathcards.stream().map(scrathcard -> {
            List<Integer> list = scrathcard.selection.stream().filter(scrathcard.win::contains).toList();
            if (!list.isEmpty()) {
                return 1 << list.size() - 1;
            }
            return 0;
        }).reduce(Integer::sum).orElseThrow();
        return result;
    }

    public static int partTwo(List<Scrathcard> scrathcards) {
        List<List<Scrathcard>> list = scrathcards.stream().map(scrathcard -> {
            List<Scrathcard> scrathcardArrayList = new ArrayList<>();
            scrathcardArrayList.add(scrathcard);
            return scrathcardArrayList;
        }).toList();
        for (int i = 0; i < list.size(); i++) {
            List<Scrathcard> scrathcardList = list.get(i);
            for (int k = 0; k < scrathcardList.size(); k++) {
                Scrathcard scrathcard = scrathcardList.get(k);
                long matching = scrathcard.selection.stream().filter(scrathcard.win::contains).count();
                if (matching > 0) {
                    for (int j = i + 1; j <= matching + i && j < list.size(); j++) {
                        list.get(j).add(list.get(j).get(0));
                    }
                }
            }
        }
        return list.stream().map(List::size).reduce(Integer::sum).orElseThrow();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Scrathcard> scrathcards = readFile(Objects.requireNonNull(ClassLoader.getSystemResource("Day4/input.txt").toURI()));
        System.out.println(scrathcards);
        System.out.println(partOne(scrathcards));
        System.out.println(partTwo(scrathcards));
    }

    record Scrathcard(int id, List<Integer> win, List<Integer> selection) {
        @Override
        public String toString() {
            return "Scrathcard{" +
                    "id=" + id +
                    '}';
        }
    }
}
