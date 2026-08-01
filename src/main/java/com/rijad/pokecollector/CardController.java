package com.rijad.pokecollector;


import com.rijad.pokecollector.dto.CardSummaryDto;
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
    public List<Card>  findAll(){
        return cardRepository.findAll();
    }
    @GetMapping("/{id}")
    public Card findById(@PathVariable Long id) {
        return cardRepository.findById(id).orElse(null);
    }
    @GetMapping("/search")
    public List<CardSummaryDto> search(@RequestParam String name, @RequestParam String number){
        return cardImportService.searchCards(name, number);
    }

}
