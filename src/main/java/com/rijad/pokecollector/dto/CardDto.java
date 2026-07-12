package com.rijad.pokecollector.dto;

public record CardDto(String id, String name, String localId, String rarity, String image, SetDto set, PricingDto pricing){
}
