package com.rijad.pokecollector.card;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String externalId;
    private String pname;
    private String number;
    private String rarity;
    private String imageUrl;
    private Double price;
    private Instant priceUpdatedAt;
    @ManyToOne
    private CardSet set;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 25)
    private List<CardVariant> variants=new ArrayList<>();

    public Card() {}
    public Card(long id, String pname, Double price, CardSet set) {
        this.id = id;
        this.pname = pname;
        this.price = price;
        this.set = set;
    }

    public long getId() {
        return id;
    }

    public String getPname() {
        return pname;
    }

    public Double getPrice() {
        return price;
    }

    public List<CardVariant> getVariants() {
        return variants;
    }

    public CardSet getSet() {
        return set;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getNumber() {
        return number;
    }

    public String getRarity() {
        return rarity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Instant getPriceUpdatedAt() {
        return priceUpdatedAt;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPriceUpdatedAt(Instant priceUpdatedAt) {
        this.priceUpdatedAt = priceUpdatedAt;
    }

    public void setSet(CardSet set) {
        this.set = set;
    }

}
