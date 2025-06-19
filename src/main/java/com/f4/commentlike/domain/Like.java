package com.f4.commentlike.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * The Like entity, representing a user's like on a parent item.
 * The parent can be a Reel, a Comment, etc. (Polymorphic Association)
 */
@Entity
@Table(name = "jhi_like")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Like implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false)
    private UUID id;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "parent_id", length = 36, nullable = false)
    private UUID parentId;

    @NotNull
    @Column(name = "parent_type", nullable = false)
    private String parentType;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "user_id", length = 36, nullable = false)
    private UUID userId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Like id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getParentId() {
        return this.parentId;
    }

    public Like parentId(UUID parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getParentType() {
        return this.parentType;
    }

    public Like parentType(String parentType) {
        this.setParentType(parentType);
        return this;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public Like userId(UUID userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Like createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Like)) {
            return false;
        }
        return getId() != null && getId().equals(((Like) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Like{" +
            "id=" + getId() +
            ", parentId='" + getParentId() + "'" +
            ", parentType='" + getParentType() + "'" +
            ", userId='" + getUserId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
