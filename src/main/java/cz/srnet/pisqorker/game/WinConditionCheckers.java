package cz.srnet.pisqorker.game;

import org.springframework.lang.NonNull;

@FunctionalInterface
public interface WinConditionCheckers {

	@NonNull
	WinConditionChecker forRules(@NonNull Rules rules);

}
