package com.f4.commentlike.service;

import com.f4.commentlike.service.dto.LikeDTO;
import com.f4.commentlike.service.dto.LikeWithRedisUserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.f4.commentlike.domain.Like}.
 */
public interface LikeService {
    /**
     * Save a like.
     *
     * @param likeDTO the entity to save.
     * @return the persisted entity.
     */
    LikeDTO save(LikeDTO likeDTO);

    /**
     * Updates a like.
     *
     * @param likeDTO the entity to update.
     * @return the persisted entity.
     */
    LikeDTO update(LikeDTO likeDTO);

    /**
     * Partially updates a like.
     *
     * @param likeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LikeDTO> partialUpdate(LikeDTO likeDTO);

    /**
     * Get all the likes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LikeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" like.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikeDTO> findOne(UUID id);

    /**
     * Delete the "id" like.
     *
     * @param id the id of the entity.
     */
    void delete(UUID id);

    /**
     * Get likes by parentId and parentType.
     *
     * @param parentId   the parent ID.
     * @param parentType the parent type.
     * @param pageable   the pagination information.
     * @return the list of entities.
     */
    Page<LikeWithRedisUserDTO> findByParentIdAndParentType(UUID parentId, String parentType, Pageable pageable);

    /**
     * Count likes by parentId and parentType.
     *
     * @param parentId   the parent ID.
     * @param parentType the parent type.
     * @return the count of entities.
     */
    long countByParentIdAndParentType(UUID parentId, String parentType);

    /**
     * Get like counts for a list of parent IDs and a parent type.
     *
     * @param parentIds  the list of parent IDs.
     * @param parentType the parent type.
     * @return a list of like counts corresponding to each parent ID.
     */
    public List<Integer> countLikesParentIdsAndParentType(List<UUID> parentIds, String parentType);

    /**
     * Check if a like exists by parentId and userId.
     *
     * @param parentId the parent ID.
     * @param userId   the user ID.
     * @return true if like exists, false otherwise.
     */
    boolean existsByParentIdAndUserId(UUID parentId, UUID userId);
}
