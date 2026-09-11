package com.rijad.pokecollector.Security;

import com.rijad.pokecollector.owner.Owner;
import com.rijad.pokecollector.owner.OwnerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OwnerDetailsService implements UserDetailsService {
    OwnerRepository ownerRepository;

    public OwnerDetailsService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner=ownerRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User doesnt exist"+username));
        return User.withUsername(owner.getUsername())
                .password(owner.getPasswordHash())
                .roles("USER")
                .build();
    }

}
