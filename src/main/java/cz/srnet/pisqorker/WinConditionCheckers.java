package cz.srnet.pisqorker;

import org.springframework.lang.NonNull;

@FunctionalInterface
interface WinConditionCheckers {

	@NonNull
	WinConditionChecker forRules(@NonNull Rules rules);

}
