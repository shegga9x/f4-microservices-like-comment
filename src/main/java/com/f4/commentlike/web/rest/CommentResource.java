package com.f4.commentlike.web.rest;

import com.f4.commentlike.domain.Comment;
import com.f4.commentlike.repository.CommentRepository;
import com.f4.commentlike.service.CommentService;
import com.f4.commentlike.service.dto.CommentDTO;
import com.f4.commentlike.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
 * REST controller for managing {@link com.f4.commentlike.domain.Comment}.
 */
@RestController
@RequestMapping("/api/comments")
public class CommentResource {

    private static final Logger LOG = LoggerFactory.getLogger(CommentResource.class);

    private static final String ENTITY_NAME = "msCommentlikeComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    public CommentResource(CommentService commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    /**
     * {@code POST  /comments} : Create a new comment.
     *
     * @param commentDTO the commentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new commentDTO, or with status {@code 400 (Bad Request)} if
     *         the comment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO)
            throws URISyntaxException {
        LOG.debug("REST request to save Comment : {}", commentDTO);
        if (commentDTO.getId() != null) {
            throw new BadRequestAlertException("A new comment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        commentDTO = commentService.save(commentDTO);
        return ResponseEntity.created(new URI("/api/comments/" + commentDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME,
                        commentDTO.getId().toString()))
                .body(commentDTO);
    }

    /**
     * {@code PUT  /comments/:id} : Updates an existing comment.
     *
     * @param id         the id of the commentDTO to save.
     * @param commentDTO the commentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated commentDTO,
     *         or with status {@code 400 (Bad Request)} if the commentDTO is not
     *         valid,
     *         or with status {@code 500 (Internal Server Error)} if the commentDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable(value = "id", required = false) final UUID id,
            @Valid @RequestBody CommentDTO commentDTO) throws URISyntaxException {
        LOG.debug("REST request to update Comment : {}, {}", id, commentDTO);
        if (commentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        commentDTO = commentService.update(commentDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,
                        commentDTO.getId().toString()))
                .body(commentDTO);
    }

    /**
     * {@code PATCH  /comments/:id} : Partial updates given fields of an existing
     * comment, field will ignore if it is null
     *
     * @param id         the id of the commentDTO to save.
     * @param commentDTO the commentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the updated commentDTO,
     *         or with status {@code 400 (Bad Request)} if the commentDTO is not
     *         valid,
     *         or with status {@code 404 (Not Found)} if the commentDTO is not
     *         found,
     *         or with status {@code 500 (Internal Server Error)} if the commentDTO
     *         couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CommentDTO> partialUpdateComment(
            @PathVariable(value = "id", required = false) final UUID id,
            @NotNull @RequestBody CommentDTO commentDTO) throws URISyntaxException {
        LOG.debug("REST request to partial update Comment partially : {}, {}", id, commentDTO);
        if (commentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, commentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!commentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CommentDTO> result = commentService.partialUpdate(commentDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentDTO.getId().toString()));
    }

    /**
     * {@code GET  /comments} : get all the comments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of comments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CommentDTO>> getAllComments(
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Comments");
        Page<CommentDTO> page = commentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comments/by-parent} : get comments by parentId and parentType.
     *
     * @param parentId   the parent ID to get comments for.
     * @param parentType the parent type to get comments for.
     * @param pageable   the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
     *         of comments in body.
     */
    @GetMapping("/by-parent")
    public ResponseEntity<List<CommentDTO>> getCommentsByParent(
            @RequestParam("parentId") UUID parentId,
            @RequestParam("parentType") String parentType,
            @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get comments for parentId: {} and parentType: {}", parentId, parentType);
        Page<CommentDTO> page = commentService.findByParentIdAndParentType(parentId, parentType, pageable);
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /comments/count} : get count of comments by parentId and
     * parentType.
     *
     * @param parentId   the parent ID to count comments for.
     * @param parentType the parent type to count comments for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
     *         in body.
     */
    @GetMapping("/countByParentIdAndParentType")
    public ResponseEntity<Long> countByParentIdAndParentType(
            @RequestParam("parentId") UUID parentId,
            @RequestParam("parentType") String parentType) {
        LOG.debug("REST request to get comment count for parentId: {} and parentType: {}", parentId, parentType);
        long count = commentService.countByParentIdAndParentType(parentId, parentType);
        return ResponseEntity.ok(count);
    }

    /**
     * {@code GET  /comments/:id} : get the "id" comment.
     *
     * @param id the id of the commentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
     *         the commentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("id") UUID id) {
        LOG.debug("REST request to get Comment : {}", id);
        Optional<CommentDTO> commentDTO = commentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentDTO);
    }

    /**
     * {@code DELETE  /comments/:id} : delete the "id" comment.
     *
     * @param id the id of the commentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") UUID id) {
        LOG.debug("REST request to delete Comment : {}", id);
        commentService.delete(id);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
