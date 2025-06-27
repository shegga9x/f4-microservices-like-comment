package com.f4.commentlike.service.impl;

import com.f4.commentlike.client.api.UserResourceApi;
import com.f4.commentlike.client.model.RedisUserDTO;
import com.f4.commentlike.domain.Comment;
import com.f4.commentlike.repository.CommentRepository;
import com.f4.commentlike.service.CommentService;
import com.f4.commentlike.service.dto.CommentDTO;
import com.f4.commentlike.service.dto.CommentWithRedisUserDTO;
import com.f4.commentlike.service.mapper.CommentMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.f4.commentlike.domain.Comment}.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;
    private final UserResourceApi userResourceApi;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper,
            UserResourceApi userResourceApi) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userResourceApi = userResourceApi;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        LOG.debug("Request to save Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO) {
        LOG.debug("Request to update Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public Optional<CommentDTO> partialUpdate(CommentDTO commentDTO) {
        LOG.debug("Request to partially update Comment : {}", commentDTO);

        return commentRepository
                .findById(commentDTO.getId())
                .map(existingComment -> {
                    commentMapper.partialUpdate(existingComment, commentDTO);

                    return existingComment;
                })
                .map(commentRepository::save)
                .map(commentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Comments");
        return commentRepository.findAll(pageable).map(commentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDTO> findOne(UUID id) {
        LOG.debug("Request to get Comment : {}", id);
        return commentRepository.findById(id).map(commentMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete Comment : {}", id);
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentWithRedisUserDTO> findByParentIdAndParentType(UUID parentId, String parentType,
            Pageable pageable) {
        LOG.debug("Request to get userIds from Comments by parentId: {} and parentType: {}", parentId, parentType);

        Page<CommentDTO> commentPage = commentRepository.findByParentIdAndParentType(parentId, parentType, pageable)
                .map(commentMapper::toDto);

        List<CommentDTO> commentDTOs = commentPage.getContent();

        List<UUID> userIds = commentDTOs.stream()
                .map(CommentDTO::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<RedisUserDTO> redisUserDTOs = userResourceApi.getUsersFromRedisPost(userIds);

        // Map userId -> RedisUserDTO
        Map<UUID, RedisUserDTO> userMap = redisUserDTOs.stream()
                .collect(Collectors.toMap(RedisUserDTO::getId, Function.identity()));

        // Build CommentWithRedisUserDTO list
        List<CommentWithRedisUserDTO> combined = commentDTOs.stream()
                .map(comment -> new CommentWithRedisUserDTO(
                        comment,
                        userMap.get(comment.getUserId())))
                .collect(Collectors.toList());

        // Return as a Page
        return new PageImpl<>(combined, pageable, commentPage.getTotalElements());
    }

    @Override
    public long countByParentIdAndParentType(UUID parentId, String parentType) {
        LOG.debug("Request to count Comments by parentId: {} and parentType: {}", parentId, parentType);
        return commentRepository.countByParentIdAndParentType(parentId, parentType);
    }

    @Override
    public List<Integer> countCommentsParentIdsAndParentType(List<UUID> parentIds, String parentType) {
        LOG.debug("Request to count Comments for parentIds: {} and parentType: {}", parentIds, parentType);
        List<Object[]> results = commentRepository.countCommentsParentIdsAndParentType(parentIds, parentType);

        // Create a map for quick lookup
        Map<UUID, Integer> countMap = new HashMap<>();
        for (Object[] result : results) {
            UUID parentId = (UUID) result[0];
            Long count = (Long) result[1];
            countMap.put(parentId, count.intValue());
        }

        // Return counts in the same order as input parentIds, with 0 for missing
        // entries
        return parentIds.stream()
                .map(parentId -> countMap.getOrDefault(parentId, 0))
                .collect(Collectors.toList());
    }

}
