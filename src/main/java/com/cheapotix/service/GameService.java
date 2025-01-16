package com.cheapotix.service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
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
	
	//grabs games by arena id and threshhold then sort by date and remove games in the past
	public List<Game> getSortedGames(List<String> arenaIds, double threshhold){
		List<Game> unsortedGames = gameRepository.findByArenaIdsAndThreshhold(arenaIds, threshhold);
		
		//removes games that are in the past
		Iterator<Game> iter = unsortedGames.iterator();
		while (iter.hasNext()) {
			Game game = iter.next();
			if (game.getDate().isBefore(LocalDateTime.now())) {
				iter.remove();
			}
		}
		
		//sorts games by ascending order of date and time
		unsortedGames.sort((g1, g2) -> g1.getDate().compareTo(g2.getDate()));
		
		return unsortedGames;
		
	}
	
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
