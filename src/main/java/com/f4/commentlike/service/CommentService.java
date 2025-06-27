package com.f4.commentlike.service;

import com.f4.commentlike.service.dto.CommentDTO;
import com.f4.commentlike.service.dto.CommentWithRedisUserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.f4.commentlike.domain.Comment}.
 */
public interface CommentService {
    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save.
     * @return the persisted entity.
     */
    CommentDTO save(CommentDTO commentDTO);

    /**
     * Updates a comment.
     *
     * @param commentDTO the entity to update.
     * @return the persisted entity.
     */
    CommentDTO update(CommentDTO commentDTO);

    /**
     * Partially updates a comment.
     *
     * @param commentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommentDTO> partialUpdate(CommentDTO commentDTO);

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentDTO> findOne(UUID id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);

    /**
     * Get comments by parentId and parentType.
     *
     * @param parentId   the parent ID.
     * @param parentType the parent type.
     * @param pageable   the pagination information.
     * @return the list of entities.
     */
    Page<CommentWithRedisUserDTO> findByParentIdAndParentType(UUID parentId, String parentType,
            Pageable pageable);

    /**
     * Count comments by parentId and parentType.
     *
     * @param parentId   the parent ID.
     * @param parentType the parent type.
     * @return the count of entities.
     */
    long countByParentIdAndParentType(UUID parentId, String parentType);

    /**
     * Count comments for multiple parent IDs and parent type.
     *
     * @param parentIds  the list of parent IDs.
     * @param parentType the parent type.
     * @return the list of comment counts.
     */
    List<Integer> countCommentsParentIdsAndParentType(List<UUID> parentIds, String parentType);
}
