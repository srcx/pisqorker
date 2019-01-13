package cz.srnet.pisqorker.rest;

import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cz.srnet.pisqorker.game.Game;
import cz.srnet.pisqorker.game.Games;

@RestController
@RequestMapping(RestApiVersions.v1)
final class GamesRestController {

	private final @NonNull Games games;

	public GamesRestController(@NonNull Games games) {
		this.games = games;
	}

	@PostMapping("/games")
	public @NonNull Game newGame(@NonNull @RequestBody NewGameRequest request) {
		return request.execute(games);
	}

	@GetMapping("/games/{id}")
	public @NonNull Game game(@NonNull @PathVariable String id) {
		return Objects.requireNonNull(games.game(id).orElseThrow(() -> new GameNotFoundException(id)));
	}

}
