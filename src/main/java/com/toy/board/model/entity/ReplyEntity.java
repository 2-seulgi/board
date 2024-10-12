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
    name = "reply",
    indexes = {@Index(name = "reply_userid_idx", columnList ="userid" ),
               @Index(name = "reply_userid_idx", columnList ="userid" )})
@SQLDelete(sql="UPDATE \"reply\" SET deletedatetime = CURRENT_TIMESTAMP WHERE replyid = ?" )
@SQLRestriction("deletedatetime IS NULL")
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Column
    private ZonedDateTime createDateTime;
    @Column
    private ZonedDateTime updateDateTime;
    @Column
    private ZonedDateTime deleteDateTime;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "postid")
    private PostEntity post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReplyEntity that)) return false;
        return Objects.equals(getReplyId(), that.getReplyId()) && Objects.equals(getBody(), that.getBody()) && Objects.equals(getCreateDateTime(), that.getCreateDateTime()) && Objects.equals(getUpdateDateTime(), that.getUpdateDateTime()) && Objects.equals(getDeleteDateTime(), that.getDeleteDateTime()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getPost(), that.getPost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReplyId(), getBody(), getCreateDateTime(), getUpdateDateTime(), getDeleteDateTime(), getUser(), getPost());
    }

    public static ReplyEntity of(String body, UserEntity user, PostEntity post){
        var reply = new ReplyEntity();
        reply.setBody(body);
        reply.setUser(user);
        reply.setPost(post);

        return reply;
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
