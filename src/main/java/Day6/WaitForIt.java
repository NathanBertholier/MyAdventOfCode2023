package Day6;

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
import java.util.stream.Collectors;

public class WaitForIt {

    private static final String TIME_REGEX = "Time:( *(\\d+))+";
    private static final String DISTANCE_REGEX = "Distance:( *(\\d+))+";

    public record Race(long time, long distance) {
        public boolean run(long chargeTime) {
            long remainingTime = time - chargeTime;
            return remainingTime * chargeTime > distance;
        }
    }

    public static ArrayList<Race> readFilePartOne(URI uri) throws IOException {
        String file = Files.readString(Paths.get(uri));
        List<Long> times = Pattern.compile(TIME_REGEX)
                                     .matcher(file)
                                     .results()
                                     .flatMap(matchResult -> Pattern.compile("(\\d+)")
                                                                    .matcher(matchResult.group())
                                                                    .results()
                                                                    .map(matchResult1 -> Long.parseLong(matchResult1.group())))
                                     .toList();
        List<Long> distances = Pattern.compile(DISTANCE_REGEX)
                                         .matcher(file)
                                         .results()
                                         .flatMap(matchResult -> Pattern.compile("(\\d+)")
                                                                        .matcher(matchResult.group())
                                                                        .results()
                                                                        .map(matchResult1 -> Long.parseLong(matchResult1.group())))
                                         .toList();
        ArrayList<Race> races = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), distances.get(i)));
        }
        return races;
    }

    public static Race readFilePartTwo(URI uri) throws IOException {
        String file = Files.readString(Paths.get(uri));
        long time = Long.parseLong(Pattern.compile(TIME_REGEX)
                                           .matcher(file)
                                           .results()
                                           .map(matchResult -> Pattern.compile("(\\d+)")
                                                                      .matcher(matchResult.group())
                                                                      .results()
                                                                      .map(MatchResult::group)
                                                                      .collect(Collectors.joining()))
                                           .findFirst()
                                           .orElseThrow());

        long distance = Long.parseLong(Pattern.compile(DISTANCE_REGEX)
                                               .matcher(file)
                                               .results()
                                               .map(matchResult -> Pattern.compile("(\\d+)")
                                                                          .matcher(matchResult.group())
                                                                          .results()
                                                                          .map(MatchResult::group)
                                                                          .collect(Collectors.joining()))
                                               .findFirst()
                                               .orElseThrow());
        return new Race(time, distance);
    }

    public static long partOne(List<Race> races) {
        long result = 1;
        for (Race race : races) {
            long wins = 0;
            for (long i = 0; i <= race.time; i++) {
                if (race.run(i)) {
                    wins++;
                }
            }
            result *= wins;
        }
        return result;
    }

    public static long partTwo(Race race) {
        long wins = 0;
        for (long i = 0; i <= race.time; i++) {
            if (race.run(i)) {
                wins++;
            }
        }
        return wins;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        ArrayList<Race> races = readFilePartOne(Objects.requireNonNull(ClassLoader.getSystemResource("Day6/input.txt")
                                                                                  .toURI()));
        System.out.println(partOne(races));
        Race race = readFilePartTwo(Objects.requireNonNull(ClassLoader.getSystemResource("Day6/input.txt")
                                                                      .toURI()));
        System.out.println(partTwo(race));
    }
}
