package org.example.repositories;

import java.util.List;

import org.example.models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    @Query(value = "SELECT * FROM get_quotes_by_authors_id_and_tags_id(:tagsId, :authorsId, :startIndex, 5)", nativeQuery = true)
    List<Quote> findQuotesByAuthorAndTag(@Param("tagsId") List<Integer> tagsId, @Param("authorsId") List<Integer> authorsId, @Param("startIndex") Integer startIndex);

    @Query(value = "SELECT * FROM get_user_added_quotes_by_user_id(:userId, :startIndex, 5)", nativeQuery = true)
    List<Quote> getSavedUserQuotes(@Param("userId") Integer userId, @Param("startIndex") Integer startIndex);

    @Query(value = "SELECT * FROM get_random_user_not_added_quotes_by_user_id(:userId, 5)", nativeQuery = true)
    List<Quote> getRandomNotSavedUserQuotes(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM search_quotes_by_pattern(:pattern, :startIndex, 5)", nativeQuery = true)
    List<Quote> searchQuotes(@Param("pattern") String pattern, @Param("startIndex") Integer startIndex);
}
