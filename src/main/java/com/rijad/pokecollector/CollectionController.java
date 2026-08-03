package com.rijad.pokecollector;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/owners")
public class CollectionController {
    private final CollectionService collectionService;
    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }
    @PostMapping("/{ownerId}/cards")
    public void addCard(@PathVariable int ownerId, @RequestParam String externalId, @RequestParam int amount, @RequestParam String condition){
        collectionService.addToCollection(ownerId,externalId,amount,condition);
    }
    @GetMapping("/{ownerId}/value")
    public Double total(@PathVariable int ownerId){
        return collectionService.totalValue(ownerId);
    }

}
