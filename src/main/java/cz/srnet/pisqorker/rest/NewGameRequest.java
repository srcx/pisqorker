package cz.srnet.pisqorker.rest;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.Games;
import cz.srnet.pisqorker.game.Players;
import cz.srnet.pisqorker.game.Rules;

final class NewGameRequest {

	private final @NonNull Rules rules;
	private final @NonNull Players players;

	@JsonCreator
	public NewGameRequest(@JsonProperty(value = "rules", required = true) @NonNull Rules rules,
			@JsonProperty(value = "players", required = true) @NonNull Players players) {
		this.rules = rules;
		this.players = players;
	}

	public @NonNull Game execute(@NonNull Games games) {
		return games.newGame(rules, players);
	}

}
