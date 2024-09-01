package com.toy.board.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "post")
@SQLDelete(sql="UPDATE \"post\" SET deletedatetime = CURRENT_TIMESTAMP WHERE postId = ?" )
@SQLRestriction("deletedatetime IS NULL")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Column
    private ZonedDateTime createDateTime;
    @Column
    private ZonedDateTime updateDateTime;
    @Column
    private ZonedDateTime deleteDateTime;

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreateDateTime(ZonedDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public void setUpdateDateTime(ZonedDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public void setDeleteDateTime(ZonedDateTime deleteDateTime) {
        this.deleteDateTime = deleteDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostEntity that)) return false;
        return Objects.equals(getPostId(), that.getPostId()) && Objects.equals(getBody(), that.getBody()) && Objects.equals(getCreateDateTime(), that.getCreateDateTime()) && Objects.equals(getUpdateDateTime(), that.getUpdateDateTime()) && Objects.equals(getDeleteDateTime(), that.getDeleteDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getBody(), getCreateDateTime(), getUpdateDateTime(), getDeleteDateTime());
    }

    @PrePersist
    private void PrePersist(){
        this.createDateTime = ZonedDateTime.now();
        this.updateDateTime = ZonedDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.updateDateTime = ZonedDateTime.now();
    }


}
