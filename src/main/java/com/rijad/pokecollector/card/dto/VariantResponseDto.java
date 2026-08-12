package com.rijad.pokecollector.card.dto;

import com.rijad.pokecollector.card.CardVariant;

import java.time.Instant;

public record VariantResponseDto(String variantName, Double price, Instant updatedPriceAt) {

    public static VariantResponseDto from(CardVariant variant) {
        return new VariantResponseDto(
                variant.getVariantName(),
                variant.getPrice(),
                variant.getUpdatedPriceAt()
        );
    }
}
