/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.util.Random;

/**
 *
 * @author Soumit
 */
public class Deck {

    private final int DECK_SIZE = 52;
    private final int SHUFFLE_EXCHANGES = 2000;
    private final int HAND_SIZE = 2;
    public int restOfDeck = 3;
    public int deckpos=0;

    Card[] deck = new Card[DECK_SIZE];
    Random r = new Random();

    Deck() {

    }

    // fill deck with cards
    public void fillDeck() {
        int counter = 0;
        deckpos = 0;
        for (int suit = 1; suit <= 4; suit++) {
            for (int rank = 2; rank <= 14; rank++) {
                deck[counter] = new Card();
                deck[counter].suit = suit;
                deck[counter].rank = rank;
                counter++;
            }
        }
    }

    // shuffle deck
    public void shuffle() {
        for (int x = 0; x <= SHUFFLE_EXCHANGES; x++) {
            int number1 = r.nextInt(DECK_SIZE);
            int number2 = r.nextInt(DECK_SIZE);
            Card temp = deck[number1];
            deck[number1] = deck[number2];
            deck[number2] = temp;
        }
        deckpos = 0;
    }

    // deals cards
    public Card[] deal(int n) {
        Card[] hand = new Card[n];
        int x=deckpos;
        if (n == 2) {
            int i=0;
            for (int deckPosition = deckpos; deckPosition < n+x ; deckPosition++) {
                hand[i] = deck[deckPosition];
                deckpos++;
                i++;
            }
            return hand;
        }
        if (n == 5) {
            int i = 0;
            for (int deckPosition = deckpos; deckPosition < n+x ; deckPosition++) {
                hand[i] = deck[deckPosition];
                i++;
                deckpos++;
            }
            return hand;
        }
        return hand;
    }

    // deals cards for redraw
    public Card redeal() {
        Card nextCard = deck[restOfDeck];
        restOfDeck++;
        return nextCard;
    }

    // refreshes deck position to 6 for next hand
    public void refreshDeckPosition() {
        restOfDeck = 6;
    }
}