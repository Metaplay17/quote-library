package org.example.repositories;

import java.util.List;

import org.example.models.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    @Query(value = "SELECT * FROM get_quotes_by_pattern_and_authors_id_and_tags_id(:is_saved, :userId, :pattern, CAST(:tagsId AS integer[]), CAST(:authorsId AS integer[]), :startIndex, 4)", nativeQuery = true)
    List<Quote> findQuotesByPatternAndAuthorsAndTags(@Param("is_saved") Boolean isSaved,
                                                    @Param("userId") Integer userId,
                                                    @Param("pattern") String pattern,
                                                    @Param("tagsId") Integer[] tagsId,
                                                    @Param("authorsId") Integer[] authorsId,
                                                    @Param("startIndex") Integer startIndex
                                                );

    @Query(value = "SELECT * FROM get_user_added_quotes_by_user_id(:userId, :startIndex, 4)", nativeQuery = true)
    List<Quote> getSavedUserQuotes(@Param("userId") Integer userId, @Param("startIndex") Integer startIndex);

    @Query(value = "SELECT * FROM get_random_user_not_added_quotes_by_user_id(:userId, 4)", nativeQuery = true)
    List<Quote> getRandomNotSavedUserQuotes(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM create_quote(:text, :author_id, :context, :tags)", nativeQuery = true)
    void createQuotes(@Param("text") String text, @Param("author_id") Integer authorId, @Param("context") String context, @Param("tags") String[] tags);

    @Query(value = "SELECT * FROM delete_quote(:quote_id)", nativeQuery = true)
    void deleteQuote(@Param("quote_id") Integer quote_id);
}
