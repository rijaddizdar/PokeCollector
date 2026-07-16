package com.rijad.pokecollector;

import com.rijad.pokecollector.dto.CardDto;
import com.rijad.pokecollector.dto.SetDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;


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
    public Card toCard(CardDto dto){
        Card card=new Card();
        card.setExternalId(dto.id());
        card.setPname(dto.name());
        card.setNumber(dto.localId());
        card.setRarity(dto.rarity());
        card.setImageUrl(dto.image());
        Double price=null;
        if(dto.pricing()!=null && dto.pricing().tcgplayer()!=null && dto.pricing().tcgplayer().normal()!=null){
            price=dto.pricing().tcgplayer().normal().marketPrice();
            card.setPriceUpdatedAt(Instant.now());
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
        cardRepository.save(card);
        return card;
    }
}
