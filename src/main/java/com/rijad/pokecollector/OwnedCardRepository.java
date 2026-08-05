package com.rijad.pokecollector;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnedCardRepository extends JpaRepository<OwnedCard,Integer> {
    List<OwnedCard> findByOwnerId(int ownerId);
    Optional<OwnedCard> findByOwnerIdAndId(int ownerId, int ownedCardId);
}
