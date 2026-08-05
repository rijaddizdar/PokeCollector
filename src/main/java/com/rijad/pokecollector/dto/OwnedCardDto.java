package com.rijad.pokecollector.dto;


public record OwnedCardDto(int id, String pname, String externalId, Double price, int amountOfCards,String condition) {
}
