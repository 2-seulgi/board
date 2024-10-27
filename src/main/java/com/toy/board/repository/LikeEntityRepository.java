package com.toy.board.repository;

import com.toy.board.model.entity.LikeEntity;
import com.toy.board.model.entity.PostEntity;
import com.toy.board.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Long> {
    List<LikeEntity> findByUser(UserEntity user);
    List<LikeEntity> findByPost(PostEntity post);

    //user 와 post 는 유니크 인덱스라 optional 사용
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

}
