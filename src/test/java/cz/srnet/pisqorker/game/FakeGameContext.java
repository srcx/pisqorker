package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

import cz.srnet.pisqorker.game.GameContext;
import cz.srnet.pisqorker.game.Move;
import cz.srnet.pisqorker.game.Rules;
import cz.srnet.pisqorker.game.WinConditionChecker;

final class FakeGameContext implements GameContext {

	private @NonNull Rules rules = new FakeRules(21, 5);
	private @NonNull WinConditionChecker winConditionChecker = new WinConditionChecker() {

		@Override
		public boolean isWon(@NonNull Move move) {
			return false;
		}
	};
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

}
