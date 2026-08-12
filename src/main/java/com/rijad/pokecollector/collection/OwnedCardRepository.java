package com.rijad.pokecollector.collection;

import com.rijad.pokecollector.collection.dto.SetCompletionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnedCardRepository extends JpaRepository<OwnedCard,Integer> {
    List<OwnedCard> findByOwnerId(int ownerId);
    Optional<OwnedCard> findByOwnerIdAndId(int ownerId, int ownedCardId);
    Optional<OwnedCard> findByCardIdAndOwnerIdAndCondition(long cardId, int ownerId, Condition condition);
    @Query("""
            select new com.rijad.pokecollector.collection.dto.SetCompletionDto(
                s.name, s.externalId, count(distinct o.card.id), s.cardCount)
            from OwnedCard o
            join o.card c
            join c.set s
            where o.owner.id=:ownerId
            group by s.id, s.name, s.externalId, s.cardCount
            """)
    List<SetCompletionDto> findSetCompletion(@Param("ownerId") int ownerId);
}
