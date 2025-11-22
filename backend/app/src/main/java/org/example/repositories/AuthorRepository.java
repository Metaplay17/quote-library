package org.example.repositories;

import org.example.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query(value = "SELECT * FROM create_author(:name)", nativeQuery = true)
    void createAuthor(@Param("name") String name);

    @Query(value = "SELECT * FROM delete_author(:author_id)", nativeQuery = true)
    public void deleteAuthor(@Param("author_id") Integer authorId);
}
