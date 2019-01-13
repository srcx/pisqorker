package cz.srnet.pisqorker.game;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
final class GamesImpl implements Games {

	private final @NonNull WinConditionCheckers winConditionCheckers;
	private final @NonNull AtomicLong nextId = new AtomicLong();
	private final @NonNull Map<String, Game> games = new ConcurrentHashMap<>();

	public GamesImpl(@NonNull WinConditionCheckers winConditionCheckers) {
		this.winConditionCheckers = winConditionCheckers;
	}

	@Override
	@NonNull
	public Game newGame(@NonNull Rules rules, @NonNull Players players) {
		GameContext context = new GameContext() {

			@Override
			@NonNull
			public WinConditionChecker winConditionChecker() {
				return winConditionCheckers.forRules(rules);
			}

			@Override
			@NonNull
			public Rules rules() {
				return rules;
			}
		};
		MovesRepositoryImpl movesRepo = new MovesRepositoryImpl(context, players.first().piece(),
				piece -> checkMove(players, Objects.requireNonNull(piece)));
		String id = newId();
		GameImpl game = new GameImpl(id, context, movesRepo, players);
		games.put(id, game);
		return game;
	}

	private void checkMove(@NonNull Players players, @NonNull Piece piece) {
		Player nextPlayer = players.with(piece);
		if (!nextPlayer.isPlayableNow()) {
			throw new IllegalArgumentException("Forbidden to play as " + nextPlayer);
		}
	}

	private @NonNull String newId() {
		return Objects.requireNonNull(String.valueOf(nextId.getAndIncrement()));
	}

	@Override
	@NonNull
	public Optional<Game> game(@NonNull String id) {
		return Objects.requireNonNull(Optional.ofNullable(games.get(id)));
	}

}
