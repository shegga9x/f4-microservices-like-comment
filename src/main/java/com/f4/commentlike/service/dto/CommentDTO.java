package com.f4.commentlike.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.f4.commentlike.domain.Comment} entity.
 */
@Schema(
    description = "The Comment entity, representing a user's comment on a parent item.\nThe parent can be a Reel, another Comment, etc. (Polymorphic Association)"
)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentDTO implements Serializable {

    @NotNull
    private UUID id;

    @NotNull
    private UUID parentId;

    @NotNull
    private String parentType;

    @NotNull
    private UUID userId;

    @Lob
    private String content;

    @NotNull
    private Instant createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
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
            ", parentId='" + getParentId() + "'" +
            ", parentType='" + getParentType() + "'" +
            ", userId='" + getUserId() + "'" +
            ", content='" + getContent() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
