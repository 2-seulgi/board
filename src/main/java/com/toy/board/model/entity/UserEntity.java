package com.toy.board.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;

@Entity
@Table(name = "\"user\"",
indexes = { @Index(name="user_username_idx", columnList = "username", unique = true)})
            @SQLDelete(sql = "UPDATE \"user\" SET deletedatetime = CURRENT_TIMESTAMP WHERE userId = ?" )
            @SQLRestriction("deletedatetime IS NULL")
public class UserEntity implements UserDetails {


    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false) private String username;
    @Column(nullable = false) private String password;
    @Getter
    @Column private String profile;
    @Getter
    @Column private String description;
    @Getter
    @Column
    private ZonedDateTime createDateTime;
    @Getter
    @Column
    private ZonedDateTime updateDateTime;
    @Getter
    @Column
    private ZonedDateTime deleteDateTime;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getProfile(), that.getProfile()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getCreateDateTime(), that.getCreateDateTime()) && Objects.equals(getUpdateDateTime(), that.getUpdateDateTime()) && Objects.equals(getDeleteDateTime(), that.getDeleteDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getPassword(), getProfile(), getDescription(), getCreateDateTime(), getUpdateDateTime(), getDeleteDateTime());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public static UserEntity of(String username, String password){
        var userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);

        //랜덤한 프로필 사진 설정
        userEntity.setProfile("https://avatar.iran.liara.run/public/" + (new Random().nextInt(100) + 1));

        return userEntity;
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
