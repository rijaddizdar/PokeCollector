package com.rijad.pokecollector.card;

import com.rijad.pokecollector.card.tcgdex.*;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class CardImportService {
    private final CardRepository cardRepository;
    private final RestClient restClient=RestClient.create();
    private final CardSetRepository cardSetRepository;
    private static final Logger log= LoggerFactory.getLogger(CardImportService.class);

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
        Card card=cardRepository.findByExternalId(dto.id()).orElseGet(Card::new);
        card.setExternalId(dto.id());
        card.setPname(dto.name());
        card.setNumber(dto.localId());
        card.setRarity(dto.rarity());
        card.setImageUrl(dto.image());
        Double price=null;
        if(dto.pricing()!=null && dto.pricing().tcgplayer()!=null){
            TcgPlayerDto tcg=dto.pricing().tcgplayer();
            VariantDto variant= tcg.getVariants().get("normal");
            if(variant==null && !tcg.getVariants().isEmpty()){
                variant= tcg.getVariants().values().iterator().next();
            }
            if(variant!=null){
                price=variant.marketPrice();
                card.setPriceUpdatedAt(Instant.now());
            }
            for(Map.Entry<String,VariantDto> entry: tcg.getVariants().entrySet()){
                String name=entry.getKey();
                VariantDto incoming=entry.getValue();

                Optional<CardVariant> existing= card.getVariants().stream()
                        .filter(v->name.equals(v.getVariantName()))
                        .findFirst();
                if(existing.isPresent()){
                    CardVariant existingVariant=existing.get();
                    existingVariant.setPrice(incoming.marketPrice());
                    existingVariant.setUpdatedPriceAt(Instant.now());
                }
                else{
                    CardVariant newVariant=new CardVariant(card,name,incoming.marketPrice(),Instant.now());
                    card.getVariants().add(newVariant);
                }
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
                    set.setCardCount(dto.cardCount().total());
                    return cardSetRepository.save(set);
                });
    }
    @Transactional
    public Card importCard(String externalId){
        CardDto dto= fetchCard(externalId);
        Card card=toCard(dto);
        return cardRepository.save(card);

    }
    @Transactional
    @Scheduled(fixedDelay=6, timeUnit = TimeUnit.HOURS)
    public int refreshAllPrices(){
        List<Card> all=cardRepository.findAll();
        for(Card card:all){
            importCard(card.getExternalId());
        }
        log.info("Refreshed {} cards", all.size());
        return all.size();
    }
}
