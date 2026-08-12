package com.rijad.pokecollector.card.dto;

import com.rijad.pokecollector.card.Card;

import java.util.List;

public record CardResponseDto(Long id, String externalId, String name, String number,
                              String image, Double price, String setName,
                              List<VariantResponseDto> variants) {

    public static CardResponseDto from(Card card) {
        List<VariantResponseDto> variants = card.getVariants().stream()
                .map(VariantResponseDto::from)
                .toList();

        return new CardResponseDto(
                card.getId(),
                card.getExternalId(),
                card.getPname(),
                card.getNumber(),
                card.getImageUrl(),
                card.getPrice(),
                card.getSet() != null ? card.getSet().getName() : null,
                variants
        );
    }
}
