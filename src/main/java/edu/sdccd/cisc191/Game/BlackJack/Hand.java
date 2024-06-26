package edu.sdccd.cisc191.Game.BlackJack;


import edu.sdccd.cisc191.Game.BlackJack.Cards.Rank;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;


public class Hand {

    private ObservableList<Node> cards;
    private SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    private int aces = 0;

    public Hand(ObservableList<Node> cards) {
        this.cards = cards;
    }

    /**
     * takes in a card
     * @param card takes in a card
     */
    public void takeCard(Cards card) {
        cards.add(card);

        if (card.rank == Rank.ACE) {
            aces++;
        }

        if (value.get() + card.value > 21 && aces > 0) {
            value.set(value.get() + card.value - 10);    //then count ace as '1' not '11'
            aces--;
        }
        else {
            value.set(value.get() + card.value);
        }
    }

    /**
     * resets card values
     */
    public void reset() {
        cards.clear();
        value.set(0);
        aces = 0;
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }
}

