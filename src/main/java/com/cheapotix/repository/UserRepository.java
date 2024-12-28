package com.cheapotix.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cheapotix.model.AppUser;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	Optional<AppUser> findByEmail(String email);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE users SET arenaids = ARRAY[:arenaids], threshhold = :threshhold WHERE email = :email", 
			nativeQuery = true)
    void updateArenaIdsAndThreshhold(@Param("email") String email,
    										@Param("arenaids") List<String> arenaIds, 
                                            @Param("threshhold") Double threshhold);
}
