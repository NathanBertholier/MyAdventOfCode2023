package Day7;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CamelCardsTest {

    @org.junit.jupiter.api.Test
    void fiveOfAKind() {
        CamelCards.Hand hand = new CamelCards.Hand(List.of('A', 'A', 'A', 'A', 'A'), 0);
        CamelCards.Hand hand2 = new CamelCards.Hand(List.of('A', 'A', 'A', 'A', '1'), 0);
        assertAll(
                () -> assertTrue(CamelCards.fiveOfAKind(hand)),
                () -> assertFalse(CamelCards.fiveOfAKind(hand2))
        );
    }

    @org.junit.jupiter.api.Test
    void fourOfAKind() {
        CamelCards.Hand hand = new CamelCards.Hand(List.of('A', 'A', 'A', 'A', '1'), 0);
        CamelCards.Hand hand2 = new CamelCards.Hand(List.of('A', 'A', 'A', '1', '1'), 0);
        assertAll(
                () -> assertTrue(CamelCards.fourOfAKind(hand)),
                () -> assertFalse(CamelCards.fourOfAKind(hand2))
        );
    }

    @org.junit.jupiter.api.Test
    void fullHouse() {
        CamelCards.Hand hand = new CamelCards.Hand(List.of('A', 'A', 'A', '1', '1'), 0);
        CamelCards.Hand hand2 = new CamelCards.Hand(List.of('A', 'A', 'A', '2', '1'), 0);
        assertAll(
                () -> assertTrue(CamelCards.fullHouse(hand)),
                () -> assertFalse(CamelCards.fullHouse(hand2))
        );
    }

    @org.junit.jupiter.api.Test
    void threeOfAKind() {
        CamelCards.Hand hand = new CamelCards.Hand(List.of('A', 'A', 'A', '1', '2'), 0);
        CamelCards.Hand hand2 = new CamelCards.Hand(List.of('A', 'A', '1', '2', '3'), 0);
        assertAll(
                () -> assertTrue(CamelCards.threeOfAKind(hand)),
                () -> assertFalse(CamelCards.threeOfAKind(hand2))
        );
    }

    @org.junit.jupiter.api.Test
    void twoPair() {
        CamelCards.Hand hand = new CamelCards.Hand(List.of('A', 'A', '1', '1', '2'), 0);
        CamelCards.Hand hand2 = new CamelCards.Hand(List.of('A', 'A', '1', '2', '3'), 0);
        assertAll(
                () -> assertTrue(CamelCards.twoPair(hand)),
                () -> assertFalse(CamelCards.twoPair(hand2))
        );
    }

    @org.junit.jupiter.api.Test
    void onePair() {
        CamelCards.Hand hand = new CamelCards.Hand(List.of('A', 'A', '1', '2', '3'), 0);
        CamelCards.Hand hand2 = new CamelCards.Hand(List.of('A', '1', '2', '3', '4'), 0);
        assertAll(
                () -> assertTrue(CamelCards.onePair(hand)),
                () -> assertFalse(CamelCards.onePair(hand2))
        );
    }

    @Test
    void highCard() {
        CamelCards.Hand hand = new CamelCards.Hand(List.of('A', '1', '2', '3', '4'), 0);
        CamelCards.Hand hand2 = new CamelCards.Hand(List.of('A', '1', '2', '3', '3'), 0);
        assertAll(
                () -> assertTrue(CamelCards.highCard(hand)),
                () -> assertFalse(CamelCards.highCard(hand2))
        );
    }
}