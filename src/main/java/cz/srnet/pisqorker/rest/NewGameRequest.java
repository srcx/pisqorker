package cz.srnet.pisqorker.rest;

import java.util.Objects;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.Games;
import cz.srnet.pisqorker.game.Players;
import cz.srnet.pisqorker.game.Rules;

final class NewGameRequest {

	private Rules rules;
	private Players players;

	public @NonNull Game execute(@NonNull Games games) {
		// TODO better validation
		return games.newGame(Objects.requireNonNull(rules), Objects.requireNonNull(players));
	}

	public Rules getRules() {
		return rules;
	}

	public void setRules(Rules rules) {
		this.rules = rules;
	}

	public Players getPlayers() {
		return players;
	}

	public void setPlayers(Players players) {
		this.players = players;
	}

}
