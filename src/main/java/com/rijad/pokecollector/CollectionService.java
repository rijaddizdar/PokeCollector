package com.rijad.pokecollector;


import com.rijad.pokecollector.dto.OwnedCardDto;
import com.rijad.pokecollector.dto.SetCompletionDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found: " + ownerId));
        Card card= cardImportService.importCard(externalId);
        Optional<OwnedCard> ownedCardExisting = ownedCardRepository.findByCardIdAndOwnerIdAndCondition(card.getId(), ownerId, condition);
        if(ownedCardExisting.isPresent()) {
            OwnedCard ownedCard= ownedCardExisting.get();
            ownedCard.setAmountOfCards(ownedCard.getAmountOfCards() + amount);
            ownedCardRepository.save(ownedCard);
        }
        else {
            OwnedCard ownedCard=new OwnedCard(card,owner,amount,condition);
            ownedCardRepository.save(ownedCard);
        }
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
            OwnedCardDto ownedCardDto= new OwnedCardDto(ownedCard.getId(), card.getPname(), card.getExternalId(),price, ownedCard.getAmountOfCards(),condition);
            ownedCardDtos.add(ownedCardDto);
        }
        return ownedCardDtos;
    }
    @Transactional
    public void updateCardAmount(int ownerId, int ownedCardId, int amount){
        OwnedCard ownedCard=ownedCardRepository.findByOwnerIdAndId(ownerId,ownedCardId)
                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "You don`t own this card: " + ownedCardId));
        ownedCard.setAmountOfCards(amount);
    }

    public void deleteOwnedCard(int ownerId, int ownedCardId){
        OwnedCard ownedCard=ownedCardRepository.findByOwnerIdAndId(ownerId,ownedCardId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "The card was not found: " + ownedCardId));
        ownedCardRepository.delete(ownedCard);
    }
    public List<SetCompletionDto> getSetCompletions(int ownerId) {
        ownerRepository.findById(ownerId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found: " + ownerId));
        return ownedCardRepository.findSetCompletion(ownerId);
    }
}
