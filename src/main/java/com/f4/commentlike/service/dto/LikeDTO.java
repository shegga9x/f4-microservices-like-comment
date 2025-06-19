package com.f4.commentlike.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.f4.commentlike.domain.Like} entity.
 */
@Schema(
    description = "The Like entity, representing a user's like on a parent item.\nThe parent can be a Reel, a Comment, etc. (Polymorphic Association)"
)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LikeDTO implements Serializable {

    @NotNull
    private UUID id;

    @NotNull
    private UUID parentId;

    @NotNull
    private String parentType;

    @NotNull
    private UUID userId;

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
        if (!(o instanceof LikeDTO)) {
            return false;
        }

        LikeDTO likeDTO = (LikeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, likeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LikeDTO{" +
            "id='" + getId() + "'" +
            ", parentId='" + getParentId() + "'" +
            ", parentType='" + getParentType() + "'" +
            ", userId='" + getUserId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
