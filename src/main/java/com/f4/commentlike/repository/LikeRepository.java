package com.f4.commentlike.repository;

import com.f4.commentlike.domain.Like;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Like entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {
    long countByParentIdAndParentType(UUID parentId, String parentType);

    Page<Like> findByParentIdAndParentType(UUID parentId, String parentType, Pageable pageable);
}
