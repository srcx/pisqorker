package cz.srnet.pisqorker.game;

import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
final class GamesImpl implements Games {

	private final @NonNull GamesRepository repository;

	public GamesImpl(@NonNull GamesRepository repository) {
		this.repository = repository;
	}

	@Override
	@NonNull
	public Game newGame(@NonNull Rules rules, @NonNull Players players) {
		if (!isPlayableNow(players)) {
			throw new IllegalArgumentException("Forbidden to play game with players " + players);
		}
		return repository.newGame(rules, players, piece -> checkMove(players, Objects.requireNonNull(piece)));
	}

	private static void checkMove(@NonNull Players players, @NonNull Piece piece) {
		Player nextPlayer = players.with(piece);
		if (!nextPlayer.isPlayableNow()) {
			throw new IllegalArgumentException("Forbidden to play as " + nextPlayer);
		}
	}

	@Override
	@NonNull
	public Optional<Game> game(@NonNull String id) {
		return Objects.requireNonNull(repository.game(id).filter(game -> isPlayableNow(game.players())));
	}

	private boolean isPlayableNow(@NonNull Players players) {
		return players.first().isPlayableNow() || players.second().isPlayableNow();
	}

}
