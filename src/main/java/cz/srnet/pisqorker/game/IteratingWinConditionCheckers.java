package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
final class IteratingWinConditionCheckers implements WinConditionCheckers {

	@Override
	@NonNull
	public WinConditionChecker forRules(@NonNull Rules rules) {
		return new IteratingWinConditionChecker(rules);
	}

}
