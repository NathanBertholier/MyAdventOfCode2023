package Day7;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.groupingBy;

public class CamelCards {
    public static Map<Character, Integer> VALUE_MAP = initValueMap();

    // Five of a kind -> 7
    // Four of a kind -> 6
    // Full house -> 5
    // Three of a kind -> 4
    // Two pair -> 3
    // One pair -> 2
    // High card -> 1
    // Other -> 0
    public static boolean fiveOfAKind(Hand hand) {
        Map<Character, List<Character>> collect = hand.cards.stream()
                                                            .collect(groupingBy(Character::charValue));
        return collect.size() == 1;
    }

    public static boolean fourOfAKind(Hand hand) {
        Map<Character, List<Character>> collect = hand.cards.stream()
                                                            .collect(groupingBy(Character::charValue));
        if (collect.size() != 2) {
            return false;
        }
        return collect.values()
                      .stream()
                      .anyMatch(characters -> characters.size() == 4);
    }

    public static boolean fullHouse(Hand hand) {
        Map<Character, List<Character>> collect = hand.cards.stream()
                                                            .collect(groupingBy(Character::charValue));
        if (collect.size() != 2) {
            return false;
        }
        return collect.values()
                      .stream()
                      .anyMatch(characters -> characters.size() == 3);
    }

    public static boolean threeOfAKind(Hand hand) {
        Map<Character, List<Character>> collect = hand.cards.stream()
                                                            .collect(groupingBy(Character::charValue));
        if (collect.size() != 3) {
            return false;
        }
        return collect.values()
                      .stream()
                      .anyMatch(characters -> characters.size() == 3);
    }

    public static boolean twoPair(Hand hand) {
        Map<Character, List<Character>> collect = hand.cards.stream()
                                                            .collect(groupingBy(Character::charValue));
        if (collect.size() != 3) {
            return false;
        }
        return collect.values()
                      .stream()
                      .anyMatch(characters -> characters.size() == 2);
    }

    public static boolean onePair(Hand hand) {
        Map<Character, List<Character>> collect = hand.cards.stream()
                                                            .collect(groupingBy(Character::charValue));
        if (collect.size() != 4) {
            return false;
        }
        return collect.values()
                      .stream()
                      .anyMatch(characters -> characters.size() == 2);
    }

    public static boolean highCard(Hand hand) {
        Map<Character, List<Character>> collect = hand.cards.stream()
                                                            .collect(groupingBy(Character::charValue));
        return collect.size() == 5;
    }


    public record Hand(List<Character> cards, int id) {
        public boolean win(Hand otherHand) {
            int handType = this.getType();
            int otherHandType = otherHand.getType();
            if (handType > otherHandType) {
                return true;
            } else if (handType < otherHandType) {
                return false;
            } else {
                return this.isGreaterThan(otherHand);
            }
        }

        private int getType() {
            if (fiveOfAKind(this)) {
                return 7;
            } else if (fourOfAKind(this)) {
                return 6;
            } else if (fullHouse(this)) {
                return 5;
            } else if (threeOfAKind(this)) {
                return 4;
            } else if (twoPair(this)) {
                return 3;
            } else if (onePair(this)) {
                return 2;
            } else if (highCard(this)) {
                return 1;
            }
            return 0;
        }

        private boolean isGreaterThan(Hand otherHand) {
            for (int i = 0; i < this.cards.size(); i++) {
                Integer selfValue = VALUE_MAP.get(this.cards.get(i));
                Integer otherHandValue = VALUE_MAP.get(otherHand.cards.get(i));
                if (selfValue > otherHandValue) {
                    return true;
                } else if (selfValue < otherHandValue) {
                    return false;
                }
            }
            throw new IllegalStateException();
        }

    }

    public static List<Hand> readFile(URI uri) throws IOException {
        String file = Files.readString(Paths.get(uri));
        return Pattern.compile("(\\w+) (\\d+)")
                      .matcher(file)
                      .results()
                      .map(matchResult -> {
                          List<Character> cards = matchResult.group(1)
                                                             .chars()
                                                             .mapToObj(c -> (char) c)
                                                             .toList();
                          int id = Integer.parseInt(matchResult.group(2));
                          return new Hand(cards, id);
                      })
                      .toList();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Hand> hands = readFile(Objects.requireNonNull(ClassLoader.getSystemResource("Day7/input.txt")
                                                                      .toURI()));
        System.out.println(partOne(hands));
    }

    public static int partOne(List<Hand> hands) {
        Hand[] sortedHands = new Hand[hands.size()];
        for (Hand hand : hands) {
            int i = 0;
            for (Hand hand1 : hands) {
                if (hand == hand1) {
                    continue;
                }
                if (hand.win(hand1)) {
                    i++;
                }
            }
            sortedHands[i] = hand;
        }
        int result=0;
        for (int i = 0; i < sortedHands.length; i++) {
            Hand sortedHand = sortedHands[i];
            result+=sortedHand.id*(i+1);
        }
        return result;
    }

    public static Map<Character, Integer> initValueMap() {
        Map<Character, Integer> valueMap = new HashMap<>();
        for (int i = 2; i < 10; i++) {
            valueMap.put(Integer.toString(i)
                                .charAt(0), i);
        }
        valueMap.put('T', 10);
        valueMap.put('J', 11);
        valueMap.put('Q', 12);
        valueMap.put('K', 13);
        valueMap.put('A', 14);
        return valueMap;
    }
}
