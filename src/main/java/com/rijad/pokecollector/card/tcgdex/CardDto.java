package com.rijad.pokecollector.card.tcgdex;

public record CardDto(String id, String name, String localId, String rarity, String image, SetDto set, PricingDto pricing){
}
