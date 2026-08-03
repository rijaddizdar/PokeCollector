package com.rijad.pokecollector;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class OwnedCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    Card card;
    @ManyToOne
    Owner owner;
    int amountOfCards;
    String condition;
    public OwnedCard() {}
    public OwnedCard(Card card, Owner owner, int amountOfCards, String condition) {
        this.card = card;
        this.owner = owner;
        this.amountOfCards = amountOfCards;
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public Card getCard() {
        return card;
    }

    public Owner getOwner() {
        return owner;
    }

    public int getAmountOfCards() {
        return amountOfCards;
    }

    public String getCondition() {
        return condition;
    }
}
