package com.rijad.pokecollector.owner;


import com.rijad.pokecollector.owner.dto.CreateOwnerRequest;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    public Owner createOwner(CreateOwnerRequest req){
        Owner owner = new Owner(req.fname(),  req.lname(), req.email(),
                req.password(),   // TODO: hash before storing (Spring Security) — plaintext for now
                req.username());
        return ownerRepository.save(owner);
    }

}
