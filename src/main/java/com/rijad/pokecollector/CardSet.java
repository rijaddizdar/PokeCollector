package com.rijad.pokecollector;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class CardSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    Instant ReleseDate=Instant.now();

}
