package com.f4.commentlike.repository;

import com.f4.commentlike.domain.Comment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    long countByParentIdAndParentType(UUID parentId, String parentType);

    Page<Comment> findByParentIdAndParentType(UUID parentIds, String parentType, Pageable pageable);

    @Query("SELECT c.parentId, COUNT(c) FROM Comment c " +
            "WHERE c.parentType = :parentType AND c.parentId IN :parentIds " +
            "GROUP BY c.parentId")
    List<Object[]> countCommentsParentIdsAndParentType(
            @Param("parentIds") List<UUID> parentIds,
            @Param("parentType") String parentType);
}
