package com.cheapotix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import com.cheapotix.model.Game;

public interface GameRepository extends JpaRepository<Game, String> {
	
	Optional<Game> findByIdAndArenaId(String id, String arenaId);
	
	//return the games specified but include prices slightly higher in case there are not many games available
	@Query("SELECT g FROM Game g WHERE g.arenaId IN :arenaIds AND g.minPrice <= :threshhold + 5")
    List<Game> findByArenaIdsAndThreshhold(@Param("arenaIds") List<String> arenaIds, 
                                            @Param("threshhold") Double threshhold);
;}
