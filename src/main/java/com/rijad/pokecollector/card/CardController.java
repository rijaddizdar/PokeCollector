package com.rijad.pokecollector.card;


import com.rijad.pokecollector.card.dto.CardResponseDto;
import com.rijad.pokecollector.card.tcgdex.CardSummaryDto;
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
    public CardResponseDto importCard(@PathVariable String externalId) {
        return CardResponseDto.from(cardImportService.importCard(externalId));
    }
    @GetMapping
    public Page<CardResponseDto> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable).map(CardResponseDto::from);
    }
    @GetMapping("/{id}")
    public CardResponseDto findById(@PathVariable Long id) {
        return cardRepository.findById(id).map(CardResponseDto::from).orElse(null);
    }
    @GetMapping("/search")
    public List<CardSummaryDto> search(@RequestParam String name, @RequestParam String number){return cardImportService.searchCards(name, number);}
    @PostMapping("/refresh-prices")
    public int refreshAllPrices(){
        return cardImportService.refreshAllPrices();
    }

}
