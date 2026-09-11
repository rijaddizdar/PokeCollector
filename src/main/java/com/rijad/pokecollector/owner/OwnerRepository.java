package com.rijad.pokecollector.owner;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner,Integer> {
    Optional<Owner> findByUsername(String username);
}
