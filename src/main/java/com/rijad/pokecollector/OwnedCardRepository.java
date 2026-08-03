package com.rijad.pokecollector;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnedCardRepository extends JpaRepository<OwnedCard,Integer> {
    List<OwnedCard> findByOwnerId(int ownerId);
}
