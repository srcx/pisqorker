package cz.srnet.pisqorker.rest;

import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.Games;
import cz.srnet.pisqorker.game.Rules;

@RestController
final class GamesRestController {

	private final @NonNull Games games;

	public GamesRestController(@NonNull Games games) {
		this.games = games;
	}

	@PostMapping(RestApiVersions.v1 + "/games")
	public @NonNull Game newGame(@RequestBody Rules rules) {
		return games.newGame(Objects.requireNonNull(rules));
	}

}
