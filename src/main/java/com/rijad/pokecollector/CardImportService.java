package com.rijad.pokecollector;

import com.rijad.pokecollector.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;


@Service
public class CardImportService {
    private final CardRepository cardRepository;
    private final RestClient restClient=RestClient.create();
    private final CardSetRepository cardSetRepository;

    public CardImportService(CardRepository cardRepository, CardSetRepository cardSetRepository) {
        this.cardRepository = cardRepository;
        this.cardSetRepository = cardSetRepository;
    }
    public CardDto fetchCard(String externalId){
        String url="https://api.tcgdex.net/v2/en/cards/"+externalId;

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(CardDto.class);
    }
    private String normalize(String s){
        return s
                .trim()
                .toUpperCase()
                .split("/")[0]
                .replaceFirst("^0+","");
    }
    public List<CardSummaryDto> searchCards(String name, String number){
        CardSummaryDto[] hits= restClient.get()
                .uri("https://api.tcgdex.net/v2/en/cards?name={n}", name)
                .retrieve()
                .body(CardSummaryDto[].class);
        String target=normalize(number);
        return Arrays.stream(hits)
                .filter(c ->normalize(c.localId()).equals(target))
                .toList();
    }
    public Card toCard(CardDto dto){
        Card card=new Card();
        card.setExternalId(dto.id());
        card.setPname(dto.name());
        card.setNumber(dto.localId());
        card.setRarity(dto.rarity());
        card.setImageUrl(dto.image());
        Double price=null;
        if(dto.pricing()!=null && dto.pricing().tcgplayer()!=null){
            TcgPlayerDto tcg=dto.pricing().tcgplayer();
            VariantDto variant= tcg.normal() !=null ? tcg.normal() : tcg.holofoil();
            if(variant!=null){
                price=variant.marketPrice();
                card.setPriceUpdatedAt(Instant.now());
            }
        }
        card.setPrice(price);
        if(dto.set()!=null){
            card.setSet(toSet(dto.set()));
        }

        return card;
    }
    public CardSet toSet(SetDto dto){
        return cardSetRepository.findByExternalId(dto.id())
                .orElseGet(()->{
                    CardSet set=new CardSet();
                    set.setName(dto.name());
                    set.setExternalId(dto.id());
                    return cardSetRepository.save(set);
                });
    }
    public Card importCard(String externalId){
        CardDto dto= fetchCard(externalId);
        Card card=toCard(dto);
        cardRepository.findByExternalId(dto.id())
                .ifPresent(existing-> card.setId(existing.getId()));
        return cardRepository.save(card);

    }
}
