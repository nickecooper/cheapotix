package com.cheapotix.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cheapotix.model.Game;
import com.cheapotix.repository.GameRepository;

import java.text.MessageFormat;

@Service
public class GameService {

	private final GameRepository gameRepository;
	
	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}
	
//	public Game addGame(String id, String title, String ticketsLink, String arenaId, double minPrice, boolean dateHasPassed) {
//		Game newGame = new Game(id,  title,  ticketsLink,  arenaId,  minPrice,  dateHasPassed) ;
//		return gameRepository.save(newGame);
//	}
//	
//	public Game updateGameStatus(String id, double minPrice, boolean dateHasPassed) {
//		Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"))
//	}
//	
//	public boolean doesGameExist(String id, String arenaId) {
//		return gameRepository.existsByIdAndArenaid(id, arenaId);
//	}
	public Game addOrUpdateGame(String id, String title, String ticketsLink, String arenaId, double minPrice, LocalDateTime date) {
		
		Optional<Game> game = gameRepository.findByIdAndArenaId(id, arenaId);
		
		Game newGame;
		if (game.isPresent()) {
			//if game is already in database then update with latest minimum price from ticketmaster api
			newGame = game.get();
			if (newGame.getMinPrice() != minPrice) {
				System.out.println(MessageFormat.format("OLD: {0}, NEW: {1}, TITLE: {2}, LINK: {3}", 
						newGame.getMinPrice(), minPrice, title, ticketsLink));
			}
			newGame.setMinPrice(minPrice);
		
		}else {
			//if game not in database yet then add
			newGame = new Game(id,  title,  ticketsLink,  arenaId,  minPrice,  date);
		}
		return gameRepository.save(newGame);
	}
	
}
