package com.f4.commentlike.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.f4.commentlike.domain.Comment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentDTO implements Serializable {

    @NotNull
    private UUID id;

    @NotNull
    private String parentType;

    @NotNull
    private UUID parentId;

    @NotNull
    private UUID userId;

    @Lob
    private String content;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    @NotNull
    private Integer likesCount;

    @NotNull
    private Integer repliesCount;

    private UUID mentions;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(Integer repliesCount) {
        this.repliesCount = repliesCount;
    }

    public UUID getMentions() {
        return mentions;
    }

    public void setMentions(UUID mentions) {
        this.mentions = mentions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentDTO)) {
            return false;
        }

        CommentDTO commentDTO = (CommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentDTO{" +
            "id='" + getId() + "'" +
            ", parentType='" + getParentType() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", userId='" + getUserId() + "'" +
            ", content='" + getContent() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", likesCount=" + getLikesCount() +
            ", repliesCount=" + getRepliesCount() +
            ", mentions='" + getMentions() + "'" +
            "}";
    }
}
