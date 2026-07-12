package com.rijad.pokecollector;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class CardSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String externalId;
    private String name;
    private Instant releaseDate = Instant.now();

    public int getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getName() {
        return name;
    }

    public Instant getReleaseDate() {
        return releaseDate;
    }
}
