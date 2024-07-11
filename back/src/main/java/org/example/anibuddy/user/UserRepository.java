package org.example.anibuddy.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
