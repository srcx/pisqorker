package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

final class GamesImpl implements Games {

	private final @NonNull WinConditionCheckerFactory winConditionCheckerFactory;

	public GamesImpl(@NonNull WinConditionCheckerFactory winConditionCheckerFactory) {
		this.winConditionCheckerFactory = winConditionCheckerFactory;
	}

	@Override
	@NonNull
	public Game newGame(@NonNull Rules rules) {
		GameContext context = new GameContext() {

			@Override
			@NonNull
			public WinConditionChecker winConditionChecker() {
				return winConditionCheckerFactory.create(rules);
			}

			@Override
			@NonNull
			public Rules rules() {
				return rules;
			}
		};
		return new GameImpl(new MovesRepositoryImpl(context));
	}

}
