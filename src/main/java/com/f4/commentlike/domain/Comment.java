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
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "parent_type", nullable = false)
    private String parentType;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "parent_id", length = 36, nullable = false)
    private UUID parentId;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "user_id", length = 36, nullable = false)
    private UUID userId;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @NotNull
    @Column(name = "likes_count", nullable = false)
    private Integer likesCount;

    @NotNull
    @Column(name = "replies_count", nullable = false)
    private Integer repliesCount;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "mentions", length = 36)
    private UUID mentions;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Comment id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getParentType() {
        return this.parentType;
    }

    public Comment parentType(String parentType) {
        this.setParentType(parentType);
        return this;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public UUID getParentId() {
        return this.parentId;
    }

    public Comment parentId(UUID parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public Comment userId(UUID userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getContent() {
        return this.content;
    }

    public Comment content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Comment createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Comment updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getLikesCount() {
        return this.likesCount;
    }

    public Comment likesCount(Integer likesCount) {
        this.setLikesCount(likesCount);
        return this;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getRepliesCount() {
        return this.repliesCount;
    }

    public Comment repliesCount(Integer repliesCount) {
        this.setRepliesCount(repliesCount);
        return this;
    }

    public void setRepliesCount(Integer repliesCount) {
        this.repliesCount = repliesCount;
    }

    public UUID getMentions() {
        return this.mentions;
    }

    public Comment mentions(UUID mentions) {
        this.setMentions(mentions);
        return this;
    }

    public void setMentions(UUID mentions) {
        this.mentions = mentions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }
        return getId() != null && getId().equals(((Comment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
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
