package com.toy.board.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(
    name = "\"like\"",
    indexes = {
            @Index(name = "like_userid_postId_idx", columnList ="userid, postId", unique = true )
    })

public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    @Column
    private ZonedDateTime createDateTime;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "postid")
    private PostEntity post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeEntity that)) return false;
        return Objects.equals(getLikeId(), that.getLikeId())  && Objects.equals(getCreateDateTime(), that.getCreateDateTime())  && Objects.equals(getUser(), that.getUser()) && Objects.equals(getPost(), that.getPost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLikeId(), getCreateDateTime(), getUser(), getPost());
    }

    public static LikeEntity of(UserEntity user, PostEntity post){
        var like = new LikeEntity();
        like.setUser(user);
        like.setPost(post);

        return like;
    }

    @PrePersist
    private void PrePersist(){
        this.createDateTime = ZonedDateTime.now();
    }

}
