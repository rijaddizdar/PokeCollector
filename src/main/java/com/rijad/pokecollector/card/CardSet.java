package com.rijad.pokecollector.card;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class CardSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String externalId;
    private String name;
    private LocalDate releaseDate;
    private int cardCount;

    public long getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
    public int getCardCount() {
        return cardCount;
    }
    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

}
