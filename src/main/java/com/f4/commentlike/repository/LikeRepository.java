package com.f4.commentlike.repository;

import com.f4.commentlike.domain.Like;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    long countByParentIdAndParentType(UUID parentId, String parentType);

    Page<Like> findByParentIdAndParentType(UUID parentId, String parentType, Pageable pageable);

    @Query("SELECT l.parentId, COUNT(l) FROM Like l " +
            "WHERE l.parentType = :parentType AND l.parentId IN :parentIds " +
            "GROUP BY l.parentId")
    List<Object[]> countLikesParentIdsAndParentType(
            @Param("parentIds") List<UUID> parentIds,
            @Param("parentType") String parentType);

    boolean existsByParentIdAndUserId(UUID parentId, UUID userId);

    void deleteByParentIdAndUserId(UUID parentId, UUID userId);
}
