package com.f4.commentlike.web.rest;

import com.f4.commentlike.repository.LikeRepository;
import com.f4.commentlike.service.LikeService;
import com.f4.commentlike.service.dto.LikeDTO;
import com.f4.commentlike.service.dto.LikeWithRedisUserDTO;
import com.f4.commentlike.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.f4.commentlike.domain.Like}.
 */
@RestController
@RequestMapping("/api/likes")
public class LikeResource {

    private static final Logger LOG = LoggerFactory.getLogger(LikeResource.class);

    private static final String ENTITY_NAME = "msCommentlikeLike";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeService likeService;

    private final LikeRepository likeRepository;

    public LikeResource(LikeService likeService, LikeRepository likeRepository) {
        this.likeService = likeService;
        this.likeRepository = likeRepository;
    }

    /**
     * {@code POST  /likes} : Create a new like.
     *
     * @param likeDTO the likeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new likeDTO, or with status {@code 400 (Bad Request)} if the
     *         like has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LikeDTO> createLike(@Valid @RequestBody LikeDTO likeDTO) throws URISyntaxException {
        LOG.debug("REST request to save Like : {}", likeDTO);
        if (likeDTO.getId() != null) {
            likeDTO.setId(null); // Ensure ID is null for creation
        }
        likeDTO = likeService.save(likeDTO);
        return ResponseEntity.created(new URI("/api/likes/" + likeDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
                        likeDTO.getId().toString()))
                .body(likeDTO);
    }

    /**
     * {@code PUT  /likes/:id} : Updates an existing like.
     *
     * @param id      the id of the likeDTO to save.
     * @param likeDTO the likeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated likeDTO,
     *         or with status {@code 400 (Bad Request)} if the likeDTO is not valid,
     *         or with status {@code 500 (Internal Server Error)} if the likeDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LikeDTO> updateLike(
            @PathVariable(value = "id", required = false) final UUID id,
            @Valid @RequestBody LikeDTO likeDTO) throws URISyntaxException {
        LOG.debug("REST request to update Like : {}, {}", id, likeDTO);
        if (likeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, likeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        likeDTO = likeService.update(likeDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                        likeDTO.getId().toString()))
                .body(likeDTO);
    }

    /**
     * {@code PATCH  /likes/:id} : Partial updates given fields of an existing like,
     * field will ignore if it is null
     *
     * @param id      the id of the likeDTO to save.
     * @param likeDTO the likeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated likeDTO,
     *         or with status {@code 400 (Bad Request)} if the likeDTO is not valid,
     *         or with status {@code 404 (Not Found)} if the likeDTO is not found,
     *         or with status {@code 500 (Internal Server Error)} if the likeDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LikeDTO> partialUpdateLike(
            @PathVariable(value = "id", required = false) final UUID id,
            @NotNull @RequestBody LikeDTO likeDTO) throws URISyntaxException {
        LOG.debug("REST request to partial update Like partially : {}, {}", id, likeDTO);
        if (likeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, likeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LikeDTO> result = likeService.partialUpdate(likeDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, likeDTO.getId().toString()));
    }

    /**
     * {@code GET  /likes} : get all the likes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of likes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LikeDTO>> getAllLikes(
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Likes");
        Page<LikeDTO> page = likeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /likes/:id} : get the "id" like.
     *
     * @param id the id of the likeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the likeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LikeDTO> getLike(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get Like : {}", id);
        Optional<LikeDTO> likeDTO = likeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likeDTO);
    }

    /**
     * {@code DELETE  /likes/:id} : delete the "id" like.
     *
     * @param id the id of the likeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete Like : {}", id);
        likeService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }

    /**
     * {@code GET  /likes/by-parent} : get likes by parentId and parentType.
     *
     * @param parentId   the parent ID to get likes for.
     * @param parentType the parent type to get likes for.
     * @param pageable   the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of likes in body.
     */
    @GetMapping("/by-parent")
    public ResponseEntity<List<LikeWithRedisUserDTO>> getLikesByParent(
            @RequestParam("parentId") UUID parentId,
            @RequestParam("parentType") String parentType,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get likes for parentId: {} and parentType: {}", parentId, parentType);
        Page<LikeWithRedisUserDTO> page = likeService.findByParentIdAndParentType(parentId, parentType, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /likes/countByParentIdAndParentType} : get count of likes by
     * parentId and
     * parentType.
     *
     * @param parentId   the parent ID to count likes for.
     * @param parentType the parent type to count likes for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/countByParentIdAndParentType")
    public ResponseEntity<Long> countByParentIdAndParentType(
            @RequestParam("parentId") UUID parentId,
            @RequestParam("parentType") String parentType) {
        LOG.debug("REST request to get like count for parentId: {} and parentType: {}", parentId, parentType);
        long count = likeService.countByParentIdAndParentType(parentId, parentType);
        return ResponseEntity.ok(count);
    }

    /**
     * {@code GET  /likes/exists} : check if like exists by parentId and userId.
     *
     * @param parentId the parent ID to check.
     * @param userId   the user ID to check.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and boolean result in body.
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkLikeExists(
            @RequestParam("parentId") UUID parentId,
            @RequestParam("userId") UUID userId) {
        LOG.debug("REST request to check if like exists for parentId: {} and userId: {}", parentId, userId);
        boolean exists = likeService.existsByParentIdAndUserId(parentId, userId);
        return ResponseEntity.ok(exists);
    }

    /**
     * {@code DELETE  /likes/by-parent-and-user} : delete like by parentId and userId.
     *
     * @param parentId the parent ID.
     * @param userId   the user ID.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/by-parent-and-user")
    public ResponseEntity<Void> deleteLikeByParentIdAndUserId(
            @RequestParam("parentId") UUID parentId,
            @RequestParam("userId") UUID userId) {
        LOG.debug("REST request to delete like by parentId: {} and userId: {}", parentId, userId);
        likeService.deleteByParentIdAndUserId(parentId, userId);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, 
                    parentId.toString() + "-" + userId.toString()))
                .build();
    }

    /**
     * {@code GET  /likes/countLikesParentIdsAndParentType} : get like counts for a list of parent IDs and a
     * parent type.
     *
     * @param parentIds  the list of parent IDs to get like counts for.
     * @param parentType the parent type to get like counts for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of like counts in body.
     */
    @GetMapping("/countLikesParentIdsAndParentType")
    public ResponseEntity<List<Integer>> countLikesParentIdsAndParentType(
            @RequestParam("parentIds") List<UUID> parentIds,
            @RequestParam("parentType") String parentType) {
        LOG.debug("REST request to get like counts for parentIds: {} and parentType: {}", parentIds, parentType);
        List<Integer> likeCounts = likeService.countLikesParentIdsAndParentType(parentIds, parentType);
        return ResponseEntity.ok(likeCounts);
    }
}
