package cz.srnet.pisqorker.spring;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.Games;
import cz.srnet.pisqorker.game.Rules;
import cz.srnet.pisqorker.game.WinConditionCheckers;

@Service
final class GamesService implements Games {

	private final @NonNull Games inner;

	public GamesService(@NonNull WinConditionCheckers winConditionCheckers) {
		inner = Games.create(winConditionCheckers);
	}

	@Override
	@NonNull
	public Game newGame(@NonNull Rules rules) {
		return inner.newGame(rules);
	}

}
