package com.f4.commentlike.service.impl;

import com.f4.commentlike.client.api.UserResourceApi;
import com.f4.commentlike.client.model.RedisUserDTO;
import com.f4.commentlike.domain.Like;
import com.f4.commentlike.repository.LikeRepository;
import com.f4.commentlike.service.LikeService;
import com.f4.commentlike.service.dto.LikeDTO;
import com.f4.commentlike.service.dto.LikeWithRedisUserDTO;
import com.f4.commentlike.service.mapper.LikeMapper;

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
 * Service Implementation for managing {@link com.f4.commentlike.domain.Like}.
 */
@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    private static final Logger LOG = LoggerFactory.getLogger(LikeServiceImpl.class);

    private final LikeRepository likeRepository;

    private final LikeMapper likeMapper;

    private final UserResourceApi userResourceApi;

    public LikeServiceImpl(LikeRepository likeRepository, LikeMapper likeMapper, UserResourceApi userResourceApi) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;
        this.userResourceApi = userResourceApi;
    }

    @Override
    public LikeDTO save(LikeDTO likeDTO) {
        LOG.debug("Request to save Like : {}", likeDTO);
        Like like = likeMapper.toEntity(likeDTO);
        like = likeRepository.save(like);
        return likeMapper.toDto(like);
    }

    @Override
    public LikeDTO update(LikeDTO likeDTO) {
        LOG.debug("Request to update Like : {}", likeDTO);
        Like like = likeMapper.toEntity(likeDTO);
        like = likeRepository.save(like);
        return likeMapper.toDto(like);
    }

    @Override
    public Optional<LikeDTO> partialUpdate(LikeDTO likeDTO) {
        LOG.debug("Request to partially update Like : {}", likeDTO);

        return likeRepository
                .findById(likeDTO.getId())
                .map(existingLike -> {
                    likeMapper.partialUpdate(existingLike, likeDTO);

                    return existingLike;
                })
                .map(likeRepository::save)
                .map(likeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LikeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Likes");
        return likeRepository.findAll(pageable).map(likeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LikeDTO> findOne(UUID id) {
        LOG.debug("Request to get Like : {}", id);
        return likeRepository.findById(id).map(likeMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        LOG.debug("Request to delete Like : {}", id);
        likeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LikeWithRedisUserDTO> findByParentIdAndParentType(UUID parentId, String parentType,
            Pageable pageable) {
        LOG.debug("Request to get userIds from Likes by parentId: {} and parentType: {}", parentId, parentType);

        Page<LikeDTO> likePage = likeRepository.findByParentIdAndParentType(parentId, parentType, pageable)
                .map(likeMapper::toDto);

        List<LikeDTO> likeDTOs = likePage.getContent();

        List<UUID> userIds = likeDTOs.stream()
                .map(LikeDTO::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        List<RedisUserDTO> redisUserDTOs = userResourceApi.getUsersFromRedisPost(userIds);

        // Map userId -> RedisUserDTO
        Map<UUID, RedisUserDTO> userMap = redisUserDTOs.stream()
                .collect(Collectors.toMap(RedisUserDTO::getId, Function.identity()));

        // Build LikeWithRedisUserDTO list
        List<LikeWithRedisUserDTO> combined = likeDTOs.stream()
                .map(like -> new LikeWithRedisUserDTO(
                        like,
                        userMap.get(like.getUserId())))
                .collect(Collectors.toList());

        // Return as a Page
        return new PageImpl<>(combined, pageable, likePage.getTotalElements());
    }

    @Override
    public long countByParentIdAndParentType(UUID parentId, String parentType) {
        LOG.debug("Request to count Likes by parentId: {} and parentType: {}", parentId, parentType);
        return likeRepository.countByParentIdAndParentType(parentId, parentType);
    }

    @Override
    public List<Integer> countLikesParentIdsAndParentType(List<UUID> parentIds, String parentType) {
        List<Object[]> rawCounts = likeRepository.countLikesParentIdsAndParentType(parentIds, parentType);

        // Map UUID → count
        Map<UUID, Integer> countMap = rawCounts.stream()
                .collect(Collectors.toMap(
                        row -> (UUID) row[0],
                        row -> ((Number) row[1]).intValue()));

        // Return ordered list of counts matching the input parentIds
        return parentIds.stream()
                .map(id -> countMap.getOrDefault(id, 0))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByParentIdAndUserId(UUID parentId, UUID userId) {
        LOG.debug("Request to check if Like exists by parentId: {} and userId: {}", parentId, userId);
        return likeRepository.existsByParentIdAndUserId(parentId, userId);
    }

    @Override
    public void deleteByParentIdAndUserId(UUID parentId, UUID userId) {
        LOG.debug("Request to delete Like by parentId: {} and userId: {}", parentId, userId);
        likeRepository.deleteByParentIdAndUserId(parentId, userId);
    }

}
