package com.rijad.pokecollector;


import com.rijad.pokecollector.dto.CreateOwnerRequest;
import com.rijad.pokecollector.dto.OwnerDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {
    private final OwnerService ownerService;
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDto create(@RequestBody CreateOwnerRequest req) {
        Owner saved=ownerService.createOwner(req);
        return new OwnerDto(saved.getId(), saved.getFname(), saved.getLname(), saved.getEmail(), saved.getUsername());
    }


}
