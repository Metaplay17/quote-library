package org.example.repositories;

import org.example.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query(value = "SELECT * FROM create_tag(:tag_name)", nativeQuery = true)
    void createTag(@Param("tag_name") String name);
    
    @Query(value = "SELECT * FROM delete_tag(:tag_id)", nativeQuery = true)
    void deleteTag(@Param("tag_id") Integer tagId);
}
