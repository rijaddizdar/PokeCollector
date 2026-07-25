package com.rijad.pokecollector;

import jakarta.persistence.*;

import java.time.Instant;
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

}
