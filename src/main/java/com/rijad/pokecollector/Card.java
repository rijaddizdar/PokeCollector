package com.rijad.pokecollector;

import jakarta.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pname;
    private Double price;
    @ManyToOne
    private CardSet set;

    public Card() {}
    public Card(int id, String pname, Double price, CardSet set) {
        this.id = id;
        this.pname = pname;
        this.price = price;
        this.set = set;
    }

    public int getId() {
        return id;
    }

    public String getPname() {
        return pname;
    }

    public Double getPrice() {
        return price;
    }

    public CardSet getSet() {
        return set;
    }

}
