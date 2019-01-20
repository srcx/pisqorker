package cz.srnet.pisqorker.game;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
final class InMemoryGamesRepository implements GamesRepository {

	private final @NonNull WinConditionCheckers winConditionCheckers;
	private final @NonNull AtomicLong nextId = new AtomicLong();
	private final @NonNull Map<String, Game> games = new ConcurrentHashMap<>();

	public InMemoryGamesRepository(@NonNull WinConditionCheckers winConditionCheckers) {
		this.winConditionCheckers = winConditionCheckers;
	}

	@Override
	@NonNull
	public Game newGame(@NonNull Rules rules, @NonNull Players players, @NonNull Consumer<Piece> moveChecker) {
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
		MovesRepositoryImpl movesRepo = new MovesRepositoryImpl(context, players.first().piece(), moveChecker);
		String id = newId();
		GameImpl game = new GameImpl(id, context, movesRepo, players);
		games.put(id, game);
		return game;
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
