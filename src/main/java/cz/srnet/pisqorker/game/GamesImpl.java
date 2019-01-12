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
	public Game newGame(@NonNull Rules rules, @NonNull Player firstPlayer, @NonNull Player secondPlayer) {
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
		String id = newId();
		GameImpl game = new GameImpl(id, context, new MovesRepositoryImpl(context, firstPlayer.piece()), firstPlayer,
				secondPlayer);
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
