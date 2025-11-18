package org.example.repositories;

import java.util.Optional;

import org.example.models.UserQuote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQuoteRepository extends JpaRepository<UserQuote, Integer> {
    Optional<UserQuote> findByUserIdAndQuoteId(Integer userId, Integer QuoteId);
}
