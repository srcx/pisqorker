package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

final class GamesImpl implements Games {

	private final @NonNull WinConditionCheckers winConditionCheckers;

	public GamesImpl(@NonNull WinConditionCheckers winConditionCheckers) {
		this.winConditionCheckers = winConditionCheckers;
	}

	@Override
	@NonNull
	public Game newGame(@NonNull Rules rules) {
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
		return new GameImpl(context, new MovesRepositoryImpl(context));
	}

}
