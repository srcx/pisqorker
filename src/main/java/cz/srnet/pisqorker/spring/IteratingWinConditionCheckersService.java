package cz.srnet.pisqorker.spring;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import cz.srnet.pisqorker.game.IteratingWinConditionChecker;
import cz.srnet.pisqorker.game.Rules;
import cz.srnet.pisqorker.game.WinConditionChecker;
import cz.srnet.pisqorker.game.WinConditionCheckers;

@Service
final class IteratingWinConditionCheckersService implements WinConditionCheckers {

	@Override
	@NonNull
	public WinConditionChecker forRules(@NonNull Rules rules) {
		return new IteratingWinConditionChecker(rules);
	}

}
