package com.rijad.pokecollector.owner;


import com.rijad.pokecollector.owner.dto.CreateOwnerRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    public OwnerService(OwnerRepository ownerRepository, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public Owner createOwner(CreateOwnerRequest req){
        Owner owner = new Owner(req.fname(),  req.lname(), req.email(),
                passwordEncoder.encode(req.password()),
                req.username());
        return ownerRepository.save(owner);
    }

}
