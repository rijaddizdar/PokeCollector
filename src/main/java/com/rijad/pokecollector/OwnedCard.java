package com.rijad.pokecollector;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class OwnedCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @ManyToOne
    Card card;
    @ManyToOne
    Owner owner;
    int AmountOfCards;
    Instant ScannedDate= Instant.now();
    String condition;
    public OwnedCard() {}
    public OwnedCard(Card card, Owner owner, int AmountOfCards, Instant ScannedDate, String condition) {
        this.card = card;
        this.owner = owner;
        this.AmountOfCards = AmountOfCards;
        this.ScannedDate = ScannedDate;
        this.condition = condition;
    }


}
