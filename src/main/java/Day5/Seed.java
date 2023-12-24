package Day5;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class Seed {
    private static final String SEEDS_REGEX = "seeds:( \\d+)*";
    private static final String MAP_REGEX = ".*:\\R(\\d+ \\d+ \\d+\\R)*";

    public static Map.Entry<List<Long>, List<Mapper>> readFile(URI uri) throws IOException {
        String file = Files.readString(Paths.get(uri));
        List<Long> seeds = Pattern.compile(SEEDS_REGEX)
                .matcher(file)
                .results()
                .map(matchResult -> matchResult.group()
                        .split(": ")[1]
                        .split(" "))
                .flatMap(strings -> Arrays.stream(strings)
                        .map(Long::parseLong))
                .toList();
        List<Mapper> listStream = Pattern.compile(MAP_REGEX)
                .matcher(file)
                .results().map(matchResult -> matchResult
                        .group()
                        .split("\\R"))
                .map(strings -> Arrays.stream(strings)
                        .skip(1)
                        .map(s -> Arrays.stream(s.split(" "))
                                .map(Long::parseLong)
                                .toList())
                        .map(integers -> new Mapper.RangeMapper(integers.get(0), integers.get(1), integers.get(2)))
                        .toList()).map(Mapper::new)
                .toList();
        return Map.entry(seeds, listStream);
    }

    public static long partOne(List<Long> seeds, List<Mapper> mappers) {
        List<Long> results = new ArrayList<>();
        for (Long seed : seeds) {
            Long newSeed = seed;
            for (Mapper mapper : mappers) {
                newSeed = mapper.map(newSeed);
            }
            results.add(newSeed);
        }
        return results.stream().reduce(Long::min).orElseThrow();
    }

    public static long partTwo(List<Long> seeds, List<Mapper> mappers) {
        List<Long> results = new ArrayList<>();

        for (int i = 0; i < seeds.size(); i += 2) {
            List<Long> newSeeds = new ArrayList<>();
            Long seed = seeds.get(i);
            Long range = seeds.get(i + 1);
            for (long j = seed; j < seed + range; j++) {
                newSeeds.add(j);
            }
            System.out.println("Seeds length : " + newSeeds.size());
            long result = partOne(newSeeds, mappers);
            System.out.println("Result : " + result);
            results.add(result);
        }
        return results.stream().reduce(Long::min).orElseThrow();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Map.Entry<List<Long>, List<Mapper>> entry = readFile(Objects.requireNonNull(ClassLoader.getSystemResource("Day5/input.txt").toURI()));
        List<Long> seeds = entry.getKey();
        List<Mapper> mappers = entry.getValue();
        //System.out.println(partOne(seeds, mappers,new HashMap<>()));
        System.out.println(partTwo(seeds, mappers));


    }
}
