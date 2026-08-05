package com.rijad.pokecollector;


import com.rijad.pokecollector.dto.OwnedCardDto;
import com.rijad.pokecollector.dto.OwnerDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CollectionService {
    private final OwnedCardRepository ownedCardRepository;
    private final OwnerRepository ownerRepository;
    private final CardRepository cardRepository;
    private final CardImportService cardImportService;
    public CollectionService(OwnedCardRepository ownedCardRepository,OwnerRepository ownerRepository, CardRepository cardRepository,  CardImportService cardImportService) {
        this.ownedCardRepository = ownedCardRepository;
        this.ownerRepository = ownerRepository;
        this.cardRepository = cardRepository;
        this.cardImportService = cardImportService;
    }
    public void addToCollection(int ownerId, String externalId, int amount, Condition condition) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(()-> new RuntimeException("Owner not found: " + ownerId));
        Card card= cardImportService.importCard(externalId);
        OwnedCard ownedCard=new OwnedCard(card,owner,amount,condition);
        ownedCardRepository.save(ownedCard);
    }
    public Double totalValue(int ownerId) {
        List<OwnedCard> ownedCards = ownedCardRepository.findByOwnerId(ownerId);
        double total = 0.0;
        for (OwnedCard ownedCard : ownedCards) {
            Double price=ownedCard.getCard().getPrice();
            if(price!=null) {
                total+=price*ownedCard.getAmountOfCards();
            }
        }
        return total;
    }
    public List<OwnedCardDto> getOwnedCards(int ownerId) {
        List<OwnedCard> ownedCards = ownedCardRepository.findByOwnerId(ownerId);
        List<OwnedCardDto> ownedCardDtos = new ArrayList<>();
        for (OwnedCard ownedCard : ownedCards) {
            Card card=ownedCard.getCard();
            Double price=card.getPrice();
            String condition=ownedCard.getCondition().toString();
            OwnedCardDto ownedCardDto= new OwnedCardDto(card.getPname(), card.getExternalId(),price, ownedCard.getAmountOfCards(),condition);
            ownedCardDtos.add(ownedCardDto);
        }
        return ownedCardDtos;
    }
}
