package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

final class FakeGameContext implements GameContext {

	private @NonNull Rules rules = new FakeRules(21, 5);
	private @NonNull WinConditionChecker winConditionChecker = new WinConditionChecker() {

		@Override
		public boolean isWon(@NonNull Move move) {
			return false;
		}
	};
	private @NonNull FakeNextMoves nextMoves = new FakeNextMoves();

	@NonNull
	FakeGameContext _rules(@NonNull Rules rules) {
		this.rules = rules;
		return this;
	}

	@NonNull
	FakeGameContext _winConditionChecker(@NonNull WinConditionChecker winConditionChecker) {
		this.winConditionChecker = winConditionChecker;
		return this;
	}

	@Override
	@NonNull
	public Rules rules() {
		return rules;
	}

	@Override
	@NonNull
	public WinConditionChecker winConditionChecker() {
		return winConditionChecker;
	}

	@Override
	@NonNull
	public FakeNextMoves nextMoves() {
		return nextMoves;
	}

}
