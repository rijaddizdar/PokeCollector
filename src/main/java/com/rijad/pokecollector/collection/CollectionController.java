package com.rijad.pokecollector.collection;

import com.rijad.pokecollector.collection.dto.OwnedCardDto;
import com.rijad.pokecollector.collection.dto.SetCompletionDto;
import com.rijad.pokecollector.owner.Owner;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/owners")
public class CollectionController {
    private final CollectionService collectionService;
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }
    @PostMapping("/{ownerId}/cards")
    public void addCard(@PathVariable int ownerId, @RequestParam String externalId, @RequestParam int amount, @RequestParam Condition condition,Authentication auth) {
        String username = auth.getName();
        collectionService.addToCollection(ownerId,externalId,amount,condition,username);
    }
    @GetMapping("/{ownerId}/value")
    public Double total(@PathVariable int ownerId, Authentication auth) {
        String username = auth.getName();
        return collectionService.totalValue(ownerId,username);
    }
    @GetMapping("/{ownerId}/cards")
    public List<OwnedCardDto> getCards(@PathVariable int ownerId, Authentication auth){
        String username = auth.getName();
        return collectionService.getOwnedCards(ownerId,username);
    }
    @PatchMapping("/{ownerId}/cards/{ownedCardId}")
    public void updateAmount(@PathVariable int ownerId, @PathVariable int ownedCardId, @RequestParam int amount, Authentication auth){
        String username = auth.getName();
    collectionService.updateCardAmount(ownerId,ownedCardId,amount,username);
    }

    @DeleteMapping("/{ownerId}/cards/{ownedCardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOwnedCard(@PathVariable int ownerId, @PathVariable int ownedCardId, Authentication auth){
        String username = auth.getName();
        collectionService.deleteOwnedCard(ownerId,ownedCardId,username);
    }
    @GetMapping("/{ownerId}/sets")
    public List<SetCompletionDto> setCompletion(@PathVariable int ownerId, Authentication auth){
        String username = auth.getName();
        return collectionService.getSetCompletions(ownerId,username);
    }

}
