package com.cheapotix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.cheapotix.model.Game;

public interface GameRepository extends JpaRepository<Game, String> {
	//boolean existsByIdAndArenaid(String id, String arenaId);
	
	Optional<Game> findByIdAndArenaId(String id, String arenaId);
;}
