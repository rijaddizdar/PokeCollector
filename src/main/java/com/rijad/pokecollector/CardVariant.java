package com.rijad.pokecollector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"card_id","variant_name"}))
public class CardVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Card card;
    private String variantName;
    private Double price;
    private Instant updatedPriceAt;

    public CardVariant() {}

    public CardVariant(Card card, String variantName, Double price, Instant updatedPriceAt) {
        this.card = card;
        this.variantName = variantName;
        this.price = price;
        this.updatedPriceAt = updatedPriceAt;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setUpdatedPriceAt(Instant updatedPriceAt) {
        this.updatedPriceAt = updatedPriceAt;
    }

    public long getId() {
        return id;
    }
    @JsonIgnore
    public Card getCard() {
        return card;
    }

    public String getVariantName() {
        return variantName;
    }

    public Double getPrice() {
        return price;
    }

    public Instant getUpdatedPriceAt() {
        return updatedPriceAt;
    }

}
