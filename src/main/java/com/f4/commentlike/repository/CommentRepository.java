package com.f4.commentlike.repository;

import com.f4.commentlike.domain.Comment;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    
    long countByParentIdAndParentType(UUID parentId, String parentType);
    
    
    Page<Comment> findByParentIdAndParentType(UUID parentId, String parentType, Pageable pageable);
}
