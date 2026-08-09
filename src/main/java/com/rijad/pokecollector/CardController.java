package com.rijad.pokecollector;


import com.rijad.pokecollector.dto.CardSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardRepository cardRepository;
    private final CardImportService cardImportService;


    public CardController(CardRepository cardRepository, CardImportService cardImportService) {
        this.cardRepository = cardRepository;
        this.cardImportService = cardImportService;
    }
    @PostMapping("/import/{externalId}")
    public Card importCard(@PathVariable String externalId) {
        return cardImportService.importCard(externalId);
    }
    @GetMapping
    public Page<Card> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }
    @GetMapping("/{id}")
    public Card findById(@PathVariable Long id) {
        return cardRepository.findById(id).orElse(null);
    }
    @GetMapping("/search")
    public List<CardSummaryDto> search(@RequestParam String name, @RequestParam String number){
        return cardImportService.searchCards(name, number);
    }
    @PostMapping("/refresh-prices")
    public int refreshAllPrices(){
        return cardImportService.refreshAllPrices();
    }

}
